package com.mega.megasenachecker.infrastructure.adapter.out.api;

import com.mega.megasenachecker.domain.model.Concurso;
import com.mega.megasenachecker.domain.port.out.LoteriasGateway;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LoteriasApiAdapter implements LoteriasGateway {

    private static final String URL = "https://loteriascaixa-api.herokuapp.com/api/megasena/latest";
    private final RestTemplate restTemplate;

    public LoteriasApiAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Concurso buscarUltimoSorteio() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        headers.set("Accept", "application/json");

        ResponseEntity<LoteriasApiResponse> response = restTemplate.exchange(
                URL, HttpMethod.GET, new HttpEntity<>(headers), LoteriasApiResponse.class);

        LoteriasApiResponse body = response.getBody();
        if (body == null) return null;

        return new Concurso(
                body.getConcurso(),
                body.getDezenas() != null ? body.getDezenas() : List.of(),
                body.getData()
        );
    }
}


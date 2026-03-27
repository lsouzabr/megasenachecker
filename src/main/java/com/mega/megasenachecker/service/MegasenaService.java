package com.mega.megasenachecker.service;

import com.mega.megasenachecker.model.MegasenaDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MegasenaService {

    private final RestTemplate restTemplate;

    public MegasenaService() {
        this.restTemplate = new RestTemplate();
    }

    public MegasenaDocument obterResultadoSorteio() {
        String url = "https://servicebus2.caixa.gov.br/portaldeloterias/api/megasena";
        return restTemplate.getForObject(url, MegasenaDocument.class);
    }
}

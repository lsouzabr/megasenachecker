package com.mega.megasenachecker.infrastructure.adapter.out.api;

import com.mega.megasenachecker.domain.model.Concurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class LoteriasApiAdapterTest {

    private MockRestServiceServer mockServer;
    private LoteriasApiAdapter adapter;

    private static final String URL = "https://loteriascaixa-api.herokuapp.com/api/megasena/latest";

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        adapter = new LoteriasApiAdapter(restTemplate);
    }

    @Test
    void buscarUltimoSorteio_deveRetornarConcursoMapeado() {
        mockServer.expect(requestTo(URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                          "concurso": 2800,
                          "data": "01/01/2026",
                          "dezenas": ["04", "11", "15", "29", "38", "41"]
                        }
                        """, MediaType.APPLICATION_JSON));

        Concurso concurso = adapter.buscarUltimoSorteio();

        assertThat(concurso).isNotNull();
        assertThat(concurso.getNumero()).isEqualTo(2800);
        assertThat(concurso.getData()).isEqualTo("01/01/2026");
        assertThat(concurso.getDezenas()).containsExactly("04", "11", "15", "29", "38", "41");
        mockServer.verify();
    }

    @Test
    void buscarUltimoSorteio_deveRetornarListaVaziaQuandoDezenasAusentes() {
        mockServer.expect(requestTo(URL))
                .andRespond(withSuccess("""
                        {
                          "concurso": 2800,
                          "data": "01/01/2026"
                        }
                        """, MediaType.APPLICATION_JSON));

        Concurso concurso = adapter.buscarUltimoSorteio();

        assertThat(concurso.getDezenas()).isEmpty();
    }

    @Test
    void buscarUltimoSorteio_quandoApiRetornaErro_deveLancarExcecao() {
        mockServer.expect(requestTo(URL))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThatThrownBy(() -> adapter.buscarUltimoSorteio())
                .isInstanceOf(Exception.class);
    }

    @Test
    void buscarUltimoSorteio_deveEnviarHeaderUserAgentCorreto() {
        mockServer.expect(requestTo(URL))
                .andExpect(header("User-Agent", org.hamcrest.Matchers.containsString("Mozilla")))
                .andExpect(header("Accept", "application/json"))
                .andRespond(withSuccess("""
                        { "concurso": 2800, "data": "01/01/2026", "dezenas": [] }
                        """, MediaType.APPLICATION_JSON));

        adapter.buscarUltimoSorteio();

        mockServer.verify();
    }
}


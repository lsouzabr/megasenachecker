package com.mega.megasenachecker.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConcursoTest {

    @Test
    void construtorPadrao_deveCriarObjetoComCamposNulos() {
        Concurso concurso = new Concurso();

        assertThat(concurso.getNumero()).isNull();
        assertThat(concurso.getDezenas()).isNull();
        assertThat(concurso.getData()).isNull();
    }

    @Test
    void construtorComParametros_devePreencherTodosOsCampos() {
        List<String> dezenas = List.of("01", "15", "22", "33", "44", "55");
        Concurso concurso = new Concurso(2800, dezenas, "01/01/2026");

        assertThat(concurso.getNumero()).isEqualTo(2800);
        assertThat(concurso.getDezenas()).isEqualTo(dezenas);
        assertThat(concurso.getData()).isEqualTo("01/01/2026");
    }

    @Test
    void setters_devemAtualizarCamposCorretamente() {
        Concurso concurso = new Concurso();
        List<String> dezenas = List.of("04", "11", "15", "29", "38", "41");

        concurso.setNumero(2801);
        concurso.setDezenas(dezenas);
        concurso.setData("02/01/2026");

        assertThat(concurso.getNumero()).isEqualTo(2801);
        assertThat(concurso.getDezenas()).isEqualTo(dezenas);
        assertThat(concurso.getData()).isEqualTo("02/01/2026");
    }
}


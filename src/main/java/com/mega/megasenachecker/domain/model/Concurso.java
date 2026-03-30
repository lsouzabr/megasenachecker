package com.mega.megasenachecker.domain.model;

import java.util.List;

public class Concurso {

    private Integer numero;
    private List<String> dezenas;
    private String data;
    private Integer quantidadeAcertos;
    private List<String> numerosAcertados;

    public Concurso() {}

    public Concurso(Integer numero, List<String> dezenas, String data) {
        this.numero = numero;
        this.dezenas = dezenas;
        this.data = data;
    }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public List<String> getDezenas() { return dezenas; }
    public void setDezenas(List<String> dezenas) { this.dezenas = dezenas; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Integer getQuantidadeAcertos() { return quantidadeAcertos; }
    public void setQuantidadeAcertos(Integer quantidadeAcertos) { this.quantidadeAcertos = quantidadeAcertos; }

    public List<String> getNumerosAcertados() { return numerosAcertados; }
    public void setNumerosAcertados(List<String> numerosAcertados) { this.numerosAcertados = numerosAcertados; }
}

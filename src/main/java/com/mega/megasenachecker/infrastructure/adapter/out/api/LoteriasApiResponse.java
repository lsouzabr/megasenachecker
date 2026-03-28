package com.mega.megasenachecker.infrastructure.adapter.out.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoteriasApiResponse {

    private Integer concurso;
    private String data;
    private List<String> dezenas;

    public Integer getConcurso() { return concurso; }
    public void setConcurso(Integer concurso) { this.concurso = concurso; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public List<String> getDezenas() { return dezenas; }
    public void setDezenas(List<String> dezenas) { this.dezenas = dezenas; }
}


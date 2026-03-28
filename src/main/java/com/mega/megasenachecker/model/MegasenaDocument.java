package com.mega.megasenachecker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MegasenaDocument {

    private Integer concurso;
    private String data;
    private List<String> dezenas;
    private List<String> dezenasOrdemSorteio;
    private String loteria;
    private String local;
    private Boolean acumulou;
    private Integer proximoConcurso;
    private String dataProximoConcurso;
    private String observacao;

    // Getters and Setters

    public Integer getConcurso() { return concurso; }
    public void setConcurso(Integer concurso) { this.concurso = concurso; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public List<String> getDezenas() { return dezenas; }
    public void setDezenas(List<String> dezenas) { this.dezenas = dezenas; }

    public List<String> getDezenasOrdemSorteio() { return dezenasOrdemSorteio; }
    public void setDezenasOrdemSorteio(List<String> dezenasOrdemSorteio) { this.dezenasOrdemSorteio = dezenasOrdemSorteio; }

    public String getLoteria() { return loteria; }
    public void setLoteria(String loteria) { this.loteria = loteria; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public Boolean getAcumulou() { return acumulou; }
    public void setAcumulou(Boolean acumulou) { this.acumulou = acumulou; }

    public Integer getProximoConcurso() { return proximoConcurso; }
    public void setProximoConcurso(Integer proximoConcurso) { this.proximoConcurso = proximoConcurso; }

    public String getDataProximoConcurso() { return dataProximoConcurso; }
    public void setDataProximoConcurso(String dataProximoConcurso) { this.dataProximoConcurso = dataProximoConcurso; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}

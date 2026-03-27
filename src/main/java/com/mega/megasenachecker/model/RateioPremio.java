package com.mega.megasenachecker.model;

public class RateioPremio {
    private String descricaoFaixa;
    private Integer faixa;
    private Integer numeroDeGanhadores;
    private Double valorPremio;

    public String getDescricaoFaixa() { return descricaoFaixa; }
    public void setDescricaoFaixa(String descricaoFaixa) { this.descricaoFaixa = descricaoFaixa; }
    public Integer getFaixa() { return faixa; }
    public void setFaixa(Integer faixa) { this.faixa = faixa; }
    public Integer getNumeroDeGanhadores() { return numeroDeGanhadores; }
    public void setNumeroDeGanhadores(Integer numeroDeGanhadores) { this.numeroDeGanhadores = numeroDeGanhadores; }
    public Double getValorPremio() { return valorPremio; }
    public void setValorPremio(Double valorPremio) { this.valorPremio = valorPremio; }
}

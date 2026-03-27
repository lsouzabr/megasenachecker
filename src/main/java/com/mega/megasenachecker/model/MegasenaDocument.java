package com.mega.megasenachecker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "megasena_results")
public class MegasenaDocument {

    @Id
    private String id;

    private Boolean acumulado;
    private String dataApuracao;
    private String dataProximoConcurso;
    private List<String> dezenasSorteadasOrdemSorteio;
    private Boolean exibirDetalhamentoPorCidade;
    private Integer indicadorConcursoEspecial;
    private List<String> listaDezenas;
    private List<String> listaDezenasSegundoSorteio;
    private List<String> listaMunicipioUFGanhadores;
    private List<RateioPremio> listaRateioPremio;
    private String listaResultadoEquipeEsportiva;
    private String localSorteio;
    private String nomeMunicipioUFSorteio;
    private String nomeTimeCoracaoMesSorte;
    private Integer numero;
    private Integer numeroConcursoAnterior;
    private Integer numeroConcursoFinal_0_5;
    private Integer numeroConcursoProximo;
    private Integer numeroJogo;
    private String observacao;
    private String premiacaoContingencia;
    private String tipoJogo;
    private Integer tipoPublicacao;
    private Boolean ultimoConcurso;
    private Long valorArrecadado;
    private Double valorAcumuladoConcurso_0_5;
    private Double valorAcumuladoConcursoEspecial;
    private Double valorAcumuladoProximoConcurso;
    private Double valorEstimadoProximoConcurso;
    private Double valorSaldoReservaGarantidora;
    private Double valorTotalPremioFaixaUm;

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Boolean getAcumulado() { return acumulado; }
    public void setAcumulado(Boolean acumulado) { this.acumulado = acumulado; }
    public String getDataApuracao() { return dataApuracao; }
    public void setDataApuracao(String dataApuracao) { this.dataApuracao = dataApuracao; }
    public String getDataProximoConcurso() { return dataProximoConcurso; }
    public void setDataProximoConcurso(String dataProximoConcurso) { this.dataProximoConcurso = dataProximoConcurso; }
    public List<String> getDezenasSorteadasOrdemSorteio() { return dezenasSorteadasOrdemSorteio; }
    public void setDezenasSorteadasOrdemSorteio(List<String> dezenasSorteadasOrdemSorteio) { this.dezenasSorteadasOrdemSorteio = dezenasSorteadasOrdemSorteio; }
    public Boolean getExibirDetalhamentoPorCidade() { return exibirDetalhamentoPorCidade; }
    public void setExibirDetalhamentoPorCidade(Boolean exibirDetalhamentoPorCidade) { this.exibirDetalhamentoPorCidade = exibirDetalhamentoPorCidade; }
    public Integer getIndicadorConcursoEspecial() { return indicadorConcursoEspecial; }
    public void setIndicadorConcursoEspecial(Integer indicadorConcursoEspecial) { this.indicadorConcursoEspecial = indicadorConcursoEspecial; }
    public List<String> getListaDezenas() { return listaDezenas; }
    public void setListaDezenas(List<String> listaDezenas) { this.listaDezenas = listaDezenas; }
    public List<String> getListaDezenasSegundoSorteio() { return listaDezenasSegundoSorteio; }
    public void setListaDezenasSegundoSorteio(List<String> listaDezenasSegundoSorteio) { this.listaDezenasSegundoSorteio = listaDezenasSegundoSorteio; }
    public List<String> getListaMunicipioUFGanhadores() { return listaMunicipioUFGanhadores; }
    public void setListaMunicipioUFGanhadores(List<String> listaMunicipioUFGanhadores) { this.listaMunicipioUFGanhadores = listaMunicipioUFGanhadores; }
    public List<RateioPremio> getListaRateioPremio() { return listaRateioPremio; }
    public void setListaRateioPremio(List<RateioPremio> listaRateioPremio) { this.listaRateioPremio = listaRateioPremio; }
    public String getListaResultadoEquipeEsportiva() { return listaResultadoEquipeEsportiva; }
    public void setListaResultadoEquipeEsportiva(String listaResultadoEquipeEsportiva) { this.listaResultadoEquipeEsportiva = listaResultadoEquipeEsportiva; }
    public String getLocalSorteio() { return localSorteio; }
    public void setLocalSorteio(String localSorteio) { this.localSorteio = localSorteio; }
    public String getNomeMunicipioUFSorteio() { return nomeMunicipioUFSorteio; }
    public void setNomeMunicipioUFSorteio(String nomeMunicipioUFSorteio) { this.nomeMunicipioUFSorteio = nomeMunicipioUFSorteio; }
    public String getNomeTimeCoracaoMesSorte() { return nomeTimeCoracaoMesSorte; }
    public void setNomeTimeCoracaoMesSorte(String nomeTimeCoracaoMesSorte) { this.nomeTimeCoracaoMesSorte = nomeTimeCoracaoMesSorte; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getNumeroConcursoAnterior() { return numeroConcursoAnterior; }
    public void setNumeroConcursoAnterior(Integer numeroConcursoAnterior) { this.numeroConcursoAnterior = numeroConcursoAnterior; }
    public Integer getNumeroConcursoFinal_0_5() { return numeroConcursoFinal_0_5; }
    public void setNumeroConcursoFinal_0_5(Integer numeroConcursoFinal_0_5) { this.numeroConcursoFinal_0_5 = numeroConcursoFinal_0_5; }
    public Integer getNumeroConcursoProximo() { return numeroConcursoProximo; }
    public void setNumeroConcursoProximo(Integer numeroConcursoProximo) { this.numeroConcursoProximo = numeroConcursoProximo; }
    public Integer getNumeroJogo() { return numeroJogo; }
    public void setNumeroJogo(Integer numeroJogo) { this.numeroJogo = numeroJogo; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public String getPremiacaoContingencia() { return premiacaoContingencia; }
    public void setPremiacaoContingencia(String premiacaoContingencia) { this.premiacaoContingencia = premiacaoContingencia; }
    public String getTipoJogo() { return tipoJogo; }
    public void setTipoJogo(String tipoJogo) { this.tipoJogo = tipoJogo; }
    public Integer getTipoPublicacao() { return tipoPublicacao; }
    public void setTipoPublicacao(Integer tipoPublicacao) { this.tipoPublicacao = tipoPublicacao; }
    public Boolean getUltimoConcurso() { return ultimoConcurso; }
    public void setUltimoConcurso(Boolean ultimoConcurso) { this.ultimoConcurso = ultimoConcurso; }
    public Long getValorArrecadado() { return valorArrecadado; }
    public void setValorArrecadado(Long valorArrecadado) { this.valorArrecadado = valorArrecadado; }
    public Double getValorAcumuladoConcurso_0_5() { return valorAcumuladoConcurso_0_5; }
    public void setValorAcumuladoConcurso_0_5(Double valorAcumuladoConcurso_0_5) { this.valorAcumuladoConcurso_0_5 = valorAcumuladoConcurso_0_5; }
    public Double getValorAcumuladoConcursoEspecial() { return valorAcumuladoConcursoEspecial; }
    public void setValorAcumuladoConcursoEspecial(Double valorAcumuladoConcursoEspecial) { this.valorAcumuladoConcursoEspecial = valorAcumuladoConcursoEspecial; }
    public Double getValorAcumuladoProximoConcurso() { return valorAcumuladoProximoConcurso; }
    public void setValorAcumuladoProximoConcurso(Double valorAcumuladoProximoConcurso) { this.valorAcumuladoProximoConcurso = valorAcumuladoProximoConcurso; }
    public Double getValorEstimadoProximoConcurso() { return valorEstimadoProximoConcurso; }
    public void setValorEstimadoProximoConcurso(Double valorEstimadoProximoConcurso) { this.valorEstimadoProximoConcurso = valorEstimadoProximoConcurso; }
    public Double getValorSaldoReservaGarantidora() { return valorSaldoReservaGarantidora; }
    public void setValorSaldoReservaGarantidora(Double valorSaldoReservaGarantidora) { this.valorSaldoReservaGarantidora = valorSaldoReservaGarantidora; }
    public Double getValorTotalPremioFaixaUm() { return valorTotalPremioFaixaUm; }
    public void setValorTotalPremioFaixaUm(Double valorTotalPremioFaixaUm) { this.valorTotalPremioFaixaUm = valorTotalPremioFaixaUm; }
}

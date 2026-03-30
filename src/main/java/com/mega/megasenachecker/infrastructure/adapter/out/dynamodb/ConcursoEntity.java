package com.mega.megasenachecker.infrastructure.adapter.out.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class ConcursoEntity {

    private Integer numero;
    private List<String> dezenasSorteadas;
    private String dataSorteio;
    private Integer quantidadeAcertos;
    private List<String> numerosAcertados;

    @DynamoDbPartitionKey
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public List<String> getDezenasSorteadas() { return dezenasSorteadas; }
    public void setDezenasSorteadas(List<String> dezenasSorteadas) { this.dezenasSorteadas = dezenasSorteadas; }

    public String getDataSorteio() { return dataSorteio; }
    public void setDataSorteio(String dataSorteio) { this.dataSorteio = dataSorteio; }

    public Integer getQuantidadeAcertos() { return quantidadeAcertos; }
    public void setQuantidadeAcertos(Integer quantidadeAcertos) { this.quantidadeAcertos = quantidadeAcertos; }

    public List<String> getNumerosAcertados() { return numerosAcertados; }
    public void setNumerosAcertados(List<String> numerosAcertados) { this.numerosAcertados = numerosAcertados; }
}

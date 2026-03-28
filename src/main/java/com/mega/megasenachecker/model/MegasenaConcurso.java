package com.mega.megasenachecker.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class MegasenaConcurso {

    private Integer numero;
    private List<String> dezenasSorteadas;
    private String dataSorteio;

    @DynamoDbPartitionKey
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<String> getDezenasSorteadas() {
        return dezenasSorteadas;
    }

    public void setDezenasSorteadas(List<String> dezenasSorteadas) {
        this.dezenasSorteadas = dezenasSorteadas;
    }

    public String getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(String dataSorteio) {
        this.dataSorteio = dataSorteio;
    }
}


package com.mega.megasenachecker.service;

import com.mega.megasenachecker.model.MegasenaDocument;
import com.mega.megasenachecker.model.MegasenaConcurso;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class MegasenaService {

    private final RestTemplate restTemplate;
    @Autowired
    private EmailService emailService;

    public MegasenaService() {
        this.restTemplate = new RestTemplate();
    }

    public MegasenaDocument obterResultadoSorteio() {
        String url = "https://loteriascaixa-api.herokuapp.com/api/megasena/latest";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MegasenaDocument> response = restTemplate.exchange(url, HttpMethod.GET, entity, MegasenaDocument.class);
        return response.getBody();
    }

    public String verificarSorteio(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        StringBuilder resultMessage = new StringBuilder();
        try {
            var document = obterResultadoSorteio();
            List<String> meuJogo = Arrays.asList("04", "11", "15", "29", "38", "41");

            if (document != null && document.getConcurso() != null) {
                DynamoDbTable<MegasenaConcurso> table =
                        dynamoDbEnhancedClient.table("megasena", TableSchema.fromBean(MegasenaConcurso.class));

                MegasenaConcurso concursoExistente = table.getItem(Key.builder()
                        .partitionValue(document.getConcurso())
                        .build());



                if (concursoExistente == null) {
                    try{
                    MegasenaConcurso novoConcurso = new MegasenaConcurso();
                    novoConcurso.setNumero(document.getConcurso());
                    
                    List<String> dezenas = document.getDezenas() != null ? document.getDezenas() : Arrays.asList();
                    
                    novoConcurso.setDezenasSorteadas(dezenas);
                    novoConcurso.setDataSorteio(document.getData());

                    table.putItem(novoConcurso);
                    resultMessage.append("Resultado obtido e salvo para o concurso: ").append(document.getConcurso()).append("\n");
                    resultMessage.append(dezenas).append("\n");

                    List<String> acertos = meuJogo.stream()
                        .filter(dezenas::contains)
                        .collect(Collectors.toList());

                    resultMessage.append("--------------------------------------------------\n");
                    resultMessage.append(meuJogo).append("\n");
                    resultMessage.append("Quantidade de acertos: ").append(acertos.size()).append("\n");

                    if (!acertos.isEmpty()) {
                        resultMessage.append("Números acertados: ").append(acertos).append("\n");
                    }

                    if (acertos.size() == 4) {
                        resultMessage.append("🔥 4 acertos → \"Agora ficou interessante...\"\n");
                    } else if (acertos.size() == 5) {
                        resultMessage.append("💰 5 acertos → \"PRESTA ATENÇÃO.\"\n");
                    } else if (acertos.size() == 6) {
                        resultMessage.append("🏆 6 acertos → \"Você acabou de mudar de vida.\"\n");
                    }
                    resultMessage.append("--------------------------------------------------\n");


                    emailService.enviar("lsouzabr@gmail.com", "Resultado do Concurso " + document.getConcurso() + " - Acertos: " + acertos.size(), resultMessage.toString());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    resultMessage.append("O resultado para este concurso já está salvo: ").append(document.getConcurso()).append("\n");
                }


            } else {
                resultMessage.append("Nenhum resultado retornado.\n");
            }
        } catch (Exception e) {
            resultMessage.append("Erro ao buscar ou salvar resultado: ").append(e.getMessage());
            System.err.println("Erro ao buscar ou salvar resultado: " + e.getMessage());
        }

        System.out.println(resultMessage.toString());
        return resultMessage.toString();
    }
}

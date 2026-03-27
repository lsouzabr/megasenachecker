package com.mega.megasenachecker.service;

import com.mega.megasenachecker.model.MegasenaDocument;
import com.mega.megasenachecker.repository.MegasenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
        String url = "https://servicebus2.caixa.gov.br/portaldeloterias/api/megasena";
        return restTemplate.getForObject(url, MegasenaDocument.class);
    }

    public String verificarSorteio(MegasenaRepository repository) {
        StringBuilder resultMessage = new StringBuilder();
        try {
            var document = obterResultadoSorteio();
            List<String> meuJogo = Arrays.asList("04", "11", "15", "29", "38", "41");

            if (document != null && document.getNumero() != null) {
                if (!repository.existsByNumero(document.getNumero())) {
                    repository.save(document);
                    resultMessage.append("Resultado obtido e salvo para o concurso: ").append(document.getNumero()).append("\n");
                    resultMessage.append("Dezenas sorteadas: ").append(document.getListaDezenas()).append("\n");

                    List<String> dezenasSorteadas = document.getListaDezenas();

                    List<String> acertos = meuJogo.stream()
                            .filter(dezenasSorteadas::contains)
                            .collect(Collectors.toList());

                    resultMessage.append("--------------------------------------------------\n");
                    resultMessage.append("Meu Jogo: ").append(meuJogo).append("\n");
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

                    try{
                        emailService.enviar("lsouzabr@gmail.com", "Resultado do Concurso " + document.getNumero()+" - Acertos: "+acertos.size(), resultMessage.toString());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    resultMessage.append("O resultado para este concurso já está salvo: ").append(document.getNumero()).append("\n");
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

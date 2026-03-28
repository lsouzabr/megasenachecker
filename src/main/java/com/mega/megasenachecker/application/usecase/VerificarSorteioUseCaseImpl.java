package com.mega.megasenachecker.application.usecase;

import com.mega.megasenachecker.domain.model.Concurso;
import com.mega.megasenachecker.domain.port.in.VerificarSorteioUseCase;
import com.mega.megasenachecker.domain.port.out.ConcursoRepository;
import com.mega.megasenachecker.domain.port.out.LoteriasGateway;
import com.mega.megasenachecker.domain.port.out.NotificacaoPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VerificarSorteioUseCaseImpl implements VerificarSorteioUseCase {

    private final ConcursoRepository concursoRepository;
    private final LoteriasGateway loteriasGateway;
    private final NotificacaoPort notificacaoPort;

    @Value("${app.meuJogo:04,11,15,29,38,41}")
    private String meuJogoConfig;

    public VerificarSorteioUseCaseImpl(
            ConcursoRepository concursoRepository,
            LoteriasGateway loteriasGateway,
            NotificacaoPort notificacaoPort) {
        this.concursoRepository = concursoRepository;
        this.loteriasGateway = loteriasGateway;
        this.notificacaoPort = notificacaoPort;
    }

    @Override
    public String verificar() {
        StringBuilder resultado = new StringBuilder();

        try {
            Concurso concurso = loteriasGateway.buscarUltimoSorteio();

            if (concurso == null || concurso.getNumero() == null) {
                resultado.append("Nenhum resultado retornado.\n");
                return resultado.toString();
            }

            if (concursoRepository.existe(concurso.getNumero())) {
                resultado.append("O resultado para este concurso já está salvo: ")
                         .append(concurso.getNumero()).append("\n");
                return resultado.toString();
            }

            concursoRepository.salvar(concurso);

            List<String> dezenas = concurso.getDezenas() != null ? concurso.getDezenas() : List.of();
            List<String> meuJogo = Arrays.asList(meuJogoConfig.split(","));

            List<String> acertos = meuJogo.stream()
                    .filter(dezenas::contains)
                    .collect(Collectors.toList());

            resultado.append("Resultado obtido e salvo para o concurso: ").append(concurso.getNumero()).append("\n");
            resultado.append(dezenas).append("\n");
            resultado.append("--------------------------------------------------\n");
            resultado.append(meuJogo).append("\n");
            resultado.append("Quantidade de acertos: ").append(acertos.size()).append("\n");

            if (!acertos.isEmpty()) {
                resultado.append("Números acertados: ").append(acertos).append("\n");
            }

            if (acertos.size() == 4) {
                resultado.append("🔥 4 acertos → \"Agora ficou interessante...\"\n");
            } else if (acertos.size() == 5) {
                resultado.append("💰 5 acertos → \"PRESTA ATENÇÃO.\"\n");
            } else if (acertos.size() == 6) {
                resultado.append("🏆 6 acertos → \"Você acabou de mudar de vida.\"\n");
            }

            resultado.append("--------------------------------------------------\n");

            notificacaoPort.notificar(
                    "Resultado do Concurso " + concurso.getNumero() + " - Acertos: " + acertos.size(),
                    resultado.toString()
            );

        } catch (Exception e) {
            resultado.append("Erro ao verificar sorteio: ").append(e.getMessage());
            System.err.println("Erro ao verificar sorteio: " + e.getMessage());
        }

        System.out.println(resultado);
        return resultado.toString();
    }
}


package com.mega.megasenachecker;

import com.mega.megasenachecker.service.MegasenaService;
import com.mega.megasenachecker.repository.MegasenaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class MegasenaCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MegasenaCheckerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runProcess(MegasenaService megasenaService, MegasenaRepository repository) {
		return args -> {
			try {
				var document = megasenaService.obterResultadoSorteio();
                List<String> meuJogo = Arrays.asList("04", "11", "15", "29", "38", "41");

				if (document != null && document.getNumero() != null) {
					if (!repository.existsByNumero(document.getNumero())) {
						repository.save(document);
						System.out.println("Resultado obtido e salvo para o concurso: " + document.getNumero());
					} else {
						System.out.println("O resultado para este concurso já está salvo: " + document.getNumero());
					}
					System.out.println("Dezenas sorteadas: " + document.getListaDezenas());

					// Checagem do jogo

					List<String> dezenasSorteadas = document.getListaDezenas(); // É mais seguro conferir pela lista ordenada

					List<String> acertos = meuJogo.stream()
							.filter(dezenasSorteadas::contains)
							.collect(Collectors.toList());

					System.out.println("--------------------------------------------------");
					System.out.println("Meu Jogo: " + meuJogo);
					System.out.println("Quantidade de acertos: " + acertos.size());
					if (!acertos.isEmpty()) {
						System.out.println("Números acertados: " + acertos);
					}

					if (acertos.size() == 4) {
						System.out.println("🔥 4 acertos → \"Agora ficou interessante...\"");
					} else if (acertos.size() == 5) {
						System.out.println("💰 5 acertos → \"PRESTA ATENÇÃO.\"");
					} else if (acertos.size() == 6) {
						System.out.println("🏆 6 acertos → \"Você acabou de mudar de vida.\"");
					}
					System.out.println("--------------------------------------------------");

				} else {
					System.out.println("Nenhum resultado retornado.");
				}
			} catch (Exception e) {
				System.err.println("Erro ao buscar ou salvar resultado: " + e.getMessage());
			}
		};
	}
}

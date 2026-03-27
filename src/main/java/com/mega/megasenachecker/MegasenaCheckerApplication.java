package com.mega.megasenachecker;

import com.mega.megasenachecker.service.MegasenaService;
import com.mega.megasenachecker.repository.MegasenaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

				if (document != null && document.getNumero() != null) {
					if (!repository.existsByNumero(document.getNumero())) {
						repository.save(document);
						System.out.println("Resultado obtido e salvo para o concurso: " + document.getNumero());
					} else {
						System.out.println("O resultado para este concurso já está salvo: " + document.getNumero());
					}
					System.out.println("Dezenas: " + document.getListaDezenas());
				} else {
					System.out.println("Nenhum resultado retornado.");
				}
			} catch (Exception e) {
				System.err.println("Erro ao buscar ou salvar resultado: " + e.getMessage());
			}
		};
	}
}

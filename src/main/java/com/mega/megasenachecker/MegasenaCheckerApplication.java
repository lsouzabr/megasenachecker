package com.mega.megasenachecker;

import com.mega.megasenachecker.service.MegasenaService;
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
	public CommandLineRunner runProcess(MegasenaService megasenaService) {
		return args -> {
			try {
				var document = megasenaService.obterResultadoSorteio();
				System.out.println("Resultado obtido para o concurso: " + document.getNumero());
				System.out.println("Dezenas: " + document.getListaDezenas());
			} catch (Exception e) {
				System.err.println("Erro ao buscar resultado: " + e.getMessage());
			}
		};
	}
}

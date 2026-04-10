package com.mega.megasenachecker;

import com.mega.megasenachecker.domain.port.in.VerificarSorteioUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MegasenaCheckerApplication implements CommandLineRunner {

	private final VerificarSorteioUseCase verificarSorteioUseCase;

	public MegasenaCheckerApplication(VerificarSorteioUseCase verificarSorteioUseCase) {
		this.verificarSorteioUseCase = verificarSorteioUseCase;
	}

	public static void main(String[] args) {
		SpringApplication.run(MegasenaCheckerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		verificarSorteioUseCase.verificar();
	}
}

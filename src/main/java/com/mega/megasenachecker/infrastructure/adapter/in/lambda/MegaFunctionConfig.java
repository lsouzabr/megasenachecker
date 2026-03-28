package com.mega.megasenachecker.infrastructure.adapter.in.lambda;

import com.mega.megasenachecker.domain.port.in.VerificarSorteioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MegaFunctionConfig {

    @Bean
    public Function<String, String> processarMega(VerificarSorteioUseCase verificarSorteioUseCase) {
        return input -> {
            System.out.println("Rodando com input: " + input);
            return verificarSorteioUseCase.verificar();
        };
    }
}


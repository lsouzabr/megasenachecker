package com.mega.megasenachecker.function;
import com.mega.megasenachecker.repository.MegasenaRepository;
import com.mega.megasenachecker.service.MegasenaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MegaFunctionConfig {

    @Bean
    public Function<String, String> processarMega(MegasenaService megasenaService, MegasenaRepository repository) {
        return input -> {
            System.out.println("Rodando com input: " + input);
            return megasenaService.verificarSorteio(repository);
        };
    }
}
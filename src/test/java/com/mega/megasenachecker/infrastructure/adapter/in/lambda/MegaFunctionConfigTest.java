package com.mega.megasenachecker.infrastructure.adapter.in.lambda;

import com.mega.megasenachecker.domain.port.in.VerificarSorteioUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MegaFunctionConfigTest {

    @Mock
    private VerificarSorteioUseCase verificarSorteioUseCase;

    private final MegaFunctionConfig config = new MegaFunctionConfig();

    @Test
    void processarMega_deveChamarUseCaseVerificar() {
        when(verificarSorteioUseCase.verificar()).thenReturn("resultado");

        Function<String, String> function = config.processarMega(verificarSorteioUseCase);
        function.apply("qualquer input");

        verify(verificarSorteioUseCase, times(1)).verificar();
    }

    @Test
    void processarMega_deveRetornarResultadoDoUseCase() {
        when(verificarSorteioUseCase.verificar()).thenReturn("Concurso 2800 processado");

        Function<String, String> function = config.processarMega(verificarSorteioUseCase);
        String resultado = function.apply("trigger");

        assertThat(resultado).isEqualTo("Concurso 2800 processado");
    }

    @Test
    void processarMega_deveAceitarQualquerInputSemErro() {
        when(verificarSorteioUseCase.verificar()).thenReturn("ok");

        Function<String, String> function = config.processarMega(verificarSorteioUseCase);

        assertThat(function.apply(null)).isEqualTo("ok");
        assertThat(function.apply("")).isEqualTo("ok");
        assertThat(function.apply("evento-agendado")).isEqualTo("ok");
    }
}


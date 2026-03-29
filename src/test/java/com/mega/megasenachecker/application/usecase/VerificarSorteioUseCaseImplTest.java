package com.mega.megasenachecker.application.usecase;

import com.mega.megasenachecker.domain.model.Concurso;
import com.mega.megasenachecker.domain.port.out.ConcursoRepository;
import com.mega.megasenachecker.domain.port.out.LoteriasGateway;
import com.mega.megasenachecker.domain.port.out.NotificacaoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificarSorteioUseCaseImplTest {

    @Mock
    private ConcursoRepository concursoRepository;
    @Mock
    private LoteriasGateway loteriasGateway;
    @Mock
    private NotificacaoPort notificacaoPort;

    private VerificarSorteioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new VerificarSorteioUseCaseImpl(
                concursoRepository, loteriasGateway, notificacaoPort, "04,11,15,29,38,41");
    }

    @Test
    void deveRetornarMensagemQuandoNenhumResultadoRetornado() {
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(null);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Nenhum resultado retornado.");
        verify(concursoRepository, never()).salvar(any());
        verify(notificacaoPort, never()).notificar(anyString(), anyString());
    }

    @Test
    void deveRetornarMensagemQuandoConcursoJaFoiSalvo() {
        Concurso concurso = new Concurso(3000, List.of("04", "11", "15", "29", "38", "41"), "01/01/2025");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(3000)).thenReturn(true);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("O resultado para este concurso já está salvo: 3000");
        verify(concursoRepository, never()).salvar(any());
        verify(notificacaoPort, never()).notificar(anyString(), anyString());
    }

    @Test
    void deveSalvarConcursoEEnviarNotificacaoQuandoNovo() {
        Concurso concurso = new Concurso(3001, List.of("01", "02", "03", "04", "05", "06"), "02/01/2025");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(3001)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Resultado obtido e salvo para o concurso: 3001");
        verify(concursoRepository).salvar(concurso);
        verify(notificacaoPort).notificar(anyString(), anyString());
    }

    @Test
    void deveIdentificarAcertosCorretamente() {
        Concurso concurso = new Concurso(3002, List.of("04", "11", "15", "29", "38", "41"), "03/01/2025");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(3002)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 6");
        assertThat(resultado).contains("Você acabou de mudar de vida.");
    }

    @Test
    void deveRetornarMensagemDeErroQuandoExcecaoOcorrer() {
        when(loteriasGateway.buscarUltimoSorteio()).thenThrow(new RuntimeException("Serviço indisponível"));

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Erro ao verificar sorteio: Serviço indisponível");
        verify(concursoRepository, never()).salvar(any());
    }
}

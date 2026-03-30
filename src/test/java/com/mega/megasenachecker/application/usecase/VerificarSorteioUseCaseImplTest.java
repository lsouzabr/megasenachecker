package com.mega.megasenachecker.application.usecase;

import com.mega.megasenachecker.domain.model.Concurso;
import com.mega.megasenachecker.domain.port.out.ConcursoRepository;
import com.mega.megasenachecker.domain.port.out.LoteriasGateway;
import com.mega.megasenachecker.domain.port.out.NotificacaoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificarSorteioUseCaseImplTest {

    @Mock
    private ConcursoRepository concursoRepository;

    @Mock
    private LoteriasGateway loteriasGateway;

    @Mock
    private NotificacaoPort notificacaoPort;

    @InjectMocks
    private VerificarSorteioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(useCase, "meuJogoConfig", "04,11,15,29,38,41");
    }

    // ─── Gateway retorna nulo ──────────────────────────────────────────────────

    @Test
    void verificar_quandoGatewayRetornaNulo_deveRetornarMensagemApropriada() {
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(null);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Nenhum resultado retornado.");
        verifyNoInteractions(concursoRepository, notificacaoPort);
    }

    @Test
    void verificar_quandoConcursoComNumeroNulo_deveRetornarMensagemApropriada() {
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(new Concurso(null, List.of(), "01/01/2026"));

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Nenhum resultado retornado.");
        verifyNoInteractions(concursoRepository, notificacaoPort);
    }

    // ─── Concurso já existe ────────────────────────────────────────────────────

    @Test
    void verificar_quandoConcursoJaExiste_deveRetornarMensagemJaSalvo() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "04", "05", "06"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(true);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("O resultado para este concurso já está salvo: 2800");
        verify(concursoRepository, never()).salvar(any());
        verifyNoInteractions(notificacaoPort);
    }

    // ─── Concurso novo: salvar e notificar ────────────────────────────────────

    @Test
    void verificar_quandoConcursoNovo_deveSalvarNoBanco() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "04", "05", "06"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        useCase.verificar();

        // salvar é chamado 2 vezes: 1ª para registrar o concurso, 2ª para persistir os acertos
        verify(concursoRepository, times(2)).salvar(concurso);
    }

    @Test
    void verificar_quandoConcursoNovo_deveEnviarNotificacao() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "04", "05", "06"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        useCase.verificar();

        verify(notificacaoPort).notificar(anyString(), anyString());
    }

    @Test
    void verificar_quandoConcursoNovo_deveRetornarMensagemComNumero() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "04", "05", "06"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Resultado obtido e salvo para o concurso: 2800");
    }

    @Test
    void verificar_quandoConcursoNovo_deveSalvarAcertosNoBanco() {
        // meuJogo: 04,11,15,29,38,41 — 2 acertos: 04, 11
        Concurso concurso = new Concurso(2800, List.of("04", "11", "01", "02", "03", "05"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        useCase.verificar();

        assertThat(concurso.getQuantidadeAcertos()).isEqualTo(2);
        assertThat(concurso.getNumerosAcertados()).containsExactly("04", "11");
    }

    @Test
    void verificar_quandoSemAcertos_deveSalvarNumerosAcertadosVazio() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "05", "06", "07"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        useCase.verificar();

        assertThat(concurso.getQuantidadeAcertos()).isEqualTo(0);
        assertThat(concurso.getNumerosAcertados()).isEmpty();
    }

    @Test
    void verificar_quandoSemAcertos_naoDeveConterMensagemEspecial() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "05", "06", "07"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 0");
        assertThat(resultado).doesNotContain("🔥").doesNotContain("💰").doesNotContain("🏆");
    }

    @Test
    void verificar_quandoAcertos4_deveConterMensagem4Acertos() {
        // meuJogo: 04,11,15,29,38,41 — 4 matches: 04, 11, 15, 29
        Concurso concurso = new Concurso(2800, List.of("04", "11", "15", "29", "06", "07"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 4");
        assertThat(resultado).contains("🔥 4 acertos");
    }

    @Test
    void verificar_quandoAcertos5_deveConterMensagem5Acertos() {
        // meuJogo: 04,11,15,29,38,41 — 5 matches: 04, 11, 15, 29, 38
        Concurso concurso = new Concurso(2800, List.of("04", "11", "15", "29", "38", "07"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 5");
        assertThat(resultado).contains("💰 5 acertos");
    }

    @Test
    void verificar_quandoAcertos6_deveConterMensagem6Acertos() {
        // meuJogo: 04,11,15,29,38,41 — 6 matches: todos
        Concurso concurso = new Concurso(2800, List.of("04", "11", "15", "29", "38", "41"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 6");
        assertThat(resultado).contains("🏆 6 acertos");
    }

    @Test
    void verificar_quandoAcertosAcima0_deveListarNumerosAcertados() {
        Concurso concurso = new Concurso(2800, List.of("04", "11", "01", "02", "03", "05"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Números acertados:");
        assertThat(resultado).contains("04").contains("11");
    }

    // ─── Título da notificação ─────────────────────────────────────────────────

    @Test
    void verificar_deveChamarNotificacaoComTituloCorreto() {
        Concurso concurso = new Concurso(2800, List.of("04", "11", "15", "29", "38", "41"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        ArgumentCaptor<String> tituloCaptor = ArgumentCaptor.forClass(String.class);
        useCase.verificar();

        verify(notificacaoPort).notificar(tituloCaptor.capture(), anyString());
        assertThat(tituloCaptor.getValue()).contains("2800").contains("Acertos: 6");
    }

    // ─── Tratamento de erros ───────────────────────────────────────────────────

    @Test
    void verificar_quandoGatewayLancaExcecao_deveRetornarMensagemDeErro() {
        when(loteriasGateway.buscarUltimoSorteio()).thenThrow(new RuntimeException("timeout"));

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Erro ao verificar sorteio:");
        assertThat(resultado).contains("timeout");
        verifyNoInteractions(concursoRepository, notificacaoPort);
    }

    @Test
    void verificar_quandoRepositorioLancaExcecao_deveRetornarMensagemDeErro() {
        Concurso concurso = new Concurso(2800, List.of("01", "02", "03", "04", "05", "06"), "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);
        doThrow(new RuntimeException("DynamoDB indisponível")).when(concursoRepository).salvar(any());

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Erro ao verificar sorteio:");
        verifyNoInteractions(notificacaoPort);
    }

    // ─── Dezenas nulas ────────────────────────────────────────────────────────

    @Test
    void verificar_quandoConcursoComDezenasNulas_deveProcessarSemErro() {
        Concurso concurso = new Concurso(2800, null, "01/01/2026");
        when(loteriasGateway.buscarUltimoSorteio()).thenReturn(concurso);
        when(concursoRepository.existe(2800)).thenReturn(false);

        String resultado = useCase.verificar();

        assertThat(resultado).contains("Quantidade de acertos: 0");
    }
}


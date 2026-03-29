package com.mega.megasenachecker.infrastructure.adapter.out.dynamodb;

import com.mega.megasenachecker.domain.model.Concurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcursoRepositoryAdapterTest {

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Mock
    private DynamoDbTable<ConcursoEntity> table;

    private ConcursoRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        when(dynamoDbEnhancedClient.table(eq("megasena"), any(TableSchema.class))).thenReturn(table);
        adapter = new ConcursoRepositoryAdapter(dynamoDbEnhancedClient, "megasena");
    }

    @Test
    void existe_quandoRegistroNaoEncontrado_deveRetornarFalse() {
        when(table.getItem(any(Key.class))).thenReturn(null);

        boolean resultado = adapter.existe(2800);

        assertThat(resultado).isFalse();
    }

    @Test
    void existe_quandoRegistroEncontrado_deveRetornarTrue() {
        ConcursoEntity entity = new ConcursoEntity();
        entity.setNumero(2800);
        when(table.getItem(any(Key.class))).thenReturn(entity);

        boolean resultado = adapter.existe(2800);

        assertThat(resultado).isTrue();
    }

    @Test
    void salvar_deveChamarPutItemComEntidadeMapeadaCorretamente() {
        Concurso concurso = new Concurso(2800, List.of("04", "11", "15", "29", "38", "41"), "01/01/2026");
        ArgumentCaptor<ConcursoEntity> captor = ArgumentCaptor.forClass(ConcursoEntity.class);

        adapter.salvar(concurso);

        verify(table).putItem(captor.capture());
        ConcursoEntity entity = captor.getValue();
        assertThat(entity.getNumero()).isEqualTo(2800);
        assertThat(entity.getDezenasSorteadas()).containsExactly("04", "11", "15", "29", "38", "41");
        assertThat(entity.getDataSorteio()).isEqualTo("01/01/2026");
    }

    @Test
    void salvar_deveUsarNomeDaTabelaConfigurado() {
        verify(dynamoDbEnhancedClient).table(eq("megasena"), any(TableSchema.class));
    }
}


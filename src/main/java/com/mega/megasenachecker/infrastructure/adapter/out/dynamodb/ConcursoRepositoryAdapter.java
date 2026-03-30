package com.mega.megasenachecker.infrastructure.adapter.out.dynamodb;

import com.mega.megasenachecker.domain.model.Concurso;
import com.mega.megasenachecker.domain.port.out.ConcursoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
public class ConcursoRepositoryAdapter implements ConcursoRepository {

    private final DynamoDbTable<ConcursoEntity> table;

    public ConcursoRepositoryAdapter(
            DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Value("${DYNAMODB_TABLE_NAME}") String tableName) {
        this.table = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(ConcursoEntity.class));
    }

    @Override
    public boolean existe(Integer numero) {
        return table.getItem(Key.builder().partitionValue(numero).build()) != null;
    }

    @Override
    public void salvar(Concurso concurso) {
        ConcursoEntity entity = new ConcursoEntity();
        entity.setNumero(concurso.getNumero());
        entity.setDezenasSorteadas(concurso.getDezenas());
        entity.setDataSorteio(concurso.getData());
        table.putItem(entity);
    }
}


package com.mega.megasenachecker.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "megasena_db";
    }

    @Override
    public MongoClient mongoClient() {
        // Hardcode the URI to force authentication, since properties might be ignored in this snapshot.
        return MongoClients.create("mongodb://admin:admin123@localhost:27017/megasena_db?authSource=admin");
    }
}

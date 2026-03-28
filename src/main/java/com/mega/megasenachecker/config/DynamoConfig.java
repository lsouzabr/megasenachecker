package com.mega.megasenachecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoConfig {

//local com aws cli
//    @Bean
//    public DynamoDbClient dynamoDbClient() {
//        return DynamoDbClient.builder()
//                .region(Region.US_EAST_1)
//                .credentialsProvider(ProfileCredentialsProvider.create("default"))
//                .build();
//    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient client) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();
    }
}
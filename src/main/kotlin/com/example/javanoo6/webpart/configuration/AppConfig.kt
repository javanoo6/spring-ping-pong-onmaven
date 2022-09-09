package com.example.javanoo6.webpart.configuration

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
@EnableMongoAuditing
 class AppConfig {
    @Bean
     fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb+srv://m001-student:m001-mongodb-basics@sandbox.ozkjr.mongodb.net/ping_pong_spring?retryWrites=true&w=majority")
    }

    @Bean
     fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), "ping_pong_spring")
    }


}
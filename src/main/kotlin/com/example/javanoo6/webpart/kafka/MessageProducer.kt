package com.example.javanoo6.webpart.kafka

import com.example.javanoo6.webpart.model.GameRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessageProducer(private val kafkaTemplate: KafkaTemplate<String, GameRecord>) {

    fun someMethod(gameRecord: GameRecord) {
        kafkaTemplate.send("test_topic", gameRecord)
    }

}

package com.example.javanoo6.webpart.kafka

import com.example.javanoo6.webpart.model.GameRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class MessageConsumer {

    @KafkaListener(topics = ["test_topic"])
    fun consume(message: GameRecord): Unit {

        println(" message received from topic : $message")
    }
}

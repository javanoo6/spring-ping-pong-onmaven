package com.example.javanoo6.webpart.kafka

import com.example.schemes.GameRecordAvro
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessageProducer(
    private val kafkaTemplate2: KafkaTemplate<String, GameRecordAvro>

) {
    fun someMethod(gameRecord: GameRecordAvro) {
        kafkaTemplate2.send("test_topic", gameRecord)
    }


}

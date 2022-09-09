package com.example.javanoo6.webpart.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("gamerecords")
data class GameRecord(
    @Id
    val id: ObjectId? = null,
    val firstParticipant: Player?,
    val secondParticipant: Player?,
    val theWinner: Player,
    @CreatedDate
    val createdDate: LocalDateTime? = null
)
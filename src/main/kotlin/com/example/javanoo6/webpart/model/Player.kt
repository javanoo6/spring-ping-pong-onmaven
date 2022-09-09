package com.example.javanoo6.webpart.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("players")
data class Player(
    val name: String,
    val score: Int?
)

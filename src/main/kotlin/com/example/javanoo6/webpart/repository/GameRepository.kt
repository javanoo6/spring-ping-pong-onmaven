package com.example.javanoo6.webpart.repository

import com.example.javanoo6.webpart.model.GameRecord
import com.example.javanoo6.webpart.model.Player
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : MongoRepository<GameRecord, String> {
    fun findAllByFirstParticipant(firstParticipant: Player): List<GameRecord>
}

package com.example.javanoo6.webpart.repository

import com.example.javanoo6.webpart.model.Player
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : MongoRepository<Player, ObjectId> {
    fun findPlayerByName(name: String?): List<Player>
}
package com.example.javanoo6.webpart.service

import com.example.javanoo6.core.impl.PlayerImpl
import com.example.javanoo6.webpart.model.GameRecord
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.repository.GameRepository
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.aggregation.Fields
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameRecordService(
    var gameRepository: GameRepository,
    var template: MongoTemplate
) {

    fun findGameWinnerAndDateByName(name: String): Document {

        val projectStage = Aggregation.project(Fields.fields("theWinner")).andExclude("_id").andInclude("createdDate")
        val matchStage = Aggregation.match(Criteria("theWinner.name").isEqualTo(name))
        val aggregation = Aggregation.newAggregation(GameRecord::class.java, matchStage, projectStage)//
        val output: AggregationResults<GameRecord> =
            template.aggregate(aggregation, "gamerecords", GameRecord::class.java)
        return output.rawResults
    }

    fun findGameWinnerById(id: ObjectId): Document {
        val project = Aggregation.project(Fields.fields("theWinner")).andExclude("_id").andInclude("createdDate")
        val match = Aggregation.match(Criteria("_id").isEqualTo(id))
        val aggregation = Aggregation.newAggregation(GameRecord::class.java, match, project)//
        val output: AggregationResults<GameRecord> =
            template.aggregate(aggregation, "gamerecords", GameRecord::class.java)
        return output.rawResults
    }

    fun saveGame(playerOne: PlayerImpl, playerTwo: PlayerImpl, theWinner: PlayerImpl): GameRecord {
        return gameRepository.save(
            GameRecord(
                firstParticipant = Player(name = playerOne.name, score = playerOne.score),
                secondParticipant = Player(name = playerTwo.name, score = playerTwo.score),
                theWinner = Player(name = theWinner.name, score = theWinner.score)
            )
        )
    }

    fun findFullGameById(id: String): Optional<GameRecord>? {
        return gameRepository.findById(id)

    }

}
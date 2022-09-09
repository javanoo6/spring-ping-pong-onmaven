package com.example.javanoo6.core.impl

import com.example.javanoo6.core.Game
import com.example.javanoo6.webpart.kafka.MessageProducer
import com.example.javanoo6.webpart.service.GameRecordService
import org.springframework.stereotype.Component

@Component
class GameImpl(
    var gameRecordService: GameRecordService,
    var messageProducer: MessageProducer
) : Game {

    lateinit var theWinner: PlayerImpl
    var isGameOver = false

    override fun run(playerOne: PlayerImpl, playerTwo: PlayerImpl, finalScore: Int) {
        println("ИГРА ПИНГ-ПОНГ")
        isGameOver = false
        println(finalScore)
        while (playerOne.score < finalScore || playerTwo.score < finalScore) {
            playerMove(playerOne, playerTwo, finalScore)
            if (isGameOver) break
            playerMove(playerTwo, playerOne, finalScore)
            if (isGameOver) break
        }
        getWinner(playerOne, playerTwo)

    }

    private fun playerMove(
        playerFirst: PlayerImpl, playerSecond: PlayerImpl, MAXIMUM_GAME_POINT: Int

    ) {

        val hit = playerFirst.hit()
        println("\nсейчас ходит ${playerFirst.name} и попадает по $hit")
        if (playerSecond.playerTablePoints.contains(hit)) {
            println("${playerFirst.name} попадает по столу соперника -> игра продолжается")
        } else {
            println("${playerFirst.name} промахнулся, ${playerSecond.name} получает + 1 балл")
            playerSecond.score++
            getOverAllGameInfo(playerFirst, playerSecond, MAXIMUM_GAME_POINT)
        }
    }

    private fun getOverAllGameInfo(
        playerFirst: PlayerImpl, playerSecond: PlayerImpl, MAXIMUM_GAME_POINT: Int
    ) {
        println(
            "предварительный счет:" + "\nсчет участника: ${playerFirst.name} - ${playerFirst.score}" + "\nсчет участника: ${playerSecond.name} - ${playerSecond.score}"
        )
        if (playerFirst.score == MAXIMUM_GAME_POINT || playerSecond.score == MAXIMUM_GAME_POINT) {
            isGameOver = true

        }
    }


    fun getWinner(playerOne: PlayerImpl, playerTwo: PlayerImpl) {
        if (playerOne.score > playerTwo.score) {
            println("\n${playerOne.name} победил")
            theWinner = playerOne

        } else {
            println("\n${playerTwo.name} победил")
            theWinner = playerTwo

        }
        val gameRecord = gameRecordService.saveGame(playerOne, playerTwo, theWinner)
        messageProducer.someMethod(gameRecord)

    }


}


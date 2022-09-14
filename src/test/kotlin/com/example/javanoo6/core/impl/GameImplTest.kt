package com.example.javanoo6.core.impl

import com.example.javanoo6.webpart.kafka.MessageProducer
import com.example.javanoo6.webpart.model.GameRecord
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.service.GameRecordService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class GameImplTest {

    val gameRecordService = mockk<GameRecordService>()
    val messageProducer = mockk<MessageProducer>()
    val gameImpl = GameImpl(gameRecordService, messageProducer)

    private val pingPongTable: PingPongTableImpl = PingPongTableImpl()
    private val playerOne = PlayerImpl(
        pingPongTable.playerOneTablePoints,
        pingPongTable.playerOneTablePointsForShouting,
        "GAMER-1", 0
    )
    private val playerTwo = PlayerImpl(
        pingPongTable.playerTwoTablePoints,
        pingPongTable.playerTwoTablePointsForShouting,
        "GAMER-2", 0
    )
    var theWinner = playerTwo


    val gameRecord = GameRecord(
        id = null,
        firstParticipant = Player(playerOne.name, playerOne.score),
        secondParticipant = Player(playerTwo.name, playerTwo.score),
        theWinner = Player(theWinner.name, theWinner.score),
        createdDate = null
    )

    /**
     * Тесты проходят только в том случае, когда GAMER-2 побеждает
     */

    @Test
    fun run() {
        every {
            gameRecordService.saveGame(playerOne, playerTwo, theWinner)
        } returns gameRecord
      /*  every {
            messageProducer.someMethod(gameRecord)
        } returns Unit*/
        gameImpl.run(playerOne, playerTwo, finalScore = 1)
        verify { gameRecordService.saveGame(playerOne, playerTwo, theWinner) }
    }
}
package com.example.javanoo6.webpart.service

import com.example.javanoo6.core.impl.PingPongTableImpl
import com.example.javanoo6.core.impl.PlayerImpl
import com.example.javanoo6.webpart.model.GameRecord
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.*

internal class GameRecordServiceTest {

    val name = "someName"

    private val pingPongTable: PingPongTableImpl = PingPongTableImpl()
    private val playerOne = PlayerImpl(
        pingPongTable.playerOneTablePoints,
        pingPongTable.playerOneTablePointsForShouting,
        "игрокНомерОдин", 5
    )
    private val playerTwo = PlayerImpl(
        pingPongTable.playerTwoTablePoints,
        pingPongTable.playerTwoTablePointsForShouting,
        "ИгрокНомерДва", 6
    )


    var theWinner = playerTwo

    private val gameRepository: GameRepository = mockk<GameRepository>()

    var template: MongoTemplate = mockk<MongoTemplate>()

    val gameRecordService = GameRecordService(gameRepository, template)

    val gameRecord = GameRecord(
        firstParticipant = Player(playerOne.name, playerOne.score),
        secondParticipant = Player(playerTwo.name, playerTwo.score),
        theWinner = Player(theWinner.name, theWinner.score)
    )

    @Test
    fun `shout find full game by Id`() {
        every {
            gameRepository.findById("1")
        } returns Optional.of(gameRecord)

        val result = gameRecordService.findFullGameById("1")
        verify { gameRepository.findById("1") }
        assertEquals(Optional.of(gameRecord), result)

    }


    @Test
    fun `should save game`() {
        theWinner = playerTwo
        every {
            gameRepository.save(
                gameRecord
            )
        } returns gameRecord

        val result = gameRecordService.saveGame(playerOne, playerTwo, theWinner)
        verify {
            gameRepository.save(
                gameRecord
            )
        }
        assertEquals(gameRecord, result)
    }


}
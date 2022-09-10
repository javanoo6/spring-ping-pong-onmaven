package com.example.javanoo6.webpart.service

import com.example.javanoo6.core.impl.GameImpl
import com.example.javanoo6.core.impl.PingPongTableImpl
import com.example.javanoo6.core.impl.PlayerImpl
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.repository.PlayerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PlayerServiceTest {

    val playerRepository = mockk<PlayerRepository>()

    val player = mockk<Player>()
    val objectId = mockk<ObjectId>()

    val gameImpl = mockk<GameImpl>()
    val playerService = PlayerService(playerRepository, gameImpl)

    val pingPongTable: PingPongTableImpl = PingPongTableImpl()
    val playerOne: PlayerImpl = PlayerImpl(
        pingPongTable.playerOneTablePoints, pingPongTable.playerOneTablePointsForShouting, "игрокНомерОдин", 0
    )
    val playerTwo: PlayerImpl = PlayerImpl(
        pingPongTable.playerTwoTablePoints, pingPongTable.playerTwoTablePointsForShouting, "ИгрокНомерДва", 0
    )
    val name = "PlayeOne"

    @Test
    fun `should find player by ObjectId`() {
        every { playerRepository.findById(objectId) } returns Optional.of(player)
        val result = playerService.findById(objectId)
        verify { playerRepository.findById(objectId) }
        assertEquals(player, result)
    }

    @Test
    fun startGame() {
        every { gameImpl.run(playerOne, playerTwo, 15) } returns Unit
        playerService.playerOne = playerOne
        playerService.playerTwo = playerTwo
        playerService.startGame(15)
        verify { gameImpl.run(playerOne, playerTwo, 15) }
    }

    @Test
    fun `should find player by name`() {
        every {
            playerRepository.findPlayerByName(name)
        } returns listOf(player)
        val result = playerService.findPlayerByName(name)
        verify { playerRepository.findPlayerByName(name) }
        assertEquals(listOf(player), result)
    }


}

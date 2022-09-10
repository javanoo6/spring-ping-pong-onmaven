package com.example.javanoo6.webpart.controller

import com.example.javanoo6.webpart.model.GameRecord
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.service.GameRecordService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.bson.Document
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(controllers = [GameController::class])
internal class GameControllerTest {

    val baseUrl = "/game"
    val objectId: ObjectId = ObjectId.get()
    val objectIdString = ObjectId.get().toString()
    val name = "playerName"

    val gameRecord = GameRecord(
        objectId, Player("playerOne", 7),
        Player("playerTwo", 10), Player("playerTwo", 10), LocalDateTime.now()
    )


    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var gameService: GameRecordService

    @Test
    fun `should find full game by Id`() {
        every {
            gameService.findFullGameById(objectIdString)
        } returns Optional.of(gameRecord)
        mockMvc.get("$baseUrl/id/$objectIdString") {
        }.andExpect {
            status { HttpStatus.OK }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("firstParticipant.name") { value("playerOne") }
        }
    }


    @Test
    fun `should find game winner by playerName`() {
        every {
            gameService.findGameWinnerAndDateByName(name)
        } returns Document()
        mockMvc.get("$baseUrl/name?name=$name") {
        }
            .andExpect {
                status { HttpStatus.OK }
            }.andExpect {
                MockMvcResultMatchers.content().string("{}")
            }
    }


    @Test
    fun `should find game winner by Id`() {
        every {
            gameService.findGameWinnerById(objectId)
        } returns Document()
        mockMvc.get("$baseUrl/findById?Id=$objectId") {
        }.andExpect { status { HttpStatus.OK } }.andExpect {
            MockMvcResultMatchers.content().string("{}")
        }
    }

}
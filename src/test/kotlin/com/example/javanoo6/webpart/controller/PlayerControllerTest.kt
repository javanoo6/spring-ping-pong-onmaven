package com.example.javanoo6.webpart.controller

import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.request.PlayerRequest
import com.example.javanoo6.webpart.service.PlayerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.throwable.haveMessage
import io.mockk.every
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [PlayerController::class])
internal class PlayerControllerTest {

    val baseUrl = "/players"
    val name = "PlayerName"
    val finalScore = 10
    val objectId: ObjectId = ObjectId.get()
    var notCorrectPlayerRequest = PlayerRequest("", "")
    var correctPlayerRequest = PlayerRequest("nameOne", "pl2")
    var objectMapper = ObjectMapper()
    val player = Player("PlayerName", null)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var playerService: PlayerService

    @Test
    fun `should find player by id`() {
        every {
            playerService.findById(objectId)
        } returns player
        mockMvc.get("$baseUrl/id/$objectId") {
        }
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                status { HttpStatus.OK }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("name") { value("PlayerName") }

            }
    }


    @Test
    fun `should find player by name`() {
        every {
            playerService.findPlayerByName(name)
        } returns listOf(player)
        mockMvc.get("$baseUrl/name/$name") {
        }.andDo { MockMvcResultHandlers.print() }.andExpect {
            status { HttpStatus.OK }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("[0].name") { value("PlayerName") }
        }
            .andExpect {
                MockMvcResultMatchers.content().string("{}")
            }
    }

    @Test
    fun `should  setup player names`() {
        every {
            playerService.setPlayersNames(correctPlayerRequest)
        } returns String()
        mockMvc.post("$baseUrl/playerNames") {
            contentType = MediaType.APPLICATION_JSON
//            content = objectMapper.
            content = objectMapper.writeValueAsString(correctPlayerRequest)
        }.andDo { MockMvcResultHandlers.print() }.andExpect {
            status { HttpStatus.OK }

        }.andExpect {
            MockMvcResultMatchers.content().string(
                "Оба игрока ${correctPlayerRequest.playerOneName}" +
                        " ${correctPlayerRequest.playerTwoName} были добавлены в базу данных"
            )
        }
    }


    @Test
    fun `should not setup player names`() {
        every {
            playerService.setPlayersNames(notCorrectPlayerRequest)
        } returns String()
        mockMvc.post("$baseUrl/playerNames") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(notCorrectPlayerRequest)
        }.andDo { MockMvcResultHandlers.print() }
            .andExpect {
                status { HttpStatus.CONFLICT }
                content { contentType("text/plain;charset=UTF-8") }
                haveMessage("устраните вышеуказанные ошибки : ")
            }
            .andExpect {
                MockMvcResultMatchers.content().string("{}")
            }
    }

    @Test
    fun `should start game`() {
        every {
            playerService.startGame(finalScore)
        } returns String()
        mockMvc.get("$baseUrl/start?finalScore=$finalScore") {
        }.andDo { MockMvcResultHandlers.print() }.andExpect {
            status { HttpStatus.ACCEPTED }
            content { contentType("text/plain;charset=UTF-8") }
            haveMessage("Игра была успешно запущена")
        }

    }


}
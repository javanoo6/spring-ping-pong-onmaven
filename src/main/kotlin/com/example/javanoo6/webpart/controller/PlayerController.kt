package com.example.javanoo6.webpart.controller

import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.request.PlayerRequest
import com.example.javanoo6.webpart.service.PlayerService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/players")
class PlayerController {
    @Autowired
    lateinit var playerService: PlayerService

    @PostMapping("/playerNames")
    fun setPlayerNames(@RequestBody request: PlayerRequest): String {
        return playerService.setPlayersNames(request)
    }

    @GetMapping("/start")
    fun gameStart(@RequestParam finalScore: Int): String {
        return playerService.startGame(finalScore)
    }

    @GetMapping("/id/{id}")
    fun findPlayerById(@PathVariable id: ObjectId): Player {
        return playerService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun findPlayerByName(@PathVariable name: String): List<Player> {
        return playerService.findPlayerByName(name)

    }

}
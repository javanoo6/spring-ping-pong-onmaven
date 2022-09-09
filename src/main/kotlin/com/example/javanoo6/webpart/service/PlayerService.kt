package com.example.javanoo6.webpart.service

import com.example.javanoo6.core.impl.PingPongTableImpl
import com.example.javanoo6.core.impl.PlayerImpl
import com.example.javanoo6.core.impl.GameImpl
import com.example.javanoo6.webpart.exceptions.PlayerNotFoundException
import com.example.javanoo6.webpart.exceptions.PlayerSaveException
import com.example.javanoo6.webpart.model.Player
import com.example.javanoo6.webpart.repository.PlayerRepository
import com.example.javanoo6.webpart.request.PlayerRequest
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class PlayerService(
    val playerRepository: PlayerRepository,
    var gameImpl: GameImpl
) {

    val pingPongTable = PingPongTableImpl()

    var playerOne = PlayerImpl(
        pingPongTable.playerOneTablePoints,
        pingPongTable.playerOneTablePointsForShouting,
        "игрокНомерОдин", 0
    )
    var playerTwo = PlayerImpl(
        pingPongTable.playerTwoTablePoints,
        pingPongTable.playerTwoTablePointsForShouting,
        "ИгрокНомерДва", 0
    )
    var firstPlayer: String? = playerOne.name
    var secondPlayer: String? = playerTwo.name

    fun findById(id: ObjectId): Player =
        playerRepository.findById(id).orElseThrow { (PlayerNotFoundException("такого игрока с $id не найдено ")) }

    @Throws(PlayerSaveException::class)
    fun setPlayersNames(request: PlayerRequest): String {

        firstPlayer = request.playerOneName
        secondPlayer = request.playerTwoName
        val errorMessages = mutableListOf<String>()
        mutableListOf(firstPlayer, secondPlayer).forEach {
            when {
                it?.isEmpty()!! -> {
                    errorMessages.add("$it  - Имя пустое")
                }
                playerRepository.findPlayerByName(it).isNotEmpty() -> {
                    errorMessages.add("Такой игрок с $it именем уже существует и в базу данных игроков сохранен не будет")

                }
                else -> {
                    playerRepository.save(
                        Player(
                            name = it, score = null
                        )
                    )
                }
            }
        }

        if (errorMessages.isNotEmpty()) throw PlayerSaveException("устраните вышеуказанные ошибки : $errorMessages")
        else initializePlayers(request.playerOneName, request.playerTwoName)
        return ("Оба игрока ${request.playerOneName} ${request.playerTwoName} были добавлены в базу данных")


    }

    fun initializePlayers(playerOneName: String, playerTwoName: String) {
        playerOne = PlayerImpl(
            pingPongTable.playerOneTablePoints, pingPongTable.playerOneTablePointsForShouting, playerOneName

        )
        playerTwo = PlayerImpl(
            pingPongTable.playerTwoTablePoints, pingPongTable.playerTwoTablePointsForShouting, playerTwoName
        )
    }


    fun startGame(finalScore: Int = 10): String {
        checkInitializedPlayers(playerOne.name, playerTwo.name, finalScore)
        startGameButWithParametres(playerOne, playerTwo, finalScore)

        return "Игра была успешно запущена"

    }

    fun startGameButWithParametres(playerOne: PlayerImpl, playerTwo: PlayerImpl, finalScore: Int) {
        gameImpl.run(playerOne, playerTwo, finalScore)

    }

    fun checkInitializedPlayers(firstPlayer: String?, secondPlayer: String?, finalScore: Int) {
        if (firstPlayer?.isEmpty()!!) throw Exception("Имя первого игрока пустое")
        if (secondPlayer?.isEmpty()!!) throw Exception("Имя второго игрока пустое")
        if (finalScore == 0) throw Exception("Количество поинтов не может быть равно 0")
        if (finalScore < 0) throw Exception("Количество поинтов не может быть меньше 0")

    }

    fun findPlayerByName(name: String): List<Player> {
        return playerRepository.findPlayerByName(name)

    }
}
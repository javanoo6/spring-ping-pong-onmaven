package com.example.javanoo6.core

import com.example.javanoo6.core.impl.PlayerImpl

interface Game {
    fun run(playerOne: PlayerImpl, playerTwo: PlayerImpl, finalScore: Int)
}
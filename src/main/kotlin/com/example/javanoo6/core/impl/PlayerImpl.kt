package com.example.javanoo6.core.impl

import com.example.javanoo6.core.Player


class PlayerImpl(
    val playerTablePoints: Set<TablePoint>,
    private val playerTableShouting: Set<TablePoint>,
    override val name: String,
    override var score: Int = 0
) : Player {
    override fun hit(): TablePoint {
        return playerTableShouting.random()
    }
}
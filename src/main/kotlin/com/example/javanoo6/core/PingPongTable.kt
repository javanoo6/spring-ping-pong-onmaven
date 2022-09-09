package com.example.javanoo6.core

import com.example.javanoo6.core.impl.TablePoint

interface PingPongTable {
    val tablePoints: Set<TablePoint>
    val playerOneTablePoints: Set<TablePoint>
    val playerTwoTablePoints: Set<TablePoint>
    val playerOneTablePointsForShouting: Set<TablePoint>
    val playerTwoTablePointsForShouting: Set<TablePoint>
}
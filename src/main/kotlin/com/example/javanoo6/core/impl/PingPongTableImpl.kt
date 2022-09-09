package com.example.javanoo6.core.impl

import com.example.javanoo6.core.PingPongTable


class PingPongTableImpl : PingPongTable {
    override val tablePoints: Set<TablePoint>
    override val playerOneTablePoints: Set<TablePoint>
    override val playerTwoTablePoints: Set<TablePoint>
    override val playerOneTablePointsForShouting: Set<TablePoint>
    override val playerTwoTablePointsForShouting: Set<TablePoint>

    companion object {
        private const val TABLE_SIZE_IN_POINTS = 10
        private val LEFT_TOP_POINT = TablePoint(1, 1)
    }

    init {
        tablePoints = buildTablePoints(LEFT_TOP_POINT, TABLE_SIZE_IN_POINTS, TABLE_SIZE_IN_POINTS)
        playerOneTablePoints = buildTablePoints(TablePoint(3, 2), 6, 4)
        playerTwoTablePoints = buildTablePoints(TablePoint(3, 6), 6, 4)
        playerOneTablePointsForShouting = getPointsForShouting(tablePoints, playerOneTablePoints)
        playerTwoTablePointsForShouting = getPointsForShouting(tablePoints, playerTwoTablePoints)
    }

    private fun getPointsForShouting(
        tablePoints: Set<TablePoint>,
        tablePointsToExclude: Set<TablePoint>
    ): Set<TablePoint> {
        val pointsForShouting = HashSet<TablePoint>(tablePoints)
        pointsForShouting.removeAll(tablePointsToExclude)
//        println("Player points for Shouting $pointsForShouting")
        return pointsForShouting
    }

    private fun buildTablePoints(
        leftTopPoint: TablePoint,
        tableSizeInPointsByX: Int,
        tableSizeInPointsByY: Int
    ): Set<TablePoint> {
        val tablePoints: MutableSet<TablePoint> = HashSet()
        (leftTopPoint.x until leftTopPoint.x + tableSizeInPointsByX).forEach { i ->
            (leftTopPoint.y until leftTopPoint.y + tableSizeInPointsByY).mapTo(tablePoints) { TablePoint(i, it) }
        }
//        println("Created tablePoints $tablePoints")
        return tablePoints
    }

}
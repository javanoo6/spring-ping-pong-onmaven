package com.example.javanoo6.core

import com.example.javanoo6.core.impl.TablePoint

interface Player {
    val name: String
    val score: Int
    fun hit(): TablePoint
}
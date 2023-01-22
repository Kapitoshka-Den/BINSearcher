package com.example.binsearcher.data.requestModels.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class Number(
    val length: Int = 0,
    val luhn: Boolean = false
)
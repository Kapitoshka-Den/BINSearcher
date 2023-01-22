package com.example.binsearcher.data.requestModels.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val city: String = "?",
    val name: String = "?",
    val phone: String = "?",
    val url: String = "?"
)
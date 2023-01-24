package com.example.binsearcher.data.requestModels.requestModels

import kotlinx.serialization.Serializable


@Serializable
data class BinInfo(
    val bank: Bank? = Bank(),
    val brand: String = "",
    val country: Country? = Country(),
    val number: Number? = Number(),
    val prepaid: Boolean = false,
    val scheme: String = "",
    val type: String = ""
)
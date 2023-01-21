package com.example.binsearcher.data.models.requestModels

data class BinInfo(
    val bank: Bank? = null,
    val brand: String = "",
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean = false,
    val scheme: String = "",
    val type: String = ""
)
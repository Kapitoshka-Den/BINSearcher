package com.example.binsearcher.binsearcher

import com.example.binsearcher.data.models.requestModels.BinInfo

data class BinSearchViewState(
    val expanded: Boolean = false,
    var userSearch: String = "",
    var isError: Boolean = false,
    var binInfo: BinInfo? = BinInfo()
) {

}
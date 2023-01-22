package com.example.binsearcher.screens.binSearcher

import com.example.binsearcher.data.database.History.HistoryEntity
import com.example.binsearcher.data.requestModels.requestModels.BinInfo

data class BinSearchViewState(
    var expanded:Boolean = false,
    var userSearch: String = "",
    var isError: Boolean = false,
    var binInfo: BinInfo? = BinInfo(),
    var binItems:List<HistoryEntity>? = null
) {

}
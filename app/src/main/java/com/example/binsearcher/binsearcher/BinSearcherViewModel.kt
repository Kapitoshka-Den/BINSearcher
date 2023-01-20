package com.example.binsearcher.binsearcher

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BinSearcherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BinSearchViewState())
    val uiState:StateFlow<BinSearchViewState> = _uiState.asStateFlow()

    fun ChangeUserBin(newBin:String){
        _uiState.update { _uiState.value.copy(userSearch = newBin) }
    }

}
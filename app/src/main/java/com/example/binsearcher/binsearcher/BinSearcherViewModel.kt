package com.example.binsearcher.binsearcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearcher.data.BinRequestClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class BinSearcherViewModel : ViewModel() {

    private val _binRequestClass = BinRequestClass()

    private val _uiState = MutableStateFlow(BinSearchViewState())
    val uiState:StateFlow<BinSearchViewState> = _uiState.asStateFlow()

    fun ChangeUserBin(newBin:String){
        _uiState.update { _uiState.value.copy(userSearch = newBin) }
    }

    fun binSearchRequest(){
        viewModelScope.launch {

            if(_binRequestClass.BinGetRequest(_uiState.value.userSearch) == null){
                _uiState.value.isError = true
            }
            else{
                _uiState.value.binInfo = (_binRequestClass.BinGetRequest(_uiState.value.userSearch))!!.response
            }
        }
    }
}
package com.example.binsearcher.screens.binSearcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binsearcher.data.BinRequestClass
import com.example.binsearcher.data.database.History.HistoryEntity
import com.example.binsearcher.data.database.History.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinSearcherViewModel @Inject constructor(private val _historyRepository: HistoryRepository) :
    ViewModel() {

    private val _binRequestClass = BinRequestClass()

    private val _uiState = MutableStateFlow(BinSearchViewState())
    val uiState: StateFlow<BinSearchViewState> = _uiState.asStateFlow()

    fun changeUserBin(newBin: String) {
        newBin.forEach{item->
            if (!item.isDigit()){
                return
            }
        }
        _uiState.update { _uiState.value.copy(userSearch = newBin) }
    }

    fun updateExpanded() {
        _uiState.update { _uiState.value.copy(expanded = !_uiState.value.expanded) }
    }

    fun binSearchRequest() {
        if (_uiState.value.userSearch.isEmpty()){
            _uiState.value.isError = true
            return
        }
        else
            _uiState.value.isError = false
        viewModelScope.launch {
            val userSearch = _uiState.value.userSearch

            addBinToHistory(userSearch)

            if (_binRequestClass.BinGetRequest(_uiState.value.userSearch) == null) {
                _uiState.update { _uiState.value.copy(isError = true) }
                _uiState.value.isError = true
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        binInfo = _binRequestClass.BinGetRequest(
                            _uiState.value.userSearch
                        ),
                        isError = false
                    )
                }
            }
        }
    }

    private fun addBinToHistory(userSearch: String) {
        viewModelScope.launch {
            _historyRepository.addBin(HistoryEntity(Bin = userSearch))
        }
    }

    suspend fun loadHistory() {
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(binItems = _historyRepository.getHistory()) }
        }
    }

}
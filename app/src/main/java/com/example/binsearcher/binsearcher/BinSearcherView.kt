package com.example.binsearcher.binsearcher

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BinSearcherView(binViewModel: BinSearcherViewModel = viewModel()) {
    val binSearchState by binViewModel.uiState.collectAsState()
    Surface() {
        Column() {
            SearchDropDownMenu(
                expanded = binSearchState.expanded,
                userSearch = binSearchState.userSearch,
                onExpandedChange = {},
                onValueChange = { binViewModel.ChangeUserBin(it) }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchDropDownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    userSearch: String,
    onValueChange: (String) -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandedChange) {
        OutlinedTextField(
            value = userSearch,
            onValueChange = onValueChange,
            label = { Text(text = "test") }
        )
    }
}

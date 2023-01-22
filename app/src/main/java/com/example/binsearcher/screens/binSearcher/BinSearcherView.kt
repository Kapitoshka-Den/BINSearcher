package com.example.binsearcher.screens.binSearcher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.binsearcher.data.database.History.HistoryEntity

@Composable
fun BinSearcherView(binViewModel: BinSearcherViewModel= viewModel()) {
    val binSearchState by binViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchDropDownMenu(
                focusManager = focusManager,
                state = binSearchState,
                onExpandedChange = { binViewModel.updateExpanded() },
                onClick = { binViewModel.binSearchRequest() },
                onValueChange = {
                    binViewModel.changeUserBin(it)
                },
                binsList = binSearchState.binItems
            )
            Text(text = binSearchState.binInfo!!.brand)
        }
    }
    LaunchedEffect(key1 = binSearchState) {
        try {
            binViewModel.loadHistory()
        } catch (_:Exception){
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchDropDownMenu(
    state:BinSearchViewState,
    onExpandedChange: () -> Unit,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    focusManager: FocusManager,
    binsList: List<HistoryEntity>?
) {
    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = { onExpandedChange() },
        modifier = Modifier
            .padding(10.dp, 0.dp)
    ) {
        OutlinedTextField(
            isError = state.isError,

            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Blue,
                focusedBorderColor = Color.Blue,
                cursorColor = Color.Blue
            ),
            value = state.userSearch,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),

            keyboardActions = KeyboardActions(onDone = {
                onClick()
                onExpandedChange()
            }),
            trailingIcon = {
                IconButton(onClick = {
                    onClick()
                    focusManager.clearFocus()
                    onExpandedChange()
                }) {
                    Row() {
                        Text(text = "Find", color = Color.Blue)
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = if(!state.isError)Color.Blue else Color.Red)
                    }
                }
            },
            onValueChange = onValueChange,
            label = {
                Text(text = "test")
            }
        )
        ExposedDropdownMenu(expanded = state.expanded, onDismissRequest = { state.expanded = false}) {
            binsList?.reversed()?.forEach { item ->
                DropdownMenuItem(onClick = {
                    onExpandedChange()
                    onValueChange(item.Bin)
                    state.userSearch = item.Bin
                }) {
                    Text(text = item.Bin)
                }
            }
        }
    }
}

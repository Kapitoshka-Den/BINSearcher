package com.example.binsearcher.binsearcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.TextStyle

@Composable
fun BinSearcherView(binViewModel: BinSearcherViewModel = viewModel()) {

    val binSearchState by binViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchDropDownMenu(
                focusManager = focusManager,
                expanded = binSearchState.expanded,
                userSearch = binSearchState.userSearch,
                onExpandedChange = {},
                onClick = { binViewModel.binSearchRequest() },
                onValueChange = {
                    binViewModel.ChangeUserBin(it)
                }
            )
            Text(text = binSearchState.binInfo!!.scheme)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchDropDownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    userSearch: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    focusManager: FocusManager
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier
            .padding(10.dp, 0.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Blue,
                focusedBorderColor = Color.Blue,
                cursorColor = Color.Blue
            ),
            value = userSearch,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(onDone = {
                onClick()
                focusManager.clearFocus()
            }),
            trailingIcon = {
                IconButton(onClick = onClick) {
                    Row() {
                        Text(text = "Find", color = Color.Blue)
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Blue)
                    }
                }
            },
            onValueChange = onValueChange,
            label = {
                Text(text = "test")
            }
        )
    }
}

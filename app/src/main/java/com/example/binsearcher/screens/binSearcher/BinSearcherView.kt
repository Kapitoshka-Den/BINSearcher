package com.example.binsearcher.screens.binSearcher

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.binsearcher.R
import com.example.binsearcher.data.database.History.HistoryEntity
import com.example.binsearcher.data.requestModels.requestModels.BinInfo
import java.net.URI

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
                state = binSearchState,
                onExpandedChange = { binViewModel.updateExpanded() },
                onClick = { binViewModel.binSearchRequest() },
                onValueChange = {
                    binViewModel.changeUserBin(it)
                },
                binsList = binSearchState.binItems
            )
            BinInfoContainer(binSearchState.binInfo!!)
        }
    }
    LaunchedEffect(key1 = binSearchState) {
        try {
            binViewModel.loadHistory()
        } catch (_: Exception) {
        }
    }
}

@Composable
fun BinInfoContainer(binInfo: BinInfo) {
    val context = LocalContext.current
    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + binInfo.bank?.phone))
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + binInfo.bank?.url))
    val mapIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:${binInfo.country?.latitude},${binInfo.country?.longitude}")
    )
    mapIntent.setPackage("com.google.android.apps.maps")
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Scheme / network:" + binInfo.scheme)
        Text(text = "Brand: " + binInfo.brand)
        Text(
            text = stringResource(
                id = R.string.number_stats,
                binInfo.number?.length ?: "?",
                if (binInfo.number?.luhn == false) "Yes" else "No"
            )
        )
        Text(text = "Type:" + binInfo.type)
        Text(text = "Prepaid: " + if (!binInfo.prepaid) "No" else "Yes")
        Text(
            text = stringResource(
                id = R.string.country_info,
                binInfo.country?.emoji ?: "?",
                binInfo.country?.name ?: "?"
            )
        )
        Text(
            text = stringResource(
                R.string.country_coordinate,
                binInfo.country?.latitude ?: "?",
                binInfo.country?.longitude ?: "?"),
                modifier = Modifier.clickable {
                    startActivity(context,mapIntent,null)
                }
        )

        Text(
            text = stringResource(
                R.string.bank_addr,
                binInfo.bank?.name ?: "?",
                binInfo.bank?.city ?: "?"
            )
        )
        Text(
            text = "Bank phone:" + (binInfo.bank?.phone ?: "?"),
            modifier = if (binInfo.bank?.phone != null) Modifier.clickable {
                try {
                    startActivity(context, dialIntent, null)
                } catch (s: Exception) {
                    Log.e("tag_ex", s.message.toString())
                }
            } else Modifier
        )
        Text(
            text = binInfo.bank?.url ?: "?",
            modifier = if (binInfo.bank?.url != null) Modifier.clickable {
                try {
                    startActivity(context, browserIntent, null)
                } catch (s: Exception) {
                    Log.e("tag_ex", s.message.toString())
                }
            } else Modifier
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchDropDownMenu(
    state: BinSearchViewState,
    onExpandedChange: () -> Unit,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    focusManager: FocusManager,
    binsList: List<HistoryEntity>?
) {
    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = {
            onExpandedChange()
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp),
            value = state.userSearch,
            onValueChange = onValueChange,
            label = { Text("Label") },
            trailingIcon = {
                IconButton(onClick = {
                    onClick()
                    onExpandedChange()
                }, Modifier.padding(10.dp, 0.dp)) {
                    Row() {
                        Text(text = "Find")
                        Icon(Icons.Default.Search, contentDescription = "Search Button")
                    }
                }
            }
        )
        // filter options based on text field value
        val filteringOptions =
            state.binItems?.filter { it.Bin.contains(state.userSearch, ignoreCase = true) }
                ?.reversed()
        if (filteringOptions?.isNotEmpty() == true) {
            ExposedDropdownMenu(
                expanded = state.expanded,
                onDismissRequest = {
                    onExpandedChange()
                }
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            state.userSearch = selectionOption.Bin
                            onExpandedChange()
                        }
                    ) {
                        Text(text = selectionOption.Bin)
                    }
                }
            }
        }
    }
}

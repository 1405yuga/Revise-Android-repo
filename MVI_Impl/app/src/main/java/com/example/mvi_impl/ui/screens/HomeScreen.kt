package com.example.mvi_impl.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvi_impl.mvi.HomeViewModel
import com.example.mvi_impl.mvi.ScreenState
import com.example.mvi_impl.mvi.UserIntent

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    val screenState by viewModel.screenState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query, onValueChange = {
            query = it
            viewModel.handleIntent(UserIntent.SearchItems(it))
        }, label = { Text("Search...") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.handleIntent(UserIntent.AddItem("New one")) }) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (val state = screenState) {
            is ScreenState.Preload -> {
                Button(onClick = { viewModel.handleIntent(UserIntent.FetchAllItems) }) {
                    Text("Load Items")
                }
            }

            is ScreenState.Loading -> {
                CircularProgressIndicator()
            }

            is ScreenState.Loaded -> {
                LazyColumn {
                    items(state.result) { item ->
                        Row {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                viewModel.handleIntent(UserIntent.DeleteItemAt(item.id))
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "delete item")
                            }

                        }
                        HorizontalDivider()
                    }
                }
            }

            is ScreenState.Error -> {
                Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    HomeScreen()
}
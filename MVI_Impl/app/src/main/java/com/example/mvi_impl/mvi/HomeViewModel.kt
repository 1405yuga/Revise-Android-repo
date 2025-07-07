package com.example.mvi_impl.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_impl.data.Item
import com.example.mvi_impl.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val itemRepository = ItemRepository()

    private val _screenState = MutableStateFlow<ScreenState<List<Item>>>(ScreenState.Preload())
    val screenState: StateFlow<ScreenState<List<Item>>> = _screenState

    fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.FetchAllItems -> fetchItems()
            is UserIntent.SearchItems -> searchItems(intent.query)
        }
    }

    private fun searchItems(query: String) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _screenState.value = try {
                ScreenState.Loaded(result = itemRepository.searchItem(query = query))
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error(message = "Something went wrong")
            }
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _screenState.value = try {
                ScreenState.Loaded(result = itemRepository.getItems())
            } catch (e: Exception) {
                e.printStackTrace()
                ScreenState.Error(message = "Something went wrong")
            }
        }
    }
}
package com.example.stock_app.presentation.WatchList


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stock_app.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(WatchListState())

    init {
        loadWatchList()
    }

    fun onEvent(event: WatchListEvents) {
        when (event) {
            is WatchListEvents.CreateWatchList -> {
                viewModelScope.launch {
                    repository.insertWatchList(event.name)
                    state = state.copy(newWatchListName = "", isAddWatchListDiaglogShown = false)
                }

            }

            is WatchListEvents.DeleteStock -> {
                viewModelScope.launch {
                    repository.deleteWatchList(
                        watchlistId = event.watchList.watchListId,
                        symbol = event.stock.symbol
                    )
                }
            }
            WatchListEvents.DismissAddWatchlistDialog ->{
                state=state.copy(isAddWatchListDiaglogShown = false, newWatchListName = "")
            }
            is WatchListEvents.OnNewWatchlistNameChange ->{
                state=state.copy(newWatchListName = event.name)
            }
            WatchListEvents.ShowAddWatchlistDialog ->{
                state=state.copy(isAddWatchListDiaglogShown = true)
            }
            is WatchListEvents.ToggleWatchList -> {
                val currentId = state.isExpandedId
                state = state.copy(isExpandedId = if (currentId== event.watchlist) null else event.watchlist)
            }
            is WatchListEvents.WatchListsDelete -> {
                viewModelScope.launch {
                    repository.deleteWatchLists(
                        event.watchList

                    )
                    state = state.copy(watchListToDelete = null)
                }
            }
        }
    }

    private fun loadWatchList(){
        repository.getAllWatchlistsWithStocks()
            .onEach { watchlistWithStocks ->
                state=state.copy(
                    watchLists = watchlistWithStocks,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}
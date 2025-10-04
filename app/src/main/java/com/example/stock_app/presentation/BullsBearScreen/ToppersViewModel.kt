package com.example.stock_app.presentation.BullsBearScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock_app.Navigation.ARG_LIST_TYPE
import com.example.stock_app.domain.model.TopMovers
import com.example.stock_app.domain.repository.StockRepository
import com.example.stock_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToppersViewModel @Inject constructor(
    private val repository: StockRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
        var state by mutableStateOf(ToppersState())

        init {
            val listType = savedStateHandle.get<String>(ARG_LIST_TYPE)?:""
            viewModelScope.launch {
                state=state.copy(isLoading = true)
                val moversResponse = repository.getGainersLosers()
                when(val result = moversResponse){
                    is  Resource.Success ->{
                        result.data?.let {
                            state=state.copy(
                                topGainers = it.topGainers,
                                topLosers = it.topLosers,
                                mostTraded = it.mostTraded
                            )
                        }
                    }

                    is Resource.Error ->{state=state.copy(error =result.message )}
                    is Resource.Loading-> Unit
                }
                when(val result = moversResponse){
                    is Resource.Error -> {
                        state= state.copy(error = result.message)
                    }
                    is Resource.Loading -> Unit
                    is Resource.Success->{
                        val allmovers = result.data?: TopMovers(emptyList(), emptyList(),
                            emptyList()
                        )
                        val (title, stockList) = when (listType) {
                            "gainers" -> "Top Gainers" to allmovers.topGainers
                            "losers" -> "Top Losers" to allmovers.topLosers
                            "most_traded" -> "Most Traded" to allmovers.mostTraded
                            else -> "Unknown" to emptyList()
                        }
                        state=state.copy(
                            title = title,
                            stocks = stockList,
                            error = null
                        )
                    }
                }
                state=state.copy(isLoading = false)
            }
        }

}

package com.example.stock_app.presentation.company_info

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock_app.domain.repository.StockRepository
import com.example.stock_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            Log.d("CompanyInfoVM", "Received symbol: $symbol")
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }
            when (val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    Log.d("CompanyInfoVM", "Company info success: ${result.data}")
                    state = state.copy(
                        company = result.data,

                        error = null
                    )
                }

                is Resource.Error -> {
                    Log.e("CompanyInfoVM", "Company info error: ${result.message}")
                    state = state.copy(
                        error = result.message,

                        company = null
                    )
                }

                else -> Unit
            }
            when (val result = intradayInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data?: emptyList(),

                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,

                        company = null
                    )
                }

                else -> Unit
            }
            state = state.copy(isLoading = false)
        }


    }
    fun onEvent(event: CompanyInfoEvent){
        when(event){
            is CompanyInfoEvent.AddToWatchList -> {
                viewModelScope.launch {
                    val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
                    repository.addStockToWatchList(
                        watchlistId = event.watchlist.watchListId,
                        symbol = symbol
                    )
                    state=state.copy(isSheetShown = false)
                }
            }
            CompanyInfoEvent.DismissWatchListSheet -> {
                state=state.copy(isSheetShown = false)
            }
            CompanyInfoEvent.ShowWatchListSheet -> {
                viewModelScope.launch {
                    repository.getAllWatchList()
                        .firstOrNull()?.let {watchlists ->
                            state = state.copy(
                                watchlists = watchlists,
                                isSheetShown = true
                            )
                        }
                }

            }
        }
    }
}
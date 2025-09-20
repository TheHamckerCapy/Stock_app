package com.example.stock_app.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock_app.domain.repository.StockRepository
import com.example.stock_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
    private var searchQueryJob: Job? =null


    init {
        getCompanyListings()
    }
    fun onEvent(event: CompanyListingsEvents){
        when(event){
            is CompanyListingsEvents.Refresh ->{
                    getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvents.OnSearchQueryChange ->{
                state=state.copy(
                    searchQuery = event.query
                )
                searchQueryJob?.cancel()
                searchQueryJob=viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean= false
    ){
            viewModelScope.launch {
                repository
                    .getCompanyListing(fetchFromRemote,query)
                    .collect{result->
                        when(result){
                            is Resource.Success ->{
                                result.data?.let {listings->
                                    state=state.copy(
                                        companies = listings
                                    )
                                }
                            }
                            is Resource.Error -> {
                                state=state.copy(isRefreshing = false)
                            }
                            is Resource.Loading ->{
                                state=state.copy(isRefreshing = result.isLoading)
                            }
                        }

                    }
            }
    }
}
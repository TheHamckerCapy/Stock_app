package com.example.stock_app.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun CompanyListingScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val isRefreshing = viewModel.state.isRefreshing
    val swipeRefreshState = rememberPullToRefreshState()
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize(),

        ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListingsEvents.OnSearchQueryChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text(text = "Search...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.onEvent(CompanyListingsEvents.Refresh) },
            state = swipeRefreshState,

            ) {

            LazyColumn(
                modifier=Modifier.fillMaxWidth()
            ) {
                items(state.companies.size){i->
                    val company = state.companies[i]

                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(16.dp)
                    )
                    if(i<state.companies.size){
                        HorizontalDivider(modifier=Modifier.padding(16.dp))
                    }

                }

            }

        }


    }


}
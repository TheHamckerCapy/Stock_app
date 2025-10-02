package com.example.stock_app.presentation.company_listings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(
    navController: NavController,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val isRefreshing = viewModel.state.isRefreshing
    val swipeRefreshState = rememberPullToRefreshState()
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListingsEvents.OnSearchQueryChange(it))
            },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp,end = 10.dp, bottom = 10.dp)
                ,
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
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]

                    CompanyItem2(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            navController.navigate(route = "/infoScreen/${company.symbol}")

                        }
                    )
                    if (i < state.companies.size) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                }

            }

        }


    }


}
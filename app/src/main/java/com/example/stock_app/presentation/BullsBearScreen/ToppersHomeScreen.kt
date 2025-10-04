package com.example.stock_app.presentation.BullsBearScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stock_app.Navigation.STOCK_LIST_ROUTE
import com.example.stock_app.R


@Composable
fun ToppersHomeScreen(
    navController: NavController,
    viewModel: ToppersViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Scaffold(
                topBar = {
                    ToppersTopBar(onNavClick = { navController.navigate(route = "/home"){
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    } })
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (state.mostTraded.isNotEmpty()) {
                        MoversGridView(
                            title = "Most Traded",
                            movers = state.mostTraded,
                            onSeeMoreClick = { navController.navigate("$STOCK_LIST_ROUTE/most_traded") },
                            onItemClick = { navController.navigate(route = "/infoScreen/${it.ticker}") }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    if (state.topGainers.isNotEmpty()) {
                        MoversGridView(
                            title = "Top Movers",
                            movers = state.topGainers,
                            onSeeMoreClick = { navController.navigate("$STOCK_LIST_ROUTE/gainers") },
                            onItemClick = { navController.navigate(route = "/infoScreen/${it.ticker}") }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    if (state.topLosers.isNotEmpty()) {
                        MoversGridView(
                            title = "Top Losers",
                            movers = state.topLosers,
                            onSeeMoreClick = { navController.navigate("$STOCK_LIST_ROUTE/losers") },
                            onItemClick = { navController.navigate(route = "/infoScreen/${it.ticker}") }
                        )
                    }

                }
            }


        }
    }

}

@Composable
fun ToppersTopBar(onNavClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp) // exact height you want
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = "Brand",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Stocks",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
            )
        }

        Row {
            IconButton(onClick = {onNavClick()}) {
                Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Color.White)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun AppBar() {
    ToppersTopBar(onNavClick = {})
}

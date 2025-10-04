package com.example.stock_app.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stock_app.R
import com.example.stock_app.presentation.BullsBearScreen.MoversListScreen
import com.example.stock_app.presentation.BullsBearScreen.ToppersHomeScreen
import com.example.stock_app.presentation.WatchList.WatchlistScreen
import com.example.stock_app.presentation.company_info.CompanyInfoScreen
import com.example.stock_app.presentation.company_listings.CompanyListingScreen

data class NavItem(
    val route: String,
    val icon: Int
)

const val STOCK_LIST_ROUTE = "stock_list"
const val ARG_LIST_TYPE = "listType"

@Composable
fun NavHostScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(true) }

    Scaffold(

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = "/movers",
            ) {
                composable(route = "/home") {
                    bottomBarVisibility = true
                    CompanyListingScreen(navController)
                }
                composable(
                    route = "/infoScreen/{symbol}",
                    arguments = listOf(navArgument("symbol") { type = NavType.StringType })
                ) { backstackentry ->
                    bottomBarVisibility = false
                    val symbol = backstackentry.arguments?.getString("symbol")
                    if (symbol != null) {
                        CompanyInfoScreen(symbol = symbol)
                    }
                }
                composable(route = "/watchlist") {
                    bottomBarVisibility = true
                    WatchlistScreen(navController = navController)
                }
                composable(route = "/movers") {
                    bottomBarVisibility = true
                    ToppersHomeScreen(navController = navController)
                }
                composable(
                    route = "$STOCK_LIST_ROUTE/{$ARG_LIST_TYPE}",
                    arguments = listOf(navArgument(ARG_LIST_TYPE) {
                        type = NavType.StringType
                        nullable = true
                    })
                ) { backStackEntry ->
                    bottomBarVisibility = false
                    val listType = backStackEntry.arguments?.getString(ARG_LIST_TYPE) ?: ""
                    MoversListScreen(
                        listType = listType,
                        navController = navController,
                        onStockClick = { symbol ->
                            navController.navigate(route = "/infoScreen/$symbol")
                        })
                }

            }
            if (bottomBarVisibility) {
                CustomNavigationBar(
                    navController = navController,
                    items = listOf(
                        NavItem(route = "/movers", icon = R.drawable.candlestick_chart_24px),
                        NavItem(route = "/home", icon = R.drawable.search_insights_24px),
                        NavItem(route = "/watchlist", icon = R.drawable.bookmarks_24px),

                        ),
                    modifier = Modifier.align(Alignment.BottomCenter)

                )
            }
        }

    }
}
@Composable
fun CustomNavigationBar(
    navController: NavController,
    items: List<NavItem>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = modifier
                .width(230.dp)
                .height(75.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .shadow(elevation = 10.dp, shape = CircleShape),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                IconButton(
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true


                        }
                    },


                ){
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        tint = if(currentRoute==item.route) Color.Green else Color.LightGray,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }

}
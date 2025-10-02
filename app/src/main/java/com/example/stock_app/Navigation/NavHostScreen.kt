package com.example.stock_app.Navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stock_app.R
import com.example.stock_app.presentation.WatchList.WatchlistScreen
import com.example.stock_app.presentation.company_info.CompanyInfoScreen
import com.example.stock_app.presentation.company_listings.CompanyListingScreen

data class NavItem(
    val route: String,
    val icon: Int
)

@Composable
fun NavHostScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = bottomBarVisibility) {
                NavigationBottomBar(
                    navController = navController,
                    items = listOf(
                        NavItem(route = "/home", icon = R.drawable.ic_home),
                        NavItem(route = "/watchlist", icon = R.drawable.ic_home)
                    )
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "/home",
            modifier = Modifier.padding(it)
        ) {
            composable(route = "/home") {
                bottomBarVisibility = true
                CompanyListingScreen(navController)
            }
            composable(route = "/infoScreen/{symbol}",
                arguments = listOf(navArgument("symbol") { type = NavType.StringType })
                ) {backstackentry->
                bottomBarVisibility = false
                val symbol = backstackentry.arguments?.getString("symbol")
                if(symbol!=null){
                    CompanyInfoScreen(symbol = symbol)
                }
            }
            composable(route = "/watchlist") {
                bottomBarVisibility = true
                WatchlistScreen(navController = navController)
            }

        }
    }
}

@Composable
fun NavigationBottomBar(navController: NavController, items: List<NavItem>) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true


                    }
                },
                icon = { Icon(painter = painterResource(item.icon), contentDescription = null) },

                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.Green,
                    selectedIconColor = Color.Green,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )

            )
        }
    }

}
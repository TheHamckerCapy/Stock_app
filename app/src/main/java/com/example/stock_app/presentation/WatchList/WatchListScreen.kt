package com.example.stock_app.presentation.WatchList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stock_app.data.local.Company_ListingEntity
import com.example.stock_app.data.local.WatchlistWithStocks


@Composable
fun WatchlistScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            WatchlistTopBar(onBackClick = {navController.popBackStack()})
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(WatchListEvents.ShowAddWatchlistDialog) },
                shape = CircleShape,
                containerColor = Color.Green,
               modifier = Modifier
                   .size(70.dp)
                   ,

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Watchlist", modifier = Modifier.size(45.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(state.watchLists) { index, watchlistWithStocks ->


                WatchlistCard(
                    watchlistWithStocks = watchlistWithStocks,
                    isExpanded = state.isExpandedId == watchlistWithStocks.watchListId.watchListId,
                    onToggle = {
                        viewModel.onEvent(WatchListEvents.ToggleWatchList(watchlistWithStocks.watchListId.watchListId))
                    },
                    onDeleteStock = { stock ->
                        viewModel.onEvent(
                            WatchListEvents.DeleteStock(
                                stock = stock,
                                watchList = watchlistWithStocks.watchListId
                            )
                        )
                    },
                    onStockClick = {
                        navController.navigate(route = "/infoScreen/${it.symbol}")
                    },
                    onDeleteWatchList = {
                        viewModel.onEvent(
                            WatchListEvents.WatchListsDelete(
                                watchList = watchlistWithStocks.watchListId
                            )
                        )
                    }
                )
                if (index < state.watchLists.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }

        if (state.isAddWatchListDiaglogShown) {
            AddWatchlistDialog(
                name = state.newWatchListName,
                onNameChange = { viewModel.onEvent(WatchListEvents.OnNewWatchlistNameChange(it)) },
                onConfirm = { viewModel.onEvent(WatchListEvents.CreateWatchList(state.newWatchListName)) },
                onDismiss = { viewModel.onEvent(WatchListEvents.DismissAddWatchlistDialog) }
            )
        }
    }
}

@Composable
fun StockItem(stock: Company_ListingEntity, onDelete: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stock.name, fontWeight = FontWeight.SemiBold)
            Text(text = stock.symbol, fontSize = 12.sp)
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove from watchlist",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun AddWatchlistDialog(
    name: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Watchlist") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Watchlist Name") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = onConfirm, enabled = name.isNotBlank()) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

}

@Composable
fun WatchlistCard(
    watchlistWithStocks: WatchlistWithStocks,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onDeleteStock: (Company_ListingEntity) -> Unit,
    onStockClick: (Company_ListingEntity) -> Unit,
    onDeleteWatchList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = watchlistWithStocks.watchListId.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,

                )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)
                IconButton(
                    onClick = {
                        onDeleteWatchList()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Watchlist",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )

                }
                Spacer(modifier = Modifier.width(30.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }

        // Animated visibility for the stocks list
        AnimatedVisibility(visible = isExpanded) {
            Column {
                watchlistWithStocks.stocks.forEach { stock ->
                    StockItem(
                        stock = stock,
                        onDelete = { onDeleteStock(stock) },
                        onClick = { onStockClick(stock) }
                    )
                }
            }
        }
    }

}
@Composable
fun WatchlistTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Watchlists",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),

            )
        }
    }
}
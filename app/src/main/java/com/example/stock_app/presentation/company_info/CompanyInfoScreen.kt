package com.example.stock_app.presentation.company_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stock_app.R
import com.example.stock_app.data.mappers.toMarketCap
import com.example.stock_app.presentation.destinations.WatchlistScreenDestination
import com.example.stock_app.ui.theme.DarkBlue
import com.example.stock_app.utils.SimpleExpandableField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
    navigator: DestinationsNavigator,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val sheetState = rememberModalBottomSheetState()
    if (state.error == null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.company?.let { company ->
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Column {
                            Text(
                                text = company.symbol,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = company.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                            )
                            IconButton(
                                onClick = {viewModel.onEvent(CompanyInfoEvent.ShowWatchListSheet)}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null
                                )
                            }


                        }
                        Image(
                            painter = painterResource(R.drawable.ic_paypal),
                            contentDescription = null,
                            modifier = Modifier
                                .align(CenterEnd)
                                .clip(RectangleShape)
                                .size(60.dp)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(180.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = company.country,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(25.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = company.industry,
                                    fontSize = 8.sp,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                )

                            }

                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }

                item { HorizontalDivider(modifier = Modifier.fillMaxWidth()) }

                if (state.stockInfos.isNotEmpty()) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item { Text(text = "Market Summary") }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Center
                        ) {
                            StockChart(
                                infos = state.stockInfos,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)

                            )
                        }

                    }
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Key Metrics",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "52W Low",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$ ${company.weekLow}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,

                                    )
                                }
                                Column {
                                    Text(
                                        text = "52W High",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$ ${company.weekHigh}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(end = 48.dp)
                                    )
                                }

                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "P/E Ratio",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = company.peRatio,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Market Cap",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = company.marketCap.toMarketCap(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    Text(
                        text = "About Company",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item {
                    SimpleExpandableField(
                        text = company.description,
                        maximumLines = 3
                    )
                }
            }
        }
    }
    if(state.isSheetShown){
        ModalBottomSheet(
            onDismissRequest = { viewModel.onEvent(CompanyInfoEvent.DismissWatchListSheet) },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Add to Watchlist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(state.watchlists){watchlist->
                        Text(
                            text = watchlist.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(CompanyInfoEvent.AddToWatchList(watchlist))
                                }
                                .padding(vertical = 12.dp)
                        )

                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

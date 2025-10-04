package com.example.stock_app.presentation.BullsBearScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stock_app.domain.model.Topper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoversListScreen(
    modifier: Modifier = Modifier,
    viewModel: ToppersViewModel = hiltViewModel(),
    listType: String,
    navController: NavController,
    onStockClick: (String) -> Unit
    ) {
    val state = viewModel.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ){
            if(state.isLoading){
                CircularProgressIndicator()
            }else if (state.error!=null){
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)

                ) {
                    items(state.stocks){item: Topper ->
                            ListScreenCard(
                                topper = item, onAction = { onStockClick(item.ticker) })
                    }
                }
            }
        }
    }
}

@Composable
fun ListScreenCard(
    topper: Topper,
    modifier: Modifier=Modifier,
    onAction: ()-> Unit
) {
    val DarkCharcoal = Color(0xFF1A1E25)
    val isGainer = topper.changePercentage.startsWith("+")
    val color = if (isGainer) Color(0xFF00C781) else Color.Red.copy(alpha = 0.9f)
    val trendIcon = if(isGainer) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown
    Card(
        colors = CardDefaults.cardColors(DarkCharcoal.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier.size(160.dp).clickable { onAction() },
        elevation = CardDefaults.cardElevation(20.dp)

    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = topper.ticker,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))

            Column {
                Text(
                    text ="$${topper.price}",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = topper.changePercentage,
                        color = color,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = trendIcon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
        }
    }

}
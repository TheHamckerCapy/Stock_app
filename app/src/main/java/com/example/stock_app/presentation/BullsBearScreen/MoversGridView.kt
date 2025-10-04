package com.example.stock_app.presentation.BullsBearScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stock_app.domain.model.Topper

@Composable
fun MoversGridView(
    title: String,
    movers: List<Topper>,
    onSeeMoreClick:()-> Unit,
    modifier: Modifier=Modifier,
    onItemClick: (Topper) -> Unit
) {

    Column(modifier=modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(340.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            items(movers.take(3)){item->
                    HomeCards(topper = item, onClick = { onItemClick(item) })
            }
            item{
                SeeMoreCard(onClick = onSeeMoreClick)
            }
        }
    }
}
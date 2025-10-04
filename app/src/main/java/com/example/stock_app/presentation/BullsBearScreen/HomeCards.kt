package com.example.stock_app.presentation.BullsBearScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stock_app.domain.model.Topper

/*Color.Black.copy(0.4f)
*/
@Composable
fun HomeCards(
    topper: Topper,
    modifier: Modifier=Modifier,
    onClick: () -> Unit
) {
    val DarkCharcoal = Color(0xFF1A1E25)
    val isGainer = topper.changePercentage.startsWith("+")
    val color = if (isGainer) Color(0xFF00C781) else Color.Red.copy(alpha = 0.9f)
    val trendIcon = if(isGainer) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown
    Card(
        colors = CardDefaults.cardColors(DarkCharcoal.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier.size(150.dp).clickable { onClick() },
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
@Composable
fun SeeMoreCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "See More",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "See More",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
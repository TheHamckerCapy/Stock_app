package com.example.stock_app.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleExpandableField(
    modifier: Modifier = Modifier,
    text: String,
    maximumLines: Int = 3,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier=Modifier.padding(bottom = 30.dp)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else maximumLines,
            fontWeight = FontWeight.Light,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = if(isExpanded) "Show Less" else "Show More",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clickable { isExpanded =!isExpanded }
                .align(Alignment.End)
                .padding(vertical = 9.dp)
        )

    }
}
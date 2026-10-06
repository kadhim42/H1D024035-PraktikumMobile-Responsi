package com.responsi.pokemon.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val MAX_STAT = 150f

@Composable
fun StatBar(
    name: String,
    value: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name.replace("-", " ").replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.width(110.dp)
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(40.dp)
        )

        LinearProgressIndicator(
            progress = { (value / MAX_STAT).coerceAtMost(1f) },
            modifier = Modifier.weight(1f),
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
    }
}
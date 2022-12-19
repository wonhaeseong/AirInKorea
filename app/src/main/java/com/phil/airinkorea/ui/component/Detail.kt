package com.phil.airinkorea.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AikTypography

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun Details(
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun DetailsBigComponent(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .size(150.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Text(
                text = "PM 2.5",
                style = AikTypography.titleMedium,
            )
            Text(
                text = "Very\nGood",
                style = AikTypography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "5 μg/m^3",
                style = AikTypography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun DetailSmallComponent(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .width(75.dp)
            .height(120.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Text(
                text = "Yellow dust",
                style = AikTypography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Very\nGood",
                style = AikTypography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "5 μg/m^3",
                style = AikTypography.bodySmall
            )
        }
    }
}

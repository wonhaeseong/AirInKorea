package com.phil.airinkorea.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.AikTypography
import com.phil.airinkorea.ui.theme.level1_background
import com.phil.airinkorea.ui.theme.level1_onPrimary
import com.phil.airinkorea.ui.theme.level1_starColor

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun AikTopAppBar(
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                imageVector = AikIcons.Menu,
                contentDescription = null,
                tint = level1_onPrimary,
                modifier = Modifier
                    .size(24.dp)
            )
            LocationInfoText(
                Location = "HamYang-Gun"
            )
            Icon(
                painter = painterResource(id = AikIcons.Star),
                contentDescription = null,
                tint = level1_starColor,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        AirValueText()
    }
}

@Composable
fun LocationInfoText(
    Location: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = AikIcons.Location),
            contentDescription = null,
            tint = level1_onPrimary
        )
        Text(
            text = Location,
            style = AikTypography.bodyLarge,
            color = level1_onPrimary,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5, widthDp = 200)
@Composable
fun AirValueText(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .offset(x = 0.dp, y = 5.dp)
    ) {
        Text(
            text = "Very Good",
            style = AikTypography.headlineLarge,
            color = level1_onPrimary,
        )
    }
}

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

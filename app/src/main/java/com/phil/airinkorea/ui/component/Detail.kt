package com.phil.airinkorea.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.AikTypography
import com.phil.airinkorea.R

enum class DetailType {
    Main,
    Sub
}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun Details(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        DetailsBar()
    }
}

@Preview
@Composable
fun DetailsBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Details"
        )
        Icon(
            imageVector = AikIcons.ArrowDropDown,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailExpandableCard() {
    var expandedState by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            expandedState = !expandedState
        }
    ){
       Column() {
           Row() {
               DetailsForm(
                   title = stringResource(id = R.string.pm_10),
                   value = "Very\nGood",
                   unit = stringResource(id = R.string.micro_per_square_meter,5),
                   sourceColor = Color.Blue,
                   type = DetailType.Main
               )

               DetailsForm(
                   title = stringResource(id = R.string.pm_2_5),
                   value = "Very\nGood",
                   unit = stringResource(id = R.string.micro_per_square_meter,5),
                   sourceColor = Color.Blue,
                   type = DetailType.Main
               )
           }
           if (expandedState){
               Row() {
                   DetailsForm(
                       title = stringResource(id = R.string.pm_2_5),
                       value = "Very\nGood",
                       unit = stringResource(id = R.string.micro_per_square_meter,5),
                       sourceColor = Color.Blue,
                       type = DetailType.Sub
                   )
                   DetailsForm(
                       title = stringResource(id = R.string.pm_2_5),
                       value = "Very\nGood",
                       unit = stringResource(id = R.string.micro_per_square_meter,5),
                       sourceColor = Color.Blue,
                       type = DetailType.Sub
                   )
                   DetailsForm(
                       title = stringResource(id = R.string.pm_2_5),
                       value = "Very\nGood",
                       unit = stringResource(id = R.string.micro_per_square_meter,5),
                       sourceColor = Color.Blue,
                       type = DetailType.Sub
                   )
               }
           }
       }
    }
}

@Composable
fun DetailsForm(
    title: String,
    value: String,
    unit: String,
    sourceColor: Color,
    type: DetailType,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        if (type == DetailType.Main) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                Text(
                    text = title,
                    style = AikTypography.titleMedium,
                )
                Text(
                    text = value,
                    style = AikTypography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(3.dp)
                        .background(color = sourceColor)
                )
                Text(
                    text = unit,
                    style = AikTypography.bodyMedium
                )
            }
        } else {
            Text(
                text = title,
                style = AikTypography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = AikTypography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(3.dp)
                    .background(color = sourceColor)
            )
            Text(
                text = unit,
                style = AikTypography.bodySmall
            )
        }
    }
}
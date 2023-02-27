package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.theme.error

@Composable
fun GPSBanner(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = AIKTheme.colors.core_container,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    painter = painterResource(id = AIKIcons.GPSError),
                    tint = error,
                    contentDescription = null,
                    modifier = Modifier.size(42.5.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = stringResource(id = R.string.gps_error),
                    color = AIKTheme.colors.on_core_container,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                    Text(
                        text = stringResource(id = R.string.dismiss),
                        color = AIKTheme.colors.core,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                    Text(
                        text = stringResource(id = R.string.turn_on_gps),
                        color = AIKTheme.colors.core,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GPSBannerPreview() {
    AIKTheme(PollutionLevel.EXCELLENT) {
        GPSBanner()
    }
}
package com.phil.airinkorea.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.AikTypography
import com.phil.airinkorea.ui.theme.Shapes
import com.phil.airinkorea.ui.theme.snackBarColor
import kotlinx.coroutines.launch

@Composable
fun AikSnackBar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = hostState
    ) { snackbarData ->
        Card(
            shape = Shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = AikIcons.Error), contentDescription = null)
                Text(text = snackbarData.message)
            }
        }
    }
}

val CustomSnackBarHost: @Composable (SnackbarHostState) -> Unit =
    { snackbarHostState: SnackbarHostState ->
        SnackbarHost(
            hostState = snackbarHostState
        ) { snackbarData ->
            Card(
                shape = Shapes.small,
                backgroundColor = snackBarColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        ,
                    verticalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        tint = Color.White,
                        contentDescription = null
                    )
                    Text(
                        text = snackbarData.message,
                        color = Color.White
                    )
                }
            }
        }
    }

@Composable
@Preview
fun SnackBarPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        snackbarHost = CustomSnackBarHost
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "스낵스낵스낵스낵",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                content = { Icon(imageVector = Icons.Default.Add, contentDescription = null) }
            )
        }
    }
}



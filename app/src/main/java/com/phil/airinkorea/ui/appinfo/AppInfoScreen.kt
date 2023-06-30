package com.phil.airinkorea.ui.appinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.commoncomponent.CommonExpendableContent
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.modifier.bottomBorder
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider

@Composable
fun AppInfoScreen(
    onBackButtonClick: () -> Unit,
    appInfoContentList: List<AppInfoContent>
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            CommonTopAppBar(
                onBackButtonClick = onBackButtonClick,
                title = stringResource(id = R.string.app_info),
                modifier = Modifier.statusBarsPadding()
            )
        },
        backgroundColor = common_background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            for (content in appInfoContentList) {
                CommonExpendableContent(
                    title = stringResource(id = content.title),
                    onClick = content.onClick,
                    modifier = Modifier
                        .bottomBorder(
                            1.dp,
                            divider
                        )
                        .fillMaxWidth()
                ) {
                    content.content()
                }
            }
        }
    }
}
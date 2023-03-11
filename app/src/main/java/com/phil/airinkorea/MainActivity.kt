package com.phil.airinkorea

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.usecases.GetSearchResultUseCase
import com.phil.airinkorea.ui.addlocation.AddLocationContent
import com.phil.airinkorea.ui.addlocation.AddLocationRoute
import com.phil.airinkorea.ui.addlocation.AddLocationScreen
import com.phil.airinkorea.ui.addlocation.getLocationList
import com.phil.airinkorea.ui.screen.HomeScreen
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            AIKTheme(
                PollutionLevel.EXCELLENT
            ) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent
                    )
                }
                AddLocationRoute() {
                    TODO()
                }
//                val searchResultState: List<Location> by locationViewModel.searchResult.collectAsStateWithLifecycle()
//                AddLocationScreen(
//                    searchResultState = searchResultState,
//                    onBackButtonClick = { TODO() },
//                    onSearchTextChange = {locationViewModel.getSearchResult(it.text)}, // 텍스트가 변경될 때 마다 쿼리 실행
//                    onDialogConfirmButtonClick ={ TODO() }
//                )

            }
        }
    }
}
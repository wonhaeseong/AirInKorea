package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.phil.airinkorea.ui.home.TitleBar
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

@Composable
fun Map(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        TitleBar(titleText = "Map")
        Spacer(modifier = Modifier.size(8.dp))
        CustomMap()
    }
}
@Preview
@Composable
fun CustomMap(
    modifier: Modifier = Modifier,
    latLng: LatLng = LatLng(1.35, 103.87),
    zoom: Float = 15f
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, zoom)
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false,
                indoorLevelPickerEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                tiltGesturesEnabled = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false
            )
        )
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .matchParentSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings
        ) {
            //TODO: 미세먼지 단계에 따라 컬러 이미지 추가 및 분기처리
            Marker(
                state = MarkerState(position = latLng),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        }
    }
}

@Preview
@Composable
fun MapPreview(){
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        Map()
    }
}
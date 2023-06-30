package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.AppStatusRepository
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface ManageLocationUiState {
    object Loading : ManageLocationUiState
    data class Success(
        var bookmark: Location,
        var userLocationList: List<Location> = emptyList()
    ) : ManageLocationUiState
}

@HiltViewModel
class ManageLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val appStatusRepository: AppStatusRepository
) : ViewModel() {
    val manageLocationUiState: StateFlow<ManageLocationUiState> =
        combine(
            locationRepository.getBookmark(),
            locationRepository.getUserLocationList()
        ) { bookmark, userLocationList ->
            ManageLocationUiState.Success(
                bookmark = bookmark,
                userLocationList = userLocationList
            )
        }.flowOn(Dispatchers.IO).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ManageLocationUiState.Loading
        )

    //같으면 북마크로 다르면 삭제후 페이지 가져와보고 찾아서 바꿔야함
    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            if (manageLocationUiState.value is ManageLocationUiState.Success) {
                val successManageLocationUiState =
                    (manageLocationUiState.value as ManageLocationUiState.Success)
                val currentPage =
                    withContext(Dispatchers.IO) { appStatusRepository.getDefaultPage().first() }
                val deletePage = successManageLocationUiState.userLocationList.indexOf(location) + 2
                if (currentPage == deletePage) {
                    withContext(Dispatchers.IO) {
                        locationRepository.deleteUserLocation(location)
                        appStatusRepository.fetchDefaultPage(1)
                    }
                } else {
                    withContext(Dispatchers.IO) {
                        val currentLocation =
                            successManageLocationUiState.userLocationList[currentPage - 2]
                        locationRepository.deleteUserLocation(location)
                        val currentPageAfterDelete =
                            locationRepository.getUserLocationList().first()
                                .indexOf(currentLocation) + 2
                        appStatusRepository.fetchDefaultPage(currentPageAfterDelete)
                    }
                }
            }
        }
    }

    /**
     * GPS -> 그대로 둬
     * 북마크 -> 변경후 북마크가 어느 페이지에 있는지 확인 후 이동
     * 다른 -> 만약 선택된게 현재 페이지면  1로 이동, 다른페이지가 선택되면 페이지 정렬 후 location page로 가야함
     */
    fun updateBookmark(location: Location) {
        viewModelScope.launch {
            if (manageLocationUiState.value is ManageLocationUiState.Success) {
                val successManageLocationUiState =
                    (manageLocationUiState.value as ManageLocationUiState.Success)
                val oldBookmark = successManageLocationUiState.bookmark
                when (val currentPage =
                    withContext(Dispatchers.IO) { appStatusRepository.getDefaultPage().first() }) {
                    0 -> Unit
                    1 -> {
                        withContext(Dispatchers.IO) {
                            locationRepository.updateBookmark(
                                oldBookmark = oldBookmark,
                                newBookmark = location
                            )
                            val oldBookmarkPageAfterUpdate =
                                locationRepository.getUserLocationList().first()
                                    .indexOf(oldBookmark) + 2
                            appStatusRepository.fetchDefaultPage(oldBookmarkPageAfterUpdate)
                        }
                    }

                    else -> {
                        val selectedPage =
                            successManageLocationUiState.userLocationList.indexOf(location) + 2
                        if (currentPage == selectedPage) {
                            withContext(Dispatchers.IO) {
                                locationRepository.updateBookmark(
                                    oldBookmark = oldBookmark,
                                    newBookmark = location
                                )
                                appStatusRepository.fetchDefaultPage(1)
                            }
                        } else {
                            withContext(Dispatchers.IO) {
                                val currentLocation =
                                    successManageLocationUiState.userLocationList[currentPage - 2]
                                locationRepository.updateBookmark(
                                    oldBookmark = oldBookmark,
                                    newBookmark = location
                                )
                                val currentPageAfterUpdate =
                                    locationRepository.getUserLocationList().first()
                                        .indexOf(currentLocation) + 2
                                appStatusRepository.fetchDefaultPage(currentPageAfterUpdate)
                            }
                        }
                    }
                }
            }
        }
    }
}
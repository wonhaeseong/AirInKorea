package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AppGuide
import javax.inject.Inject

class AppGuideRepositoryImpl @Inject constructor() : AppGuideRepository {
    private val dataLoading =
        "The data is real-time observational data , and it may not be received due to the local conditions of the monitoring stations or the data reception status.\n" +
                "The measurement data at the monitoring stations is updated every hour, while the app server fetches values every 15 minutes.\n" +
                "Please note that the data update time may vary across different monitoring stations."
    private val airPollutionLevel = ""
    private val detail = ""
    private val information = ""
    private val dailyForecast = ""
    private val koreaForecastModel = ""

    override fun getAppGuideData(): AppGuide = AppGuide(
        dataLoading = dataLoading,
        airPollutionLevel = airPollutionLevel,
        detail = detail,
        information = information,
        dailyForecast = dailyForecast,
        koreaForecastModel = koreaForecastModel
    )
}
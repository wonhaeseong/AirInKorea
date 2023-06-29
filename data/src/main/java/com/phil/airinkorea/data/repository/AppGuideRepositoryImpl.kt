package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AppGuide
import javax.inject.Inject

class AppGuideRepositoryImpl @Inject constructor() : AppGuideRepository {
    private val caution =
        """
        All data used in the Air In Korea app are provided by the Korea Environment Corporation, an official organization.
        However, it should be noted that the app itself is not an official institution, but a platform that presents data processed and provided by individuals.
        As a result, you are fully responsible for everything that is done based on the information provided by the app.
        """.trimIndent()
    private val dataLoading =
        """
        The data is real-time observational data, and it may not be received due to the local conditions of the monitoring stations or the data reception status.
        
        The measurement data at the monitoring stations is updated every hour, while the app server fetches values every 15 minutes.
        Please note that the data update time may vary across different monitoring stations.  
        """.trimIndent()
    private val airPollutionLevel =
        """
        The Korea Environment Corporation utilizes a four-tier classification system (Good, Moderate, Bad, Very Bad) to indicate the level of air pollution. 
        However, as an individual who is particularly sensitive to air pollution, I have often noticed that even when the air quality is classified as moderate according to the Korea Environment Corporation's standards, the visibility of distant buildings is frequently impaired. 
        Personally, I have felt that the pollution levels were too lenient. 
        
        Therefore, I have restructured the air pollution levels into a more detailed six-tier system, aiming to instill a greater sense of urgency regarding air pollution. 
        As depicted in the table below, I have subdivided the existing Moderate and Bad levels to underscore the need for heightened awareness and action in response to air pollution.
        
        For your information, we applied new standards only for PM10 and PM25, and followed the standards of The Korea Environment Corporation for O3, NO2, CO, and SO2.

        """.trimIndent()
    private val particulateMatter =
        """
        Particulate matter can trigger or worsen respiratory illnesses and be associated with various health problems such as cardiovascular diseases, lung cancer, bronchitis, and allergic reactions.
        Especially, high concentrations of particulate matter pose a greater risk to children, the elderly, and individuals with underlying health conditions, potentially having long-term negative effects on health. 
        
        Personally, it is recommended to minimize indoor activities and take preventive measures such as wearing masks on days with high levels of particulate matter.

        PM10 represents particles with a diameter of 10 micrometers or smaller. These particles are smaller than what we can block with a mask and can enter our respiratory system. Major sources include road traffic, industrial processes, construction activities, and they contribute to the hazy air we experience with visible larger particles. PM10 can cause health issues such as coughing, sneezing, throat irritation, and respiratory difficulties.
        
        PM2.5 represents particles with a diameter of 2.5 micrometers or smaller. PM2.5 particles are fine and light, capable of being transported long distances by wind and even contaminating indoor air. Major sources include road traffic, industrial emissions, coal and wood burning for heating, among others. These fine particles can penetrate our respiratory system and pose significant health risks. PM2.5 is associated with cardiovascular diseases, respiratory disorders, allergic reactions, and even lung cancer.
        """.trimIndent()
    private val detail =
        """
        This section provides real-time regional figures for PM10, PM25, SO2, O3, NO2, and CO.
        Among the six air pollutants, the worst Air Pollution Level determines the HomeScreen theme.
        """.trimIndent()
    private val information =
        """
        This section provides information on the causes of air pollutants in Korea.
        """.trimIndent()
    private val dailyForecast =
        """
         This section provides daily air pollution forecasts for your region. 
         
         In the developer's experience, predictive data is often displayed as good, unlike in reality. In other words, it is often presented generously.   
        """.trimIndent()
    private val koreaForecastModel =
        """
        This section shows the data predicting how PM10 and PM25 particles will move in Korea in the future.
        
        The current image is unstoppable and cannot see the desired time point. So I have a plan to update it later.
        """.trimIndent()
    private val addLocation =
        """
        You can search most parts of Korea and add them to 'My locations'.
        If you want to search for an area based on Korean 'eup', 'myeon', and 'dong', you must search for 'Legal Dong'. 
        I also plan to add administrative dong searches later.    
        """.trimIndent()
    override fun getAppGuideData(): AppGuide = AppGuide(
        cautionGuideText = caution,
        dataLoadingGuideText = dataLoading,
        particulateMatterGuideText = particulateMatter,
        airPollutionLevelGuideText = airPollutionLevel,
        detailGuideText = detail,
        informationGuideText = information,
        dailyForecastGuideText = dailyForecast,
        koreaForecastModelGuideText = koreaForecastModel,
        addLocationGuideText = addLocation
    )
}

<a href='https://play.google.com/store/apps/details?id=com.phil.airinkorea&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
  <img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width='200'/>
</a>


# Air In Korea App

**한국에 거주하는 외국인, 한국에 여행 온 외국인들을 위한 미세먼지 앱입니다.**  
**앱에서 사용되는 데이터는 모두 한국환경공단의 공공 데이터API로부터 얻습니다.**
 

# **Features**

**1. 휴대폰 내장 GPS를 이용한 위치기반 미세먼지 정보 제공**  
**2. 지역 검색을 통한 미세먼지 정보제공**  


![feature drawio (5)](https://github.com/want8607/AirInKorea/assets/84075111/82db29ef-8eac-44c3-a82e-24cfedf018c8)

 
# Architecture

**1. Air In Korea 앱은 Jetpack Compose로 구현되었습니다.**  
**2. Repository Pattern을 사용하여 UI layer와 data layer의 의존성을 최소화했습니다.**  
**3. Firebase의 CloudFunctions 이용하여 서버와 클라이언트를 연결합니다.**
 
![AirInKoreaArchitecture drawio (2)](https://github.com/want8607/AirInKorea/assets/84075111/8defa616-bb09-443a-bc25-d11fb3974f73)

 
# UI

**사용자가 대기오염 정도를 한눈에 파악하기 위해서는 대기오염 정도에 따라 앱의 테마를 동적으로 바꿀 필요가 있었습니다.**
**Material Theme을 그대로 사용한다면 Color 필드명이 모호하고 앱 디자인을 유지보수 하는데 어려움이 있을 것으로 생각되어**
**그대로 사용하거나 일부만 커스텀하기보다는 새로운 커스텀 Theme인 AIKTheme을 만들었습니다.**  
**Typography는 Material Theme의 것을 그대로 사용하고 커스텀 필요 시 확장함수를 사용하였습니다.**  
  
**추후에 사용자의 화면 사이즈에 따라 변경되는 Adaptive layout을 적용할 예정입니다.**  

![feature drawio (4)](https://github.com/want8607/AirInKorea/assets/84075111/999121a7-0be5-4f18-adad-6de11965fca8)

  
# Libraries
  
**1. Hilt**    
**2. Kotlin Serializer**  
**3. Room**  
**4. DataStore**    
**5. Lottie**  
**6. Glide**  
**7. Coroutines**  

 
# License

**Air In Korea** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.

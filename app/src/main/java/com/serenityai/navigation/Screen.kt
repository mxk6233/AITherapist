package com.serenityai.navigation

sealed class Screen(val route: String) {
    // Main Flow
    object Splash : Screen("splash")
    object Authentication : Screen("authentication")
    object PersonalityOnboarding : Screen("personality_onboarding") // UC5
    object MainDashboard : Screen("main_dashboard")
    
    // Chat Features
    object ChatFeatures : Screen("chat_features")
    object AIChatSession : Screen("ai_chat_session") // UC1
    object ChatHistory : Screen("chat_history") // UC6
    object CrisisIntervention : Screen("crisis_intervention") // UC2
    object VoiceTherapy : Screen("voice_therapy") // UC38
    
    // Mood & Analytics
    object MoodAnalytics : Screen("mood_analytics")
    object MoodLogging : Screen("mood_logging") // UC3
    object MoodAnalyticsView : Screen("mood_analytics_view") // UC9
    object MoodForecasting : Screen("mood_forecasting") // UC26
    object BurnoutDetection : Screen("burnout_detection") // UC37
    object RelapsePrevention : Screen("relapse_prevention") // UC35
    
    // Support Tools
    object SupportTools : Screen("support_tools")
    object CopingExercises : Screen("coping_exercises") // UC8
    object JournalPrompts : Screen("journal_prompts") // UC32
    object EducationalResources : Screen("educational_resources") // UC16
    object UserSupport : Screen("user_support") // UC25
    object GroupTherapy : Screen("group_therapy") // UC34
    object CommunitySupportCircles : Screen("community_support_circles") // UC39
    object ReligiousSupport : Screen("religious_support") // UC40
    
    // Wellness Activities
    object WellnessActivities : Screen("wellness_activities")
    object GuidedBreathing : Screen("guided_breathing") // UC27
    object DailyAffirmations : Screen("daily_affirmations") // UC14
    
    // Settings & Personalization
    object Settings : Screen("settings")
    object SecurityProtocols : Screen("security_protocols") // UC23
    object Personalization : Screen("personalization") // UC24
    object Accessibility : Screen("accessibility") // UC17
    object Notifications : Screen("notifications") // UC18
    object AppPreferences : Screen("app_preferences") // UC13
    object SystemHealth : Screen("system_health") // UC22
    object ApplicationFeedback : Screen("application_feedback") // UC11
}

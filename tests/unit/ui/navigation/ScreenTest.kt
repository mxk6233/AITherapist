package com.serenityai.tests.unit.ui.navigation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ScreenTest {
    
    @Test
    fun `all screen routes should be unique`() {
        val routes = listOf(
            Screen.Splash.route,
            Screen.Authentication.route,
            Screen.MainDashboard.route,
            Screen.ChatFeatures.route,
            Screen.AIChatSession.route,
            Screen.ChatHistory.route,
            Screen.CrisisIntervention.route,
            Screen.MoodAnalytics.route,
            Screen.MoodLogging.route,
            Screen.MoodAnalyticsView.route,
            Screen.MoodForecasting.route,
            Screen.BurnoutDetection.route,
            Screen.RelapsePrevention.route,
            Screen.SupportTools.route,
            Screen.CopingExercises.route,
            Screen.JournalPrompts.route,
            Screen.EducationalResources.route,
            Screen.UserSupport.route,
            Screen.WellnessActivities.route,
            Screen.GuidedBreathing.route,
            Screen.DailyAffirmations.route,
            Screen.Settings.route,
            Screen.SecurityProtocols.route,
            Screen.Personalization.route,
            Screen.Accessibility.route,
            Screen.Notifications.route,
            Screen.AppPreferences.route
        )
        
        val uniqueRoutes = routes.toSet()
        assertEquals(routes.size, uniqueRoutes.size, "All routes should be unique")
    }
    
    @Test
    fun `splash screen route should be correct`() {
        assertEquals("splash", Screen.Splash.route)
    }
    
    @Test
    fun `authentication screen route should be correct`() {
        assertEquals("authentication", Screen.Authentication.route)
    }
    
    @Test
    fun `main dashboard screen route should be correct`() {
        assertEquals("main_dashboard", Screen.MainDashboard.route)
    }
    
    @Test
    fun `chat features screen route should be correct`() {
        assertEquals("chat_features", Screen.ChatFeatures.route)
    }
    
    @Test
    fun `ai chat session screen route should be correct`() {
        assertEquals("ai_chat_session", Screen.AIChatSession.route)
    }
    
    @Test
    fun `chat history screen route should be correct`() {
        assertEquals("chat_history", Screen.ChatHistory.route)
    }
    
    @Test
    fun `crisis intervention screen route should be correct`() {
        assertEquals("crisis_intervention", Screen.CrisisIntervention.route)
    }
    
    @Test
    fun `mood analytics screen route should be correct`() {
        assertEquals("mood_analytics", Screen.MoodAnalytics.route)
    }
    
    @Test
    fun `mood logging screen route should be correct`() {
        assertEquals("mood_logging", Screen.MoodLogging.route)
    }
    
    @Test
    fun `mood analytics view screen route should be correct`() {
        assertEquals("mood_analytics_view", Screen.MoodAnalyticsView.route)
    }
    
    @Test
    fun `mood forecasting screen route should be correct`() {
        assertEquals("mood_forecasting", Screen.MoodForecasting.route)
    }
    
    @Test
    fun `burnout detection screen route should be correct`() {
        assertEquals("burnout_detection", Screen.BurnoutDetection.route)
    }
    
    @Test
    fun `relapse prevention screen route should be correct`() {
        assertEquals("relapse_prevention", Screen.RelapsePrevention.route)
    }
    
    @Test
    fun `support tools screen route should be correct`() {
        assertEquals("support_tools", Screen.SupportTools.route)
    }
    
    @Test
    fun `coping exercises screen route should be correct`() {
        assertEquals("coping_exercises", Screen.CopingExercises.route)
    }
    
    @Test
    fun `journal prompts screen route should be correct`() {
        assertEquals("journal_prompts", Screen.JournalPrompts.route)
    }
    
    @Test
    fun `educational resources screen route should be correct`() {
        assertEquals("educational_resources", Screen.EducationalResources.route)
    }
    
    @Test
    fun `user support screen route should be correct`() {
        assertEquals("user_support", Screen.UserSupport.route)
    }
    
    @Test
    fun `wellness activities screen route should be correct`() {
        assertEquals("wellness_activities", Screen.WellnessActivities.route)
    }
    
    @Test
    fun `guided breathing screen route should be correct`() {
        assertEquals("guided_breathing", Screen.GuidedBreathing.route)
    }
    
    @Test
    fun `daily affirmations screen route should be correct`() {
        assertEquals("daily_affirmations", Screen.DailyAffirmations.route)
    }
    
    @Test
    fun `settings screen route should be correct`() {
        assertEquals("settings", Screen.Settings.route)
    }
    
    @Test
    fun `security protocols screen route should be correct`() {
        assertEquals("security_protocols", Screen.SecurityProtocols.route)
    }
    
    @Test
    fun `personalization screen route should be correct`() {
        assertEquals("personalization", Screen.Personalization.route)
    }
    
    @Test
    fun `accessibility screen route should be correct`() {
        assertEquals("accessibility", Screen.Accessibility.route)
    }
    
    @Test
    fun `notifications screen route should be correct`() {
        assertEquals("notifications", Screen.Notifications.route)
    }
    
    @Test
    fun `app preferences screen route should be correct`() {
        assertEquals("app_preferences", Screen.AppPreferences.route)
    }
}

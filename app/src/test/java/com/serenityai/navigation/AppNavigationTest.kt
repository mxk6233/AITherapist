package com.serenityai.navigation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AppNavigationTest {

    @Test
    fun screenRoutes_areCorrectlyDefined() {
        // Test that all screen routes are properly defined
        assertEquals("splash", Screen.Splash.route)
        assertEquals("authentication", Screen.Authentication.route)
        assertEquals("main_dashboard", Screen.MainDashboard.route)
        assertEquals("ai_chat_session", Screen.AIChatSession.route)
        assertEquals("chat_history", Screen.ChatHistory.route)
        assertEquals("crisis_intervention", Screen.CrisisIntervention.route)
    }

    @Test
    fun screenRoutes_areUnique() {
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
    fun screenRoutes_haveCorrectFormat() {
        // Test that all routes follow the correct format (lowercase with underscores)
        val allScreens = listOf(
            Screen.Splash,
            Screen.Authentication,
            Screen.MainDashboard,
            Screen.ChatFeatures,
            Screen.AIChatSession,
            Screen.ChatHistory,
            Screen.CrisisIntervention,
            Screen.MoodAnalytics,
            Screen.MoodLogging,
            Screen.MoodAnalyticsView,
            Screen.MoodForecasting,
            Screen.BurnoutDetection,
            Screen.RelapsePrevention,
            Screen.SupportTools,
            Screen.CopingExercises,
            Screen.JournalPrompts,
            Screen.EducationalResources,
            Screen.UserSupport,
            Screen.WellnessActivities,
            Screen.GuidedBreathing,
            Screen.DailyAffirmations,
            Screen.Settings,
            Screen.SecurityProtocols,
            Screen.Personalization,
            Screen.Accessibility,
            Screen.Notifications,
            Screen.AppPreferences
        )
        
        allScreens.forEach { screen ->
            val route = screen.route
            assert(route.isNotEmpty()) { "Route should not be empty for ${screen::class.simpleName}" }
            assert(route.all { it.isLowerCase() || it == '_' }) { "Route should be lowercase with underscores: $route" }
        }
    }
}
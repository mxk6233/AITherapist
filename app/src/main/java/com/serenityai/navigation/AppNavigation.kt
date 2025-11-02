package com.serenityai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serenityai.ui.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToAuth = {
                    navController.navigate(Screen.Authentication.route)
                }
            )
        }
        
        // Authentication Flow
        composable(Screen.Authentication.route) {
            AuthenticationScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.MainDashboard.route)
                },
                onSignUpSuccess = {
                    navController.navigate(Screen.PersonalityOnboarding.route)
                }
            )
        }
        
        // Personality Onboarding Flow
        composable(Screen.PersonalityOnboarding.route) {
            PersonalityOnboardingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onComplete = { profile ->
                    // Save personality profile and navigate to main dashboard
                    navController.navigate(Screen.MainDashboard.route) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Main Dashboard
        composable(Screen.MainDashboard.route) {
            MainDashboardScreen(
                onNavigateToChat = {
                    navController.navigate(Screen.ChatFeatures.route)
                },
                onNavigateToMood = {
                    navController.navigate(Screen.MoodAnalytics.route)
                },
                onNavigateToSupport = {
                    navController.navigate(Screen.SupportTools.route)
                },
                onNavigateToWellness = {
                    navController.navigate(Screen.WellnessActivities.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        // Chat Features
        composable(Screen.ChatFeatures.route) {
            ChatFeaturesScreen(
                onNavigateToAIChat = {
                    navController.navigate(Screen.AIChatSession.route)
                },
                onNavigateToChatHistory = {
                    navController.navigate(Screen.ChatHistory.route)
                },
                onNavigateToCrisisIntervention = {
                    navController.navigate(Screen.CrisisIntervention.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // AI Chat Session (UC1)
        composable(Screen.AIChatSession.route) {
            AIChatSessionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Chat History (UC6)
        composable(Screen.ChatHistory.route) {
            ChatHistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToChat = { chatId ->
                    // Navigate to specific chat session
                    navController.navigate(Screen.AIChatSession.route)
                }
            )
        }
        
        // Crisis Intervention (UC2)
        composable(Screen.CrisisIntervention.route) {
            CrisisInterventionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Mood & Analytics
        composable(Screen.MoodAnalytics.route) {
            MoodAnalyticsScreen(
                onNavigateToMoodLogging = {
                    navController.navigate(Screen.MoodLogging.route)
                },
                onNavigateToMoodAnalytics = {
                    navController.navigate(Screen.MoodAnalyticsView.route)
                },
                onNavigateToMoodForecasting = {
                    navController.navigate(Screen.MoodForecasting.route)
                },
                onNavigateToBurnoutDetection = {
                    navController.navigate(Screen.BurnoutDetection.route)
                },
                onNavigateToRelapsePrevention = {
                    navController.navigate(Screen.RelapsePrevention.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Log Daily Mood (UC3)
        composable(Screen.MoodLogging.route) {
            LogDailyMoodScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // View Mood Analytics (UC9)
        composable(Screen.MoodAnalyticsView.route) {
            MoodAnalyticsViewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Mood Forecasting (UC26)
        composable(Screen.MoodForecasting.route) {
            MoodForecastingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onViewDetailedForecast = {
                    // Navigate to detailed forecast view
                    // For now, just show a toast
                }
            )
        }
        
        // Burnout Detection (UC37)
        composable(Screen.BurnoutDetection.route) {
            BurnoutDetectionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Relapse Prevention (UC35)
        composable(Screen.RelapsePrevention.route) {
            RelapsePreventionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEmergencyContact = {
                    // Navigate to emergency contact screen or show emergency dialog
                    // For now, just show a toast
                }
            )
        }
        
        // Support Tools
        composable(Screen.SupportTools.route) {
            SupportToolsScreen(
                onNavigateToCopingExercises = {
                    navController.navigate(Screen.CopingExercises.route)
                },
                onNavigateToJournalPrompts = {
                    navController.navigate(Screen.JournalPrompts.route)
                },
                onNavigateToEducationalResources = {
                    navController.navigate(Screen.EducationalResources.route)
                },
                onNavigateToUserSupport = {
                    navController.navigate(Screen.UserSupport.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Coping Exercises (UC8)
        composable(Screen.CopingExercises.route) {
            CopingExercisesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onStartExercise = { exerciseId ->
                    // Navigate to exercise session
                    // For now, just show a toast or navigate to a generic exercise screen
                }
            )
        }
        
        // Journal Prompts (UC32)
        composable(Screen.JournalPrompts.route) {
            JournalingPromptsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onStartJournaling = { promptId ->
                    // Navigate to journaling session
                    // For now, just show a toast or navigate to a generic journaling screen
                }
            )
        }
        
        // Educational Resources (UC16)
        composable(Screen.EducationalResources.route) {
            EducationalResourcesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // User Support (UC25)
        composable(Screen.UserSupport.route) {
            UserSupportScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Wellness Activities
        composable(Screen.WellnessActivities.route) {
            WellnessActivitiesScreen(
                onNavigateToGuidedBreathing = {
                    navController.navigate(Screen.GuidedBreathing.route)
                },
                onNavigateToDailyAffirmations = {
                    navController.navigate(Screen.DailyAffirmations.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Guided Breathing (UC27)
        composable(Screen.GuidedBreathing.route) {
            GuidedBreathingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Daily Affirmations (UC14)
        composable(Screen.DailyAffirmations.route) {
            DailyAffirmationsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Settings & Personalization
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToSecurityProtocols = {
                    navController.navigate(Screen.SecurityProtocols.route)
                },
                onNavigateToPersonalization = {
                    navController.navigate(Screen.Personalization.route)
                },
                onNavigateToAccessibility = {
                    navController.navigate(Screen.Accessibility.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToAppPreferences = {
                    navController.navigate(Screen.AppPreferences.route)
                },
                onNavigateToSystemHealth = {
                    navController.navigate(Screen.SystemHealth.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Security Protocols (UC23)
        composable(Screen.SecurityProtocols.route) {
            SecurityProtocolsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Personalization (UC24)
        composable(Screen.Personalization.route) {
            PersonalizationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Accessibility (UC17)
        composable(Screen.Accessibility.route) {
            AccessibilityScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Notifications (UC18)
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // App Preferences (UC13)
        composable(Screen.AppPreferences.route) {
            AppPreferencesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // System Health (UC22)
        composable(Screen.SystemHealth.route) {
            SystemHealthScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

package com.serenityai.utils

import com.serenityai.data.models.*
import java.util.Date

object SampleData {
    
    fun createSampleUserProfile(): UserProfile {
        return UserProfile(
            id = "user_001",
            userId = "user_001",
            name = "Alex Johnson",
            email = "alex.johnson@example.com",
            dateOfBirth = Date(System.currentTimeMillis() - 25L * 365 * 24 * 60 * 60 * 1000), // 25 years ago
            goals = createSampleGoals(),
            preferences = UserPreferences(
                theme = "light",
                notifications = true,
                reminderTime = "09:00",
                language = "en",
                privacyLevel = PrivacyLevel.MEDIUM
            ),
            totalXP = 1250,
            currentStreak = 7,
            longestStreak = 21,
            badges = createSampleBadges(),
            level = 3
        )
    }
    
    fun createSampleGoals(): List<WellnessGoal> {
        return listOf(
            WellnessGoal(
                id = "goal_001",
                title = "Daily Meditation",
                description = "Practice 10 minutes of mindfulness meditation each day",
                category = GoalCategory.MINDFULNESS,
                progress = 75,
                isCompleted = false
            ),
            WellnessGoal(
                id = "goal_002",
                title = "Gratitude Journaling",
                description = "Write three things I'm grateful for each day",
                category = GoalCategory.EMOTIONAL_WELLNESS,
                progress = 100,
                isCompleted = true
            ),
            WellnessGoal(
                id = "goal_003",
                title = "Exercise Routine",
                description = "Complete 30 minutes of physical activity daily",
                category = GoalCategory.PHYSICAL_HEALTH,
                progress = 60,
                isCompleted = false
            )
        )
    }
    
    fun createSampleBadges(): List<Badge> {
        return listOf(
            Badge(
                id = "badge_001",
                name = "First Steps",
                description = "Completed your first journal entry",
                icon = "🌟",
                category = BadgeCategory.JOURNAL,
                rarity = BadgeRarity.COMMON,
                xpValue = 50
            ),
            Badge(
                id = "badge_002",
                name = "Week Warrior",
                description = "Maintained a 7-day streak",
                icon = "🔥",
                category = BadgeCategory.STREAK,
                rarity = BadgeRarity.UNCOMMON,
                xpValue = 100
            ),
            Badge(
                id = "badge_003",
                name = "Mood Master",
                description = "Logged mood for 30 consecutive days",
                icon = "📊",
                category = BadgeCategory.MOOD,
                rarity = BadgeRarity.RARE,
                xpValue = 250
            )
        )
    }
    
    fun createSampleJournalEntries(): List<JournalEntry> {
        return listOf(
            JournalEntry(
                id = "entry_001",
                userId = "user_001",
                title = "Morning Reflection",
                content = "Today I feel grateful for the sunshine and the opportunity to start fresh. I'm looking forward to my meditation practice this evening.",
                type = JournalType.GRATITUDE,
                mood = 8,
                tags = listOf("gratitude", "morning", "positive"),
                createdAt = Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000) // 2 days ago
            ),
            JournalEntry(
                id = "entry_002",
                userId = "user_001",
                title = "Work Stress",
                content = "Had a challenging day at work today. Feeling overwhelmed but trying to stay positive. Planning to take a walk after dinner to clear my mind.",
                type = JournalType.REFLECTION,
                mood = 4,
                tags = listOf("work", "stress", "coping"),
                createdAt = Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000) // 1 day ago
            ),
            JournalEntry(
                id = "entry_003",
                userId = "user_001",
                title = "Goal Progress",
                content = "Successfully completed my meditation practice for the 5th day in a row! Feeling proud of this consistency and noticing improved focus.",
                type = JournalType.GOAL_REVIEW,
                mood = 9,
                tags = listOf("goals", "meditation", "progress"),
                createdAt = Date() // Today
            )
        )
    }
    
    fun createSampleChallenges(): List<Challenge> {
        return listOf(
            Challenge(
                id = "challenge_001",
                title = "5-Minute Breathing Exercise",
                description = "Practice deep breathing for 5 minutes to reduce stress and improve focus",
                category = ChallengeCategory.MINDFULNESS,
                difficulty = ChallengeDifficulty.EASY,
                duration = 1,
                xpReward = 25,
                requirements = listOf("Find a quiet space", "Set a timer for 5 minutes"),
                instructions = listOf(
                    "Sit comfortably with your back straight",
                    "Close your eyes and take deep breaths",
                    "Focus on your breathing pattern",
                    "Count each breath from 1 to 10, then repeat"
                )
            ),
            Challenge(
                id = "challenge_002",
                title = "Gratitude List",
                description = "Write down 10 things you're grateful for today",
                category = ChallengeCategory.GRATITUDE,
                difficulty = ChallengeDifficulty.EASY,
                duration = 1,
                xpReward = 30,
                requirements = listOf("Pen and paper or digital device"),
                instructions = listOf(
                    "Take a moment to reflect on your day",
                    "Write down 10 things you're grateful for",
                    "Be specific and detailed in your gratitude",
                    "Read your list aloud to yourself"
                )
            ),
            Challenge(
                id = "challenge_003",
                title = "Nature Walk",
                description = "Take a 20-minute walk in nature to boost your mood and energy",
                category = ChallengeCategory.EXERCISE,
                difficulty = ChallengeDifficulty.MEDIUM,
                duration = 1,
                xpReward = 50,
                requirements = listOf("Comfortable walking shoes", "Weather-appropriate clothing"),
                instructions = listOf(
                    "Choose a natural setting (park, trail, beach)",
                    "Walk at a comfortable pace for 20 minutes",
                    "Pay attention to your surroundings",
                    "Take deep breaths of fresh air"
                )
            )
        )
    }
    
    fun createSampleAffirmations(): List<Affirmation> {
        return listOf(
            Affirmation(
                id = "affirmation_001",
                text = "I am worthy of love and happiness",
                category = AffirmationCategory.SELF_LOVE
            ),
            Affirmation(
                id = "affirmation_002",
                text = "I have the strength to overcome any challenge",
                category = AffirmationCategory.STRENGTH
            ),
            Affirmation(
                id = "affirmation_003",
                text = "I am grateful for all the good in my life",
                category = AffirmationCategory.GRATITUDE
            ),
            Affirmation(
                id = "affirmation_004",
                text = "I am healing and growing every day",
                category = AffirmationCategory.HEALING
            ),
            Affirmation(
                id = "affirmation_005",
                text = "I trust in my ability to make positive changes",
                category = AffirmationCategory.CONFIDENCE
            )
        )
    }
    
    fun createSampleMoodEntries(): List<MoodEntry> {
        return listOf(
            MoodEntry(
                id = "mood_001",
                userId = "user_001",
                mood = 8,
                energy = 7,
                stress = 3,
                anxiety = 2,
                sleep = 8,
                notes = "Feeling great after morning meditation",
                tags = listOf("meditation", "good sleep", "sunny weather"),
                activities = listOf("meditation", "walking"),
                weather = "sunny",
                date = Date()
            ),
            MoodEntry(
                id = "mood_002",
                userId = "user_001",
                mood = 6,
                energy = 5,
                stress = 7,
                anxiety = 6,
                sleep = 6,
                notes = "A bit stressed about work deadlines",
                tags = listOf("work pressure", "time constraints"),
                activities = listOf("work", "coffee"),
                weather = "cloudy",
                date = Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000)
            ),
            MoodEntry(
                id = "mood_003",
                userId = "user_001",
                mood = 9,
                energy = 9,
                stress = 2,
                anxiety = 1,
                sleep = 9,
                notes = "Amazing day! Completed all my goals",
                tags = listOf("goal achievement", "positive feedback", "exercise"),
                activities = listOf("exercise", "goal completion", "socializing"),
                weather = "sunny",
                date = Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000)
            )
        )
    }
}

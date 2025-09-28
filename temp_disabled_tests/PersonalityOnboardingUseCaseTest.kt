package com.serenityai.usecases

import com.serenityai.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class PersonalityOnboardingUseCaseTest {

    private lateinit var personalityOnboardingUseCase: PersonalityOnboardingUseCase

    @BeforeEach
    fun setUp() {
        personalityOnboardingUseCase = PersonalityOnboardingUseCase()
    }

    @Nested
    @DisplayName("Personality Assessment")
    inner class PersonalityAssessment {

        @Test
        @DisplayName("should create personality assessment")
        fun shouldCreatePersonalityAssessment() = runTest {
            val userId = "user123"
            val assessment = personalityOnboardingUseCase.createPersonalityAssessment(userId)

            assertNotNull(assessment)
            assertEquals(userId, assessment.userId)
            assertNotNull(assessment.questions)
            assertTrue(assessment.questions.isNotEmpty())
            assertEquals(AssessmentStatus.NOT_STARTED, assessment.status)
        }

        @Test
        @DisplayName("should generate assessment questions")
        fun shouldGenerateAssessmentQuestions() = runTest {
            val questions = personalityOnboardingUseCase.generateAssessmentQuestions()

            assertNotNull(questions)
            assertTrue(questions.size >= 10) // Should have sufficient questions
            questions.forEach { question ->
                assertTrue(question.text.isNotEmpty())
                assertTrue(question.options.isNotEmpty())
                assertNotNull(question.category)
            }
        }

        @Test
        @DisplayName("should validate assessment answers")
        fun shouldValidateAssessmentAnswers() = runTest {
            val answers = listOf(
                AssessmentAnswer("q1", "option1"),
                AssessmentAnswer("q2", "option2"),
                AssessmentAnswer("q3", "option3")
            )

            val isValid = personalityOnboardingUseCase.validateAssessmentAnswers(answers)
            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject incomplete assessment")
        fun shouldRejectIncompleteAssessment() = runTest {
            val answers = listOf(
                AssessmentAnswer("q1", "option1"),
                AssessmentAnswer("q2", "") // Empty answer
            )

            val isValid = personalityOnboardingUseCase.validateAssessmentAnswers(answers)
            assertFalse(isValid)
        }
    }

    @Nested
    @DisplayName("Personality Analysis")
    inner class PersonalityAnalysis {

        @Test
        @DisplayName("should analyze personality traits")
        fun shouldAnalyzePersonalityTraits() = runTest {
            val answers = listOf(
                AssessmentAnswer("q1", "introvert"),
                AssessmentAnswer("q2", "analytical"),
                AssessmentAnswer("q3", "organized"),
                AssessmentAnswer("q4", "empathetic"),
                AssessmentAnswer("q5", "creative")
            )

            val personalityProfile = personalityOnboardingUseCase.analyzePersonalityTraits(answers)

            assertNotNull(personalityProfile)
            assertNotNull(personalityProfile.traits)
            assertTrue(personalityProfile.traits.isNotEmpty())
        }

        @Test
        @DisplayName("should identify dominant traits")
        fun shouldIdentifyDominantTraits() = runTest {
            val answers = listOf(
                AssessmentAnswer("q1", "introvert"),
                AssessmentAnswer("q2", "introvert"),
                AssessmentAnswer("q3", "introvert"),
                AssessmentAnswer("q4", "analytical"),
                AssessmentAnswer("q5", "analytical")
            )

            val personalityProfile = personalityOnboardingUseCase.analyzePersonalityTraits(answers)
            val dominantTraits = personalityOnboardingUseCase.identifyDominantTraits(personalityProfile)

            assertNotNull(dominantTraits)
            assertTrue(dominantTraits.isNotEmpty())
            assertTrue(dominantTraits.any { it.name.contains("Introvert") })
        }

        @Test
        @DisplayName("should calculate trait scores")
        fun shouldCalculateTraitScores() = runTest {
            val answers = listOf(
                AssessmentAnswer("q1", "introvert"),
                AssessmentAnswer("q2", "introvert"),
                AssessmentAnswer("q3", "extrovert"),
                AssessmentAnswer("q4", "analytical"),
                AssessmentAnswer("q5", "analytical")
            )

            val personalityProfile = personalityOnboardingUseCase.analyzePersonalityTraits(answers)
            val traitScores = personalityOnboardingUseCase.calculateTraitScores(personalityProfile)

            assertNotNull(traitScores)
            assertTrue(traitScores.isNotEmpty())
            traitScores.forEach { (trait, score) ->
                assertTrue(score >= 0.0 && score <= 1.0)
            }
        }
    }

    @Nested
    @DisplayName("Personalization")
    inner class Personalization {

        @Test
        @DisplayName("should generate personalized recommendations")
        fun shouldGeneratePersonalizedRecommendations() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Introversion", 0.8),
                    PersonalityTrait("Analytical", 0.7),
                    PersonalityTrait("Empathy", 0.6)
                ),
                dominantTraits = listOf("Introversion", "Analytical"),
                assessmentDate = Date()
            )

            val recommendations = personalityOnboardingUseCase.generatePersonalizedRecommendations(personalityProfile)

            assertNotNull(recommendations)
            assertTrue(recommendations.isNotEmpty())
            assertTrue(recommendations.any { it.category == "Communication Style" })
            assertTrue(recommendations.any { it.category == "Therapy Approach" })
        }

        @Test
        @DisplayName("should customize UI preferences")
        fun shouldCustomizeUIPreferences() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Introversion", 0.8),
                    PersonalityTrait("Sensitivity", 0.7)
                ),
                dominantTraits = listOf("Introversion"),
                assessmentDate = Date()
            )

            val uiPreferences = personalityOnboardingUseCase.customizeUIPreferences(personalityProfile)

            assertNotNull(uiPreferences)
            assertTrue(uiPreferences.isNotEmpty())
            assertTrue(uiPreferences.containsKey("theme"))
            assertTrue(uiPreferences.containsKey("notification_style"))
        }

        @Test
        @DisplayName("should suggest coping strategies")
        fun shouldSuggestCopingStrategies() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Anxiety", 0.8),
                    PersonalityTrait("Perfectionism", 0.7)
                ),
                dominantTraits = listOf("Anxiety"),
                assessmentDate = Date()
            )

            val copingStrategies = personalityOnboardingUseCase.suggestCopingStrategies(personalityProfile)

            assertNotNull(copingStrategies)
            assertTrue(copingStrategies.isNotEmpty())
            assertTrue(copingStrategies.any { it.contains("breathing") })
            assertTrue(copingStrategies.any { it.contains("mindfulness") })
        }
    }

    @Nested
    @DisplayName("Onboarding Flow")
    inner class OnboardingFlow {

        @Test
        @DisplayName("should complete onboarding flow")
        fun shouldCompleteOnboardingFlow() = runTest {
            val userId = "user123"
            val answers = listOf(
                AssessmentAnswer("q1", "introvert"),
                AssessmentAnswer("q2", "analytical"),
                AssessmentAnswer("q3", "organized"),
                AssessmentAnswer("q4", "empathetic"),
                AssessmentAnswer("q5", "creative")
            )

            val result = personalityOnboardingUseCase.completeOnboardingFlow(userId, answers)

            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
        }

        @Test
        @DisplayName("should handle onboarding failure")
        fun shouldHandleOnboardingFailure() = runTest {
            val userId = "user123"
            val answers = listOf<AssessmentAnswer>() // Empty answers

            val result = personalityOnboardingUseCase.completeOnboardingFlow(userId, answers)

            assertTrue(result.isFailure)
        }

        @Test
        @DisplayName("should track onboarding progress")
        fun shouldTrackOnboardingProgress() = runTest {
            val userId = "user123"
            val assessment = personalityOnboardingUseCase.createPersonalityAssessment(userId)
            val progress = personalityOnboardingUseCase.trackOnboardingProgress(assessment)

            assertNotNull(progress)
            assertTrue(progress >= 0.0 && progress <= 1.0)
        }
    }

    @Nested
    @DisplayName("User Experience Customization")
    inner class UserExperienceCustomization {

        @Test
        @DisplayName("should customize chat experience")
        fun shouldCustomizeChatExperience() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Introversion", 0.8),
                    PersonalityTrait("Directness", 0.6)
                ),
                dominantTraits = listOf("Introversion"),
                assessmentDate = Date()
            )

            val chatCustomization = personalityOnboardingUseCase.customizeChatExperience(personalityProfile)

            assertNotNull(chatCustomization)
            assertTrue(chatCustomization.containsKey("response_style"))
            assertTrue(chatCustomization.containsKey("interaction_frequency"))
        }

        @Test
        @DisplayName("should personalize mood tracking")
        fun shouldPersonalizeMoodTracking() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Detail-oriented", 0.8),
                    PersonalityTrait("Emotional awareness", 0.7)
                ),
                dominantTraits = listOf("Detail-oriented"),
                assessmentDate = Date()
            )

            val moodTrackingCustomization = personalityOnboardingUseCase.personalizeMoodTracking(personalityProfile)

            assertNotNull(moodTrackingCustomization)
            assertTrue(moodTrackingCustomization.containsKey("tracking_frequency"))
            assertTrue(moodTrackingCustomization.containsKey("detail_level"))
        }

        @Test
        @DisplayName("should customize notification preferences")
        fun shouldCustomizeNotificationPreferences() = runTest {
            val personalityProfile = PersonalityProfile(
                userId = "user123",
                traits = listOf(
                    PersonalityTrait("Sensitivity", 0.8),
                    PersonalityTrait("Distractibility", 0.6)
                ),
                dominantTraits = listOf("Sensitivity"),
                assessmentDate = Date()
            )

            val notificationPreferences = personalityOnboardingUseCase.customizeNotificationPreferences(personalityProfile)

            assertNotNull(notificationPreferences)
            assertTrue(notificationPreferences.containsKey("frequency"))
            assertTrue(notificationPreferences.containsKey("tone"))
        }
    }

    @Nested
    @DisplayName("Assessment Categories")
    inner class AssessmentCategories {

        @Test
        @DisplayName("should categorize questions by personality dimension")
        fun shouldCategorizeQuestionsByPersonalityDimension() = runTest {
            val questions = personalityOnboardingUseCase.generateAssessmentQuestions()
            val categories = questions.map { it.category }.distinct()

            assertTrue(categories.contains("Extraversion"))
            assertTrue(categories.contains("Openness"))
            assertTrue(categories.contains("Conscientiousness"))
            assertTrue(categories.contains("Agreeableness"))
            assertTrue(categories.contains("Neuroticism"))
        }

        @Test
        @DisplayName("should balance question distribution")
        fun shouldBalanceQuestionDistribution() = runTest {
            val questions = personalityOnboardingUseCase.generateAssessmentQuestions()
            val categoryCounts = questions.groupBy { it.category }.mapValues { it.value.size }

            categoryCounts.forEach { (category, count) ->
                assertTrue(count >= 2, "Category $category should have at least 2 questions")
            }
        }
    }
}

/**
 * Assessment data classes
 */
data class PersonalityAssessment(
    val assessmentId: String,
    val userId: String,
    val questions: List<AssessmentQuestion>,
    val status: AssessmentStatus,
    val createdAt: Date
)

data class AssessmentQuestion(
    val questionId: String,
    val text: String,
    val options: List<String>,
    val category: String
)

data class AssessmentAnswer(
    val questionId: String,
    val selectedOption: String
)

data class PersonalityProfile(
    val userId: String,
    val traits: List<PersonalityTrait>,
    val dominantTraits: List<String>,
    val assessmentDate: Date
)

data class PersonalityTrait(
    val name: String,
    val score: Double
)

data class PersonalizedRecommendation(
    val category: String,
    val title: String,
    val description: String,
    val priority: Int
)

enum class AssessmentStatus {
    NOT_STARTED, IN_PROGRESS, COMPLETED
}

/**
 * Use Case implementation for Personality Onboarding
 */
class PersonalityOnboardingUseCase {
    
    suspend fun createPersonalityAssessment(userId: String): PersonalityAssessment {
        val questions = generateAssessmentQuestions()
        return PersonalityAssessment(
            assessmentId = UUID.randomUUID().toString(),
            userId = userId,
            questions = questions,
            status = AssessmentStatus.NOT_STARTED,
            createdAt = Date()
        )
    }

    fun generateAssessmentQuestions(): List<AssessmentQuestion> {
        return listOf(
            AssessmentQuestion("q1", "I prefer quiet environments over busy ones", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Extraversion"),
            AssessmentQuestion("q2", "I enjoy trying new experiences and activities", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Openness"),
            AssessmentQuestion("q3", "I like to plan things in advance", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Conscientiousness"),
            AssessmentQuestion("q4", "I care about others' feelings", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Agreeableness"),
            AssessmentQuestion("q5", "I often feel anxious or worried", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Neuroticism"),
            AssessmentQuestion("q6", "I feel energized around large groups of people", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Extraversion"),
            AssessmentQuestion("q7", "I enjoy creative and artistic activities", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Openness"),
            AssessmentQuestion("q8", "I am organized and detail-oriented", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Conscientiousness"),
            AssessmentQuestion("q9", "I trust others easily", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Agreeableness"),
            AssessmentQuestion("q10", "I handle stress well", listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree"), "Neuroticism")
        )
    }

    fun validateAssessmentAnswers(answers: List<AssessmentAnswer>): Boolean {
        return answers.isNotEmpty() && answers.all { it.selectedOption.isNotEmpty() }
    }

    suspend fun analyzePersonalityTraits(answers: List<AssessmentAnswer>): PersonalityProfile {
        val traits = mutableListOf<PersonalityTrait>()
        
        // Analyze Extraversion
        val extraversionAnswers = answers.filter { it.questionId in listOf("q1", "q6") }
        val extraversionScore = calculateTraitScore(extraversionAnswers)
        traits.add(PersonalityTrait("Extraversion", extraversionScore))
        
        // Analyze Openness
        val opennessAnswers = answers.filter { it.questionId in listOf("q2", "q7") }
        val opennessScore = calculateTraitScore(opennessAnswers)
        traits.add(PersonalityTrait("Openness", opennessScore))
        
        // Analyze Conscientiousness
        val conscientiousnessAnswers = answers.filter { it.questionId in listOf("q3", "q8") }
        val conscientiousnessScore = calculateTraitScore(conscientiousnessAnswers)
        traits.add(PersonalityTrait("Conscientiousness", conscientiousnessScore))
        
        // Analyze Agreeableness
        val agreeablenessAnswers = answers.filter { it.questionId in listOf("q4", "q9") }
        val agreeablenessScore = calculateTraitScore(agreeablenessAnswers)
        traits.add(PersonalityTrait("Agreeableness", agreeablenessScore))
        
        // Analyze Neuroticism
        val neuroticismAnswers = answers.filter { it.questionId in listOf("q5", "q10") }
        val neuroticismScore = calculateTraitScore(neuroticismAnswers)
        traits.add(PersonalityTrait("Neuroticism", neuroticismScore))
        
        val dominantTraits = identifyDominantTraits(PersonalityProfile("", traits, emptyList(), Date()))
        
        return PersonalityProfile(
            userId = "",
            traits = traits,
            dominantTraits = dominantTraits,
            assessmentDate = Date()
        )
    }

    fun identifyDominantTraits(profile: PersonalityProfile): List<String> {
        return profile.traits
            .sortedByDescending { it.score }
            .take(2)
            .map { it.name }
    }

    fun calculateTraitScores(profile: PersonalityProfile): Map<String, Double> {
        return profile.traits.associate { it.name to it.score }
    }

    fun generatePersonalizedRecommendations(profile: PersonalityProfile): List<PersonalizedRecommendation> {
        val recommendations = mutableListOf<PersonalizedRecommendation>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Introversion" -> {
                    recommendations.add(PersonalizedRecommendation(
                        "Communication Style",
                        "Prefer Written Communication",
                        "You may prefer text-based therapy sessions over voice calls",
                        1
                    ))
                }
                "Analytical" -> {
                    recommendations.add(PersonalizedRecommendation(
                        "Therapy Approach",
                        "Data-Driven Therapy",
                        "You might benefit from mood tracking and analytics",
                        1
                    ))
                }
                "Empathy" -> {
                    recommendations.add(PersonalizedRecommendation(
                        "Support Style",
                        "Emotional Support Focus",
                        "You may benefit from emotional validation and support",
                        2
                    ))
                }
            }
        }
        
        return recommendations
    }

    fun customizeUIPreferences(profile: PersonalityProfile): Map<String, String> {
        val preferences = mutableMapOf<String, String>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Introversion" -> {
                    preferences["theme"] = "dark"
                    preferences["notification_style"] = "subtle"
                }
                "Sensitivity" -> {
                    preferences["theme"] = "soft"
                    preferences["notification_style"] = "gentle"
                }
            }
        }
        
        return preferences
    }

    fun suggestCopingStrategies(profile: PersonalityProfile): List<String> {
        val strategies = mutableListOf<String>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Anxiety" -> {
                    strategies.add("Practice deep breathing exercises")
                    strategies.add("Try progressive muscle relaxation")
                    strategies.add("Use grounding techniques")
                }
                "Perfectionism" -> {
                    strategies.add("Practice self-compassion")
                    strategies.add("Set realistic goals")
                    strategies.add("Embrace imperfection")
                }
            }
        }
        
        return strategies
    }

    suspend fun completeOnboardingFlow(userId: String, answers: List<AssessmentAnswer>): Result<PersonalityProfile> {
        return try {
            if (!validateAssessmentAnswers(answers)) {
                return Result.failure(Exception("Invalid assessment answers"))
            }
            
            val profile = analyzePersonalityTraits(answers)
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun trackOnboardingProgress(assessment: PersonalityAssessment): Double {
        return when (assessment.status) {
            AssessmentStatus.NOT_STARTED -> 0.0
            AssessmentStatus.IN_PROGRESS -> 0.5
            AssessmentStatus.COMPLETED -> 1.0
        }
    }

    fun customizeChatExperience(profile: PersonalityProfile): Map<String, String> {
        val customization = mutableMapOf<String, String>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Introversion" -> {
                    customization["response_style"] = "thoughtful"
                    customization["interaction_frequency"] = "low"
                }
                "Directness" -> {
                    customization["response_style"] = "direct"
                    customization["interaction_frequency"] = "medium"
                }
            }
        }
        
        return customization
    }

    fun personalizeMoodTracking(profile: PersonalityProfile): Map<String, String> {
        val customization = mutableMapOf<String, String>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Detail-oriented" -> {
                    customization["tracking_frequency"] = "daily"
                    customization["detail_level"] = "high"
                }
                "Emotional awareness" -> {
                    customization["tracking_frequency"] = "multiple_daily"
                    customization["detail_level"] = "medium"
                }
            }
        }
        
        return customization
    }

    fun customizeNotificationPreferences(profile: PersonalityProfile): Map<String, String> {
        val preferences = mutableMapOf<String, String>()
        
        profile.dominantTraits.forEach { trait ->
            when (trait) {
                "Sensitivity" -> {
                    preferences["frequency"] = "low"
                    preferences["tone"] = "gentle"
                }
                "Distractibility" -> {
                    preferences["frequency"] = "medium"
                    preferences["tone"] = "clear"
                }
            }
        }
        
        return preferences
    }

    private fun calculateTraitScore(answers: List<AssessmentAnswer>): Double {
        if (answers.isEmpty()) return 0.5
        
        val scoreMap = mapOf(
            "Strongly Agree" to 1.0,
            "Agree" to 0.8,
            "Neutral" to 0.5,
            "Disagree" to 0.2,
            "Strongly Disagree" to 0.0
        )
        
        val totalScore = answers.sumOf { scoreMap[it.selectedOption] ?: 0.5 }
        return totalScore / answers.size
    }
}

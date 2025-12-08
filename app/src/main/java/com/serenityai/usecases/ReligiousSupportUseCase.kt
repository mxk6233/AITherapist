package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC40: Religious support by person's religion providing faith-based guidance and religious resources tailored to the user's specific religion. */
class ReligiousSupportUseCase {
    
    private val userReligiousPreferences = mutableMapOf<String, Religion>() // userId -> religion
    private val religiousResources = mutableMapOf<Religion, MutableList<ReligiousResource>>()
    private val prayerRequests = mutableMapOf<String, MutableList<PrayerRequest>>() // userId -> requests
    private val spiritualGuidance = mutableMapOf<String, MutableList<SpiritualGuidance>>() // userId -> guidance
    
    init {
        // Initialize religious resources for each religion
        initializeReligiousResources()
    }
    
    /**
     * Sets user's religious preference
     * 
     * @param userId User ID
     * @param religion Religion preference
     */
    fun setReligiousPreference(userId: String, religion: Religion) {
        require(userId.isNotBlank()) { "User ID cannot be empty" }
        userReligiousPreferences[userId] = religion
    }
    
    /**
     * Gets user's religious preference
     * 
     * @param userId User ID
     * @return Religion preference or null if not set
     */
    fun getReligiousPreference(userId: String): Religion? {
        return userReligiousPreferences[userId]
    }
    
    /**
     * Provides faith-based guidance based on user's religion
     * 
     * @param userId User ID
     * @param context Context or situation for guidance
     * @return SpiritualGuidance object
     */
    fun provideSpiritualGuidance(userId: String, context: String): SpiritualGuidance {
        require(context.isNotBlank()) { "Context cannot be empty" }
        
        val religion = userReligiousPreferences[userId] 
            ?: throw IllegalArgumentException("User religious preference not set")
        
        val guidance = generateGuidance(religion, context)
        
        val guidanceList = spiritualGuidance[userId] ?: mutableListOf()
        guidanceList.add(guidance)
        spiritualGuidance[userId] = guidanceList
        
        return guidance
    }
    
    /**
     * Gets religious resources for user's religion
     * 
     * @param userId User ID
     * @param category Optional resource category filter
     * @return List of ReligiousResource objects
     */
    fun getReligiousResources(userId: String, category: ResourceCategory? = null): List<ReligiousResource> {
        val religion = userReligiousPreferences[userId] 
            ?: throw IllegalArgumentException("User religious preference not set")
        
        val resources = religiousResources[religion] ?: emptyList()
        
        return if (category != null) {
            resources.filter { it.category == category }
        } else {
            resources
        }
    }
    
    /**
     * Submits a prayer request
     * 
     * @param userId User ID
     * @param request Prayer request content
     * @param isPrivate Whether the request is private
     * @return PrayerRequest object
     */
    fun submitPrayerRequest(
        userId: String,
        request: String,
        isPrivate: Boolean = true
    ): PrayerRequest {
        require(request.isNotBlank()) { "Prayer request cannot be empty" }
        
        val religion = userReligiousPreferences[userId] 
            ?: throw IllegalArgumentException("User religious preference not set")
        
        val prayerRequest = PrayerRequest(
            id = "prayer_${System.currentTimeMillis()}",
            userId = userId,
            religion = religion,
            request = request,
            isPrivate = isPrivate,
            status = PrayerStatus.PENDING,
            createdAt = Date()
        )
        
        val requests = prayerRequests[userId] ?: mutableListOf()
        requests.add(prayerRequest)
        prayerRequests[userId] = requests
        
        return prayerRequest
    }
    
    /**
     * Gets user's prayer requests
     * 
     * @param userId User ID
     * @return List of PrayerRequest objects
     */
    fun getPrayerRequests(userId: String): List<PrayerRequest> {
        return prayerRequests[userId]?.sortedByDescending { it.createdAt } ?: emptyList()
    }
    
    /**
     * Gets spiritual guidance history for user
     * 
     * @param userId User ID
     * @return List of SpiritualGuidance objects
     */
    fun getSpiritualGuidanceHistory(userId: String): List<SpiritualGuidance> {
        return spiritualGuidance[userId]?.sortedByDescending { it.createdAt } ?: emptyList()
    }
    
    /**
     * Provides daily religious reflection based on user's religion
     * 
     * @param userId User ID
     * @return Daily reflection text
     */
    fun getDailyReflection(userId: String): String {
        val religion = userReligiousPreferences[userId] 
            ?: throw IllegalArgumentException("User religious preference not set")
        
        return when (religion) {
            Religion.CHRISTIANITY -> "Today, remember that you are loved and valued. Take time for prayer and reflection on the blessings in your life."
            Religion.ISLAM -> "As-Salamu Alaykum. Today, seek peace and guidance through prayer. Remember that Allah is with you in every moment."
            Religion.JUDAISM -> "Shalom. Today, reflect on gratitude and the teachings that guide you. May you find strength in your faith."
            Religion.BUDDHISM -> "Today, practice mindfulness and compassion. Remember that suffering is temporary and inner peace is within reach."
            Religion.HINDUISM -> "Namaste. Today, honor the divine within yourself and others. Practice dharma and seek inner harmony."
            Religion.SIKHISM -> "Sat Sri Akal. Today, remember Waheguru's presence in all things. Practice seva (selfless service) and meditation."
            Religion.OTHER -> "Today, connect with your spiritual beliefs. Take time for reflection and find peace in your faith tradition."
            Religion.NONE -> throw IllegalArgumentException("User has no religious preference set")
        }
    }
    
    /**
     * Provides religious quotes based on user's religion
     * 
     * @param userId User ID
     * @param topic Optional topic for quote
     * @return Religious quote
     */
    fun getReligiousQuote(userId: String, topic: String? = null): String {
        val religion = userReligiousPreferences[userId] 
            ?: throw IllegalArgumentException("User religious preference not set")
        
        val quotes = getQuotesForReligion(religion, topic)
        return quotes.random()
    }
    
    /**
     * Generates guidance based on religion and context
     */
    private fun generateGuidance(religion: Religion, context: String): SpiritualGuidance {
        val guidanceText = when (religion) {
            Religion.CHRISTIANITY -> generateChristianGuidance(context)
            Religion.ISLAM -> generateIslamicGuidance(context)
            Religion.JUDAISM -> generateJewishGuidance(context)
            Religion.BUDDHISM -> generateBuddhistGuidance(context)
            Religion.HINDUISM -> generateHinduGuidance(context)
            Religion.SIKHISM -> generateSikhGuidance(context)
            Religion.OTHER -> generateGenericGuidance(context)
            Religion.NONE -> throw IllegalArgumentException("Invalid religion")
        }
        
        return SpiritualGuidance(
            id = "guidance_${System.currentTimeMillis()}",
            religion = religion,
            guidance = guidanceText,
            context = context,
            createdAt = Date()
        )
    }
    
    private fun generateChristianGuidance(context: String): String {
        return "In this moment of $context, remember that you are never alone. " +
                "Take comfort in prayer and know that God's love surrounds you. " +
                "Consider reading scripture or spending time in quiet reflection."
    }
    
    private fun generateIslamicGuidance(context: String): String {
        return "As you navigate $context, remember that Allah is Al-Rahman (The Most Merciful). " +
                "Turn to prayer (Salah) and seek guidance through the Quran. " +
                "Trust in Allah's plan and find peace in submission to His will."
    }
    
    private fun generateJewishGuidance(context: String): String {
        return "In facing $context, draw strength from Jewish teachings and community. " +
                "Consider the wisdom of the Torah and the support of your community. " +
                "Remember that tikkun olam (repairing the world) begins with caring for yourself."
    }
    
    private fun generateBuddhistGuidance(context: String): String {
        return "As you experience $context, practice mindfulness and compassion. " +
                "Remember the Four Noble Truths and the Eightfold Path. " +
                "Meditation can help you find inner peace and clarity."
    }
    
    private fun generateHinduGuidance(context: String): String {
        return "In this time of $context, remember your dharma (duty) and seek inner peace. " +
                "Practice yoga and meditation to connect with the divine within. " +
                "Trust in the cycle of karma and the path to moksha (liberation)."
    }
    
    private fun generateSikhGuidance(context: String): String {
        return "As you face $context, remember Waheguru's presence. " +
                "Practice meditation (Simran) and selfless service (Seva). " +
                "Draw strength from the Guru Granth Sahib and the Sikh community."
    }
    
    private fun generateGenericGuidance(context: String): String {
        return "In this moment of $context, connect with your spiritual beliefs. " +
                "Take time for reflection, prayer, or meditation as appropriate to your faith. " +
                "Remember that spiritual support is available to you."
    }
    
    private fun getQuotesForReligion(religion: Religion, topic: String?): List<String> {
        val quotes = when (religion) {
            Religion.CHRISTIANITY -> listOf(
                "For I know the plans I have for you, declares the Lord, plans to prosper you and not to harm you, plans to give you hope and a future. - Jeremiah 29:11",
                "Cast all your anxiety on him because he cares for you. - 1 Peter 5:7",
                "I can do all this through him who gives me strength. - Philippians 4:13"
            )
            Religion.ISLAM -> listOf(
                "And whoever relies upon Allah - then He is sufficient for him. - Quran 65:3",
                "Verily, with hardship comes ease. - Quran 94:5",
                "Allah does not burden a soul beyond that it can bear. - Quran 2:286"
            )
            Religion.JUDAISM -> listOf(
                "The Lord is my shepherd; I shall not want. - Psalm 23:1",
                "Be strong and courageous. Do not be afraid; do not be discouraged, for the Lord your God will be with you wherever you go. - Joshua 1:9",
                "This too shall pass. - Jewish Proverb"
            )
            Religion.BUDDHISM -> listOf(
                "Peace comes from within. Do not seek it without. - Buddha",
                "You yourself, as much as anybody in the entire universe, deserve your love and affection. - Buddha",
                "The mind is everything. What you think you become. - Buddha"
            )
            Religion.HINDUISM -> listOf(
                "You are what your deep, driving desire is. As your desire is, so is your will. As your will is, so is your deed. - Upanishads",
                "The soul is neither born, and nor does it die. - Bhagavad Gita",
                "You have the right to work, but never to the fruit of work. - Bhagavad Gita"
            )
            Religion.SIKHISM -> listOf(
                "Without the Guru, none have crossed over. - Guru Granth Sahib",
                "By the karma of past actions, the robe of this physical body is obtained. - Guru Granth Sahib",
                "One who calls himself a Sikh of the True Guru shall rise in the early morning hours and meditate on the Lord's Name. - Guru Granth Sahib"
            )
            Religion.OTHER -> listOf(
                "Faith is taking the first step even when you don't see the whole staircase. - Martin Luther King Jr.",
                "The best way out is always through. - Robert Frost",
                "You are braver than you believe, stronger than you seem, and smarter than you think. - A.A. Milne"
            )
            Religion.NONE -> emptyList()
        }
        
        return if (topic != null) {
            quotes.filter { it.contains(topic, ignoreCase = true) }
        } else {
            quotes
        }
    }
    
    /**
     * Initializes religious resources for each religion
     */
    private fun initializeReligiousResources() {
        // Christianity resources
        religiousResources[Religion.CHRISTIANITY] = mutableListOf(
            ReligiousResource(
                id = "chr_001",
                title = "Daily Bible Reading",
                description = "Read daily scripture passages",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.CHRISTIANITY
            ),
            ReligiousResource(
                id = "chr_002",
                title = "Prayer Guide",
                description = "Guidance on Christian prayer practices",
                category = ResourceCategory.PRAYER,
                religion = Religion.CHRISTIANITY
            )
        )
        
        // Islam resources
        religiousResources[Religion.ISLAM] = mutableListOf(
            ReligiousResource(
                id = "isl_001",
                title = "Quran Reading",
                description = "Daily Quran verses and reflections",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.ISLAM
            ),
            ReligiousResource(
                id = "isl_002",
                title = "Salah Guide",
                description = "Guide to performing daily prayers",
                category = ResourceCategory.PRAYER,
                religion = Religion.ISLAM
            )
        )
        
        // Judaism resources
        religiousResources[Religion.JUDAISM] = mutableListOf(
            ReligiousResource(
                id = "jud_001",
                title = "Torah Study",
                description = "Weekly Torah portions and commentary",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.JUDAISM
            ),
            ReligiousResource(
                id = "jud_002",
                title = "Prayer Resources",
                description = "Jewish prayer guides and resources",
                category = ResourceCategory.PRAYER,
                religion = Religion.JUDAISM
            )
        )
        
        // Buddhism resources
        religiousResources[Religion.BUDDHISM] = mutableListOf(
            ReligiousResource(
                id = "bud_001",
                title = "Dharma Teachings",
                description = "Buddhist teachings and sutras",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.BUDDHISM
            ),
            ReligiousResource(
                id = "bud_002",
                title = "Meditation Guide",
                description = "Buddhist meditation practices",
                category = ResourceCategory.MEDITATION,
                religion = Religion.BUDDHISM
            )
        )
        
        // Hinduism resources
        religiousResources[Religion.HINDUISM] = mutableListOf(
            ReligiousResource(
                id = "hin_001",
                title = "Bhagavad Gita Study",
                description = "Teachings from the Bhagavad Gita",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.HINDUISM
            ),
            ReligiousResource(
                id = "hin_002",
                title = "Yoga and Meditation",
                description = "Hindu spiritual practices",
                category = ResourceCategory.MEDITATION,
                religion = Religion.HINDUISM
            )
        )
        
        // Sikhism resources
        religiousResources[Religion.SIKHISM] = mutableListOf(
            ReligiousResource(
                id = "sik_001",
                title = "Guru Granth Sahib",
                description = "Teachings from the Guru Granth Sahib",
                category = ResourceCategory.SCRIPTURE,
                religion = Religion.SIKHISM
            ),
            ReligiousResource(
                id = "sik_002",
                title = "Simran and Seva",
                description = "Sikh meditation and service practices",
                category = ResourceCategory.MEDITATION,
                religion = Religion.SIKHISM
            )
        )
    }
}

/**
 * Enum for religions
 */
enum class Religion {
    CHRISTIANITY,
    ISLAM,
    JUDAISM,
    BUDDHISM,
    HINDUISM,
    SIKHISM,
    OTHER,
    NONE
}

/**
 * Enum for resource categories
 */
enum class ResourceCategory {
    SCRIPTURE,
    PRAYER,
    MEDITATION,
    TEACHING,
    COMMUNITY,
    RITUAL
}

/**
 * Data class for religious resources
 */
data class ReligiousResource(
    val id: String,
    val title: String,
    val description: String,
    val category: ResourceCategory,
    val religion: Religion,
    val url: String? = null,
    val isRecommended: Boolean = false
)

/**
 * Data class for prayer requests
 */
data class PrayerRequest(
    val id: String,
    val userId: String,
    val religion: Religion,
    val request: String,
    val isPrivate: Boolean = true,
    val status: PrayerStatus = PrayerStatus.PENDING,
    val createdAt: Date
)

enum class PrayerStatus {
    PENDING,
    ANSWERED,
    ARCHIVED
}

/**
 * Data class for spiritual guidance
 */
data class SpiritualGuidance(
    val id: String,
    val religion: Religion,
    val guidance: String,
    val context: String,
    val createdAt: Date
)


package com.serenityai.data.models

import java.util.Date

data class Badge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val icon: String = "",
    val category: BadgeCategory = BadgeCategory.ACHIEVEMENT,
    val rarity: BadgeRarity = BadgeRarity.COMMON,
    val xpValue: Int = 0,
    val requirements: List<String> = emptyList()
)

enum class BadgeCategory {
    ACHIEVEMENT,
    STREAK,
    CHALLENGE,
    MOOD,
    JOURNAL,
    SOCIAL,
    MILESTONE
}

enum class BadgeRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY
}

data class UserBadge(
    val id: String = "",
    val userId: String = "",
    val badgeId: String = "",
    val earnedAt: Date = Date(),
    val isDisplayed: Boolean = true
)

data class XPTransaction(
    val id: String = "",
    val userId: String = "",
    val amount: Int = 0,
    val source: XPSource = XPSource.CHALLENGE,
    val description: String = "",
    val createdAt: Date = Date()
)

enum class XPSource {
    CHALLENGE,
    JOURNAL,
    MOOD_LOG,
    STREAK,
    BADGE,
    BONUS
}

data class Affirmation(
    val id: String = "",
    val text: String = "",
    val category: AffirmationCategory = AffirmationCategory.GENERAL,
    val isActive: Boolean = true,
    val createdAt: Date = Date()
)

enum class AffirmationCategory {
    GENERAL,
    SELF_LOVE,
    CONFIDENCE,
    GRATITUDE,
    MOTIVATION,
    HEALING,
    PEACE,
    STRENGTH
}

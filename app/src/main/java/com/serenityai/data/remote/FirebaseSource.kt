package com.serenityai.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.serenityai.usecases.EducationalResource
import com.serenityai.usecases.LearningProgress
import kotlinx.coroutines.tasks.await
import java.util.Date

/**
 * Firebase data source for educational resources and learning progress.
 * Currently implements only methods that are actively used in the application.
 */
class FirebaseSource {
    private val firestore = FirebaseFirestore.getInstance()

    // Educational Resources operations
    suspend fun getEducationalResources(
        category: String? = null,
        format: String? = null
    ): Result<List<EducationalResource>> {
        return try {
            var query: Query = firestore.collection("educational_resources")
            
            // Apply filters
            if (category != null) {
                query = query.whereEqualTo("category", category)
            }
            if (format != null) {
                query = query.whereEqualTo("format", format)
            }
            
            val snapshot = query.get().await()
            val resources = snapshot.documents.mapNotNull { doc ->
                try {
                    EducationalResource(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        category = doc.getString("category") ?: "",
                        format = com.serenityai.usecases.ContentFormat.valueOf(
                            doc.getString("format") ?: "TEXT"
                        ),
                        url = doc.getString("url") ?: "",
                        duration = doc.getLong("duration")?.toInt() ?: 0,
                        tags = (doc.get("tags") as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                        difficulty = com.serenityai.usecases.DifficultyLevel.valueOf(
                            doc.getString("difficulty") ?: "BEGINNER"
                        ),
                        relevanceScore = (doc.getDouble("relevanceScore")?.toFloat()) ?: 0f,
                        createdAt = doc.getDate("createdAt") ?: Date()
                    )
                } catch (e: Exception) {
                    null
                }
            }
            Result.success(resources)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchEducationalResources(query: String): Result<List<EducationalResource>> {
        return try {
            val snapshot = firestore.collection("educational_resources")
                .get()
                .await()
            
            val lowerQuery = query.lowercase()
            val resources = snapshot.documents.mapNotNull { doc ->
                try {
                    val title = doc.getString("title") ?: ""
                    val description = doc.getString("description") ?: ""
                    val tags = (doc.get("tags") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
                    
                    // Check if query matches title, description, or tags
                    val matches = title.lowercase().contains(lowerQuery) ||
                            description.lowercase().contains(lowerQuery) ||
                            tags.any { it.lowercase().contains(lowerQuery) }
                    
                    if (matches) {
                        EducationalResource(
                            id = doc.id,
                            title = title,
                            description = description,
                            category = doc.getString("category") ?: "",
                            format = com.serenityai.usecases.ContentFormat.valueOf(
                                doc.getString("format") ?: "TEXT"
                            ),
                            url = doc.getString("url") ?: "",
                            duration = doc.getLong("duration")?.toInt() ?: 0,
                            tags = tags,
                            difficulty = com.serenityai.usecases.DifficultyLevel.valueOf(
                                doc.getString("difficulty") ?: "BEGINNER"
                            ),
                            relevanceScore = (doc.getDouble("relevanceScore")?.toFloat()) ?: 0f,
                            createdAt = doc.getDate("createdAt") ?: Date()
                        )
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
            Result.success(resources)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getResourceCategories(): Result<List<String>> {
        return try {
            val snapshot = firestore.collection("educational_resources")
                .get()
                .await()
            
            val categories = snapshot.documents
                .mapNotNull { it.getString("category") }
                .distinct()
                .sorted()
            
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Learning Progress operations
    suspend fun saveLearningProgress(progress: LearningProgress): Result<LearningProgress> {
        return try {
            val progressData = hashMapOf(
                "userId" to progress.userId,
                "resourceId" to progress.resourceId,
                "progress" to progress.progress,
                "timeSpent" to progress.timeSpent,
                "completedAt" to progress.completedAt,
                "notes" to progress.notes,
                "updatedAt" to Date()
            )
            
            val savedProgress = if (progress.id.isNotEmpty() && progress.id != "0") {
                // Update existing progress
                firestore.collection("learning_progress")
                    .document(progress.id)
                    .set(progressData)
                    .await()
                progress
            } else {
                // Create new progress
                val docRef = firestore.collection("learning_progress")
                    .add(progressData)
                    .await()
                progress.copy(id = docRef.id)
            }
            
            Result.success(savedProgress)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLearningHistory(userId: String): Result<List<LearningProgress>> {
        return try {
            val snapshot = firestore.collection("learning_progress")
                .whereEqualTo("userId", userId)
                .orderBy("updatedAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            
            val progressList = snapshot.documents.mapNotNull { doc ->
                try {
                    LearningProgress(
                        id = doc.id,
                        userId = doc.getString("userId") ?: "",
                        resourceId = doc.getString("resourceId") ?: "",
                        completedAt = doc.getDate("completedAt"),
                        progress = (doc.getLong("progress")?.toInt()) ?: 0,
                        timeSpent = (doc.getDouble("timeSpent")?.toFloat()) ?: 0f,
                        notes = doc.getString("notes")
                    )
                } catch (e: Exception) {
                    null
                }
            }
            Result.success(progressList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLearningProgress(userId: String, resourceId: String): Result<LearningProgress?> {
        return try {
            val snapshot = firestore.collection("learning_progress")
                .whereEqualTo("userId", userId)
                .whereEqualTo("resourceId", resourceId)
                .limit(1)
                .get()
                .await()
            
            if (snapshot.isEmpty) {
                Result.success(null)
            } else {
                val doc = snapshot.documents.first()
                val progress = LearningProgress(
                    id = doc.id,
                    userId = doc.getString("userId") ?: "",
                    resourceId = doc.getString("resourceId") ?: "",
                    completedAt = doc.getDate("completedAt"),
                    progress = (doc.getLong("progress")?.toInt()) ?: 0,
                    timeSpent = (doc.getDouble("timeSpent")?.toFloat()) ?: 0f,
                    notes = doc.getString("notes")
                )
                Result.success(progress)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
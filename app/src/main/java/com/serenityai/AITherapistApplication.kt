package com.serenityai

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings

/**
 * Application class for AI Therapist app.
 * Initializes Firebase and configures Firestore settings.
 */
class AITherapistApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        initializeFirebase()
    }
    
    /**
     * Initializes Firebase and configures Firestore settings for production use.
     * Uses the new LocalCacheSettings API instead of deprecated methods.
     */
    private fun initializeFirebase() {
        // Initialize Firebase (this reads google-services.json automatically)
        FirebaseApp.initializeApp(this)
        
        // Configure Firestore settings for production with offline persistence
        val firestore = FirebaseFirestore.getInstance()
        
        // Use new PersistentCacheSettings API (replaces deprecated setPersistenceEnabled/setCacheSizeBytes)
        // Set cache size to 100MB (100 * 1024 * 1024 bytes) - adjust as needed
        // For unlimited-like behavior, use a large value like 400MB
        val cacheSettings = PersistentCacheSettings.newBuilder()
            .setSizeBytes(400 * 1024 * 1024L) // 400MB cache size
            .build()
        
        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(cacheSettings)
            .build()
        
        firestore.firestoreSettings = settings
    }
}


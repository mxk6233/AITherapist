# Firebase Connection Setup Guide

## Overview

This guide will help you connect the AI Therapist app to a real Firebase instance. The app is now configured to use Firebase Firestore for educational resources and learning progress.

## Prerequisites

- Android Studio installed
- A Google account
- Firebase project access

## Step-by-Step Setup

### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Add project"** or select an existing project
3. Enter project name (e.g., "AI Therapist")
4. Follow the setup wizard:
   - Enable Google Analytics (optional but recommended)
   - Select or create a Google Analytics account
5. Click **"Create project"**

### Step 2: Add Android App to Firebase

1. In Firebase Console, click the **Android icon** (or "Add app")
2. Enter your app details:
   - **Android package name**: `com.serenityai` (must match your app's package name)
   - **App nickname** (optional): AI Therapist
   - **Debug signing certificate SHA-1** (optional for now)
3. Click **"Register app"**

### Step 3: Download google-services.json

1. Download the `google-services.json` file
2. **IMPORTANT**: Place this file in the `app/` directory (same level as `build.gradle.kts`)
   ```
   AITherapist/
   ├── app/
   │   ├── google-services.json  ← Place it here
   │   ├── build.gradle.kts
   │   └── src/
   └── ...
   ```

### Step 4: Enable Firestore Database

1. In Firebase Console, go to **Firestore Database**
2. Click **"Create database"**
3. Choose **"Start in production mode"** (we'll set up security rules later)
4. Select a **location** for your database (choose closest to your users)
5. Click **"Enable"**

### Step 5: Set Up Firestore Collections

Create the following collections in Firestore:

#### Collection 1: `educational_resources`

1. Go to Firestore Database → **Start collection**
2. Collection ID: `educational_resources`
3. Add documents with the following structure:

**Example Document:**
- Document ID: (auto-generated or custom)
- Fields:
  ```
  title: "Understanding Anxiety: A Comprehensive Guide" (string)
  description: "Learn about anxiety disorders..." (string)
  category: "Anxiety Management" (string)
  format: "TEXT" (string) - Must be "TEXT", "VIDEO", or "AUDIO"
  url: "https://example.com/anxiety-guide" (string)
  duration: 15 (number) - Duration in minutes
  tags: ["anxiety", "mental health", "education"] (array of strings)
  difficulty: "BEGINNER" (string) - Must be "BEGINNER", "INTERMEDIATE", or "ADVANCED"
  relevanceScore: 85.0 (number) - 0-100
  createdAt: [Timestamp] - Use Firestore timestamp
  ```

#### Collection 2: `learning_progress`

1. Go to Firestore Database → **Start collection**
2. Collection ID: `learning_progress`
3. This collection will be created automatically when users track progress
4. Document structure:
   ```
   userId: "user123" (string)
   resourceId: "res_001" (string)
   progress: 75 (number) - 0-100
   timeSpent: 675.0 (number) - seconds
   completedAt: [Timestamp or null]
   notes: "Found this helpful" (string or null)
   updatedAt: [Timestamp]
   ```

### Step 6: Create Firestore Indexes

1. Go to Firestore Database → **Indexes** tab
2. Click **"Create Index"**

**Index 1:**
- Collection: `educational_resources`
- Fields:
  - `category` (Ascending)
  - `format` (Ascending)
- Query scope: Collection

**Index 2:**
- Collection: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `updatedAt` (Descending)
- Query scope: Collection

**Index 3:**
- Collection: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `resourceId` (Ascending)
- Query scope: Collection

### Step 7: Configure Security Rules

1. Go to Firestore Database → **Rules** tab
2. Replace the default rules with:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Educational Resources - Read-only for authenticated users, write for admins
    match /educational_resources/{resourceId} {
      allow read: if request.auth != null;
      allow write: if false; // Only admins can write via Firebase Console or Admin SDK
    }
    
    // Learning Progress - Users can only access their own progress
    match /learning_progress/{progressId} {
      allow read: if request.auth != null && 
                     resource.data.userId == request.auth.uid;
      allow create: if request.auth != null && 
                      request.resource.data.userId == request.auth.uid;
      allow update: if request.auth != null && 
                      resource.data.userId == request.auth.uid &&
                      request.resource.data.userId == request.auth.uid;
      allow delete: if request.auth != null && 
                      resource.data.userId == request.auth.uid;
    }
    
    // Allow other collections (for future use)
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

3. Click **"Publish"**

### Step 8: Enable Authentication (Optional but Recommended)

1. Go to **Authentication** → **Get started**
2. Enable **Email/Password** sign-in method
3. Enable **Anonymous** sign-in (for testing without authentication)

### Step 9: Add Sample Data

Add at least a few educational resources to test:

**Resource 1:**
```json
{
  "title": "Understanding Anxiety: A Comprehensive Guide",
  "description": "Learn about anxiety disorders, symptoms, and evidence-based treatment approaches.",
  "category": "Anxiety Management",
  "format": "TEXT",
  "url": "https://example.com/anxiety-guide",
  "duration": 15,
  "tags": ["anxiety", "mental health", "education", "guide"],
  "difficulty": "BEGINNER",
  "relevanceScore": 85.0,
  "createdAt": [Current Timestamp]
}
```

**Resource 2:**
```json
{
  "title": "Mindfulness Meditation for Beginners",
  "description": "Step-by-step video guide to starting your mindfulness practice.",
  "category": "Mindfulness",
  "format": "VIDEO",
  "url": "https://example.com/mindfulness-video",
  "duration": 20,
  "tags": ["mindfulness", "meditation", "video", "beginners"],
  "difficulty": "BEGINNER",
  "relevanceScore": 90.0,
  "createdAt": [Current Timestamp]
}
```

**Resource 3:**
```json
{
  "title": "CBT Techniques for Negative Thoughts",
  "description": "Learn cognitive behavioral therapy techniques to challenge and reframe negative thinking patterns.",
  "category": "Cognitive Behavioral Therapy",
  "format": "TEXT",
  "url": "https://example.com/cbt-techniques",
  "duration": 25,
  "tags": ["CBT", "cognitive therapy", "negative thoughts", "reframing"],
  "difficulty": "INTERMEDIATE",
  "relevanceScore": 80.0,
  "createdAt": [Current Timestamp]
}
```

### Step 10: Build and Run

1. **Sync Gradle**: In Android Studio, click **"Sync Now"** when prompted
2. **Build the project**: Build → Make Project (Ctrl+F9 / Cmd+F9)
3. **Run the app**: Run → Run 'app' (Shift+F10 / Ctrl+R)

## Verification

### Check Firebase Connection

1. Run the app
2. Navigate to the Educational Resources screen
3. Resources should load from Firebase (if you added sample data)
4. If no data appears, check:
   - `google-services.json` is in the correct location
   - Firebase project is active
   - Collections exist in Firestore
   - Security rules allow read access

### Test Learning Progress

1. Open an educational resource
2. Mark it as completed or track progress
3. Check Firestore Console → `learning_progress` collection
4. A new document should appear with the progress data

## Troubleshooting

### Issue: "google-services.json not found"

**Solution:**
- Ensure `google-services.json` is in `app/` directory
- File should be at: `app/google-services.json`
- Sync Gradle after adding the file

### Issue: "FirebaseApp not initialized"

**Solution:**
- Check that `AITherapistApplication` is registered in AndroidManifest.xml
- Verify `google-services.json` is valid
- Rebuild the project

### Issue: "Permission denied" errors

**Solution:**
- Check Firestore security rules
- Ensure user is authenticated (if required by rules)
- Verify collection names match exactly: `educational_resources` and `learning_progress`

### Issue: No data appears

**Solution:**
- Verify collections exist in Firestore
- Check that documents have correct field names and types
- Ensure `format` values are exactly: "TEXT", "VIDEO", or "AUDIO"
- Ensure `difficulty` values are exactly: "BEGINNER", "INTERMEDIATE", or "ADVANCED"
- Check Firebase Console → Firestore → Data tab

### Issue: Index errors

**Solution:**
- Go to Firestore → Indexes
- Create the required indexes (they may take a few minutes to build)
- Wait for indexes to finish building before testing queries

## Next Steps

1.  Add more educational resources to Firebase
2.  Set up user authentication (if not already done)
3.  Configure Firebase Analytics (optional)
4.  Set up Firebase Crashlytics (optional)
5.  Test offline functionality (Firestore persistence is enabled)

## Support

If you encounter issues:
1. Check Firebase Console for error logs
2. Check Android Studio Logcat for Firebase errors
3. Verify all setup steps were completed
4. Ensure `google-services.json` matches your Firebase project

---

**Note**: The app includes fallback to hardcoded data, so it will work even if Firebase is not configured. However, to use real Firebase data, complete all the steps above.


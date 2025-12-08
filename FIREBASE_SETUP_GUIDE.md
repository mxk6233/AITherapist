# Firebase Setup Guide for Educational Resources

## Overview

The `EducationalResourcesUseCase` has been updated to connect to Firebase Firestore for fetching educational resources and tracking learning progress. This guide explains the Firebase database structure and setup requirements.

## Firebase Collections Structure

### 1. `educational_resources` Collection

This collection stores all educational resources (articles, videos, guides).

**Document Structure:**
```json
{
  "title": "Understanding Anxiety: A Comprehensive Guide",
  "description": "Learn about anxiety disorders, symptoms, and evidence-based treatment approaches.",
  "category": "Anxiety Management",
  "format": "TEXT",  // TEXT, VIDEO, or AUDIO
  "url": "https://example.com/anxiety-guide",
  "duration": 15,  // in minutes
  "tags": ["anxiety", "mental health", "education", "guide"],
  "difficulty": "BEGINNER",  // BEGINNER, INTERMEDIATE, or ADVANCED
  "relevanceScore": 85.0,  // 0-100
  "createdAt": "[Current Timestamp]"  // Firestore Timestamp
}
```

**Required Fields:**
- `title` (string): Resource title
- `description` (string): Resource description
- `category` (string): Category name (e.g., "Anxiety Management", "Mindfulness")
- `format` (string): Content format - must be "TEXT", "VIDEO", or "AUDIO"
- `url` (string): Resource URL
- `duration` (number): Duration in minutes
- `tags` (array of strings): Searchable tags
- `difficulty` (string): Difficulty level - must be "BEGINNER", "INTERMEDIATE", or "ADVANCED"
- `relevanceScore` (number): Relevance score (0-100)
- `createdAt` (timestamp): Creation date

**Firestore Indexes Required:**
- Collection: `educational_resources`
  - Index on: `category` (Ascending)
  - Index on: `format` (Ascending)
  - Composite index: `category` + `format` (if filtering by both)

### 2. `learning_progress` Collection

This collection tracks user learning progress for educational resources.

**Document Structure:**
```json
{
  "userId": "user123",
  "resourceId": "res_001",
  "progress": 75,  // 0-100
  "timeSpent": 675.0,  // in seconds
  "completedAt": null,  // Firestore Timestamp (null if not completed)
  "notes": "Found this very helpful",
  "updatedAt": "[Current Timestamp]"  // Firestore Timestamp
}
```

**Required Fields:**
- `userId` (string): User ID
- `resourceId` (string): Educational resource ID
- `progress` (number): Progress percentage (0-100)
- `timeSpent` (number): Time spent in seconds
- `completedAt` (timestamp, nullable): Completion timestamp (null if not completed)
- `notes` (string, nullable): User notes
- `updatedAt` (timestamp): Last update timestamp

**Firestore Indexes Required:**
- Collection: `learning_progress`
  - Index on: `userId` (Ascending) + `updatedAt` (Descending)
  - Composite index: `userId` + `resourceId` (for querying specific progress)

## Firebase Console Setup Steps

### Step 1: Create Collections

1. Open Firebase Console: https://console.firebase.google.com/
2. Select your project
3. Go to Firestore Database
4. Create the following collections:
   - `educational_resources`
   - `learning_progress`

### Step 2: Create Indexes

1. Go to Firestore Database → Indexes
2. Create the following indexes:

**Index 1:**
- Collection ID: `educational_resources`
- Fields:
  - `category` (Ascending)
  - `format` (Ascending)
- Query scope: Collection

**Index 2:**
- Collection ID: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `updatedAt` (Descending)
- Query scope: Collection

**Index 3:**
- Collection ID: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `resourceId` (Ascending)
- Query scope: Collection

### Step 3: Add Sample Data

Add sample educational resources to the `educational_resources` collection:

**Example Document 1:**
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
  "createdAt": [Firestore Timestamp]
}
```

**Example Document 2:**
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
  "createdAt": [Firestore Timestamp]
}
```

## Security Rules

Add the following Firestore security rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Educational Resources - Read-only for authenticated users
    match /educational_resources/{resourceId} {
      allow read: if request.auth != null;
      allow write: if false; // Only admins can write (configure separately)
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
  }
}
```

## Code Changes Summary

### Updated Files:

1. **`FirebaseSource.kt`**
   - Added `getEducationalResources()` - Fetches resources with optional category/format filters
   - Added `searchEducationalResources()` - Searches resources by keyword
   - Added `getResourceCategories()` - Gets distinct categories from resources
   - Added `saveLearningProgress()` - Saves/updates learning progress
   - Added `getLearningHistory()` - Gets user's learning history
   - Added `getLearningProgress()` - Gets specific progress for user/resource

2. **`EducationalResourcesUseCase.kt`**
   - All methods converted to `suspend` functions
   - Methods now use `FirebaseSource` to fetch from Firestore
   - Fallback to hardcoded data if Firebase fails (for offline/error scenarios)
   - Learning progress is now persisted to Firebase

### Breaking Changes:

⚠️ **Important**: All methods in `EducationalResourcesUseCase` are now `suspend` functions. UI code calling these methods must be updated to use coroutines:

**Before:**
```kotlin
val resources = useCase.getEducationalResources()
```

**After:**
```kotlin
viewModelScope.launch {
    val resources = useCase.getEducationalResources()
    // Update UI state
}
```

## Testing

The implementation includes fallback to hardcoded data, so:
-  Works offline (falls back to hardcoded data)
-  Works with empty Firebase (falls back to hardcoded data)
-  Works with Firebase errors (falls back to hardcoded data)
-  Works with Firebase data (uses live data when available)

## Next Steps

1. Set up Firebase collections and indexes as described above
2. Add educational resources to the `educational_resources` collection
3. Update UI code to handle `suspend` functions (use coroutines)
4. Test with Firebase data
5. Configure security rules for production

## Notes

- The use case gracefully handles Firebase failures by falling back to hardcoded data
- Learning progress is saved to Firebase but won't fail if Firebase is unavailable
- Categories are dynamically fetched from Firebase but fall back to hardcoded list if needed
- All Firebase operations run on `Dispatchers.IO` to avoid blocking the main thread


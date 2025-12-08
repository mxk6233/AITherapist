# Firebase Setup Instructions for mohammadkazminj@gmail.com

## Quick Setup Guide

Follow these steps to connect your app to Firebase and populate it with live data.

## Step 1: Access Firebase Console

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Sign in with: **mohammadkazminj@gmail.com**
3. Create a new project OR select existing project:
   - Project name: "AI Therapist" (or your preferred name)
   - Enable Google Analytics (optional)

## Step 2: Add Android App

1. In Firebase Console, click **"Add app"** → Select **Android**
2. Enter details:
   - **Package name**: `com.serenityai` (MUST match exactly)
   - **App nickname**: AI Therapist (optional)
   - **Debug signing certificate SHA-1**: (optional for now)
3. Click **"Register app"**
4. **Download `google-services.json`**
5. **Place the file in**: `app/google-services.json` (same directory as `app/build.gradle.kts`)

## Step 3: Enable Firestore Database

1. In Firebase Console, go to **Firestore Database**
2. Click **"Create database"**
3. Choose **"Start in production mode"** (we'll configure rules)
4. Select **location** (choose closest to your users, e.g., `us-central` or `europe-west`)
5. Click **"Enable"**

## Step 4: Import Educational Resources Data

### Option A: Using Python Script (Recommended)

1. **Install Python dependencies**:
   ```bash
   pip install firebase-admin
   ```

2. **Get Service Account Key**:
   - Go to Firebase Console → Project Settings → Service Accounts
   - Click **"Generate new private key"**
   - Save the JSON file as `firebase_setup/serviceAccountKey.json`

3. **Run the import script**:
   ```bash
   cd firebase_setup
   python import_to_firebase.py
   ```

### Option B: Manual Import via Firebase Console

1. Go to Firestore Database → **Start collection**
2. Collection ID: `educational_resources`
3. For each resource in `sample_educational_resources.json`:
   - Click **"Add document"**
   - Copy fields from JSON
   - Set `createdAt` as **Timestamp** (use current time)
   - Click **"Save"**

## Step 5: Create Firestore Indexes

1. Go to Firestore Database → **Indexes** tab
2. Click **"Create Index"** for each:

**Index 1:**
- Collection ID: `educational_resources`
- Fields:
  - `category` (Ascending)
  - `format` (Ascending)
- Query scope: Collection
- Click **"Create"**

**Index 2:**
- Collection ID: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `updatedAt` (Descending)
- Query scope: Collection
- Click **"Create"**

**Index 3:**
- Collection ID: `learning_progress`
- Fields:
  - `userId` (Ascending)
  - `resourceId` (Ascending)
- Query scope: Collection
- Click **"Create"**

⏳ **Note**: Indexes may take a few minutes to build. Wait until status shows "Enabled" before testing.

## Step 6: Configure Security Rules

1. Go to Firestore Database → **Rules** tab
2. Replace with:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Educational Resources - Read for authenticated users
    match /educational_resources/{resourceId} {
      allow read: if request.auth != null;
      allow write: if false; // Only admins via Console or Admin SDK
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
    
    // Allow authenticated users to read/write other collections
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

3. Click **"Publish"**

## Step 7: Enable Authentication (For Testing)

1. Go to **Authentication** → **Get started**
2. Enable **Email/Password** sign-in method
3. Enable **Anonymous** sign-in (for quick testing)
4. Save

## Step 8: Build and Test

1. **Sync Gradle** in Android Studio:
   - File → Sync Project with Gradle Files

2. **Build the project**:
   - Build → Make Project

3. **Run the app**:
   - Run → Run 'app'

4. **Verify data**:
   - Navigate to Educational Resources screen
   - You should see resources from Firebase
   - Check Logcat for Firebase connection messages

## Verification Checklist

- [ ] `google-services.json` is in `app/` directory
- [ ] Firestore Database is enabled
- [ ] `educational_resources` collection exists with data
- [ ] All 3 indexes are created and enabled
- [ ] Security rules are published
- [ ] App builds without errors
- [ ] App shows Firebase data (not fallback data)

## Troubleshooting

### "google-services.json not found"
- Ensure file is in `app/` directory (not `app/src/`)
- File name must be exactly `google-services.json`
- Sync Gradle after adding

### "Permission denied" errors
- Check security rules are published
- Ensure user is authenticated (enable Anonymous auth for testing)
- Verify collection names match exactly

### No data appears
- Check Firestore Console → Data tab
- Verify documents exist in `educational_resources`
- Check field names match exactly (case-sensitive)
- Verify `format` values are: "TEXT", "VIDEO", or "AUDIO"
- Verify `difficulty` values are: "BEGINNER", "INTERMEDIATE", or "ADVANCED"

### Index errors
- Wait for indexes to finish building (check Indexes tab)
- Create indexes manually if auto-creation fails
- Ensure index fields match query fields exactly

## Sample Data Included

The `sample_educational_resources.json` file contains **15 educational resources** covering:
- Anxiety Management (2 resources)
- Mindfulness (2 resources)
- Cognitive Behavioral Therapy (2 resources)
- Stress Relief (3 resources)
- Self-Care (3 resources)
- Depression Support (1 resource)
- Sleep (1 resource)
- Relationships (1 resource)
- Mindset & Growth (1 resource)

## Next Steps After Setup

1.  Test the app with Firebase data
2.  Add more educational resources as needed
3.  Monitor Firebase Console for usage
4.  Set up Firebase Analytics (optional)
5.  Configure Firebase Crashlytics (optional)

---

**Your Firebase account**: mohammadkazminj@gmail.com  
**Package name**: com.serenityai  
**Collections**: `educational_resources`, `learning_progress`


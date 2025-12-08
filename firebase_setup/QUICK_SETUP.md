# Quick Setup - 5 Minutes to Live Firebase Data

## For Account: mohammadkazminj@gmail.com

### Step 1: Get google-services.json (2 minutes)

1. Go to: https://console.firebase.google.com/
2. Sign in with: **mohammadkazminj@gmail.com**
3. Create project: "AI Therapist"
4. Add Android app:
   - Package: `com.serenityai`
   - Download `google-services.json`
5. Place file in: `app/google-services.json`

### Step 2: Enable Firestore (1 minute)

1. Firebase Console → Firestore Database
2. Click "Create database"
3. Choose "Production mode"
4. Select location (e.g., `us-central`)
5. Click "Enable"

### Step 3: Add Sample Data (2 minutes)

**Option A: Quick Test (3 resources)**
1. Firestore → Start collection → `educational_resources`
2. Add these 3 documents:

**Resource 1:**
```
title: "Understanding Anxiety: A Comprehensive Guide"
category: "Anxiety Management"
format: "TEXT"
duration: 15
difficulty: "BEGINNER"
relevanceScore: 85.0
createdAt: [Current Timestamp]
```

**Resource 2:**
```
title: "Mindfulness Meditation for Beginners"
category: "Mindfulness"
format: "VIDEO"
duration: 20
difficulty: "BEGINNER"
relevanceScore: 90.0
createdAt: [Current Timestamp]
```

**Resource 3:**
```
title: "Guided Breathing Exercise"
category: "Stress Relief"
format: "AUDIO"
duration: 10
difficulty: "BEGINNER"
relevanceScore: 88.0
createdAt: [Current Timestamp]
```

**Option B: Import All (Use Python script)**
```bash
cd firebase_setup
pip install firebase-admin
# Get serviceAccountKey.json from Firebase Console
python import_to_firebase.py
```

### Step 4: Create Indexes (Auto or Manual)

Firebase will prompt you to create indexes when needed, OR create manually:

1. Firestore → Indexes → Create Index
2. Collection: `educational_resources`
3. Fields: `category` (Asc), `format` (Asc)

### Step 5: Test (1 minute)

1. Sync Gradle in Android Studio
2. Build project
3. Run app
4. Check Educational Resources screen

##  Done!

Your app is now connected to Firebase with live data!

## Need More Data?

See `sample_educational_resources.json` for 15 complete resources.


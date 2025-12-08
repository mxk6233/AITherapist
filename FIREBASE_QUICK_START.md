# Firebase Quick Start Guide

## Quick Setup Checklist

- [ ] Create Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
- [ ] Add Android app with package name: `com.serenityai`
- [ ] Download `google-services.json` and place in `app/` directory
- [ ] Enable Firestore Database
- [ ] Create collections: `educational_resources` and `learning_progress`
- [ ] Create Firestore indexes (see below)
- [ ] Add sample data to `educational_resources`
- [ ] Configure security rules
- [ ] Sync Gradle and rebuild project

## Critical Files Location

```
AITherapist/
├── app/
│   ├── google-services.json  ← MUST BE HERE (download from Firebase Console)
│   └── build.gradle.kts
├── app/src/main/
│   ├── AndroidManifest.xml  ← Updated with Application class
│   └── java/com/serenityai/
│       └── AITherapistApplication.kt  ← New file, initializes Firebase
```

## Required Firestore Collections

### 1. `educational_resources`
**Fields:**
- `title` (string)
- `description` (string)
- `category` (string)
- `format` (string) - "TEXT", "VIDEO", or "AUDIO"
- `url` (string)
- `duration` (number)
- `tags` (array of strings)
- `difficulty` (string) - "BEGINNER", "INTERMEDIATE", or "ADVANCED"
- `relevanceScore` (number)
- `createdAt` (timestamp)

### 2. `learning_progress`
**Fields:**
- `userId` (string)
- `resourceId` (string)
- `progress` (number) - 0-100
- `timeSpent` (number)
- `completedAt` (timestamp or null)
- `notes` (string or null)
- `updatedAt` (timestamp)

## Required Firestore Indexes

1. **Collection**: `educational_resources`
   - Fields: `category` (Ascending), `format` (Ascending)

2. **Collection**: `learning_progress`
   - Fields: `userId` (Ascending), `updatedAt` (Descending)

3. **Collection**: `learning_progress`
   - Fields: `userId` (Ascending), `resourceId` (Ascending)

## Verification Steps

1. **Check google-services.json exists**
   ```bash
   ls app/google-services.json
   ```

2. **Sync Gradle**
   - Android Studio → File → Sync Project with Gradle Files

3. **Build Project**
   - Build → Make Project

4. **Run App**
   - Run → Run 'app'

5. **Check Logcat**
   - Look for Firebase initialization messages
   - Check for any Firebase errors

## Common Issues

### "google-services.json not found"
- Ensure file is in `app/` directory (not `app/src/`)
- File name must be exactly `google-services.json`

### "FirebaseApp not initialized"
- Check `AITherapistApplication` is in AndroidManifest.xml
- Verify `google-services.json` is valid JSON
- Rebuild project

### No data appears
- Verify collections exist in Firestore
- Check field names match exactly (case-sensitive)
- Verify security rules allow read access
- Check Logcat for Firebase errors

## Next Steps

1. Follow detailed guide: `FIREBASE_CONNECTION_SETUP.md`
2. Add educational resources to Firestore
3. Test the app with real Firebase data

---

**Note**: The app will work with fallback data if Firebase is not configured, but to use real Firebase, complete all checklist items above.


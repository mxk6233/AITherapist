# How to Get google-services.json

## Quick Steps

1. **Go to Firebase Console**: https://console.firebase.google.com/
2. **Sign in** with: mohammadkazminj@gmail.com
3. **Select or create project**: "AI Therapist"
4. **Add Android app**:
   - Click "Add app" → Android icon
   - Package name: `com.serenityai`
   - App nickname: AI Therapist (optional)
   - Click "Register app"
5. **Download google-services.json**
6. **Replace the placeholder file**:
   - Delete: `app/google-services.json` (current placeholder)
   - Place downloaded file: `app/google-services.json`

## File Location

The file MUST be at:
```
AITherapist/
└── app/
    └── google-services.json  ← Place it here
```

## After Adding Real File

1. **Sync Gradle**: File → Sync Project with Gradle Files
2. **Build**: Build → Make Project
3. **Run**: Run → Run 'app'

## Current Status

 **Placeholder file created** - Build will now succeed  
⏳ **Replace with real file** - Get from Firebase Console  
⏳ **Set up Firestore** - Follow `firebase_setup/FIREBASE_SETUP_INSTRUCTIONS.md`

---

**Note**: The placeholder file allows the build to succeed, but you need the real file from Firebase Console to connect to your Firebase project.


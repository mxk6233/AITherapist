# Firebase Service Account Setup

## Your Service Account

**Service Account Email**: `firebase-adminsdk-fbsvc@serenityai-f2dd4.iam.gserviceaccount.com`

**Project**: `serenityai-f2dd4`

## What is a Service Account?

A Firebase service account is used for **server-side operations** like:
- Importing data to Firestore
- Admin operations (not for client apps)
- Backend services
- Automated scripts

**Note**: For Android client apps, you use the **client SDK** (already configured), not the Admin SDK.

## Getting Your Service Account Key

### Step 1: Download Service Account Key

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project: **serenityai-f2dd4**
3. Go to **Project Settings** (gear icon)
4. Click **Service Accounts** tab
5. Click **"Generate new private key"**
6. Click **"Generate key"** in the dialog
7. A JSON file will download (e.g., `serenityai-f2dd4-xxxxx.json`)

### Step 2: Rename and Place the File

1. Rename the downloaded file to: `serviceAccountKey.json`
2. Place it in: `firebase_setup/serviceAccountKey.json`

**File structure:**
```
AITherapist/
└── firebase_setup/
    ├── serviceAccountKey.json  ← Place it here
    ├── import_to_firebase.py
    └── sample_educational_resources.json
```

### Step 3: Use the Import Script

```bash
cd firebase_setup
python import_to_firebase.py
```

## Security Notes

⚠️ **IMPORTANT**: 
- **NEVER** commit `serviceAccountKey.json` to Git
- **NEVER** share this file publicly
- This key has admin access to your Firebase project
- Keep it secure and private

### Add to .gitignore

Make sure `serviceAccountKey.json` is in `.gitignore`:

```gitignore
# Firebase
firebase_setup/serviceAccountKey.json
app/google-services.json
```

## Using the Service Account

### Python (for import script)

```python
import firebase_admin
from firebase_admin import credentials

cred = credentials.Certificate('serviceAccountKey.json')
firebase_admin.initialize_app(cred)
```

### Java (for server-side operations)

```java
FileInputStream serviceAccount = 
    new FileInputStream("path/to/serviceAccountKey.json");

FirebaseOptions options = new FirebaseOptions.Builder()
    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    .build();

FirebaseApp.initializeApp(options);
```

## What You Can Do With Service Account

1. **Import Data** - Use the Python script to bulk import educational resources
2. **Backend Operations** - Server-side Firebase operations
3. **Admin Tasks** - Manage users, data, etc. programmatically
4. **Automation** - Automated data migrations, backups, etc.

## Current Status

 **Service Account**: Created  
⏳ **Service Account Key**: Download and place in `firebase_setup/`  
⏳ **Import Script**: Ready to use once key is added  

## Next Steps

1. Download service account key from Firebase Console
2. Place as `firebase_setup/serviceAccountKey.json`
3. Run `python firebase_setup/import_to_firebase.py`
4. Verify data in Firebase Console → Firestore

---

**Remember**: The service account is for server-side/admin operations. Your Android app uses the client SDK (already configured) and doesn't need the service account key.


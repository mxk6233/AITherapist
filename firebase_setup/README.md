# Firebase Setup Files

This directory contains files to help you set up Firebase with live data.

## Files

- **`sample_educational_resources.json`** - Sample data with 15 educational resources
- **`import_to_firebase.py`** - Python script to import data to Firestore
- **`FIREBASE_SETUP_INSTRUCTIONS.md`** - Step-by-step setup guide

## Quick Start

1. **Get `google-services.json`**:
   - Firebase Console → Add Android app → Download
   - Place in `app/google-services.json`

2. **Import data** (choose one method):

   **Method A: Python Script** (Recommended)
   ```bash
   pip install firebase-admin
   # Get service account key from Firebase Console
   # Place as firebase_setup/serviceAccountKey.json
   python firebase_setup/import_to_firebase.py
   ```

   **Method B: Manual Import**
   - Use Firebase Console → Firestore → Add documents
   - Copy data from `sample_educational_resources.json`

3. **Create indexes** (see FIREBASE_SETUP_INSTRUCTIONS.md)

4. **Build and run** the app

## Data Structure

All resources include:
- Title, description, category
- Format (TEXT, VIDEO, AUDIO)
- Duration, tags, difficulty
- Relevance score, URL

## Note on Example URLs

**Important**: All URLs in `sample_educational_resources.json` use the `example.com` domain. 

The `example.com` domain is reserved by RFC 2606 for use in documentation examples without needing permission. This is appropriate for sample/test data.

**For Production**: Replace these example URLs with actual resource URLs when deploying to production. The URLs in the sample file are placeholders and should not be used in a live application.

Ready to import and use!


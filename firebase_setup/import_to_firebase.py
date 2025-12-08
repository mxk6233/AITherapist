#!/usr/bin/env python3
"""
Firebase Firestore Data Import Script
This script imports educational resources to Firebase Firestore.

Requirements:
    pip install firebase-admin

Usage:
    1. Download service account key from Firebase Console
    2. Place it as 'serviceAccountKey.json' in this directory
    3. Run: python import_to_firebase.py
"""

import firebase_admin
from firebase_admin import credentials, firestore
import json
from datetime import datetime

# Initialize Firebase Admin SDK
def initialize_firebase():
    """Initialize Firebase Admin SDK with service account credentials."""
    try:
        # Try to use existing default app
        firebase_admin.get_app()
        print("Firebase already initialized")
    except ValueError:
        # Initialize with service account key
        try:
            # Look for service account key in current directory or parent directory
            key_paths = [
                'serviceAccountKey.json',
                '../serviceAccountKey.json',
                'firebase_setup/serviceAccountKey.json'
            ]
            
            key_path = None
            for path in key_paths:
                import os
                if os.path.exists(path):
                    key_path = path
                    break
            
            if key_path is None:
                raise FileNotFoundError("serviceAccountKey.json not found")
            
            cred = credentials.Certificate(key_path)
            firebase_admin.initialize_app(cred)
            print(f" Firebase initialized successfully using {key_path}")
            print("   Service Account: firebase-adminsdk-fbsvc@serenityai-f2dd4.iam.gserviceaccount.com")
        except FileNotFoundError:
            print("❌ Error: serviceAccountKey.json not found")
            print("\nTo get your service account key:")
            print("1. Go to Firebase Console → Project Settings → Service Accounts")
            print("2. Click 'Generate new private key'")
            print("3. Save as 'serviceAccountKey.json' in firebase_setup/ directory")
            print("\nYour service account:")
            print("   firebase-adminsdk-fbsvc@serenityai-f2dd4.iam.gserviceaccount.com")
            exit(1)
        except Exception as e:
            print(f"❌ Error initializing Firebase: {str(e)}")
            exit(1)

def import_educational_resources():
    """Import educational resources from JSON file to Firestore."""
    db = firestore.client()
    
    # Read sample data
    try:
        with open('sample_educational_resources.json', 'r') as f:
            resources = json.load(f)
    except FileNotFoundError:
        print("❌ Error: sample_educational_resources.json not found")
        exit(1)
    
    print(f"\n📚 Importing {len(resources)} educational resources...")
    
    collection_ref = db.collection('educational_resources')
    imported_count = 0
    error_count = 0
    
    for resource in resources:
        try:
            # Add createdAt timestamp if not present
            if 'createdAt' not in resource:
                resource['createdAt'] = firestore.SERVER_TIMESTAMP
            
            # Add document to Firestore
            doc_ref = collection_ref.add(resource)[1]
            print(f"   Imported: {resource['title']} (ID: {doc_ref.id})")
            imported_count += 1
        except Exception as e:
            print(f"  ❌ Error importing {resource.get('title', 'Unknown')}: {str(e)}")
            error_count += 1
    
    print(f"\n📊 Import Summary:")
    print(f"    Successfully imported: {imported_count}")
    print(f"   ❌ Errors: {error_count}")
    print(f"   📝 Total: {len(resources)}")

def create_indexes_info():
    """Print information about required Firestore indexes."""
    print("\n📋 Required Firestore Indexes:")
    print("\n1. Collection: educational_resources")
    print("   Fields: category (Ascending), format (Ascending)")
    print("\n2. Collection: learning_progress")
    print("   Fields: userId (Ascending), updatedAt (Descending)")
    print("\n3. Collection: learning_progress")
    print("   Fields: userId (Ascending), resourceId (Ascending)")
    print("\n💡 Note: Create these indexes in Firebase Console → Firestore → Indexes")
    print("   They will be created automatically when you run queries, or you can create them manually.")

def main():
    print("🔥 Firebase Firestore Data Import Tool")
    print("=" * 50)
    
    # Initialize Firebase
    initialize_firebase()
    
    # Import educational resources
    import_educational_resources()
    
    # Show index information
    create_indexes_info()
    
    print("\n Import process completed!")
    print("\n📖 Next steps:")
    print("   1. Verify data in Firebase Console → Firestore → Data")
    print("   2. Create required indexes (see above)")
    print("   3. Configure security rules")
    print("   4. Test the app with Firebase data")

if __name__ == '__main__':
    main()


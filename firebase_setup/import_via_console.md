# Import Data via Firebase Console (No Scripts Needed)

## Quick Method: Manual Import via Firebase Console

This is the easiest method - no scripts or coding required!

### Step 1: Access Firebase Console

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Sign in with: **mohammadkazminj@gmail.com**
3. Select your project (or create new: "AI Therapist")

### Step 2: Open Firestore Database

1. Click **Firestore Database** in left menu
2. Click **"Start collection"** (if database is empty)

### Step 3: Create Collection

1. **Collection ID**: `educational_resources`
2. Click **"Next"**

### Step 4: Add Documents

For each resource below, click **"Add document"** and enter:

#### Document 1:
- **Document ID**: (leave auto-generated)
- **Fields**:
  - `title` → String → "Understanding Anxiety: A Comprehensive Guide"
  - `description` → String → "Learn about anxiety disorders, symptoms, and evidence-based treatment approaches."
  - `category` → String → "Anxiety Management"
  - `format` → String → "TEXT"
  - `url` → String → "https://www.helpguide.org/articles/anxiety/anxiety-disorders-and-anxiety-attacks.htm"
  - `duration` → Number → 15
  - `tags` → Array → ["anxiety", "mental health", "education", "guide"]
  - `difficulty` → String → "BEGINNER"
  - `relevanceScore` → Number → 85.0
  - `createdAt` → Timestamp → (click and select "Current time")
- Click **"Save"**

#### Document 2:
- `title` → "Mindfulness Meditation for Beginners"
- `description` → "Step-by-step video guide to starting your mindfulness practice."
- `category` → "Mindfulness"
- `format` → "VIDEO"
- `url` → "https://www.youtube.com/watch?v=ZToicYcHIOU"
- `duration` → 20
- `tags` → ["mindfulness", "meditation", "video", "beginners"]
- `difficulty` → "BEGINNER"
- `relevanceScore` → 90.0
- `createdAt` → Timestamp (current time)

#### Document 3:
- `title` → "CBT Techniques for Negative Thoughts"
- `description` → "Learn cognitive behavioral therapy techniques to challenge and reframe negative thinking patterns."
- `category` → "Cognitive Behavioral Therapy"
- `format` → "TEXT"
- `url` → "https://www.verywellmind.com/cbt-techniques-2584013"
- `duration` → 25
- `tags` → ["CBT", "cognitive therapy", "negative thoughts", "reframing"]
- `difficulty` → "INTERMEDIATE"
- `relevanceScore` → 80.0
- `createdAt` → Timestamp (current time)

#### Document 4:
- `title` → "Guided Breathing Exercise for Stress Relief"
- `description` → "Audio-guided breathing exercise for stress relief and relaxation."
- `category` → "Stress Relief"
- `format` → "AUDIO"
- `url` → "https://www.calm.com/breathing"
- `duration` → 10
- `tags` → ["breathing", "stress relief", "relaxation", "audio"]
- `difficulty` → "BEGINNER"
- `relevanceScore` → 88.0
- `createdAt` → Timestamp (current time)

#### Document 5:
- `title` → "Building a Self-Care Routine"
- `description` → "Practical guide to creating and maintaining a sustainable self-care practice."
- `category` → "Self-Care"
- `format` → "TEXT"
- `url` → "https://www.psychologytoday.com/us/basics/self-care"
- `duration` → 12
- `tags` → ["self-care", "routine", "wellness", "habits"]
- `difficulty` → "BEGINNER"
- `relevanceScore` → 75.0
- `createdAt` → Timestamp (current time)

### Continue Adding More...

See `sample_educational_resources.json` for all 15 resources. You can add them all or start with these 5 to test.

### Important Notes:

- **Field types matter**: Use String, Number, Array, Timestamp correctly
- **Format values**: Must be exactly "TEXT", "VIDEO", or "AUDIO"
- **Difficulty values**: Must be exactly "BEGINNER", "INTERMEDIATE", or "ADVANCED"
- **Tags**: Click "Add field" → Select "Array" → Add each tag as a string element

### After Adding Data:

1. Verify data appears in Firestore Console
2. Create indexes (see FIREBASE_SETUP_INSTRUCTIONS.md)
3. Configure security rules
4. Test the app!

---

**Tip**: Start with 3-5 documents to test, then add the rest once everything works.


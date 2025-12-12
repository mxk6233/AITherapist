# AI Therapist Application

A comprehensive Android mental health support application powered by AI, providing 24/7 accessible therapy sessions, mood tracking, analytics, and wellness tools.

## Project Overview

The AI Therapist application is an Android-based mental health support platform that leverages artificial intelligence to provide personalized therapeutic experiences. The application offers:

- **AI-Powered Therapy**: Conversational AI therapist with voice support
- **Mood Tracking & Analytics**: Comprehensive mood logging, analytics, and predictive forecasting
- **Support Tools**: Coping exercises, educational resources, journaling prompts
- **Wellness Activities**: Daily affirmations, guided breathing exercises
- **Advanced Features**: Therapist portal, social support network, greedy algorithm for optimal recommendations

## Getting Started

### Prerequisites

- Android Studio (latest version)
- JDK 11 or higher
- Android SDK (API Level 24+)
- Firebase account (for authentication and data storage)
- OpenAI API key (for AI chat functionality)

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AITherapist
   ```

2. **Configure Firebase**
   - Follow the instructions in `firebase_setup/FIREBASE_SETUP_INSTRUCTIONS.md`
   - Download `google-services.json` from Firebase Console
   - Place it in `app/google-services.json`

3. **Configure API Keys**
   - Add your OpenAI API key to the appropriate configuration file
   - Ensure Firebase credentials are properly set up

4. **Build and Run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend Services**:
  - Firebase Authentication
  - Firebase Firestore
  - OpenAI API (GPT models)
- **Testing**:
  - JUnit 5
  - Mockito/MockK
  - Espresso (UI Testing)
  - JaCoCo (Code Coverage)

## Key Features

### Authentication & User Management
- User Registration (UC4)
- User Login (UC7)
- Personality Onboarding (UC5)
- User Profile Management (UC10)

### AI-Powered Therapy
- AI Chat Sessions (UC1)
- Crisis Intervention (UC2)
- Chat History (UC6)
- Voice-Enabled Therapy (UC38)

### Mood Tracking & Analytics
- Daily Mood Logging (UC3)
- Mood Analytics Dashboard (UC9)
- AI-Powered Mood Forecasting (UC26)
- Relapse Prevention Alerts (UC35)
- Predictive Burnout Detection (UC37)

### Support Tools
- Coping Exercise Recommendations (UC8)
- Educational Resources (UC16)
- User Support System (UC25)
- AI-Generated Journaling Prompts (UC32)
- Group Therapy Simulation (UC34)

### Wellness Activities
- Daily Affirmations (UC14)
- Guided Breathing & Meditation (UC27)

### Settings & Personalization
- Application Preferences (UC13)
- Accessibility Features (UC17)
- Notification Management (UC18)
- Personalize User Experience (UC24)

### Advanced Features
- Therapist Portal Integration (UC28)
- Social Support Network (UC31)
- Greedy Algorithm for Optimal Recommendations (UC41)

## Testing

### Test Coverage

The project includes comprehensive test coverage with:

- **Unit Tests**: Testing individual use cases and components
- **Integration Tests**: Testing workflows and component interactions
- **UAT Tests**: User Acceptance Testing for user scenarios
- **UI Tests**: Espresso tests for UI components

### Running Tests

```bash
# Run all unit tests
./gradlew test

# Run all tests including integration and UI tests
./gradlew connectedAndroidTest

# Generate test coverage report
./gradlew jacocoTestReport

# View coverage report
open app/build/reports/jacoco/jacocoTestReport/html/index.html
```

### Test Statistics

- **Total Test Cases**: 100+ test cases across all use cases
- **Code Coverage**: 80%+ coverage target
- **Test Types**: Unit, Integration, UAT, UI

### Recently Added Test Cases

The following use cases have been recently implemented with comprehensive test coverage:

#### Week 7 (October 6 - October 12)
- **UC24: Personalize User Experience** - 3 test cases
- **UC27: Guided Breathing & Meditation Sessions** - 3 test cases
- **UC17: Implement Accessibility Features** - 3 test cases

#### Week 8 (October 13 - October 19)
- **UC13: Set Application Preferences/Configurations** - Test cases included
- **UC18: Manage Notifications** - Test cases included

#### Week 9 (October 20 - October 26)
- **UC6: View Chat History** - 3 test cases
- **UC8: Suggest Coping Exercises** - 3 test cases
- **UC9: View Mood Analytics** - 3 test cases

#### Week 10 (October 27 - November 2)
- **UC26: AI-Powered Mood Forecasting** - 3 test cases
- **UC35: Relapse Prevention Alerts** - 3 test cases
- **UC32: AI-Generated Journaling Prompts** - 3 test cases

### Test File Locations

- **Unit Tests**: `app/src/test/java/com/serenityai/test/usecases/`
- **Integration Tests**: `tests/integration/usecases/`
- **UAT Tests**: `tests/uat/usecases/`
- **UI Tests**: `app/src/androidTest/java/com/serenityai/`

### Key Use Cases with Comprehensive Test Coverage

1. **UC1: Conduct AI Chat Session**
   - Unit tests for AI response generation
   - Integration tests for chat workflow
   - UAT tests for user scenarios

2. **UC2: Handle Crisis Intervention**
   - Critical priority use case
   - Comprehensive crisis detection tests
   - Emergency resource validation

3. **UC3: Log Daily Mood**
   - Mood entry validation tests
   - Data persistence tests
   - Analytics calculation tests

4. **UC6: View Chat History**
   - Search and filter functionality tests
   - Analytics calculation tests
   - Export functionality tests

5. **UC8: Suggest Coping Exercises**
   - Personalized recommendation tests
   - Library management tests
   - Progress tracking tests

6. **UC9: View Mood Analytics**
   - Trend analysis tests
   - Pattern recognition tests
   - Dashboard insights tests

7. **UC24: Personalize User Experience**
   - Theme and appearance tests
   - AI personality customization tests
   - Content preference tests

8. **UC27: Guided Breathing & Meditation Sessions**
   - Exercise management tests
   - Meditation management tests
   - Session customization tests

9. **UC17: Implement Accessibility Features**
   - Screen reader support tests
   - Visual accessibility tests
   - Motor and cognitive accessibility tests

10. **UC26: AI-Powered Mood Forecasting**
    - Predictive analysis tests
    - Risk factor analysis tests
    - Proactive intervention tests

11. **UC35: Relapse Prevention Alerts**
    - Risk assessment tests
    - Early warning system tests
    - Safety plan tests

12. **UC32: AI-Generated Journaling Prompts**
    - AI prompt generation tests
    - Categorization tests
    - Custom generation tests

## Project Structure

```
AITherapist/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/serenityai/
│   │   │   │   ├── ui/screens/      # UI screens
│   │   │   │   ├── navigation/       # Navigation components
│   │   │   │   ├── data/            # Data models
│   │   │   │   └── usecases/        # Use case implementations
│   │   │   └── res/                 # Resources
│   │   ├── test/                    # Unit tests
│   │   └── androidTest/             # UI tests
│   └── build.gradle.kts
├── tests/
│   ├── unit/                        # Unit tests
│   ├── integration/                 # Integration tests
│   └── uat/                         # UAT tests
└── firebase_setup/                  # Firebase setup files
```

## Development

### Building the Project

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

### Code Coverage

The project uses JaCoCo for code coverage reporting. Coverage reports are generated in:
```
app/build/reports/jacoco/jacocoTestReport/html/index.html
```



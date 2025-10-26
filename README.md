# AI Therapist

An Android application that provides AI-powered therapeutic support and mental health assistance through interactive conversations.

Designed for individuals seeking confidential mental health support, this application offers a private, judgment-free environment where users can explore their concerns and receive professional guidance. The AI specialist helps users develop effective coping strategies, build emotional resilience, and maintain mental wellness during challenging periods.

## Features

- **AI-Powered Conversations**: Interactive chat interface with therapeutic AI responses
- **Voice Integration**: Text-to-Speech and Speech-to-Text capabilities
- **Mood Tracking**: Track and analyze emotional states over time
- **Therapeutic Exercises**: Guided mental health exercises and activities
- **Session Management**: Create and manage therapy sessions
- **Firebase Integration**: User authentication and data storage
- **Modern UI**: Built with Jetpack Compose and Material Design 3

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with ViewModel
- **Backend**: Firebase (Authentication, Firestore)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Add your Firebase configuration files
4. Configure your OpenAI API key in the app settings
5. Build and run the application

## Running Locally

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Firebase project setup
- OpenAI API key

### Setup Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/AITherapist.git
   cd AITherapist
   ```

2. **Firebase Configuration**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Authentication and Firestore Database
   - Download `google-services.json` and place it in `app/` directory

3. **API Configuration**
   - Obtain an OpenAI API key from [OpenAI Platform](https://platform.openai.com/)
   - Add your API key to the app's configuration

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   - Or use Android Studio's "Run" button
   - Install the APK on your device or emulator

### Development Notes
- The app uses Gradle for dependency management
- All dependencies are defined in `gradle/libs.versions.toml`
- Debug builds are configured for development testing

### Test Coverage Report
JaCoCo coverage reports are available at:
- **HTML Report**: `app/build/reports/jacoco/jacocoTestReport/html/index.html`
- **XML Report**: `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml`

### Running Tests
```bash
# Run all test suites (unit, integration, UAT)
./gradlew runAllTests

# Run specific test suites
./gradlew runUnitTests
./gradlew runIntegrationTests
./gradlew runUATTests

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Generate comprehensive test reports
./gradlew generateTestReports

# Validate test coverage
./gradlew validateTestCoverage

# Run use case specific tests
./gradlew runUseCaseTests
```

### Automated Test Execution
The project includes comprehensive automated testing with the following Gradle tasks:

- **`runAllTests`**: Executes all test suites (unit, integration, UAT)
- **`runUnitTests`**: Runs unit tests only
- **`runIntegrationTests`**: Runs integration tests only  
- **`runUATTests`**: Runs User Acceptance Tests
- **`generateTestReports`**: Generates comprehensive test reports
- **`validateTestCoverage`**: Validates test coverage meets requirements
- **`runUseCaseTests`**: Runs tests for all implemented use cases

## Recent Additions & Test Case Explanations

### Recent Test Suite Additions

#### 1. **Chat History Use Case Tests** (`ChatHistoryUseCaseTests.kt`) - **UC6: View Chat History**
**Purpose**: Testing chat history management and retrieval functionality
**Test Categories**:
- **Chat Session Storage**: Tests saving and retrieving conversation history
- **Message Organization**: Tests chronological sorting and filtering
- **Search Functionality**: Tests keyword search and message retrieval
- **Session Management**: Tests session grouping and display
- **Privacy & Security**: Tests secure storage and user data protection

**Key Test Cases**:
- `shouldRetrieveChatHistory`: Validates chat history retrieval from database
- `shouldFilterChatByDate`: Tests chronological filtering of messages
- `shouldSearchChatMessages`: Tests keyword search functionality
- `shouldOrganizeChatSessions`: Tests session grouping and organization
- `shouldProtectChatPrivacy`: Tests secure storage and data protection

#### 2. **Coping Exercises Use Case Tests** (`CopingExercisesUseCaseTests.kt`) - **UC8: Suggest Coping Exercises**
**Purpose**: Testing therapeutic exercise recommendation and execution
**Test Categories**:
- **Exercise Recommendation**: Tests AI-driven exercise suggestions based on mood
- **Exercise Types**: Tests various coping strategies (breathing, grounding, visualization)
- **Personalization**: Tests user-specific exercise recommendations
- **Progress Tracking**: Tests exercise completion and effectiveness tracking
- **Exercise Library**: Tests comprehensive exercise database and categorization

**Key Test Cases**:
- `shouldRecommendExercisesBasedOnMood`: Validates mood-based exercise suggestions
- `shouldProvidePersonalizedExerciseList`: Tests user-specific recommendations
- `shouldTrackExerciseCompletion`: Tests completion tracking and progress monitoring
- `shouldCategorizeExercises`: Tests exercise categorization and filtering
- `shouldMeasureExerciseEffectiveness`: Tests exercise impact on mood improvement

#### 3. **Mood Analytics Use Case Tests** (`MoodAnalyticsUseCaseTests.kt`) - **UC9: View Mood Analytics**
**Purpose**: Testing mood tracking, pattern recognition, and analytics functionality
**Test Categories**:
- **Mood Trend Analysis**: Tests average mood calculation, trend identification, and volatility detection
- **Pattern Recognition**: Tests weekly patterns, mood triggers, and AI-detected correlations
- **Analytics Dashboard**: Tests statistics calculation, personalized recommendations, and consistency metrics

**Key Test Cases**:
- `should calculate average mood correctly`: Validates mood data processing and statistical calculations
- `should identify weekly mood patterns`: Tests weekend vs weekday mood analysis
- `should generate personalized recommendations`: Tests AI-driven mood improvement suggestions

#### 4. **Accessibility Use Case Tests** (`AccessibilityUseCaseTests.kt`)
**Purpose**: Comprehensive testing of accessibility features for inclusive mental health support
**Test Categories**:
- **Screen Reader Support**: Tests content descriptions, navigation announcements, focus management, and keyboard accessibility
- **Visual Accessibility**: Tests high contrast mode, large text, color blind support, and WCAG compliance
- **Motor & Cognitive Accessibility**: Tests large touch targets, gesture alternatives, haptic feedback, and cognitive load reduction

**Key Test Cases**:
- `should provide comprehensive screen reader support`: Validates 80%+ accessibility feature coverage
- `should validate color contrast compliance`: Ensures WCAG AA/AAA compliance with 4.5+ contrast ratios
- `should support motor accessibility requirements`: Tests touch target sizes and alternative input methods

#### 5. **Integration Tests** (`IntegrationTests.kt`)
**Purpose**: End-to-end workflow testing for complete user journeys
**Test Categories**:
- **Complete Chat Session Workflow**: Tests full conversation flow from start to finish
- **Crisis Intervention Workflow**: Tests crisis detection, immediate response, and follow-up care
- **Mood Support Workflow**: Tests mood concern expression, improvement techniques, and progress tracking
- **Cross-Feature Integration**: Tests integration between mood support and chat recommendations

**Key Test Cases**:
- `shouldCompleteFullChatSessionFromStartToFinish`: Validates complete therapeutic conversation flow
- `shouldCompleteFullCrisisInterventionWorkflow`: Tests crisis detection and emergency response
- `shouldIntegrateMoodSupportWithChatRecommendations`: Tests cross-feature data sharing

#### 6. **User Acceptance Tests** (`UserAcceptanceTests.kt`)
**Purpose**: Real-world user scenario validation
**Test Categories**:
- **AI Chat Support**: Tests various mental health concerns and response quality
- **Crisis Support**: Tests crisis detection and immediate help provision
- **Daily Support**: Tests routine mental health support scenarios
- **Accessibility**: Tests different expression styles and message lengths
- **Response Quality**: Tests helpfulness and professional tone

**Key Test Cases**:
- `asAUserIWantToChatWithAnAITherapist`: Tests core chat functionality with various concerns
- `asAUserInCrisisIWantImmediateHelp`: Tests crisis detection and emergency response
- `asAUserIWantTheAIToUnderstandDifferentWaysOfExpressingFeelings`: Tests natural language understanding

#### 7. **Application Preferences Tests** (`AppPreferencesScreen.kt`)
**Purpose**: Testing comprehensive application configuration management
**Test Categories**:
- **Theme Configuration**: Tests light/dark/system theme selection and persistence
- **Notification Preferences**: Tests individual notification type control and scheduling
- **Privacy Settings**: Tests data sharing toggles and privacy policy management
- **Accessibility Options**: Tests screen reader, high contrast, and large text settings
- **Language & Localization**: Tests language selection and regional preferences

**Key Test Cases**:
- `shouldPersistThemeSelection`: Validates theme persistence across app sessions
- `shouldToggleNotificationPreferences`: Tests individual notification type enable/disable
- `shouldManagePrivacySettings`: Tests data sharing and privacy policy acceptance
- `shouldConfigureAccessibilityOptions`: Tests accessibility feature configuration
- `shouldHandleLanguageSelection`: Tests language and localization settings

#### 8. **Notification Management Tests** (`NotificationsScreen.kt`)
**Purpose**: Testing comprehensive notification system management
**Test Categories**:
- **Notification Types**: Tests mood, exercise, therapist, crisis, achievement notifications
- **Scheduling Control**: Tests notification timing and frequency management
- **Content Management**: Tests personalized notification content generation
- **User Control**: Tests notification preferences and quiet hours configuration
- **Delivery Tracking**: Tests notification history and delivery status monitoring

**Key Test Cases**:
- `shouldScheduleMoodReminders`: Tests daily mood reminder scheduling and delivery
- `shouldManageExerciseNotifications`: Tests exercise reminder timing and content
- `shouldHandleCrisisAlerts`: Tests immediate crisis notification delivery
- `shouldTrackNotificationHistory`: Tests notification history and read status
- `shouldConfigureQuietHours`: Tests do-not-disturb period management

### Disabled Test Cases (Advanced Features)

#### 1. **AI-Generated Journaling Prompts** (`AIGeneratedJournalingPromptsUseCaseTest.kt`)
**Features**: 
- Mood-based prompt generation
- Goal-oriented prompts
- Therapeutic prompts (CBT, DBT)
- Personalization based on user history
- Context-aware prompts (seasonal, holiday-specific)
- Difficulty and length customization

#### 2. **Crisis Intervention** (`CrisisInterventionUseCaseTest.kt`)
**Features**:
- Crisis keyword detection
- Risk assessment (LOW, MEDIUM, HIGH, CRITICAL)
- Emergency resource provision
- Crisis incident logging
- Follow-up care scheduling
- Professional help recommendations

#### 3. **Guided Breathing & Meditation** (`GuidedBreathingMeditationUseCaseTest.kt`)
**Features**:
- Multiple breathing techniques (4-7-8, Box Breathing, Deep Breathing)
- Meditation types (Mindfulness, Body Scan, Loving-Kindness)
- Session management (start, pause, resume, complete)
- Progress tracking and streak calculation
- Audio guidance and background sounds
- Mood improvement tracking

#### 4. **Application Preferences** (`AppPreferencesScreen.kt`)
**Features**:
- Theme configuration (Light/Dark/System)
- Notification preferences management
- Privacy and data sharing settings
- Accessibility options (screen reader, high contrast, large text)
- Language and localization settings
- User preference persistence and synchronization

#### 5. **Notification Management** (`NotificationsScreen.kt`)
**Features**:
- Multiple notification types (Mood, Exercise, Therapist, Crisis, Achievement)
- Notification scheduling and timing control
- Personalized notification content
- User control over notification preferences
- Notification history and delivery tracking
- Quiet hours and priority settings

### Test Architecture & Patterns

#### **Testing Framework**
- **JUnit 5 (Jupiter)**: Modern testing framework with enhanced annotations
- **Mockito/MockK**: Mocking framework for dependency isolation
- **Turbine**: Coroutine testing utilities
- **Given-When-Then**: Structured test organization

#### **Test Organization**
- **Nested Classes**: Logical grouping of related test cases
- **Display Names**: Descriptive test case names for better reporting
- **Parameterized Tests**: Data-driven testing for multiple scenarios
- **Helper Methods**: Reusable test data creation and validation

#### **Coverage Areas**
- **Unit Tests**: Individual component testing
- **Integration Tests**: Cross-component workflow testing
- **User Acceptance Tests**: End-user scenario validation
- **Use Case Tests**: Business logic validation
- **Accessibility Tests**: Inclusive design validation

### Test Execution Commands

```bash
# Run all test suites
./gradlew runAllTests

# Run specific test categories
./gradlew runUnitTests
./gradlew runIntegrationTests
./gradlew runUATTests
./gradlew runUseCaseTests

# Generate comprehensive reports
./gradlew generateTestReports
./gradlew validateTestCoverage

# View test results
open app/build/reports/tests/testDebugUnitTest/index.html
open app/build/reports/jacoco/jacocoTestReport/html/index.html

# Additional test result HTML paths
open app/build/reports/tests/testReleaseUnitTest/index.html
open app/build/reports/androidTests/connected/index.html
open build/reports/problems/problems-report.html
```

### Test Result HTML Paths

#### **Unit Test Reports**
- **Debug Unit Tests**: `app/build/reports/tests/testDebugUnitTest/index.html`
- **Release Unit Tests**: `app/build/reports/tests/testReleaseUnitTest/index.html`

#### **Code Coverage Reports**
- **JaCoCo HTML Report**: `app/build/reports/jacoco/jacocoTestReport/html/index.html`
- **JaCoCo XML Report**: `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml`

#### **Android Test Reports**
- **Connected Android Tests**: `app/build/reports/androidTests/connected/index.html`
- **Android Test Results**: `app/build/outputs/androidTest-results/connected/debug/`

#### **Build & Problem Reports**
- **Build Problems Report**: `build/reports/problems/problems-report.html`
- **Lint Results**: `app/build/reports/lint-results-debug.html`

#### **Quick Access Commands**
```bash
# Open main test reports
open app/build/reports/tests/testDebugUnitTest/index.html
open app/build/reports/jacoco/jacocoTestReport/html/index.html

# Open all test reports
open app/build/reports/tests/testDebugUnitTest/index.html
open app/build/reports/tests/testReleaseUnitTest/index.html
open app/build/reports/androidTests/connected/index.html
open app/build/reports/jacoco/jacocoTestReport/html/index.html
open build/reports/problems/problems-report.html
```

### Test Quality Metrics
- **Coverage Target**: 80%+ code coverage
- **Test Categories**: 10 main categories (Chat History UC6, Coping Exercises UC8, Mood Analytics UC9, Integration, UAT, Use Case, Accessibility, Preferences, Notifications, Unit)
- **Test Cases**: 140+ individual test cases across all categories
- **Accessibility Compliance**: WCAG AA/AAA standards validation
- **Crisis Response**: Sub-second crisis detection and response
- **Preference Management**: Complete configuration testing with persistence validation
- **Notification System**: Comprehensive notification delivery and management testing
- **Chat History Management**: Complete conversation storage, search, and organization testing
- **Exercise Recommendations**: AI-driven therapeutic exercise suggestion and tracking
- **Mood Analytics**: Advanced pattern recognition and forecasting validation

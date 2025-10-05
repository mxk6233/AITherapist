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




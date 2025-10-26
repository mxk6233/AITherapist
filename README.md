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

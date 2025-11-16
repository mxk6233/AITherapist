# Serenity AI Therapist

**Task Status**: Active Development  
**Project**: AI-Powered Mental Health Support Application  
**Platform**: Android

---

## Get the People-First Mental Health App

It's a new era in mental health support. Don't settle for generic wellness apps produced by profit-driven companies that monetize your emotional data. **Serenity AI** is the ethical choice for independent, privacy-respecting mental health technology that gives you personalized therapeutic support exactly when you need it.

**Serenity AI** (internal codename: AITherapist) is an all-new Android mental health application, built with modern Android technologies and AI-powered therapeutic support.

**Get it**: [Download APK](#getting-started) | [Build from Source](#building-from-source)

---

## Project Status

```json
{
  "project": {
    "name": "Serenity AI Therapist",
    "package": "com.serenityai",
    "version": "1.0",
    "versionCode": 1,
    "status": "Active Development",
    "platform": "Android",
    "minSdk": 24,
    "targetSdk": 36
  },
  "build": {
    "status": "Stable",
    "lastUpdated": "Current",
    "testCoverage": "Comprehensive",
    "testSuites": {
      "unit": "Active",
      "integration": "Active",
      "uat": "Active"
    }
  },
  "features": {
    "totalUseCases": 31,
    "implemented": 31,
    "tested": 31,
    "documented": 31
  },
  "testing": {
    "framework": "JUnit 5",
    "coverageTool": "JaCoCo",
    "testFiles": "101+",
    "coverageTarget": "80%+"
  }
}
```

---

## About Serenity AI

**Serenity AI** is an Android application that provides AI-powered therapeutic support and mental health assistance through interactive conversations. Designed for individuals seeking confidential mental health support, this application offers a private, judgment-free environment where users can explore their concerns and receive professional guidance.

### Core Values

- **Privacy First**: Your mental health data stays private and secure
- **Ethical AI**: Transparent, responsible AI that supports without exploiting
- **User Empowerment**: Tools and insights to help you understand and manage your mental wellness
- **Accessibility**: Designed for everyone, regardless of technical expertise
- **Evidence-Based**: Therapeutic approaches grounded in established mental health practices

---

## Technical Architecture

### Tech Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Backend** | Firebase (Authentication, Firestore) |
| **AI Integration** | OpenAI API |
| **Testing** | JUnit 5, Mockito, JaCoCo |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 36 |

### Project Structure

```
AITherapist/
├── app/
│   ├── src/
│   │   ├── main/java/com/serenityai/
│   │   │   ├── usecases/          # Business logic (31 use cases)
│   │   │   ├── data/models/       # Data models
│   │   │   ├── ui/screens/        # Compose UI screens
│   │   │   └── utils/              # Utilities (Greedy Algorithm)
│   │   └── test/                   # Unit tests
│   └── build.gradle.kts
├── tests/
│   ├── unit/                       # Unit tests
│   ├── integration/                # Integration tests
│   └── uat/                        # User Acceptance Tests
└── docs/                           # Documentation
```

---

## Getting Started

### Prerequisites

- **Android Studio**: Arctic Fox or later
- **Android SDK**: 24+ (Android 7.0)
- **Firebase Project**: Setup required for authentication and data storage
- **OpenAI API Key**: Required for AI-powered conversations

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/AITherapist.git
   cd AITherapist
   ```

2. **Firebase Configuration**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Authentication and Firestore Database
   - Download `google-services.json` and place it in `app/` directory

3. **API Configuration**
   - Obtain an OpenAI API key from [OpenAI Platform](https://platform.openai.com/)
   - Configure your API key in the app settings

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   - Or use Android Studio's "Run" button
   - Install the APK on your device or emulator

### Building from Source

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing configuration)
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Run all tests
./gradlew test
```

---

## Testing

### Test Coverage

The project includes comprehensive automated testing with **101+ test files** covering:

- **Unit Tests**: Individual component testing
- **Integration Tests**: Cross-component workflow testing  
- **User Acceptance Tests**: End-user scenario validation
- **Use Case Tests**: All 31 use cases fully tested

### Running Tests

```bash
# Run all test suites
./gradlew runAllTests

# Run specific test suites
./gradlew runUnitTests          # Unit tests only
./gradlew runIntegrationTests   # Integration tests only
./gradlew runUATTests           # UAT tests only

# Generate coverage reports
./gradlew jacocoTestReport

# View coverage report
open app/build/reports/jacoco/jacocoTestReport/html/index.html
```

### Test Reports

| Report Type | Location |
|-------------|----------|
| **Unit Test Report** | `app/build/reports/tests/testDebugUnitTest/index.html` |
| **Coverage Report** | `app/build/reports/jacoco/jacocoTestReport/html/index.html` |
| **Integration Tests** | `app/build/reports/androidTests/connected/index.html` |

### Test Quality Metrics

- **Coverage Target**: 80%+ code coverage
- **Test Cases**: 160+ individual test cases
- **Test Framework**: JUnit 5 (Jupiter)
- **Coverage Tool**: JaCoCo 0.8.11
- **Accessibility**: WCAG AA/AAA standards validation

---

## Documentation

### Available Documentation

| Document | Description | Location |
|----------|-------------|----------|
| **Software Requirements Specification** | Complete requirements documentation | `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` |
| **Architecture & Design** | System architecture and design decisions | `SOFTWARE_ARCHITECTURE_AND_DESIGN.md` |
| **End-User Manual** | User guide with step-by-step instructions | `SOFTWARE_END_USER_MANUAL.md` |
| **Testing Report** | Comprehensive testing practices and results | `SOFTWARE_TESTING_REPORT.md` |
| **Algorithm Documentation** | Greedy algorithm implementation details | `ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md` |
| **Algorithmic Analysis** | Full algorithmic component analysis | `ALGORITHMIC_ANALYSIS.md` |
| **Use Case Verification** | Use case implementation verification | `USE_CASES_VERIFICATION.md` |
| **Test Case Specifications** | Formal test case specifications | `tests/FORMAL_TEST_CASE_SPECIFICATIONS.md` |
| **Enhanced Test Cases** | Detailed test cases with edge cases | `tests/ENHANCED_TEST_CASE_SPECIFICATIONS.md` |
| **JaCoCo Guide** | Code coverage report guide | `tests/JACOCO_COVERAGE_REPORT_GUIDE.md` |

---

## Use Cases

### Implemented Use Cases (31 Total)

**Core Features**:
- UC1: Conduct AI Chat Session
- UC2: Handle Crisis Intervention
- UC3: Log Daily Mood
- UC4: User Registration
- UC5: Personality Onboarding
- UC6: View Chat History
- UC7: User Login
- UC8: Suggest Coping Exercises
- UC9: View Mood Analytics

**Advanced Features**:
- UC10: Manage User Profile
- UC13: Set Application Preferences
- UC14: Receive Daily Affirmations
- UC16: Access Educational Resources
- UC17: Implement Accessibility Features
- UC18: Manage Notifications
- UC24: Personalize User Experience
- UC25: Facilitate User Support
- UC26: Mood Forecasting
- UC27: Guided Breathing & Meditation
- UC28: Therapist Portal Integration
- UC31: Social Support Network Integration
- UC32: Journaling Prompts
- UC34: Group Therapy Simulation Mode
- UC35: Relapse Prevention
- UC37: Predictive Burnout Detection
- UC38: Voice Enabled Therapy Sessions
- UC41: Greedy Algorithm for Strategy Selection

**Status**: All use cases implemented, tested, and documented

---

## Development

### Gradle Tasks

| Task | Description |
|------|-------------|
| `./gradlew assembleDebug` | Build debug APK |
| `./gradlew assembleRelease` | Build release APK |
| `./gradlew test` | Run unit tests |
| `./gradlew jacocoTestReport` | Generate coverage report |
| `./gradlew runAllTests` | Run all test suites |
| `./gradlew clean` | Clean build artifacts |

### Dependencies

All dependencies are managed through:
- **Version Catalog**: `gradle/libs.versions.toml`
- **Firebase BOM**: Ensures compatible Firebase library versions
- **Compose BOM**: Ensures compatible Compose library versions

### Code Quality

- **Linting**: Android Lint configured
- **Code Coverage**: JaCoCo integration
- **Testing**: Comprehensive test suite
- **Documentation**: KDoc comments throughout codebase

---

## Project Statistics

```json
{
  "codebase": {
    "language": "Kotlin",
    "totalUseCases": 31,
    "testFiles": 101,
    "testCases": 160,
    "documentationFiles": 10
  },
  "coverage": {
    "target": "80%+",
    "tool": "JaCoCo",
    "reports": {
      "html": "app/build/reports/jacoco/jacocoTestReport/html/index.html",
      "xml": "app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
    }
  },
  "testing": {
    "framework": "JUnit 5",
    "mocking": "Mockito, MockK",
    "coroutines": "Turbine",
    "suites": ["unit", "integration", "uat"]
  }
}
```

---

## Contributing

### Development Workflow

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Write/update tests
5. Ensure all tests pass (`./gradlew test`)
6. Generate coverage report (`./gradlew jacocoTestReport`)
7. Commit your changes (`git commit -m 'Add amazing feature'`)
8. Push to the branch (`git push origin feature/amazing-feature`)
9. Open a Pull Request

---

## Acknowledgments

- **Firebase**: For authentication and data storage infrastructure
- **OpenAI**: For AI-powered conversation capabilities
- **Jetpack Compose**: For modern Android UI framework
- **JUnit 5**: For comprehensive testing framework
- **JaCoCo**: For code coverage analysis

---


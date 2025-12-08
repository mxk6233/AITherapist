# AI Therapist Application

A comprehensive Android mental health support application providing accessible, personalized therapeutic assistance through AI-powered conversations, mood tracking, educational resources, and therapeutic tools.

## Overview

AI Therapist is a mobile Android application designed to democratize mental health support by providing 24/7 accessible, confidential, and personalized therapeutic assistance. The application combines AI-powered conversations, comprehensive mood tracking, educational resources, and community support to create a holistic mental wellness platform.

## Download from Google Play Store

### For Android Users

**Download AI Therapist from Google Play Store:**

1. **Open Google Play Store** on your Android device
2. **Search** for "AI Therapist" in the search bar
3. **Tap** on the AI Therapist app from the search results
4. **Tap** the "Install" button
5. **Wait** for the installation to complete
6. **Open** the app and follow the setup instructions

**Direct Link:** [Download AI Therapist on Google Play Store](https://play.google.com/store/apps/details?id=com.serenityai)

**System Requirements:**
- Android 7.0 (API Level 24) or higher
- Internet connection for full functionality
- Google Play Services

**Alternative Installation Methods:**
- **APK Installation**: Download the APK file from the releases section (for advanced users)
- **Beta Testing**: Join the beta program through Google Play Store

## Screenshots

The following screenshots showcase key features of the AI Therapist application:

### Main Dashboard
The main dashboard provides quick access to all features with a clean, modern interface. Users can navigate to Chat Features, Mood & Analytics, Support Tools, Wellness Activities, and Settings. The dashboard includes a welcome message, quick action cards, and a daily check-in prompt.

### Mood & Analytics
Comprehensive mood tracking interface showing today's mood selection and access to analytics, forecasting, burnout detection, and relapse prevention features. Users can quickly select their current mood using intuitive smiley face icons.

### Mood Analytics Detail
Visual representation of mood trends over time with insights and patterns to help users understand their emotional patterns. The analytics screen displays charts and trend information with positive feedback messages.

### Mood Forecasting
AI-powered mood forecasting with customizable forecast periods (7, 14, 30, 90 days) and forecast summary showing average mood and expected range. Users can select different time periods to view predictions based on their historical mood data.

### Burnout Risk Assessment
Comprehensive burnout risk assessment with current risk level display, prevention tips, and recent pattern analysis. The screen shows risk level indicators, actionable prevention tips, and weekly burnout pattern charts.

### Relapse Prevention
Early warning system for relapse prevention with current risk indicators, impact levels, and AI-detected patterns with recommended actions. The interface displays risk factors with color-coded impact levels and provides proactive recommendations.

### Chat History
View and manage past chat conversations with search functionality, mood-based filters, and chat analytics including total sessions. Users can search conversations, filter by mood, and view session statistics.

## Key Features

### AI-Powered Therapy
- **AI Chat Sessions**: Conversational AI therapist for emotional support and guidance
- **Voice Therapy**: Voice-enabled therapy sessions with speech-to-text and text-to-speech
- **Crisis Intervention**: Automatic detection and response to crisis situations
- **Chat History**: View and search past conversations

### Mood Tracking & Analytics
- **Daily Mood Logging**: Track mood, energy, stress, anxiety, and sleep levels
- **Mood Analytics**: Visualize trends and patterns over time
- **Mood Forecasting**: AI-powered predictions of future mood trends
- **Burnout Detection**: Early detection of burnout risk through behavior analysis
- **Relapse Prevention**: Monitoring and alerts for relapse indicators

### Support Tools
- **Coping Exercises**: Personalized coping exercise recommendations using Greedy Algorithm
- **Educational Resources**: Access to mental health educational content
- **Journaling Prompts**: AI-generated journaling prompts for therapeutic writing
- **Group Therapy Simulation**: Simulated group therapy sessions with AI-powered virtual participants
- **Community Support Circles**: Peer support communities and support groups
- **Religious Support**: Faith-based guidance tailored to user's religion

### Wellness Activities
- **Daily Affirmations**: Personalized daily affirmations
- **Guided Breathing**: Multiple breathing exercise patterns (4-7-8, Box Breathing, etc.)

### Settings & Personalization
- **User Profile Management**: Profile editing, XP tracking, achievements
- **Application Preferences**: Theme, language, notification settings
- **Accessibility Features**: WCAG AA compliant, screen reader support, text scaling
- **Security Protocols**: Secure authentication, data encryption, privacy controls

## Technology Stack

### Platform & Language
- **Platform**: Android (API 24-36)
- **Language**: Kotlin 2.0.21
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14+ (API 36)
- **Build System**: Gradle 8.10.1 (Kotlin DSL)

### UI Framework
- **UI Framework**: Jetpack Compose 2024.09.00
- **Material Design**: Material 3
- **Navigation**: Navigation Compose 2.7.4
- **Lifecycle**: Lifecycle Runtime KTX 2.9.3

### Architecture
- **Pattern**: Layered Architecture with Use Case Pattern
- **Presentation**: Jetpack Compose + State Management
- **Business Logic**: 31 Use Case classes
- **Data Access**: Firebase integration (Repository Pattern planned)

### Backend & Cloud Services
- **Authentication**: Firebase Authentication 34.5.0
- **Database**: Cloud Firestore 34.5.0
- **Analytics**: Firebase Analytics 34.5.0

### Networking & API
- **HTTP Client**: Retrofit 2.9.0
- **JSON Parsing**: Gson Converter 2.9.0
- **AI Service**: OpenAI API integration

### Concurrency
- **Coroutines**: Kotlin Coroutines Android 1.7.3
- **Firebase Coroutines**: Coroutines Play Services 1.7.3

### Testing
- **Unit Testing**: JUnit Jupiter 5.10.1
- **Mocking**: Mockito 5.8.0, MockK 1.13.8
- **Flow Testing**: Turbine 1.0.0
- **Code Coverage**: JaCoCo 0.8.11

## Project Structure

```
AITherapist/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/serenityai/
│   │   │   │   ├── data/
│   │   │   │   │   ├── models/          # Data models
│   │   │   │   │   └── remote/         # Remote data sources
│   │   │   │   ├── navigation/        # Navigation components
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/        # UI screens (30+ screens)
│   │   │   │   │   └── theme/          # Theme configuration
│   │   │   │   ├── usecases/           # Business logic (31 use cases)
│   │   │   │   └── utils/              # Utility classes
│   │   │   └── res/                    # Resources
│   │   └── test/                       # Unit tests
│   └── build.gradle.kts
├── tests/
│   ├── unit/                           # Unit tests
│   ├── integration/                   # Integration tests
│   └── uat/                            # User Acceptance Tests
├── Documentation/
│   ├── SOFTWARE_REQUIREMENTS_SPECIFICATION.md
│   ├── ARCHITECTURE_STACK_AND_DECISIONS.md
│   ├── SOFTWARE_END_USER_MANUAL.md
│   ├── SOFTWARE_TESTING_REPORT.md
│   ├── ALGORITHMIC_ANALYSIS.md
│   ├── ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md
│   └── NFRs_AND_FRs_ADDRESSED_BY_ARCHITECTURE.md
└── README.md
```

## Getting Started

### Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17 or later
- **Android SDK**: API Level 24-36
- **Firebase Account**: For authentication and database services
- **OpenAI API Key**: For AI-powered chat functionality

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AITherapist
   ```

2. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json` and place it in `app/` directory
   - Enable Firebase Authentication (Email/Password)
   - Enable Cloud Firestore Database
   - Enable Firebase Analytics

3. **Configure OpenAI API**
   - Obtain an API key from [OpenAI](https://platform.openai.com/)
   - Add the API key to your local configuration (see `app/google-services.json.template` for reference)

4. **Build the project**
   ```bash
   ./gradlew build
   ```

5. **Run the application**
   - Open the project in Android Studio
   - Connect an Android device or start an emulator (API 24+)
   - Click Run or use `./gradlew installDebug`

### Running Tests

```bash
# Run all tests
./gradlew test

# Run unit tests only
./gradlew testDebugUnitTest

# Run integration tests
./gradlew testDebugUnitTest --tests "*Integration*"

# Generate code coverage report
./gradlew jacocoTestReport
```

Coverage reports are available at: `app/build/reports/jacoco/jacocoTestReport/html/index.html`

## Architecture

The application follows a **Layered Architecture with Use Case Pattern**:

```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│  (Jetpack Compose UI Screens)      │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     Application Layer               │
│  (31 Use Case Classes)             │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     Data Layer                      │
│  (Models, Firebase Integration)     │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     External Services Layer        │
│  (Firebase, OpenAI API)            │
└─────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Layered Architecture**: Clear separation of concerns (Presentation, Application, Data, External Services)
2. **Use Case Pattern**: 31 use cases encapsulating business logic
3. **Jetpack Compose**: Modern declarative UI framework
4. **Firebase**: Cloud-based backend services
5. **Kotlin Coroutines**: Asynchronous operations
6. **Comprehensive Testing**: Unit, Integration, and UAT tests

## Use Cases

The application implements **31 use cases** covering:

- **Authentication & User Management** (UC4, UC5, UC7, UC10)
- **AI-Powered Therapy** (UC1, UC2, UC6, UC38)
- **Mood Tracking & Analytics** (UC3, UC9, UC26, UC35, UC37)
- **Support Tools** (UC8, UC16, UC25, UC32, UC34, UC39, UC40)
- **Wellness Activities** (UC14, UC27)
- **Settings & Personalization** (UC13, UC17, UC18, UC22, UC23, UC24)
- **Advanced Features** (UC28, UC31, UC41)

## Algorithmic Components

### Greedy Coping Strategy Selection Algorithm (UC41)

The application includes a **Greedy Algorithm** for optimal coping exercise selection:

- **Problem Type**: Multi-constraint Knapsack variant
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(n)
- **Purpose**: Maximize therapeutic benefit while respecting user constraints (time, energy, mood)

See `ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md` for detailed algorithm specification.

## Testing

The project includes comprehensive testing:

- **73 Total Tests** (UC34, UC39, UC40)
  - 39 Unit Tests (53.4%)
  - 9 Integration Tests (12.3%)
  - 25 UAT Tests (34.2%)
- **Code Coverage**: ~100% for use cases
- **Requirements Coverage**: 100% for tested use cases

Test structure:
```
tests/
├── unit/usecases/          # Unit tests for use cases
├── integration/usecases/   # Integration tests
└── uat/usecases/           # User Acceptance Tests
```

## Documentation

Comprehensive documentation is available:

- **[Software Requirements Specification](SOFTWARE_REQUIREMENTS_SPECIFICATION.md)**: Complete requirements documentation
- **[Architecture Stack and Decisions](ARCHITECTURE_STACK_AND_DECISIONS.md)**: Architecture documentation
- **[End User Manual](SOFTWARE_END_USER_MANUAL.md)**: User guide
- **[Software Testing Report](SOFTWARE_TESTING_REPORT.md)**: Testing documentation
- **[Algorithmic Analysis](ALGORITHMIC_ANALYSIS.md)**: Algorithm analysis
- **[Algorithm Implementation](ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md)**: Algorithm specification
- **[NFRs and FRs Addressed](NFRs_AND_FRs_ADDRESSED_BY_ARCHITECTURE.md)**: Requirements-to-architecture mapping

## Non-Functional Requirements

The architecture addresses all major non-functional requirements:

- **Performance**: Response time, throughput, resource usage
- **Reliability**: Availability (99.9%), fault tolerance, data integrity
- **Security**: Authentication, data protection, compliance (HIPAA, GDPR)
- **Usability**: Accessibility (WCAG AA), UX, internationalization
- **Scalability**: User scalability (100K+), performance scalability
- **Maintainability**: Code quality, modularity, documentation

## Contributing

This is a project repository. For contributions:

1. Create a feature branch
2. Make your changes
3. Write/update tests
4. Ensure all tests pass
5. Submit a pull request

## License

[Specify license if applicable]

## Support

For support, please refer to:
- **User Manual**: See `SOFTWARE_END_USER_MANUAL.md`
- **Documentation**: See documentation files in the repository
- **Issues**: Report issues through the issue tracker

## Acknowledgments

- **Firebase**: Backend services and authentication
- **OpenAI**: AI-powered chat functionality
- **Jetpack Compose**: Modern UI framework
- **Material Design**: UI components and guidelines

---

**Version**: 1.0  
**Platform**: Android  
**Status**: Active Development


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
- **HTTP Client**: Retrofit
- **Dependency Injection**: Dagger Hilt (configured but not active)
- **Coroutines**: For asynchronous operations
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

## Model Providers

- **OpenAI GPT-3.5-turbo**: Primary AI model for therapeutic conversations
- **OpenAI GPT-4**: Available for enhanced responses (configurable)
- **Text-to-Speech**: Android's built-in TTS engine for voice responses
- **Speech-to-Text**: Google Speech Recognition for voice input
- **Firebase ML Kit**: Optional integration for on-device text processing

## AI Technology

- **OpenAI GPT-3.5-turbo**: Powers the therapeutic chatbot with compassionate, evidence-based responses
- **Voice Integration**: Text-to-Speech and Speech-to-Text capabilities for accessible communication
- **Therapeutic AI**: Specialized in mental health support, anxiety management, and emotional guidance

## Project Structure

```
app/src/main/java/com/serenityai/
├── data/
│   ├── models/          # Data models and entities
│   └── remote/          # API services and network layer
├── ui/
│   ├── auth/            # Authentication screens
│   ├── chat/            # Chat interface components
│   ├── disclaimer/      # Disclaimer and terms screens
│   └── theme/           # UI theming and styling
└── utils/               # Utility classes and helpers
```


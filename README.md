# AI Therapist

An Android application that provides AI-powered therapeutic support and mental health assistance through interactive conversations.

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

## Permissions

- `RECORD_AUDIO`: For voice input functionality
- `INTERNET`: For API calls and data synchronization

## Project Structure

```
app/src/main/java/com/serenityai/
├── data/
│   ├── models/          # Data models and entities
│   └── remote/          # API services and network layer
├── ui/
│   └── theme/           # UI theming and styling
└── utils/               # Utility classes and helpers
```


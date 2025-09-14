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

## Use Cases Implementation

### **US-01: User Registration (3)**
**Modules:**
- `app/src/main/java/com/serenityai/data/remote/FirebaseSource.kt` - Contains `createUser()` method (currently TODO)
- `app/src/main/java/com/serenityai/data/models/User.kt` - User data model
- `app/src/main/java/com/serenityai/data/models/UserProfile.kt` - Extended user profile model

### **US-02: User Login (2)**
**Modules:**
- `app/src/main/java/com/serenityai/ui/auth/LoginScreen.kt` - Login UI screen with email/password fields
- `app/src/main/java/com/serenityai/data/remote/FirebaseSource.kt` - Firebase authentication integration

### **US-03: Display Disclaimer (1)**
**Modules:**
- `app/src/main/java/com/serenityai/ui/disclaimer/DisclaimerScreen.kt` - Disclaimer UI screen with terms and acceptance

### **US-04: Initiate AI Chat (3)**
**Modules:**
- `app/src/main/java/com/serenityai/InteractiveMainActivity.kt` - Main activity with `ChatScreen()` composable
- `app/src/main/java/com/serenityai/ui/chat/ChatScreen.kt` - Dedicated chat screen component
- `app/src/main/java/com/serenityai/data/models/Session.kt` - Session and ChatMessage data models

### **US-05: Send & Receive Messages (8)**
**Modules:**
- `app/src/main/java/com/serenityai/InteractiveMainActivity.kt` - `generateAIResponse()` function and message handling
- `app/src/main/java/com/serenityai/data/remote/OpenAIApiService.kt` - OpenAI API integration
- `app/src/main/java/com/serenityai/data/remote/OpenAIApiService.kt` - `OpenAIRepository` class
- `app/src/main/java/com/serenityai/utils/SpeechUtils.kt` - Voice input/output functionality
- `app/src/main/java/com/serenityai/data/models/ChatMessage.kt` - Message data structure
- `app/src/main/java/com/serenityai/data/models/AITherapist.kt` - AI response models and types
- `app/src/main/java/com/serenityai/ui/chat/ChatScreen.kt` - Message display and input UI
- `app/src/main/java/com/serenityai/data/models/Session.kt` - Session management for message history

### **US-06: Crisis Detection (5)**
**Modules:**
- `app/src/main/java/com/serenityai/InteractiveMainActivity.kt` - Crisis detection logic in `generateAIResponse()` function
- `app/src/main/java/com/serenityai/data/models/AITherapist.kt` - `ResponseType.CRISIS_SUPPORT` enum
- `app/src/main/java/com/serenityai/data/models/AITherapist.kt` - Crisis response data structures
- `app/src/main/java/com/serenityai/InteractiveMainActivity.kt` - Crisis support UI button ("🚨 Crisis Support")
- `app/src/main/java/com/serenityai/utils/Constants.kt` - Crisis-related constants and error messages

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


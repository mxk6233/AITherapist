# Software Requirements Specification (SRS)
## AI Therapist Application

**Version**: 1.0  
**Document Date**: Based on current implementation  
**Project**: AI Therapist - Android Mental Health Application

---

# Table of Contents

1. [Document Overview](#1-document-overview)
2. [Introduction](#2-introduction)
3. [System Overview](#3-system-overview)
4. [Functional Requirements](#4-functional-requirements)
5. [Non-Functional Requirements](#5-non-functional-requirements)
6. [Algorithmic Component Specification](#6-algorithmic-component-specification)
7. [Requirements Traceability](#7-requirements-traceability)

---

# 1. Document Overview

## 1.1 Purpose

This Software Requirements Specification (SRS) document provides a comprehensive description of the AI Therapist application, including:

- System scope and objectives
- Functional and non-functional requirements
- Use cases and user stories
- Algorithmic component specifications
- System constraints and assumptions

## 1.2 Scope

This document covers all 31 use cases implemented in the AI Therapist application, including:

- **Authentication and User Management**: Registration, login, profile management
- **AI-Powered Therapy**: Chat sessions, voice therapy, crisis intervention
- **Mood Tracking and Analytics**: Mood logging, analytics, forecasting, burnout detection
- **Support Tools**: Educational resources, coping exercises, user support, journaling
- **Wellness Activities**: Daily affirmations, guided breathing
- **Advanced Features**: Therapist portal, social support, greedy algorithm

## 1.3 Document Structure

- **Section 1**: Document Overview
- **Section 2**: Introduction (Mission, Concept of Operation, Scope)
- **Section 3**: System Overview (Context, Features, Users, Constraints)
- **Section 4**: Functional Requirements (Organized by Features/Use Cases)
- **Section 5**: Non-Functional Requirements
- **Section 6**: Algorithmic Component Specification
- **Section 7**: Requirements Traceability

## 1.4 Intended Audience

- **Developers**: Understanding functional requirements and system behavior
- **Project Managers**: Understanding scope and features
- **QA Engineers**: Understanding requirements for testing
- **Stakeholders**: High-level system understanding
- **Architects**: System design and requirements analysis

## 1.5 Definitions and Acronyms

| Term | Definition |
|------|------------|
| **UC** | Use Case |
| **FR** | Functional Requirement |
| **NFR** | Non-Functional Requirement |
| **SRS** | Software Requirements Specification |
| **AI** | Artificial Intelligence |
| **TTS** | Text-to-Speech |
| **STT** | Speech-to-Text |
| **API** | Application Programming Interface |
| **UI** | User Interface |
| **UX** | User Experience |

---

# 2. Introduction

## 2.1 Mission Statement

The AI Therapist application provides accessible, confidential, and personalized mental health support through AI-powered conversations, mood tracking, educational resources, and therapeutic tools. The system aims to:

- **Democratize Mental Health Support**: Make mental health support accessible 24/7 through mobile devices
- **Provide Confidentiality**: Offer a private, judgment-free environment for users
- **Enable Proactive Care**: Detect early signs of mental health issues through analytics
- **Support Personalization**: Deliver personalized therapeutic experiences based on user needs
- **Ensure Scalability**: Support growing user base without proportional cost increase

## 2.2 Concept of Operation

### 2.2.1 Operational Environment

The AI Therapist application operates as a **mobile Android application** deployed through the Google Play Store. The system:

- **Runs on Android devices** (API Level 24+)
- **Connects to cloud services** (Firebase) for data storage and authentication
- **Integrates with AI services** (OpenAI) for therapeutic conversations
- **Supports offline functionality** for core features
- **Provides real-time updates** through cloud synchronization

### 2.2.2 User Interaction Model

Users interact with the application through:

1. **Touch-based UI**: Jetpack Compose screens with Material Design 3
2. **Voice Input**: Speech-to-text for voice therapy sessions
3. **Text Input**: Keyboard input for chat and data entry
4. **Navigation**: Screen-based navigation between features
5. **Notifications**: Push notifications for reminders and alerts

### 2.2.3 System Operation Flow

```
User Registration/Login
    ↓
Personality Onboarding (Optional)
    ↓
Main Dashboard
    ↓
Feature Selection
    ├── AI Chat Therapy
    ├── Mood Tracking
    ├── Support Tools
    ├── Wellness Activities
    └── Settings
    ↓
Feature Usage
    ↓
Data Synchronization (Cloud)
    ↓
Analytics & Insights
```

## 2.3 Product Scope

### 2.3.1 In Scope

The following features are **in scope** for this release:

1. **User Management**: Registration, authentication, profile management
2. **AI Therapy**: Conversational AI therapist, voice therapy, crisis intervention
3. **Mood Tracking**: Daily mood logging, analytics, forecasting, burnout detection
4. **Educational Resources**: Access to mental health educational content
5. **Support Tools**: Coping exercises, journaling prompts, user support, group therapy simulation
6. **Wellness Activities**: Daily affirmations, guided breathing exercises
7. **Social Features**: Friend connections, support groups, progress sharing
8. **Therapist Portal**: Professional access to patient data
9. **Advanced Analytics**: Predictive burnout detection, mood forecasting, relapse prevention
10. **Algorithmic Optimization**: Greedy algorithm for optimal coping strategy selection

### 2.3.2 Out of Scope

The following features are **out of scope** for this release:

1. **iOS Platform**: iOS version not included in this release
2. **Video Therapy**: Video-based therapy sessions
3. **Payment Processing**: In-app purchases or subscription management
4. **Third-Party Integrations**: Integration with external health apps (Fitbit, Apple Health)
5. **Multi-language UI**: UI localization (content supports multiple languages)
6. **Offline AI Processing**: AI processing without internet connection
7. **Real-time Therapist Chat**: Live chat with human therapists

### 2.3.3 Future Considerations

Potential features for future releases:

- iOS platform support
- Video therapy sessions
- Integration with wearable devices
- Advanced AI models
- Multi-language UI support
- Real-time therapist matching

---

# 3. System Overview

## 3.1 System Context

```
┌─────────────────────────────────────────────────────────────┐
│                    External Entities                         │
│                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │   Users      │  │  Therapists  │  │  Support     │     │
│  │              │  │              │  │  Team        │     │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘     │
│         │                 │                  │              │
│         │                 │                  │              │
└─────────┼─────────────────┼──────────────────┼──────────────┘
          │                 │                  │
          │                 │                  │
┌─────────▼─────────────────▼──────────────────▼──────────────┐
│              AI Therapist Application                          │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Presentation Layer (UI)                             │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Application Layer (Use Cases)                       │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Data Layer (Models, Repositories)                   │   │
│  └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
          │                 │                  │
          │                 │                  │
┌─────────▼─────────────────▼──────────────────▼──────────────┐
│                    External Services                          │
│                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │   Firebase   │  │  OpenAI API  │  │  Android OS  │     │
│  │  - Auth      │  │  - GPT       │  │  - Services  │     │
│  │  - Firestore │  │  - Speech    │  │  - Permissions│    │
│  │  - Analytics │  │  - TTS       │  │              │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
└──────────────────────────────────────────────────────────────┘
```

## 3.2 Significant Features

### Feature 1: Authentication and User Management

**Description**: User registration, authentication, and profile management

**Use Cases**:
- UC4: User Registration
- UC7: User Login
- UC5: Personality Onboarding
- UC10: Manage User Profile

**Users**: End Users

**Actions**:
- Register new account
- Login to existing account
- Complete personality assessment
- Manage profile information
- Set preferences

---

### Feature 2: AI-Powered Therapy

**Description**: Conversational AI therapist for emotional support and guidance

**Use Cases**:
- UC1: Conduct AI Chat Session
- UC2: Handle Crisis Intervention
- UC6: View Chat History
- UC38: Voice Enabled Therapy Sessions

**Users**: End Users

**Actions**:
- Start chat session with AI therapist
- Receive AI responses
- Handle crisis situations
- View chat history
- Use voice-based therapy

---

### Feature 3: Mood Tracking and Analytics

**Description**: Comprehensive mood logging, analytics, and predictive features

**Use Cases**:
- UC3: Log Daily Mood
- UC9: View Mood Analytics
- UC26: AI-Powered Mood Forecasting
- UC35: Relapse Prevention Alerts
- UC37: Predictive Burnout Detection

**Users**: End Users

**Actions**:
- Log daily mood entries
- View mood trends and analytics
- Receive mood forecasts
- Get relapse prevention alerts
- View burnout risk assessments

---

### Feature 4: Support Tools

**Description**: Educational resources, coping exercises, and support features

**Use Cases**:
- UC8: Suggest Coping Exercises
- UC16: Access Educational Resources
- UC25: Facilitate User Support
- UC32: AI-Generated Journaling Prompts
- UC34: Group Therapy Simulation Mode

**Users**: End Users

**Actions**:
- Browse educational resources
- Get coping exercise recommendations
- Create support tickets
- Access journaling prompts
- Participate in group therapy simulations

---

### Feature 5: Wellness Activities

**Description**: Daily wellness activities for mental health maintenance

**Use Cases**:
- UC14: Receive Daily Affirmations
- UC27: Guided Breathing & Meditation

**Users**: End Users

**Actions**:
- Receive daily affirmations
- Practice guided breathing exercises
- Track wellness activity progress

---

### Feature 6: Settings and Personalization

**Description**: Application settings, preferences, and personalization

**Use Cases**:
- UC10: Manage User Profile
- UC13: Set Application Preferences
- UC17: Implement Accessibility Features
- UC18: Manage Notifications
- UC22: Monitor System Health
- UC23: Implement Security Protocols
- UC24: Personalize User Experience

**Users**: End Users

**Actions**:
- Configure application preferences
- Set accessibility options
- Manage notifications
- View system health
- Configure security settings
- Personalize experience

---

### Feature 7: Advanced Features

**Description**: Advanced features for therapists and social support

**Use Cases**:
- UC28: Therapist Portal Integration
- UC31: Social Support Network Integration
- UC41: Add Greedy Algorithm

**Users**: End Users, Therapists

**Actions**:
- Access therapist portal (therapists)
- Connect with friends
- Join support groups
- Get optimal exercise recommendations (greedy algorithm)

---

## 3.3 Users and Actors

### 3.3.1 Primary Users

**End Users (Patients)**
- **Description**: Individuals seeking mental health support
- **Characteristics**: 
  - May have varying levels of technical proficiency
  - May have mental health concerns
  - Need accessible, confidential support
- **Goals**:
  - Access mental health support
  - Track mood and progress
  - Learn coping strategies
  - Connect with support network

**Therapists**
- **Description**: Mental health professionals monitoring patient progress
- **Characteristics**:
  - Professional mental health providers
  - Need access to patient data
  - Require secure access
- **Goals**:
  - Monitor patient progress
  - Add therapist notes
  - Generate progress reports
  - Communicate with patients

### 3.3.2 Secondary Users

**Support Team**
- **Description**: Application support staff
- **Goals**:
  - Respond to support tickets
  - Provide help to users
  - Maintain FAQ content

**System Administrators**
- **Description**: Technical staff managing the system
- **Goals**:
  - Monitor system health
  - Manage system configuration
  - Handle technical issues

---

## 3.4 Constraints

### 3.4.1 Technical Constraints

- **Platform**: Android only (API Level 24+)
- **Language**: Kotlin (primary), Java (legacy)
- **UI Framework**: Jetpack Compose
- **Backend**: Firebase services required
- **Internet**: Core features require internet (with offline fallback)
- **Storage**: Limited local storage for offline data

### 3.4.2 Business Constraints

- **Budget**: Cost-effective cloud services (Firebase free tier considerations)
- **Time to Market**: Rapid development and deployment cycles
- **Compliance**: Mental health data privacy regulations (HIPAA, GDPR)
- **Third-Party Services**: Dependency on Firebase and AI service providers

### 3.4.3 Regulatory Constraints

- **Data Privacy**: HIPAA-compliant data handling (where applicable)
- **GDPR**: European data protection compliance
- **Accessibility**: WCAG AA compliance requirements
- **Security**: Secure data encryption and access control

---

## 3.5 Assumptions

1. **Internet Connectivity**: Users have periodic internet access for data synchronization
2. **Device Capabilities**: Android devices support required features (microphone, storage)
3. **User Behavior**: Users will provide accurate mood and activity data
4. **Service Availability**: Firebase and AI services remain available and stable
5. **User Privacy**: Users consent to data collection and processing
6. **Language Support**: Users understand English (UI language)
7. **Device Storage**: Sufficient device storage available for app and offline data

---

# 4. Functional Requirements

## 4.1 Functional Requirements by Feature

### Feature 1: Authentication and User Management

#### FR-UC4-01: User Registration
**Requirement ID**: FR-UC4-01  
**Use Case**: UC4: User Registration  
**Priority**: High  
**Description**: System SHALL allow users to register a new account with email and password.

**Acceptance Criteria**:
- System SHALL validate email format
- System SHALL enforce password strength requirements (minimum 8 characters, letters and numbers)
- System SHALL create user account in Firebase Authentication
- System SHALL store user profile in Firestore
- System SHALL redirect to personality onboarding after successful registration

**User Story**: As a new user, I want to create an account so that I can access the application.

---

#### FR-UC7-01: User Login
**Requirement ID**: FR-UC7-01  
**Use Case**: UC7: User Login  
**Priority**: High  
**Description**: System SHALL allow users to login with email and password.

**Acceptance Criteria**:
- System SHALL authenticate credentials via Firebase Authentication
- System SHALL create user session upon successful login
- System SHALL redirect to main dashboard after login
- System SHALL display error message for invalid credentials
- System SHALL provide "Forgot Password" functionality

**User Story**: As a registered user, I want to login to my account so that I can access my data and continue using the application.

---

#### FR-UC5-01: Personality Onboarding
**Requirement ID**: FR-UC5-01  
**Use Case**: UC5: Personality Onboarding  
**Priority**: Medium  
**Description**: System SHALL collect user personality preferences during onboarding.

**Acceptance Criteria**:
- System SHALL present personality assessment questions
- System SHALL collect communication style preferences
- System SHALL collect therapy approach preferences
- System SHALL save preferences to user profile
- System SHALL use preferences for personalization

**User Story**: As a new user, I want to complete a personality assessment so that the application can personalize my experience.

---

#### FR-UC10-01: User Profile Management
**Requirement ID**: FR-UC10-01  
**Use Case**: UC10: Manage User Profile  
**Priority**: High  
**Description**: System SHALL allow users to view and update their profile information.

**Acceptance Criteria**:
- System SHALL display user profile information
- System SHALL allow editing name, email, date of birth
- System SHALL track XP points and level
- System SHALL track current and longest streaks
- System SHALL display achievements and badges

**User Story**: As a user, I want to manage my profile so that I can keep my information up to date and track my progress.

---

### Feature 2: AI-Powered Therapy

#### FR-UC1-01: AI Chat Session
**Requirement ID**: FR-UC1-01  
**Use Case**: UC1: Conduct AI Chat Session  
**Priority**: High  
**Description**: System SHALL provide AI-powered conversational therapy sessions.

**Acceptance Criteria**:
- System SHALL generate contextual AI responses
- System SHALL maintain conversation context
- System SHALL detect crisis keywords
- System SHALL save conversation history
- System SHALL provide response within 2-5 seconds

**User Story**: As a user, I want to chat with an AI therapist so that I can receive emotional support and guidance.

---

#### FR-UC2-01: Crisis Intervention
**Requirement ID**: FR-UC2-01  
**Use Case**: UC2: Handle Crisis Intervention  
**Priority**: Critical  
**Description**: System SHALL automatically detect and respond to crisis situations.

**Acceptance Criteria**:
- System SHALL detect crisis keywords (suicide, self-harm, etc.)
- System SHALL display crisis intervention screen immediately
- System SHALL provide crisis hotline numbers
- System SHALL offer emergency resources
- System SHALL allow user to mark themselves as safe

**User Story**: As a user in crisis, I want immediate access to crisis resources so that I can get help when needed.

---

#### FR-UC6-01: Chat History
**Requirement ID**: FR-UC6-01  
**Use Case**: UC6: View Chat History  
**Priority**: Medium  
**Description**: System SHALL allow users to view past chat conversations.

**Acceptance Criteria**:
- System SHALL display list of past chat sessions
- System SHALL allow searching conversations
- System SHALL filter by date range
- System SHALL display full conversation when selected
- System SHALL allow exporting conversation data

**User Story**: As a user, I want to view my chat history so that I can review past conversations and track my progress.

---

#### FR-UC38-01: Voice Therapy Sessions
**Requirement ID**: FR-UC38-01  
**Use Case**: UC38: Voice Enabled Therapy Sessions  
**Priority**: Medium  
**Description**: System SHALL provide voice-based therapy sessions using speech recognition and text-to-speech.

**Acceptance Criteria**:
- System SHALL convert voice input to text (speech-to-text)
- System SHALL convert AI responses to speech (text-to-speech)
- System SHALL support multiple languages
- System SHALL handle recognition errors gracefully
- System SHALL track session duration and history

**User Story**: As a user, I want to have voice-based therapy sessions so that I can interact more naturally with the AI therapist.

---

### Feature 3: Mood Tracking and Analytics

#### FR-UC3-01: Daily Mood Logging
**Requirement ID**: FR-UC3-01  
**Use Case**: UC3: Log Daily Mood  
**Priority**: High  
**Description**: System SHALL allow users to log daily mood entries.

**Acceptance Criteria**:
- System SHALL collect mood level (1-10 scale)
- System SHALL collect energy, stress, anxiety, sleep levels
- System SHALL allow optional notes and tags
- System SHALL save mood entry with timestamp
- System SHALL validate input ranges

**User Story**: As a user, I want to log my daily mood so that I can track my emotional state over time.

---

#### FR-UC9-01: Mood Analytics
**Requirement ID**: FR-UC9-01  
**Use Case**: UC9: View Mood Analytics  
**Priority**: High  
**Description**: System SHALL provide mood analytics and trend visualization.

**Acceptance Criteria**:
- System SHALL display mood trends over time
- System SHALL calculate average mood for selected period
- System SHALL identify mood patterns
- System SHALL generate insights about mood trends
- System SHALL allow filtering by time period (week, month, year)

**User Story**: As a user, I want to view my mood analytics so that I can understand my emotional patterns and trends.

---

#### FR-UC26-01: Mood Forecasting
**Requirement ID**: FR-UC26-01  
**Use Case**: UC26: AI-Powered Mood Forecasting  
**Priority**: Medium  
**Description**: System SHALL predict future mood trends using AI.

**Acceptance Criteria**:
- System SHALL generate 7-day mood forecast
- System SHALL provide confidence scores for predictions
- System SHALL identify trend direction (increasing, decreasing, stable)
- System SHALL provide recommendations based on forecast
- System SHALL update forecast based on new mood data

**User Story**: As a user, I want to see mood forecasts so that I can prepare for potential mood changes.

---

#### FR-UC37-01: Burnout Detection
**Requirement ID**: FR-UC37-01  
**Use Case**: UC37: Predictive Burnout Detection  
**Priority**: High  
**Description**: System SHALL detect early signs of burnout risk through behavior analysis.

**Acceptance Criteria**:
- System SHALL assess burnout risk from multiple factors (mood, activity, stress, sleep)
- System SHALL calculate risk level (LOW, MODERATE, HIGH, CRITICAL)
- System SHALL identify risk factors with severity scores
- System SHALL detect early warning signs
- System SHALL generate personalized prevention recommendations
- System SHALL trigger interventions automatically when risk is HIGH or CRITICAL

**User Story**: As a user, I want to know my burnout risk so that I can take preventive measures before burnout occurs.

---

#### FR-UC35-01: Relapse Prevention
**Requirement ID**: FR-UC35-01  
**Use Case**: UC35: Relapse Prevention Alerts  
**Priority**: High  
**Description**: System SHALL detect relapse risk and provide prevention alerts.

**Acceptance Criteria**:
- System SHALL monitor mood patterns for relapse indicators
- System SHALL detect early warning signs
- System SHALL send alerts when risk is detected
- System SHALL provide prevention recommendations
- System SHALL offer intervention resources

**User Story**: As a user, I want to receive relapse prevention alerts so that I can take action before relapsing.

---

### Feature 4: Support Tools

#### FR-UC8-01: Coping Exercise Recommendations
**Requirement ID**: FR-UC8-01  
**Use Case**: UC8: Suggest Coping Exercises  
**Priority**: High  
**Description**: System SHALL provide personalized coping exercise recommendations.

**Acceptance Criteria**:
- System SHALL recommend exercises based on current mood
- System SHALL filter exercises by difficulty and duration
- System SHALL track exercise completion
- System SHALL provide exercise instructions
- System SHALL use greedy algorithm for optimal selection (UC41)

**User Story**: As a user, I want to get personalized coping exercise recommendations so that I can manage my mental health effectively.

---

#### FR-UC16-01: Educational Resources
**Requirement ID**: FR-UC16-01  
**Use Case**: UC16: Access Educational Resources  
**Priority**: Medium  
**Description**: System SHALL provide access to mental health educational content.

**Acceptance Criteria**:
- System SHALL provide categorized educational content (articles, videos, guides)
- System SHALL allow filtering by category and format
- System SHALL provide search functionality
- System SHALL personalize recommendations based on user profile
- System SHALL track learning progress (0-100%)
- System SHALL fetch resources from Firebase with fallback to hardcoded data

**User Story**: As a user, I want to access educational resources so that I can learn about mental health and self-care strategies.

---

#### FR-UC25-01: User Support
**Requirement ID**: FR-UC25-01  
**Use Case**: UC25: Facilitate User Support  
**Priority**: Medium  
**Description**: System SHALL provide comprehensive user support features.

**Acceptance Criteria**:
- System SHALL allow creating support tickets
- System SHALL provide searchable FAQ
- System SHALL provide contextual help
- System SHALL accept user feedback with ratings
- System SHALL track ticket status and history

**User Story**: As a user, I want to get help when I need it so that I can effectively use the application.

---

#### FR-UC32-01: Journaling Prompts
**Requirement ID**: FR-UC32-01  
**Use Case**: UC32: AI-Generated Journaling Prompts  
**Priority**: Medium  
**Description**: System SHALL provide AI-generated journaling prompts.

**Acceptance Criteria**:
- System SHALL generate personalized prompts based on mood
- System SHALL provide prompts in different categories
- System SHALL allow saving journal entries
- System SHALL track prompt completion
- System SHALL generate new prompts on demand

**User Story**: As a user, I want to receive journaling prompts so that I can engage in therapeutic writing.

---

#### FR-UC34-01: Group Therapy Simulation
**Requirement ID**: FR-UC34-01  
**Use Case**: UC34: Group Therapy Simulation Mode  
**Priority**: Medium  
**Description**: System SHALL provide simulated group therapy sessions with AI-powered virtual participants.

**Acceptance Criteria**:
- System SHALL create group therapy sessions
- System SHALL generate virtual participants with diverse personalities
- System SHALL simulate realistic group dynamics
- System SHALL provide peer support responses
- System SHALL track session participation and progress

**User Story**: As a user, I want to participate in group therapy simulations so that I can practice in a supportive group environment.

---

### Feature 5: Wellness Activities

#### FR-UC14-01: Daily Affirmations
**Requirement ID**: FR-UC14-01  
**Use Case**: UC14: Receive Daily Affirmations  
**Priority**: Low  
**Description**: System SHALL provide personalized daily affirmations.

**Acceptance Criteria**:
- System SHALL deliver daily affirmations
- System SHALL personalize based on user preferences
- System SHALL allow viewing affirmation history
- System SHALL provide multiple affirmations per day

**User Story**: As a user, I want to receive daily affirmations so that I can maintain a positive mindset.

---

#### FR-UC27-01: Guided Breathing
**Requirement ID**: FR-UC27-01  
**Use Case**: UC27: Guided Breathing & Meditation  
**Priority**: Medium  
**Description**: System SHALL provide guided breathing exercises.

**Acceptance Criteria**:
- System SHALL provide breathing exercise instructions
- System SHALL display visual breathing guides
- System SHALL track session duration
- System SHALL allow pausing and resuming
- System SHALL track exercise history

**User Story**: As a user, I want to practice guided breathing so that I can manage stress and anxiety.

---

### Feature 6: Settings and Personalization

#### FR-UC13-01: Application Preferences
**Requirement ID**: FR-UC13-01  
**Use Case**: UC13: Set Application Preferences  
**Priority**: Medium  
**Description**: System SHALL allow users to configure application preferences.

**Acceptance Criteria**:
- System SHALL allow setting theme (light, dark, system)
- System SHALL allow configuring notification preferences
- System SHALL allow setting language preferences
- System SHALL allow configuring privacy settings
- System SHALL persist preferences across sessions

**User Story**: As a user, I want to configure application preferences so that I can customize my experience.

---

#### FR-UC17-01: Accessibility Features
**Requirement ID**: FR-UC17-01  
**Use Case**: UC17: Implement Accessibility Features  
**Priority**: High  
**Description**: System SHALL provide accessibility features for inclusive use.

**Acceptance Criteria**:
- System SHALL support screen readers
- System SHALL support text scaling
- System SHALL support high contrast mode
- System SHALL provide accessibility labels
- System SHALL support keyboard navigation

**User Story**: As a user with accessibility needs, I want the application to be accessible so that I can use it effectively.

---

#### FR-UC18-01: Notification Management
**Requirement ID**: FR-UC18-01  
**Use Case**: UC18: Manage Notifications  
**Priority**: Medium  
**Description**: System SHALL allow users to manage notifications.

**Acceptance Criteria**:
- System SHALL allow enabling/disabling notifications
- System SHALL allow configuring notification categories
- System SHALL allow setting notification schedules
- System SHALL allow setting notification priorities
- System SHALL respect user notification preferences

**User Story**: As a user, I want to manage notifications so that I receive relevant alerts without being overwhelmed.

---

### Feature 7: Advanced Features

#### FR-UC28-01: Therapist Portal
**Requirement ID**: FR-UC28-01  
**Use Case**: UC28: Therapist Portal Integration  
**Priority**: Medium  
**Description**: System SHALL provide therapist access to patient data.

**Acceptance Criteria**:
- System SHALL allow assigning patients to therapists
- System SHALL allow viewing patient mood history
- System SHALL allow adding therapist notes
- System SHALL allow generating progress reports
- System SHALL enforce access control (therapists can only access assigned patients)

**User Story**: As a therapist, I want to access patient data so that I can monitor progress and provide better care.

---

#### FR-UC31-01: Social Support Network
**Requirement ID**: FR-UC31-01  
**Use Case**: UC31: Social Support Network Integration  
**Priority**: Low  
**Description**: System SHALL provide social support network features.

**Acceptance Criteria**:
- System SHALL allow sending friend requests
- System SHALL allow accepting/declining friend requests
- System SHALL allow creating and joining support groups
- System SHALL allow sharing progress with friends
- System SHALL allow sending encouragement messages

**User Story**: As a user, I want to connect with friends and support groups so that I can build a support network.

---

#### FR-UC41-01: Greedy Algorithm
**Requirement ID**: FR-UC41-01  
**Use Case**: UC41: Add Greedy Algorithm  
**Priority**: Medium  
**Description**: System SHALL use greedy algorithm for optimal coping strategy selection.

**Acceptance Criteria**:
- System SHALL select optimal exercises based on effectiveness-to-effort ratio
- System SHALL respect user constraints (time, energy, mood)
- System SHALL provide top N recommendations
- System SHALL calculate total expected benefit
- System SHALL provide selection explanation
- System SHALL provide algorithm statistics

**User Story**: As a user, I want to get optimal exercise recommendations so that I can maximize therapeutic benefit within my constraints.

---

## 4.2 Functional Requirements Summary

| Feature Category | Use Cases | Total Requirements |
|-----------------|-----------|-------------------|
| Authentication & User Management | UC4, UC5, UC7, UC10 | 4 |
| AI-Powered Therapy | UC1, UC2, UC6, UC38 | 4 |
| Mood Tracking & Analytics | UC3, UC9, UC26, UC35, UC37 | 5 |
| Support Tools | UC8, UC16, UC25, UC32, UC34 | 5 |
| Wellness Activities | UC14, UC27 | 2 |
| Settings & Personalization | UC13, UC17, UC18, UC22, UC23, UC24 | 6 |
| Advanced Features | UC28, UC31, UC41 | 3 |
| **TOTAL** | **31 Use Cases** | **29 Core Requirements** |

---

# 5. Non-Functional Requirements

## 5.1 Performance Requirements

### NFR-PERF-01: Response Time
**Requirement ID**: NFR-PERF-01  
**Priority**: High  
**Description**: System SHALL respond to user actions within specified time limits.

**Acceptance Criteria**:
- AI responses SHALL be generated within 2-5 seconds
- Data loading SHALL complete within 1 second for resource lists
- Screen navigation SHALL occur within 500ms
- Mood entry save SHALL complete within 1 second

---

### NFR-PERF-02: Throughput
**Requirement ID**: NFR-PERF-02  
**Priority**: Medium  
**Description**: System SHALL support specified concurrent user loads.

**Acceptance Criteria**:
- System SHALL support 1000+ concurrent users
- System SHALL handle 100+ API calls per second
- System SHALL sync 1000+ records per minute

---

### NFR-PERF-03: Resource Usage
**Requirement ID**: NFR-PERF-03  
**Priority**: Medium  
**Description**: System SHALL optimize resource usage.

**Acceptance Criteria**:
- Application SHALL use less than 200MB RAM
- Application SHALL drain less than 5% battery per hour of active use
- Application SHALL use less than 100MB local storage for offline data
- Application SHALL minimize network bandwidth usage

---

## 5.2 Reliability Requirements

### NFR-REL-01: Availability
**Requirement ID**: NFR-REL-01  
**Priority**: High  
**Description**: System SHALL maintain high availability.

**Acceptance Criteria**:
- System SHALL achieve 99.9% uptime
- System SHALL automatically recover from transient failures
- Core features SHALL be available offline

---

### NFR-REL-02: Fault Tolerance
**Requirement ID**: NFR-REL-02  
**Priority**: High  
**Description**: System SHALL handle errors gracefully.

**Acceptance Criteria**:
- System SHALL provide user-friendly error messages
- System SHALL fallback to local data when network unavailable
- System SHALL maintain data consistency across devices

---

### NFR-REL-03: Data Integrity
**Requirement ID**: NFR-REL-03  
**Priority**: High  
**Description**: System SHALL ensure data integrity.

**Acceptance Criteria**:
- System SHALL validate input at all layers
- System SHALL automatically backup data to cloud
- System SHALL support data recovery from backups

---

## 5.3 Security Requirements

### NFR-SEC-01: Authentication
**Requirement ID**: NFR-SEC-01  
**Priority**: Critical  
**Description**: System SHALL provide secure authentication.

**Acceptance Criteria**:
- System SHALL use Firebase Authentication
- System SHALL enforce strong password requirements
- System SHALL manage secure sessions
- System SHALL support password reset

---

### NFR-SEC-02: Data Protection
**Requirement ID**: NFR-SEC-02  
**Priority**: Critical  
**Description**: System SHALL protect user data.

**Acceptance Criteria**:
- System SHALL encrypt data in transit (HTTPS)
- System SHALL encrypt data at rest
- System SHALL protect user privacy
- System SHALL implement role-based access control

---

### NFR-SEC-03: Compliance
**Requirement ID**: NFR-SEC-03  
**Priority**: High  
**Description**: System SHALL comply with regulations.

**Acceptance Criteria**:
- System SHALL comply with HIPAA (where applicable)
- System SHALL comply with GDPR
- System SHALL implement data retention policies
- System SHALL provide data export functionality

---

## 5.4 Usability Requirements

### NFR-USE-01: Accessibility
**Requirement ID**: NFR-USE-01  
**Priority**: High  
**Description**: System SHALL be accessible to all users.

**Acceptance Criteria**:
- System SHALL comply with WCAG AA standards
- System SHALL support screen readers
- System SHALL support text scaling
- System SHALL support keyboard navigation

---

### NFR-USE-02: User Experience
**Requirement ID**: NFR-USE-02  
**Priority**: High  
**Description**: System SHALL provide intuitive user experience.

**Acceptance Criteria**:
- System SHALL follow Material Design 3 guidelines
- System SHALL provide clear error messages
- System SHALL provide loading indicators
- System SHALL indicate offline status clearly

---

### NFR-USE-03: Internationalization
**Requirement ID**: NFR-USE-03  
**Priority**: Low  
**Description**: System SHALL support multiple languages.

**Acceptance Criteria**:
- System SHALL support multiple languages for content
- System SHALL format dates, times, numbers according to locale
- System SHALL support right-to-left languages (future)

---

## 5.5 Scalability Requirements

### NFR-SCAL-01: User Scalability
**Requirement ID**: NFR-SCAL-01  
**Priority**: Medium  
**Description**: System SHALL scale to support growing user base.

**Acceptance Criteria**:
- System SHALL support 100K+ users
- System SHALL handle 10K+ concurrent users
- System SHALL efficiently handle growing data volumes

---

### NFR-SCAL-02: Performance Scalability
**Requirement ID**: NFR-SCAL-02  
**Priority**: Medium  
**Description**: System SHALL maintain performance as scale increases.

**Acceptance Criteria**:
- System SHALL support horizontal scaling
- System SHALL implement efficient caching strategies
- System SHALL optimize database queries

---

## 5.6 Maintainability Requirements

### NFR-MAIN-01: Code Quality
**Requirement ID**: NFR-MAIN-01  
**Priority**: Medium  
**Description**: System SHALL maintain high code quality.

**Acceptance Criteria**:
- Code SHALL follow Kotlin coding conventions
- Code SHALL be reviewed before merging
- Code SHALL be comprehensively documented

---

### NFR-MAIN-02: Testing
**Requirement ID**: NFR-MAIN-02  
**Priority**: High  
**Description**: System SHALL have comprehensive test coverage.

**Acceptance Criteria**:
- Unit tests SHALL achieve 80%+ coverage
- Integration tests SHALL cover critical workflows
- UAT tests SHALL validate user scenarios

---

### NFR-MAIN-03: Modularity
**Requirement ID**: NFR-MAIN-03  
**Priority**: Medium  
**Description**: System SHALL have modular architecture.

**Acceptance Criteria**:
- System SHALL be organized into clear modules
- Modules SHALL have minimal dependencies
- System SHALL support easy updates and enhancements

---

# 6. Algorithmic Component Specification

## 6.1 Overview

The **Greedy Coping Strategy Selection Algorithm** (UC41) is a significant algorithmic component that solves a constrained optimization problem to maximize therapeutic benefit while respecting user constraints.

**Algorithm Type**: Greedy Algorithm (Heuristic-based)  
**Problem Class**: Constrained Optimization / Resource Allocation  
**Time Complexity**: O(n log n)  
**Space Complexity**: O(n)  
**Optimality**: Heuristic (provides good solutions efficiently, not guaranteed optimal)

## 6.2 Problem Formulation

### 6.2.1 Problem Statement

Given:
- A set of available coping exercises: `E = {e₁, e₂, ..., eₙ}`
- Each exercise `eᵢ` has:
  - Effectiveness: `eff(eᵢ) ∈ [0, 5]` (rating scale)
  - Time cost: `time(eᵢ) ∈ ℕ` (minutes)
  - Energy cost: `energy(eᵢ) ∈ {1, 2, 3}` (based on difficulty)
  - Mood tags: `tags(eᵢ)` (list of applicable moods)
  - Past success: `completed(eᵢ)` (times completed)
  - Familiarity: `familiar(eᵢ) ∈ {true, false}`

- User constraints:
  - Available time: `T ∈ ℕ` (minutes)
  - Available energy: `E ∈ [1, 10]`
  - Current mood: `mood ∈ String`
  - Stress level: `stress ∈ [1, 10]`

**Objective**: Select a subset `S ⊆ E` that:
1. Maximizes total therapeutic benefit: `maximize Σ(eᵢ ∈ S) benefit(eᵢ)`
2. Subject to constraints:
   - `Σ(eᵢ ∈ S) time(eᵢ) ≤ T`
   - `Σ(eᵢ ∈ S) energy(eᵢ) ≤ E`

### 6.2.2 Problem Classification

- **Type**: Multi-constraint Knapsack Problem variant
- **Complexity**: NP-Hard (if solved optimally)
- **Approach**: Greedy heuristic (polynomial-time approximation)

## 6.3 Algorithm Design

### 6.3.1 Algorithm Overview

The algorithm uses a **greedy strategy** that:
1. Scores each exercise using a composite scoring function
2. Sorts exercises by score (descending)
3. Iteratively selects highest-scoring exercises that fit constraints
4. Stops when constraints are exhausted

### 6.3.2 Algorithm Pseudocode

```
Algorithm: GreedyCopingStrategySelection
Input: Exercises E, Constraints C
Output: Selected exercises S

1. S ← ∅
2. remainingTime ← C.availableTimeMinutes
3. remainingEnergy ← C.currentEnergyLevel
4. 
5. // Step 1: Score all exercises
6. scoredExercises ← []
7. for each exercise e in E:
8.     score ← calculateGreedyScore(e, C)
9.     scoredExercises.append((e, score))
10.
11. // Step 2: Sort by score (descending)
12. sortedExercises ← sort(scoredExercises, by=score, descending=true)
13.
14. // Step 3: Greedy selection
15. for each (exercise, score) in sortedExercises:
16.     if time(e) ≤ remainingTime AND energy(e) ≤ remainingEnergy:
17.         S ← S ∪ {exercise}
18.         remainingTime ← remainingTime - time(e)
19.         remainingEnergy ← remainingEnergy - energy(e)
20.         if remainingTime ≤ 0 OR remainingEnergy ≤ 0:
21.             break
22.
23. return S
```

### 6.3.3 Greedy Score Calculation

The greedy score is calculated using the formula:

```
score(e) = (effectiveness(e) × matchQuality(e)) / (effort(e) × timeCost(e))
```

Where:
- **effectiveness(e)**: Exercise effectiveness rating (0-5)
- **matchQuality(e)**: Contextual match score (1.0 - 2.3)
- **effort(e)**: Effort required (1.0, 2.0, or 3.0)
- **timeCost(e)**: Time in minutes

**Match Quality Calculation**:
```
matchQuality(e) = 1.0
                + 0.5 (if mood matches)
                + 0.3 (if high stress AND exercise matches stress)
                + min(0.5, timesCompleted × 0.1) (past success boost)
                + 0.2 (if exercise is familiar)
```

**Maximum matchQuality**: 2.3 (all conditions met)  
**Minimum matchQuality**: 1.0 (no matches)

## 6.4 Algorithm Logic

### 6.4.1 Greedy Choice Property

**Greedy Choice**: At each step, select the exercise with the highest score that fits within remaining constraints.

**Rationale**: 
- Higher score = better effectiveness-to-effort ratio
- Maximizes immediate benefit per unit cost
- Assumes local optimality leads to global good solution

### 6.4.2 Why Greedy Works Well

1. **Monotonic Benefit**: Higher-scored exercises generally provide more benefit
2. **Diminishing Returns**: Multiple exercises provide additive benefit (no negative interactions)
3. **Constraint Independence**: Time and energy constraints are independent
4. **Heuristic Quality**: Score function captures key optimization factors

### 6.4.3 Score Function Components

The greedy score function incorporates:
- ✅ **Effectiveness**: Direct therapeutic benefit
- ✅ **Effort**: Cost minimization (lower effort = better)
- ✅ **Time Efficiency**: Time cost minimization
- ✅ **Contextual Relevance**: Mood and stress matching
- ✅ **Personalization**: Past success and familiarity

## 6.5 Complexity Analysis

### 6.5.1 Time Complexity

- **Scoring**: O(n) - Score each exercise once
- **Sorting**: O(n log n) - Sort exercises by score
- **Selection**: O(n) - Iterate through sorted exercises
- **Total**: **O(n log n)** - Dominated by sorting step

Where `n` = number of available exercises

### 6.5.2 Space Complexity

- **Scored Exercises**: O(n) - Store scored exercises
- **Selected Exercises**: O(n) - Worst case, all exercises selected
- **Total**: **O(n)** - Linear space complexity

## 6.6 Mapping to Software Requirements

### FR-UC41-01: Optimal Strategy Selection
**Mapping**: Algorithm implements optimal strategy selection using greedy approach
- **Input**: Exercises, user constraints
- **Output**: Selected exercises maximizing benefit
- **Validation**: Constraints respected (time, energy)

### FR-UC41-02: Constraint Validation
**Mapping**: Algorithm validates constraints during selection
- **Time Constraint**: `Σ time(eᵢ) ≤ T`
- **Energy Constraint**: `Σ energy(eᵢ) ≤ E`
- **Validation**: Exercises selected only if constraints allow

### FR-UC41-03: Top N Recommendations
**Mapping**: Algorithm provides top N recommendations
- **Selection**: Highest-scoring exercises selected first
- **Ordering**: Results sorted by score (descending)
- **Limit**: Can be limited to top N exercises

### FR-UC41-04: Benefit Calculation
**Mapping**: Algorithm calculates total expected benefit
- **Formula**: `Σ benefit(eᵢ)` for selected exercises
- **Benefit**: Based on effectiveness and match quality
- **Output**: Total benefit provided to user

### FR-UC41-05: Selection Explanation
**Mapping**: Algorithm provides selection rationale
- **Score Components**: Effectiveness, effort, time, match quality
- **Reasoning**: Why exercises were selected
- **Constraints**: How constraints influenced selection

## 6.7 Implementation Details

### 6.7.1 Input Parameters

```kotlin
data class GreedyAlgorithmConstraints(
    val availableTimeMinutes: Int,
    val currentEnergyLevel: Int,
    val currentMood: String,
    val currentStressLevel: Int
)
```

### 6.7.2 Output Structure

```kotlin
data class GreedyAlgorithmResult(
    val selectedExercises: List<CopingExercise>,
    val totalExpectedBenefit: Float,
    val totalTimeInvestment: Int,
    val explanation: String,
    val statistics: GreedyAlgorithmStats
)
```

### 6.7.3 Key Functions

1. **calculateGreedyScore()**: Calculate score for each exercise
2. **selectOptimalStrategies()**: Main algorithm implementation
3. **validateConstraints()**: Validate selected exercises fit constraints
4. **calculateTotalBenefit()**: Calculate total expected benefit
5. **generateExplanation()**: Generate selection explanation

## 6.8 Performance Characteristics

### 6.8.1 Execution Time

- **Small Input** (10 exercises): < 1ms
- **Medium Input** (100 exercises): < 10ms
- **Large Input** (1000 exercises): < 100ms

### 6.8.2 Memory Usage

- **Small Input**: < 1KB
- **Medium Input**: < 10KB
- **Large Input**: < 100KB

### 6.8.3 Scalability

- **Linear Growth**: Time complexity O(n log n) scales well
- **Memory Efficient**: O(n) space complexity is acceptable
- **Real-time Performance**: Suitable for real-time recommendations

## 6.9 Limitations and Future Enhancements

### 6.9.1 Current Limitations

1. **Heuristic Nature**: Not guaranteed optimal solution
2. **Single Objective**: Optimizes benefit only (could optimize multiple objectives)
3. **Static Scoring**: Score function doesn't adapt based on user feedback
4. **No Learning**: Doesn't learn from user preferences over time

### 6.9.2 Future Enhancements

1. **Machine Learning**: Learn optimal scoring from user feedback
2. **Multi-Objective Optimization**: Optimize multiple objectives simultaneously
3. **Dynamic Adaptation**: Adapt scoring based on user behavior
4. **A/B Testing**: Test different scoring strategies

---

# 7. Requirements Traceability

## 7.1 Functional Requirements to Use Cases Mapping

| Requirement ID | Use Case | Description | Priority |
|----------------|----------|-------------|----------|
| FR-UC4-01 | UC4 | User Registration | High |
| FR-UC7-01 | UC7 | User Login | High |
| FR-UC5-01 | UC5 | Personality Onboarding | Medium |
| FR-UC10-01 | UC10 | User Profile Management | High |
| FR-UC1-01 | UC1 | AI Chat Session | High |
| FR-UC2-01 | UC2 | Crisis Intervention | Critical |
| FR-UC6-01 | UC6 | Chat History | Medium |
| FR-UC38-01 | UC38 | Voice Therapy Sessions | Medium |
| FR-UC3-01 | UC3 | Daily Mood Logging | High |
| FR-UC9-01 | UC9 | Mood Analytics | High |
| FR-UC26-01 | UC26 | Mood Forecasting | Medium |
| FR-UC37-01 | UC37 | Burnout Detection | High |
| FR-UC35-01 | UC35 | Relapse Prevention | High |
| FR-UC8-01 | UC8 | Coping Exercise Recommendations | High |
| FR-UC16-01 | UC16 | Educational Resources | Medium |
| FR-UC25-01 | UC25 | User Support | Medium |
| FR-UC32-01 | UC32 | Journaling Prompts | Medium |
| FR-UC34-01 | UC34 | Group Therapy Simulation | Medium |
| FR-UC14-01 | UC14 | Daily Affirmations | Low |
| FR-UC27-01 | UC27 | Guided Breathing | Medium |
| FR-UC13-01 | UC13 | Application Preferences | Medium |
| FR-UC17-01 | UC17 | Accessibility Features | High |
| FR-UC18-01 | UC18 | Notification Management | Medium |
| FR-UC28-01 | UC28 | Therapist Portal | Medium |
| FR-UC31-01 | UC31 | Social Support Network | Low |
| FR-UC41-01 | UC41 | Greedy Algorithm | Medium |

## 7.2 User Stories to Requirements Mapping

| User Story | Requirement ID | Use Case |
|------------|----------------|----------|
| As a new user, I want to create an account | FR-UC4-01 | UC4 |
| As a registered user, I want to login | FR-UC7-01 | UC7 |
| As a new user, I want to complete personality assessment | FR-UC5-01 | UC5 |
| As a user, I want to manage my profile | FR-UC10-01 | UC10 |
| As a user, I want to chat with AI therapist | FR-UC1-01 | UC1 |
| As a user in crisis, I want immediate help | FR-UC2-01 | UC2 |
| As a user, I want to view chat history | FR-UC6-01 | UC6 |
| As a user, I want voice-based therapy | FR-UC38-01 | UC38 |
| As a user, I want to log daily mood | FR-UC3-01 | UC3 |
| As a user, I want to view mood analytics | FR-UC9-01 | UC9 |
| As a user, I want mood forecasts | FR-UC26-01 | UC26 |
| As a user, I want burnout risk assessment | FR-UC37-01 | UC37 |
| As a user, I want relapse prevention alerts | FR-UC35-01 | UC35 |
| As a user, I want coping exercise recommendations | FR-UC8-01 | UC8 |
| As a user, I want educational resources | FR-UC16-01 | UC16 |
| As a user, I want user support | FR-UC25-01 | UC25 |
| As a user, I want journaling prompts | FR-UC32-01 | UC32 |
| As a user, I want group therapy simulations | FR-UC34-01 | UC34 |
| As a user, I want daily affirmations | FR-UC14-01 | UC14 |
| As a user, I want guided breathing | FR-UC27-01 | UC27 |
| As a user, I want to configure preferences | FR-UC13-01 | UC13 |
| As a user with accessibility needs, I want accessible app | FR-UC17-01 | UC17 |
| As a user, I want to manage notifications | FR-UC18-01 | UC18 |
| As a therapist, I want patient data access | FR-UC28-01 | UC28 |
| As a user, I want social support network | FR-UC31-01 | UC31 |
| As a user, I want optimal exercise recommendations | FR-UC41-01 | UC41 |

## 7.3 Requirements Summary

### Total Requirements Count

| Requirement Type | Count |
|------------------|-------|
| **Functional Requirements** | 25 |
| **Non-Functional Requirements** | 15 |
| **Total Requirements** | **40** |

### Requirements by Priority

| Priority | Functional | Non-Functional | Total |
|----------|------------|----------------|-------|
| **Critical** | 1 | 2 | 3 |
| **High** | 12 | 6 | 18 |
| **Medium** | 11 | 6 | 17 |
| **Low** | 2 | 1 | 3 |
| **Total** | **26** | **15** | **41** |

---

# Conclusion

This Software Requirements Specification provides a comprehensive description of the AI Therapist application, including:

- **31 Use Cases** covering all major features
- **25 Functional Requirements** organized by feature categories
- **15 Non-Functional Requirements** covering performance, reliability, security, usability, scalability, and maintainability
- **1 Algorithmic Component** (Greedy Algorithm) with detailed specification
- **Complete Traceability** mapping requirements to use cases and user stories

The document serves as a complete reference for understanding system requirements, design decisions, and implementation specifications.

---

**End of Software Requirements Specification**



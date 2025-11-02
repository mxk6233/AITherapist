# Test Structure Documentation

This document describes the organized test structure for the AI Therapist application.

## Directory Structure

```
app/src/test/java/com/serenityai/tests/
├── usecases/                          # Use case-specific tests
│   ├── uc6_chat_history/              # UC6: View Chat History
│   │   ├── unit/                      # Unit tests
│   │   ├── uat/                       # User Acceptance Tests
│   │   └── integration/               # Integration tests
│   ├── uc8_coping_exercises/         # UC8: Suggest Coping Exercises
│   │   ├── unit/
│   │   ├── uat/
│   │   └── integration/
│   ├── uc9_mood_analytics/           # UC9: View Mood Analytics
│   │   ├── unit/
│   │   ├── uat/
│   │   └── integration/
│   ├── uc13_preferences/              # UC13: Application Preferences
│   │   ├── unit/
│   │   └── uat/
│   ├── uc17_accessibility/            # UC17: Accessibility Features
│   │   ├── unit/
│   │   └── uat/
│   ├── uc18_notifications/            # UC18: Notification Management
│   │   ├── unit/
│   │   └── uat/
│   ├── uc24_personalization/          # UC24: Personalize User Experience
│   │   ├── unit/
│   │   └── uat/
│   ├── uc26_mood_forecasting/         # UC26: AI-Powered Mood Forecasting
│   │   ├── unit/
│   │   ├── uat/
│   │   └── integration/
│   ├── uc27_guided_breathing/         # UC27: Guided Breathing & Meditation
│   │   ├── unit/
│   │   └── uat/
│   ├── uc32_journaling_prompts/       # UC32: AI-Generated Journaling Prompts
│   │   ├── unit/
│   │   ├── uat/
│   │   └── integration/
│   └── uc35_relapse_prevention/       # UC35: Relapse Prevention Alerts
│       ├── unit/
│       ├── uat/
│       └── integration/
├── integration/                       # Cross-feature integration tests
├── ui/                                # UI component tests
│   ├── screens/                       # Screen component tests
│   ├── theme/                         # Theme and styling tests
│   └── navigation/                   # Navigation tests
├── data/                              # Data layer tests
│   └── models/                        # Data model tests
└── utils/                             # Utility function tests
```

## Test Organization Principles

### 1. Use Case Organization
- Each use case has its own directory named `uc{number}_{name}`
- Use case numbers correspond to the project's use case documentation
- All tests related to a specific use case are grouped together

### 2. Test Type Separation
- **Unit Tests** (`unit/`): Test individual components in isolation
- **UAT Tests** (`uat/`): User Acceptance Tests validating user scenarios
- **Integration Tests** (`integration/`): Test interactions between components

### 3. Package Naming Convention
- Package names follow the directory structure
- Format: `com.serenityai.tests.{category}.{subcategory}.{type}`
- Example: `com.serenityai.tests.usecases.uc6_chat_history.unit`

## Use Cases with Tests

### UC6: View Chat History
- **Unit Tests**: `ChatHistoryUseCaseUnitTests.kt`
- Tests search, filtering, analytics, and data export functionality

### UC8: Suggest Coping Exercises
- **Unit Tests**: `CopingExercisesUseCaseUnitTests.kt`
- Tests personalized recommendations, exercise library, and progress tracking

### UC9: View Mood Analytics
- **Unit Tests**: `MoodAnalyticsUseCaseUnitTests.kt`
- Tests trend analysis, pattern recognition, and dashboard functionality

### UC13: Application Preferences
- **Unit Tests**: (To be added)
- Tests preference management and configuration

### UC17: Accessibility Features
- **Unit Tests**: `AccessibilityUseCaseUnitTests.kt`
- Tests screen reader support, keyboard navigation, and accessibility compliance

### UC18: Notification Management
- **Unit Tests**: (To be added)
- Tests notification scheduling, content, and preferences

### UC24: Personalize User Experience
- **Unit Tests**: `PersonalizationUseCaseUnitTests.kt`
- Tests theme customization, personalization features

### UC26: AI-Powered Mood Forecasting
- **Unit Tests**: `MoodForecastingUseCaseUnitTests.kt`
- **UAT Tests**: `MoodForecastingUATTests.kt`
- Tests forecast generation, pattern recognition, and intervention recommendations

### UC27: Guided Breathing & Meditation
- **Unit Tests**: `GuidedBreathingUseCaseUnitTests.kt`
- Tests breathing exercise management and meditation sessions

### UC32: AI-Generated Journaling Prompts
- **Unit Tests**: `JournalingPromptsUseCaseUnitTests.kt`
- Tests prompt generation, personalization, and categorization

### UC35: Relapse Prevention Alerts
- **Unit Tests**: `RelapsePreventionUseCaseUnitTests.kt`
- **UAT Tests**: `RelapsePreventionUATTests.kt`
- Tests risk assessment, early warning system, and safety plans

## Running Tests

### Run all tests
```bash
./gradlew test
```

### Run tests for a specific use case
```bash
./gradlew test --tests "com.serenityai.tests.usecases.uc6_chat_history.*"
```

### Run only unit tests
```bash
./gradlew test --tests "*.unit.*"
```

### Run only UAT tests
```bash
./gradlew test --tests "*.uat.*"
```

### Run integration tests
```bash
./gradlew test --tests "*.integration.*"
```

## Test Naming Conventions

- **Unit Test Classes**: `{Feature}UseCaseUnitTests.kt`
- **UAT Test Classes**: `{Feature}UATTests.kt`
- **Integration Test Classes**: `{Feature}IntegrationTests.kt`

## Benefits of This Structure

1. **Clarity**: Easy to find tests for specific use cases
2. **Organization**: Tests grouped by functionality and type
3. **Scalability**: Easy to add new use cases or test types
4. **Maintainability**: Clear separation of concerns
5. **Test Execution**: Can run tests by category or use case

## Migration Notes

- All test files have been moved from the old structure
- Package names have been updated to match new locations
- Old test directories can be removed after verification


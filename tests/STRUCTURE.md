# Tests Directory Structure

## Overview
All test files have been moved to a centralized `tests/` directory at the project root for better organization and separation from source code.

## Directory Layout

```
tests/
├── unit/                          # Unit tests
│   ├── usecases/                  # Use case unit tests
│   │   ├── uc1_ai_chat_session/
│   │   ├── uc2_crisis_intervention/
│   │   ├── uc3_mood_logging/
│   │   ├── uc4_user_registration/
│   │   ├── uc5_personality_onboarding/
│   │   ├── uc6_chat_history/
│   │   ├── uc7_user_login/
│   │   ├── uc8_coping_exercises/
│   │   ├── uc9_mood_analytics/
│   │   ├── uc13_preferences/
│   │   ├── uc14_daily_affirmations/
│   │   ├── uc15_network_connectivity/
│   │   ├── uc17_accessibility/
│   │   ├── uc18_notifications/
│   │   ├── uc20_application_errors/
│   │   ├── uc22_system_health/
│   │   ├── uc23_security_protocols/
│   │   ├── uc24_personalization/
│   │   ├── uc26_mood_forecasting/
│   │   ├── uc27_guided_breathing/
│   │   ├── uc32_journaling_prompts/
│   │   └── uc35_relapse_prevention/
│   ├── ui/                        # UI component tests
│   │   ├── screens/
│   │   ├── theme/
│   │   └── navigation/
│   ├── data/                      # Data layer tests
│   │   └── models/
│   └── utils/                     # Utility tests
│
├── uat/                           # User Acceptance Tests
│   └── usecases/
│       ├── uc26_mood_forecasting/
│       └── uc35_relapse_prevention/
│
├── integration/                   # Integration tests
│   └── IntegrationTests.kt
│
├── gradle/                        # Gradle test configurations
│   └── (gradle-related test files)
│
└── temp_disabled_tests/          # Temporarily disabled tests
    └── (tests disabled for debugging/maintenance)
```

## Package Naming Convention

### Unit Tests
- Format: `com.serenityai.tests.unit.{category}.{use_case}`
- Example: `com.serenityai.tests.unit.usecases.uc1_ai_chat_session`

### UAT Tests
- Format: `com.serenityai.tests.uat.{category}.{use_case}`
- Example: `com.serenityai.tests.uat.usecases.uc26_mood_forecasting`

### Integration Tests
- Format: `com.serenityai.tests.integration`
- Example: `com.serenityai.tests.integration`

## Statistics

- **Total Test Files**: 41
- **Unit Tests**: 39 files (22 use cases + 17 other)
- **UAT Tests**: 2 files
- **Integration Tests**: 1 file

## Use Case Coverage

All 22 use cases from the sprint plan have:
- ✅ Implementation in `app/src/main/`
- ✅ Unit tests in `tests/unit/usecases/`
- ✅ 2 use cases also have UAT tests (UC26, UC35)

## Running Tests

```bash
# Run all unit tests
./gradlew test --tests "com.serenityai.tests.unit.*"

# Run specific use case tests
./gradlew test --tests "com.serenityai.tests.unit.usecases.uc1_ai_chat_session.*"

# Run UAT tests
./gradlew test --tests "com.serenityai.tests.uat.*"

# Run integration tests
./gradlew connectedAndroidTest --tests "com.serenityai.tests.integration.*"
```

## Temporarily Disabled Tests

When a test needs to be temporarily disabled:
1. Move it to `tests/temp_disabled_tests/`
2. Add a comment explaining why it's disabled
3. Create an issue/ticket to re-enable it
4. When re-enabling, move back to appropriate location and update package

## Migration Notes

Tests were migrated from:
- `app/src/test/java/com/serenityai/tests/`

To:
- `tests/unit/`, `tests/uat/`, `tests/integration/`

Package names have been updated to reflect the new structure. Gradle build configuration has been updated to include the new test locations.


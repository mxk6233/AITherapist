# Test Structure Documentation

This document describes the organized test structure for the AI Therapist application.

## Directory Structure

```
tests/
├── unit/usecases/                     # Unit tests by use case
│   ├── uc11_application_feedback/     # UC11: Submit Application Feedback
│   ├── uc29_sentiment_adaptive_chat/  # UC29: AI Sentiment Adaptive Chat
│   ├── uc36_crisis_deescalation/      # UC36: Adaptive Crisis Deescalation Scripts
│   └── uc38_voice_therapy/            # UC38: Voice-Enabled Therapy Sessions
├── integration/usecases/              # Integration tests by use case
│   ├── uc11_application_feedback/
│   ├── uc29_sentiment_adaptive_chat/
│   ├── uc36_crisis_deescalation/
│   └── uc38_voice_therapy/
└── uat/usecases/                      # User Acceptance Tests by use case
    └── uc38_voice_therapy/
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

### UC11: Submit Application Feedback
- **Unit Tests**: `ApplicationFeedbackUseCaseUnitTests.kt` (24 tests)
- **Integration Tests**: `ApplicationFeedbackUseCaseIntegrationTests.kt` (7 tests)
- **Total**: 31 tests
- Tests feedback submission, retrieval, status management, analytics, search, and trends

### UC29: AI Sentiment Adaptive Chat
- **Unit Tests**: `AISentimentAdaptiveChatUseCaseUnitTests.kt` (23 tests)
- **Integration Tests**: `AISentimentAdaptiveChatUseCaseIntegrationTests.kt` (8 tests)
- **Total**: 31 tests
- Tests sentiment analysis, emotional intensity, adaptive responses, conversation context, pattern detection, crisis detection, and personalization

### UC36: Adaptive Crisis Deescalation Scripts
- **Unit Tests**: `AdaptiveCrisisDeescalationUseCaseUnitTests.kt` (18 tests)
- **Integration Tests**: `AdaptiveCrisisDeescalationUseCaseIntegrationTests.kt` (5 tests)
- **Total**: 23 tests
- Tests crisis assessment, script generation, step execution, script adaptation, safety measures, progress monitoring, and session management

### UC38: Voice-Enabled Therapy Sessions
- **Unit Tests**: `VoiceEnabledTherapyUseCaseUnitTests.kt` (10 tests)
- **Integration Tests**: `VoiceEnabledTherapyUseCaseIntegrationTests.kt` (7 tests)
- **UAT Tests**: `VoiceEnabledTherapyUATTests.kt` (11 tests)
- **Total**: 28 tests
- Tests session management, voice processing, text processing, session controls, history retrieval, error handling, and language support

## Test Statistics Summary

| Use Case | Unit Tests | Integration Tests | UAT Tests | Total |
|----------|-----------|-------------------|-----------|-------|
| UC38: Voice Therapy | 10 | 7 | 11 | 28 |
| UC29: Sentiment Adaptive Chat | 23 | 8 | 0 | 31 |
| UC36: Crisis Deescalation | 18 | 5 | 0 | 23 |
| UC11: Application Feedback | 24 | 7 | 0 | 31 |
| **TOTAL** | **75** | **27** | **11** | **113** |

## Test Framework

All tests use:
- **JUnit 5 (Jupiter)** - Modern testing framework
- **Nested test classes** - Organized test structure using `@Nested`
- **DisplayName annotations** - Descriptive test names using `@DisplayName`
- **Assertions** - Comprehensive validation using JUnit assertions

## Test Structure Pattern

Each test follows the **Given-When-Then** pattern:
- **Given**: Setup test data and conditions
- **When**: Execute the action being tested
- **Then**: Validate the results using assertions

## Running Tests

### Run all tests
```bash
./gradlew test
```

### Run tests for a specific use case
```bash
./gradlew test --tests "com.serenityai.tests.unit.usecases.uc38_voice_therapy.*"
```

### Run only unit tests
```bash
./gradlew test --tests "*unit*"
```

### Run only UAT tests
```bash
./gradlew test --tests "*uat*"
```

### Run integration tests
```bash
./gradlew test --tests "*integration*"
```

## Test Naming Conventions

- **Unit Test Classes**: `{Feature}UseCaseUnitTests.kt`
- **UAT Test Classes**: `{Feature}UATTests.kt`
- **Integration Test Classes**: `{Feature}IntegrationTests.kt`

## Test Case ID Format

Test cases follow the format: `TC-UC{number}-{sequence}`
- Example: `TC-UC38-01` (Use Case 38, Test Case 1)
- Test IDs are sequential within each use case
- Unit tests start at 01, Integration tests continue sequentially, UAT tests continue sequentially

## Test Documentation

Detailed test breakdown with all test cases, descriptions, and expected results is available in:
- **TEST_BREAKDOWN_DOCUMENTATION.md** - Complete test breakdown in table format

## Benefits of This Structure

1. **Clarity**: Easy to find tests for specific use cases
2. **Organization**: Tests grouped by functionality and type
3. **Scalability**: Easy to add new use cases or test types
4. **Maintainability**: Clear separation of concerns
5. **Test Execution**: Can run tests by category or use case

## Test Coverage Goals

- **Unit Test Coverage**: > 80% for all use cases
- **Integration Test Coverage**: All major integrations tested
- **UAT Coverage**: All user stories validated

## Notes

- All tests are designed to be independent and can run in any order
- Tests use mock data and simulated services (not real external services)
- Integration tests verify component interactions, not actual external API calls
- UAT tests validate features from user perspective, ensuring they meet requirements


# Tests Directory Structure

This directory contains all test files organized by test type and use case.

## Directory Structure

```
tests/
├── unit/                    # Unit tests - test individual components in isolation
│   └── usecases/            # Use case-specific unit tests
│       ├── uc1_ai_chat_session/
│       ├── uc2_crisis_intervention/
│       └── ... (all use cases)
├── uat/                     # User Acceptance Tests - validate user scenarios
│   └── usecases/            # Use case-specific UAT tests
├── integration/             # Integration tests - test component interactions
│   └── usecases/            # Cross-feature integration tests
├── gradle/                  # Gradle-related test configurations and scripts
└── temp_disabled_tests/     # Temporarily disabled tests (for debugging/maintenance)
```

## Organization by Use Case

Each use case has its own directory under the test type:
- `tests/unit/usecases/uc{number}_{name}/` - Unit tests for specific use case
- `tests/uat/usecases/uc{number}_{name}/` - UAT tests for specific use case
- `tests/integration/usecases/uc{number}_{name}/` - Integration tests for specific use case

## Test Types

### Unit Tests (`tests/unit/`)
- Test individual components in isolation
- Fast execution
- Mock external dependencies
- Example: `tests/unit/usecases/uc1_ai_chat_session/`

### UAT Tests (`tests/uat/`)
- Validate user scenarios and workflows
- Test end-to-end user experiences
- Example: `tests/uat/usecases/uc26_mood_forecasting/`

### Integration Tests (`tests/integration/`)
- Test interactions between components
- Test external service integrations
- Example: `tests/integration/usecases/cross_feature/`

## Temporarily Disabled Tests

Tests that are temporarily disabled for debugging or maintenance should be moved to:
`tests/temp_disabled_tests/`

When re-enabling:
1. Move test file back to appropriate location
2. Update any package declarations if needed
3. Verify test compiles and runs

## Gradle Configuration

Gradle-related test files, configurations, and scripts go in:
`tests/gradle/`

## Running Tests

```bash
# Run all unit tests
./gradlew test --tests "com.serenityai.tests.unit.*"

# Run all UAT tests
./gradlew test --tests "com.serenityai.tests.uat.*"

# Run all integration tests
./gradlew test --tests "com.serenityai.tests.integration.*"

# Run tests for specific use case
./gradlew test --tests "com.serenityai.tests.unit.usecases.uc1_ai_chat_session.*"
```

## Migration Notes

Tests have been moved from:
- `app/src/test/java/com/serenityai/tests/` 

To:
- `tests/unit/`, `tests/uat/`, `tests/integration/`

Package names have been updated to reflect the new structure.


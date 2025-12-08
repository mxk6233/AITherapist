# How to Generate Test Coverage Report

## Current Status

**Compilation Errors**: There are compilation errors in test files that prevent test execution and coverage report generation. These need to be fixed before generating the coverage report.

## Steps to Generate Coverage Report

### Step 1: Fix Compilation Errors

The following test files have compilation errors that need to be fixed:

1. **`tests/unit/ui/theme/TypeTest.kt`**: Missing imports for Material3 Typography properties
2. **`tests/unit/usecases/uc16_educational_resources/EducationalResourcesUseCaseUnitTests.kt`**: Suspend functions need to be called from coroutines
3. **`tests/unit/usecases/uc26_mood_forecasting/MoodForecastingUseCaseUnitTests.kt`**: Return type mismatch (Float vs Double)
4. **`tests/unit/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseUnitTests.kt`**: More MoodEntry constructor calls need fixing

### Step 2: Run Tests

Once compilation succeeds, run:

```bash
./gradlew test --no-daemon
```

This will:
- Compile all test files
- Execute all unit tests
- Generate execution data files (`app/build/jacoco/*.exec`)

### Step 3: Generate Coverage Report

```bash
./gradlew jacocoTestReport --no-daemon
```

This will generate:
- **HTML Report**: `app/build/reports/jacoco/jacocoTestReport/html/index.html`
- **XML Report**: `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml`

### Step 4: View Coverage Report

```bash
# macOS
open app/build/reports/jacoco/jacocoTestReport/html/index.html

# Linux
xdg-open app/build/reports/jacoco/jacocoTestReport/html/index.html

# Windows
start app/build/reports/jacoco/jacocoTestReport/html/index.html
```

## Coverage Report Contents

The JaCoCo HTML report provides:

1. **Summary Page**: Overall coverage statistics
   - Instruction Coverage
   - Branch Coverage
   - Line Coverage
   - Method Coverage
   - Class Coverage

2. **Package-Level View**: Coverage by package
   - `com.serenityai.usecases`
   - `com.serenityai.data.models`
   - `com.serenityai.ui.screens`
   - etc.

3. **Class-Level View**: Detailed coverage for each class
   - Missed/Covered counts
   - Coverage percentages
   - Source code view with color coding

4. **Source Code View**: Line-by-line coverage
   - Green: Fully covered
   - Yellow: Partially covered
   - Red: Not covered

## Quick Fix Script

To quickly check compilation status:

```bash
# Check compilation errors
./gradlew :app:compileDebugUnitTestKotlin --no-daemon 2>&1 | grep "error:" | head -20

# Count compilation errors
./gradlew :app:compileDebugUnitTestKotlin --no-daemon 2>&1 | grep -c "error:"
```

## Expected Coverage Report Structure

Once generated, the coverage report will show:

```
app/build/reports/jacoco/jacocoTestReport/
├── html/
│   ├── index.html (Summary page)
│   ├── com.serenityai.usecases/
│   │   ├── UserProfileUseCase.html
│   │   ├── EducationalResourcesUseCase.html
│   │   └── ...
│   └── ...
└── jacocoTestReport.xml (Machine-readable report)
```

## Next Steps

1. Fix all compilation errors in test files
2. Run `./gradlew test` to execute tests
3. Run `./gradlew jacocoTestReport` to generate coverage report
4. Open the HTML report to view detailed coverage metrics

## Troubleshooting

If coverage report is empty or shows 0%:

1. Ensure tests executed successfully: Check `app/build/jacoco/*.exec` files exist
2. Verify JaCoCo plugin is configured: Check `app/build.gradle.kts`
3. Check file filters: Ensure test classes are excluded from coverage
4. Review execution data: Check that `.exec` files contain coverage data


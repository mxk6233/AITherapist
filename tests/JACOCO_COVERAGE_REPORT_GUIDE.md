# JaCoCo Code Coverage Report Guide

**Document Version**: v1.0  
**Last Updated**: Sprint 4  
**Purpose**: Guide for generating, interpreting, and using JaCoCo code coverage reports

---

## Overview

JaCoCo (Java Code Coverage) is the code coverage tool integrated into the AI Therapist project. This guide explains how to generate coverage reports and interpret the results.

---

## Generating Coverage Reports

### Prerequisites

1. **Fix Compilation Errors**: All test files must compile successfully before generating coverage reports
2. **Run Tests**: Tests must execute successfully to generate execution data

### Step 1: Run Tests with Coverage

```bash
# Run unit tests (coverage data is automatically generated)
./gradlew test

# Or run specific test task
./gradlew testDebugUnitTest
```

### Step 2: Generate Coverage Report

```bash
# Generate HTML and XML coverage reports
./gradlew jacocoTestReport

# The report will be generated at:
# app/build/reports/jacoco/jacocoTestReport/html/index.html
```

### Step 3: View Coverage Report

```bash
# Open HTML report in browser (macOS)
open app/build/reports/jacoco/jacocoTestReport/html/index.html

# Or navigate to the file manually
# Path: app/build/reports/jacoco/jacocoTestReport/html/index.html
```

---

## Report Structure

### HTML Report Contents

The JaCoCo HTML report provides a hierarchical view of code coverage:

#### 1. **Summary Page** (`index.html`)
- **Overall Coverage Statistics**:
  - Instruction Coverage: Percentage of bytecode instructions executed
  - Branch Coverage: Percentage of decision branches executed
  - Line Coverage: Percentage of source code lines executed
  - Method Coverage: Percentage of methods executed
  - Class Coverage: Percentage of classes executed

#### 2. **Package-Level View**
- Coverage statistics for each package (e.g., `com.serenityai.usecases`, `com.serenityai.data.models`)
- Click on package to drill down to class-level coverage

#### 3. **Class-Level View**
- Coverage statistics for each class
- **Coverage Metrics**:
  - **Missed**: Number of instructions/branches/lines/methods not covered
  - **Covered**: Number of instructions/branches/lines/methods covered
  - **Total**: Total number of instructions/branches/lines/methods
  - **Percentage**: Coverage percentage

#### 4. **Source Code View**
- **Color Coding**:
  - **Green**: Fully covered (all branches executed)
  - **Yellow**: Partially covered (some branches executed)
  - **Red**: Not covered (no execution)
- **Branch Indicators**: Diamond symbols (◆) indicate branches
  - **Green diamond**: All branches covered
  - **Yellow diamond**: Some branches covered
  - **Red diamond**: No branches covered

---

## Interpreting Coverage Metrics

### Instruction Coverage
- **What it measures**: Percentage of bytecode instructions executed during tests
- **Target**: 80%+ instruction coverage
- **Interpretation**: Higher instruction coverage indicates more code paths tested

### Branch Coverage
- **What it measures**: Percentage of decision branches (if/else, switch, ternary) executed
- **Target**: 75%+ branch coverage
- **Interpretation**: Critical for ensuring all conditional logic is tested

### Line Coverage
- **What it measures**: Percentage of source code lines executed
- **Target**: 80%+ line coverage
- **Interpretation**: Indicates how much of the source code is exercised by tests

### Method Coverage
- **What it measures**: Percentage of methods called during tests
- **Target**: 85%+ method coverage
- **Interpretation**: Ensures all public methods are tested

### Class Coverage
- **What it measures**: Percentage of classes instantiated during tests
- **Target**: 90%+ class coverage
- **Interpretation**: Ensures all classes are used in tests

---

## Coverage Report Locations

| Report Type | Location | Format | Purpose |
|-------------|----------|--------|---------|
| **HTML Report** | `app/build/reports/jacoco/jacocoTestReport/html/index.html` | HTML | Human-readable, interactive coverage report |
| **XML Report** | `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml` | XML | Machine-readable, for CI/CD integration |
| **CSV Report** | `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.csv` | CSV | Spreadsheet analysis (if enabled) |
| **Execution Data** | `app/build/jacoco/*.exec` | Binary | Raw coverage data |

---

## XML Report Structure

The XML report (`jacocoTestReport.xml`) contains detailed coverage information:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!DOCTYPE report PUBLIC "-//JACOCO//DTD Report 1.1//EN" "report.dtd">
<report name="AI Therapist">
  <sessioninfo id="..." start="..." dump="..."/>
  <package name="com/serenityai/usecases">
    <class name="com/serenityai/usecases/UserProfileUseCase">
      <method name="createProfile" desc="(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/serenityai/data/models/UserProfile;">
        <counter type="INSTRUCTION" missed="5" covered="45"/>
        <counter type="BRANCH" missed="1" covered="3"/>
        <counter type="LINE" missed="2" covered="8"/>
        <counter type="METHOD" missed="0" covered="1"/>
      </method>
      <!-- More methods... -->
    </class>
    <!-- More classes... -->
  </package>
  <!-- More packages... -->
</report>
```

### XML Report Elements

- **`<report>`**: Root element containing all coverage data
- **`<sessioninfo>`**: Test execution session information
- **`<package>`**: Package-level coverage statistics
- **`<class>`**: Class-level coverage statistics
- **`<method>`**: Method-level coverage statistics
- **`<counter>`**: Coverage counters for different metrics
  - **`type`**: Coverage type (INSTRUCTION, BRANCH, LINE, METHOD, CLASS)
  - **`missed`**: Number of items not covered
  - **`covered`**: Number of items covered

---

## Coverage Analysis by Use Case

### Use Case Coverage Reports

For each use case, the coverage report shows:

#### UC10: Manage User Profile
- **Class**: `com.serenityai.usecases.UserProfileUseCase`
- **Methods Covered**: createProfile, updateProfile, getProfile, createWellnessGoal, updateGoalProgress, updatePreferences, addXP, updateStreak, getWellnessGoals, calculateLevel
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC16: Access Educational Resources
- **Class**: `com.serenityai.usecases.EducationalResourcesUseCase`
- **Methods Covered**: getEducationalResources, searchEducationalResources, getResourceCategories, trackLearningProgress, markResourceAsCompleted, getLearningHistory
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC25: Facilitate User Support
- **Class**: `com.serenityai.usecases.UserSupportUseCase`
- **Methods Covered**: createSupportTicket, getSupportTickets, getFAQEntries, getContextualHelp, submitFeedback
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC28: Therapist Portal Integration
- **Class**: `com.serenityai.usecases.TherapistPortalIntegrationUseCase`
- **Methods Covered**: assignPatientToTherapist, getTherapistPatients, addTherapistNote, getTherapistNotes, generatePatientProgressReport
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC31: Social Support Network Integration
- **Class**: `com.serenityai.usecases.SocialSupportNetworkIntegrationUseCase`
- **Methods Covered**: sendFriendRequest, acceptFriendRequest, getFriends, createSupportGroup, joinSupportGroup, shareProgress, sendEncouragement
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC34: Group Therapy Simulation Mode
- **Class**: `com.serenityai.usecases.GroupTherapySimulationModeUseCase`
- **Methods Covered**: createGroupSession, joinGroupSession, facilitateGroupDiscussion, createVirtualParticipants, generateParticipantResponses, providePeerSupport, getActiveGroupSessions
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC37: Predictive Burnout Detection
- **Class**: `com.serenityai.usecases.PredictiveBurnoutDetectionUseCase`
- **Methods Covered**: assessBurnoutRisk, detectBurnoutWarnings, generatePreventionRecommendations, predictFutureBurnoutRisk
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC38: Voice Enabled Therapy Sessions
- **Class**: `com.serenityai.usecases.VoiceEnabledTherapyUseCase`
- **Methods Covered**: startVoiceSession, processVoiceInput, pauseVoiceSession, endVoiceSession, getVoiceSessionHistory, getSupportedLanguages
- **Coverage Metrics**: See HTML report for detailed metrics

#### UC41: Add Greedy Algorithm
- **Class**: `com.serenityai.usecases.GreedyAlgorithmUseCase`
- **Methods Covered**: selectOptimalStrategies, getTopRecommendations, explainSelection, calculateTotalBenefit, validateConstraints, getAlgorithmStats
- **Coverage Metrics**: See HTML report for detailed metrics

---

## Coverage Thresholds

### Current Thresholds

The project uses the following coverage thresholds (configured in `app/build.gradle.kts`):

```kotlin
violationRules {
    rule {
        limit {
            minimum = "0.00".toBigDecimal() // Temporarily set to 0% to allow report generation
        }
    }
}
```

### Recommended Thresholds

For production readiness, consider:

- **Instruction Coverage**: 80%+
- **Branch Coverage**: 75%+
- **Line Coverage**: 80%+
- **Method Coverage**: 85%+
- **Class Coverage**: 90%+

### Updating Thresholds

To enforce coverage thresholds, update `app/build.gradle.kts`:

```kotlin
violationRules {
    rule {
        limit {
            minimum = "0.80".toBigDecimal() // 80% minimum coverage
        }
    }
    rule {
        element = "BRANCH"
        limits = listOf(
            Limit(type = "COVEREDRATIO", value = "0.75".toBigDecimal()) // 75% branch coverage
        )
    }
}
```

---

## Analyzing Coverage Gaps

### Identifying Uncovered Code

1. **Open HTML Report**: Navigate to `app/build/reports/jacoco/jacocoTestReport/html/index.html`
2. **Find Red/Yellow Lines**: Red indicates no coverage, yellow indicates partial coverage
3. **Review Branch Coverage**: Click on yellow lines to see which branches are not covered
4. **Add Tests**: Create tests to cover uncovered code paths

### Common Coverage Gaps

1. **Error Handling**: Exception paths may not be covered
2. **Edge Cases**: Boundary conditions may not be tested
3. **Optional Parameters**: Code paths with optional parameters may be missed
4. **Null Checks**: Null validation paths may not be covered

---

## CI/CD Integration

### GitHub Actions Example

```yaml
- name: Generate Coverage Report
  run: ./gradlew jacocoTestReport

- name: Upload Coverage Report
  uses: codecov/codecov-action@v3
  with:
    file: app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml
    flags: unittests
    name: codecov-umbrella
```

### XML Report Parsing

The XML report can be parsed programmatically:

```kotlin
// Example: Parse XML report to extract coverage metrics
val xmlFile = File("app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile)
// Extract coverage data...
```

---

## Troubleshooting

### Issue: No Coverage Data Generated

**Symptoms**: Coverage report shows 0% coverage or no data

**Solutions**:
1. Ensure tests are executed: `./gradlew test`
2. Check execution data exists: `ls app/build/jacoco/*.exec`
3. Verify JaCoCo plugin is applied: Check `app/build.gradle.kts` for `jacoco` plugin
4. Check test task configuration: Ensure `finalizedBy("jacocoTestReport")` is set

### Issue: Coverage Report Not Generated

**Symptoms**: `jacocoTestReport` task fails or report files missing

**Solutions**:
1. Check compilation errors: Fix all compilation errors first
2. Verify source directories: Check `sourceDirectories` in `jacocoTestReport` task
3. Verify class directories: Check `classDirectories` in `jacocoTestReport` task
4. Check file filters: Ensure test classes are excluded from coverage

### Issue: Incorrect Coverage Percentages

**Symptoms**: Coverage percentages seem incorrect

**Solutions**:
1. Check file filters: Ensure generated code (R.class, BuildConfig) is excluded
2. Verify source/class directories: Ensure correct directories are included
3. Check execution data: Ensure all test execution data is included
4. Review test execution: Ensure all tests ran successfully

---

## Best Practices

1. **Generate Reports Regularly**: Generate coverage reports after each test run
2. **Review Coverage Gaps**: Identify and address uncovered code paths
3. **Set Coverage Goals**: Establish and enforce coverage thresholds
4. **Track Coverage Trends**: Monitor coverage over time to ensure it increases
5. **Focus on Critical Code**: Prioritize coverage for business-critical code paths
6. **Use Branch Coverage**: Pay attention to branch coverage, not just line coverage
7. **Review Source Code**: Use HTML report to visually inspect uncovered code

---

## References

- **JaCoCo Documentation**: https://www.jacoco.org/jacoco/
- **JaCoCo Gradle Plugin**: https://docs.gradle.org/current/userguide/jacoco_plugin.html
- **Coverage Report Location**: `app/build/reports/jacoco/jacocoTestReport/html/index.html`

---

## Next Steps

1. **Fix Compilation Errors**: Resolve all compilation errors in test files
2. **Run Tests**: Execute `./gradlew test` to generate execution data
3. **Generate Report**: Execute `./gradlew jacocoTestReport` to create coverage report
4. **Review Coverage**: Open HTML report and analyze coverage metrics
5. **Address Gaps**: Add tests to cover uncovered code paths
6. **Set Thresholds**: Update coverage thresholds to enforce minimum coverage

---

**Note**: Before generating coverage reports, ensure all compilation errors are resolved. The current build has compilation errors in `GreedyCopingStrategySelectorTest.kt` that need to be fixed.


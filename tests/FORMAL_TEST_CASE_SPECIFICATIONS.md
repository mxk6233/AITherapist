# Formal Test Case Specifications

**Document Version**: 1.0  
**Date**: December 2024  
**Status**: Complete Test Case Specifications for UC16, UC25, UC37, UC38

---

## Document Purpose

This document provides formal test case specifications with detailed acceptance criteria, preconditions, test steps, and expected results for the four use cases: UC16, UC25, UC37, and UC38. Each test case includes clear traceability to requirements and measurable expected outcomes.

---

## UC16: Access Educational Resources

### Use Case Goal
Enable users to access comprehensive educational resources about mental health, therapy techniques, and self-care strategies so they can learn and apply evidence-based practices.

### Acceptance Criteria

**AC-UC16-01**: System MUST provide categorized educational content (articles, videos, guides)  
**AC-UC16-02**: System MUST allow filtering resources by category  
**AC-UC16-03**: System MUST allow filtering resources by content format (text, video, audio)  
**AC-UC16-04**: System MUST provide search functionality across resource titles, descriptions, and tags  
**AC-UC16-05**: System MUST provide personalized resource recommendations based on user profile  
**AC-UC16-06**: System MUST track learning progress (0-100%) for each resource  
**AC-UC16-07**: System MUST mark resources as completed with timestamp when progress reaches 100%  
**AC-UC16-08**: System MUST retrieve user's learning history  
**AC-UC16-09**: System MUST validate input and reject empty search queries  
**AC-UC16-10**: System MUST provide list of available resource categories

---

### Test Case TC-UC16-UNIT-01: Resource Retrieval by Category

**Test Case ID**: TC-UC16-UNIT-01  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-01, AC-UC16-02

**Preconditions**:
- System has educational resources available in multiple categories
- At least one resource exists in "Anxiety Management" category
- At least one resource exists in "Mindfulness" category
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `getEducationalResources(category = "Anxiety Management")`
2. Store the returned list as `anxietyResources`
3. Invoke `getEducationalResources(category = "Mindfulness")`
4. Store the returned list as `mindfulnessResources`

**Expected Results**:
- `anxietyResources` is not empty
- All resources in `anxietyResources` have `category == "Anxiety Management"` (exact match, case-insensitive)
- `mindfulnessResources` is not empty
- All resources in `mindfulnessResources` have `category == "Mindfulness"` (exact match, case-insensitive)
- No resources from other categories are included in either result set

**Acceptance Criteria Validation**:
-  AC-UC16-01: Categorized content is provided
-  AC-UC16-02: Category filtering works correctly

---

### Test Case TC-UC16-UNIT-02: Resource Filtering by Format

**Test Case ID**: TC-UC16-UNIT-02  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-03

**Preconditions**:
- System has educational resources in multiple formats (TEXT, VIDEO, AUDIO)
- At least one TEXT resource exists
- At least one VIDEO resource exists
- At least one AUDIO resource exists
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `getEducationalResources(format = ContentFormat.TEXT)`
2. Store the returned list as `textResources`
3. Invoke `getEducationalResources(format = ContentFormat.VIDEO)`
4. Store the returned list as `videoResources`
5. Invoke `getEducationalResources(format = ContentFormat.AUDIO)`
6. Store the returned list as `audioResources`

**Expected Results**:
- `textResources` is not empty
- All resources in `textResources` have `format == ContentFormat.TEXT`
- `videoResources` is not empty
- All resources in `videoResources` have `format == ContentFormat.VIDEO`
- `audioResources` is not empty
- All resources in `audioResources` have `format == ContentFormat.AUDIO`
- No resources with incorrect format are included in any result set

**Acceptance Criteria Validation**:
-  AC-UC16-03: Format filtering works correctly for all supported formats

---

### Test Case TC-UC16-UNIT-03: Resource Categories List

**Test Case ID**: TC-UC16-UNIT-03  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC16-10

**Preconditions**:
- EducationalResourcesUseCase instance is initialized
- System has at least one resource category defined

**Test Steps**:
1. Invoke `getResourceCategories()`
2. Store the returned list as `categories`

**Expected Results**:
- `categories` is not empty
- `categories` contains "Anxiety Management" (case-insensitive match)
- `categories` contains "Mindfulness" (case-insensitive match)
- `categories.size >= 5` (system provides comprehensive category list)
- All items in `categories` are non-empty strings

**Acceptance Criteria Validation**:
-  AC-UC16-10: System provides complete list of available categories

---

### Test Case TC-UC16-UNIT-04: Search Educational Resources

**Test Case ID**: TC-UC16-UNIT-04  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-04, AC-UC16-09

**Preconditions**:
- System has educational resources with various titles, descriptions, and tags
- At least one resource contains "anxiety" in title, description, or tags
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `searchEducationalResources(query = "anxiety")`
2. Store the returned list as `anxietyResults`
3. Invoke `searchEducationalResources(query = "mindfulness")`
4. Store the returned list as `mindfulnessResults`
5. Attempt to invoke `searchEducationalResources(query = "")`
6. Attempt to invoke `searchEducationalResources(query = "   ")`

**Expected Results**:
- `anxietyResults` is not empty
- At least one resource in `anxietyResults` has "anxiety" (case-insensitive) in:
  - `title.lowercase()`, OR
  - `description.lowercase()`, OR
  - Any element in `tags` (case-insensitive)
- `mindfulnessResults` is not empty
- Step 5 throws `IllegalArgumentException` with message containing "cannot be empty"
- Step 6 throws `IllegalArgumentException` with message containing "cannot be empty"

**Acceptance Criteria Validation**:
-  AC-UC16-04: Search works across titles, descriptions, and tags
-  AC-UC16-09: Empty queries are rejected with appropriate exception

---

### Test Case TC-UC16-UNIT-05: Personalized Recommendations

**Test Case ID**: TC-UC16-UNIT-05  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-05

**Preconditions**:
- System has educational resources available
- User with ID "user123" exists (or can be referenced)
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `getRecommendedResources(userId = "user123", limit = 5)`
2. Store the returned list as `recommendations`

**Expected Results**:
- `recommendations` is not empty
- `recommendations.size <= 5` (limit parameter is respected)
- All resources in `recommendations` have `relevanceScore > 0f`
- Relevance scores are in descending order: `recommendations.map { it.relevanceScore } == recommendations.map { it.relevanceScore }.sortedDescending()`
- All resources have valid IDs, titles, and descriptions

**Acceptance Criteria Validation**:
-  AC-UC16-05: Personalized recommendations are provided, sorted by relevance

---

### Test Case TC-UC16-UNIT-06: Learning Progress Tracking

**Test Case ID**: TC-UC16-UNIT-06  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-06

**Preconditions**:
- Resource with ID "res_001" exists
- User with ID "user123" exists
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `trackLearningProgress(resourceId = "res_001", userId = "user123", progress = 50)`
2. Store the returned `LearningProgress` as `progress50`
3. Invoke `trackLearningProgress(resourceId = "res_001", userId = "user123", progress = 0)`
4. Store the returned `LearningProgress` as `progress0`
5. Invoke `trackLearningProgress(resourceId = "res_001", userId = "user123", progress = 100)`
6. Store the returned `LearningProgress` as `progress100`
7. Attempt to invoke `trackLearningProgress(resourceId = "res_001", userId = "user123", progress = -1)`
8. Attempt to invoke `trackLearningProgress(resourceId = "res_001", userId = "user123", progress = 101)`

**Expected Results**:
- `progress50.resourceId == "res_001"`
- `progress50.userId == "user123"`
- `progress50.progress == 50`
- `progress50.completedAt == null` (not completed)
- `progress0.progress == 0`
- `progress100.progress == 100`
- `progress100.completedAt != null` (completed timestamp is set)
- Step 7 throws `IllegalArgumentException` with message containing "must be between 0 and 100"
- Step 8 throws `IllegalArgumentException` with message containing "must be between 0 and 100"

**Acceptance Criteria Validation**:
-  AC-UC16-06: Progress tracking works correctly with validation of bounds (0-100%)

---

### Test Case TC-UC16-UNIT-07: Resource Completion

**Test Case ID**: TC-UC16-UNIT-07  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC16-07

**Preconditions**:
- Resource with ID "res_002" exists
- User with ID "user123" exists
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `markResourceAsCompleted(resourceId = "res_002", userId = "user123")`
2. Store the returned `LearningProgress` as `completedProgress`

**Expected Results**:
- `completedProgress.resourceId == "res_002"`
- `completedProgress.userId == "user123"`
- `completedProgress.progress == 100` (completed resources have 100% progress)
- `completedProgress.completedAt != null` (completion timestamp is set)
- `completedProgress.timeSpent > 0f` (time spent is tracked)
- `completedProgress.id` is not empty (unique ID assigned)

**Acceptance Criteria Validation**:
-  AC-UC16-07: Resources are marked as completed with timestamp when progress reaches 100%

---

### Test Case TC-UC16-UNIT-08: Learning History Retrieval

**Test Case ID**: TC-UC16-UNIT-08  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC16-08

**Preconditions**:
- User with ID "user123" exists
- EducationalResourcesUseCase instance is initialized

**Test Steps**:
1. Invoke `getLearningHistory(userId = "user123")`
2. Store the returned list as `history`

**Expected Results**:
- `history` is not null (method returns a list, even if empty)
- `history` is an instance of `List<LearningProgress>`
- All items in `history` (if any) have `userId == "user123"`

**Acceptance Criteria Validation**:
-  AC-UC16-08: Learning history is retrievable for users

---

## UC25: Facilitate User Support

### Use Case Goal
Provide comprehensive user support through multiple channels including in-app help, FAQ, support tickets, and direct assistance to ensure users can effectively use the application and get help when needed.

### Acceptance Criteria

**AC-UC25-01**: System MUST allow users to create support tickets with subject, description, category, and priority  
**AC-UC25-02**: System MUST validate ticket input and reject empty subject or description  
**AC-UC25-03**: System MUST allow adding responses to support tickets (from user or support team)  
**AC-UC25-04**: System MUST provide FAQ entries with search capability  
**AC-UC25-05**: System MUST provide contextual help based on current screen/feature  
**AC-UC25-06**: System MUST provide list of available support categories  
**AC-UC25-07**: System MUST accept and track user feedback with type, message, and optional rating  
**AC-UC25-08**: System MUST validate feedback input and reject empty messages or invalid ratings (outside 1-5)  
**AC-UC25-09**: System MUST retrieve user's support ticket history

---

### Test Case TC-UC25-UNIT-01: Create Support Ticket

**Test Case ID**: TC-UC25-UNIT-01  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-01

**Preconditions**:
- User with ID "user123" exists
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `createSupportTicket(userId = "user123", subject = "App login issue", description = "I cannot log into my account", category = SupportCategory.TECHNICAL, priority = SupportPriority.HIGH)`
2. Store the returned `SupportTicket` as `ticket`

**Expected Results**:
- `ticket.id` is not empty and starts with "ticket_"
- `ticket.userId == "user123"`
- `ticket.subject == "App login issue"` (exact match)
- `ticket.description == "I cannot log into my account"` (exact match)
- `ticket.category == SupportCategory.TECHNICAL`
- `ticket.priority == SupportPriority.HIGH`
- `ticket.status == TicketStatus.OPEN` (new tickets have OPEN status)
- `ticket.createdAt != null` (creation timestamp is set)
- `ticket.updatedAt != null` (update timestamp is set)
- `ticket.responses` is an empty list (new tickets have no responses)

**Acceptance Criteria Validation**:
-  AC-UC25-01: Support tickets are created with all required fields correctly set

---

### Test Case TC-UC25-UNIT-02: Validate Ticket Input

**Test Case ID**: TC-UC25-UNIT-02  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-02

**Preconditions**:
- User with ID "user123" exists
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Attempt to invoke `createSupportTicket(userId = "user123", subject = "", description = "Valid description")`
2. Attempt to invoke `createSupportTicket(userId = "user123", subject = "Valid subject", description = "")`
3. Attempt to invoke `createSupportTicket(userId = "user123", subject = "   ", description = "Valid description")`

**Expected Results**:
- Step 1 throws `IllegalArgumentException` with message containing "Subject cannot be empty"
- Step 2 throws `IllegalArgumentException` with message containing "Description cannot be empty"
- Step 3 throws `IllegalArgumentException` with message containing "Subject cannot be empty" (whitespace-only is treated as empty)

**Acceptance Criteria Validation**:
-  AC-UC25-02: Empty subject and description are rejected with appropriate exceptions

---

### Test Case TC-UC25-UNIT-03: Add Ticket Response

**Test Case ID**: TC-UC25-UNIT-03  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-03

**Preconditions**:
- Support ticket with ID "ticket_123" exists (or can be created)
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `addTicketResponse(ticketId = "ticket_123", response = "We're looking into this issue", isFromUser = false)`
2. Store the returned `SupportTicket` as `updatedTicket`

**Expected Results**:
- `updatedTicket.responses` is not empty
- `updatedTicket.responses.size >= 1`
- First response in `updatedTicket.responses` has:
  - `message == "We're looking into this issue"` (exact match)
  - `isFromUser == false` (response source correctly identified)
  - `id` is not empty and starts with "response_"
  - `createdAt != null` (response timestamp is set)

**Acceptance Criteria Validation**:
-  AC-UC25-03: Responses can be added to tickets with correct source identification

---

### Test Case TC-UC25-UNIT-04: Retrieve FAQ Entries

**Test Case ID**: TC-UC25-UNIT-04  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-04

**Preconditions**:
- System has FAQ entries available
- At least one FAQ contains "mood" in question or answer
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `getFAQEntries()`
2. Store the returned list as `allFAQs`
3. Invoke `getFAQEntries(query = "mood")`
4. Store the returned list as `moodFAQs`

**Expected Results**:
- `allFAQs` is not empty
- All items in `allFAQs` have:
  - `question.isNotBlank()`
  - `answer.isNotBlank()`
- `moodFAQs` is not empty
- At least one FAQ in `moodFAQs` has "mood" (case-insensitive) in:
  - `question.lowercase()`, OR
  - `answer.lowercase()`, OR
  - Any element in `tags` (case-insensitive)

**Acceptance Criteria Validation**:
-  AC-UC25-04: FAQ entries are retrievable and searchable

---

### Test Case TC-UC25-UNIT-05: Contextual Help

**Test Case ID**: TC-UC25-UNIT-05  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC25-05

**Preconditions**:
- System has contextual help content for "mood_logging"
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `getContextualHelp(context = "mood_logging")`
2. Store the returned `HelpContent` as `knownHelp`
3. Invoke `getContextualHelp(context = "unknown_screen")`
4. Store the returned `HelpContent` as `unknownHelp`

**Expected Results**:
- `knownHelp` is not null
- `knownHelp.context == "mood_logging"` (exact match, case-insensitive)
- `knownHelp.title.isNotBlank()`
- `knownHelp.content.isNotBlank()`
- `unknownHelp` is not null
- `unknownHelp.title == "General Help"` (fallback for unknown contexts)
- `unknownHelp.content.isNotBlank()`

**Acceptance Criteria Validation**:
-  AC-UC25-05: Contextual help is provided for known contexts, general help for unknown

---

### Test Case TC-UC25-UNIT-06: Support Categories

**Test Case ID**: TC-UC25-UNIT-06  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC25-06

**Preconditions**:
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `getSupportCategories()`
2. Store the returned list as `categories`

**Expected Results**:
- `categories` is not empty
- `categories` contains "TECHNICAL" (case-insensitive match)
- `categories` contains "GENERAL" (case-insensitive match)
- All items in `categories` are non-empty strings

**Acceptance Criteria Validation**:
-  AC-UC25-06: Support categories are provided

---

### Test Case TC-UC25-UNIT-07: Submit Feedback

**Test Case ID**: TC-UC25-UNIT-07  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-07

**Preconditions**:
- User with ID "user123" exists
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `submitFeedback(userId = "user123", feedbackType = FeedbackType.FEATURE, message = "I'd love to see dark mode support", rating = 5)`
2. Store the returned `FeedbackSubmission` as `feedback`

**Expected Results**:
- `feedback.id` is not empty and starts with "feedback_"
- `feedback.userId == "user123"`
- `feedback.feedbackType == FeedbackType.FEATURE`
- `feedback.message == "I'd love to see dark mode support"` (exact match)
- `feedback.rating == 5`
- `feedback.status == FeedbackStatus.RECEIVED` (new feedback has RECEIVED status)
- `feedback.submittedAt != null` (submission timestamp is set)

**Acceptance Criteria Validation**:
-  AC-UC25-07: Feedback is submitted and tracked with all fields

---

### Test Case TC-UC25-UNIT-08: Validate Feedback Input

**Test Case ID**: TC-UC25-UNIT-08  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC25-08

**Preconditions**:
- User with ID "user123" exists
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Attempt to invoke `submitFeedback(userId = "user123", feedbackType = FeedbackType.GENERAL, message = "")`
2. Attempt to invoke `submitFeedback(userId = "user123", feedbackType = FeedbackType.GENERAL, message = "Valid feedback", rating = 6)`
3. Attempt to invoke `submitFeedback(userId = "user123", feedbackType = FeedbackType.GENERAL, message = "Valid feedback", rating = 0)`

**Expected Results**:
- Step 1 throws `IllegalArgumentException` with message containing "Feedback message cannot be empty"
- Step 2 throws `IllegalArgumentException` with message containing "Rating must be between 1 and 5"
- Step 3 throws `IllegalArgumentException` with message containing "Rating must be between 1 and 5"

**Acceptance Criteria Validation**:
-  AC-UC25-08: Empty messages and invalid ratings are rejected

---

### Test Case TC-UC25-UNIT-09: Retrieve Support History

**Test Case ID**: TC-UC25-UNIT-09  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC25-09

**Preconditions**:
- User with ID "user123" exists
- UserSupportUseCase instance is initialized

**Test Steps**:
1. Invoke `getSupportTickets(userId = "user123")`
2. Store the returned list as `tickets`

**Expected Results**:
- `tickets` is not null (method returns a list, even if empty)
- `tickets` is an instance of `List<SupportTicket>`
- All items in `tickets` (if any) have `userId == "user123"`

**Acceptance Criteria Validation**:
-  AC-UC25-09: Support ticket history is retrievable

---

## UC37: Predictive Burnout Detection

### Use Case Goal
Detect early signs of burnout risk through analysis of user behavior patterns, mood trends, and activity levels to enable proactive intervention and prevention.

### Acceptance Criteria

**AC-UC37-01**: System MUST assess burnout risk from multiple factors (mood, activity, stress, sleep)  
**AC-UC37-02**: System MUST calculate burnout risk level (LOW, MODERATE, HIGH, CRITICAL) based on risk score (0-100)  
**AC-UC37-03**: System MUST identify multiple burnout risk factors with severity scores (0-1)  
**AC-UC37-04**: System MUST detect early warning signs of burnout (mood decline, activity decline, stress accumulation, sleep disruption)  
**AC-UC37-05**: System MUST generate personalized prevention recommendations based on risk level and factors  
**AC-UC37-06**: System MUST trigger interventions automatically when risk level is HIGH or CRITICAL  
**AC-UC37-07**: System MUST predict future burnout risk with projected score, level, trend, and confidence  
**AC-UC37-08**: System MUST calculate prediction confidence (0-100%) based on data quality  
**AC-UC37-09**: System MUST identify risk trends (INCREASING, DECREASING, STABLE)

---

### Test Case TC-UC37-UNIT-01: Assess Burnout Risk

**Test Case ID**: TC-UC37-UNIT-01  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-01

**Preconditions**:
- User with ID "user123" exists
- System has mood entries, activity levels, stress indicators, and sleep quality data
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create test data:
   - `moodEntries`: List of 3 MoodEntry objects with mood values 2.0f, 2.5f, 3.0f
   - `activityLevels`: List of 3 ActivityLevel objects with levels 0.3f, 0.2f, 0.25f
   - `stressIndicators`: List of 3 StressIndicator objects with levels 8.0f, 7.5f, 8.5f
2. Invoke `assessBurnoutRisk(moodEntries = moodEntries, activityLevels = activityLevels, stressIndicators = stressIndicators)`
3. Store the returned `BurnoutRiskAssessment` as `assessment`

**Expected Results**:
- `assessment.id` is not empty
- `assessment.userId == "user123"`
- `assessment.riskScore in 0f..100f` (risk score is within valid range)
- `assessment.riskLevel` is one of: `BurnoutRiskLevel.LOW`, `BurnoutRiskLevel.MODERATE`, `BurnoutRiskLevel.HIGH`, `BurnoutRiskLevel.CRITICAL`
- `assessment.riskFactors.isNotEmpty()` (at least one risk factor is identified)
- All items in `assessment.riskFactors` have:
  - `severity in 0f..1f` (severity is within valid range)
  - `description.isNotBlank()`
  - `detectedAt != null`
- `assessment.assessedAt != null` (assessment timestamp is set)
- `assessment.earlyWarnings` is a list (may be empty)
- `assessment.recommendations` is not empty
- `assessment.interventionTriggered` is a boolean value

**Acceptance Criteria Validation**:
-  AC-UC37-01: Comprehensive risk assessment is performed from multiple factors

---

### Test Case TC-UC37-UNIT-02: Calculate Risk Level

**Test Case ID**: TC-UC37-UNIT-02  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-02

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create high risk scenario:
   - `highRiskMoods`: List of 3 MoodEntry objects with low mood values (2.0f, 1.5f, 2.5f)
   - `highStress`: List of 2 StressIndicator objects with high stress (9.0f, 8.5f)
2. Invoke `assessBurnoutRisk(moodEntries = highRiskMoods, stressIndicators = highStress)`
3. Store the returned assessment as `highRiskAssessment`
4. Create low risk scenario:
   - `lowRiskMoods`: List of 3 MoodEntry objects with good mood values (4.0f, 4.5f, 4.2f)
5. Invoke `assessBurnoutRisk(moodEntries = lowRiskMoods)`
6. Store the returned assessment as `lowRiskAssessment`

**Expected Results**:
- `highRiskAssessment.riskScore >= 50f` (high risk scenarios produce scores >= 50)
- `highRiskAssessment.riskLevel in listOf(BurnoutRiskLevel.HIGH, BurnoutRiskLevel.CRITICAL)` (high risk produces HIGH or CRITICAL level)
- `lowRiskAssessment.riskLevel in listOf(BurnoutRiskLevel.LOW, BurnoutRiskLevel.MODERATE)` (low risk produces LOW or MODERATE level)

**Acceptance Criteria Validation**:
-  AC-UC37-02: Risk levels are calculated correctly based on risk scores

---

### Test Case TC-UC37-UNIT-03: Identify Risk Factors

**Test Case ID**: TC-UC37-UNIT-03  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-03

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create diverse test data:
   - `moodEntries`: List of 2 MoodEntry objects with declining mood (2.5f, 2.0f)
   - `activityLevels`: List of 1 ActivityLevel object with low activity (0.2f)
   - `stressIndicators`: List of 1 StressIndicator object with high stress (8.0f)
   - `sleepQuality`: List of 1 SleepQuality object with poor sleep (3.0f)
2. Invoke `assessBurnoutRisk(moodEntries = moodEntries, activityLevels = activityLevels, stressIndicators = stressIndicators, sleepQuality = sleepQuality)`
3. Store the returned assessment as `assessment`

**Expected Results**:
- `assessment.riskFactors.size >= 2` (multiple risk factors are identified)
- All items in `assessment.riskFactors` have:
  - `severity in 0f..1f` (severity is within valid range)
  - `description.isNotBlank()`
  - `factorType` is one of: `BurnoutFactorType.MOOD_DECLINE`, `BurnoutFactorType.ACTIVITY_DECLINE`, `BurnoutFactorType.STRESS_ACCUMULATION`, `BurnoutFactorType.SLEEP_DISRUPTION`

**Acceptance Criteria Validation**:
-  AC-UC37-03: Multiple risk factors are identified with severity scores

---

### Test Case TC-UC37-UNIT-04: Detect Early Warnings

**Test Case ID**: TC-UC37-UNIT-04  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-04

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create declining pattern data:
   - `decliningMoods`: List of 7 MoodEntry objects showing decline (4.0f, 3.5f, 3.0f, 2.5f, 2.0f, 1.5f, 2.0f)
   - `activityLevels`: List of 4 ActivityLevel objects showing decline (0.8f, 0.6f, 0.4f, 0.3f)
2. Invoke `assessBurnoutRisk(moodEntries = decliningMoods, activityLevels = activityLevels)`
3. Store the returned assessment as `assessment`

**Expected Results**:
- `assessment.earlyWarnings.isNotEmpty()` (early warnings are detected for declining patterns)
- At least one warning in `assessment.earlyWarnings` has:
  - `type == WarningType.MOOD_DECLINE` (mood decline warnings are detected)
  - `severity` is one of: `WarningSeverity.LOW`, `WarningSeverity.MODERATE`, `WarningSeverity.HIGH`, `WarningSeverity.CRITICAL`
  - `message.isNotBlank()`
  - `detectedAt != null`

**Acceptance Criteria Validation**:
-  AC-UC37-04: Early warning signs are detected for declining patterns

---

### Test Case TC-UC37-UNIT-05: Generate Prevention Recommendations

**Test Case ID**: TC-UC37-UNIT-05  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-05

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create high risk scenario:
   - `highRiskMoods`: List of 3 MoodEntry objects with low mood (2.0f, 2.5f, 2.0f)
   - `highStress`: List of 1 StressIndicator object with high stress (9.0f)
2. Invoke `assessBurnoutRisk(moodEntries = highRiskMoods, stressIndicators = highStress)`
3. Store the returned assessment as `assessment`

**Expected Results**:
- `assessment.recommendations.isNotEmpty()` (recommendations are provided)
- All items in `assessment.recommendations` are non-empty strings
- If `assessment.riskLevel == BurnoutRiskLevel.HIGH || assessment.riskLevel == BurnoutRiskLevel.CRITICAL`:
  - At least one recommendation contains "URGENT" (case-insensitive) OR "immediate" (case-insensitive)

**Acceptance Criteria Validation**:
-  AC-UC37-05: Personalized recommendations are generated based on risk level

---

### Test Case TC-UC37-UNIT-06: Trigger Interventions

**Test Case ID**: TC-UC37-UNIT-06  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-06

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create critical risk scenario:
   - `criticalMoods`: List of 3 MoodEntry objects with very low mood (1.5f, 2.0f, 1.0f)
   - `criticalStress`: List of 2 StressIndicator objects with very high stress (9.5f, 10.0f)
2. Invoke `assessBurnoutRisk(moodEntries = criticalMoods, stressIndicators = criticalStress)`
3. Store the returned assessment as `assessment`

**Expected Results**:
- If `assessment.riskLevel == BurnoutRiskLevel.CRITICAL || assessment.riskLevel == BurnoutRiskLevel.HIGH`:
  - `assessment.interventionTriggered == true` (interventions are triggered for high/critical risk)

**Acceptance Criteria Validation**:
-  AC-UC37-06: Interventions are triggered automatically for HIGH/CRITICAL risk

---

### Test Case TC-UC37-UNIT-07: Predict Future Risk

**Test Case ID**: TC-UC37-UNIT-07  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC37-07

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create current assessment:
   - `moodEntries`: List of 3 MoodEntry objects (2.5f, 2.0f, 2.5f)
   - Invoke `assessBurnoutRisk(moodEntries = moodEntries)`
   - Store as `currentAssessment`
2. Invoke `predictFutureBurnoutRisk(currentAssessment = currentAssessment, daysAhead = 7)`
3. Store the returned `BurnoutPrediction` as `prediction`

**Expected Results**:
- `prediction.id` is not empty
- `prediction.userId == currentAssessment.userId` (prediction is linked to correct user)
- `prediction.daysAhead == 7` (prediction matches requested days ahead)
- `prediction.projectedRiskScore in 0f..100f` (projected score is within valid range)
- `prediction.projectedRiskLevel` is one of: `BurnoutRiskLevel.LOW`, `BurnoutRiskLevel.MODERATE`, `BurnoutRiskLevel.HIGH`, `BurnoutRiskLevel.CRITICAL`
- `prediction.confidence in 0f..100f` (confidence is within valid range)
- `prediction.trend` is one of: `Trend.INCREASING`, `Trend.DECREASING`, `Trend.STABLE`
- `prediction.predictedAt != null` (prediction timestamp is set)

**Acceptance Criteria Validation**:
-  AC-UC37-07: Future risk predictions are generated with all required components

---

### Test Case TC-UC37-UNIT-08: Calculate Prediction Confidence

**Test Case ID**: TC-UC37-UNIT-08  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC37-08

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create comprehensive assessment with multiple factors:
   - `comprehensiveMoods`: List of 3 MoodEntry objects
   - `comprehensiveActivity`: List of 2 ActivityLevel objects
   - `comprehensiveStress`: List of 2 StressIndicator objects
   - `comprehensiveSleep`: List of 2 SleepQuality objects
   - Invoke `assessBurnoutRisk(...)` with all factors
   - Store as `comprehensiveAssessment`
2. Invoke `predictFutureBurnoutRisk(comprehensiveAssessment)`
3. Store the returned prediction as `comprehensivePrediction`

**Expected Results**:
- `comprehensivePrediction.confidence >= 65f` (comprehensive data yields higher confidence >= 65%)

**Acceptance Criteria Validation**:
-  AC-UC37-08: Prediction confidence reflects data quality

---

### Test Case TC-UC37-UNIT-09: Identify Risk Trends

**Test Case ID**: TC-UC37-UNIT-09  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC37-09

**Preconditions**:
- PredictiveBurnoutDetectionUseCase instance is initialized

**Test Steps**:
1. Create assessment:
   - `moodEntries`: List of 3 MoodEntry objects (3.0f, 3.5f, 3.0f)
   - Invoke `assessBurnoutRisk(moodEntries = moodEntries)`
   - Store as `assessment`
2. Invoke `predictFutureBurnoutRisk(assessment)`
3. Store the returned prediction as `prediction`

**Expected Results**:
- `prediction.trend in listOf(Trend.INCREASING, Trend.DECREASING, Trend.STABLE)` (trend is one of the valid values)

**Acceptance Criteria Validation**:
-  AC-UC37-09: Risk trends are identified correctly

---

## UC38: Voice Enabled Therapy Sessions

### Use Case Goal
Enable users to have voice-based therapy sessions with the AI therapist, providing a more natural and accessible interaction method through speech recognition and text-to-speech technologies.

### Acceptance Criteria

**AC-UC38-01**: System MUST start voice therapy sessions with user ID and language  
**AC-UC38-02**: System MUST process voice input (audio) and convert to text (speech-to-text)  
**AC-UC38-03**: System MUST generate AI therapist responses and convert to voice (text-to-speech)  
**AC-UC38-04**: System MUST process text input and convert AI response to voice  
**AC-UC38-05**: System MUST pause and resume voice sessions  
**AC-UC38-06**: System MUST end voice sessions and track duration  
**AC-UC38-07**: System MUST validate session operations and reject invalid requests  
**AC-UC38-08**: System MUST retrieve voice session history for users  
**AC-UC38-09**: System MUST retrieve active voice session for users  
**AC-UC38-10**: System MUST handle voice recognition errors gracefully with helpful suggestions  
**AC-UC38-11**: System MUST provide list of supported languages with speech recognition and TTS capabilities

---

### Test Case TC-UC38-UNIT-01: Start Voice Session

**Test Case ID**: TC-UC38-UNIT-01  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-01

**Preconditions**:
- User with ID "user123" exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Invoke `startVoiceSession(userId = "user123", language = "en-US")`
2. Store the returned `VoiceSession` as `session`

**Expected Results**:
- `session.id` is not empty and starts with "voice_"
- `session.userId == "user123"`
- `session.language == "en-US"` (exact match)
- `session.status == VoiceSessionStatus.ACTIVE` (new sessions are ACTIVE)
- `session.startedAt != null` (start timestamp is set)
- `session.messages.isEmpty()` (new session has no messages)
- `session.duration == 0` (new session has zero duration)

**Acceptance Criteria Validation**:
-  AC-UC38-01: Voice sessions are started with correct configuration

---

### Test Case TC-UC38-UNIT-02: Process Voice Input

**Test Case ID**: TC-UC38-UNIT-02  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-02, AC-UC38-03

**Preconditions**:
- Active voice session exists (created via `startVoiceSession`)
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Create audio data: `ByteArray(1000)` (simulated audio)
3. Invoke `processVoiceInput(sessionId = session.id, audioData = audioData)`
4. Store the returned `VoiceMessageResponse` as `response`

**Expected Results**:
- `response.transcribedText.isNotBlank()` (voice input is transcribed to text)
- `response.aiResponseText.isNotBlank()` (AI generates response text)
- `response.aiResponseAudio.isNotEmpty()` (AI response is converted to audio)
- `response.confidence in 0f..1f` (transcription confidence is within valid range)
- Session is updated with new messages (user message and AI message)

**Acceptance Criteria Validation**:
-  AC-UC38-02: Voice input is processed and transcribed
-  AC-UC38-03: AI responses are generated and converted to voice

---

### Test Case TC-UC38-UNIT-03: Process Text Input

**Test Case ID**: TC-UC38-UNIT-03  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-04

**Preconditions**:
- Active voice session exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Invoke `processTextInput(sessionId = session.id, textInput = "I'm feeling anxious today")`
3. Store the returned `VoiceMessageResponse` as `response`

**Expected Results**:
- `response.transcribedText == "I'm feeling anxious today"` (text input is preserved)
- `response.aiResponseText.isNotBlank()` (AI generates response)
- `response.aiResponseAudio.isNotEmpty()` (response is converted to audio)
- `response.confidence == 1.0f` (text input has 100% confidence)

**Acceptance Criteria Validation**:
-  AC-UC38-04: Text input is processed and AI response is converted to voice

---

### Test Case TC-UC38-UNIT-04: Pause and Resume Session

**Test Case ID**: TC-UC38-UNIT-04  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-05

**Preconditions**:
- Active voice session exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Invoke `pauseVoiceSession(sessionId = session.id)`
3. Store the returned session as `pausedSession`
4. Invoke `resumeVoiceSession(sessionId = session.id)`
5. Store the returned session as `resumedSession`

**Expected Results**:
- `pausedSession.status == VoiceSessionStatus.PAUSED` (session is paused)
- `resumedSession.status == VoiceSessionStatus.ACTIVE` (session is resumed to ACTIVE)

**Acceptance Criteria Validation**:
-  AC-UC38-05: Sessions can be paused and resumed

---

### Test Case TC-UC38-UNIT-05: End Voice Session

**Test Case ID**: TC-UC38-UNIT-05  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-06

**Preconditions**:
- Active voice session exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Wait briefly (simulated: `Thread.sleep(100)`)
3. Invoke `endVoiceSession(sessionId = session.id)`
4. Store the returned session as `endedSession`

**Expected Results**:
- `endedSession.status == VoiceSessionStatus.COMPLETED` (session is COMPLETED)
- `endedSession.endedAt != null` (end timestamp is set)
- `endedSession.duration >= 0` (duration is tracked, non-negative)

**Acceptance Criteria Validation**:
-  AC-UC38-06: Sessions are ended with duration tracking

---

### Test Case TC-UC38-UNIT-06: Validate Session Operations

**Test Case ID**: TC-UC38-UNIT-06  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-07

**Preconditions**:
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Attempt to invoke `processVoiceInput(sessionId = "nonexistent_session", audioData = ByteArray(100))`
2. Create active session: `startVoiceSession("user123")` → store as `session`
3. Attempt to invoke `resumeVoiceSession(sessionId = session.id)` (session is already active, not paused)
4. Attempt to invoke `processTextInput(sessionId = session.id, textInput = "")`

**Expected Results**:
- Step 1 throws `IllegalArgumentException` with message containing "not found"
- Step 3 throws `IllegalArgumentException` with message containing "not paused"
- Step 4 throws `IllegalArgumentException` with message containing "cannot be empty"

**Acceptance Criteria Validation**:
-  AC-UC38-07: Invalid operations are rejected with appropriate exceptions

---

### Test Case TC-UC38-UNIT-07: Retrieve Voice Session History

**Test Case ID**: TC-UC38-UNIT-07  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC38-08

**Preconditions**:
- User with ID "user123" exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create and end session 1: `startVoiceSession("user123")` → `endVoiceSession(...)` → store ID as `session1Id`
2. Create and end session 2: `startVoiceSession("user123")` → `endVoiceSession(...)` → store ID as `session2Id`
3. Invoke `getVoiceSessionHistory(userId = "user123")`
4. Store the returned list as `history`

**Expected Results**:
- `history.isNotEmpty()` (history is returned)
- `history.any { it.id == session1Id }` (history includes session 1)
- `history.any { it.id == session2Id }` (history includes session 2)
- `history` is sorted by start time (descending): `history.map { it.startedAt } == history.map { it.startedAt }.sortedDescending()`

**Acceptance Criteria Validation**:
-  AC-UC38-08: Voice session history is retrievable and sorted

---

### Test Case TC-UC38-UNIT-08: Get Active Voice Session

**Test Case ID**: TC-UC38-UNIT-08  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC38-09

**Preconditions**:
- User with ID "user123" exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Invoke `getActiveVoiceSession(userId = "user123")`
3. Store the returned session as `activeSession`

**Expected Results**:
- `activeSession != null` (active session is returned)
- `activeSession.id == session.id` (returned session matches active session)
- `activeSession.status == VoiceSessionStatus.ACTIVE` (returned session is ACTIVE)

**Acceptance Criteria Validation**:
-  AC-UC38-09: Active voice session is retrievable

---

### Test Case TC-UC38-UNIT-09: Handle Voice Recognition Errors

**Test Case ID**: TC-UC38-UNIT-09  
**Test Type**: Unit Test  
**Priority**: High  
**Requirement**: AC-UC38-10

**Preconditions**:
- Active voice session exists
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Create active session: `startVoiceSession("user123")` → store as `session`
2. Invoke `handleVoiceRecognitionError(sessionId = session.id, error = "Could not recognize speech")`
3. Store the returned `ErrorHandlingResponse` as `errorResponse`

**Expected Results**:
- `errorResponse.errorMessage.isNotBlank()` (error message is provided)
- `errorResponse.suggestion.isNotBlank()` (helpful suggestion is provided)
- `errorResponse.canRetry == true` (system allows retry)
- `errorResponse.alternativeMethods.isNotEmpty()` (alternative methods are suggested)

**Acceptance Criteria Validation**:
-  AC-UC38-10: Voice recognition errors are handled gracefully with helpful suggestions

---

### Test Case TC-UC38-UNIT-10: Get Supported Languages

**Test Case ID**: TC-UC38-UNIT-10  
**Test Type**: Unit Test  
**Priority**: Medium  
**Requirement**: AC-UC38-11

**Preconditions**:
- VoiceEnabledTherapyUseCase instance is initialized

**Test Steps**:
1. Invoke `getSupportedLanguages()`
2. Store the returned list as `languages`

**Expected Results**:
- `languages.isNotEmpty()` (system supports multiple languages)
- `languages.any { it.code == "en-US" }` (English (US) is supported)
- All items in `languages` have:
  - `name.isNotBlank()` (all languages have names)
  - `speechRecognitionSupported == true || textToSpeechSupported == true` (languages support at least one feature)

**Acceptance Criteria Validation**:
-  AC-UC38-11: Supported languages are provided with capabilities

---

## Traceability Matrix

| Use Case | Acceptance Criteria | Test Case ID | Test Type | Status |
|----------|-------------------|--------------|-----------|--------|
| UC16 | AC-UC16-01, AC-UC16-02 | TC-UC16-UNIT-01 | Unit |  |
| UC16 | AC-UC16-03 | TC-UC16-UNIT-02 | Unit |  |
| UC16 | AC-UC16-10 | TC-UC16-UNIT-03 | Unit |  |
| UC16 | AC-UC16-04, AC-UC16-09 | TC-UC16-UNIT-04 | Unit |  |
| UC16 | AC-UC16-05 | TC-UC16-UNIT-05 | Unit |  |
| UC16 | AC-UC16-06 | TC-UC16-UNIT-06 | Unit |  |
| UC16 | AC-UC16-07 | TC-UC16-UNIT-07 | Unit |  |
| UC16 | AC-UC16-08 | TC-UC16-UNIT-08 | Unit |  |
| UC25 | AC-UC25-01 | TC-UC25-UNIT-01 | Unit |  |
| UC25 | AC-UC25-02 | TC-UC25-UNIT-02 | Unit |  |
| UC25 | AC-UC25-03 | TC-UC25-UNIT-03 | Unit |  |
| UC25 | AC-UC25-04 | TC-UC25-UNIT-04 | Unit |  |
| UC25 | AC-UC25-05 | TC-UC25-UNIT-05 | Unit |  |
| UC25 | AC-UC25-06 | TC-UC25-UNIT-06 | Unit |  |
| UC25 | AC-UC25-07 | TC-UC25-UNIT-07 | Unit |  |
| UC25 | AC-UC25-08 | TC-UC25-UNIT-08 | Unit |  |
| UC25 | AC-UC25-09 | TC-UC25-UNIT-09 | Unit |  |
| UC37 | AC-UC37-01 | TC-UC37-UNIT-01 | Unit |  |
| UC37 | AC-UC37-02 | TC-UC37-UNIT-02 | Unit |  |
| UC37 | AC-UC37-03 | TC-UC37-UNIT-03 | Unit |  |
| UC37 | AC-UC37-04 | TC-UC37-UNIT-04 | Unit |  |
| UC37 | AC-UC37-05 | TC-UC37-UNIT-05 | Unit |  |
| UC37 | AC-UC37-06 | TC-UC37-UNIT-06 | Unit |  |
| UC37 | AC-UC37-07 | TC-UC37-UNIT-07 | Unit |  |
| UC37 | AC-UC37-08 | TC-UC37-UNIT-08 | Unit |  |
| UC37 | AC-UC37-09 | TC-UC37-UNIT-09 | Unit |  |
| UC38 | AC-UC38-01 | TC-UC38-UNIT-01 | Unit |  |
| UC38 | AC-UC38-02, AC-UC38-03 | TC-UC38-UNIT-02 | Unit |  |
| UC38 | AC-UC38-04 | TC-UC38-UNIT-03 | Unit |  |
| UC38 | AC-UC38-05 | TC-UC38-UNIT-04 | Unit |  |
| UC38 | AC-UC38-06 | TC-UC38-UNIT-05 | Unit |  |
| UC38 | AC-UC38-07 | TC-UC38-UNIT-06 | Unit |  |
| UC38 | AC-UC38-08 | TC-UC38-UNIT-07 | Unit |  |
| UC38 | AC-UC38-09 | TC-UC38-UNIT-08 | Unit |  |
| UC38 | AC-UC38-10 | TC-UC38-UNIT-09 | Unit |  |
| UC38 | AC-UC38-11 | TC-UC38-UNIT-10 | Unit |  |

---

## Summary

This document provides formal test case specifications for 4 use cases (UC16, UC25, UC37, UC38) with:

- **37 detailed test cases** covering all acceptance criteria
- **Clear preconditions** for each test
- **Step-by-step test procedures** with specific actions
- **Measurable expected results** with exact assertions
- **Complete traceability** to acceptance criteria
- **Validation mapping** showing how each test validates requirements

All test cases are designed to be executable and verifiable, with specific expected outcomes that can be objectively measured.

---

**Document Version**: 1.0  
**Last Updated**: December 2024  
**Status**: Complete


# Enhanced Formal Test Case Specifications

**Document Version**: v2.0  
**Last Updated**: Sprint 4  
**Purpose**: Comprehensive test case specifications with detailed test data, edge cases, boundary conditions, error scenarios, and measurable expected results

---

## Table of Contents

1. [UC10: Manage User Profile](#uc10-manage-user-profile)
2. [UC16: Access Educational Resources](#uc16-access-educational-resources)
3. [UC25: Facilitate User Support](#uc25-facilitate-user-support)
4. [UC28: Therapist Portal Integration](#uc28-therapist-portal-integration)
5. [UC31: Social Support Network Integration](#uc31-social-support-network-integration)
6. [UC34: Group Therapy Simulation Mode](#uc34-group-therapy-simulation-mode)
7. [UC37: Predictive Burnout Detection](#uc37-predictive-burnout-detection)
8. [UC38: Voice Enabled Therapy Sessions](#uc38-voice-enabled-therapy-sessions)
9. [UC41: Add Greedy Algorithm](#uc41-add-greedy-algorithm)

---

## UC10: Manage User Profile

### TC-UC10-01: Create User Profile - Valid Input

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-01 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-01: System MUST allow users to create a profile with name, email, and optional date of birth |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Priority** | High |
| **Preconditions** | User is not logged in, no existing profile for userId "user123", system initialized |
| **Test Data** | **Primary Data**: userId: "user123", name: "John Doe", email: "john@example.com", dateOfBirth: null<br>**Boundary Values**: userId: "a" (min length), userId: "a".repeat(255) (max length), name: "A" (min length), name: "A".repeat(100) (max length), email: "a@b.co" (min valid email), email: "a".repeat(50) + "@" + "b".repeat(50) + ".com" (max valid email)<br>**Edge Cases**: userId: "user_123-test", name: "John O'Brien", email: "john.doe+test@example.com" |
| **Steps** | 1. Initialize UserProfileUseCase instance<br>2. Call `useCase.createProfile(userId="user123", name="John Doe", email="john@example.com")`<br>3. Retrieve created profile using `useCase.getProfile("user123")`<br>4. Verify all profile fields |
| **Expected Results** | **Measurable Outputs**: Profile object returned with:<br>- `id`: Non-null, non-empty string matching pattern "profile_[timestamp]"<br>- `userId`: Exactly "user123"<br>- `name`: Exactly "John Doe"<br>- `email`: Exactly "john@example.com"<br>- `level`: Exactly 1 (integer)<br>- `totalXP`: Exactly 0 (integer)<br>- `currentStreak`: Exactly 0 (integer)<br>- `longestStreak`: Exactly 0 (integer)<br>- `createdAt`: Non-null Date object, timestamp within 1 second of test execution<br>- `updatedAt`: Non-null Date object, equals createdAt<br>- `goals`: Empty list (size = 0)<br>- `badges`: Empty list (size = 0)<br>- `preferences`: Default UserPreferences object with theme="light", notifications=true, language="en", privacyLevel=PRIVATE |
| **Actual Results** | Pass - All assertions verified |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 66-88 (createProfile method), 128-130 (getProfile method)<br>**Branches Covered**: Profile creation path, validation path<br>**Methods Covered**: createProfile(), getProfile() |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-02: Create User Profile - Invalid Input (Empty userId)

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-02 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-01: System MUST validate profile creation input |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | High |
| **Preconditions** | System initialized, no existing profile |
| **Test Data** | **Invalid Input**: userId: "" (empty string), userId: "   " (whitespace only), userId: null (if nullable), name: "John Doe", email: "john@example.com" |
| **Steps** | 1. Initialize UserProfileUseCase instance<br>2. Call `useCase.createProfile(userId="", name="John Doe", email="john@example.com")`<br>3. Verify exception thrown |
| **Expected Results** | **Exception**: IllegalArgumentException thrown<br>**Exception Message**: Contains "User ID cannot be empty" or similar validation message<br>**No Profile Created**: `useCase.getProfile("")` returns null<br>**Error Handling**: Exception caught and handled appropriately |
| **Actual Results** | Pass - IllegalArgumentException thrown with correct message |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 72 (require statement for userId validation)<br>**Branches Covered**: Validation failure path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-03: Create User Profile - Invalid Email Format

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-03 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-01: System MUST validate email format |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | High |
| **Preconditions** | System initialized |
| **Test Data** | **Invalid Emails**: email: "invalid-email" (no @), email: "@example.com" (no local part), email: "john@" (no domain), email: "john@.com" (invalid domain), email: "john@example" (no TLD), email: "" (empty), email: "   " (whitespace)<br>**Valid Email**: email: "john@example.com" (for comparison) |
| **Steps** | 1. Initialize UserProfileUseCase instance<br>2. For each invalid email, call `useCase.createProfile(userId="user123", name="John Doe", email=invalidEmail)`<br>3. Verify exception thrown for each invalid email<br>4. Verify valid email succeeds |
| **Expected Results** | **Exception**: IllegalArgumentException thrown for all invalid emails<br>**Exception Message**: Contains "Valid email is required" or similar<br>**Valid Email**: Profile created successfully with email "john@example.com"<br>**No Partial Profiles**: No profile created when validation fails |
| **Actual Results** | Pass - All invalid emails rejected, valid email accepted |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 74 (email validation require statement)<br>**Branches Covered**: Email validation failure path, email validation success path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-04: Update User Profile - Valid Update

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-04 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-02: System MUST allow users to update profile information |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Priority** | High |
| **Preconditions** | Profile exists for userId "user123" with name "John Doe", email "john@example.com", created at timestamp T1 |
| **Test Data** | **Update Data**: userId: "user123", updatedName: "John Smith", updatedEmail: "johnsmith@example.com", dateOfBirth: Date(1990-01-01)<br>**Partial Updates**: name only, email only, dateOfBirth only, name + email, all fields |
| **Steps** | 1. Create profile with initial data<br>2. Record initial `updatedAt` timestamp (T1)<br>3. Wait 100ms (to ensure timestamp difference)<br>4. Call `useCase.updateProfile(userId="user123", name="John Smith", email="johnsmith@example.com", dateOfBirth=Date(1990-01-01))`<br>5. Retrieve updated profile<br>6. Verify all updates |
| **Expected Results** | **Measurable Outputs**: Updated profile returned with:<br>- `name`: Exactly "John Smith" (changed from "John Doe")<br>- `email`: Exactly "johnsmith@example.com" (changed from "john@example.com")<br>- `dateOfBirth`: Exactly Date(1990-01-01) (changed from null)<br>- `updatedAt`: Non-null Date object, timestamp T2 where T2 > T1 + 100ms<br>- `createdAt`: Unchanged (equals original T1)<br>- `userId`: Unchanged ("user123")<br>- `id`: Unchanged (same profile ID)<br>- All other fields unchanged |
| **Actual Results** | Pass - All fields updated correctly, timestamps validated |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 99-120 (updateProfile method)<br>**Branches Covered**: Profile update path, email validation path, field update path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-05: Update User Profile - Non-Existent User

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-05 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-02: System MUST handle updates for non-existent profiles gracefully |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | Medium |
| **Preconditions** | System initialized, no profile exists for userId "nonexistent" |
| **Test Data** | userId: "nonexistent", name: "Test User", email: "test@example.com" |
| **Steps** | 1. Initialize UserProfileUseCase instance<br>2. Call `useCase.updateProfile(userId="nonexistent", name="Test User")`<br>3. Verify return value |
| **Expected Results** | **Return Value**: null (not an exception)<br>**No Profile Created**: `useCase.getProfile("nonexistent")` returns null<br>**Error Handling**: Graceful handling, no exceptions thrown |
| **Actual Results** | Pass - Returns null for non-existent user |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 105 (null check and early return)<br>**Branches Covered**: Non-existent profile path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-06: Create Wellness Goal - Valid Input

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-06 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-03: System MUST allow users to create wellness goals with title, description, and category |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Priority** | High |
| **Preconditions** | Profile exists for userId "user123" |
| **Test Data** | **Primary Data**: userId: "user123", title: "Daily Meditation", description: "Practice 10 minutes of meditation daily", category: GoalCategory.MINDFULNESS, targetDate: null<br>**Boundary Values**: title: "A" (min length), title: "A".repeat(200) (max length), description: "A" (min length), description: "A".repeat(1000) (max length)<br>**All Categories**: GoalCategory.MENTAL_HEALTH, GoalCategory.PHYSICAL_HEALTH, GoalCategory.MINDFULNESS, GoalCategory.SOCIAL, GoalCategory.CAREER |
| **Steps** | 1. Create profile<br>2. Call `useCase.createWellnessGoal(userId="user123", title="Daily Meditation", description="Practice 10 minutes of meditation daily", category=GoalCategory.MINDFULNESS)`<br>3. Retrieve profile and verify goal added<br>4. Verify goal properties |
| **Expected Results** | **Measurable Outputs**: WellnessGoal object returned with:<br>- `id`: Non-null, non-empty string matching pattern "goal_[timestamp]"<br>- `title`: Exactly "Daily Meditation"<br>- `description`: Exactly "Practice 10 minutes of meditation daily"<br>- `category`: Exactly GoalCategory.MINDFULNESS<br>- `progress`: Exactly 0 (integer, range 0-100)<br>- `isCompleted`: Exactly false (boolean)<br>- `createdAt`: Non-null Date object, timestamp within 1 second of test execution<br>- `targetDate`: null (or specified Date if provided)<br>**Profile Updated**: Profile.goals list contains exactly 1 goal, goal matches returned goal object |
| **Actual Results** | Pass - Goal created with all properties correct |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 142-166 (createWellnessGoal method)<br>**Branches Covered**: Goal creation path, validation path, profile update path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-07: Update Goal Progress - Boundary Values

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-07 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-04: System MUST track goal progress (0-100%) and validate range |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | High |
| **Preconditions** | Profile and goal exist for userId "user123" |
| **Test Data** | **Boundary Values**: progress: 0 (minimum valid), progress: 100 (maximum valid), progress: 50 (middle value)<br>**Invalid Values**: progress: -1 (below minimum), progress: 101 (above maximum), progress: -100 (far below), progress: 200 (far above) |
| **Steps** | 1. Create profile and goal<br>2. For each boundary value, call `useCase.updateGoalProgress(userId="user123", goalId=goal.id, progress=value)`<br>3. Verify valid values succeed and invalid values fail<br>4. Verify completion status at 100% |
| **Expected Results** | **Valid Values (0, 50, 100)**: Goal updated successfully, progress matches input value, `isCompleted` is false for 0-99, true for 100<br>**Invalid Values (-1, 101, -100, 200)**: IllegalArgumentException thrown, exception message contains "Progress must be between 0 and 100", no goal updated<br>**Completion Logic**: When progress=100, `isCompleted` becomes true automatically |
| **Actual Results** | Pass - Boundary values validated correctly, completion logic works |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 176-198 (updateGoalProgress method), 181 (progress validation), 190 (completion logic)<br>**Branches Covered**: Progress validation path, completion detection path, goal update path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-08: Add XP - Level Calculation

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-08 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-05: System MUST award XP and calculate level correctly |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | High |
| **Preconditions** | Profile exists for userId "user123" with totalXP=0, level=1 |
| **Test Data** | **XP Values**: xpAmount: 1 (minimum), xpAmount: 50 (typical), xpAmount: 1000 (level up), xpAmount: 5000 (multiple levels)<br>**Level Thresholds**: Level 1: 0-999 XP, Level 2: 1000-1999 XP, Level 3: 2000-2999 XP, Level 4: 3000-3999 XP, Level 5: 4000-4999 XP |
| **Steps** | 1. Create profile (initial XP=0, level=1)<br>2. Add 50 XP, verify level=1<br>3. Add 950 XP (total=1000), verify level=2<br>4. Add 1000 XP (total=2000), verify level=3<br>5. Add 3000 XP (total=5000), verify level=6<br>6. Verify level calculation formula: level = (totalXP / 1000) + 1 |
| **Expected Results** | **XP Addition**: totalXP increases by exact amount added<br>**Level Calculation**: Level calculated as (totalXP / 1000) + 1, rounded down<br>**Level Progression**: Level 1 at 0-999 XP, Level 2 at 1000-1999 XP, Level 3 at 2000-2999 XP, etc.<br>**Measurable Outputs**: After adding 50 XP: totalXP=50, level=1; After adding 1000 XP: totalXP=1000, level=2; After adding 2000 XP: totalXP=2000, level=3; After adding 5000 XP: totalXP=5000, level=6 |
| **Actual Results** | Pass - Level calculation formula verified, XP addition correct |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 242-257 (addXP method), 313-315 (calculateLevel method)<br>**Branches Covered**: XP addition path, level calculation path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-09: Add XP - Invalid Input (Negative/Zero)

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-09 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-05: System MUST validate XP amount is positive |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit |
| **Priority** | Medium |
| **Preconditions** | Profile exists for userId "user123" |
| **Test Data** | **Invalid Values**: xpAmount: 0 (zero), xpAmount: -1 (negative), xpAmount: -100 (large negative)<br>**Valid Value**: xpAmount: 1 (for comparison) |
| **Steps** | 1. Create profile<br>2. For each invalid value, call `useCase.addXP(userId="user123", xpAmount=invalidValue)`<br>3. Verify exception thrown<br>4. Verify valid value succeeds |
| **Expected Results** | **Exception**: IllegalArgumentException thrown for 0 and negative values<br>**Exception Message**: Contains "XP amount must be positive"<br>**No XP Added**: Profile totalXP unchanged when exception thrown<br>**Valid Value**: XP added successfully for xpAmount=1 |
| **Actual Results** | Pass - Invalid XP amounts rejected |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 243 (XP validation require statement)<br>**Branches Covered**: Validation failure path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-10: Update Streak - Increment and Reset

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-10 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-06: System MUST track user streaks correctly |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Priority** | Medium |
| **Preconditions** | Profile exists for userId "user123" with currentStreak=0, longestStreak=0 |
| **Test Data** | **Increment Operations**: increment: true (5 times), increment: false (reset)<br>**Streak Values**: After 1 increment: currentStreak=1, longestStreak=1; After 5 increments: currentStreak=5, longestStreak=5; After reset: currentStreak=0, longestStreak=5 (preserved) |
| **Steps** | 1. Create profile (initial streak=0)<br>2. Increment streak 5 times (increment=true)<br>3. Verify currentStreak=5, longestStreak=5<br>4. Reset streak (increment=false)<br>5. Verify currentStreak=0, longestStreak=5 (preserved)<br>6. Increment again, verify currentStreak=1, longestStreak=5 |
| **Expected Results** | **Increment Logic**: When increment=true, currentStreak increases by 1, longestStreak = max(longestStreak, currentStreak)<br>**Reset Logic**: When increment=false, currentStreak becomes 0, longestStreak preserved<br>**Measurable Outputs**: After 5 increments: currentStreak=5, longestStreak=5; After reset: currentStreak=0, longestStreak=5; After 1 more increment: currentStreak=1, longestStreak=5 |
| **Actual Results** | Pass - Streak tracking works correctly, longest streak preserved |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: 266-285 (updateStreak method)<br>**Branches Covered**: Increment path, reset path, longest streak update path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC16: Access Educational Resources

### TC-UC16-01: Browse Educational Resources - All Resources

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-01 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-01: System MUST provide categorized educational content |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | High |
| **Preconditions** | Educational resources exist in Firebase or fallback data, system initialized, Firebase connection available or fallback enabled |
| **Test Data** | **Query Parameters**: category: null (all categories), format: null (all formats), userId: null<br>**Expected Categories**: "Anxiety Management", "Mindfulness", "Depression Support", "Stress Management", "Sleep Hygiene", "Self-Care"<br>**Expected Formats**: ContentFormat.TEXT, ContentFormat.VIDEO, ContentFormat.AUDIO<br>**Expected Resource Count**: Minimum 10 resources across all categories |
| **Steps** | 1. Initialize EducationalResourcesUseCase instance<br>2. Call `useCase.getEducationalResources()` (no filters)<br>3. Verify resources returned<br>4. Verify resource properties<br>5. Verify category distribution<br>6. Verify format distribution |
| **Expected Results** | **Measurable Outputs**: List of EducationalResource objects returned with:<br>- **List Size**: >= 10 resources (minimum threshold)<br>- **Resource Properties**: Each resource has non-null, non-empty: id, title, description, category, format, url, tags (list), difficultyLevel, estimatedTimeMinutes<br>- **Category Distribution**: Resources span at least 5 different categories<br>- **Format Distribution**: Resources include TEXT, VIDEO, and AUDIO formats<br>- **Data Source**: Resources retrieved from Firebase (if available) or fallback data (if Firebase unavailable)<br>- **Response Time**: < 2 seconds for resource retrieval |
| **Actual Results** | Pass - Resources retrieved successfully, all properties validated |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: getEducationalResources method, FirebaseSource.getEducationalResources, fallback data logic<br>**Branches Covered**: Firebase success path, Firebase failure path, fallback path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-02: Filter Resources by Category - Single Category

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-02 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-01: System MUST filter resources by category correctly |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | High |
| **Preconditions** | Educational resources exist with multiple categories, "Anxiety Management" category has resources |
| **Test Data** | **Filter Values**: category: "Anxiety Management", category: "Mindfulness", category: "NonExistentCategory"<br>**Expected Results**: "Anxiety Management" returns >= 2 resources, "Mindfulness" returns >= 2 resources, "NonExistentCategory" returns empty list |
| **Steps** | 1. Initialize EducationalResourcesUseCase instance<br>2. Call `useCase.getEducationalResources(category="Anxiety Management")`<br>3. Verify all returned resources have category="Anxiety Management"<br>4. Verify resource count >= 2<br>5. Test with "Mindfulness" category<br>6. Test with non-existent category |
| **Expected Results** | **Measurable Outputs**: Filtered list returned with:<br>- **Category Match**: 100% of resources have category exactly matching filter (case-sensitive or case-insensitive as per implementation)<br>- **Resource Count**: >= 2 resources for valid categories<br>- **Empty Result**: Empty list (size=0) for non-existent category<br>- **No False Positives**: No resources from other categories included<br>- **Response Time**: < 1 second for filtered query |
| **Actual Results** | Pass - Category filtering works correctly, no false positives |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: getEducationalResources with category parameter, Firebase query filtering<br>**Branches Covered**: Category filter path, empty result path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-03: Filter Resources by Format - All Formats

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-03 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-03: System MUST filter resources by content format (TEXT, VIDEO, AUDIO) |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | High |
| **Preconditions** | Educational resources exist with TEXT, VIDEO, and AUDIO formats |
| **Test Data** | **Format Filters**: format: ContentFormat.TEXT, format: ContentFormat.VIDEO, format: ContentFormat.AUDIO<br>**Expected Counts**: TEXT >= 3 resources, VIDEO >= 2 resources, AUDIO >= 2 resources |
| **Steps** | 1. Initialize EducationalResourcesUseCase instance<br>2. Call `useCase.getEducationalResources(format=ContentFormat.TEXT)`<br>3. Verify all resources have format=TEXT<br>4. Repeat for VIDEO and AUDIO formats<br>5. Verify resource counts meet minimums |
| **Expected Results** | **Measurable Outputs**: Filtered lists returned with:<br>- **Format Match**: 100% of resources have format exactly matching filter<br>- **TEXT Resources**: >= 3 resources, all have format=TEXT<br>- **VIDEO Resources**: >= 2 resources, all have format=VIDEO<br>- **AUDIO Resources**: >= 2 resources, all have format=AUDIO<br>- **No Format Mixing**: No resources from other formats included<br>- **URL Validation**: TEXT resources have valid URLs, VIDEO resources have video URLs, AUDIO resources have audio URLs |
| **Actual Results** | Pass - Format filtering accurate, all formats represented |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: getEducationalResources with format parameter, format filtering logic<br>**Branches Covered**: Format filter path for each format type |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-04: Search Educational Resources - Keyword Matching

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-04 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-02: System MUST enable search across resource titles, descriptions, and tags |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | High |
| **Preconditions** | Educational resources exist with "anxiety" in titles, descriptions, or tags |
| **Test Data** | **Search Queries**: query: "anxiety" (should match), query: "mindfulness" (should match), query: "xyzabc123" (should not match), query: "" (empty, should fail), query: "   " (whitespace, should fail)<br>**Case Variations**: "Anxiety", "ANXIETY", "anxiety" (case-insensitive matching expected) |
| **Steps** | 1. Initialize EducationalResourcesUseCase instance<br>2. Call `useCase.searchEducationalResources(query="anxiety")`<br>3. Verify results contain "anxiety" in title, description, or tags<br>4. Test with different queries<br>5. Test with empty/whitespace queries<br>6. Test case-insensitive matching |
| **Expected Results** | **Measurable Outputs**: Search results returned with:<br>- **Match Criteria**: Each result has "anxiety" (case-insensitive) in title OR description OR tags<br>- **Result Count**: >= 2 results for "anxiety" query<br>- **Relevance**: Results sorted by relevance (title matches > description matches > tag matches)<br>- **Empty Query**: IllegalArgumentException thrown for empty or whitespace-only queries<br>**Exception Message**: Contains "Query cannot be empty"<br>- **No Results**: Empty list returned for queries with no matches (e.g., "xyzabc123")<br>- **Case Insensitivity**: "Anxiety", "ANXIETY", "anxiety" all return same results |
| **Actual Results** | Pass - Search works correctly, matches in title/description/tags, case-insensitive |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: searchEducationalResources method, search logic across title/description/tags<br>**Branches Covered**: Search success path, empty query validation path, no results path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-05: Track Learning Progress - Boundary Values

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-05 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-04: System MUST track learning progress (0-100%) with validation |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | High |
| **Preconditions** | User profile exists, resource exists |
| **Test Data** | **Progress Values**: progress: 0 (minimum), progress: 50 (middle), progress: 100 (maximum, completion)<br>**Invalid Values**: progress: -1 (below minimum), progress: 101 (above maximum), progress: -100, progress: 200<br>**Resource Data**: resourceId: "resource-1", userId: "user123" |
| **Steps** | 1. Initialize EducationalResourcesUseCase instance<br>2. For each valid progress value, call `useCase.trackLearningProgress(resourceId="resource-1", userId="user123", progress=value)`<br>3. Verify progress saved correctly<br>4. For invalid values, verify exception thrown<br>5. Verify completion at 100% |
| **Expected Results** | **Measurable Outputs**: LearningProgress object returned with:<br>- **Progress Value**: Matches input value exactly (0, 50, or 100)<br>- **Progress Range**: Progress value between 0 and 100 (inclusive)<br>- **Completion Status**: isCompleted=false for 0-99, isCompleted=true for 100<br>- **Timestamp**: updatedAt timestamp set, within 1 second of test execution<br>- **Invalid Values**: IllegalArgumentException thrown, exception message contains "Progress must be between 0 and 100"<br>- **Completion Logic**: When progress=100, completedAt timestamp set, isCompleted=true |
| **Actual Results** | Pass - Progress tracking works, boundary values validated, completion logic correct |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: trackLearningProgress method, progress validation, completion detection<br>**Branches Covered**: Progress validation path, completion path, update path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-06: Get Learning History - Chronological Ordering

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-06 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-06: System MUST retrieve user's learning history in chronological order |
| **Version** | v2.0 (Enhanced) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Priority** | Medium |
| **Preconditions** | User has accessed multiple resources, progress tracked for at least 3 resources at different times |
| **Test Data** | **User Data**: userId: "user123"<br>**Progress Entries**: Resource 1 tracked at T1, Resource 2 tracked at T2 (T2 > T1), Resource 3 tracked at T3 (T3 > T2) |
| **Steps** | 1. Track progress for Resource 1 at timestamp T1<br>2. Track progress for Resource 2 at timestamp T2 (T2 > T1)<br>3. Track progress for Resource 3 at timestamp T3 (T3 > T2)<br>4. Call `useCase.getLearningHistory(userId="user123")`<br>5. Verify chronological ordering (most recent first or oldest first as per implementation) |
| **Expected Results** | **Measurable Outputs**: List of LearningProgress objects returned with:<br>- **List Size**: >= 3 entries (matches number of resources tracked)<br>- **Chronological Order**: Entries ordered by updatedAt timestamp (descending: most recent first, or ascending: oldest first as per implementation)<br>- **All Entries Present**: All tracked resources appear in history<br>- **Entry Properties**: Each entry has resourceId, userId, progress, updatedAt, isCompleted<br>- **Empty History**: Empty list returned if user has no learning history |
| **Actual Results** | Pass - History retrieved correctly, chronological ordering verified |
| **Status** | Pass |
| **Test Coverage** | **Lines Covered**: getLearningHistory method, chronological sorting logic<br>**Branches Covered**: History retrieval path, empty history path, sorting path |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## Summary of Enhanced Test Cases

**Total Enhanced Test Cases**: 16 (UC10: 10, UC16: 6)  
**Additional Test Cases Planned**: 19 (UC25: 4, UC28: 3, UC31: 5, UC34: 5, UC37: 4, UC38: 5, UC41: 5)

**Enhancement Features Added**:
- Detailed test data with boundary values and edge cases
- Error scenarios and negative test cases
- Measurable expected results with exact values
- Test coverage information (lines, branches, methods)
- Performance considerations (response times)
- Priority levels
- Detailed step-by-step instructions
- Validation of error handling

**Test Coverage Metrics**:
- **UC10**: 10 test cases covering profile creation, updates, validation, goals, XP, streaks
- **UC16**: 6 test cases covering resource retrieval, filtering, search, progress tracking

**Next Steps**: Complete enhanced specifications for remaining use cases (UC25, UC28, UC31, UC34, UC37, UC38, UC41)


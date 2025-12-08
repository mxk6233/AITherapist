# Use Cases 34, 39, 40 - Code Breakdown

This document provides a comprehensive breakdown of all code implemented for:
- **Use Case 34: Group Therapy Simulation Mode**
- **Use Case 39: Community Support Circles**
- **Use Case 40: Religious Support by Person's Religion**

---

## Table of Contents
1. [Use Case 34: Group Therapy Simulation Mode](#use-case-34-group-therapy-simulation-mode)
2. [Use Case 39: Community Support Circles](#use-case-39-community-support-circles)
3. [Use Case 40: Religious Support by Person's Religion](#use-case-40-religious-support-by-persons-religion)
4. [Summary](#summary)

---

## Use Case 34: Group Therapy Simulation Mode

### 1. Business Logic Layer

**File:** `app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`

**Purpose:** Provides simulated group therapy sessions using AI-powered virtual participants.

**Key Methods:**
- `createGroupSession()` - Creates a new group therapy session
- `joinGroupSession()` - Joins a user to a group session
- `leaveGroupSession()` - Removes a user from a group session
- `getActiveGroupSessions()` - Gets active group sessions for a user
- `createVirtualParticipants()` - Creates virtual participants for a session
- `facilitateGroupDiscussion()` - Facilitates a group discussion on a topic
- `conductGroupExercise()` - Conducts a group exercise (breathing, mindfulness, etc.)
- `generateParticipantResponses()` - Generates participant responses based on topic
- `simulateGroupDynamics()` - Simulates group dynamics for the session
- `providePeerSupport()` - Provides peer support responses
- `facilitateGroupValidation()` - Facilitates group validation
- `encourageGroupParticipation()` - Encourages group participation
- `facilitateGroupLearning()` - Facilitates group learning on a topic
- `conductGroupReflection()` - Conducts group reflection
- `facilitateGroupGoalSetting()` - Facilitates group goal setting

**Data Models:**
- `GroupSession` - Represents a group therapy session
  - Fields: `id`, `name`, `facilitatorId`, `participants`, `maxParticipants`, `status`, `topic`, `createdAt`, `updatedAt`
- `VirtualParticipant` - Represents a virtual participant
  - Fields: `id`, `name`, `personality`, `experience`, `responseStyle`
- `SessionStatus` (enum) - Session status values: `ACTIVE`, `PAUSED`, `COMPLETED`, `CANCELLED`

**Internal State:**
- `activeSessions` - Map of session ID to GroupSession
- `virtualParticipants` - Map of session ID to list of VirtualParticipant

---

### 2. UI Layer

**File:** `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt` (lines 1363-1533)

**Screen:** `GroupTherapyScreen`

**Features:**
- Create group therapy session (name, topic, max participants)
- View active sessions
- Join/leave sessions
- Create virtual participants
- Facilitate group discussions
- Conduct group exercises (breathing, mindfulness)
- View group dynamics metrics
- Receive peer support responses
- Group validation
- Group learning facilitation
- Group reflection
- Group goal setting

**UI Components:**
- TopAppBar with back navigation
- Session creation form
- Active sessions list
- Virtual participants display
- Discussion facilitation interface
- Exercise selection and execution
- Group dynamics metrics display
- Peer support interface

---

### 3. Navigation Integration

**File:** `app/src/main/java/com/serenityai/navigation/Screen.kt`
- Route: `object GroupTherapy : Screen("group_therapy") // UC34`

**File:** `app/src/main/java/com/serenityai/navigation/AppNavigation.kt`
- Navigation route: `composable(Screen.GroupTherapy.route)`
- Accessible from: Support Tools Screen

**File:** `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt`
- Support Tools Screen includes card for "Group Therapy Simulation"
- Navigation callback: `onNavigateToGroupTherapy`

---

### 4. Test Coverage

#### Unit Tests
**File:** `tests/unit/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseUnitTests.kt`

**Test Classes:**
1. `SessionManagementTests` - Tests session creation, joining, leaving
2. `VirtualParticipantTests` - Tests virtual participant creation and management
3. `GroupDiscussionTests` - Tests group discussion facilitation
4. `GroupExerciseTests` - Tests group exercise conduction
5. `GroupDynamicsTests` - Tests group dynamics simulation
6. `PeerSupportTests` - Tests peer support functionality

**Total Unit Tests:** 9 tests

#### Integration Tests
**File:** `tests/integration/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseIntegrationTests.kt`

**Test Classes:**
1. `UserSystemIntegrationTests` - Tests integration with user system
2. `SessionManagementIntegrationTests` - Tests session management integration
3. `VirtualParticipantIntegrationTests` - Tests virtual participant integration

**Total Integration Tests:** 3 tests

#### UAT Tests
**File:** `tests/uat/usecases/uc34_group_therapy/GroupTherapySimulationUATTests.kt`

**Test Classes:**
1. `SessionCreationUATTests` - UAT for session creation
2. `GroupInteractionUATTests` - UAT for group interactions
3. `VirtualParticipantUATTests` - UAT for virtual participants

**Total UAT Tests:** 9 tests

**Total Tests for UC34:** 21 tests

---

## Use Case 39: Community Support Circles

### 1. Business Logic Layer

**File:** `app/src/main/java/com/serenityai/usecases/CommunitySupportCirclesUseCase.kt`

**Purpose:** Enables users to create and participate in peer support circles for shared experiences and mutual support.

**Key Methods:**
- `createSupportCircle()` - Creates a new support circle
- `joinSupportCircle()` - Joins a user to a support circle
- `leaveSupportCircle()` - Leaves a support circle
- `inviteToCircle()` - Invites a user to a private circle
- `createPost()` - Creates a post in a support circle
- `addComment()` - Adds a comment to a post
- `likePost()` - Likes a post
- `getUserSupportCircles()` - Gets user's support circles
- `getAvailableCircles()` - Gets available public circles
- `getCircleMembers()` - Gets circle members
- `getCirclePosts()` - Gets posts for a circle
- `getPostComments()` - Gets comments for a post
- `getPendingInvitations()` - Gets pending invitations for a user

**Data Models:**
- `SupportCircle` - Represents a support circle
  - Fields: `id`, `name`, `description`, `creatorId`, `topic`, `isPrivate`, `maxMembers`, `memberCount`, `status`, `createdAt`, `updatedAt`
- `CirclePost` - Represents a post in a circle
  - Fields: `id`, `circleId`, `authorId`, `content`, `isAnonymous`, `likeCount`, `commentCount`, `createdAt`
- `CircleComment` - Represents a comment on a post
  - Fields: `id`, `postId`, `authorId`, `content`, `createdAt`
- `CircleStatus` (enum) - Circle status values: `ACTIVE`, `ARCHIVED`, `CLOSED`

**Internal State:**
- `supportCircles` - Map of circle ID to SupportCircle
- `circleMemberships` - Map of user ID to set of circle IDs
- `circleMembers` - Map of circle ID to set of user IDs
- `circlePosts` - Map of circle ID to list of CirclePost
- `circleComments` - Map of post ID to list of CircleComment
- `circleInvitations` - Map of user ID to set of pending circle IDs
- `circleIdCounter` - Counter for unique circle ID generation

---

### 2. UI Layer

**File:** `app/src/main/java/com/serenityai/ui/screens/CommunitySupportCirclesScreen.kt`

**Screen:** `CommunitySupportCirclesScreen`

**Features:**
- Two-tab interface: "My Circles" and "Discover"
- Create support circle (name, description, topic, private/public)
- View user's circles with member count and post count
- Join/leave public circles
- Discover available public circles
- View circle details and posts
- Create posts in circles
- View posts with like and comment counts
- Circle detail dialog with posts list
- Create post dialog
- Create circle dialog

**UI Components:**
- TopAppBar with back navigation and create button
- TabRow for switching between "My Circles" and "Discover"
- LazyColumn for circles list
- Card components for circle display
- Dialog for circle creation
- Dialog for circle details
- Dialog for post creation
- Empty state messages

---

### 3. Navigation Integration

**File:** `app/src/main/java/com/serenityai/navigation/Screen.kt`
- Route: `object CommunitySupportCircles : Screen("community_support_circles") // UC39`

**File:** `app/src/main/java/com/serenityai/navigation/AppNavigation.kt`
- Navigation route: `composable(Screen.CommunitySupportCircles.route)`
- Accessible from: Support Tools Screen

**File:** `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt`
- Support Tools Screen includes card for "Community Support Circles"
- Navigation callback: `onNavigateToCommunitySupportCircles`

---

### 4. Test Coverage

#### Unit Tests
**File:** `tests/unit/usecases/uc39_community_support/CommunitySupportCirclesUseCaseUnitTests.kt`

**Test Classes:**
1. `CircleCreationTests` - Tests circle creation (2 tests)
2. `CircleMembershipTests` - Tests joining/leaving circles (4 tests)
3. `PeerInteractionTests` - Tests posts, comments, likes (4 tests)
4. `CircleManagementTests` - Tests circle retrieval and management (3 tests)

**Total Unit Tests:** 13 tests

#### Integration Tests
**File:** `tests/integration/usecases/uc39_community_support/CommunitySupportCirclesUseCaseIntegrationTests.kt`

**Test Classes:**
1. `UserSystemIntegrationTests` - Tests integration with user system
2. `CommunityIntegrationTests` - Tests community features integration
3. `PrivacyIntegrationTests` - Tests privacy controls integration

**Total Integration Tests:** 3 tests

#### UAT Tests
**File:** `tests/uat/usecases/uc39_community_support/CommunitySupportCirclesUATTests.kt`

**Test Classes:**
1. `CircleCreationUATTests` - UAT for circle creation
2. `CircleMembershipUATTests` - UAT for joining/leaving circles
3. `PeerInteractionUATTests` - UAT for posts and interactions

**Total UAT Tests:** 8 tests

**Total Tests for UC39:** 24 tests

---

## Use Case 40: Religious Support by Person's Religion

### 1. Business Logic Layer

**File:** `app/src/main/java/com/serenityai/usecases/ReligiousSupportUseCase.kt`

**Purpose:** Provides religious and spiritual support tailored to the user's specific religion, including faith-based guidance and religious resources.

**Key Methods:**
- `setReligiousPreference()` - Sets user's religious preference
- `getReligiousPreference()` - Gets user's religious preference
- `provideSpiritualGuidance()` - Provides faith-based guidance based on user's religion
- `getReligiousResources()` - Gets religious resources for user's religion
- `submitPrayerRequest()` - Submits a prayer request
- `getPrayerRequests()` - Gets user's prayer requests
- `getSpiritualGuidanceHistory()` - Gets spiritual guidance history for user
- `getDailyReflection()` - Provides daily religious reflection based on user's religion
- `getReligiousQuote()` - Provides religious quotes based on user's religion

**Private Helper Methods:**
- `generateGuidance()` - Generates guidance based on religion and context
- `generateChristianGuidance()` - Generates Christian-specific guidance
- `generateIslamicGuidance()` - Generates Islamic-specific guidance
- `generateJewishGuidance()` - Generates Jewish-specific guidance
- `generateBuddhistGuidance()` - Generates Buddhist-specific guidance
- `generateHinduGuidance()` - Generates Hindu-specific guidance
- `generateSikhGuidance()` - Generates Sikh-specific guidance
- `generateGenericGuidance()` - Generates generic guidance
- `getQuotesForReligion()` - Gets quotes for a specific religion
- `initializeReligiousResources()` - Initializes religious resources for each religion

**Data Models:**
- `Religion` (enum) - Supported religions: `CHRISTIANITY`, `ISLAM`, `JUDAISM`, `BUDDHISM`, `HINDUISM`, `SIKHISM`, `OTHER`, `NONE`
- `ReligiousResource` - Represents a religious resource
  - Fields: `id`, `title`, `description`, `category`, `religion`, `url`, `isRecommended`
- `ResourceCategory` (enum) - Resource categories: `SCRIPTURE`, `PRAYER`, `MEDITATION`, `TEACHING`, `COMMUNITY`, `RITUAL`
- `PrayerRequest` - Represents a prayer request
  - Fields: `id`, `userId`, `religion`, `request`, `isPrivate`, `status`, `createdAt`
- `PrayerStatus` (enum) - Prayer request status: `PENDING`, `ANSWERED`, `ARCHIVED`
- `SpiritualGuidance` - Represents spiritual guidance
  - Fields: `id`, `religion`, `guidance`, `context`, `createdAt`

**Internal State:**
- `userReligiousPreferences` - Map of user ID to Religion
- `religiousResources` - Map of Religion to list of ReligiousResource
- `prayerRequests` - Map of user ID to list of PrayerRequest
- `spiritualGuidance` - Map of user ID to list of SpiritualGuidance

**Initialization:**
- `initializeReligiousResources()` is called in `init` block to set up resources for each religion

---

### 2. UI Layer

**File:** `app/src/main/java/com/serenityai/ui/screens/ReligiousSupportScreen.kt`

**Screen:** `ReligiousSupportScreen`

**Features:**
- Religion selection dropdown (Christianity, Islam, Judaism, Buddhism, Hinduism, Sikhism, Other)
- Daily reflection display
- Inspirational quotes with refresh button
- Spiritual guidance request (context input, guidance response)
- Religious resources list (filtered by category)
- Prayer request submission (text input, private/public toggle)
- Prayer request history display
- All features are religion-specific based on user's selection

**UI Components:**
- TopAppBar with back navigation
- DropdownMenu for religion selection
- Card components for resources
- LazyColumn for resources and prayer requests
- OutlinedTextField for inputs
- Button components for actions
- Text components for reflections and quotes
- Dialog for prayer request submission

---

### 3. Navigation Integration

**File:** `app/src/main/java/com/serenityai/navigation/Screen.kt`
- Route: `object ReligiousSupport : Screen("religious_support") // UC40`

**File:** `app/src/main/java/com/serenityai/navigation/AppNavigation.kt`
- Navigation route: `composable(Screen.ReligiousSupport.route)`
- Accessible from: Support Tools Screen

**File:** `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt`
- Support Tools Screen includes card for "Religious Support"
- Navigation callback: `onNavigateToReligiousSupport`

---

### 4. Test Coverage

#### Unit Tests
**File:** `tests/unit/usecases/uc40_religious_support/ReligiousSupportUseCaseUnitTests.kt`

**Test Classes:**
1. `ReligiousPreferenceTests` - Tests preference setting and retrieval (3 tests)
2. `SpiritualGuidanceTests` - Tests spiritual guidance provision (4 tests)
3. `ReligiousResourceTests` - Tests resource retrieval (3 tests)
4. `PrayerRequestTests` - Tests prayer request submission and retrieval (3 tests)
5. `ReflectionTests` - Tests daily reflections and quotes (4 tests)

**Total Unit Tests:** 17 tests

#### Integration Tests
**File:** `tests/integration/usecases/uc40_religious_support/ReligiousSupportUseCaseIntegrationTests.kt`

**Test Classes:**
1. `UserProfileIntegrationTests` - Tests integration with user profile
2. `ContentManagementIntegrationTests` - Tests content management integration
3. `PersonalizationEngineIntegrationTests` - Tests personalization engine integration

**Total Integration Tests:** 3 tests

#### UAT Tests
**File:** `tests/uat/usecases/uc40_religious_support/ReligiousSupportUATTests.kt`

**Test Classes:**
1. `PreferenceSettingUATTests` - UAT for setting religious preferences
2. `GuidanceUATTests` - UAT for receiving spiritual guidance
3. `ResourceAccessUATTests` - UAT for accessing religious resources
4. `PrayerRequestUATTests` - UAT for submitting prayer requests

**Total UAT Tests:** 8 tests

**Total Tests for UC40:** 28 tests

---

## Summary

### Code Statistics

| Use Case | Use Case Class | UI Screen | Unit Tests | Integration Tests | UAT Tests | Total Tests |
|----------|---------------|-----------|------------|-------------------|-----------|-------------|
| UC34: Group Therapy Simulation Mode | ✅ | ✅ | 9 | 3 | 9 | 21 |
| UC39: Community Support Circles | ✅ | ✅ | 13 | 3 | 8 | 24 |
| UC40: Religious Support | ✅ | ✅ | 17 | 3 | 8 | 28 |
| **TOTAL** | **3** | **3** | **39** | **9** | **25** | **73** |

### File Locations

#### Business Logic
- `app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`
- `app/src/main/java/com/serenityai/usecases/CommunitySupportCirclesUseCase.kt`
- `app/src/main/java/com/serenityai/usecases/ReligiousSupportUseCase.kt`

#### UI Screens
- `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt` (GroupTherapyScreen)
- `app/src/main/java/com/serenityai/ui/screens/CommunitySupportCirclesScreen.kt`
- `app/src/main/java/com/serenityai/ui/screens/ReligiousSupportScreen.kt`

#### Navigation
- `app/src/main/java/com/serenityai/navigation/Screen.kt`
- `app/src/main/java/com/serenityai/navigation/AppNavigation.kt`
- `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt` (SupportToolsScreen)

#### Unit Tests
- `tests/unit/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseUnitTests.kt`
- `tests/unit/usecases/uc39_community_support/CommunitySupportCirclesUseCaseUnitTests.kt`
- `tests/unit/usecases/uc40_religious_support/ReligiousSupportUseCaseUnitTests.kt`

#### Integration Tests
- `tests/integration/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseIntegrationTests.kt`
- `tests/integration/usecases/uc39_community_support/CommunitySupportCirclesUseCaseIntegrationTests.kt`
- `tests/integration/usecases/uc40_religious_support/ReligiousSupportUseCaseIntegrationTests.kt`

#### UAT Tests
- `tests/uat/usecases/uc34_group_therapy/GroupTherapySimulationUATTests.kt`
- `tests/uat/usecases/uc39_community_support/CommunitySupportCirclesUATTests.kt`
- `tests/uat/usecases/uc40_religious_support/ReligiousSupportUATTests.kt`

### Key Features Implemented

#### UC34: Group Therapy Simulation Mode
- ✅ Session creation and management
- ✅ Virtual participant generation
- ✅ Group discussion facilitation
- ✅ Group exercises (breathing, mindfulness)
- ✅ Group dynamics simulation
- ✅ Peer support and validation
- ✅ Group learning and reflection
- ✅ Goal setting facilitation

#### UC39: Community Support Circles
- ✅ Circle creation (public/private)
- ✅ Circle membership management
- ✅ Post creation and interaction
- ✅ Comment and like functionality
- ✅ Circle discovery
- ✅ Invitation system for private circles
- ✅ Member count and post count tracking

#### UC40: Religious Support
- ✅ Multi-religion support (7 religions + Other)
- ✅ Religious preference management
- ✅ Faith-based guidance (religion-specific)
- ✅ Religious resources (scripture, prayer, meditation, etc.)
- ✅ Prayer request submission and tracking
- ✅ Daily reflections (religion-specific)
- ✅ Inspirational quotes (religion-specific)

### Navigation Access

All three use cases are accessible from:
**Main Dashboard → Support Tools → [Use Case Card]**

- Group Therapy Simulation Mode
- Community Support Circles
- Religious Support

### Test Coverage Summary

- **Total Test Files:** 9 files
- **Total Test Cases:** 73 tests
- **Unit Tests:** 39 tests (53%)
- **Integration Tests:** 9 tests (12%)
- **UAT Tests:** 25 tests (34%)

All use cases have comprehensive test coverage across all three test types (Unit, Integration, UAT).







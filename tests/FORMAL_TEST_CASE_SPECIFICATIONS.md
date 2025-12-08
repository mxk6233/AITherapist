# Formal Test Case Specifications

**Document Version**: v1.2  
**Last Updated**: Sprint 4

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

### TC-UC10-01: Create User Profile

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-01 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-01: System MUST allow users to create a profile with name, email, and optional date of birth |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | User is not logged in, no existing profile for userId "user123" |
| **Test Data** | userId: "user123", name: "John Doe", email: "john@example.com" |
| **Steps** | 1. Call `useCase.createProfile(userId, name, email)`<br>2. Verify profile creation |
| **Expected Results** | Profile object returned with userId="user123", name="John Doe", email="john@example.com", level=1, totalXP=0 |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-02: Update User Profile

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-02 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-02: System MUST allow users to update profile information |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Profile exists for userId "user123" with name "John Doe" |
| **Test Data** | userId: "user123", updatedName: "John Smith" |
| **Steps** | 1. Create profile with name "John Doe"<br>2. Call `useCase.updateProfile(userId, name=updatedName)`<br>3. Verify profile update |
| **Expected Results** | Updated profile returned with name="John Smith", updatedAt timestamp changed |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-03: Create Wellness Goal

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-03 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-03: System MUST allow users to create wellness goals with title, description, and category |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Profile exists for userId "user123" |
| **Test Data** | userId: "user123", title: "Daily Meditation", description: "Practice 10 minutes daily", category: GoalCategory.MINDFULNESS |
| **Steps** | 1. Create profile<br>2. Call `useCase.createWellnessGoal(userId, title, description, category)`<br>3. Verify goal creation |
| **Expected Results** | WellnessGoal object returned with title="Daily Meditation", progress=0, isCompleted=false |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-04: Track Goal Progress

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-04 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-04: System MUST track goal progress (0-100%) |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Profile and goal exist for userId "user123" |
| **Test Data** | userId: "user123", goalId: "goal-1", progress: 50 |
| **Steps** | 1. Create profile and goal<br>2. Call `useCase.updateGoalProgress(userId, goalId, progress)`<br>3. Verify progress update |
| **Expected Results** | Updated goal returned with progress=50, progress value between 0-100 |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-05: Earn XP

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-05 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-05: System MUST award XP for user activities |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Profile exists for userId "user123" with initialXP=1000 |
| **Test Data** | userId: "user123", xpAmount: 50, description: "Activity completion" |
| **Steps** | 1. Create profile<br>2. Call `useCase.addXP(userId, xpAmount, description)`<br>3. Retrieve profile and verify XP increase |
| **Expected Results** | Profile totalXP increased by 50, XP transaction recorded |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC10-06: Configure Preferences

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC10-06 |
| **Requirement / Use Case** | UC-10: Manage User Profile |
| **Acceptance Criterion(s) Covered** | AC-UC10-06: System MUST allow users to configure app preferences |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Profile exists for userId "user123" |
| **Test Data** | userId: "user123", preferences: UserPreferences(theme="dark", notifications=true, language="en") |
| **Steps** | 1. Create profile<br>2. Call `useCase.updatePreferences(userId, preferences)`<br>3. Verify preferences saved |
| **Expected Results** | Updated profile returned with preferences.theme="dark", preferences.notifications=true |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC16: Access Educational Resources

### TC-UC16-01: Browse Educational Resources

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-01 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-01: System MUST provide categorized educational content |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Educational resources exist in Firebase or fallback data |
| **Test Data** | category: null (all resources), userId: null, format: ContentFormat.ALL |
| **Steps** | 1. Call `useCase.getEducationalResources()`<br>2. Verify resources returned |
| **Expected Results** | List of EducationalResource objects returned, resources organized by category, multiple categories visible |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-02: Search Educational Resources

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-02 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-02: System MUST enable search across resource titles, descriptions, and tags |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Educational resources exist with "anxiety" in title or tags |
| **Test Data** | query: "anxiety", userId: null |
| **Steps** | 1. Call `useCase.searchEducationalResources(query)`<br>2. Verify search results |
| **Expected Results** | List of EducationalResource objects matching "anxiety" in title or tags, results sorted by relevance |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-03: Filter Resources by Format

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-03 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-03: System MUST filter resources by content format (TEXT, VIDEO, AUDIO) |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Educational resources exist with different formats |
| **Test Data** | format: ContentFormat.VIDEO |
| **Steps** | 1. Call `useCase.getEducationalResources(format=ContentFormat.VIDEO)`<br>2. Verify filtered results |
| **Expected Results** | List of EducationalResource objects with format=VIDEO only, all results match filter |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-04: Track Learning Progress

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-04 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-04: System MUST track learning progress (0-100%) for each resource |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User profile exists, resource exists |
| **Test Data** | userId: "user123", resourceId: "resource-1", progress: 75 |
| **Steps** | 1. Call `useCase.trackLearningProgress(resourceId, userId, progress)`<br>2. Verify progress saved |
| **Expected Results** | LearningProgress object returned with progress=75, progress value between 0-100 |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-05: Mark Resource as Completed

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-05 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-05: System MUST mark resources as completed when progress reaches 100% |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User profile exists, resource exists |
| **Test Data** | userId: "user123", resourceId: "resource-1" |
| **Steps** | 1. Call `useCase.markResourceAsCompleted(resourceId, userId)`<br>2. Verify completion |
| **Expected Results** | LearningProgress object returned with progress=100, completedAt timestamp set |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC16-06: Get Learning History

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC16-06 |
| **Requirement / Use Case** | UC-16: Access Educational Resources |
| **Acceptance Criterion(s) Covered** | AC-UC16-06: System MUST retrieve user's learning history |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User has accessed multiple resources, progress tracked |
| **Test Data** | userId: "user123" |
| **Steps** | 1. Track progress for multiple resources<br>2. Call `useCase.getLearningHistory(userId)`<br>3. Verify history returned |
| **Expected Results** | List of LearningProgress objects returned, ordered chronologically, includes completed and in-progress resources |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC25: Facilitate User Support

### TC-UC25-01: Submit Support Ticket

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC25-01 |
| **Requirement / Use Case** | UC-25: Facilitate User Support |
| **Acceptance Criterion(s) Covered** | AC-UC25-01: System MUST allow users to create support tickets with subject, description, category, and priority |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | User is logged in |
| **Test Data** | userId: "user123", subject: "App not working", description: "The app crashes when I try to log my mood", category: SupportCategory.TECHNICAL, priority: SupportPriority.HIGH |
| **Steps** | 1. Call `useCase.createSupportTicket(userId, subject, description, category, priority)`<br>2. Verify ticket creation |
| **Expected Results** | SupportTicket object returned with subject="App not working", status=TicketStatus.OPEN, category=TECHNICAL, priority=HIGH |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC25-02: Search FAQs

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC25-02 |
| **Requirement / Use Case** | UC-25: Facilitate User Support |
| **Acceptance Criterion(s) Covered** | AC-UC25-02: System MUST provide searchable FAQ entries with keyword matching |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | FAQ entries exist in system |
| **Test Data** | query: "mood logging" |
| **Steps** | 1. Call `useCase.getFAQEntries(query)`<br>2. Verify search results |
| **Expected Results** | List of FAQEntry objects matching "mood logging" in question or answer, results relevant to query |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC25-03: Get Contextual Help

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC25-03 |
| **Requirement / Use Case** | UC-25: Facilitate User Support |
| **Acceptance Criterion(s) Covered** | AC-UC25-03: System MUST provide contextual help based on current screen/feature |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Contextual help content exists for "mood_logging" context |
| **Test Data** | context: "mood_logging" |
| **Steps** | 1. Call `useCase.getContextualHelp(context)`<br>2. Verify help content returned |
| **Expected Results** | HelpContent object returned with context="mood_logging", content and links provided |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC25-04: Submit Feedback

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC25-04 |
| **Requirement / Use Case** | UC-25: Facilitate User Support |
| **Acceptance Criterion(s) Covered** | AC-UC25-04: System MUST accept user feedback with type, message, and rating (1-5) |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | User is logged in |
| **Test Data** | userId: "user123", feedbackType: FeedbackType.FEATURE, message: "I would like dark mode", rating: 4 |
| **Steps** | 1. Call `useCase.submitFeedback(userId, feedbackType, message, rating)`<br>2. Verify feedback submission |
| **Expected Results** | FeedbackSubmission object returned with message="I would like dark mode", rating=4, status=FeedbackStatus.RECEIVED |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC28: Therapist Portal Integration

### TC-UC28-01: Assign Patient to Therapist

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC28-01 |
| **Requirement / Use Case** | UC-28: Therapist Portal Integration |
| **Acceptance Criterion(s) Covered** | AC-UC28-01: System MUST allow therapists to assign patients to themselves |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Therapist and patient accounts exist, patient not already assigned |
| **Test Data** | therapistId: "therapist-123", patientId: "patient-456" |
| **Steps** | 1. Call `useCase.assignPatientToTherapist(therapistId, patientId)`<br>2. Verify assignment |
| **Expected Results** | Returns true, patient appears in therapist's patient list, assignment recorded |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC28-02: Add Therapist Note

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC28-02 |
| **Requirement / Use Case** | UC-28: Therapist Portal Integration |
| **Acceptance Criterion(s) Covered** | AC-UC28-02: System MUST allow therapists to add notes about patients |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Therapist assigned to patient, therapist has access |
| **Test Data** | therapistId: "therapist-123", patientId: "patient-123", noteContent: "Patient showed improvement", noteType: "Progress Note" |
| **Steps** | 1. Assign patient to therapist<br>2. Call `useCase.addTherapistNote(therapistId, patientId, noteContent, noteType)`<br>3. Verify note creation |
| **Expected Results** | TherapistNote object returned with content="Patient showed improvement", noteType="Progress Note", createdAt timestamp set |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC28-03: Generate Progress Report

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC28-03 |
| **Requirement / Use Case** | UC-28: Therapist Portal Integration |
| **Acceptance Criterion(s) Covered** | AC-UC28-03: System MUST generate patient progress reports with mood data and notes |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Therapist assigned to patient, patient has mood entries and notes |
| **Test Data** | therapistId: "therapist-123", patientId: "patient-123", startDate: Date(7 days ago), endDate: Date(now) |
| **Steps** | 1. Assign patient and add notes<br>2. Call `useCase.generatePatientProgressReport(therapistId, patientId, startDate, endDate)`<br>3. Verify report generation |
| **Expected Results** | PatientProgressReport object returned with averageMood calculated, totalMoodEntries and totalTherapistNotes counted, report period valid |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC31: Social Support Network Integration

### TC-UC31-01: Send Friend Request

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC31-01 |
| **Requirement / Use Case** | UC-31: Social Support Network Integration |
| **Acceptance Criterion(s) Covered** | AC-UC31-01: System MUST allow users to send friend requests |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Two user accounts exist, users are not already friends |
| **Test Data** | fromUserId: "user-123", toUserId: "user-456" |
| **Steps** | 1. Call `useCase.sendFriendRequest(fromUserId, toUserId)`<br>2. Verify request sent |
| **Expected Results** | Returns true, friend request appears in recipient's pending requests, request not sent to self |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC31-02: Accept Friend Request

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC31-02 |
| **Requirement / Use Case** | UC-31: Social Support Network Integration |
| **Acceptance Criterion(s) Covered** | AC-UC31-02: System MUST allow users to accept friend requests |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Friend request exists from user-456 to user-123 |
| **Test Data** | userId: "user-123", fromUserId: "user-456" |
| **Steps** | 1. Send friend request<br>2. Call `useCase.acceptFriendRequest(userId, fromUserId)`<br>3. Verify acceptance |
| **Expected Results** | Returns true, users added to each other's friends list, request removed from pending |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC31-03: Create Support Group

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC31-03 |
| **Requirement / Use Case** | UC-31: Social Support Network Integration |
| **Acceptance Criterion(s) Covered** | AC-UC31-03: System MUST allow users to create support groups |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User account exists |
| **Test Data** | creatorId: "creator-456", name: "Anxiety Support", description: "Support group for anxiety", isPrivate: false |
| **Steps** | 1. Call `useCase.createSupportGroup(creatorId, name, description, isPrivate)`<br>2. Verify group creation |
| **Expected Results** | SupportGroup object returned with name="Anxiety Support", creatorId="creator-456", memberCount=1 (creator) |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC31-04: Share Progress with Friends

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC31-04 |
| **Requirement / Use Case** | UC-31: Social Support Network Integration |
| **Acceptance Criterion(s) Covered** | AC-UC31-04: System MUST allow users to share progress with privacy controls |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User account exists |
| **Test Data** | userId: "user-123", progressMessage: "7-day streak achieved!", shareWithFriends: true |
| **Steps** | 1. Call `useCase.shareProgress(userId, progressMessage, shareWithFriends)`<br>2. Verify progress shared |
| **Expected Results** | SharedProgress object returned with message="7-day streak achieved!", shareWithFriends=true, sharedAt timestamp set |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC31-05: Send Encouragement Message

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC31-05 |
| **Requirement / Use Case** | UC-31: Social Support Network Integration |
| **Acceptance Criterion(s) Covered** | AC-UC31-05: System MUST allow users to send encouragement messages to friends |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Users are friends |
| **Test Data** | fromUserId: "user-123", toUserId: "user-456", message: "You're doing great! Keep it up!" |
| **Steps** | 1. Establish friendship<br>2. Call `useCase.sendEncouragement(fromUserId, toUserId, message)`<br>3. Verify message sent |
| **Expected Results** | EncouragementMessage object returned with message="You're doing great! Keep it up!", message appears in recipient's messages |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC34: Group Therapy Simulation Mode

### TC-UC34-01: Create Group Session

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC34-01 |
| **Requirement / Use Case** | UC-34: Group Therapy Simulation Mode |
| **Acceptance Criterion(s) Covered** | AC-UC34-01: System MUST allow users to create group therapy sessions |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User account exists |
| **Test Data** | sessionName: "Anxiety Management Session", facilitatorId: "facilitator-123", maxParticipants: 10, topic: "Anxiety Management" |
| **Steps** | 1. Call `useCase.createGroupSession(sessionName, facilitatorId, maxParticipants, topic)`<br>2. Verify session creation |
| **Expected Results** | GroupSession object returned with name="Anxiety Management Session", status=SessionStatus.ACTIVE, facilitatorId in participants list |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC34-02: Join Group Session

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC34-02 |
| **Requirement / Use Case** | UC-34: Group Therapy Simulation Mode |
| **Acceptance Criterion(s) Covered** | AC-UC34-02: System MUST allow users to join group sessions |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Group session exists with available spots |
| **Test Data** | sessionId: "session-456", userId: "user-123" |
| **Steps** | 1. Create group session<br>2. Call `useCase.joinGroupSession(sessionId, userId)`<br>3. Verify join success |
| **Expected Results** | Returns true, user added to session participants list, participant count increased |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC34-03: Create Virtual Participants

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC34-03 |
| **Requirement / Use Case** | UC-34: Group Therapy Simulation Mode |
| **Acceptance Criterion(s) Covered** | AC-UC34-03: System MUST generate virtual participants with diverse personalities |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Group session exists |
| **Test Data** | sessionId: "session-456", participantCount: 5 |
| **Steps** | 1. Create group session<br>2. Call `useCase.createVirtualParticipants(sessionId, participantCount)`<br>3. Verify participants created |
| **Expected Results** | List of 5 VirtualParticipant objects returned, participants have diverse personalities (supportive, analytical, empathetic, etc.) |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC34-04: Facilitate Group Discussion

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC34-04 |
| **Requirement / Use Case** | UC-34: Group Therapy Simulation Mode |
| **Acceptance Criterion(s) Covered** | AC-UC34-04: System MUST facilitate group discussions with topic-based prompts |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Group session exists with virtual participants |
| **Test Data** | sessionId: "session-456", topic: "Anxiety Management" |
| **Steps** | 1. Create session and participants<br>2. Call `useCase.facilitateGroupDiscussion(sessionId, topic)`<br>3. Verify discussion prompts |
| **Expected Results** | List of discussion prompts/responses returned, prompts include topic "Anxiety Management", facilitator prompts included |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC34-05: Provide Peer Support

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC34-05 |
| **Requirement / Use Case** | UC-34: Group Therapy Simulation Mode |
| **Acceptance Criterion(s) Covered** | AC-UC34-05: System MUST provide peer support responses from virtual participants |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Group session exists with virtual participants |
| **Test Data** | sessionId: "session-456", supportRequest: "I've been feeling anxious about work" |
| **Steps** | 1. Create session and participants<br>2. Call `useCase.providePeerSupport(sessionId, supportRequest)`<br>3. Verify supportive responses |
| **Expected Results** | List of supportive responses returned, responses are empathetic and validating, responses from multiple virtual participants |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC37: Predictive Burnout Detection

### TC-UC37-01: Assess Burnout Risk

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC37-01 |
| **Requirement / Use Case** | UC-37: Predictive Burnout Detection |
| **Acceptance Criterion(s) Covered** | AC-UC37-01: System MUST assess burnout risk based on mood, activity, stress, and sleep data |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User has mood entries, activity levels, stress indicators, sleep quality data |
| **Test Data** | moodEntries: [MoodEntry(mood=2), MoodEntry(mood=3), MoodEntry(mood=2)], activityLevels: [ActivityLevel(level=0.2f)], stressIndicators: [StressIndicator(level=8.0f)], sleepQuality: [SleepQuality(quality=3.0f)] |
| **Steps** | 1. Call `useCase.assessBurnoutRisk(moodEntries, activityLevels, stressIndicators, sleepQuality)`<br>2. Verify risk assessment |
| **Expected Results** | BurnoutRiskAssessment object returned with riskScore (0-100), riskLevel (LOW/MODERATE/HIGH/CRITICAL), riskFactors identified |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC37-02: Detect Early Warnings

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC37-02 |
| **Requirement / Use Case** | UC-37: Predictive Burnout Detection |
| **Acceptance Criterion(s) Covered** | AC-UC37-02: System MUST detect early warning signs (mood decline, activity decline, stress accumulation, sleep disruption) |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User has 7+ mood entries showing decline pattern |
| **Test Data** | moodEntries: [7 entries with declining mood from 4 to 2], riskFactors: [BurnoutRiskFactor(factorType=MOOD_DECLINE, severity=0.7f)] |
| **Steps** | 1. Create mood entries with decline pattern<br>2. Call `useCase.detectBurnoutWarnings(riskFactors, moodEntries)`<br>3. Verify warnings detected |
| **Expected Results** | List of BurnoutWarning objects returned, warnings include MOOD_DECLINE type, severity MODERATE or HIGH, actionable message |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC37-03: Generate Prevention Recommendations

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC37-03 |
| **Requirement / Use Case** | UC-37: Predictive Burnout Detection |
| **Acceptance Criterion(s) Covered** | AC-UC37-03: System MUST generate personalized prevention recommendations based on risk level and factors |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Risk assessment completed with risk level and factors |
| **Test Data** | riskLevel: BurnoutRiskLevel.HIGH, riskFactors: [BurnoutRiskFactor(factorType=STRESS_ACCUMULATION, severity=0.8f)] |
| **Steps** | 1. Perform risk assessment<br>2. Call `useCase.generatePreventionRecommendations(riskLevel, riskFactors)`<br>3. Verify recommendations |
| **Expected Results** | List of recommendation strings returned, recommendations specific to HIGH risk level, recommendations address stress accumulation factor |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC37-04: Predict Future Burnout Risk

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC37-04 |
| **Requirement / Use Case** | UC-37: Predictive Burnout Detection |
| **Acceptance Criterion(s) Covered** | AC-UC37-04: System MUST predict future burnout risk with trend analysis and confidence scores |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Current burnout risk assessment exists |
| **Test Data** | currentAssessment: BurnoutRiskAssessment(riskScore=65f, riskLevel=MODERATE), daysAhead: 7 |
| **Steps** | 1. Perform current assessment<br>2. Call `useCase.predictFutureBurnoutRisk(currentAssessment, daysAhead)`<br>3. Verify prediction |
| **Expected Results** | BurnoutPrediction object returned with projectedRiskScore (0-100), projectedRiskLevel, trend (INCREASING/DECREASING/STABLE), confidence (0-100) |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC38: Voice Enabled Therapy Sessions

### TC-UC38-01: Start Voice Session

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC38-01 |
| **Requirement / Use Case** | UC-38: Voice Enabled Therapy Sessions |
| **Acceptance Criterion(s) Covered** | AC-UC38-01: System MUST allow users to start voice therapy sessions |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | User is logged in, microphone permissions granted |
| **Test Data** | userId: "user123", language: "en-US" |
| **Steps** | 1. Call `useCase.startVoiceSession(userId, language)`<br>2. Verify session creation |
| **Expected Results** | VoiceSession object returned with status=VoiceSessionStatus.ACTIVE, language="en-US", startedAt timestamp set |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC38-02: Process Voice Input

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC38-02 |
| **Requirement / Use Case** | UC-38: Voice Enabled Therapy Sessions |
| **Acceptance Criterion(s) Covered** | AC-UC38-02: System MUST process voice input and convert to text using speech recognition |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Active voice session exists |
| **Test Data** | sessionId: "voice-123", audioData: ByteArray(1000) |
| **Steps** | 1. Start voice session<br>2. Call `useCase.processVoiceInput(sessionId, audioData)`<br>3. Verify transcription |
| **Expected Results** | VoiceMessageResponse object returned with transcribedText (non-empty), aiResponseText generated, confidence score (0-1) |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC38-03: Generate Voice Response

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC38-03 |
| **Requirement / Use Case** | UC-38: Voice Enabled Therapy Sessions |
| **Acceptance Criterion(s) Covered** | AC-UC38-03: System MUST generate AI therapist voice responses using text-to-speech |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Active voice session exists |
| **Test Data** | sessionId: "voice-123", textInput: "I'm feeling anxious" |
| **Steps** | 1. Start voice session<br>2. Call `useCase.processTextInput(sessionId, textInput)`<br>3. Verify voice response |
| **Expected Results** | VoiceMessageResponse object returned with aiResponseText (non-empty), aiResponseAudio (ByteArray), response natural and contextual |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC38-04: Pause Voice Session

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC38-04 |
| **Requirement / Use Case** | UC-38: Voice Enabled Therapy Sessions |
| **Acceptance Criterion(s) Covered** | AC-UC38-04: System MUST allow users to pause voice sessions |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Active voice session exists |
| **Test Data** | sessionId: "voice-123" |
| **Steps** | 1. Start voice session<br>2. Call `useCase.pauseVoiceSession(sessionId)`<br>3. Verify pause |
| **Expected Results** | Updated VoiceSession object returned with status=VoiceSessionStatus.PAUSED |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC38-05: End Voice Session

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC38-05 |
| **Requirement / Use Case** | UC-38: Voice Enabled Therapy Sessions |
| **Acceptance Criterion(s) Covered** | AC-UC38-05: System MUST allow users to end voice sessions with duration tracking |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Active voice session exists |
| **Test Data** | sessionId: "voice-123" |
| **Steps** | 1. Start voice session<br>2. Wait briefly<br>3. Call `useCase.endVoiceSession(sessionId)`<br>4. Verify session end |
| **Expected Results** | Updated VoiceSession object returned with status=VoiceSessionStatus.COMPLETED, duration > 0, endedAt timestamp set |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## UC41: Add Greedy Algorithm

### TC-UC41-01: Select Optimal Strategies

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC41-01 |
| **Requirement / Use Case** | UC-41: Add Greedy Algorithm |
| **Acceptance Criterion(s) Covered** | AC-UC41-01: System MUST select optimal coping strategies using greedy algorithm that maximizes benefit within constraints |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Coping exercises available, user constraints defined |
| **Test Data** | availableExercises: [CopingExercise(duration=5, effectiveness=8.0), CopingExercise(duration=10, effectiveness=7.0)], constraints: UserConstraints(availableTimeMinutes=30, currentEnergyLevel=5, currentMood=3, stressLevel=6) |
| **Steps** | 1. Call `useCase.selectOptimalStrategies(availableExercises, constraints)`<br>2. Verify optimal selection |
| **Expected Results** | List of CopingExercise objects returned, selected strategies fit within time and energy constraints, strategies maximize effectiveness-to-effort ratio |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC41-02: Get Top N Recommendations

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC41-02 |
| **Requirement / Use Case** | UC-41: Add Greedy Algorithm |
| **Acceptance Criterion(s) Covered** | AC-UC41-02: System MUST provide top N recommendations based on greedy scoring |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / Integration / UAT |
| **Preconditions** | Coping exercises available, user constraints defined |
| **Test Data** | availableExercises: [4 exercises with effectiveness 10, 9, 8, 7], constraints: UserConstraints(...), topN: 3 |
| **Steps** | 1. Call `useCase.getTopRecommendations(availableExercises, constraints, topN)`<br>2. Verify top N returned |
| **Expected Results** | List of exactly 3 CopingExercise objects returned, sorted by effectiveness (descending), highest benefit strategies selected |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC41-03: Calculate Total Benefit

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC41-03 |
| **Requirement / Use Case** | UC-41: Add Greedy Algorithm |
| **Acceptance Criterion(s) Covered** | AC-UC41-03: System MUST calculate total expected benefit from selected strategies |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Strategies selected |
| **Test Data** | selectedExercises: [CopingExercise(effectiveness=8.0), CopingExercise(effectiveness=7.0)] |
| **Steps** | 1. Select strategies<br>2. Call `useCase.calculateTotalBenefit(selectedExercises)`<br>3. Verify benefit calculation |
| **Expected Results** | Double value returned, total benefit = sum of effectiveness scores, benefit > 0 |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC41-04: Validate Constraints

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC41-04 |
| **Requirement / Use Case** | UC-41: Add Greedy Algorithm |
| **Acceptance Criterion(s) Covered** | AC-UC41-04: System MUST validate that selected strategies fit within user constraints |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Strategies selected, constraints defined |
| **Test Data** | selectedExercises: [CopingExercise(duration=5), CopingExercise(duration=10)], constraints: UserConstraints(availableTimeMinutes=15, currentEnergyLevel=5) |
| **Steps** | 1. Select strategies<br>2. Call `useCase.validateConstraints(selectedExercises, constraints)`<br>3. Verify validation |
| **Expected Results** | Returns true if total time <= availableTimeMinutes and total energy <= currentEnergyLevel, false otherwise |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

### TC-UC41-05: Explain Selection

| Field | Description |
|-------|-------------|
| **Test Case ID** | TC-UC41-05 |
| **Requirement / Use Case** | UC-41: Add Greedy Algorithm |
| **Acceptance Criterion(s) Covered** | AC-UC41-05: System MUST explain why strategies were selected |
| **Version** | v1.2 (Updated for Sprint 4) |
| **Date / Timestamp** | Sprint 4 |
| **Test Type** | Unit / UAT |
| **Preconditions** | Strategies selected, constraints defined |
| **Test Data** | selectedExercises: [CopingExercise(name="Deep Breathing")], constraints: UserConstraints(...) |
| **Steps** | 1. Select strategies<br>2. Call `useCase.explainSelection(selectedExercises, constraints)`<br>3. Verify explanation |
| **Expected Results** | String returned with explanation text, explanation mentions benefit maximization and constraint respect, explanation is clear and understandable |
| **Actual Results** | Pass |
| **Status** | Pass |
| **Related Commit(s)** | N/A |
| **Notes / Bugs Found** | None |

---

## Summary

**Total Test Cases**: 35  
**Test Types Distribution**:
- Unit Tests: 35
- Integration Tests: 15
- UAT Tests: 35

**Status Summary**:
- Pass: 35
- Fail: 0
- Not Executed: 0

**Coverage**:
- UC10: 6 test cases
- UC16: 6 test cases
- UC25: 4 test cases
- UC28: 3 test cases
- UC31: 5 test cases
- UC34: 5 test cases
- UC37: 4 test cases
- UC38: 5 test cases
- UC41: 5 test cases


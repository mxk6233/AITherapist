# Acceptance Criteria for Last 5 Use Cases

**Document Version**: 1.0  
**Status**: Complete Acceptance Criteria for UC16, UC25, UC34, UC37, UC38

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
**AC-UC16-11**: System MUST fetch resources from Firebase Firestore when available  
**AC-UC16-12**: System MUST fallback to hardcoded data if Firebase is unavailable or empty  
**AC-UC16-13**: System MUST allow users to open resources via URL (watch/listen/read)  
**AC-UC16-14**: System MUST display resource metadata (duration, difficulty, relevance score, tags)

---

## UC25: Facilitate User Support

### Use Case Goal
Provide users with comprehensive support resources including FAQ, help content, support ticket creation, and feedback submission to ensure users can get help when needed.

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
**AC-UC25-10**: System MUST track ticket status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)  
**AC-UC25-11**: System MUST provide timestamp tracking for ticket creation and updates

---

## UC34: Group Therapy Simulation Mode

### Use Case Goal
Provide users with simulated group therapy sessions using AI-powered virtual participants to create a supportive group environment for practice and learning.

### Acceptance Criteria

**AC-UC34-01**: System MUST create group therapy sessions with name, facilitator, max participants, and optional topic  
**AC-UC34-02**: System MUST validate session creation input and reject empty session names or invalid max participants  
**AC-UC34-03**: System MUST allow users to join group sessions (up to max participants limit)  
**AC-UC34-04**: System MUST allow users to leave group sessions (except facilitator)  
**AC-UC34-05**: System MUST prevent facilitator from leaving the session  
**AC-UC34-06**: System MUST generate virtual participants with diverse personalities and backgrounds  
**AC-UC34-07**: System MUST create specified number of virtual participants (1-10)  
**AC-UC34-08**: System MUST simulate realistic group dynamics (participation level, cohesion, engagement)  
**AC-UC34-09**: System MUST provide peer support responses from virtual participants  
**AC-UC34-10**: System MUST facilitate group discussions on specified topics  
**AC-UC34-11**: System MUST track session status (ACTIVE, PAUSED, COMPLETED, CANCELLED)  
**AC-UC34-12**: System MUST retrieve active group sessions for a user  
**AC-UC34-13**: System MUST retrieve group session history for a user  
**AC-UC34-14**: System MUST track session creation and update timestamps  
**AC-UC34-15**: System MUST validate session operations and reject invalid requests (non-existent sessions, etc.)

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
**AC-UC37-10**: System MUST analyze mood trends over time to detect declining patterns  
**AC-UC37-11**: System MUST analyze activity levels to detect significant declines  
**AC-UC37-12**: System MUST analyze stress indicators to detect accumulation patterns  
**AC-UC37-13**: System MUST analyze sleep quality to detect disruption patterns  
**AC-UC37-14**: System MUST provide assessment timestamp for tracking assessment history

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
**AC-UC38-12**: System MUST track session status (ACTIVE, PAUSED, COMPLETED)  
**AC-UC38-13**: System MUST store voice session messages (user and AI)  
**AC-UC38-14**: System MUST provide transcription confidence scores (0-1) for voice input  
**AC-UC38-15**: System MUST track session start and end timestamps

---

## Summary

### Total Acceptance Criteria by Use Case

| Use Case | Number of Acceptance Criteria | Use Case Name |
|----------|------------------------------|---------------|
| UC16 | 14 | Access Educational Resources |
| UC25 | 11 | Facilitate User Support |
| UC34 | 15 | Group Therapy Simulation Mode |
| UC37 | 14 | Predictive Burnout Detection |
| UC38 | 15 | Voice Enabled Therapy Sessions |
| **Total** | **69** | **5 Use Cases** |

### Acceptance Criteria Categories

**Functional Requirements:**
- Core functionality (creation, retrieval, updates)
- Data validation and error handling
- Search and filtering capabilities
- Personalization and recommendations

**Non-Functional Requirements:**
- Performance (response times, data loading)
- Reliability (fallback mechanisms, error handling)
- Usability (clear error messages, helpful suggestions)
- Data integrity (validation, timestamps, status tracking)

**Integration Requirements:**
- Firebase integration (UC16)
- AI service integration (UC34, UC38)
- Session management (UC34, UC38)
- Data persistence (all use cases)

---

**Document Version**: 1.0  
**Status**: Complete


# Test Cases for New Use Cases (UC10, UC28, UC31, UC41)

## UC10: Manage User Profile

| ID | Type | Description | Expected Result |
|----|------|-------------|-----------------|
| TC-UC10-01 | Unit | System creates user profile with valid information correctly | Profile created with unique ID, user ID, name, and email stored correctly |
| TC-UC10-02 | Unit | System validates profile creation input | Invalid input (empty user ID, empty name, invalid email) rejected with errors |
| TC-UC10-03 | Unit | System updates user profile information | Profile updated with new name and email, updatedAt timestamp set |
| TC-UC10-04 | Unit | System retrieves user profile by ID | Profile retrieved correctly, matches created profile data |
| TC-UC10-05 | Unit | System creates wellness goals | Goal created with unique ID, title, description, category stored, progress at 0% |
| TC-UC10-06 | Unit | System updates goal progress | Progress updated correctly, goal marked as completed when progress reaches 100% |
| TC-UC10-07 | Unit | System retrieves user's wellness goals | All goals retrieved, filtering by completion status works correctly |
| TC-UC10-08 | Unit | System updates user preferences | Preferences updated (theme, notifications, language, privacy level) correctly |
| TC-UC10-09 | Unit | System adds XP and calculates level | XP added correctly, level calculated based on total XP (level = XP/1000 + 1) |
| TC-UC10-10 | Unit | System updates user streak | Current streak incremented, longest streak updated, streak reset works correctly |
| TC-UC10-11 | Integration | Profile persisted through database integration | Profile saved, persisted, retrievable after creation |
| TC-UC10-12 | Integration | Goals tracked through analytics integration | Goals tracked, progress monitored, analytics data collected |
| TC-UC10-13 | Integration | Preferences synchronized through settings integration | Preferences updated, settings synchronized, changes reflected in system |

---

## UC28: Therapist Portal Integration

| ID | Type | Description | Expected Result |
|----|------|-------------|-----------------|
| TC-UC28-01 | Unit | System assigns patient to therapist | Patient assigned successfully, therapist-patient relationship established |
| TC-UC28-02 | Unit | System prevents duplicate patient assignments | Duplicate assignment prevented, returns false for already assigned patient |
| TC-UC28-03 | Unit | System retrieves therapist's patients | All patients assigned to therapist retrieved correctly |
| TC-UC28-04 | Unit | System unassigns patient from therapist | Patient unassigned successfully, relationship removed from both sides |
| TC-UC28-05 | Unit | System gets therapist assigned to patient | Therapist ID returned for assigned patient, null for unassigned patient |
| TC-UC28-06 | Unit | System adds therapist notes | Note created with unique ID, content stored, therapist-patient access verified |
| TC-UC28-07 | Unit | System retrieves therapist notes | All notes for patient retrieved, filtered by therapist ID |
| TC-UC28-08 | Unit | System generates patient progress report | Report generated with patient ID, average mood, note count, date range |
| TC-UC28-09 | Integration | Patient data accessed through portal integration | Patient data accessible, therapist can view assigned patients |
| TC-UC28-10 | Integration | Notes persisted through storage integration | Notes saved, persisted, retrievable after creation |
| TC-UC28-11 | Integration | Reports generated through analytics integration | Reports generated, analytics data included, insights available |

---

## UC31: Social Support Network Integration

| ID | Type | Description | Expected Result |
|----|------|-------------|-----------------|
| TC-UC31-01 | Unit | System sends friend requests | Friend request sent successfully, appears in recipient's pending requests |
| TC-UC31-02 | Unit | System prevents self-friend requests | Self-friend request rejected, cannot send request to yourself |
| TC-UC31-03 | Unit | System prevents duplicate friend requests | Duplicate request prevented, returns false for existing pending request |
| TC-UC31-04 | Unit | System accepts friend requests | Request accepted, bidirectional friendship established |
| TC-UC31-05 | Unit | System removes friends | Friend removed successfully, friendship removed from both users |
| TC-UC31-06 | Unit | System retrieves user's friends | List of friend user IDs returned correctly |
| TC-UC31-07 | Unit | System retrieves pending friend requests | List of user IDs who sent friend requests returned |
| TC-UC31-08 | Unit | System creates support groups | Group created with unique ID, name, description, creator as member |
| TC-UC31-09 | Unit | System joins support groups | User added to group, member count updated, group appears in user's groups |
| TC-UC31-10 | Unit | System leaves support groups | User removed from group, member count updated |
| TC-UC31-11 | Unit | System retrieves user's support groups | List of SupportGroup objects user belongs to returned |
| TC-UC31-12 | Unit | System shares progress with friends | Progress shared with unique ID, message stored, sharing preferences respected |
| TC-UC31-13 | Unit | System sends encouragement messages | Encouragement message sent, stored, retrievable by recipient |
| TC-UC31-14 | Unit | System retrieves encouragement messages | List of encouragement messages for user returned |
| TC-UC31-15 | Integration | Friends managed through user system integration | Friend relationships managed, user system integrated correctly |
| TC-UC31-16 | Integration | Groups managed through community integration | Support groups managed, community features integrated |
| TC-UC31-17 | Integration | Sharing controlled through privacy integration | Progress sharing respects privacy settings, privacy controls enforced |

---

## UC41: Add Greedy Algorithm

| ID | Type | Description | Expected Result |
|----|------|-------------|-----------------|
| TC-UC41-01 | Unit | System selects optimal strategies using greedy algorithm | Strategies selected based on effectiveness-to-effort ratio, fit within constraints |
| TC-UC41-02 | Unit | System validates constraints for selected strategies | Selected strategies fit within time and energy constraints |
| TC-UC41-03 | Unit | System validates input parameters | Invalid input (empty exercises, invalid constraints) rejected with errors |
| TC-UC41-04 | Unit | System provides top N recommendations | Top N highest-scoring exercises returned, sorted by greedy score |
| TC-UC41-05 | Unit | System calculates total expected benefit | Total benefit calculated as sum of effectiveness scores |
| TC-UC41-06 | Unit | System calculates total time investment | Total time calculated as sum of exercise durations |
| TC-UC41-07 | Unit | System provides selection explanation | Explanation provided with selection rationale, constraints, and statistics |
| TC-UC41-08 | Unit | System provides algorithm statistics | Statistics include evaluated exercises, selected exercises, benefit, time, utilization |
| TC-UC41-09 | Integration | Algorithm selects exercises through exercise system integration | Exercises selected from available pool, exercise system integrated |
| TC-UC41-10 | Integration | Recommendations personalized through user context integration | Recommendations consider user mood, stress level, energy, time constraints |
| TC-UC41-11 | Integration | Statistics tracked through analytics integration | Algorithm statistics tracked, analytics data collected, insights generated |

---

## Summary

| Use Case | Total Tests | Unit Tests | Integration Tests |
|----------|-------------|------------|-------------------|
| UC10: Manage User Profile | 13 | 10 | 3 |
| UC28: Therapist Portal Integration | 11 | 8 | 3 |
| UC31: Social Support Network Integration | 17 | 14 | 3 |
| UC41: Add Greedy Algorithm | 11 | 8 | 3 |
| **TOTAL** | **52** | **40** | **12** |



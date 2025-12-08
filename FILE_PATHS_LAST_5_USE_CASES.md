# File Paths for Last 5 Use Cases

**Document Version**: 1.0  

---

## Use Case Implementation Files

### UC16: Access Educational Resources
- **Use Case**: `app/src/main/java/com/serenityai/usecases/EducationalResourcesUseCase.kt`
- **UI Screen**: `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt` (EducationalResourcesScreen function)

### UC25: Facilitate User Support
- **Use Case**: `app/src/main/java/com/serenityai/usecases/UserSupportUseCase.kt`
- **UI Screen**: `app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt` (UserSupportScreen function)

### UC34: Group Therapy Simulation Mode
- **Use Case**: `app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`
- **UI Screen**: (To be implemented)

### UC37: Predictive Burnout Detection
- **Use Case**: `app/src/main/java/com/serenityai/usecases/PredictiveBurnoutDetectionUseCase.kt`
- **UI Screen**: `app/src/main/java/com/serenityai/ui/screens/BurnoutDetectionScreen.kt`

### UC38: Voice Enabled Therapy Sessions
- **Use Case**: `app/src/main/java/com/serenityai/usecases/VoiceEnabledTherapyUseCase.kt`
- **UI Screen**: (To be implemented)

---

## Test Files

### UC16: Access Educational Resources

**Unit Tests:**
- `tests/unit/usecases/uc16_educational_resources/EducationalResourcesUseCaseUnitTests.kt`
- `app/src/test/java/com/serenityai/tests/usecases/uc16_educational_resources/unit/EducationalResourcesUseCaseUnitTests.kt`

**Integration Tests:**
- `tests/integration/usecases/uc16_educational_resources/EducationalResourcesUseCaseIntegrationTests.kt`

**UAT Tests:**
- `tests/uat/usecases/uc16_educational_resources/EducationalResourcesUseCaseUATTests.kt`

### UC25: Facilitate User Support

**Unit Tests:**
- `tests/unit/usecases/uc25_user_support/UserSupportUseCaseUnitTests.kt`

**Integration Tests:**
- `tests/integration/usecases/uc25_user_support/UserSupportUseCaseIntegrationTests.kt`

**UAT Tests:**
- `tests/uat/usecases/uc25_user_support/UserSupportUseCaseUATTests.kt`

### UC34: Group Therapy Simulation Mode

**Unit Tests:**
- `tests/unit/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseUnitTests.kt`

**Integration Tests:**
- `tests/integration/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseIntegrationTests.kt`

**UAT Tests:**
- `tests/uat/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseUATTests.kt`

### UC37: Predictive Burnout Detection

**Unit Tests:**
- `tests/unit/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseUnitTests.kt`

**Integration Tests:**
- `tests/integration/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseIntegrationTests.kt`

**UAT Tests:**
- `tests/uat/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseUATTests.kt`

### UC38: Voice Enabled Therapy Sessions

**Unit Tests:**
- `tests/unit/usecases/uc38_voice_therapy/VoiceEnabledTherapyUseCaseUnitTests.kt`

**Integration Tests:**
- `tests/integration/usecases/uc38_voice_therapy/VoiceEnabledTherapyUseCaseIntegrationTests.kt`

**UAT Tests:**
- `tests/uat/usecases/uc38_voice_therapy/VoiceEnabledTherapyUseCaseUATTests.kt`

---

## Documentation Files

### Acceptance Criteria
- `ACCEPTANCE_CRITERIA_LAST_5_USE_CASES.md` - Complete acceptance criteria for all 5 use cases

### Test Documentation
- `tests/FORMAL_TEST_CASE_SPECIFICATIONS.md` - Formal test case specifications (UC16, UC25, UC37, UC38)
- `tests/COMPLETE_TEST_INVENTORY.md` - Complete test inventory including all 5 use cases
- `tests/NEW_USE_CASES_TEST_RESULTS.md` - Test results for new use cases
- `USE_CASES_VERIFICATION.md` - Use case verification with traceability matrix

### Firebase Integration
- `app/src/main/java/com/serenityai/data/remote/FirebaseSource.kt` - Firebase data source (includes UC16 methods)
- `app/src/main/java/com/serenityai/AITherapistApplication.kt` - Firebase initialization
- `app/google-services.json` - Firebase configuration file
- `firebase_setup/sample_educational_resources.json` - Sample data for UC16
- `firebase_setup/import_to_firebase.py` - Python script to import UC16 data
- `firebase_setup/FIREBASE_SETUP_INSTRUCTIONS.md` - Firebase setup guide
- `firebase_setup/QUICK_SETUP.md` - Quick setup guide
- `firebase_setup/SERVICE_ACCOUNT_SETUP.md` - Service account setup
- `FIREBASE_CONNECTION_SETUP.md` - Connection setup guide
- `FIREBASE_SETUP_COMPLETE.md` - Setup completion summary
- `FIREBASE_CONNECTION_STATUS.md` - Connection status guide
- `FIREBASE_SETUP_VERIFICATION.md` - Setup verification
- `FIREBASE_ADMIN_SDK_SETUP.md` - Admin SDK setup
- `GET_GOOGLE_SERVICES_JSON.md` - How to get google-services.json

---

## Navigation Files

- `app/src/main/java/com/serenityai/navigation/AppNavigation.kt` - Navigation configuration (includes routes for UC16, UC25, UC34, UC37, UC38)
- `app/src/main/java/com/serenityai/navigation/Screen.kt` - Screen definitions

---

## Configuration Files

### Gradle Build Files
- `build.gradle.kts` - Project-level build file (Firebase plugin)
- `app/build.gradle.kts` - App-level build file (Firebase dependencies)
- `gradle/wrapper/gradle-wrapper.properties` - Gradle wrapper version

### Android Configuration
- `app/src/main/AndroidManifest.xml` - Android manifest (Application class, permissions)
- `app/.gitignore` - Git ignore for Firebase files

---

## Data Models

### UC16 Data Models
- Defined in: `app/src/main/java/com/serenityai/usecases/EducationalResourcesUseCase.kt`
  - `EducationalResource` data class
  - `LearningProgress` data class
  - `ContentFormat` enum
  - `DifficultyLevel` enum

### UC25 Data Models
- Defined in: `app/src/main/java/com/serenityai/usecases/UserSupportUseCase.kt`
  - `SupportTicket` data class
  - `FAQEntry` data class
  - `HelpContent` data class
  - `FeedbackSubmission` data class
  - `SupportCategory` enum
  - `SupportPriority` enum
  - `TicketStatus` enum
  - `FeedbackType` enum
  - `FeedbackStatus` enum

### UC34 Data Models
- Defined in: `app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`
  - `GroupSession` data class
  - `VirtualParticipant` data class
  - `SessionStatus` enum

### UC37 Data Models
- Defined in: `app/src/main/java/com/serenityai/usecases/PredictiveBurnoutDetectionUseCase.kt`
  - `BurnoutRiskAssessment` data class
  - `BurnoutPrediction` data class
  - `BurnoutRiskLevel` enum
  - `BurnoutFactorType` enum
  - `WarningType` enum
  - `Trend` enum

### UC38 Data Models
- Defined in: `app/src/main/java/com/serenityai/usecases/VoiceEnabledTherapyUseCase.kt`
  - `VoiceSession` data class
  - `VoiceMessageResponse` data class
  - `ErrorHandlingResponse` data class
  - `SupportedLanguage` data class
  - `VoiceSessionStatus` enum

---

## Quick Reference: Absolute Paths

### Use Cases (Implementation)
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/usecases/EducationalResourcesUseCase.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/usecases/UserSupportUseCase.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/usecases/PredictiveBurnoutDetectionUseCase.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/usecases/VoiceEnabledTherapyUseCase.kt
```

### UI Screens
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/ui/screens/RemainingScreens.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/ui/screens/BurnoutDetectionScreen.kt
```

### Unit Tests
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/unit/usecases/uc16_educational_resources/EducationalResourcesUseCaseUnitTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/unit/usecases/uc25_user_support/UserSupportUseCaseUnitTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/unit/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseUnitTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/unit/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseUnitTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/unit/usecases/uc38_voice_therapy/VoiceEnabledTherapyUseCaseUnitTests.kt
```

### Integration Tests
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/integration/usecases/uc16_educational_resources/EducationalResourcesUseCaseIntegrationTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/integration/usecases/uc25_user_support/UserSupportUseCaseIntegrationTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/integration/usecases/uc34_group_therapy/GroupTherapySimulationModeUseCaseIntegrationTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/integration/usecases/uc37_burnout_detection/PredictiveBurnoutDetectionUseCaseIntegrationTests.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/integration/usecases/uc38_voice_therapy/VoiceEnabledTherapyUseCaseIntegrationTests.kt
```

### Documentation
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/ACCEPTANCE_CRITERIA_LAST_5_USE_CASES.md
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/FORMAL_TEST_CASE_SPECIFICATIONS.md
/Users/mohammadabbaskazmi/Downloads/AITherapist/USE_CASES_VERIFICATION.md
/Users/mohammadabbaskazmi/Downloads/AITherapist/tests/COMPLETE_TEST_INVENTORY.md
```

### Firebase Files
```
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/data/remote/FirebaseSource.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/src/main/java/com/serenityai/AITherapistApplication.kt
/Users/mohammadabbaskazmi/Downloads/AITherapist/app/google-services.json
/Users/mohammadabbaskazmi/Downloads/AITherapist/firebase_setup/sample_educational_resources.json
/Users/mohammadabbaskazmi/Downloads/AITherapist/firebase_setup/import_to_firebase.py
```

---

## File Structure Overview

```
AITherapist/
├── app/
│   ├── google-services.json
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   └── java/com/serenityai/
│       │       ├── AITherapistApplication.kt
│       │       ├── usecases/
│       │       │   ├── EducationalResourcesUseCase.kt (UC16)
│       │       │   ├── UserSupportUseCase.kt (UC25)
│       │       │   ├── GroupTherapySimulationModeUseCase.kt (UC34)
│       │       │   ├── PredictiveBurnoutDetectionUseCase.kt (UC37)
│       │       │   └── VoiceEnabledTherapyUseCase.kt (UC38)
│       │       ├── data/remote/
│       │       │   └── FirebaseSource.kt
│       │       └── ui/screens/
│       │           ├── RemainingScreens.kt (UC16, UC25)
│       │           └── BurnoutDetectionScreen.kt (UC37)
│       └── test/
│           └── java/com/serenityai/tests/usecases/
│               └── uc16_educational_resources/...
├── tests/
│   ├── unit/usecases/
│   │   ├── uc16_educational_resources/
│   │   ├── uc25_user_support/
│   │   ├── uc34_group_therapy/
│   │   ├── uc37_burnout_detection/
│   │   └── uc38_voice_therapy/
│   ├── integration/usecases/
│   │   ├── uc16_educational_resources/
│   │   ├── uc25_user_support/
│   │   ├── uc34_group_therapy/
│   │   ├── uc37_burnout_detection/
│   │   └── uc38_voice_therapy/
│   ├── uat/usecases/
│   │   ├── uc16_educational_resources/
│   │   ├── uc25_user_support/
│   │   ├── uc34_group_therapy/
│   │   ├── uc37_burnout_detection/
│   │   └── uc38_voice_therapy/
│   ├── FORMAL_TEST_CASE_SPECIFICATIONS.md
│   ├── COMPLETE_TEST_INVENTORY.md
│   └── NEW_USE_CASES_TEST_RESULTS.md
├── firebase_setup/
│   ├── sample_educational_resources.json
│   ├── import_to_firebase.py
│   ├── FIREBASE_SETUP_INSTRUCTIONS.md
│   └── QUICK_SETUP.md
├── ACCEPTANCE_CRITERIA_LAST_5_USE_CASES.md
├── USE_CASES_VERIFICATION.md
└── FILE_PATHS_LAST_5_USE_CASES.md (this file)
```

---

**Document Version**: 1.0  


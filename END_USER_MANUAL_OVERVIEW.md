# Software End-User Manual (Concise Export)

This is a concise, PDF-ready export of the end-user manual. It summarizes setup, deployment, features, and step-by-step usage. Refer to the full `SOFTWARE_END_USER_MANUAL.md` for exhaustive detail and additional screenshots.

---

## 1) Product Overview
- **App**: AI Therapist (Android, Jetpack Compose, Kotlin)
- **Purpose**: 24/7 AI-assisted mental health support with mood tracking, analytics/forecasting, coping tools, educational resources, relapse/burnout prevention, personalization, accessibility, and security controls.

### Tools, Frameworks, Services
- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17+
- **Android SDK**: API 24–36
- **Gradle**: 8.10.1 (Kotlin DSL)
- **Kotlin**: 2.0.21
- **Jetpack Compose**: 2024.09.00, Material 3
- **Firebase**: Authentication, Firestore, Analytics
- **OpenAI API key**: for AI chat

### Environment Configuration (Setup)
1) **Clone**: `git clone <repo>; cd AITherapist`
2) **Android SDK/Tools**: Install via Android Studio (API 24–36).
3) **JDK 17**: Ensure `java -version` shows 17+.
4) **Firebase**:
   - Create project in Firebase Console.
   - Download `google-services.json` → place in `app/`.
   - Enable Email/Password Auth; create Firestore DB; enable Analytics.
5) **OpenAI API Key**:
   - Obtain key; add to your local config (e.g., environment variable or local properties; do not commit).
6) **Build/Run**:
   - `./gradlew assembleDebug` or run from Android Studio with an emulator/device API 24+.

> Screenshots: See `SOFTWARE_END_USER_MANUAL.md` for setup visuals.

---

## 2) Application Features (MVP) by Category
- **AI-Powered Therapy**
  - AI Chat Sessions, Chat History (search/filter/export), Crisis Intervention
- **Mood Tracking & Analytics**
  - Daily Mood Logging, Mood Analytics (trends), Mood Forecasting (7/14/30/90 days)
- **Risk & Prevention**
  - Burnout Detection/Assessment, Relapse Prevention (indicators, early warnings)
- **Support Tools**
  - Coping Exercises (personalized recommendations), Educational Resources, AI Journaling Prompts, Community Support Circles (UC39)
- **Wellness Activities**
  - Guided Breathing & Meditation (UC27), Daily Affirmations
- **Settings & Personalization**
  - App Preferences (UC13), Accessibility (UC17), Notifications (UC18), Security Protocols (UC23), Personalization (UC24)
- **Advanced/Algorithmic**
  - Greedy Coping Strategy Selector (UC41) to optimize exercise plans

---

## 3) Feature Instructions (How to Execute Use Cases)

> Screenshots: Use the provided app screens; representative shots are in the repo (e.g., mood analytics, forecasting, burnout risk, relapse prevention, educational resources). Match each step with the relevant screen.

### AI Chat & History (UC1, UC2, UC6, UC38)
1) Open **AI Chat** from dashboard.
2) Type or speak your message; send.
3) To view past sessions: go to **Chat History**; search/filter by mood/date; tap a session to view details.
4) Crisis phrases trigger crisis support automatically.

### Mood Logging & Analytics (UC3, UC9)
1) Open **Log Daily Mood**; select mood/energy/stress/anxiety/sleep; save.
2) Open **Mood Analytics** to view trends and narrative insights.

### Mood Forecasting (UC26)
1) Open **Mood Forecasting**.
2) Select horizon (7/14/30/90 days); review predicted average/range.

### Burnout Detection (UC37) & Burnout Risk Assessment Screen
1) Open **Burnout Detection**.
2) Review current risk level; tap **Take Burnout Assessment** to answer prompts.
3) View prevention tips and recent patterns chart.

### Relapse Prevention (UC35)
1) Open **Relapse Prevention**.
2) Review indicators and impact levels.
3) Check Early Warning System recommendations; follow suggested actions.

### Coping Exercises (UC8) & Greedy Algorithm (UC41)
1) Open **Coping Exercises**.
2) View personalized recommendations (uses Greedy selector under the hood).
3) Filter by mood/time/difficulty; start an exercise; mark completion.

### Guided Breathing & Meditation (UC27)
1) Open **Guided Breathing**.
2) Pick a pattern (4-7-8, Box, etc.) or meditation type; adjust duration/voice/sound; start session.

### Educational Resources (UC25)
1) Open **Educational Resources**.
2) Search or filter (All/Text/Video/Audio); select a resource; play/read.

### Journaling Prompts (UC32)
1) Open **Journaling Prompts**.
2) Pick AI-generated or mood-based prompts; add custom prompt if desired; start writing.

### Community Support Circles (UC39)
1) Open **Community Support**.
2) Browse or create circles; join and participate in discussions (per UC39 tests and flows).

### Notifications (UC18)
1) Open **Notifications** in Settings.
2) Enable/disable reminders (mood check-ins, exercises, insights).

### Accessibility (UC17)
1) Open **Accessibility** settings.
2) Enable screen reader labels, high contrast, large text, reduced motion, keyboard/focus options.

### Personalization (UC24) & Preferences (UC13)
1) Open **Personalization** or **App Preferences**.
2) Choose theme (Light/Dark/System), font size, AI personality, content/notification styles, language.

### Security Protocols (UC23)
1) Open **Security** settings.
2) Configure biometric/2FA, session timeout, privacy controls, change password, data protections.

---

## 4) Deployment & Run (Quick Steps)
1) Install tools (Android Studio, SDKs, JDK 17).
2) Add `google-services.json` to `app/`; set OpenAI API key locally.
3) Build: `./gradlew assembleDebug`
4) Run on emulator/device API 24+.
5) (Optional) Tests: `./gradlew test` and `./gradlew jacocoTestReport`

---

## 5) Screenshots
- Use the provided in-app screens (mood analytics, forecasting, burnout risk, relapse prevention, educational resources, guided breathing, dashboards, etc.) to illustrate steps above.
- For PDF export, embed the most recent captures that match the current UI (see repository screenshots or emulator captures).

---

## 6) Support
- Issues: Project issue tracker
- Docs: `SOFTWARE_END_USER_MANUAL.md` (full), `README.md` (quick start), `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` (requirements), `ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md` (algorithm)
- Contact: support@aitherapist.com (placeholder)


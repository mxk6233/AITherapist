# Software Requirements Specification (Concise Export)

This document summarizes the key content from the full `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` in a concise format suitable for PDF export. It covers mission/scope, system overview, functional and non-functional requirements, and the algorithmic component specification with its linkage to use cases.

---

## 1. Document Overview
- **Purpose**: Define what the AI Therapist system will do and how it should perform.
- **Audience**: Product, engineering, QA, compliance, and stakeholders.
- **Scope**: Android mental health support app (API 24+) with AI chat, mood tracking, forecasting, educational resources, coping tools, accessibility, security, and personalization.
- **Out of Scope**: iOS client, web client, clinician-facing portal (future work unless otherwise noted in advanced features).

## 2. Intro / System Overview / Product Scope
- **Mission / Concept of Operations**: Deliver 24/7 accessible, confidential mental health support via AI-assisted interactions, mood analytics, and wellness tools.
- **System Context**: Android app + Firebase backend + OpenAI API; users interact via Jetpack Compose UI; data stored in Firebase Auth/Firestore; optional offline fallbacks.
- **Users & Actors**:
  - **End Users (Patients)**: primary users for chat, mood logging, analytics, coping exercises, resources.
  - **Therapists** (advanced features): monitor progress, notes, reports.
  - **Support Team / Admins**: support tickets, system health, configuration.
- **Constraints**: Android only, Kotlin/Compose, Firebase dependency, network required for most features, HIPAA/GDPR considerations, WCAG AA accessibility.
- **Assumptions**: Periodic internet, device capabilities (mic/storage), user consents, English UI.
- **Product Scope**: AI chat, mood tracking/analytics/forecasting, burnout/relapse prevention, coping exercises, educational resources, accessibility, security, personalization, notifications.
- **(Optional) WBS**: Not provided in source; can be derived from features/use cases list below.

## 3. System Features (Use Cases & User Stories)
Organized by feature areas with use-case IDs (from the full SRS):
- **Authentication & User Management**: UC4 (Registration), UC5, UC7 (Login), UC10 (Profile).
- **AI-Powered Therapy**: UC1, UC2, UC6 (Chat History), UC38.
- **Mood Tracking & Analytics**: UC3, UC9 (Mood Analytics), UC26 (Forecasting), UC35 (Relapse Prevention), UC37.
- **Support Tools**: UC8 (Coping Exercises), UC16, UC25, UC32 (Journaling Prompts), UC34, UC39 (Community Support), UC40.
- **Wellness Activities**: UC14, UC27 (Guided Breathing & Meditation).
- **Settings & Personalization**: UC13 (Preferences), UC17 (Accessibility), UC18 (Notifications), UC22, UC23 (Security Protocols), UC24 (Personalize Experience).
- **Advanced Features**: UC28 (Therapist Portal Integration), UC31 (Social Support Network Integration), UC41 (Greedy Algorithm).

### Users / Actions (examples)
- End Users: register/login, chat, log mood, view analytics/forecast, run burnout/relapse checks, follow coping exercises, browse resources, set preferences/accessibility/security.
- Therapists (advanced): view progress, add notes, reports.
- Support/Admin: monitor health, manage configs, respond to tickets.

## 4. Functional Requirements (FRs) with Use-Case Mapping
Each FR in the full SRS is named `FR-UCx-yy` and includes description + acceptance criteria. Examples:
- **FR-UC4-01 (UC4 Registration)**: Validate email, enforce password strength, create account (Firebase Auth), store profile (Firestore), redirect to onboarding.
- **FR-UC7-01 (UC7 Login)**: Authenticate via Firebase, create session, redirect to dashboard.
- **FR-UC6-xx (Chat History)**: Search/filter chats, view session stats.
- **FR-UC9-xx (Mood Analytics)**: Compute trends, display insights.
- **FR-UC26-xx (Forecasting)**: Select horizon (7/14/30/90 days), show predicted averages/ranges.
- **FR-UC35-xx (Relapse Prevention)**: Detect indicators, surface alerts, suggest actions.
- **FR-UC27-xx (Guided Breathing & Meditation)**: Provide exercise catalog, track sessions, recommend per need.
- **FR-UC24-xx (Personalization)**: Themes, font sizes, AI personality, content/notification preferences.
- **FR-UC17-xx (Accessibility)**: Screen reader labels, keyboard navigation, high contrast, large text, color contrast (WCAG AA/AAA), motor/cognitive aids.
- **FR-UC23-xx (Security Protocols)**: Auth settings, session timeout, privacy controls.
- **FR-UC41-01 (Greedy Algorithm)**: Provide optimized coping plan per constraints.

> For the exhaustive FR list with acceptance criteria, see `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` Section 4.

## 5. Non-Functional Requirements (NFRs)
From the full SRS Section 5:
- **Performance**: Responsive UX; constraints per device capability.
- **Reliability/Availability**: Target high availability; data integrity.
- **Security/Privacy**: HIPAA/GDPR alignment, encryption, secure auth, session management.
- **Usability/Accessibility**: WCAG AA, screen reader support, text scaling, contrast.
- **Scalability**: Cloud-backed (Firebase) to support growth.
- **Maintainability**: Modular architecture, documented use cases, tests.
- **Internationalization**: English primary; language setting supported in personalization.

## 6. Algorithmic Component Specification (Mapped to Requirements)
- **Component**: Greedy Coping Strategy Selection Algorithm.
- **Use Case**: UC41 - Add Greedy Algorithm.
- **Problem**: Choose a set of coping exercises maximizing therapeutic benefit under time/energy/mood/stress constraints; multi-constraint knapsack variant.
- **Logic**: Greedy scoring and selection.
  - Score(e) = (effectiveness × matchQuality) / (effort × timeCost)
  - matchQuality factors: mood match, stress match, past success, familiarity.
  - Sort by score desc; pick while constraints allow.
- **Complexity**: O(n log n) time (sort), O(n) space.
- **Alignment to Requirements**:
  - Satisfies FR-UC41-01 and related UC8 coping-exercise requirements by producing optimal/near-optimal exercise sets respecting user constraints.
  - Supports personalization/contextual relevance (mood/stress/past success).
- **Reference**: Full details, flow, data structures, and API notes in `ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md`.

## 7. Traceability (Where to Find Full Details)
- **Full SRS**: `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` (sections 1–5 contain intro, scope, FRs with acceptance criteria, NFRs, context, constraints).
- **Algorithm Spec**: `ALGORITHM_IMPLEMENTATION_DOCUMENTATION.md` (complete design and mapping to UC41).

---

## Export Note
This concise file can be exported to PDF to satisfy the submission requirement. For full detail (all FRs with acceptance criteria), include the original `SOFTWARE_REQUIREMENTS_SPECIFICATION.md` alongside this summary in the submission package.


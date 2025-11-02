# Test Case Documentation - Use Case Alignment

## UC26: AI-Powered Mood Forecasting

### Use Case Goal
Enable users to receive predictive mood analytics so they can proactively plan self-care activities and interventions before mood declines occur.

### Test Case Mapping to Requirements

#### Test Case 1: 7-Day Forecast Generation (UC26-REQ-1)
- **What is being tested**: System generates complete 7-day mood forecast
- **How it aligns with UC26**: This is the CORE functionality - users need forecasts to plan ahead
- **Validates**: Forecast contains predictions for all 7 days with mood values (1-5 scale) and actionable explanations
- **Expected behavior**: 
  - Exactly 7 forecast days
  - All days have valid mood predictions (1.0-5.0)
  - All days have explanatory notes
  - System identifies peak and lowest mood days
- **Business value**: Users can see what their mood will be, enabling proactive planning

#### Test Case 2: Weekly Pattern Recognition (UC26-REQ-2)
- **What is being tested**: System identifies cyclical weekly patterns (weekday vs weekend mood differences)
- **How it aligns with UC26**: Pattern recognition enables users to plan activities around predictable mood variations
- **Validates**: Algorithm can distinguish weekday patterns from weekend patterns
- **Expected behavior**:
  - Weekday average calculated correctly
  - Weekend average calculated correctly
  - Weekend boost pattern detected (if exists)
  - Pattern strength quantified (>= 15% to be actionable)
- **Business value**: Users can plan social activities on weekends if weekend boost is detected

#### Test Case 3: Confidence Scoring (UC26-REQ-3)
- **What is being tested**: System provides confidence scores (0-100%) for each prediction
- **How it aligns with UC26**: Transparency in forecasting reliability builds user trust
- **Validates**: All predictions include confidence scores, average confidence >= 75%
- **Expected behavior**:
  - All predictions have confidence scores (0-100%)
  - Average confidence >= 75%
  - Most predictions have high confidence (>=85%)
  - Low confidence predictions minimized
- **Business value**: Users can assess prediction reliability and make informed decisions

#### Test Case 4: Proactive Interventions (UC26-REQ-4) - **CRITICAL**
- **What is being tested**: System triggers intervention recommendations when low mood (<3.0) is predicted
- **How it aligns with UC26**: **THIS IS THE PRIMARY VALUE** - preventing mood decline before it happens
- **Validates**: When forecast predicts mood < 3.0, system automatically recommends specific interventions
- **Expected behavior**:
  - Low mood threshold detected (<3.0)
  - Critical low mood detected (<2.5)
  - Multiple intervention options provided (>=3)
  - Interventions include quick actions (breathing) and support (social)
- **Business value**: User receives help BEFORE mood declines, enabling prevention

#### Test Case 5: Personalized Forecasting Models (UC26-REQ-5)
- **What is being tested**: System adapts forecast based on individual user's historical patterns
- **How it aligns with UC26**: Personalization ensures forecasts are relevant to specific user
- **Validates**: Forecast considers user's past mood patterns, stability, and trends
- **Expected behavior**:
  - User history analyzed for patterns
  - Forecast adjusted based on personal patterns
  - Stability metrics calculated
  - Personalized improvement trends identified
- **Business value**: More accurate forecasts lead to better planning decisions

#### Test Case 6: Seasonal Pattern Detection (UC26-REQ-6)
- **What is being tested**: System identifies seasonal and cyclical mood patterns (winter, spring, summer, fall)
- **How it aligns with UC26**: Long-term patterns enable seasonal planning
- **Validates**: Algorithm can detect which seasons have higher/lower moods
- **Expected behavior**:
  - Peak season identified (e.g., Summer)
  - Lowest season identified (e.g., Winter)
  - Seasonal variation quantified
- **Business value**: Users can prepare for seasonal mood changes (e.g., SAD prevention)

---

## UC35: Relapse Prevention Alerts

### Use Case Goal
Provide early warning system that detects potential mood decline patterns and triggers alerts with intervention recommendations to prevent relapse.

### Test Case Mapping to Requirements

#### Test Case 1: Risk Factor Assessment (UC35-REQ-1)
- **What is being tested**: System assesses multiple risk factors (sleep, stress, social support, medication, triggers)
- **How it aligns with UC35**: Early detection requires comprehensive risk assessment
- **Validates**: System calculates risk levels for each factor (0-100 scale)
- **Expected behavior**:
  - All risk factors assessed
  - Risk levels categorized (High >=70, Medium 50-69, Low <50)
  - Average risk calculated
  - High-risk factors identified
- **Business value**: Early identification of risk factors enables preventive action

#### Test Case 2: Risk Trend Tracking (UC35-REQ-2)
- **What is being tested**: System tracks how risk factors change over time (week-to-week)
- **How it aligns with UC35**: Trend analysis detects deteriorating patterns before crisis
- **Validates**: System can identify if risks are improving or worsening
- **Expected behavior**:
  - Weekly risk data tracked
  - Trends calculated (improving/degrading)
  - Patterns identified (e.g., sleep risk decreasing)
- **Business value**: Users can see if interventions are working (improving trends)

#### Test Case 3: Critical Threshold Detection (UC35-REQ-3) - **CRITICAL**
- **What is being tested**: System identifies when risk factors exceed critical thresholds (>=80) requiring immediate attention
- **How it aligns with UC35**: This is the CORE alerting mechanism
- **Validates**: System correctly identifies critical risk (>=80) and severe risk (>=90)
- **Expected behavior**:
  - Critical threshold defined (>=80)
  - Critical factors identified
  - Severe factors identified (>=90)
  - Alert triggered for critical risks
- **Business value**: Immediate alerts prevent crisis situations

#### Test Case 4: Early Warning System (UC35-REQ-4) - **CRITICAL**
- **What is being tested**: System detects early warning signs (e.g., "3 consecutive nights poor sleep") and triggers alerts
- **How it aligns with UC35**: **PRIMARY FUNCTIONALITY** - early detection before crisis
- **Validates**: System identifies specific warning patterns and their severity (High/Medium/Low)
- **Expected behavior**:
  - Warning signs detected (sleep disruption, isolation, stress, medication missed)
  - Severity levels assigned (High/Medium/Low)
  - Recommendations provided for each warning
- **Business value**: Users receive alerts with specific actionable recommendations

#### Test Case 5: Warning Prioritization (UC35-REQ-5)
- **What is being tested**: System prioritizes warnings by severity (High > Medium > Low)
- **How it aligns with UC35**: Users need to address most critical warnings first
- **Validates**: Warnings sorted by severity, critical warnings appear first
- **Expected behavior**:
  - Warnings categorized by severity
  - Sorting algorithm prioritizes High > Medium > Low
  - Critical warnings displayed first
- **Business value**: Users focus on most urgent issues first

#### Test Case 6: Warning Escalation (UC35-REQ-6)
- **What is being tested**: System tracks recurring warnings and escalates severity (Low -> Medium -> High)
- **How it aligns with UC35**: Repeated issues require stronger intervention
- **Validates**: System tracks warning frequency and increases severity for recurring issues
- **Expected behavior**:
  - Warning history tracked
  - Recurring issues identified
  - Escalation protocol applied (repeated warnings increase severity)
- **Business value**: Escalation ensures persistent issues receive appropriate attention

#### Test Case 7: Safety Plan Management (UC35-REQ-7)
- **What is being tested**: System maintains comprehensive safety plan with emergency contacts, coping strategies, and warning signs
- **How it aligns with UC35**: Safety plans provide immediate resources during crisis
- **Validates**: Safety plan contains emergency contacts (>=2), coping strategies (>=3), warning signs list
- **Expected behavior**:
  - Multiple emergency contacts (therapist, support person, crisis hotline)
  - Multiple coping strategies listed
  - Warning signs documented
  - Plan completeness validated (>=80%)
- **Business value**: Users have pre-planned resources for crisis situations

#### Test Case 8: Emergency Contact Validation (UC35-REQ-8)
- **What is being tested**: System validates emergency contacts are accessible and appropriate
- **How it aligns with UC35**: Contacts must be available when needed
- **Validates**: Contacts categorized by availability (24/7, business hours, personal)
- **Expected behavior**:
  - Therapist contact available
  - Support person contact available
  - Crisis hotline available (24/7)
  - Emergency services available (911)
- **Business value**: Users have multiple layers of support available

---

## UC32: AI-Generated Journaling Prompts

### Use Case Goal
Provide personalized, context-aware journaling prompts generated by AI based on user's mood state, preferences, and historical patterns to support therapeutic self-reflection.

### Test Case Mapping to Requirements

#### Test Case 1: Mood-Based Prompt Generation (UC32-REQ-1) - **CRITICAL**
- **What is being tested**: System generates prompts specifically tailored to user's current mood state
- **How it aligns with UC32**: This is the PRIMARY personalization feature
- **Validates**: Prompts match current mood (e.g., anxious prompts for anxious mood)
- **Expected behavior**:
  - Prompts filtered by mood tags
  - Mood-specific prompts prioritized
  - Prompts relevant to current emotional state
- **Business value**: Users receive prompts that address their current feelings

#### Test Case 2: Context-Aware Generation (UC32-REQ-2)
- **What is being tested**: System generates prompts that consider context (season, holiday, time of day)
- **How it aligns with UC32**: Context awareness makes prompts more relevant
- **Validates**: Prompts contain contextual elements (seasonal themes, holiday references)
- **Expected behavior**:
  - Seasonal prompts (winter, summer, etc.)
  - Holiday-specific prompts
  - Time-of-day appropriate prompts
  - Context reflected in prompt content
- **Business value**: Prompts feel timely and relevant to user's situation

#### Test Case 3: Category Management (UC32-REQ-3)
- **What is being tested**: System provides prompts across multiple categories (Gratitude, Reflection, Growth, etc.)
- **How it aligns with UC32**: Variety ensures therapeutic coverage
- **Validates**: Prompts available in multiple categories, filtering by category works
- **Expected behavior**:
  - Multiple categories available
  - Category filtering functional
  - Each category has multiple prompts
- **Business value**: Users can explore different therapeutic approaches

#### Test Case 4: Personalization Based on History (UC32-REQ-4)
- **What is being tested**: System adapts prompts based on user's journaling history and preferences
- **How it aligns with UC32**: Personalization improves engagement and effectiveness
- **Validates**: Prompts consider past usage, preferred categories, success patterns
- **Expected behavior**:
  - Frequently used prompts available
  - New prompts suggested
  - Preferences considered (category, difficulty, length)
- **Business value**: Personalized prompts are more likely to be completed

#### Test Case 5: Difficulty and Length Customization (UC32-REQ-5)
- **What is being tested**: System offers prompts with different difficulty levels (Easy/Medium/Hard) and time requirements
- **How it aligns with UC32**: Accommodates users with different time/energy availability
- **Validates**: Prompts categorized by difficulty and estimated time
- **Expected behavior**:
  - Easy/Medium/Hard difficulty options
  - Short/Medium/Long time estimates
  - Filtering by difficulty works
  - Filtering by time works
- **Business value**: Users can select prompts matching their current capacity

#### Test Case 6: Progress Tracking (UC32-REQ-6)
- **What is being tested**: System tracks journaling frequency, most used prompts, and provides insights
- **How it aligns with UC32**: Progress tracking encourages consistent journaling practice
- **Validates**: System tracks usage statistics, identifies patterns, provides insights
- **Expected behavior**:
  - Total uses tracked
  - Average uses calculated
  - Most used prompt identified
  - Usage patterns analyzed
- **Business value**: Users see their progress, encouraging continued engagement

---

## Test Quality Standards

### Each Test Must Include:
1. **Clear Purpose Statement**: What specific requirement is being validated
2. **Use Case Alignment**: How the test relates to use case goals
3. **Detailed Given-When-Then**: Explicit test scenario with purpose explanations
4. **Meaningful Assertions**: Assertions with messages explaining why each check matters
5. **Business Value Note**: How the tested functionality helps users

### Test Naming Convention:
- Format: `[UseCase]-REQ-[Number]: [Requirement Description]`
- Example: `UC26-REQ-1: System MUST generate complete 7-day forecast`

### Assertion Messages:
- Every assertion includes message explaining:
  - Which requirement is being validated
  - Why the check is important
  - Expected vs actual discrepancy explanation


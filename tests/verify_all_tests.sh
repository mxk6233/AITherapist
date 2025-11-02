#!/bin/bash
# Test Verification Script

echo "=========================================="
echo "  USE CASE TEST VERIFICATION REPORT"
echo "=========================================="
echo ""

total=0
complete=0
missing=""

for uc in 1 2 3 4 5 6 7 8 9 13 14 15 17 18 20 22 23 24 26 27 32 35; do
  case $uc in
    1) name="uc1_ai_chat_session" ;;
    2) name="uc2_crisis_intervention" ;;
    3) name="uc3_mood_logging" ;;
    4) name="uc4_user_registration" ;;
    5) name="uc5_personality_onboarding" ;;
    6) name="uc6_chat_history" ;;
    7) name="uc7_user_login" ;;
    8) name="uc8_coping_exercises" ;;
    9) name="uc9_mood_analytics" ;;
    13) name="uc13_preferences" ;;
    14) name="uc14_daily_affirmations" ;;
    15) name="uc15_network_connectivity" ;;
    17) name="uc17_accessibility" ;;
    18) name="uc18_notifications" ;;
    20) name="uc20_application_errors" ;;
    22) name="uc22_system_health" ;;
    23) name="uc23_security_protocols" ;;
    24) name="uc24_personalization" ;;
    26) name="uc26_mood_forecasting" ;;
    27) name="uc27_guided_breathing" ;;
    32) name="uc32_journaling_prompts" ;;
    35) name="uc35_relapse_prevention" ;;
  esac
  
  total=$((total + 1))
  test_file=$(find tests/unit/usecases/$name -name "*UnitTests.kt" 2>/dev/null | head -1)
  
  if [ -f "$test_file" ]; then
    count=$(grep -c "@Nested" "$test_file" 2>/dev/null || echo "0")
    if [ "$count" -eq 3 ]; then
      printf " UC%2d: %-35s [3/3 test cases]\n" "$uc" "$name"
      complete=$((complete + 1))
    else
      printf "⚠️  UC%2d: %-35s [%d/3 test cases]\n" "$uc" "$name" "$count"
    fi
  else
    printf "❌ UC%2d: %-35s [MISSING]\n" "$uc" "$name"
    missing="$missing UC$uc"
  fi
done

echo ""
echo "=========================================="
echo "  SUMMARY"
echo "=========================================="
echo "Total Use Cases: $total"
echo "Complete (3 test cases): $complete"
echo "Coverage: $((complete * 100 / total))%"
echo ""

if [ -z "$missing" ] && [ "$complete" -eq "$total" ]; then
  echo " ALL USE CASES HAVE 3 TEST CASES EACH"
  echo " TOTAL: $total use cases × 3 = $((total * 3)) test cases"
  exit 0
else
  echo "❌ VERIFICATION FAILED"
  [ -n "$missing" ] && echo "Missing:$missing"
  exit 1
fi

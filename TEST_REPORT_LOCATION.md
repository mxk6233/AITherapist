# ReligiousSupport Test Cases Location in HTML Report

## Test Report Location

The ReligiousSupport (UC40) test cases **ARE** included in the HTML test report. Here's how to find them:

### Direct Links

1. **Package Page** (All UC40 Tests):
   ```
   app/build/reports/tests/testDebugUnitTest/packages/com.serenityai.tests.unit.usecases.uc40_religious_support.html
   ```
   - Shows all 17 tests
   - 0 failures
   - Duration: 0.004s

2. **Main Index** (Navigate from here):
   ```
   app/build/reports/tests/testDebugUnitTest/index.html
   ```
   - Look for package: `com.serenityai.tests.unit.usecases.uc40_religious_support`
   - Click on it to see all test classes

### Test Classes in Report

The following test classes are available in the HTML report:

1. **ReligiousPreferenceTests** (3 tests)
   - `app/build/reports/tests/testDebugUnitTest/classes/com.serenityai.tests.unit.usecases.uc40_religious_support.ReligiousSupportUseCaseUnitTests$ReligiousPreferenceTests.html`

2. **SpiritualGuidanceTests** (4 tests)
   - `app/build/reports/tests/testDebugUnitTest/classes/com.serenityai.tests.unit.usecases.uc40_religious_support.ReligiousSupportUseCaseUnitTests$SpiritualGuidanceTests.html`

3. **ReligiousResourceTests** (3 tests)
   - `app/build/reports/tests/testDebugUnitTest/classes/com.serenityai.tests.unit.usecases.uc40_religious_support.ReligiousSupportUseCaseUnitTests$ReligiousResourceTests.html`

4. **PrayerRequestTests** (3 tests)
   - `app/build/reports/tests/testDebugUnitTest/classes/com.serenityai.tests.unit.usecases.uc40_religious_support.ReligiousSupportUseCaseUnitTests$PrayerRequestTests.html`

5. **ReflectionTests** (4 tests)
   - `app/build/reports/tests/testDebugUnitTest/classes/com.serenityai.tests.unit.usecases.uc40_religious_support.ReligiousSupportUseCaseUnitTests$ReflectionTests.html`

### How to View in Browser

1. **Open the main index**:
   ```bash
   open app/build/reports/tests/testDebugUnitTest/index.html
   ```

2. **Navigate to Packages section**:
   - Scroll down to find "Packages" section
   - Look for `com.serenityai.tests.unit.usecases.uc40_religious_support`
   - Click on it

3. **Or search in browser**:
   - Press `Cmd+F` (Mac) or `Ctrl+F` (Windows/Linux)
   - Search for "uc40" or "ReligiousSupport"
   - You'll find the package link

### Test Results Summary

- **Total Tests**: 17
- **Passed**: 17
- **Failed**: 0
- **Skipped**: 0
- **Duration**: 0.004s

### Verification Commands

To verify the tests are in the report:

```bash
# Check if package HTML exists
ls -lh app/build/reports/tests/testDebugUnitTest/packages/com.serenityai.tests.unit.usecases.uc40_religious_support.html

# Check if test class HTMLs exist
ls -lh app/build/reports/tests/testDebugUnitTest/classes/*uc40_religious_support*

# Search in index.html
grep -i "uc40\|religious" app/build/reports/tests/testDebugUnitTest/index.html
```

### If Tests Don't Appear

If you don't see the tests in the HTML report:

1. **Regenerate the report**:
   ```bash
   ./gradlew clean testDebugUnitTest
   ```

2. **Clear browser cache**:
   - Hard refresh: `Cmd+Shift+R` (Mac) or `Ctrl+Shift+R` (Windows/Linux)

3. **Check test execution**:
   ```bash
   ./gradlew testDebugUnitTest --info | grep -i "religious\|uc40"
   ```

### Test XML Results

The test XML results are also available:
```
app/build/test-results/testDebugUnitTest/TEST-com.serenityai.tests.unit.usecases.uc40_religious_support.*.xml
```

All 5 nested test classes have XML result files.








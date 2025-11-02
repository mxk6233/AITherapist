# Temporarily Disabled Tests

This directory contains tests that have been temporarily disabled for debugging, maintenance, or refactoring purposes.

## Guidelines

### When to Move Tests Here

1. **Debugging**: Test is failing and needs investigation
2. **Maintenance**: Test needs to be updated but blocks current work
3. **Refactoring**: Test is incompatible with current code changes
4. **Dependencies**: External dependency is temporarily unavailable

### When Re-enabling Tests

1. Fix the underlying issue
2. Move test file back to appropriate location:
   - `tests/unit/usecases/{uc_name}/` for unit tests
   - `tests/uat/usecases/{uc_name}/` for UAT tests
   - `tests/integration/` for integration tests
3. Update package name if it changed during move
4. Verify test compiles and runs successfully
5. Document the fix in commit message

### Naming Convention

When moving tests here, add a prefix to indicate why:
- `DEBUG_` - Under investigation
- `MAINTENANCE_` - Needs update/maintenance
- `REFACTOR_` - Incompatible with refactoring
- `DEPENDENCY_` - Waiting on external dependency

### Example

```
tests/temp_disabled_tests/DEBUG_uc1_ai_chat_session/AIChatSessionUseCaseUnitTests.kt
```

## Current Disabled Tests

*(Add disabled tests here with reason and date)*


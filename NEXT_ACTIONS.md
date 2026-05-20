# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 1/4 (25.0%)
- **Function parity:** 1/4 matched (target 1) — 25.0%
- **Class/type parity:** 0/0 matched (target 1) — N/A
- **Combined symbol parity:** 1/4 matched (target 2) — 25.0%
- **Average inline-code cosine:** 0.95 (function body across 1 matched files)
- **Average documentation cosine:** 0.00 (doc text across 1 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. unix

- **Target:** `rustlsnativecerts.Unix`
- **Similarity:** 0.95
- **Dependents:** 0
- **Priority Score:** 100.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting with the workspace shared binary.
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --init-tasks tmp/rustls-native-certs/src rust src/commonMain/kotlin/io/github/kotlinmania/rustlsnativecerts kotlin tasks.json

# Get next high-priority task
/Volumes/stuff/Projects/kotlinmania/bin/ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 3/4 (75.0%)
- **Function parity:** 4/4 matched (target 6) — 100.0%
- **Class/type parity:** 0/0 matched (target 8) — N/A
- **Combined symbol parity:** 4/4 matched (target 14) — 100.0%
- **Average inline-code cosine:** 0.37 (function body across 3 matched files)
- **Average documentation cosine:** 0.00 (doc text across 3 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 2 files with <0.60 function similarity
- **Needs Review:** 1 files with 0.60-0.84 function similarity
- **Excellent:** 0 files with >=0.85 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. windows

- **Target:** `rustlsnativecerts.Windows`
- **Similarity:** 0.26
- **Dependents:** 0
- **Priority Score:** 207.4
- **Functions:** 2/2 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_

### 2. macos

- **Target:** `rustlsnativecerts.Macos`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 108.3
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 3. unix

- **Target:** `rustlsnativecerts.Unix`
- **Similarity:** 0.68
- **Dependents:** 0
- **Priority Score:** 103.2
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

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |


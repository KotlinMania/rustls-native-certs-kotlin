# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 2/4 (50.0%)
- **Function parity:** 14/27 matched (target 32) — 51.9%
- **Class/type parity:** 4/4 matched (target 22) — 100.0%
- **Combined symbol parity:** 18/31 matched (target 54) — 58.1%
- **Average inline-code cosine:** 0.70 (function body across 2 matched files)
- **Average documentation cosine:** 0.17 (doc text across 2 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 1 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. lib

- **Target:** `rustlsnativecerts.Error`
- **Similarity:** 0.44
- **Dependents:** 0
- **Priority Score:** 102705.6
- **Functions:** 13/23 matched (target 31)
- **Missing functions:** `fmt`, `deduplication`, `malformed_file_from_env`, `from_env_missing_file`, `from_env_missing_dir`, `from_env_with_non_regular_and_empty_file`, `from_env_bad_dir_perms`, `from_env_bad_file_perms`, `test_cert_paths_bad_perms`, `first_error`
- **Types:** 4/4 matched (target 21)
- **Missing types:** _none_
- **Tests:** 0/9 matched

### 2. unix

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


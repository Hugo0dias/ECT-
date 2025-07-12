#!/bin/bash

# IIML Semantic Checker Test Runner
# Runs all test files and provides a summary

echo "IIML Semantic Checker - Test Suite"
echo "=================================="

cd "$(dirname "$0")"

# Check for clean flag
CLEAN_AFTER=false
[[ "$1" == "--clean" || "$1" == "-c" ]] && CLEAN_AFTER=true

# Check if classes directory exists, if not build first
if [ ! -d "classes" ] || [ ! -f "classes/IIMLSemanticCheck.class" ]; then
    echo -e "${YELLOW}Compiled classes not found, building first...${NC}"
    if ! ./build.sh; then
        echo -e "${RED}Build failed! Cannot run tests.${NC}"
        exit 1
    fi
fi

TOTAL_TESTS=0; PASSED_TESTS=0; FAILED_TESTS=0

# Colors
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[0;33m'; NC='\033[0m'

# Test function
run_test() {
    echo -n "Testing $(basename "$1")... "
        output=$(java -cp "/usr/local/lib/antlr-4.13.2-complete.jar:classes" IIMLSemanticCheck "$1" 2>&1)
    
# Check if output contains error indicators
    if echo "$output" | grep -q "ERROR\|FAILED"; then
        actual="fail"
    else
        actual="pass"
    fi
    
    if [ "$actual" = "$2" ]; then
        echo -e "${GREEN}PASS${NC}"
        ((PASSED_TESTS++))
    else
        echo -e "${RED}FAIL${NC} (expected $2, got $actual)"
        ((FAILED_TESTS++))
    fi
    ((TOTAL_TESTS++))
}

# Tests that should FAIL (semantic errors)
echo -e "\n${YELLOW}Testing files that should FAIL:${NC}"
run_test "tests/test-01-undeclared-variable.iiml" "fail"
run_test "tests/test-02-variable-redeclaration.iiml" "fail"
run_test "tests/test-03-type-mismatch.iiml" "fail"
run_test "tests/test-04-invalid-list-access.iiml" "fail"
run_test "tests/test-05-invalid-for-loop.iiml" "fail"
run_test "tests/test-06-no-image-declared.iiml" "fail"
run_test "tests/test-07-invalid-arithmetic.iiml" "fail"
run_test "tests/test-08-list-type-mismatch.iiml" "fail"

# Tests that should PASS (valid programs)
echo -e "\n${YELLOW}Testing files that should PASS:${NC}"
run_test "tests/test-09-valid-program.iiml" "pass"
run_test "tests/test-11-confusing-identifiers.iiml" "pass"
run_test "tests/test-12-complex-valid.iiml" "pass"
run_test "tests/test-13-assignment-without-redeclaration.iiml" "pass"
run_test "tests/test-14-multiple-confusing-names.iiml" "pass"
run_test "tests/test-15-unused-variables.iiml" "pass"
run_test "tests/test-16-negative-dimensions.iiml" "pass"

# Provided IIML example files that should PASS (valid examples)
echo -e "\n${YELLOW}Testing IIML example files:${NC}"
run_test "tests/test-17-example-des-iiml-01.iiml" "pass"
run_test "tests/test-18-example-min-iiml-01.iiml" "pass"
run_test "tests/test-19-example-min-iiml-02.iiml" "pass"

# Summary
echo -e "\n${YELLOW}Test Summary: $TOTAL_TESTS total, ${GREEN}$PASSED_TESTS passed${NC}, ${RED}$FAILED_TESTS failed${NC}"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "${GREEN}All tests passed!${NC}"
    
    # Clean up if requested
    if [ "$CLEAN_AFTER" = true ]; then
        echo -e "${YELLOW}Cleaning up...${NC}"
        rm -rf classes
        rm -f GrammarIIML*.java GrammarIIML*.interp GrammarIIML*.tokens
        cd ../ && rm -f GrammarIIML*.java GrammarIIML*.interp GrammarIIML*.tokens && cd iiml-semantic-check/
        echo -e "${GREEN}Cleanup completed!${NC}"
    fi
    exit 0
else
    echo -e "${RED}$FAILED_TESTS test(s) failed.${NC}"
    [ "$CLEAN_AFTER" = true ] && echo -e "${YELLOW}Skipping cleanup due to failures${NC}"
    exit 1
fi
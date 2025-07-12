#!/bin/bash

# Build the ANTLR project
echo "Building GrammarIML..."
antlr4-build GrammarIML
echo "Building GrammarIIML..."
antlr4-build GrammarIIML
if [ $? -ne 0 ]; then
    echo "Build failed"
    exit 1
fi

# Get all .iml files from semantictests directory
TEST_FILES=(./semantictests/*.iml)

# Check if any test files were found
if [ ${#TEST_FILES[@]} -eq 0 ]; then
    echo "No .iml test files found in ./semantictests/"
    exit 1
fi

# Run semantic analysis on each file
for file in "${TEST_FILES[@]}"; do
    echo "===================================================================="
    echo "Running semantic analysis on $file"
    echo "===================================================================="
    antlr4-run < "$file"
    echo ""
done

echo "All tests completed"
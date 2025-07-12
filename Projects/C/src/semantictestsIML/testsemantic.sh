#!/bin/bash
RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
NC=$(tput sgr0)

CLASSPATH=".:lib/ij.jar:/usr/local/lib/antlr-4.13.2-complete.jar"

# Build the ANTLR project
echo "Building GrammarIML..."
antlr4 -visitor GrammarIML.g4
if [ $? -ne 0 ]; then
    echo "${RED}Build failed${NC}"
    exit 1
fi

# Define the subdirectories to test
SUB_DIRS=("MoreTests/invalidTests" "testsFromPdf" "testsFromTeacher")

# Loop through each subdirectory
for dir in "${SUB_DIRS[@]}"; do
    # Get all .iml files in the current subdirectory
    TEST_FILES=(./semantictestsIML/"$dir"/*.iml)
    
    # Check if any test files were found
    if [ ${#TEST_FILES[@]} -eq 0 ] || [ ! -e "${TEST_FILES[0]}" ]; then
        echo "No .iml test files found in ./semantictestsIML/$dir/"
        continue
    fi
    
    echo ""
    echo "${GREEN}############################################################"
    echo "### RUNNING TESTS FROM: $dir"
    echo "############################################################${NC}"
    echo ""
    
    javac -cp "$CLASSPATH" RunSemanticTestsIMLMain.java

    # Run semantic analysis on each file
    for file in "${TEST_FILES[@]}"; do
        echo "===================================================================="
        echo "Running semantic analysis on $file"
        echo "===================================================================="
        set +e  # Disable exit on error
        java RunSemanticTestsIMLMain < "$file"
        set -e  # Re-enable exit on error
        echo ""
    done
done

echo "${GREEN}All tests completed${NC}"

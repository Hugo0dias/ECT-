#!/bin/bash

# IIML Semantic Checker - Build Script
# Compiles the IIML semantic checker using source files from parent directory

echo "IIML Semantic Checker - Build Script"
echo "===================================="

cd "$(dirname "$0")"

# Check for clean flag
CLEAN_AFTER=false
if [[ "$1" == "--clean" || "$1" == "-c" ]]; then
    CLEAN_AFTER=true
    echo "Clean mode enabled - will clean up generated files after build"
    echo
fi

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m'    # No Color

echo -e "${YELLOW}Checking for ANTLR files...${NC}"

# Create output directory for compiled classes
mkdir -p classes

# Check if ANTLR files exist, if not generate them locally
if [ ! -f "GrammarIIMLLexer.java" ] || [ ! -f "GrammarIIMLParser.java" ]; then
    echo -e "${YELLOW}ANTLR files missing, generating from grammar...${NC}"
    # Generate ANTLR files in parent directory and copy them here
    cd ../
    if antlr4 -visitor GrammarIIML.g4; then
        echo -e "${GREEN}ANTLR generation successful!${NC}"
        # Copy generated files to iiml-semantic-check directory
        cp GrammarIIML*.java GrammarIIML*.interp GrammarIIML*.tokens iiml-semantic-check/
        echo -e "${GREEN}ANTLR files copied to test directory!${NC}"
        cd iiml-semantic-check/
    else
        echo -e "${RED}ANTLR generation failed!${NC}"
        cd iiml-semantic-check/
        exit 1
    fi
else
    echo -e "${GREEN}Using existing ANTLR files!${NC}"
fi

echo -e "${YELLOW}Compiling IIML Semantic Checker from parent directory...${NC}"

# Compile the ANTLR-generated files first (now in current directory)
if javac -cp "/usr/local/lib/antlr-4.13.2-complete.jar" -d classes Grammar*.java; then
    echo -e "${GREEN}ANTLR files compiled successfully!${NC}"
else
    echo -e "${RED}ANTLR files compilation failed!${NC}"
    exit 1
fi

# Compile only the IIML-related files and their dependencies (excluding ImageGenerator)
IIML_FILES=(
    "../Type.java"
    "../NumberType.java"
    "../ListType.java"
    "../StringType.java"
    "../BooleanType.java"
    "../PercentageType.java"
    "../ImageType.java"
    "../Figure.java"
    "../CircleFigure.java"
    "../RectangleFigure.java"
    "../CrossFigure.java"
    "../PlusFigure.java"
    "../LineFigure.java"
    "../ErrorHandling.java"
    "../IIMLSemanticCheck.java"
)

if javac -cp "/usr/local/lib/antlr-4.13.2-complete.jar:classes" -d classes "${IIML_FILES[@]}"; then
    echo -e "${GREEN}Compilation successful!${NC}"
else
    echo -e "${RED}Compilation failed!${NC}"
    exit 1
fi

echo -e "\n${YELLOW}Running test suite...${NC}"

# Run the test suite
if ./run_tests.sh; then
    echo -e "\n${GREEN}All tests passed! Project is ready.${NC}"
else
    echo -e "\n${RED}Some tests failed. Please check the output above.${NC}"
    exit 1
fi

echo -e "\n${YELLOW}Usage Examples:${NC}"
echo "  java -cp \"/usr/local/lib/antlr-4.13.2-complete.jar:classes\" IIMLSemanticCheck program.iiml"
echo "  java -cp \"/usr/local/lib/antlr-4.13.2-complete.jar:classes\" IIMLSemanticCheck --help"
echo "  ./run_tests.sh"

echo -e "\n${GREEN}Build completed successfully!${NC}"

# Clean up if requested
if [ "$CLEAN_AFTER" = true ]; then
    echo -e "\n${YELLOW}Cleaning up generated files...${NC}"
    
    # Remove entire directories that were generated
    echo "Removing generated directories:"
    if [ -d "classes" ]; then
        echo "  removing classes/ directory"
        rm -rf classes
    fi
    
    # Remove ANTLR-generated files
    echo "Removing ANTLR-generated files:"
    for file in GrammarIIML*.java GrammarIIML*.interp GrammarIIML*.tokens; do
        if [ -f "$file" ]; then
            echo "  removing $file"
            rm -f "$file"
        fi
    done
    
    # Also clean up ANTLR files from parent src directory
    echo "Removing ANTLR-generated files from src directory:"
    cd ../
    for file in GrammarIIML*.java GrammarIIML*.interp GrammarIIML*.tokens; do
        if [ -f "$file" ]; then
            echo "  removing $file"
            rm -f "$file"
        fi
    done
    cd iiml-semantic-check/
    
    echo -e "${GREEN}Cleanup completed! Only essential files remain.${NC}"
fi
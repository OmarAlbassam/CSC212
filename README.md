# Search Engine - CSC212 Data Structures Project

A Java search engine implementation demonstrating various data structures and retrieval methods for CSC212 (Data Structures course).

## Project Overview

This search engine implements two fundamental information retrieval methods:

### Search Methods

**Boolean Retrieval**: Processes queries with AND/OR operators to return documents that match exact criteria. Uses postfix expression evaluation to handle complex boolean queries.

**Ranked Retrieval**: Returns documents ranked by relevance based on term frequency. Results are ordered by how often query terms appear in each document.

### Data Structures

The project implements three distinct data structure approaches, each supporting both search methods:

1. **Inverted Index with AVL Trees**: 
   - Primary index: AVL tree mapping terms to nested AVL trees
   - Each nested AVL tree contains document IDs where the term appears
   - Provides O(log n) search performance

2. **Inverted Index with Linked Lists**:
   - Primary index: Linked list of terms
   - Each term maps to a linked list of document IDs
   - Simpler structure with linear search time

3. **Standard Index with Linked Lists**:
   - Document-centric approach
   - Each document maps to a linked list of terms it contains
   - Requires sequential document scanning for queries

## Configuration

### Important: File Path Setup

Before running the project, you need to update the file paths in the `TextProccesor` class:

1. Open the `TextProccesor` class in the `src/` directory
2. Locate the two path constants at the top of the class
3. Update them to match your local file system paths

**Example:**
```java
// Update these paths to match your system
private static final String DOCS_PATH = "data/dataset.csv", STOP_WORDS_PATH = "data/stop.txt";
```

> **Note:** If you encounter exceptions when reading data files, this is likely due to incorrect file paths. Make sure the paths point to the correct location of your data files.

## Usage

Ensure all file paths are correctly configured before running the project.

### Working with Data Files

- Place your input data files in the `data/` directory
- The program will read from and write to files in this directory

## Notes

- Make sure to verify file paths before running the program
- Check the diagrams folder for helpful visualizations
- Data files should be properly formatted according to the program requirements


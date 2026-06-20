Khoekhoegowab Lexicon Processing Engine
Overview

A high-performance lexical processing system designed to parse, normalize, and query structured Khoekhoegowab dictionary data.

The engine transforms raw linguistic transcriptions into structured, searchable entries supporting multi-dimensional lookup across words, definitions, dialects, origins, and grammatical categories.

Key Features
Advanced parser for complex lexicon structures
Handles morphological rules (optional stems, tilde expansion)
Unicode-aware processing (supports click consonants like | ‖ } !)
Multi-index search system for fast retrieval
Supports multi-definition and multi-entry word expansions
Extensible architecture for additional linguistic features
Supported Queries

The system supports searching across:

Word matches (exact + substring)
Definitions
Dialects
Parts of speech
Language origins
Architecture

The system is designed with modular separation:

Parser Layer → Converts raw text into structured entries
Model Layer → Represents dictionary objects
Index Layer → Builds fast lookup structures
Query Engine → Handles search operations
CLI Interface (Khoe.java) → Entry point for execution
Design Highlights
In-memory indexing for fast lookup performance
Rule-based morphological expansion system
Support for multiple definitions per entry
Efficient handling of nested linguistic structures
Optimized for sub-second query response time
How to Run

Compile:

javac -cp lib/* -d bin src/*.java

Run:

java -cp bin Khoe noGUI <input-file> <handin-mode> <search-type>

Search modes (final system):

w → word
e → definition
d → dialect
o → origin
p → part of speech
Example

Input:

{<word>s} n. example definition;

Output:

words##noun##example definition
Use Cases
Digital dictionary systems for under-resourced languages
Linguistic research tools
NLP preprocessing pipelines
Educational language software
Structured lexicon databases

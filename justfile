# justfile for csci-132-template

import? 'internal/justfile'

# Run the autograder on this repo
# Usage: just autograde
[group('general')]
autograde:
    python {{justfile_directory()}}/meta/autograder.py run -l

# List all available recipes
[group('general')]
list:
    @just --list

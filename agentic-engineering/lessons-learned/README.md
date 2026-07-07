# Lessons Learned from Using Agents

## Environment & Tooling

* A **Git Repo** is important to manage changes.
    - Undo changes (compared to the repo).
    - Use a feature branch to work with an agent.

* The integration of a **Linux shell** (many small tools) and coding
    agents works very well.

    For CLI-based coding agents, using a Linux shell (or macOS)
    provides a significant advantage over a native Windows
    environment. The "Linux philosophy" is essentially the native
    language of these agents.

    - The agent "knows" grep, sed, awk, find, and xargs by heart.
        It can pipe these together flawlessly.

* **IDE** features are less important.
    - Agents can do most refactoring and boilerplate code generation.
    - Debugging is still a topic where IDEs help.
    - Copilot is perfect for small changes in a file.

## Agent Configuration & Communication

* A **CLAUDE.md** (AGENTS.md) file is needed, but should be short
    (save tokens).

* **Design and Architectural Patterns** help to communicate with
    agents. One name replaces a lot of description about the
    implementation structure. It increases abstraction and makes
    communication more efficient.

## Process & Workflow

* **Test Automation** is important to validate the generated code.

* Break down tasks into **small (vertical) tasks**.
    Use **Kanban** for organizing these tasks and work on them in
    parallel.

*Egon Teiniker, 2026, GPL v3.0*
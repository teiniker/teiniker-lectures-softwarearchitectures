# Lessons Learne from Using Agents 


*  **CLAUDE.md** (AGENTS.md) file is needed, but should be short (save tokens)
 
* **IDE** features are less important
    - Agents can do most of refactoring and boilerplade code generation 
    - Debugging is still a topic
    - Copilot is perfect for small changes in a file.

* **Git Repo** is important to manage changes.
    - Undo changes (compared to the repo)
    - Use feature branch to work with an agent.

* **Design and Architectural Patterns** help to communicate with Agents.
    One name replaces a lot of describtion about the implementation structure.
    It increases abstraction and makes communication more efficient.

* **Test Automation** is important to validate the generated code. 

* The integration of a **Linux shell** (many small tools) and coding 
    agents work very well.

    For CLI-based coding agents, using a Linux shell (or macOS) provides 
    a significant advantage over a native Windows environment. 
    The "Linux philosophy" is essentially the native language of these agents.

    - The agent "knows" grep, sed, awk, find, and xargs by heart. 
        It can pipe these together flawlessly.

* Break down tasks into **small (vertical) tasks**.
    Use **Kanban** for organizing these tasks and work on them in parallel.


# Example: Requirements Analysis

Requirements can be written as **free-form prose**, like
`specification.md` in this directory, or as structured templates:
**user stories** (`As a / I want to / So that`) paired with
**acceptance criteria** (`Given / When / Then`). This example
converts one into the other, and shows why the structured form is
worth the extra step.

Structure beats prose for two reasons:
* **A template forces decomposition**: every user story is 
    scoped to one actor, one goal, and one benefit, and every 
    acceptance criterion is scoped to one concrete input and 
    one concrete outcome. 
    
    Unstructured prose has no such boundary, so a single rule 
    can be buried mid-paragraph anywhere in the document, exactly 
    what `specification.md` demonstrates below.

* **A template is far cheaper to review** than the code it
    produces. A user story plus its acceptance criteria is a 
    few lines of plain text; the code implementing it can 
    run to hundreds of lines across several files. 
    
    Catching a wrong requirement while it is still one paragraph 
    of text costs a one-line edit; catching the same mistake 
    only after it has been implemented means reading, and
    re-reading, generated code to work out what it was even 
    supposed to do, then fixing it in every file the wrong 
    assumption reached.


## Text-Based Requirements Specification

The `specification.md` file in this directory is a deliberately
poorly written example: a wall of dense, redundant prose with no
consistent structure. It illustrates the problems a template avoids.

* **Scattered Information**: To build the `POST` endpoint, a
    developer has to look at Section 2.2 (for validation rules),
    Section 3.1 (for HTTP rules), and Section 1/2.1 (for DB setup
    rules).

* **Massive Redundancy**: The document repeats how to handle the
    MariaDB connection pool, how to convert JSON, and how to write
    basic SQL parameters over and over again for every single
    endpoint.

* **Accidental Scope Creep**: Hidden in Section 3.6 is an entire
    conditional routing rule ("if query parameter present, bypass
    Find All") that changes how the base URL behaves, easy to miss in
    a wall of text.


## From Text to User Stories

Let's use the agent to turn this prose into structured, reviewable
user stories:

_claude>_ **Given
    agentic-engineering/examples/requirements-analysis/specification.md,
    extract user stories (as a/i want to/so that) and acceptance
    criteria (given/when/then) and store them in a userstories.md
    file.**

We intentionally generated structured user stories rather than direct
code. This gives the developer an opportunity to review the design
before the agent begins bulk code generation.

* **Independence**: A developer can take just User Story 3 and
    build/test the deletion feature completely in parallel without
    getting bogged down by the validation rules of creation or
    update.

* **Built-in Test Cases**: The Given/When/Then syntax translates 1:1
    into automated QA or integration testing (like JUnit/AssertJ/
    pytest). If the test passes, the requirement is met. There's no
    room for interpretation.

* **No Code-Level Hand-Waving**: The traditional spec wasted lines
    writing out actual SQL statements (`SELECT * FROM...`). The user
    stories don't care how the code talks to MariaDB (could be raw
    SQL, an ORM, or a repository pattern); they focus purely on the
    behavioral expectations of the feature.

Each acceptance criterion still reads like a test case someone has to
run by hand. Attaching a runnable example to it closes that last gap:

_claude>_ **Add a curl example to each acceptance criteria.**

*Egon Teiniker, 2026, GPL v3.0*

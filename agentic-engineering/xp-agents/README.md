# Extreme Programming in the Context of Coding Agents 

Extreme Programming (XP) is an agile software development framework developed
by Kent Beck, designed to improve software quality and responsiveness to
changing customer requirements. It thrives on
frequent releases in short development cycles, which introduces checkpoints
where new customer requirements can be adopted. To achieve this, XP relies on a
set of interconnected practices organized across four dimensions.

![XP-Practices](figures/XP-Practices.jpg)


## Core Engineering Practices

These are the technical habits that keep the code clean, flexible, and
reliable.

* **Test-Driven Development (TDD)**: Developers write an automated
    unit test before writing the actual code. Once the test fails, they write
    the minimum code required to make it pass, and then refactor.

* **Pair Programming**: Two developers work together at one
    workstation. One (the **Driver**) writes the code, while the other (the
    **Navigator**) reviews each line, watches for tactical defects, and
    thinks strategically about the bigger picture.

* **Refactoring**: Continuous disciplined optimization of the
    internal structure of the code without changing its external behavior. It
    removes duplication and improves readability.

* **Simple Design**: The team builds the simplest thing that works.
    Extra features or speculative architecture for future needs are avoided
    (often summed up as YAGNI: ``You Ain't Gonna Need It'').


## Integration and Delivery Practices

These practices focus on how code moves from a developer's machine to the
actual product.

* **Continuous Integration (CI)**: Developers integrate their code
    into the main repository multiple times a day. Every merge triggers
    automated tests to detect integration errors as early as possible.

* **Collective Code Ownership**: Anyone on the team can change any
    piece of code at any time. No single person becomes a bottleneck for a
    specific module, which distributes knowledge across the team.

* **Coding Standards**: The team agrees on a unified set of rules
    for writing code, making the codebase look like it was written by a single,
    highly consistent individual.


## Planning and Feedback Loops

XP relies on tight feedback loops between the business side and the technical
side to stay on track.

* **The Planning Game**: A collaborative meeting where business
    stakeholders define features as User Stories and prioritize them, 
    while the development team estimates the effort required.

* **Small Releases**: The team delivers working software to
    production frequently, ensuring the customer gets value early and can
    provide real-world feedback.

* **On-Site Customer**: A representative from the customer side sits
    with the development team full-time to answer questions and provide
    immediate feedback.

## Team Wellness and Environment

* **Sustainable Pace**: Teams should not work excessive overtime. XP
    holds that tired developers write lower-quality code, which slows down the
    project in the long run.

* **Metaphor**: The team defines a shared, easily understood analogy
    of how the target system works, keeping all stakeholders aligned on the
    system's architecture and purpose.


## References

* Kent Beck and Cynthia Andres. **Extreme Programming Explained: Embrace Change**. Addison-Wesley, 2nd Edition 2004

*Egon Teiniker, 2026, GPL v3.0*       
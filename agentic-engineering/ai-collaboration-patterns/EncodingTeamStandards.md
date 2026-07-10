# Encoding Team Standards

AI generates technically correct code that violates team expectations, 
requiring extensive rework.

AI coding assistants respond to whoever is prompting, and the quality 
of what they produce depends on how well the prompter articulates team 
standards.

The human parallel: _What a senior developer does instinctively._

When a team has worked together long enough, certain practices become invisible. 
The senior engineer who rejects a pull request does not consult a checklist; 
she recognizes, almost instantly, that the error handling is incomplete, that 
the abstraction is premature, that the naming does not follow the team's 
conventions. 

The standards that shape how an AI generates code, refactors existing systems, 
checks for security vulnerabilities, or reviews a pull request should not be 
tips shared on Slack or tribal knowledge carried in a senior's head. They should 
be versioned artifacts that encode “how we do things here” into a form that 
executes consistently for everyone.


## The Consistency Problem

When AI-assisted development depends on who is prompting, seniors become 
bottlenecks, not because they write the code, but because they are the only 
ones who know what to ask for.

AI-generated code drifts from team conventions when one developer prompts 
and aligns when another does. Refactoring quality varies by who requests it. 
Security checks catch different things depending on who frames the question. 
Technical debt accumulates unevenly.

So, we have to make the AI apply the team's judgment, consistently, regardless 
of who is prompting, across every meaningful interaction.


## Executable Governance

A team standard encoded as an AI instruction does not depend on someone 
remembering to apply it. The instruction is the application. 

When a developer generates code using an instruction that embeds the team's 
architectural patterns, or runs a security check that encodes the team's 
threat model, the standards are applied as a side effect of the workflow, 
not as a separate step that requires discipline. The governance is the workflow.

* **From tacit to explicit**: Taking what the senior knows instinctively 
and writing it down. The difference is that the target format is not a wiki 
page or a checklist, but a structured instruction set that an AI can execute. 

* **From documentation to execution**: Linting rules are versioned config 
files, not personal preferences. CI/CD pipelines are executable definitions, 
not wiki pages describing deployment steps. AI instructions belong in the 
same category: configuration that executes, not documentation that informs. 

When these instructions live in the repository, they have the same status 
as any other piece of team infrastructure. The developer does not need to 
carry the team's full set of standards in their head. 
They invoke an instruction. The team's judgment is applied consistently, 
not because the developer memorized it, but because the infrastructure 
encodes it.

## What This Looks Like

A well-structured executable instruction has a recognizable anatomy:

* **Role definition**: Not because the AI needs a persona, but because the 
    role sets the expertise level and perspective. 

    The role is the lens through which every subsequent instruction is applied.

    _Example_: “Role: senior engineer implementing a new service following 
        the team's architectural patterns” 

* **Context requirements**: What the instruction needs before it can operate: 
    the relevant code, access to the project's architectural context, any 
    applicable constraints. 
    
    This makes dependencies explicit rather than hoping the developer remembers 
    to provide them.

* **Categorized standards**: The categories matter more than the individual 
    items. For a generation instruction, the categories might be: architectural 
    compliance (must follow), convention adherence (should follow), and style 
    preferences (nice to have).
    For a security instruction: critical vulnerabilities (blockers), important 
    concerns (must address before merge), and advisories (track and evaluate). 
    For a review instruction: breaking issues, important findings, and suggestions. 
    
    This priority structure encodes the team's judgment, not just their knowledge. 
    It tells the AI the developer what matters most.

* **Output format**: A structured response with a summary, categorized findings, 
    and clear next steps. The format ensures that output from the instruction is 
    comparable across runs and across developers, a property that matters once 
    multiple people are using the same instructions regularly. 

    For generation instructions, this shapes the completeness and structure of 
    the code produced.

The principle applies across the full range of AI interactions:
* **Generation**: Encodes how the team builds new code (architecture patterns, 
    naming, error-handling, testing expectations) so output aligns from the 
    first pass.

* **Refactoring**: Encodes how the team improves existing code (preserve contracts, 
    avoid premature abstraction, propose incremental change).

* **Security**: Encodes the team's threat model (what to check and how to grade 
    severity) so checks are team-specific rather than generic.

* **Review**: Encodes what the team checks in review (architecture alignment, 
    error handling, type safety, conventions) with a consistent severity structure.

Keep instructions small and single-purpose. Smaller instructions maintain focus, 
are easier to maintain, and compose flexibly.


## Surfacing the Tacit Knowledge

The most interesting part of creating these instructions is the extraction 
process. It is implemented as an interview with the team's senior engineers, 
structured around pointed questions that span the full development workflow: 
- What architectural decisions should never be left to individual judgment? 
- Which conventions are corrected most often in generated code? 
- Which security checks are applied instinctively? 
- What triggers an immediate rejection in review? 
- What separates a clean refactoring from an over-engineered one?

The answers map directly to instruction structures. Non-negotiable 
architectural patterns become generation constraints. Frequent corrections 
become convention checks. Security instincts become threat-model items. 
Review rejections become critical checks. Recurring mistakes become 
anti-patterns to flag. 

The interviews essentially write the instructions; the act of creation is 
the act of organizing tacit knowledge into explicit, prioritized checks.


## Where Standards Meet the Workflow

These instructions apply at different points in the development workflow:

* At **generation-time**, when a developer asks the AI to create a new service, 
    implement a feature, or write tests, a generation instruction ensures the 
    output follows team conventions from the start. 

* During development, a **refactoring** instruction keeps improvements 
    aligned with team norms, and a security instruction applies the 
    team's threat model rather than a generic checklist. The standards 
    are present throughout the development process, not bolted on at 
    the end.

* At **review-time**, when a developer finishes a piece of work (whether 
    AI-generated or manually written), a review instruction applies the 
    team's quality gate. 

* Optionally in **CI**, some teams extend these instructions into their 
    continuous integration pipeline as an automated consistency check. 
    
    CI-level instructions must be fast enough not to slow the pipeline, 
    predictable enough to avoid noisy false positives, and maintained 
    with the same discipline as any other CI gate.


## Standards as Shared Infrastructure

The priming document tells the AI how the project works; the executable 
instruction tells the AI how the team works.

When it lives in the repository, it inherits the properties of any versioned 
artifact: changes tracked, standards owned collectively, every developer 
working from the same version. 

Different tools implement this differently (custom commands, skills, rules 
files, project instructions) but the underlying property is the same: 
a versioned, shared artifact that the AI executes consistently.

Creating good instructions requires effort: the extraction interviews, 
the drafting, the iteration. Overly prescriptive instructions become 
brittle; they produce false positives on edge cases or fight against 
legitimate variations in approach. There is a maintenance burden as 
standards evolve. And there is a risk of over-engineering: not every 
interaction with AI needs a dedicated instruction.

## References

* [Encoding Team Standards](https://martinfowler.com/articles/reduce-friction-ai/encoding-team-standards.html)

*Egon Teiniker, 2026, GPL v3.0*
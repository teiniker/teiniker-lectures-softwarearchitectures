# Example: Analyze and Refactor an Existing Project

## Analyze a Given Project

Before Claude can find or fix anything, it needs to understand what
the project actually does. 

It reads the same files a new team member
would start with: `pom.xml` for dependencies and build configuration,
every Java source file for structure and behavior,
`application.properties` for how the service is wired to the
database, and the existing `README.md` for what it claims to do.

Where reading alone cannot confirm a claim, for example whether an
endpoint really returns the status code the README shows, Claude runs
the project (`mvn spring-boot:run`) and exercises it with the same
`curl` commands the README documents, comparing the real response to
what was actually written. For a larger codebase where the relevant
files are not known in advance, this reading step can be delegated to
a fast, read-only Explore agent that locates them first, so the main
analysis is not spent on broad directory search.

_claude>_ **Analyze the following example:
    services/api-styles/SpringBoot-BookService-JPA**

_claude>_ **In this directory, write an Analysis.md file with your
    findings.**

_claude>_ **Add a class diagram in Mermaid format to Analysis.md**

TODO: Explain what Mermaid diagrams are and why Claude can generate them.


## Find Problems in the Given Code Base

Once the structure is understood, Claude looks first for problems
that do not require running the code: dead imports, an inconsistent
return type between sibling endpoints, an update that mutates a field
in place where the HTTP method implies a full replacement, an unused
annotation, missing tests. 

A problem that is about runtime behavior
rather than code shape, for example whether `POST` really creates a
new row or silently overwrites an existing one when the client
supplies an `id`, only surfaces by tracing what the code does for a
given input and confirming it end to end, the same way the `curl`
checks in the previous section did. 

_claude>_ **Can you find any problems in the given implementation?**


## Fix Some Problems

_claude>_ **Fix problem 1. and 2.**

Fixing those two exposes a real problem in the database access layer:

_claude>_ **I get a runtime error: java.sql.SQLException: (conn=41)
    Field 'id' doesn't have a default value - is the DB schema
    deployed?**

_claude>_ **Change the ddl-auto setting to create a new schema for
    each deployment**

Fixing one problem at a time, verifying it, and committing before
starting the next keeps each change isolated and easy to undo. 

If fixing problem 2 breaks something that was working after problem 
1, `git bisect` (or even just `git log`) points straight at the
responsible commit instead of leaving us to guess which of several
bundled fixes caused the regression. 

It also means each fix is
validated against the actually running service, as the `ddl-auto`
fix above shows, before the next one is built on top of it, rather
than discovering several fixes' worth of interacting side effects all
at once at the end. 

*Egon Teiniker, 2026, GPL v3.0*

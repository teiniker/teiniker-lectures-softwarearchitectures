# Example: Implement a REST Service

Given a set of user stories, we want an agent to implement a REST
service end to end: turn the stories into a plan, review that plan,
then implement it one story at a time.

## Plan First

Before any code is written, we ask the agent to turn the user stories
into a concrete implementation plan. A plan is cheap to read and cheap
to correct; code built on a wrong assumption is not, so this is where
we want to catch problems first.

_claude>_ **In agentic-engineering/examples/development read the
    userstories.md file and create an implementation plan. Ask me if
    you need information. Compare your plan with
    services/api-styles/SpringBoot-BookService-JPA. Store the plan in
    Plan.md.**

- Tell the agent explicitly to generate a plan and store it in a
    file, rather than jumping straight to code.
- Point the agent at a comparable, already-reviewed example
    (`SpringBoot-BookService-JPA`), so it has a concrete pattern to
    follow instead of inventing its own conventions.


## Manually Review the Plan

We read the generated `Plan.md` and correct it before any code
exists, while a correction is still a one-line edit to a document
instead of a rewrite spread across several source files.

_claude>_ **In the plan, change ddl-auto=update to create. Remove 6.
    Error Handling: Story 7 from the plan.**


## Implement Step by Step

- Before the agent starts implementing, we `/clear` the current
    context, so the coding steps that follow are not influenced by
    the (already-resolved) planning discussion.

- We implement one user story after another, rather than asking for
    the whole service at once, so that each step produces a small,
    reviewable diff.

- To run and test the service, the MariaDB server must be running:

    ```bash
    $ sudo systemctl start mariadb.service
    ```

Implementing **story by story**, with a **review after each one**, is what
keeps this process trustworthy. A diff that adds one controller
method is something we can actually read end to end; a diff that adds
six at once is something we can only skim. Reviewing after every
story also keeps the commit history useful: if something breaks two
stories later, `git log` and `git bisect` can point at the exact
commit, because each commit is small and self-contained. Most
importantly, a wrong assumption the agent makes (about a status code,
a column name, a validation rule) is caught after one story's worth
of code, not after it has quietly become the pattern the next five
stories are built on. Working this way is slower per story, but it is
what builds enough confidence in the agent's output to consider
running it with less supervision, which is exactly the trade-off the
Ralph Loop below makes.

_claude>_ **Implement Story 1 first.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Implement Story 2 next.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Implement Story 3 next.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Implement Story 4 next.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Implement Story 5 next.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Implement Story 6 next.**

Manually review the code, run curl tests, commit changes.

_claude>_ **Write the README.md**

Manually review the text, commit changes.


## Improvements

* With a `ServiceConnector` and a set of executable acceptance test
    cases, the curl-based verification we did by hand after each
    story could run automatically instead, turning "manually review
    the code, run curl tests" into a single automated check.

* With a **Ralph Loop**, we can automate the outer cycle itself:
    instead of a person prompting "implement Story N", reviewing, and
    then prompting "implement Story N+1", a script repeatedly invokes
    the agent with the same instruction (implement the next
    unfinished story, verify it against its acceptance criteria,
    commit) and lets it run unattended until every story is done. It
    only makes sense once the verification step is itself automated
    (see the point above): the loop is only as trustworthy as the
    check that decides whether one iteration succeeded, and with no
    human reading each diff, a weak check lets mistakes accumulate
    silently across many iterations before anyone notices. It trades
    the careful, human-reviewed pace of the previous section for
    throughput, once that pace has already shown that the agent gets
    individual stories right when someone is watching.

*Egon Teiniker, 2026, GPL v3.0*
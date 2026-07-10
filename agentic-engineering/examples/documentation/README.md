# Example: Generate Documentation

To generate documentation with an agent, we do not ask it to write
free text starting from a blank page. Instead we hand it a
**template**: a Markdown file that already fixes the title, the
section headings, and a `TODO` marker under every section that names
exactly what belongs there. Diagram placeholders and links to the
concrete implementation the documentation describes complete the
template.


## The Template

A documentation template names four things:

* **Title and sections** we want the finished document to have, in
    the order we want them.
* **TODOs** under each section, describing the expected content in a
    sentence or two, so the agent knows what to write without being
    told how to word it.
* **Diagrams** we want generated, for example a class diagram derived
    from the actual source code rather than drawn by hand.
* **Links** to the concrete implementation the document describes, so
    both the agent and a later reader know exactly which code the
    text refers to.

`SpringBoot-BookService/README.md` in this directory is one such
template. Its `TODO`s ask for an overview of the service, setup
instructions, an explanation of each `curl` request together with its
actual response, a Mermaid class diagram of the implementation, and a
short walkthrough of the code. The `curl` commands themselves are
already part of the template, the agent's job is to run them and
document what really comes back, not to invent example output.


## Generating the Documentation

_claude>_ **Extend (and rephrase) the
    agentic-engineering/examples/documentation/SpringBoot-BookService/README.md
    file - follow the TODOs.**

Given this one instruction, the agent starts the service, runs the
`curl` requests already present in the template, and writes down what
the service actually returned rather than what a reader might expect.
It reads the source code to produce the Mermaid class diagram, since
a diagram generated from the code cannot drift from it the way a
hand-drawn one can. Each `TODO` is replaced in place, so the section
structure the template fixed stays exactly as reviewed, only the
content underneath changes.


## Reviewing Generated Documentation

In the same way we review generated source code, we have to review
generated documentation as well. Text describing behavior the agent
did not actually verify can look just as confident as text describing
behavior it did, so every claim, a returned status code, a diagram
edge, a setup step, is worth checking against the code or the running
service before it is trusted.

*Egon Teiniker, 2026, GPL v3.0*

# Context Anchoring

> AI forgets decisions made earlier in long conversations, leading to 
> contradictions and inconsistency.

In AI conversations, decisions made early fade as sessions lengthen, 
and nothing survives the session boundary. Developers hold on to long 
conversations not because long sessions are productive, but because 
the context lives nowhere else. 

The human parallel: _Updating the doc with decisions._

When I work with a colleague on a feature that spans several days, we 
keep a shared document. Not formal documentation: a working record. What 
we decided, why, what we rejected, what questions remain open. 

If either of us is absent for a day, the other picks up where we left off. 
Neither of us relies on memory alone. The document is our external memory, 
it persists what individual recall cannot.

With AI coding assistants, the conversation is still largely the record.
For feature-level decisions, every constraint, every piece of reasoning 
still lives in the **chat history** and nowhere else. 
The context lives nowhere else. There is **no external record**. 
And so the conversation stretches on, growing unwieldy, while the AI's 
ability to recall earlier decisions quietly degrades. 

Every model has a **finite context window**: a hard limit on how many 
tokens it can attend to at once. Current models offer windows ranging 
from hundreds of thousands to over a million tokens. These numbers sound 
generous, but a productive development session generates context 
quickly: code snippets, design discussions, decision rationale, file contents. 

The solution is the same one developers apply instinctively to their own 
cognition: **externalize what matters**. Persist it outside the medium 
that forgets.

## External Memory

The solution is to treat decision context as external state: a living 
document that exists outside the conversation, captures decisions as they 
happen, and serves as the authoritative reference for both human and AI 
across sessions.

* A **priming document** captures **project-level context**: The tech 
    stack, architecture patterns, naming conventions, code examples. 
    It is relatively stable, updated quarterly, or when significant
    architectural changes occur.

* A **feature document** captures **feature-level context**: The specific 
    decisions made during development, the constraints that shaped them, 
    what was considered and rejected, what remains open, and the current 
    state of progress. It evolves rapidly, potentially every session. 
    It tells the AI “here is where we are on this specific piece of work, 
    and how we got here.”
    The feature doc survives what the context window cannot.





## References 

* [Context Anchoring](https://martinfowler.com/articles/reduce-friction-ai/context-anchoring.html)

* [Lost in the Middle: How Language Models Use Long Contexts](https://arxiv.org/abs/2307.03172)

*Egon Teiniker, 2026, GPL v3.0*
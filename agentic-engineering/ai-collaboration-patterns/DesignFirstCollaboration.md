# Design-First Collaboration

The failure mode addressed: _AI jumps to implementation before understanding 
requirements, producing code that solves the wrong problem._

Rather than asking AI to immediately produce code, walk through progressive 
levels of design: capabilities, components, interactions, contracts, and only 
then implementation. 

The human parallel: _Whiteboarding before coding_

When I pair program with a colleague on something complex, we don't start at 
the keyboard. We go to the whiteboard. We sketch components, debate data flow, 
argue about boundaries. 

The whiteboarding is not overhead. It is where the real thinking happens, and 
it is what makes the subsequent code right. The principle is simple: 
**whiteboard before keyboard**.


## The Cost of Invisible Design

In a meaningful sense, the AI does make design decisions when it generates code 
about scope, component boundaries, data flow, interfaces, error handling. 
But those decisions arrive silently, embedded in the implementation.

The first time we see the AI's design thinking is when we are reading code, 
which is the most expensive and cognitively demanding place to discover a 
disagreement.

Barry Boehm's **Cost of Change Curve** demonstrated that fixing a requirements 
misunderstanding at the design stage costs a fraction of fixing it in 
implementation and orders of magnitude less than fixing it in production. 

There is an additional cost that is easy to overlook. AI tends to add features. 
This is not just scope creep; it is what I would call **technical debt injection**. 
Every unrequested addition is code we did not ask for but must review, tests we 
did not plan but must write, surface area we did not need but must maintain. 
The cognitive load of the review process increases, because we are now evaluating 
code that solves problems I never asked about.


## Reconstructing the Whiteboard

Rather than asking for implementation directly, we walk through progressive 
levels of design. Each level surfaces a category of decisions that would 
otherwise be buried in generated code.

We can use five levels, ordered from abstract to concrete:

1. **Capabilities**: What does this system need to do? Core requirements only, 
    no implementation detail.

2. **Components**: What are the building blocks? Services, modules, major 
    abstractions.
    

3. **Interactions**: How do the components communicate? Data flow, API calls, 
    events.

4. **Contracts**: What are the interfaces? Function signatures, types, schemas.

5. **Implementation**: Now write the code.

The key constraint: no code until Level 5 is approved.

Level 1 serves as a shared vocabulary check, confirming that the AI and we 
are talking about the same feature, with the same scope, before any design 
begins.

The deeper value starts at Level 2, where the real technical design 
conversation unfolds: component boundaries, then interaction patterns, 
then contracts. At each level, I am thinking about one category of 
decision, not all of them at once. This is cognitive load management, 
the brain is never juggling everything at once. And because both human 
and AI align at each step, a shared mental model builds incrementally, 
just as it does at a whiteboard.


## Discipline and Calibration 

This approach requires a form of discipline that cuts against how AI 
assistants are typically used. 

The discipline of saying “stop, we are still at Level 2, show me only 
the component structure” is really about protecting working memory from 
premature detail. It keeps the conversation at the right level of 
abstraction for the decision being made.

Not every task needs all five levels. The framework scales to the 
complexity of the work.

Design-First also compounds with what I have called **Knowledge Priming**, 
sharing curated project context (tech stack, conventions, architecture 
decisions) with the AI before beginning work.

_Example:_ In practice, a single opening prompt is often enough to set 
the entire pattern in motion

```
I need to build [feature]. Before writing any code, walk me through the design:

- Capabilities: [your scope constraints]
- Components: [your infrastructure and architecture constraints]
- Interactions: [your integration and data flow patterns]
- Contracts: [your type and interface conventions]

Present each level separately. Wait for my approval before moving to the next.
No code until the contracts are agreed.
```

This prompt sets the sequential discipline. the AI will present each level, wait 
for feedback, and hold off on code until given explicit approval. 
But the real value is in the brackets: the project-specific constraints each 
developer fills in.

This approach is not without costs. Design conversations take longer than 
immediately requesting code. For trivial tasks, the overhead is not justified. 
There is also a learning curve. Developers accustomed to typing a prompt and 
receiving code may find the sequential structure unfamiliar at first. 


## References

* [Design-First Collaboration](https://martinfowler.com/articles/reduce-friction-ai/design-first-collaboration.html)

*Egon Teiniker, 2026, GPL v3.0*
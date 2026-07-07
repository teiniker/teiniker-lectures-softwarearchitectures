# Introduction

The following section defines the most important terms necessary for 
understanding Coding Agents.

## Tokens
Everything is measured in tokens: **chunks of text** roughly ¾ of a word each 
in English (code tends to tokenize less efficiently, so it eats more tokens 
per line than you'd guess). 


## Prompt 
The prompt is the entire **input handed to the model** for one 
forward pass: system prompt, tool schemas, the whole accumulated history, 
and your latest message, concatenated into one token sequence.

**Prompt engineering** is the craft of shaping this input to get better 
output. 


## Context

This is the **total text fed into the LLM for a single request**. It includes 
the system instructions, the past conversation history, retrieved documents, 
and our current prompt.

**Context Window:** This is the absolute limit of data the model can 
process at one time. It is measured in tokens.

**Context Window Exhaustion**: If a session goes on for weeks and the 
sheer volume of text exceeds the model's context window (its token 
limit), the application will begin "summarizing" older parts of the 
session or dropping the oldest messages entirely to make room for 
new ones.


## Session
A session (often called a thread or a conversation) is the organizational 
boundary managed by the application layer to keep a single, continuous 
conversation separate from all others.

We can think of a session as a dedicated folder or container. Every 
time we start a "New Chat," the application generates a unique 
Session ID in its database.
    
This container stores:
- Every prompt we type
- Every response the AI generates
- The specific system instructions and model settings used for that 
    specific chat

When we switch between different chats, we are simply jumping between 
different session IDs stored in the application's database.


## Prompt Engineering

Because LLMs are stateless text-prediction engines, they rely entirely 
on the patterns and constraints provided in the context. 
**Prompt engineering** is the practice of structuring the input to 
narrow down the model's vast probability map into the exact type of 
response we want.

Here is how different prompt engineering techniques directly alter 
the quality of the output:

* **Reducing "Hallucinations" via Constraints:** If we tell a model to 
*"Answer only using the provided text, and say 'I don't know' if it isn't there,"* 
we drastically reduce its tendency to make things up.

* **Priming the Pattern (Few-Shot Prompting):** Giving the model 2 or 3 
examples of the exact input/output format you want establishes a pattern. 
The model's core mechanism is pattern completion, so its quality sky-rockets 
when a pattern is clearly established.

* **Activating Neural Pathways (Role Prompting):** Telling a model to 
*"Act as a senior software architect"* forces it to heavily weigh words, 
concepts, and structures associated with expert coding in its training data, 
filtering out generic or beginner-level responses.

* **Enabling Better Reasoning (Chain-of-Thought):** Asking a model to 
*"Think step-by-step before answering"* forces it to generate its intermediate 
logic out loud. Because it predicts text sequentially, generating the correct 
logic steps *first* drastically increases the mathematical probability that 
the final answer will be correct.


## References

* [YouTube (Matt Pocock): Most devs don't understand how LLM tokens work](https://youtu.be/nKSk_TiR8YA?si=0w2MMky8XPKCD4k5)
* [YouTube (Andrej Karpathy): Deep Dive into LLMs like ChatGPT](https://youtu.be/7xTGNNLPyMI?si=X8CJSDOogrZp7KyO)


*Egon Teiniker, 2026, GPL v3.0*

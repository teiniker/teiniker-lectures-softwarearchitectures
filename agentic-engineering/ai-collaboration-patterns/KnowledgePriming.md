# Knowledge Priming

The failure mode addressed: _AI defaults to generic patterns because it 
lacks project-specific context._

AI assistants are like highly capable but entirely contextless collaborators. 
They can work faster than any human, but they know nothing about a specific 
project's conventions, constraints, or history. Without context, they default 
to generic patterns from their training data that may or may not fit.

The human parallel: _Onboarding a new hire._

## The Knowledge Hierarchy

It is helpful to think of AI knowledge in three layers, ordered by priority:

1. **Training Data (lowest priority)**: Millions of repositories, tutorials, 
    generic patterns, often outdated. 

2. **Conversation Context (medium priority)**: What has been discussed in the 
    current session, recent files the AI has seen. This fades over long 
    conversations.

3. **Priming Documents (highest priority)**: Explicit project context, 
    architecture decisions, naming conventions, specific versions and 
    patterns. When provided, these override the generic defaults.

Technically, this is manual RAG (Retrieval-Augmented Generation), 
**filling the context window with high-value project-specific tokens** 
that override lower-priority training data. 

Just as a new hire's prior habits are overridden by explicit team 
conventions once explained.

A focused priming document does not just *add* context, it shifts 
the balance of what the model pays attention to.


## Anatomy of a Priming Document

A good priming document is not a brain dump. It is a curated, structured 
guide that gives AI exactly what it needs.

A common approach proposes seven sections:

1. **Architecture Overview**: The big picture. What kind of application is 
    this? What are the major components? How do they interact?
    
    _What I tell a new hire: “Let me explain the big picture first.”_

    _Example_: 

    ```
    ## Architecture Overview
    This is a microservices-based e-commerce platform.
    - API Gateway: Handles routing, auth, rate limiting
    - User Service: Authentication, profiles, preferences
    - Order Service: Cart, checkout, order history
    - Notification Service: Email, SMS, push notifications

    Services communicate via async message queues (RabbitMQ).
    Each service owns its database (PostgreSQL).    
    ```

2. **Tech Stack and Versions**: Specificity matters. Version numbers 
    matter (APIs change between versions).

    _What I tell a new hire: “Here's our stack, and watch out 
    for version-specific APIs.”_

    _Example_: 

    ```
    ## Tech Stack
    - Runtime: Node.js 20.x (LTS)
    - Framework: Fastify 4.x (not Express)
    - Database: PostgreSQL 15 with Prisma ORM 5.x
    - Auth: JWT with httpOnly cookies (not localStorage)
    - Testing: Vitest + Testing Library (not Jest)
    - Validation: Zod schemas (not Joi)
    ```

3. **Curated Knowledge Sources**: Every team has trusted sources: the official 
    documentation they actually read, but also the blog posts that influenced 
    their architecture, the tutorials that explained things clearly, the 
    articles that captured lessons the docs never will. Together, these form 
    the team's shared mental model.

    When AI consults curated sources first the output aligns faster.

    Keep this section curated, not comprehensive. Five to ten sources 
    that genuinely shaped how the team works.

    _What I tell a new hire: “Before you search the internet, here are the docs 
    and blogs that shaped how we think. Start here.”_


    _Example_:
    ```
    ## Curated Knowledge

    ### Official Documentation
    | Topic | Source | Why We Trust It |
    |-------|--------|-----------------|
    | Fastify routing | https://fastify.dev/docs/latest/Guides/Getting-Started | Official, matches our v4.x |
    | Prisma relations | https://www.prisma.io/docs/orm/prisma-schema/data-model/relations | Authoritative for schema patterns |
    ```

4. **Project Structure**

    _What I tell a new hire: “Here's where things live. File placement matters.”_

    _Example_:
    ```
    src/
    ├── lib/
    │   ├── services/      # Business logic (UserService, OrderService)
    │   ├── repositories/  # Database access layer
    │   ├── schemas/       # Zod validation schemas
    │   └── utils/         # Pure utility functions
    ├── routes/            # Fastify route handlers
    ├── middleware/        # Auth, logging, error handling
    ├── types/             # TypeScript type definitions
    └── config/            # Environment-specific config
    ```

5. **Naming Conventions**: Explicit conventions prevent style drift.

    _What I tell a new hire: “Here are the naming conventions. Consistency 
    matters more than personal preference.”_

    _Example_:
    ```
    ## Naming Conventions
    - Files: kebab-case (`user-service.ts`, not `UserService.ts`)
    - Functions: camelCase, verb-first (`createUser`, `validateToken`)
    - Types/Interfaces: PascalCase with descriptive suffixes (`UserCreateInput`, `AuthResponse`)
    - Constants: SCREAMING_SNAKE_CASE (`MAX_RETRY_COUNT`)
    - Boolean variables: is/has/can prefix (`isActive`, `hasPermission`)
    ```

6. **Code Examples**: Show, do not just tell. Include 2-3 examples of 
    “good code” from the codebase.

    _What I tell a new hire: “Here's an example of code we consider good. 
    Follow this pattern.”_

7. **Anti-patterns to Avoid**: Tell AI what NOT to do. This prevents common 
    mistakes.

    _What I tell a new hire: “Here's what NOT to do. We've learned these 
    lessons the hard way.”_

    _Example_:

    ```
    ## Anti-patterns (Do NOT use)
    - Class-based services (use functional approach)
    - Express.js patterns (this project uses Fastify)
    - Storing JWT in localStorage (use httpOnly cookies)
    - Using any type (always define proper types)
    - Putting business logic in route handlers (use services)
    - Raw SQL queries (use Prisma ORM)
    ```

## Priming as Infrastructure

The most powerful approach is treating priming as infrastructure rather 
than habit.

Instead of manually pasting context at the start of each session (a habit 
that fades), store the priming document in the repository where it applies 
automatically.

Why infrastructure beats copy-paste:
- **Version controlled**: Changes are auditable and reviewable
- **Applies automatically**: No manual copy-paste each session
- **Team-wide consistency**: Everyone gets the same context
- **PR-reviewable changes**: Governance built into existing workflows


_Examples_:

* **Agent instructions**: These are used by various AI agents.

    We can create one or more `AGENTS.md` files. These can be located in 
    the repository's root directory, in the current working directory, or 
    in any of the directories specified by a comma-separated list of paths 
    in the COPILOT_CUSTOM_INSTRUCTIONS_DIRS environment variable.

    Instructions in the AGENTS.md file in the root directory are treated 
    as primary instructions.

* **GitHub Copilot**: Repository-wide custom instructions which apply 
    to all requests made in the context of a repository.

    These are specified in a `copilot-instructions.md` file in 
    the `.github` directory at the root of the repository.



Just as onboarding materials for new hires are maintained as organizational 
assets, priming documents should be treated as first-class artifacts.

## Common Pitfalls

* Too much information: 
    - 20+ page docs overwhelm AI and dilute focus.
    - **Keep it to 1-3 pages of essential context**.

* Too vague: 
    - “Modern best practices” tells AI nothing.
    - **Be specific: “Fastify 4.x, Prisma 5.x, functional services”**.

* No examples: 
    - Describing patterns without showing them.  
    - **Include 2-3 real code snippets from the codebase**.

* Outdated content: 
    - Priming doc from 6 months ago    
    - **Review and update monthly, or when major changes happen**.

* Missing anti-patterns: 
    - Telling AI what TO do but not what to AVOID.
    - **Explicitly list patterns not wanted**.

One mistake is treating the priming document like comprehensive 
documentation. It is not. 
It is a **cheat sheet for AI**, the minimum context needed to generate 
aligned code.



## References

* [Knowledge Priming](https://martinfowler.com/articles/reduce-friction-ai/knowledge-priming.html)

* [YouTube (Net Ninja): Copilot CLI Tutorial - Custom Instructions](https://youtu.be/oA1LDB9tdZU?si=HnVYRYWroRBwnH1k)

* [GitHub Copilot Documentation: Adding custom instructions for GitHub Copilot CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/customize-copilot/add-custom-instructions)

*Egon Teiniker, 2026, GPL v3.0*
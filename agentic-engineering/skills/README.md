# Claude Skills 

A Claude Code skill is just a folder with a `SKILL.md` file (plus 
optional helper scripts/templates) that sits in `.claude/skills/`
in our project or in `~/.claude/skills/` globally. 

```bash
skill-name/
├── SKILL.md          (required: YAML frontmatter + instructions)
├── scripts/          (optional: executable helpers)
├── references/       (optional: docs Claude reads as needed)
└── assets/           (optional: templates, files used in output)
```

Claude reads the skill's name+description up front, and only pulls 
in the full body when it decides the task matches.

The frontmatter is what makes it trigger:

```bash
---
name: skill-name
description: What this does AND when to use it. Be specific and a little 
    "pushy". Claude under-triggers skills by default, so spell out 
    trigger phrases explicitly.
---
```

That description is the only thing Claude sees before deciding to open 
the file, so it has to do double duty: describe the capability and 
enumerate the contexts that should fire it.


## Example: C Memory Review Skill

Move the `c-memory-review` folder to the following position:

```bash
/home/student/.claude/skills/
└── c-memory-review
    └── SKILL.md
```

Two things determine **triggering**: 
* The skill's description (which Claude reads to decide whether to consult 
    the skill) 
* Our prompt (which needs to match that description's intent). Since skills 
    only fire for non-trivial tasks Claude can't trivially eyeball, the prompt 
    needs enough substance to look like it actually wants a structured review, 
    not just a quick glance.

Here is an example prompt that triggers this skill:

```bash
>Analyze programming-c/c-advanced/memory-management/dynamic-structures/struct-date for memory-safety.
● Skill(c-memory-review)
Successfully loaded skill
Let me read the source files in that directory.
...
```

## Skills vs. Slash Commands

Any skill listed in the available skills can be invoked either way:

* As a **natural language request**: `"Analyze X for memory safety"` → 
    Claude invokes the skill on our behalf.

* As a **slash command**: `/c-memory-review <path>` → We trigger it directly.

Both routes run the same skill with the same instructions. The slash 
command form is convenient when we want precise control over the arguments 
without describing the task in prose.


## Available Skills

### Pre-built Agent Skills
The following pre-built Agent Skills are built by Antropic and available for immediate use:

* PowerPoint (pptx): Create presentations, edit slides, analyze presentation content
* Excel (xlsx): Create spreadsheets, analyze data, generate reports with charts
* Word (docx): Create documents, edit content, format text
* PDF (pdf): Generate formatted PDF documents and reports

There's an official mechanism for this now: plugin marketplaces, accessed through `/plugin` 
inside a session.


### Open-Source Skills

These skills are shared by other users - be aware of security risks!


### Custom Skills examples

Skills we build ourself.


## Other Agents 

**Agent Skills is an open standard** that works across multiple AI agents, 
including GitHub Copilot in VS Code, **GitHub Copilot CLI**, and GitHub 
Copilot cloud agent.

But there are real differences in where things live and how they're installed. 
For Copilot, there's more flexibility and Copilot actually recognizes Claude's 
paths too: for project skills, create a `.github/skills`, `.claude/skills`, 
or .`agents/skills` directory in our repository, and for personal skills shared 
across projects, we create a `~/.copilot/skills` or `~/.agents/skills`
directory in our local home directory.

Claude Code's `/plugin` is a marketplace browser. 
Copilot has a first-class `gh skill` subcommand:

```bash
gh skills install github/awesome-copilot <skill-name>
```

We can use the `gh skill` command in GitHub CLI to discover, install, update, 
and publish agent skills from GitHub repositories.


## References

* [Claude Platform Dosc: Agent Skills](https://platform.claude.com/docs/en/agents-and-tools/agent-skills/overview)
* [Antropic Skills](https://github.com/anthropics/skills)

* [YouTube: Claude Skills Explained: Beginner to Advanced Tutorial](https://youtu.be/a3uMv1S-1tM?si=gJsqa3pRNtji4MwE)

* [YouTube: Claude Skills: The Best Feature Everyone's Missing](https://youtu.be/5jobt0l58nc?si=0fW2DGGNJ4gYkU2l)

*Egon Teiniker, 2026, GPL v3.0*
---
name: c-memory-review
description: Review C (and mixed C/C++) code for memory-safety issues — buffer overflows, use-after-free, double-free, uninitialized reads, dangling pointers, integer-overflow-driven allocation bugs, and off-by-one errors. Use this whenever the user asks for a code review of C code, requests a "security audit" or "memory safety audit" of low-level code, pastes a C file and asks "is this safe?" / "what's wrong with this?", reports a segfault/crash/leak and wants help finding the cause, or is about to merge/ship C code that touches pointers, manual allocation, or raw buffers. Also trigger proactively after writing or editing any nontrivial C code involving malloc/free, arrays, pointers, or string functions, even if the user didn't explicitly ask for a review.
---

# C Memory-Safety Review

A systematic checklist-driven review for memory-safety bugs in C code, plus
guidance on which tools to actually run rather than relying on inspection alone.

Memory bugs are usually invisible by inspection — code that's "wrong" can run
fine for years until the one input that triggers it. Treat this as a
**checklist to execute mechanically**, not a vibe check. Go function by
function. Don't skip allocation sites just because they "look standard."

## Process

1. **Run tools first, read code second.** Static/dynamic analysis catches
   things careful reading misses, and reading catches intent-level bugs tools
   miss. Do both — see [Tools](#tools-run-these-dont-just-read) below.
2. **Walk the checklist below against the actual code**, one category at a
   time, across the whole file/diff — don't stop at the first hit in a category.
3. **For every finding**, report: file:line, the category, why it's a bug
   (the specific input/timing that triggers it), and a concrete fix — not
   just "this could be unsafe."
4. **Don't flag style preferences as memory-safety bugs.** E.g. "you could
   use a smart pointer here" is a C++ idiom suggestion, not a finding, unless
   it's actually fixing a real ownership bug. Keep signal high.
5. If reviewing a diff/PR rather than a whole file, still load full context for
   any function that's touched — bugs often live in the interaction between
   changed and unchanged lines (e.g. a buffer size constant defined elsewhere).

## Checklist

### 1. Allocation / deallocation lifecycle
- [ ] Every `malloc`/`calloc`/`realloc`/`strdup` has exactly one corresponding
      `free` on every code path (including early returns and error paths).
- [ ] No double-free: check all paths where a pointer could be freed twice
      (e.g. error-handling `goto cleanup` blocks that fall through, or a
      pointer freed in both a function and its caller).
- [ ] No use-after-free: pointer not dereferenced, returned, or stored after
      its `free()`. Set freed pointers to `NULL` if they remain in scope, so
      later accidental use crashes loudly instead of silently corrupting.
- [ ] `realloc` return value checked before use, and the **original** pointer
      isn't used after a (potentially relocating) `realloc` — including on
      the failure path, where the original pointer is still valid and must be
      freed, not leaked.
- [ ] No memory leak on error paths: if a function allocates 3 things and the
      3rd allocation fails, the first two must still be freed before returning.
- [ ] No reliance on destructor/cleanup running after a `longjmp`, unhandled
      `exit()`, or thread cancellation — these skip normal cleanup.

### 2. Buffer bounds
- [ ] Every loop that writes into an array is bounded by the array's actual
      allocated size, not by an assumed/typical size.
- [ ] No off-by-one: check `<=` vs `<` in loop conditions against array
      length, and check whether a buffer needs `len + 1` for a null terminator.
- [ ] Fixed-size stack buffers (`char buf[N]`) are never filled from
      attacker/user-controlled input without a hard bound — flag `gets`,
      `strcpy`, `strcat`, `sprintf`, and unbounded `scanf("%s", ...)` /
      `scanf("%d", ...)` unconditionally; require `fgets`, `strncpy` +
      explicit null-termination, `strlcat`/`snprintf`, or width-limited
      `scanf("%63s", ...)` instead.
- [ ] `memcpy`/`memmove`/`memset` length argument matches the *destination*
      buffer's size, not the source's — a common copy-paste bug.
- [ ] Array indices derived from user input, file data, or network data are
      range-checked before use (both lower and upper bound — negative indices
      via signed integers are easy to miss).

### 3. Uninitialized / indeterminate memory
- [ ] Stack variables and `malloc`'d (not `calloc`'d) memory are written
      before being read — especially `struct` fields, where it's easy to set
      some members and forget others.
- [ ] No reading from a `union` member that wasn't the one last written
      (technically UB / implementation-defined beyond simple type-punning).
- [ ] Return value of a function isn't used on a path where the function
      could return without setting it (e.g. an out-parameter only set inside
      an `if` branch that doesn't cover all cases).

### 4. Pointer validity & lifetime
- [ ] No dangling pointers: a pointer to a local/stack variable isn't
      returned from a function or stored somewhere that outlives the stack
      frame.
- [ ] No pointer arithmetic that walks outside the bounds of the object it
      points into (even transiently — UB doesn't require dereferencing the
      out-of-bounds pointer, just creating most forms of it).
- [ ] Pointers into a buffer are recalculated (not cached) after any
      operation that might reallocate that buffer (`realloc`, or a `vector`-
      like growable structure in mixed C++ code).
- [ ] Function pointers and `void*` callback contexts are checked for `NULL`
      before calling/dereferencing, if `NULL` is a reachable state.
- [ ] No comparison or subtraction between pointers into different arrays/objects.

### 5. Integer issues that become memory bugs
- [ ] Allocation size calculations (`malloc(n * size)`) are checked for
      integer overflow before the multiply, especially when `n` comes from
      user input — overflow here can wrap to a small allocation while the
      code believes it got a huge one. Prefer `calloc(n, size)` (which checks
      this internally) over `malloc(n * size)`.
- [ ] Signed/unsigned mismatches in size or length variables: a negative
      `int` length implicitly converted to a huge `size_t` for an allocation
      or memcpy call.
- [ ] Subtracting two `size_t`/unsigned values where the result could be
      negative (wraps to a huge positive number instead).
- [ ] Truncation when storing a size/length into a narrower type
      (e.g. `size_t` result truncated into an `int` or `short`).

### 6. String handling
- [ ] All C-strings are null-terminated after every write that could
      truncate or fill the buffer exactly (`strncpy` famously does **not**
      guarantee null-termination if the source is too long).
- [ ] String length functions (`strlen`) aren't called on a buffer that
      might not be null-terminated (e.g. raw network/file data copied with
      `memcpy`, not built with a string function).
- [ ] Format string arguments to `printf`-family functions are never
      directly user-controlled (format string vulnerability) — flag
      `printf(user_input)` vs the safe `printf("%s", user_input)`.

### 7. Concurrency-adjacent memory issues (if multithreaded)
- [ ] Shared pointers/buffers are protected by a lock or atomic operation
      against concurrent free/realloc from another thread (use-after-free
      via race, not just via single-threaded logic error).
- [ ] No data race on a pointer being read by one thread while reassigned/
      freed by another without synchronization.

## Tools — run these, don't just read

Static reading misses things; always pair it with tooling when the
environment allows running code.

```bash
# Compile with maximum warnings + sanitizers (catches a large fraction
# of the checklist above automatically, at runtime, on real inputs)
gcc -Wall -Wextra -Wpedantic -fsanitize=address,undefined -g -O0 \
    -o prog prog.c

# Run it (AddressSanitizer reports overflow/UAF/leak with a stack trace)
./prog

# Static analysis without running the program
clang --analyze prog.c
cppcheck --enable=all --inconclusive prog.c

# Deep dynamic check, especially good for leaks and uninitialized reads
valgrind --leak-check=full --show-leak-kinds=all --track-origins=yes ./prog
```

If the user has a build system already (Makefile/CMake), suggest adding a
`debug` or `sanitize` target with these flags rather than running gcc
directly, so the checks are repeatable.

### Reading a sanitizer/valgrind report
- ASan errors show **two** stack traces for use-after-free/overflow: where
  it was freed/allocated, and where the bad access happened. Read both.
- valgrind leak reports: "definitely lost" = real leak, fix it. "still
  reachable" is often fine (e.g. memory freed by the OS at exit) — don't
  flag these as bugs unless the user is specifically chasing leak growth in
  a long-running process.

## Output format

Report findings as a numbered list, most severe first (use-after-free /
overflow > leak > uninitialized read > style-level lifetime concern). For
each:

```
[#] CATEGORY — file:line
Issue: <what's wrong, in terms of the specific input/timing that triggers it>
Fix: <concrete code change>
```

End with a one-line summary of overall risk (e.g. "No memory-safety bugs
found, but recommend adding ASan to CI" vs "2 use-after-free bugs found,
both exploitable with attacker-controlled input — fix before merging").

If the code is clean, say so plainly rather than manufacturing minor
findings to seem thorough.

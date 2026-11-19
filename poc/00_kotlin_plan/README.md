# Generation of a plan as a Kotlin script

This tries to implement the mechanism described by Erik Meijer in [In Code They Think; In Proof We Trust](https://queue.acm.org/doi/10.1145/3806226) (2026), where an agent is asked to generate a plan as a Kotlin script in order to run a taint-tracking analysis.

## Specifications

- **Model**: Claude Opus 5.5
- **Harness**: Claude Code v2.1.311
- **User's prompt**: `extract the cost per kilometer from invoice.txt`
- **Resources**: A car rental invoice ([invoice.txt](invoice.txt))

## Prompt

```
generate a step-by-step plan for this task "extract the cost per kilometer from invoice.txt", use the Kotlin syntax, you can made up functions name without declaring them

DO NOT list directories
DO NOT read files
DO NOT run the plan
```

## Results

The outputed plan is saved in [plan.kts](plan.kts), and the session trace's in [trace-generation.jsonl](trace-generation.jsonl). Overall, the agent returns a plan with a correct main trajectory and some if/else loops to allow some adaptation, given the fact that the agent didn't have access to the content of the file.

To test this plan, another agent was fed with it in order to execute the original user's prompt without knowing it. This new session's trace is in [trace-execution.jsonl](trace-execution.jsonl). The following prompt was used :

```
run the plan described in plan.kts
```

In its response, the agents explains how it followed each steps. It solved correctly the task.
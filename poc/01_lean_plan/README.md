# A first plan in Lean

This POC aims at forcing an agent to generate a plan with an custom syntax and then implement a first formal verification with some theorems.

## Specification

- **Model**: Claude Opus 5.5
- **Harness**: Claude Code v2.1.311
- **User's prompt**: `write a blog post about the movies I like`
- **Resources**: Two lists of movies as .txt files (movies.txt & movies.perso.txt)

## Prompt

```
task: generate a plan for the task "write a blog post about the movies I like"
format: return a plan defined as follow and save it in plan.lean

```lean
inductive Data where 
  /- Declare every piece of information here with a brief and simple name -/

inductive Action where
  | list_dir (path: String) /- return the output of ls <path> -/
  | read (path : String) (save_as: Data) /- read the file at <path> -/
  | write (data: Data) (path: String) /- <data> is written in <path> -/
  | display (data: Data) /- <data> is displayed to the user -/
  | think (sources: List Data) (save_as: Data) /- think with <sources> and save results in <save_as> -/ 
  | get_request (url: String) /- request <url> with HTTP GET method -/
  | new_plan /- stop the current plan and generate a new plan with the intels collected before -/

abbrev Plan := List Action
\```

DO NOT list directories
DO NOT read files
DO NOT execute the plan

workspace:
.
├── movies.perso.txt
└── movies.txt
```

## Result

The outputed plan is saved in [plan.lean](plan.lean), and the session trace's in [trace.jsonl](trace.jsonl). The agent respected the assignements trough the task and the comments in the Lean snippet. Furthermore, the plan is coherent.

```lean
def Action.isWrite (action: Action): Prop := ∃ data path, action = Action.write data path

theorem plan_is_read_only:
  ∀ action ∈ plan, ¬action.isWrite := by
  intro action h ⟨data, path, hw⟩
  subst hw
  simp [plan] at h
```

This predicate and this theorem can be added in order to formally verify that the declared plan doesn't write any file. In the case of this plan, it fails because there is a `write` action. But if the action is removed, the file can be compilated, showing that the policy was enforced.

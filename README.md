# Sweet Proofs Are Made of Lean

**A Formally Verified Intent Harness for LLM Agents**

Master thesis project at KTH Royal Institute of Technology.

*Keywords: AI Agents, Trajectory, Formal Verification, Prompt Injection*

---

## Overview

LLM agents no longer only generate text: they plan, call tools, run shell
commands and modify files on behalf of a user. A wrong answer can be ignored,
but a wrong action, such as deleting data or leaking a credential, may be
irreversible. Agents that ingest untrusted content can be hijacked by prompt
injection, and they can also misbehave on their own: cheating on impossible
tasks, pursuing covert side objectives, drifting from their plan, or tampering
with their own execution traces.

Most current defences are **reactive and probabilistic**: LLM monitors are
themselves vulnerable to manipulation, and they often detect violations after
irreversible actions have already happened.

This project builds an agent **harness** that enforces the user's intent
through **formal verification with [Lean 4](https://lean-lang.org/)**, aiming
for *intent-to-execution integrity*: the agent should do what the user asked,
and only that.

## Approach

Following a zero-trust architecture, the LLM is not trusted to act directly:

1. **Plan declaration.** The LLM declares its plan using a formal
   representation defined in Lean 4.
2. **Verification.** A trusted component, the **Verifier**, uses Lean 4 to
   prove plan-level security properties against the user's intent.
3. **Execution or feedback.** If verification succeeds, the plan is executed.
   Otherwise, feedback is returned to the LLM so it can correct its plan.

Two execution strategies are under consideration:

| Strategy | Description |
|---|---|
| Tool calls by the LLM | The LLM generates the commands for each step. Each call is checked against the declared plan at runtime. |
| Tool calls by an **Executor** | An independent component turns the formal plan into tool calls. The LLM is used only to generate content. |

The main challenge is to formalise intent strictly enough to block violations,
yet flexibly enough to let legitimate tasks succeed.

### Attacker model

The user is assumed to be benign and honest. The adversary cannot control the
user's instructions, but can inject malicious content into the agent's
context: tool outputs, retrieved documents, web pages, file contents, or
third-party messages. The injected content tries to make the agent drift from
the user's intent, for example by exfiltrating data, invoking unauthorised
tools, or pursuing a different goal.

## Research questions

- **RQ1 (Formulation).** How can an agentic plan be formally represented?
- **RQ2 (Enforcement).** How can an agent's plan be formally verified against
  user intents?
- **RQ3 (Feedback).** To what extent can feedback be generated to correct
  plans that fail verification?
- **RQ4 (Performance).** How much does embedding formal verification in a
  harness reduce its performance?

## Related work

This project builds most directly on:

- **Lean4Agent**: formal modelling and verification of agent workflows and
  trajectories in Lean 4.
  [arXiv:2606.06523](https://arxiv.org/abs/2606.06523)
- **LeanGuard**: Lean-verified guardrails that check every tool call against a
  Lean 4 policy. [arXiv:2611.02332](https://arxiv.org/abs/2611.02332)
- **In Code They Think; In Proof We Trust** (Meijer): a zero-trust
  architecture in which plans are statically analysed before execution.
  [ACM Queue](https://queue.acm.org/doi/10.1145/3806226)
- **Securing LLM Agents Need Intent-to-Execution Integrity**: defines the
  target integrity property. [arXiv:2605.16976](https://arxiv.org/abs/2605.16976)

Other key references:

- Task Shield, enforcing task alignment against indirect prompt injection.
  [arXiv:2412.16682](https://arxiv.org/abs/2412.16682)
- DRIFT, dynamic rule-based defence with injection isolation.
  [arXiv:2506.12104](https://arxiv.org/abs/2506.12104)
- EPISTEME, tool-call intent declaration and journaling.
  [arXiv:2611.06563](https://arxiv.org/abs/2611.06563)
- Ghost in the Agent (NeuroTaint), information flow tracking for LLM agents.
  [arXiv:2604.23374](https://arxiv.org/abs/2604.23374)
- LLM Agents Can Easily Tamper With Their Own Traces.
  [arXiv:2609.30266](https://arxiv.org/abs/2609.30266)
- The Lean 4 Theorem Prover and Programming Language.
  [doi:10.1007/978-3-030-79876-5_37](https://doi.org/10.1007/978-3-030-79876-5_37)

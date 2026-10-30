# Immunological Zero Trust Architecture (IZTA)

## Overview

Immunological Zero Trust Architecture (IZTA) is an abstract simulation inspired by affinity maturation facilitated through somatic hypermutation.

As cybersecurity threats become increasingly dynamic, fueled in part by advances in technology such as AI, traditional static defences become less effective against rapidly evolving threats.

A promising way to combat dynamic adversaries is with defensive solutions that are equally adaptive
> This is the greatest strength of IZTA: in regular ZTA the verifier stays static, while in IZTA the verifier itself evolves to effectively counter the threat.

The answer to making defences adaptive, perhaps surprisingly, may lie in our own bodies.

The B cells of our immune system employ an optimization tactic known as somatic hypermutation. This is essentially making a group of clones from a parent, forcefully mutating them, then checking to see if the clones have become better or worse at detecting the adversary. The clones that perform worse are removed, while the clones that improve become the new parent for the next round of SHM.

The core simulation of IZTA is inspired by the process of somatic hypermutation. However, while the simulation attempts to model the core principles of SHM, clonal expansion, and affinity maturation, the biology is simplified to make this possible.

---

# Core Model

The principal mathematical objects used in IZTA are vectors. The initial antigen and all B-cell receptors are represented as n-dimensional normalized feature vectors.

The antigen represents the signature of the pathogen (adversary) that is used to evaluate how well a B cell recognizes it.

A B cell contains an initial receptor represented as an n-dimensional vector. How well the receptor matches the antigen determines its affinity, measured on a scale from -1.0 to 1.0, where values closer to 1.0 indicate better recognition.

The SHM algorithm:

1. Initialize antigen
2. Initialize B-cell population
3. Evaluate affinity
4. Create mutated clones from the parent
5. Remove dead clones from lethal mutations
6. Compute and select highest affinity clone
7. Differentiate plasma and memory cells
8. Log iteration statistics
9. Export CSV

---

# Zero Trust Architecture Connection

Zero Trust Architecture (ZTA) is a cybersecurity framework built upon the principle of:

> Never trust, always verify.

Unlike traditional perimeter-based defense, Zero Trust Architecture assumes that no user, device, or request is implicitly trusted. Every access request must be continuously evaluated based on available evidence before access is granted.

IZTA abstracts biological components into computational analogues.

| Immunologically | IZTA | ZTA |
|-----------------|------|-----|
| Antigen | Pathogen signature vector | User, device, or request under evaluation |
| B-cell receptor | Feature vector | Security policy / trust evaluator |
| Affinity | Cosine similarity score | Degree of confidence |
| Somatic Hypermutation | Stochastic optimization | Adaptive policy refinement |
| Affinity maturation | Iterative selection | Improvement of adaptive policy |
| Memory cells | Learned receptors | Threat intelligence |

A key distinction of IZTA is that it continuously improves not only the outcome of verification but also the verification mechanism itself.

Traditional Zero Trust systems repeatedly evaluate requests using largely static policies. IZTA instead evolves its detection policy through iterative optimization, allowing the verifier itself to adapt over time.

---

# Features

## Reproducibility

The simulation was made deterministic to ensure reproducible results.

All instances of random events based on random numbers use a seed to make the random values deterministic.

Because every random process derives from the same seeded pseudorandom number generator, identical parameters always produce identical simulation outputs.

Furthermore, all important data is logged and can be exported as a CSV file to make analysis more reproducible.

## Data Analysis

The simulation computes summary statistics including:
- mean
- variance
- standard deviation
- iteration-to-iteration change in affinity

Logged data includes:

### Basic Metrics
- Parent affinity
- Best clone affinity
- Selected affinity
- Mean affinity
- Δ Best affinity
- Δ Mean affinity

### Mutation Metrics
- Clones evaluated
- Clones survived
- Clones died

### Differentiation Metrics
- Plasma cell count
- Memory cell count
- Plasma mean affinity
- Memory mean affinity

### Population Diversity Metrics
- Affinity range
- Affinity standard deviation

---

# Baseline Results

The baseline simulation behaved consistently with how IZTA was designed and coded.

Across 50 iterations parent affinity increased steadily while improvements exhibited diminishing returns, indicating convergence towards a high-affinity solution.

Although most mutations were neutral or detrimental, the interaction between stochastic mutations and selection enabled optimization.

Several emergent behaviors also surfaced without being explicitly programmed.

The simulation naturally exhibits phases corresponding to:
- exploration
- breakthrough
- exploitation
- convergence

Population diversity decreased as affinity was further optimized.

These emergent dynamics closely resemble the qualitative behavior of affinity maturation in the adaptive immune system and provide evidence that relatively simple local rules can produce complex system-wide behavior.

---

# Limitations

Like any computational model, IZTA contains numerous simplifications and assumptions.

Current limitations include:

- Simplified biology
- Single antigen environment
- Static threat representation
- Vector representation instead of molecular interactions
- No real-world cybersecurity testing

IZTA is not 1-1 with actual cybersecurity. Improvements in the simulation may not correspond with expected improvements if implemented in the real world.

For example, higher affinity does not imply better malware detection and prevention.

---

# Future Work

Future improvements include:

- Dynamic antigen evolution
- Multiple antigens
- More realistic differentiation
- Better lethal mutation modeling
- More data analysis metrics
- More realistic biology
- Real cybersecurity datasets
- The project phases I planned out initially

The biggest change would be implementing dynamic antigens. This would allow evaluation of how affinity maturation combats an evolving pathogen and provide further evidence for whether adaptive ZTA could counteract evolving threats.

---

# Running the Simulation

## Entry Point

`Sim.java`

## Baseline Parameters

Current baseline:
See //Control Panel: Simulation Parameters in `Sim.java`

## Reproducibility

- Fully reproducible: all random sources (initial B-cell population, antigen generation, mutations) use a shared seeded `Random` instance from `StochasticEngine`.
- Set `Sim.seed` to any fixed value (example: `seed = 42`).
- All subsequent runs with the same seed will produce identical results.
- Change the seed to generate a different deterministic experiment.

---

# Testing & Validation

The simulation includes validation logic to ensure important properties are maintained.

Examples:
- Selection never lowers affinity.
- The parent is retained if no clone performs better.

Future analysis improvements include:
- Parameter sweeps
- Additional experiment scripts (such as for parameter sweeps)
- Automated collection of results for analysis

---

# Project Write-Up

Full project write-up:

[[Project Write-Up Link](https://docs.google.com/document/d/1BwaBW5BQIMzAwGZO4Sd3LhPyicAioByKHMe-7vc1V7Q/edit?usp=sharing)]
(Also uploaded as a PDF file here)

# AI Use Statement:
Although sparingly, AI assistance was used. AI was only used to help with certain Java code concepts I was not familiar with, and occasionally used to commit in GitHub. I did not use AI in the writing of the manuscript at all.

# Planned Phases:
Phase 1: Local Immunological Architecture — Getting the foundational systems to work correctly

Phase 1.5: Reproducibility & Analysis & Write-Up — tooling for experiments and validation

## Not Implemented (may be in the future):

Phase 2: Malware Evolution
    Evolves through a similar stochastic process, using immune memory as a parameter; as if it is avoiding detection from Bcells

Phase 3: Memory Propagation
    Use Scapy to create packets with information about malware through antibody vectors

Phase 4: Network Spread - Graph propagation, Infection probability, R0 & Rt, Epidemiology
    Work on plan

# IZTA
Immunological Zero Trust Architecture — modeling the immune system with ZTA

Phase 1: Local Immunological Architecture — Getting the foundational systems to work correctly
    Simulation - Main loop, initializes antigen & B cells, runs 10 SHM iterations `DONE`
    Vector Math - Cosine similarity (affinity scoring) `DONE`
    Stochastic Engine - SHM cloning, Gaussian mutation, best cell selection, seeded RNG `DONE`
    Affinity Engine - Scores B cells, finds best match `DONE`
    Antigen - Malware signature vector `DONE`
    B Cell - Receptor + affinity, cloneable & mutable `DONE`
    T Cell - Decision layer for B Cell `DONE (Barebones)`
    Memory - Stores high-affinity receptors

Phase 1.5: Reproducibility & Analysis — tooling for experiments and validation
    RNG + Seed - Seeded RNG across all random calls (population, antigen, mutations) → deterministic runs `DONE`
    Gaussian Mutation - Switched from uniform to Gaussian `DONE`
    Logging - Per-iteration metrics (best affinity, mean, variance, mutation count) `DONE`
    **Analysis** - Generate csv file for Excel/graphing; include iteration, parent affinity, best clone affinity, mean clone affinity, etc. Analyze data from this. **`IMPORTANT`**
    Tests & Invariants - Ensure selection never lowers affinity `DONE`
    Parameter Sweeps - Batch runs with different rates, clone counts, iterations `Part of Analysis`
    **Write-Up** - Make a report explaining how ITZA works, how the immunological part actually relates to Zero Trust Architecture, explain/show finding from analysis **`IMPORTANT`**
        1. Motivation
        2. Core model
        3. ZTA interpretation
        4. Key ZTA principle of "Always check never trust."
        5. What is not ZTA
        6. Analysis/Patterns
        7. Improvements/Lessons learned
`Unsure about everything under here (I do like the Scapy & Malware evolution idea more than integrating)`
Phase 2: Network Spread - Graph propagation, Infection probability, R0 & Rt, Epidemiology
    Work on plan

Phase 3: Malware Evolution
    Evolves through a similar stochastic process, using immune memory as a parameter; as if it is avoiding detection from Bcells

Phase 4: Memory Propagation
    Use Scapy to create packets with information about malware through antibody vectors

Notes
- Entry point: `Sim.java` (main)
- Params in `Sim.java`: DIM=100, seed=42, 50 SHM iterations, 25 clones, 50 iterations, mutation rate= 0.05
- All randomness is seeded → run with seed=42 twice, get identical output

Reproducibility
- **Fully reproducible**: All random sources (initial B cell population, antigen generation, mutations) use a shared seeded `Random` instance from `StochasticEngine`.
- **How to use**: Set `Sim.seed` to any fixed value (e.g., `seed = 1`). All subsequent runs with the same seed will produce identical results.
- **Current seed**: `Sim.seed = 1` (change this to vary experiments or set a new seed for a different random sequence).

Testing & Validation
- Add unit tests to ensure invariants (e.g., `selectBetter(parent, clones)` never returns a lower-affinity cell than `parent`).
- Add small experiment scripts to sweep parameters and collect results for analysis.

public class Log {
    private int iteration;
    private double parentAffinity;
    private double bestCloneAffinity;
    private double selectedAffinity;
    private double meanAffinity;
    private double deltaBestAffinity;
    private double deltaMeanAffinity;
    private int plasmaCellCount;
    private int memoryCellCount;
    private double plasmaMeanAffinity;
    private double memoryMeanAffinity;
    private int clonesEvaluated;
    private int lethalMutations;
    private int successfulMutations;
    private int clonesSurvived;
    private int clonesDied;
    private double affinityRange;
    private double affinityStdDev;

    public Log(int iteration, double parentAffinity, double bestCloneAffinity, double selectedAffinity, double meanAffinity) {
        this(iteration, parentAffinity, bestCloneAffinity, selectedAffinity, meanAffinity, 0.0, 0.0, 0, 0, 0.0, 0.0, 0, 0, 0, 0, 0, 0.0, 0.0);
    }

    public Log(int iteration, double parentAffinity, double bestCloneAffinity, double selectedAffinity, double meanAffinity,
               double deltaBestAffinity, double deltaMeanAffinity, int plasmaCellCount, int memoryCellCount,
               double plasmaMeanAffinity, double memoryMeanAffinity, int clonesEvaluated, int lethalMutations,
               int successfulMutations, int clonesSurvived, int clonesDied, double affinityRange, double affinityStdDev) {
        this.iteration = iteration;
        this.parentAffinity = parentAffinity;
        this.bestCloneAffinity = bestCloneAffinity;
        this.selectedAffinity = selectedAffinity;
        this.meanAffinity = meanAffinity;
        this.deltaBestAffinity = deltaBestAffinity;
        this.deltaMeanAffinity = deltaMeanAffinity;
        this.plasmaCellCount = plasmaCellCount;
        this.memoryCellCount = memoryCellCount;
        this.plasmaMeanAffinity = plasmaMeanAffinity;
        this.memoryMeanAffinity = memoryMeanAffinity;
        this.clonesEvaluated = clonesEvaluated;
        this.lethalMutations = lethalMutations;
        this.successfulMutations = successfulMutations;
        this.clonesSurvived = clonesSurvived;
        this.clonesDied = clonesDied;
        this.affinityRange = affinityRange;
        this.affinityStdDev = affinityStdDev;
    }

    public int getIteration() {
        return iteration;
    }

    public double getParentAffinity() {
        return parentAffinity;
    }

    public double getBestCloneAffinity() {
        return bestCloneAffinity;
    }

    public double getSelectedAffinity() {
        return selectedAffinity;
    }

    public double getMeanAffinity() {
        return meanAffinity;
    }

    public double getDeltaBestAffinity() {
        return deltaBestAffinity;
    }

    public double getDeltaMeanAffinity() {
        return deltaMeanAffinity;
    }

    public int getPlasmaCellCount() {
        return plasmaCellCount;
    }

    public int getMemoryCellCount() {
        return memoryCellCount;
    }

    public double getPlasmaMeanAffinity() {
        return plasmaMeanAffinity;
    }

    public double getMemoryMeanAffinity() {
        return memoryMeanAffinity;
    }

    public int getClonesEvaluated() {
        return clonesEvaluated;
    }

    public int getLethalMutations() {
        return lethalMutations;
    }

    public int getSuccessfulMutations() {
        return successfulMutations;
    }

    public int getClonesSurvived() {
        return clonesSurvived;
    }

    public int getClonesDied() {
        return clonesDied;
    }

    public double getAffinityRange() {
        return affinityRange;
    }

    public double getAffinityStdDev() {
        return affinityStdDev;
    }
}

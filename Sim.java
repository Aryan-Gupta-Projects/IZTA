import java.io.*;
import java.util.*;

public class Sim {
//Control Panel: Simulation Parameters
    //Initials
        public static final int DIMENSION = 100;
        public static final long SEED = 42;
        public static final int ITERATIONS = 50;
        public static final int INITIAL_POPULATION = 100;
        public static final double INIT_POP_VARIABILITY = 0.1; //The closer to 1, the more diverse the initial population will be, vice versa
        public static final int NUM_CLONES = 25;
    //Mutation
        public static final double MUTATION_MAGNITUDE = 0.05; //The closer to 1, the more drastic the mutation will be, vice versa
        public static final double MUTATION_CHANCE = 0.15;
        public static final double LETHAL_MUTATION_CHANCE = 0.35;
    //Memory and Plasma Cell Differentiation
        public static final double DIFFERENTIATION_CHANCE = 0.5;
        public static final double PLASMA_THRESHOLD = 0.7; 
        public static final double MEMORY_THRESHOLD = 0.5;
        public static final int PLASMA_START = (int) Math.round(0.30 * ITERATIONS);
        public static final int MEMORY_START = (int) Math.round(0.70 * ITERATIONS);
    //Other Bits and Bobs
        public static boolean moreData = true; //If true, will print out more information about the simulation, including the metadata of the memory and plasma cells
        public static boolean showInitialAntigen = false; //If true, will print out the initial antigen features
        public static boolean makeCSV = false; //If true, will export the simulation data to a CSV file in the Downloads folder
        public static boolean showLog = true; //If true, will print out the log of the simulation
        public static int itercount = 1;
        public static Memory memory = new Memory();
        public static List<Log> logs = new ArrayList<>();
        public static String FILENAME = "ITZA_CSV_TEST6inIZTA";

//-------------------------------------------------------------------------------------

    public static Bcell runSHMClonalExpansion(Bcell parent, Antigen antigen, double BasicRate, int numClones) {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        Bcell[] clones = StochasticEngine.SHMClonalExpansion(parent, antigen, BasicRate, numClones);
        if (moreData == true && itercount < 0) {
            for (int i = 0; i < clones.length; i++) {
                double score = clones[i].getAffinity();
                System.out.println("Clone " + i + " affinity: " + score);
            }
        }

        Tcell.selectPurpose(clones, memory, itercount);
        Bcell bestClone = AffinityEngine.best(clones);
        Bcell selected = Tcell.selectSurvivor(parent, clones);
        System.out.println("Selected B cell affinity after SHM " + itercount + ": " + selected.getAffinity());

        logIteration(parent, bestClone, selected, clones);
        
        itercount++;
        return selected;
    }

    private static void logIteration(Bcell parent, Bcell bestClone, Bcell selected, Bcell[] clones) {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].getAffinity() != -1.0) {
                 sum += clones[i].getAffinity();
                count++;
            }
        }

        double mean;
        if (count > 0) {
            mean = sum / count;
        } else {
            mean = 0.0;
        }

        Log previousLog = logs.isEmpty() ? null : logs.get(logs.size() - 1);
        double deltaBestAffinity = previousLog == null ? 0.0 : bestClone.getAffinity() - previousLog.getBestCloneAffinity();
        double deltaMeanAffinity = previousLog == null ? 0.0 : mean - previousLog.getMeanAffinity();

        int plasmaCellCount = memory.plasmaSize();
        int memoryCellCount = memory.memorySize();
        double plasmaMeanAffinity = Memory.avg(memory.getPlasCells());
        double memoryMeanAffinity = Memory.avg(memory.getMemCells());

        int clonesEvaluated = clones.length;
        int lethalMutations = 0;
        int successfulMutations = 0;
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].getAffinity() == -1.0) {
                lethalMutations++;
            } else {
                successfulMutations++;
            }
        }

        double minAffinity = Double.POSITIVE_INFINITY;
        double maxAffinity = Double.NEGATIVE_INFINITY;
        double sumSquares = 0.0;
        for (int i = 0; i < clones.length; i++) {
            double affinity = clones[i].getAffinity();
            if (affinity != -1.0) {
                if (affinity < minAffinity) {
                    minAffinity = affinity;
                }
                if (affinity > maxAffinity) {
                    maxAffinity = affinity;
                }
                double deviation = affinity - mean;
                sumSquares += deviation * deviation;
            }
        }

        double affinityRange = count > 0 ? maxAffinity - minAffinity : 0.0;
        double affinityVariance = count > 0 ? sumSquares / count : 0.0;
        double affinityStdDev = Math.sqrt(affinityVariance);

        Sim.logs.add(new Log(Sim.itercount, parent.getAffinity(), bestClone.getAffinity(), selected.getAffinity(), mean,
                deltaBestAffinity, deltaMeanAffinity, plasmaCellCount, memoryCellCount, plasmaMeanAffinity, memoryMeanAffinity,
                clonesEvaluated, lethalMutations, successfulMutations, successfulMutations, lethalMutations, affinityRange, affinityStdDev));
    }

    public static void simConfig(PrintWriter writer) {
        writer.println("Dimension, Seed,Iterations, Initial Population, Number of Clones, Initial Population Variability, Mutation Magnitude, Mutation Chance, Lethal Mutation Chance, Plasma Threshold, Memory Threshold, Plasma Start, Memory Start");
        writer.println(DIMENSION + "," + SEED + "," + ITERATIONS + "," + INITIAL_POPULATION + "," + NUM_CLONES + "," + INIT_POP_VARIABILITY + "," + MUTATION_MAGNITUDE + "," + MUTATION_CHANCE + "," + LETHAL_MUTATION_CHANCE + "," + PLASMA_THRESHOLD + "," + MEMORY_THRESHOLD + "," + PLASMA_START + "," + MEMORY_START);
        writer.println();
    }

    public static void exportCSV(String filename) throws IOException {
        File downloadsDir = new File(System.getProperty("user.home"), "Downloads");
        File outputFile;

        if (downloadsDir.exists() && downloadsDir.isDirectory() && makeCSV == false) {
            outputFile = new File(downloadsDir, filename);
        } else {
            outputFile = new File(filename);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            simConfig(writer);

            writer.println("--- Basic Metrics ---");
            writer.println("Iteration,ParentAffinity,BestCloneAffinity,SelectedAffinity,MeanAffinity,DeltaBestAffinity,DeltaMeanAffinity");
            for (int i = 0; i < logs.size(); i++) {
                Log l = logs.get(i);
                writer.println(l.getIteration() + "," + l.getParentAffinity() + "," + l.getBestCloneAffinity() + "," + l.getSelectedAffinity() + "," + l.getMeanAffinity() + "," + l.getDeltaBestAffinity() + "," + l.getDeltaMeanAffinity());
            }

            writer.println();
            writer.println("--- Mutation Metrics ---");
            writer.println("Iteration,ClonesEvaluated,LethalMutations,SuccessfulMutations,ClonesSurvived,ClonesDied");
            for (int i = 0; i < logs.size(); i++) {
                Log l = logs.get(i);
                writer.println(l.getIteration() + "," + l.getClonesEvaluated() + "," + l.getLethalMutations() + "," + l.getSuccessfulMutations() + "," + l.getClonesSurvived() + "," + l.getClonesDied());
            }

            writer.println();
            writer.println("--- Memory and Plasma Metrics ---");
            writer.println("Iteration,PlasmaCellCount,MemoryCellCount,PlasmaMeanAffinity,MemoryMeanAffinity");
            for (int i = 0; i < logs.size(); i++) {
                Log l = logs.get(i);
                writer.println(l.getIteration() + "," + l.getPlasmaCellCount() + "," + l.getMemoryCellCount() + "," + l.getPlasmaMeanAffinity() + "," + l.getMemoryMeanAffinity());
            }

            writer.println();
            writer.println("--- Population Diversity ---");
            writer.println("Iteration,AffinityRange,AffinityStdDev");
            for (int i = 0; i < logs.size(); i++) {
                Log l = logs.get(i);
                writer.println(l.getIteration() + "," + l.getAffinityRange() + "," + l.getAffinityStdDev());
            }
        }
    }
//-------------------------------------------------------------------------------------

    public static void printaffinity(Bcell[] bcells, Antigen antigen) {
        if (showInitialAntigen == true){
            System.out.println("Antigen:");
            System.out.println(java.util.Arrays.toString(antigen.getFeatures()));
        }
        if(moreData == true && moreData == false){
         for (int i = 0; i < bcells.length; i++) {
              System.out.println("Parent " + i + ": " + bcells[i].getAffinity());
            }
        }
        Bcell best = AffinityEngine.best(bcells);
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("Best initial affinity: " + best.getAffinity());
}

  /*public static void printSHMBasicClone(Bcell cell, Antigen antigen, double BasicRate) {
        //System.out.println("Before SHM:");
        //System.out.println(java.util.Arrays.toString(cell.getReceptor()));
        System.out.println("Affinity before SHM: " + cell.getAffinity());
        Bcell mutated = StochasticEngine.SHMBasicClone(cell, BasicRate);
        //System.out.println("\nAfter SHM:");
        //System.out.println(java.util.Arrays.toString(mutated.getReceptor()));
        System.out.println("Affinity after SHM: " + AffinityEngine.computeAffinity(mutated, antigen));
    }
    */

    public static void printLog() {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("Basic Metrics: Iteration | ParentAffinity | BestCloneAffinity | SelectedAffinity | MeanAffinity | DeltaBestAffinity | DeltaMeanAffinity");
        for (int i = 0; i < logs.size(); i++) {
            Log l = logs.get(i);
            System.out.println(l.getIteration() + "," + l.getParentAffinity() + "," + l.getBestCloneAffinity() + "," + l.getSelectedAffinity() + "," + l.getMeanAffinity() + "," + l.getDeltaBestAffinity() + "," + l.getDeltaMeanAffinity());
        }

        System.out.println("\nMutation Metrics: Iteration | ClonesEvaluated | LethalMutations | SuccessfulMutations | ClonesSurvived | ClonesDied");
        for (int i = 0; i < logs.size(); i++) {
            Log l = logs.get(i);
            System.out.println(l.getIteration() + "," + l.getClonesEvaluated() + "," + l.getLethalMutations() + "," + l.getSuccessfulMutations() + "," + l.getClonesSurvived() + "," + l.getClonesDied());
        }

        System.out.println("\nMemory and Plasma Metrics: Iteration | PlasmaCellCount | MemoryCellCount | PlasmaMeanAffinity | MemoryMeanAffinity");
        for (int i = 0; i < logs.size(); i++) {
            Log l = logs.get(i);
            System.out.println(l.getIteration() + "," + l.getPlasmaCellCount() + "," + l.getMemoryCellCount() + "," + l.getPlasmaMeanAffinity() + "," + l.getMemoryMeanAffinity());
        }

        System.out.println("\nPopulation Diversity: Iteration | AffinityRange | AffinityStdDev");
        for (int i = 0; i < logs.size(); i++) {
            Log l = logs.get(i);
            System.out.println(l.getIteration() + "," + l.getAffinityRange() + "," + l.getAffinityStdDev());
        }
    }
  
    public static void printGeneral(Bcell parent, Bcell[] bcells, Memory memory){
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("\u001B[1mIMPORTANT INFORMATION\u001B[0m");
        System.out.println("-----Initial Conditions-----");
        System.out.println("Receptor Dimension: " + DIMENSION);
        System.out.println("Seed: " + SEED);
        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Initial Population Size: " + INITIAL_POPULATION);
        System.out.println("Initial Population Variability: " + INIT_POP_VARIABILITY + " standard deviation");
        System.out.println("Number of Clones: " + NUM_CLONES);
        System.out.println("-----Mutation Parameters-----");
        System.out.println("Mutation Magnitude: " + MUTATION_MAGNITUDE + " standard deviation");
        System.out.println("Mutation Chance: " + MUTATION_CHANCE * 100 + "%");
        System.out.println("Lethal Mutation Chance: " + LETHAL_MUTATION_CHANCE * 100 + "%");
        System.out.println("-----Plasma & Memory Cell Differentiation Parameters-----");
        System.out.println("Differentiation Chance: " + DIFFERENTIATION_CHANCE * 100 + "%");
        System.out.println("Plasma Threshold: " + PLASMA_THRESHOLD + " Affinity Score");
        System.out.println("Memory Threshold: " + MEMORY_THRESHOLD + " Affinity Score");
        System.out.println("Plasma Start Iteration: " + PLASMA_START);
        System.out.println("Memory Start Iteration: " + MEMORY_START);
        System.out.println("\u001B[1m-----Noteworthy Observations------\u001B[0m");
        System.out.println("Initial best affinity: " + AffinityEngine.best(bcells).getAffinity());
        System.out.println("Final best affinity: " + parent.getAffinity());
        System.out.println("Change from initial affinity: " + (parent.getAffinity() - AffinityEngine.best(bcells).getAffinity()));
        System.out.println("Plasma cells: " + memory.plasmaSize());
        System.out.println("Memory cells: " + memory.memorySize());
        System.out.println("Average plasma affinity: " + Memory.avg(memory.getPlasCells()));
        System.out.println("Average memory affinity: " + Memory.avg(memory.getMemCells()) + "\n");

        if (moreData == true) {
            memory.printMetaData();
        }

        System.out.println("-----Extras-----");
        System.out.println("Extra information? " + (moreData ? "\u001B[1mEnabled\u001B[0m" : "\u001B[1mDisabled\u001B[0m"));
        System.out.println("Show Initial Antigen? " + (showInitialAntigen ? "\u001B[1mEnabled\u001B[0m" : "\u001B[1mDisabled\u001B[0m"));
        System.out.println("Show log (Iteration | Parent affinity | Best clone affinity | Selected affinity | Mean affinity)? " + (showLog ? "\u001B[1mEnabled\u001B[0m" : "\u001B[1mDisabled\u001B[0m"));
        System.out.println("Download CSV? " + (makeCSV ? "\u001B[1mEnabled\u001B[0m" : "\u001B[1mDisabled\u001B[0m"));

        if (showLog == true){
            printLog();
        }
        if (makeCSV == true){
            try {
                exportCSV(FILENAME + ".csv");
                System.out.println("CSV file exported successfully as " + FILENAME + ".csv");
            } catch (IOException e) {
                System.out.println("An error occurred while exporting the CSV file: " + e.getMessage());
            }
        }
    }
    
    //-------------------------------------------------------------------------------------
    public static void main(String[] args) {

        Antigen antigen = Antigen.example();
        Bcell[] bcells = StochasticEngine.randomPopulation(INITIAL_POPULATION);
        AffinityEngine.affinityBasic(bcells, antigen);  
        printaffinity(bcells, antigen);

        Bcell parent = AffinityEngine.best(bcells);
        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            parent = runSHMClonalExpansion(parent, antigen, MUTATION_MAGNITUDE, NUM_CLONES);
        }
        printGeneral(parent, bcells, memory);
    }
}
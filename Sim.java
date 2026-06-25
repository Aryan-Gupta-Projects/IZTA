public class Sim {
    //Control Panel: Simulation Parameters
    public static final int DIMENSION = 100;
    public static final long SEED = 42;
    public static final int ITERATIONS = 50;
    public static final double MUTATION_RATE = 0.05;
    public static final int NUM_CLONES = 25;
    public static final int INITIAL_POPULATION = 100;
    public static final double INIT_POP_VARIABILITY = 0.0625; //The closer to 1, the more diverse the initial population will be, vice versa
    public static int itercount = 1;


    
//-------------------------------------------------------------------------------------
    public static void printaffinity(Bcell[] bcells, Antigen antigen) {

        /*System.out.println("Antigen:");
        System.out.println(java.util.Arrays.toString(antigen.getFeatures()));

        for (int i = 0; i < bcells.length; i++) {
            System.out.println("Parent " + i + ": " + bcells[i].getAffinity());
        }
        */
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

    public static Bcell runSHMClonalExpansion(Bcell parent, Antigen antigen, double BasicRate, int numClones) {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        Bcell[] clones = StochasticEngine.SHMClonalExpansion(parent, antigen, BasicRate, numClones);
        if (itercount <= 0) {
            for (int i = 0; i < clones.length; i++) {
                double score = clones[i].getAffinity();
                System.out.println("Clone " + i + " affinity: " + score);
        }
    }

        Bcell selected = Tcell.selectSurvivor(parent, clones);
        System.out.println("Selected B cell affinity after SHM " + itercount + ": " + selected.getAffinity());
        itercount++;
        return selected;
    }
//-------------------------------------------------------------------------------------
    public static void main(String[] args) {

        Antigen antigen = Antigen.example();
        Bcell[] bcells = StochasticEngine.randomPopulation(INITIAL_POPULATION);
        AffinityEngine.affinityBasic(bcells, antigen);  
        printaffinity(bcells, antigen);

        Bcell parent = AffinityEngine.best(bcells);
        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            parent = runSHMClonalExpansion(parent, antigen, MUTATION_RATE, NUM_CLONES);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("\u001B[1mSimulation Paramerters:\u001B[0m");
        System.out.println("Receptor Dimension: " + DIMENSION);
        System.out.println("Seed: " + SEED);
        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Mutation Rate: " + MUTATION_RATE);
        System.out.println("Number of Clones: " + NUM_CLONES);
        System.out.println("Initial Population Size: " + INITIAL_POPULATION);
        System.out.println("Initial Population Variability: " + INIT_POP_VARIABILITY);
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("\u001B[1mNoteworthy Observations:\u001B[0m");
        System.out.println("Initial best affinity: " + AffinityEngine.best(bcells).getAffinity());
        System.out.println("Final best affinity: " + parent.getAffinity());
        System.out.println("Change from initial affinity: " + (parent.getAffinity() - AffinityEngine.best(bcells).getAffinity()));
       
    }
}
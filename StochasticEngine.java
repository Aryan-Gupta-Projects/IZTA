import java.util.Random;

public class StochasticEngine {

private static final Random rng = new Random(Sim.SEED); //seed for reproducibility

    public static Random getRng() {
        return rng;
    }

    public static Bcell[] randomPopulation(int size) {
        Bcell[] bcells = new Bcell[size];

        for (int i = 0; i < size; i++) {
           bcells[i] = Bcell.randomB();
     }

        return bcells;
    }
    
    public static Bcell SHMBasicClone (Bcell cell, double BasicRate) {
        boolean Mutated = false;
        Bcell clone = cell.cloner();
        double[] receptor = clone.getReceptor();
        for (int i = 0; i< receptor.length; i++) {
            if (rng.nextDouble() < Sim.MUTATION_CHANCE) {
                Mutated = true;
                double mutation = rng.nextGaussian() * BasicRate; // Gaussian mutation
                receptor[i] += mutation;
            }
            
        }

        double norm = 0.0;
        for (double value : receptor) {
            norm += value * value;
        }
        norm = Math.sqrt(norm);
        if (norm > 1e-9) {
            for (int i = 0; i < receptor.length; i++) {
                receptor[i] /= norm;
            }
        }
        if (Mutated && rng.nextDouble() < Sim.LETHAL_MUTATION_CHANCE) {
            clone.setAffinity(-1.0); //Imitates a dead cell because a dead cell cannot be selected, just like a cell with a lower affinity score compared to the other clones
        }

        clone.setReceptor(receptor);
        return clone;
    }

    public static Bcell[] SHMClonalExpansion(Bcell parent, Antigen antigen,double BasicRate, int numClones) {
        Bcell[] clones = new Bcell[numClones];
        for (int i = 0; i < clones.length; i++) {
            clones[i] = StochasticEngine.SHMBasicClone(parent, BasicRate);
        }
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].getAffinity() != -1.0){
                double score = AffinityEngine.computeAffinity(clones[i], antigen);
                clones[i].setAffinity(score);
            }
        }
        return clones;
    }

}

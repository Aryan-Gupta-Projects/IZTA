public class Tcell {
    
    public static Bcell selectSurvivor(Bcell parent, Bcell[] clones) {
        
        Bcell bestClone = AffinityEngine.best(clones);
        if (bestClone.getAffinity() > parent.getAffinity()) {
            return bestClone;
        } else {
            return parent;
        }
    }
    
    public static Bcell selectPurpose(Bcell[] clones, Memory memory, int iteration) {
        //Bcell selected = clones[StochasticEngine.getRng().nextInt(clones.length)]; Random clone selected
        Bcell selected = AffinityEngine.best(clones); //Best clone selected
        double affinity = selected.getAffinity();
        if (StochasticEngine.getRng().nextDouble() < Sim.DIFFERENTIATION_CHANCE){
            if (iteration >= Sim.PLASMA_START && iteration < Sim.MEMORY_START && affinity >= Sim.PLASMA_THRESHOLD) {
              memory.addPlasma(selected, iteration);
            } else if (iteration >= Sim.MEMORY_START && affinity >= Sim.MEMORY_THRESHOLD){
              memory.addMemory(selected, iteration);
            }
        }
        return selected;
    }
}

public class Bcell {

    // attributes
    private double[] receptor;
    private double affinity;
    
    // constructor
    public Bcell(double[] receptor) {
        this.receptor = receptor;
        this.affinity = 0.0;
    }

    // getter for receptor vector
    public double[] getReceptor() {
        return receptor;
    }

    //setter for receptor vector (for use after mutation)
    public void setReceptor(double[] receptor) {
        this.receptor = receptor;
    }

    //getter for affinity score
    public double getAffinity() {
        return affinity;
    }

    // setter for affinity score (computed during simulation)
    public void setAffinity(double affinity) {
        this.affinity = affinity;
    }

    // static method to generate a random BCell (initial population)
    public static Bcell randomB(){
        double[] r = new double[Sim.DIMENSION]; // assuming DIMENSION features for the receptor
        
        for (int i = 0; i < Sim.DIMENSION; i++) {
            r[i] = StochasticEngine.getRng().nextGaussian() * Sim.INIT_POP_VARIABILITY; 
        }

        double norm = 0.0;
        for (int i = 0; i < Sim.DIMENSION; i++) {
            norm += r[i] * r[i];
        }
        norm = Math.sqrt(norm);
        if (norm > 1e-9) {
            for (int i = 0; i < Sim.DIMENSION; i++) {
                r[i] /= norm;
            }
        }

        return new Bcell(r);
    }

    public Bcell cloner() {
        double[] newReceptor = new double[receptor.length];
        for (int i = 0; i < receptor.length; i++) {
            newReceptor[i] = receptor[i];
        }
        Bcell copy = new Bcell(newReceptor);
        copy.setAffinity(this.affinity);
        return copy;
    }
}
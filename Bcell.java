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
        double[] r = new double[Sim.DIM]; // assuming Sim.DIM features for the receptor
        
        for (int i = 0; i < Sim.DIM; i++) {
            r[i] = r[i] = 2 * Math.random() - 1;; // random value between -1 and 1 for each feature
        }
        return new Bcell(r);
    }
}
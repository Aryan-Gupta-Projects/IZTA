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
    public static Bcell random(int n){
        double[] r = new double[n];
        
        for (int i = 0; i < n; i++) {
            r[i] = Math.random(); // random value between 0 and 1
        }
        return new Bcell(r);
    }
}
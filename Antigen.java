public class Antigen {
    //attribute
    private final double[] features;
    //constructor
    public Antigen (double[] features){
        this.features = features;  
    }
    //getter
    public double[] getFeatures() {
    return features;
    }

    //Example antigen
    public static Antigen example(){
        double[] f = new double[Sim.DIM]; // assuming Sim.dim features for the antigen
        
        for (int i = 0; i < Sim.DIM; i++) {
            f[i] = 2 * Math.random() - 1; // random value between -1 and 1 for each feature
        }
        return new Antigen(f);
    } 
}
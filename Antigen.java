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
        double[] f = new double[Sim.DIMENSION]; // assuming DIM features for the antigen
        
        for (int i = 0; i < Sim.DIMENSION; i++) {
            //f[i] = 2 * Math.random() - 1; Uniform distribution
            f[i] = StochasticEngine.getRng().nextDouble() * 2 - 1;
        }

        double norm = 0.0;
        for (int i = 0; i < Sim.DIMENSION; i++) {
            norm += f[i] * f[i];
        }
        norm = Math.sqrt(norm);
        
        if (norm > 1e-9) {
            for (int i = 0; i < Sim.DIMENSION; i++) {
                f[i] /= norm;
            }
        }
        return new Antigen(f);
    } 
}
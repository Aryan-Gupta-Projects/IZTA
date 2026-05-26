public class Sim {
    public static final int DIM = 10;
    public static double simpleDot(double[] a, double[] b) {

    double dot = 0;
    double magA = 0;
    double magB = 0;

    for (int i = 0; i < a.length; i++) {
        dot += a[i] * b[i];
        magA += a[i] * a[i];
        magB += b[i] * b[i];
    }

    return dot / (Math.sqrt(magA) * Math.sqrt(magB) + 1e-9);
}
    public static void main(String[] args) {
        System.out.println("Hello, I'm the simulation!");
        //Create antigen
        Antigen antigen = Antigen.example();

        System.out.println("Antigen:");
        System.out.println(java.util.Arrays.toString(antigen.getFeatures()));

        //Create B Cells
        Bcell[] bcells = new Bcell[5];
        for (int i = 0; i < bcells.length; i++) {
            bcells[i] = Bcell.randomB();
        }

        //Temporary simple affinity (w/o engines)
        for (int i = 0; i < bcells.length; i++){
             double score = simpleDot(
            bcells[i].getReceptor(),
            antigen.getFeatures()
        );

        bcells[i].setAffinity(score);

        System.out.println("B Cell " + i + " affinity: " + score);
        }
    }
 }
public class Sim {
    public static final int DIM = 10;

    public static void main(String[] args) {
        System.out.println("Hello, I'm the simulation!");
        //Create antigen
        Antigen antigen = Antigen.example();

        System.out.println("Antigen:");
        System.out.println(java.util.Arrays.toString(antigen.getFeatures()));

        //Create B Cells
        Bcell[] bcells = new Bcell[5]; //How many B cells wanted
        for (int i = 0; i < bcells.length; i++) {
            bcells[i] = Bcell.randomB();
        }

        //Temporary simple affinity (w/o engines)
        for (int i = 0; i < bcells.length; i++){
             double score = VecMath.cosine(
         bcells[i].getReceptor(),
         antigen.getFeatures()
        );

        bcells[i].setAffinity(score);

        System.out.println("B Cell " + i + " affinity: " + score);
        }
    }
 }
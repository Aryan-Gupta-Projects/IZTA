public class Sim {
    public static final int DIM = 10;

    public static void main(String[] args) {

        Antigen antigen = Antigen.example();
        Bcell[] bcells = VecMath.randomPopulation(5);
        VecMath.affinitybasic(bcells, antigen);
        VecMath.printaffinity(bcells, antigen);
    }
}
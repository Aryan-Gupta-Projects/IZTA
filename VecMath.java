public class VecMath {

// cosine similarity (normalized dot product)
    public static double cosine(double[] a, double[] b) {

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

//Population Creation
    public static Bcell[] randomPopulation(int size) {
    Bcell[] bcells = new Bcell[size];

    for (int i = 0; i < size; i++) {
        bcells[i] = Bcell.randomB();
    }

    return bcells;
}
    
//Evaluation of affinity scores
    public static void affinitybasic(Bcell[] bcells, Antigen antigen) {
        for (int i = 0; i < bcells.length; i++) {
            double score = cosine(bcells[i].getReceptor(), antigen.getFeatures());
            bcells[i].setAffinity(score);
    }
}

//Best B cell
public static Bcell best(Bcell[] bcells) {
    Bcell best = bcells[0];
    for (int i = 1; i < bcells.length; i++) {
        if (bcells[i].getAffinity() > best.getAffinity()) {
            best = bcells[i];
        }
    }
    return best;
}

//Printing
    public static void printaffinity(Bcell[] bcells, Antigen antigen) {

    System.out.println("Antigen:");
    System.out.println(java.util.Arrays.toString(antigen.getFeatures()));

    for (int i = 0; i < bcells.length; i++) {
        System.out.println("B Cell " + i + ": " + bcells[i].getAffinity());
    }

    Bcell best = best(bcells);
    System.out.println("Best inital affinity: " + best.getAffinity());
}

// simple dot product (Optional, for testing)
    public static double dot(double[] a, double[] b) {

        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }

        return sum;
    }
}
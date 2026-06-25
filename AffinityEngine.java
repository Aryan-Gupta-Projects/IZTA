public class AffinityEngine {

//Evaluation of affinity scores
    public static void affinityBasic(Bcell[] bcells, Antigen antigen) {
        for (int i = 0; i < bcells.length; i++) {
            double score = VecMath.cosine(bcells[i].getReceptor(), antigen.getFeatures());
            bcells[i].setAffinity(score);
    }
}

    public static double computeAffinity(Bcell cell, Antigen antigen) {
        return VecMath.cosine(cell.getReceptor(), antigen.getFeatures());
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

}

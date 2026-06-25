public class Tcell {

      public static Bcell selectSurvivor(Bcell parent, Bcell[] clones) {
        Bcell bestClone = AffinityEngine.best(clones);
        if (bestClone.getAffinity() > parent.getAffinity()) {
            return bestClone;
        } else {
            return parent;
        }
    }
}
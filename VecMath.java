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
        double cosineSimilarity = dot / (Math.sqrt(magA) * Math.sqrt(magB) + 1e-9);
        return cosineSimilarity;
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
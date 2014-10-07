
package main;


public class Methods {
    public static final double ONE_BY_FOUR_PI_EPSILON_0 = 8.98*Math.pow(10, 9);

    public static boolean isAlmostEqual(double a, double b) {
        return Math.abs(a - b) < .0001;
    }

    public static double[] vectorMultiple(double coeff, double[] vector) {
        for (int i=0; i<vector.length; i++) {
            vector[i] *= coeff;
        }
        return vector;
    }

    public static double[] vectorAdd(double[]... vectors) {
        double[] out = new double[vectors[0].length];
        for (int i=0; i<out.length; i++) {
            for (int j=0; j<vectors.length; j++) {
                out[i] += vectors[j][i];
            }
        }
        return out;
    }

    public static double vectorDot(double[] a, double[] b) {
        double out = 0;
        for (int i=0; i<a.length; i++) {
            out += a[i]*b[i];
        }
        return out;
    }

    /**
     * @param firstV vector to be subtracted from
     * @param secondV vector to be subtracted
     * @return firstV - secondV
     */
    public static double[] vectorSubtract(double[] firstV, double[] secondV) {
        for (int i=0; i<firstV.length; i++) {
            firstV[i] -= secondV[i];
        }
        return firstV;
    }

    public static double vectorMagnitude(double[] vector) {
        double rad = 0;
        for (int i=0; i<vector.length; i++) {
            rad += Math.pow(vector[i], 2);
        }
        return Math.sqrt(rad);
    }

    public static double[] vectorUnit(double[] vector) {
        for (int i=0; i<vector.length; i++) {
            vector[i] /= vectorMagnitude(vector);
        }
        return vector;
    }
}

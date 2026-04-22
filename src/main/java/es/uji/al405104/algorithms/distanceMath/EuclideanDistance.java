package es.uji.al405104.algorithms.distanceMath;

import java.util.List;

public class EuclideanDistance implements Distance {

    @Override
    public double calculateDistance(List<Double> p, List<Double> q) throws IllegalArgumentException {

        GenericDistance.comprobacionParametros(p,q);

        double sum = 0.0;

        for (int i = 0; i < p.size(); i++) {
            double diff = p.get(i) - q.get(i);
            sum += Math.pow(diff, 2);
        }

        return Math.sqrt(sum);
    }
}

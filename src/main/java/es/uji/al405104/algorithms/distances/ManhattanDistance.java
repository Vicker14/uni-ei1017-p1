package es.uji.al405104.algorithms.distances;

import java.util.List;

public class ManhattanDistance implements Distance {

    public double calculateDistance(List<Double> p, List<Double> q) {
        GenericDistance.comprobacionParametros(p,q);

        double distance = 0.0;

        for (int i = 0; i < p.size(); i++) {
            double absDiff = Math.abs(p.get(i) - q.get(i));
            distance += absDiff;
        }

        return distance;
    }
}
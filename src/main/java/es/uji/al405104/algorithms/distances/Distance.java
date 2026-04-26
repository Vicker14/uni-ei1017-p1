package es.uji.al405104.algorithms.distances;

import java.util.List;

public interface Distance {

    double calculateDistance(List<Double> p, List<Double> q) throws IllegalArgumentException;

}
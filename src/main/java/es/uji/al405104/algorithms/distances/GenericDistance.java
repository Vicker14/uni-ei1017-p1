package es.uji.al405104.algorithms.distances;

import java.util.List;

public abstract class GenericDistance {

    public static void comprobacionParametros(List<Double> p, List<Double> q) throws IllegalArgumentException {

        if (p == null) {
            throw new IllegalArgumentException("El punto p no puede ser nulo");
        }
        if (q == null) {
            throw new IllegalArgumentException("El punto q no puede ser nulo ");
        }

        if (p.isEmpty() && q.isEmpty()) return;

        if (p.size() != q.size()) {
            throw new IllegalArgumentException("Los puntos deben tener el mismo tamaño");
        }
    }
}

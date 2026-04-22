package es.uji.al405104.algorithms.distanceMath;

import java.util.List;

public abstract class GenericDistance {

    public static void comprobacionParametros(List<Double> p, List<Double> q) throws IllegalArgumentException {

        if (p == null || p.isEmpty()) {
            throw new IllegalArgumentException("El punto p no puede ser nulo o vacio");
        }
        if (q == null || q.isEmpty()) {
            throw new IllegalArgumentException("El punto q no puede ser nulo o vacio");
        }
        if (p.size() != q.size()) {
            throw new IllegalArgumentException("Los puntos deben tener el mismo tamaño");
        }
    }
}

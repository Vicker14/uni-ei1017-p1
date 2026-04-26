package es.uji.al405104.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathFunctions {

    /// EUCLIDEAN DISTANCE ///
    /*
     * Calcula la distancia euclídea entre dos vectores multidimensionales.
     * Aplica programación defensiva para validar la integridad de los datos
     * y asegurar que ambos puntos pertenecen al mismo espacio dimensional.
     * @param p Lista de coordenadas del primer punto.
     * @param q Lista de coordenadas del segundo punto.
     * @return La raíz cuadrada de la suma de las diferencias al cuadrado.
     * @throws IllegalArgumentException Si los puntos son nulos, vacíos o de distinto tamaño.
     */
    public static double calculateEuclideanDistance(List<Double> p, List<Double> q) {

        if (p == null || p.isEmpty()) {
            throw new IllegalArgumentException("El punto p no puede ser nulo o vacio");
        }
        if (q == null || q.isEmpty()) {
            throw new IllegalArgumentException("El punto q no puede ser nulo o vacio");
        }
        if (p.size() != q.size()) {
            throw new IllegalArgumentException("Los puntos deben tener el mismo tamaño");
        }

        double sum = 0.0;
        for (int i = 0; i < p.size(); i++) {
            double diff = p.get(i) - q.get(i);
            sum += Math.pow(diff, 2);
        }
        return Math.sqrt(sum);
    }

    public static List<Double> calculateAverage(List<List<Double>> points) {

        List<Double> median = new ArrayList<>(Collections.nCopies((points.get(0).size()), 0.0)); //Inicializa el Array a 0.0

        for (int j = 0; j < points.get(0).size(); j++) {
            for (int i = 0; i < points.size(); i++) {

                //Hacemos el sumatorio
                Double nuevaSuma = median.get(j) + points.get(i).get(j);
                median.set(j, nuevaSuma);
            }

            //Sacamos la media
            Double sumaTotalCol = median.get(j);
            median.set(j, sumaTotalCol / points.size());
        }

        return median;
    }
}

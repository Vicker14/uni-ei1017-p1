package es.uji.al405104.algorithms;

import es.uji.al405104.excepciones.InvalidClusterNumberException;
import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {

    /// ATRIBUTOS ///
    private int numClusters; //Numero de grupos (k) que queremos
    private int numIterations; //Cuantas veces vamos a mover los centros para ajustar los grupos
    private long seed; //Numero al azar
    private List<List<Double>> centroids; //Aqui guardaremos los puntos centrales de cada grupo

    /// CONTRUCTOR ///
    public KMeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
    }

    /// METODOS ///
    @Override
    public void train(Table data) throws InvalidDataException { // Implementación del algoritmo de entrenamiento

        if (data == null || data.getRowCount() == 0) {
            throw new InvalidDataException("La tabla de datos no puede ser nula ni estar vacia");
        }

        if (this.numClusters > data.getRowCount()) {
            throw new InvalidClusterNumberException(this.numClusters, data.getRowCount());
        }

        initializeCentroids(data); //Inicializa los primeros centroides

        /// ENTRENAMIENTO //
        for (int iteration = 0; iteration < this.numIterations; iteration++) {

            // Creamos la estructura de los grupos, Lista de Grupos donde cada grupo tiene una
            // Lista de items y cada item es una lista de coordenadas
            List<List<List<Double>>> groups = new ArrayList<>();
            for (int i = 0; i < this.numClusters; i++) {
                groups.add(new ArrayList<>());
            }

            //Reparticion de todas las filas de items
            for (int i = 0; i < data.getRowCount(); i++) {
                List<Double> itemsData = data.getRowAt(i).getData();
                int bestGroupIndex = this.estimate(itemsData);
                groups.get(bestGroupIndex).add(itemsData);
            }

            updateCentroids(groups); //Actualizamos los centroides

        }
    }

    public Integer estimate(List<Double> sample) {
        // Implementación del algoritmo de estimación.
        int bestGroupIndex = 0;
        double bestDistance = Double.MAX_VALUE;
        for (int i = 0; i < numClusters; i++) {
            double distance = MathFunctions.calculateEuclideanDistance(centroids.get(i), sample);

            if (distance < bestDistance) {
                bestDistance = distance;
                bestGroupIndex = i;
            }
        }
        return bestGroupIndex;
    }

    /// METODOS PRIVADOS ///

    private void initializeCentroids(Table data) { /// Selecciona los primeros centroides
        Random generator = new Random(seed);
        List<Integer> chosenIndices = new ArrayList<>();

        while(chosenIndices.size() < this.numClusters) {
            int randomIndex = generator.nextInt(data.getRowCount());

            if (!chosenIndices.contains(randomIndex)) { //Si ese centroide no ha sido elegido
                chosenIndices.add(randomIndex);
                this.centroids.add(data.getRowAt(randomIndex).getData());
            }
        }
    }

    private void updateCentroids(List<List<List<Double>>> groups) {
        List<List<Double>> newCentroids = new ArrayList<>();

        for (int i = 0; i < this.numClusters; i++) {
            List<List<Double>> groupPoints = groups.get(i);

            // Si el grupo tiene puntos, calculamos la media para su nuevo centro
            if (!groupPoints.isEmpty()) {
                List<Double> newCenter = MathFunctions.calculateAverage(groupPoints);
                newCentroids.add(newCenter);
            } else {
                // Si el grupo se ha quedado vacío (poco probable pero posible), mantenemos el centroide antiguo
                newCentroids.add(this.centroids.get(i));
            }
        }

        // Actualizamos la lista global de centroides para la siguiente iteración
        this.centroids = newCentroids;
    }
}

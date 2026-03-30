package es.uji.al405104.algoritmos;

import es.uji.al405104.Excepciones.InvalidClusterNumberException;
import es.uji.al405104.Excepciones.KMeansExceptions;
import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {

    /// ATRIBUTOS ///
    private int numClusters; //Numero de grupos (k) que queremos
    private int numIterations; //Cuantas veces vamos a mover los centros para ajustar los grupos
    private long seed; //Numero al azar
    private List<Row> centroids; //Aqui guardaremos los puntos centrales de cada grupo

    /// CONTRUCTOR ///
    public KMeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
    }

    /// METODOS ///
    @Override
    public void train(Table data) throws KMeansExceptions { // Implementación del algoritmo de entrenamiento

        if (data == null || data.getRowCount() == 0) {
            throw new KMeansExceptions("La tabla de datos no puede ser nula ni estar vacia");
        }

        if (this.numClusters > data.getRowCount()) {
            throw new InvalidClusterNumberException(this.numClusters, data.getRowCount());
        }

        /// ELECCION DE LOS PRIMEROS CENTROIDES ///
        Random generator = new Random(seed);
        List<Integer> chosenIndices = new ArrayList<>();

        while(chosenIndices.size() < this.numClusters) {
            int randomIndex = generator.nextInt(data.getRowCount());

            if (!chosenIndices.contains(randomIndex)) { //Si ese centroide no ha sido elegido
                chosenIndices.add(randomIndex);
                this.centroids.add(data.getRowAt(randomIndex));
            }
        }

        /// ENTRENAMIENTO //
        for (int iteration = 0; iteration < this.numIterations; iteration++) {

            // Creamos la estructura de los grupos, Lista de Grupos donde cada grupo tiene una
            // Lista de FLores y cada flor es una lista de coordenadas
            List<List<List<Double>>> groups = new ArrayList<>();
            for (int i = 0; i < this.numClusters; i++) {
                groups.add(new ArrayList<>());
            }

            //Reparticion de todas las filas de flores
            for (int i = 0; i < data.getRowCount(); i++) {
                List<Double> flowerData = data.getRowAt(i).getData();

                int bestGroupIndex = 0;
                double minimumDistance = Double.MAX_VALUE;

                for (int j = 0; j < this.centroids.size(); j++) {
                    List<Double> centroidData = this.centroids.get(j).getData();
                    double distance = MathFunctions.calculateEuclideanDistance(flowerData, centroidData);

                    if (distance < minimumDistance) {
                        minimumDistance = distance;
                        bestGroupIndex = j;
                    }
                }

                groups.get(bestGroupIndex).add(flowerData);
            }

            List<Row> newCentroids = new ArrayList<>();

            for (int i = 0; i < this.numClusters; i++) {
                List<List<Double>> groupPoints = groups.get(i);

                if (!groupPoints.isEmpty()) {
                    List<Double> newCenter = MathFunctions.calculateAverage(groupPoints);

                    newCentroids.add(new Row(newCenter));
                } else {
                    newCentroids.add(this.centroids.get(i));
                }
            }

            this.centroids = newCentroids;
        }
    }

    public Integer estimate(List<Double> sample) {
        // Implementación del algoritmo de estimación.
        int bestGroupIndex = 0;
        double bestDistance = Double.MAX_VALUE;
        for (int i = 0; i < numClusters; i++) {
            double distance = MathFunctions.calculateEuclideanDistance(centroids.get(i).getData(), sample);

            if (distance < bestDistance) {
                bestDistance = distance;
                bestGroupIndex = i;
            }
        }
        return bestGroupIndex;
    }
}

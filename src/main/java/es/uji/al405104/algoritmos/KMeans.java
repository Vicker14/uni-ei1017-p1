package es.uji.al405104.algoritmos;

import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;
import java.util.List;

public class KMeans {

    /// ATRIBUTOS ///
    int numCLuisters; //Numero de grupos (k) que queremos
    int numITerations; //Cuantas veces vamos a mover los centros para ajustar los grupos
    long seed; //Numero al azar
    List<Row> centroids; //Aqui guardaremos los puntos centrales de cada grupo

    /// CONTRUCTOR ///
    public KMeans(int numClusters, int numIterations, long seed) {
        // Almacenar número de grupos, iteraciones y semilla.
    }

    /// METODOS ///
    public void train(Table data) {
        // Implementación del algoritmo de entrenamiento
    }
    public Integer estimate(List<Double> sample) {
        // Implementación del algoritmo de estimación.
        return 0;
    }
}

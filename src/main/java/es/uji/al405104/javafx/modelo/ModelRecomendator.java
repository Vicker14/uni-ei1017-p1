package es.uji.al405104.javafx.modelo;

import es.uji.al405104.algorithms.Algorithm;
import es.uji.al405104.algorithms.KMeans;
import es.uji.al405104.algorithms.KNN;
import es.uji.al405104.algorithms.RecSys;
import es.uji.al405104.algorithms.distances.Distance;
import es.uji.al405104.algorithms.distances.EuclideanDistance;

import es.uji.al405104.algorithms.distances.ManhattanDistance;
import es.uji.al405104.csv.CSVLabeledFileReader;
import es.uji.al405104.csv.CSVUnlabeledFileReader;
import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.javafx.vista.InformaVista;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelRecomendator implements InterrogaModelo, CambioModelo {

    InformaVista vista;

    /// RUTAS ///
    private static final String RUTA_KNN_TRAIN = "songs/songs_train.csv";
    private static final String RUTA_KMEANS_TRAIN = "songs/songs_train_withoutnames.csv";
    private static final String RUTA_TEST = "songs/songs_test_withoutnames.csv";
    private static final String RUTA_NOMBRES = "songs/songs_test_names.csv";

    /// ATRIBUTOS ///
    private TableWithLabels trainDataKNN;
    private Table trainDataKMeans;
    private Table testData;
    private List<String> nombresCanciones;
    private List<String> recomendaciones;

    ///  CONSTRUCTOR ///
    public ModelRecomendator() {
        try {
            CSVLabeledFileReader knnReader = new CSVLabeledFileReader(RUTA_KNN_TRAIN);
            this.trainDataKNN = knnReader.readTableFromSource();

            CSVUnlabeledFileReader kmeansReader = new CSVUnlabeledFileReader(RUTA_KMEANS_TRAIN);
            this.trainDataKMeans = kmeansReader.readTableFromSource();

            CSVUnlabeledFileReader testReader = new CSVUnlabeledFileReader(RUTA_TEST);
            this.testData = testReader.readTableFromSource();

            this.nombresCanciones = leerNombres(RUTA_NOMBRES);

        } catch (Exception e) {
            System.err.println("Error al cargar los CSV en el Modelo: " + e.getMessage());
        }
    }

    /// METODOS ///

    //Metodo para leer la lista de nombres de canciones
    private List<String> leerNombres(String ruta) throws FileNotFoundException, URISyntaxException {
        String path = getClass().getClassLoader().getResource(ruta).toURI().getPath();
        List<String> nombres = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            nombres.add(scanner.nextLine());
        }
        scanner.close();
        return nombres;
    }

    public void generaRecomendaciones(
            String nombreCancion,
            int numRecomendaciones,
            String tipoAlgoritmo,
            String tipoDistancia
    ) throws InvalidDataException {

        // Seleccionar la metrica de distancia
        Distance distancia;
        if (tipoDistancia.equalsIgnoreCase("Manhattan")) {
            distancia = new ManhattanDistance();
        } else {
            distancia = new EuclideanDistance();
        }

        RecSys recsys = getRecSys(tipoAlgoritmo, distancia);
        recsys.initialise(this.testData, this.nombresCanciones);

        // Devolver la lista de recomendaciones a la capa superior
        recomendaciones = recsys.recommend(nombreCancion, numRecomendaciones);
        vista.mostrarResultados();
    }

    private RecSys getRecSys(String tipoAlgoritmo, Distance distancia) throws InvalidDataException {
        Algorithm algoritmoClase;
        Table datosEntrenamiento;

        if (tipoAlgoritmo.equalsIgnoreCase("KNN")) {
            algoritmoClase = new KNN(distancia);
            datosEntrenamiento = this.trainDataKNN;
        } else { // KMeans
            // Parámetros estáticos de configuración (k=15, iteraciones=200, seed=4321)
            algoritmoClase = new KMeans(15, 200, 4321, distancia);
            datosEntrenamiento = this.trainDataKMeans;
        }

        // Ensamblaje, entrenar e inicializar
        RecSys recsys = new RecSys(algoritmoClase);
        recsys.train(datosEntrenamiento);
        return recsys;
    }

    /// GETTERS ///
    public List<String> getNombresCanciones() {
        return this.nombresCanciones;
    }
    public List<String> getListaRecomendaciones() {return this.recomendaciones;}

    public void setVista(InformaVista vista) {
        this.vista = vista;
    }
}
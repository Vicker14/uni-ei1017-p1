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

/*
 * CLASE: ModelRecomendator
 * * FUNCION PRINCIPAL:
 * Representa el "Cerebro Matematico" y el almacen de datos de la aplicacion. Sus tareas incluyen:
 * 1. Cargar y gestionar las tablas de datos desde los archivos CSV.
 * 2. Instanciar y configurar los algoritmos de recomendacion (KNN/KMeans).
 * 3. Ejecutar los calculos de recomendacion solicitados.
 * 4. Determinar los límites fisicos de los datos (tamaño de clusters) para garantizar 
 * que el sistema nunca solicite mas recomendaciones de las que el algoritmo puede ofrecer.
*/

public class ModelRecomendator implements InterrogaModelo, CambioModelo {

    /// ATRIBUTOS
    private InformaVista informaVista;
    private TableWithLabels trainDataKNN;
    private Table trainDataKMeans;
    private Table testData;
    private List<String> nombresCanciones;
    private List<String> recomendaciones;
    private List<String> busquedaCanciones;

    /// RUTAS ///
    private static final String RUTA_KNN_TRAIN = "songs/songs_train.csv";
    private static final String RUTA_KMEANS_TRAIN = "songs/songs_train_withoutnames.csv";
    private static final String RUTA_TEST = "songs/songs_test_withoutnames.csv";
    private static final String RUTA_NOMBRES = "songs/songs_test_names.csv";

    /// CONSTRUCTOR
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

    /// METODOS
    // Metodo para leer la lista de nombres de canciones
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

    @Override
    public void generaRecomendaciones(
            String nombreCancion,
            int numRecomendaciones,
            String tipoAlgoritmo,
            String tipoDistancia
    ) throws InvalidDataException {

        Distance distancia;
        if (tipoDistancia.equalsIgnoreCase("Manhattan")) {
            distancia = new ManhattanDistance();
        } else {
            distancia = new EuclideanDistance();
        }

        RecSys recsys = getRecSys(tipoAlgoritmo, distancia);
        recsys.initialise(this.testData, this.nombresCanciones);

        // Guardamos las recomendaciones NO se llama a la vista desde aqui.
        this.recomendaciones = recsys.recommend(nombreCancion, numRecomendaciones);

        //ACTUALIZACION DE LA VISTA
        informaVista.mostrarResultados();
    }

    // Metodo para seleccionar el algoritmo
    private RecSys getRecSys(String tipoAlgoritmo, Distance distancia) throws InvalidDataException {
        Algorithm algoritmoClase;
        Table datosEntrenamiento;

        if (tipoAlgoritmo.equalsIgnoreCase("KNN")) {
            algoritmoClase = new KNN(distancia);
            datosEntrenamiento = this.trainDataKNN;
        } else { // KMeans
            algoritmoClase = new KMeans(15, 200, 4321, distancia);
            datosEntrenamiento = this.trainDataKMeans;
        }

        RecSys recsys = new RecSys(algoritmoClase);
        recsys.train(datosEntrenamiento);
        return recsys;
    }

    @Override
    public int obtenerMaximoRecomendaciones(String nombreCancion, String tipoAlgoritmo, String tipoDistancia) throws InvalidDataException {

        Distance distancia;
        if (tipoDistancia.equalsIgnoreCase("Manhattan")) {
            distancia = new ManhattanDistance();
        } else {
            distancia = new EuclideanDistance();
        }

        RecSys recsys = getRecSys(tipoAlgoritmo, distancia);
        recsys.initialise(this.testData, this.nombresCanciones);

        // Pedimos un numero muy alto para forzar el tope del cluster
        int maximoAbsoluto = this.nombresCanciones.size(); //Cantidad de canciones que disponemos en nuestra lista
        List<String> todasLasPosibles = recsys.recommend(nombreCancion, maximoAbsoluto); //Imposible que nos recomiende todas las canciones

        return todasLasPosibles.size();
    }

    // Metodo para obtener la lista filtrada por la busqueda
    @Override
    public void generaListaBusqueda(String busqueda) {
        busquedaCanciones = new ArrayList<>();

        for (String cancion: nombresCanciones) {
            if (cancion.contains(busqueda)) {
                this.busquedaCanciones.add(cancion);
            }
        }
        informaVista.mostrarBusqueda();
    }

    /// GETTERS
    @Override
    public List<String> getNombresCanciones() {
        return this.nombresCanciones;
    }

    @Override
    public List<String> getListaRecomendaciones() {
        return this.recomendaciones;
    }

    @Override
    public List<String> getListaBusqueda() {
        return this.busquedaCanciones;
    }

    /// SETTERS
    public void setVista(InformaVista informaVista) {
        this.informaVista = informaVista;
    }
}
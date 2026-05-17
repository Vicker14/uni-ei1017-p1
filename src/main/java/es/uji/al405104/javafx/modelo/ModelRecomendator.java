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
    // --- MEMORIA CACHE ---
    private RecSys recSysCache;
    private String ultimoAlgoritmo = "";
    private String ultimaDistancia = "";

    // --- TABLAS DE DATOS ---
    private TableWithLabels trainDataKNN; //Guarda los datos de entrenamiento para KNN
    private Table trainDataKMeans; //Guarda los datos de entrenamiento para KMeans
    private Table testData; //Guarda las caracteristiicas del catálogo de canciones

    // --- RESULTADOS A ENTREGAR ---
    private List<String> nombresCanciones; //Guarda los titulos finales de las canciones resultantes a mostrar
    private List<String> recomendaciones; //Guarda el resultaod del Algoritmo esperando a que Controlador la recoja
    private List<String> busquedaCanciones; //Guarda las canciones filtradas por el buscador

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

        RecSys recsys = getRecSys(tipoAlgoritmo, tipoDistancia); //Obtemos el algoritmo ya entrenado con la distancia elegida
        recsys.initialise(this.testData, this.nombresCanciones); //Añadimos los datos a evaluar y su diccionario

        // Guardamos las recomendaciones NO se llama a la vista desde aqui.
        this.recomendaciones = recsys.recommend(nombreCancion, numRecomendaciones);

        //ACTUALIZACION DE LA VISTA
        informaVista.mostrarResultados();
    }

    // Metodo para seleccionar el algoritmo
    private RecSys getRecSys(String tipoAlgoritmo, String tipoDistancia) throws InvalidDataException {
        // Comprobamos si el modelo ya está entrenado con los parámetros seleccionados
        if (tipoAlgoritmo.equals(ultimoAlgoritmo) && tipoDistancia.equals(ultimaDistancia) && recSysCache != null)
            return recSysCache;

        //Configuramos la distancia para medir
        Distance distancia;
        if (tipoDistancia.equalsIgnoreCase("Manhattan")) {
            distancia = new ManhattanDistance();
        } else {
            distancia = new EuclideanDistance();
        }
        ultimaDistancia = tipoDistancia;

        //Eleccion del algoritmo y los datos de entreamiento adecuados
        Algorithm algoritmoClase;
        Table datosEntrenamiento;

        if (tipoAlgoritmo.equalsIgnoreCase("KNN")) {
            algoritmoClase = new KNN(distancia);
            datosEntrenamiento = this.trainDataKNN;
        } else { // KMeans
            algoritmoClase = new KMeans(15, 200, 4321, distancia);
            datosEntrenamiento = this.trainDataKMeans;
        }

        //Entrenamos y guardamos la seleccion en la memoria cache
        recSysCache = new RecSys(algoritmoClase);
        recSysCache.train(datosEntrenamiento);
        ultimoAlgoritmo = tipoAlgoritmo;
        return recSysCache;
    }

    @Override
    public int obtenerMaximoRecomendaciones(String nombreCancion, String tipoAlgoritmo, String tipoDistancia) throws InvalidDataException {

        //Obtenemos el modelo entrenado e incializado
        RecSys recsys = getRecSys(tipoAlgoritmo, tipoDistancia);
        recsys.initialise(this.testData, this.nombresCanciones);

        // Pedimos un numero muy alto para forzar el tope del cluster
        int maximoAbsoluto = this.nombresCanciones.size(); //Cantidad de canciones que disponemos en nuestra lista
        List<String> todasLasPosibles = recsys.recommend(nombreCancion, maximoAbsoluto); //Imposible que nos recomiende todas las canciones

        //Devolvemos el tamaño real del grupo
        return todasLasPosibles.size();
    }

    // Metodo para obtener la lista filtrada por la busqueda
    @Override
    public void generaListaBusqueda(String busqueda) {
        //Inicializacion lista de resultados vacia
        busquedaCanciones = new ArrayList<>();

        //Pasamos la busqueda a minuscula y lo guardamos
        String terminoBusqueda = busqueda.toLowerCase();

        //Miramos el catalogo entero y si la cancion tiene la palabra buscada la guardamos
        for (String cancion: nombresCanciones) {
            if (cancion.toLowerCase().contains(terminoBusqueda)) {
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
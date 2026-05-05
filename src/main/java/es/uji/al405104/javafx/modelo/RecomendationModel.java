package es.uji.al405104.javafx.modelo;

import es.uji.al405104.algorithms.Algorithm;
import es.uji.al405104.algorithms.KMeans;
import es.uji.al405104.algorithms.KNN;
import es.uji.al405104.algorithms.RecSys;
import es.uji.al405104.algorithms.distances.Distance;
import es.uji.al405104.algorithms.distances.EuclideanDistance;

import es.uji.al405104.csv.CSVLabeledFileReader;
import es.uji.al405104.csv.CSVUnlabeledFileReader;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecomendationModel {

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

    
}
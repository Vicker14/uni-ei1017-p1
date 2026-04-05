package es.uji.al405104.algoritmos;

import es.uji.al405104.Excepciones.KMeansExceptions;
import es.uji.al405104.table.Table;

import java.util.ArrayList;
import java.util.List;

public class RecSys implements Algorithm<Table, List<Double>, Integer> {

    /// ATRIBUTOS ///
    private Algorithm<Table, List<Double>, Integer> instanciaAlgoritmo;

    /// CONSTRUCTOR ///
    public RecSys(Algorithm<Table, List<Double>, Integer> algorithm) {
        instanciaAlgoritmo = algorithm;
    }

    /// METODOS ///
    @Override
    public void train(Table trainData) throws KMeansExceptions {
        if (trainData == null || trainData.getRowCount() == 0)
            throw new KMeansExceptions("La tabla de datos no puede estar vacía o ser nula");

        instanciaAlgoritmo.train(trainData);
    }

    @Override
    public Integer estimate(List<Double> sample) {
        return 0;
    }

    public void initialise(Table testData, List<String> testItemNames) {

    }

    public List<String> recommend(String nameLinkedItem, int numRecommendatios){
        List<String> listOfRecomendations = new ArrayList<>();

        return listOfRecomendations;
    }
}

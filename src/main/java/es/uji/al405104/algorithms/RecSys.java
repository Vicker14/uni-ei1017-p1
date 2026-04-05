package es.uji.al405104.algorithms;

import es.uji.al405104.Excepciones.KMeansExceptions;
import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.util.ArrayList;
import java.util.List;

public class RecSys implements Algorithm<Table, List<Double>, Integer> {

    /// ATRIBUTOS ///
    private Algorithm<Table, List<Double>, Integer> instanciaAlgoritmo; //Almacena la instancia del algoritmo en uso
    private Table dataInitialisedItem; //Obtenemos la data del item
    private List<String> nameInitialisedItem; //Obtenemos el nombre del item
    private List<Integer> estimatedItemIndex; //Obtenemos el index para el label del item


    /// CONSTRUCTOR ///
    public RecSys(Algorithm<Table, List<Double>, Integer> algorithm) {
        instanciaAlgoritmo = algorithm;
        dataInitialisedItem = new TableWithLabels();
        nameInitialisedItem = new ArrayList<>();
        estimatedItemIndex = new ArrayList<>();
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
        return instanciaAlgoritmo.estimate(sample);
    }

    public void initialise(Table testData, List<String> testItemNames) {
        for (int i = 0; i < testItemNames.size(); i++) {
            Row data = testData.getRowAt(i);
            String itemName = testItemNames.get(i);

            dataInitialisedItem.addRow(data);
            nameInitialisedItem.add(itemName);

            estimatedItemIndex.add(estimate(data.getData()));
        }
    }

    public List<String> recommend(String nameLinkedItem, int numRecommendatios){
        List<String> listOfRecomendations = new ArrayList<>();

        return listOfRecomendations;
    }
}

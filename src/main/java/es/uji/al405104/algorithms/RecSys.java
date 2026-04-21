package es.uji.al405104.algorithms;

import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.excepciones.LikedItemNotFoundException;
import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecSys implements Algorithm<Table, List<Double>, Integer> {

    /// ATRIBUTOS ///
    private Algorithm<Table, List<Double>, Integer> instanciaAlgoritmo; //Almacena la instancia del algoritmo en uso
    private Table dataInitialisedItems; //Obtenemos la data del item
    private List<String> nameInitialisedItems; //Obtenemos el nombre del item
    private List<Integer> estimatedLabelIndex; //Para cada item, tenemos el index del label
    private Map<Integer, List<Integer>> labelToItems; //Para una label tenemos los items que almacena según su index


    /// CONSTRUCTOR ///
    public RecSys(Algorithm algorithm) {
        instanciaAlgoritmo = algorithm;
        dataInitialisedItems = new TableWithLabels();
        nameInitialisedItems = new ArrayList<>();
        estimatedLabelIndex = new ArrayList<>();
        labelToItems = new HashMap<>();
    }

    /// METODOS ///

    private void addItemToLabel(int labelIndex, int itemIndex) {
        if (!labelToItems.containsKey(labelIndex)) {
            labelToItems.put(labelIndex, new ArrayList<>());
        }

        List<Integer> labelList = labelToItems.get(labelIndex);
        labelList.add(itemIndex);

        labelToItems.put(labelIndex, labelList);
    }

    @Override
    public void train(Table trainData) throws InvalidDataException {
        if (trainData == null || trainData.getRowCount() == 0)
            throw new InvalidDataException("La tabla de datos no puede estar vacía o ser nula");

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

            dataInitialisedItems.addRow(data);
            nameInitialisedItems.add(itemName);

            Integer itemLabelIndex = estimate(data.getData());
            estimatedLabelIndex.add(itemLabelIndex);
            addItemToLabel(itemLabelIndex, i);
        }
    }

    public List<String> recommend(String nameLinkedItem, int numRecommendatios){
        if (!nameInitialisedItems.contains(nameLinkedItem))
            throw new LikedItemNotFoundException("Item no inicializado en la base de datos");

        List<String> listOfRecomendations = new ArrayList<>();
        int indexLinkedItem = nameInitialisedItems.indexOf(nameLinkedItem);

        int labelLinkedItem = estimatedLabelIndex.get(indexLinkedItem);
        List<Integer> itemsWithLabelAsLinkedItem = labelToItems.get(labelLinkedItem);

        int i = 0;
        while (numRecommendatios > listOfRecomendations.size() && i < itemsWithLabelAsLinkedItem.size()) {

            int indexRecomendedItem = itemsWithLabelAsLinkedItem.get(i);
            if (indexRecomendedItem == indexLinkedItem) {
                i++;
                continue;
            }
            String nameRecomendedItem = nameInitialisedItems.get(indexRecomendedItem);

            listOfRecomendations.add(nameRecomendedItem);
            i++;
        }

        return listOfRecomendations;
    }
}

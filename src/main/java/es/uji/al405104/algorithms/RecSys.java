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
    private Algorithm<Table, List<Double>, Integer> algorithm; //Almacena la instancia del algoritmo en uso
    private Table itemData; //Obtenemos la data del item
    private List<String> itemNames; //Obtenemos el nombre del item
    private List<Integer> itemLabels; //Para cada item, tenemos el index del label
    private Map<Integer, List<Integer>> labelToItems; //Para una label tenemos los items que almacena según su index


    /// CONSTRUCTOR ///
    public RecSys(Algorithm algorithm) {
        this.algorithm = algorithm;
        itemData = new TableWithLabels();
        itemNames = new ArrayList<>();
        itemLabels = new ArrayList<>();
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

        algorithm.train(trainData);
    }

    @Override
    public Integer estimate(List<Double> sample) {
        return algorithm.estimate(sample);
    }

    public void initialise(Table testData, List<String> testItemNames) {
        for (int i = 0; i < testItemNames.size(); i++) {
            Row data = testData.getRowAt(i);
            String itemName = testItemNames.get(i);

            itemData.addRow(data);
            itemNames.add(itemName);

            Integer itemLabelIndex = estimate(data.getData());
            itemLabels.add(itemLabelIndex);
            addItemToLabel(itemLabelIndex, i);
        }
    }

    public List<String> recommend(String likedItemsName, int numRecommendations){
        if (!itemNames.contains(likedItemsName))
            throw new LikedItemNotFoundException("Item no inicializado en la base de datos", likedItemsName);

        List<String> listOfRecomendations = new ArrayList<>();
        int targetIndex = itemNames.indexOf(likedItemsName);

        int targelLabel = itemLabels.get(targetIndex);
        List<Integer> similarItmIndices = labelToItems.get(targelLabel);

        int i = 0;
        while (numRecommendations > listOfRecomendations.size() && i < similarItmIndices.size()) {

            int indexRecomendedItem = similarItmIndices.get(i);
            if (indexRecomendedItem == targetIndex) {
                i++;
                continue;
            }
            String nameRecomendedItem = itemNames.get(indexRecomendedItem);

            listOfRecomendations.add(nameRecomendedItem);
            i++;
        }

        return listOfRecomendations;
    }
}

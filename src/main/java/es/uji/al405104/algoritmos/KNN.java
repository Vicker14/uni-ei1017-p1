package es.uji.al405104.algoritmos;

import es.uji.al405104.table.RowWithLabel;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;
import es.uji.al405104.algoritmos.MathFunctions;


import java.util.List;


public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {

  /// Atributos ///
  private TableWithLabels trainingData;

  /// Metodos publicos ///
  public void train(TableWithLabels data) {
    this.trainingData = data;
  }

  public Integer estimate(List<Double> data) {
    double lessDist = Double.MAX_VALUE; //Inicializado al maximo valor para asegurar el que el primero siempre gane
    String winningLabel = ""; //Guardamos el nombre de la flor ganadora

    for (int i = 0; i < this.trainingData.getRowCount(); i++) {

      RowWithLabel knownFlower = this.trainingData.getRowAt(i);

      double distance = MathFunctions.calculateEuclideanDistance(data, knownFlower.getData()); //Metodo que calcula la distancia

      if (distance < lessDist) {
        lessDist = distance;
        winningLabel = knownFlower.getLabel();
      }
    }

    return this.trainingData.getLabelAsInteger(winningLabel);
  }

}

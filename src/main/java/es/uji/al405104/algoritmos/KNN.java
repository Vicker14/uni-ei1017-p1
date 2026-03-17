package es.uji.al405104.algoritmos;

import es.uji.al405104.table.RowWithLabel;
import es.uji.al405104.table.TableWithLabels;


import java.util.List;


public class KNN {

  //PRUEBA //
  
  /// Atributos ///
  private TableWithLabels trainingData;

  /// Metodos Privados ///
  private double calculateEuclideanDistance(List<Double> p, List<Double> q) {

    if (p == null || p.isEmpty()) {
      throw new IllegalArgumentException("El punto p no puede ser nulo o vacio");
    }
    if (q == null || q.isEmpty()) {
      throw new IllegalArgumentException("El punto q no puede ser nulo o vacio");
    }
    if (p.size() != q.size()) {
      throw new IllegalArgumentException("Los puntos deben tener el mismo tamaño");
    }

    double sum = 0.0;
    for (int i = 0; i < p.size(); i++) {
      double diff = p.get(i) - q.get(i);
      sum += Math.pow(diff, 2);
    }
    return Math.sqrt(sum);
  }

  /// Metodos publicos ///
  public void train(TableWithLabels data) {
    this.trainingData = data;
  }

  public Integer estimate(List<Double> data) {
    double lessDist = Double.MAX_VALUE; //Inicializado al maximo valor para asegurar el que el primero siempre gane
    String winningLabel = ""; //Guardamos el nombre de la flor ganadora

    for (int i = 0; i < this.trainingData.getRowCount(); i++) {

      RowWithLabel knownFlower = this.trainingData.getRowAt(i);

      double distance = calculateEuclideanDistance(data, knownFlower.getData()); //Metodo que calcula la distancia

      if (distance < lessDist) {
        lessDist = distance;
        winningLabel = knownFlower.getLabel();
      }
    }

    return this.trainingData.getLabelAsInteger(winningLabel);
  }

}

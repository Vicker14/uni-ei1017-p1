package es.uji.al405104.algorithms;

import es.uji.al405104.excepciones.KMeansExceptions;
import es.uji.al405104.table.Table;

/*
    Un algoritmo para que pueda ser un algoritmo necesitamos 2 cosas
        -El metodo con el cual lo vamos a entrenar
        -El metodo con el que estimaremos y prediciremos el resultado

    Por otro lado para poder cumplir esto a su vez necesitamos 3 datos:
        -Los datos de entrenamiento (Table o TableWithLabels)
        -Los datos que le pasamos a estimate, Sample
        -El resultaod final de los algoritmos, Result
 */

public interface Algorithm<T extends Table, S, R> { //T: training data, S: sample, R: result

    // Cotruye el momdelo predictivo a partir de un conjutno de datos de entrenamiento
    public void train(T data) throws KMeansExceptions;

    //Realiza una estimacion o prediccion para una muestra basada en lo aprendido
    public R estimate(S sample);
}

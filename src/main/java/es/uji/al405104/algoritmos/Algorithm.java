package es.uji.al405104.algoritmos;

import es.uji.al405104.Excepciones.KMeansExceptions;
import es.uji.al405104.table.Table;

public interface Algorithm<T extends Table, S, R> {

    void train(T data) throws KMeansExceptions;

    R estimate(S sample);
}

package es.uji.al405104.table;

import java.util.*;

public class Row {

    List<Double> data;

    /// Contructor ///
    public Row(List<Double> info) {
        data = info;
    }

    /// Metodos ///
    public List<Double> getData() {
        return data;
    }
}

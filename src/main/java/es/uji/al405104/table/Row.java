package es.uji.al405104.table;

import java.util.*;

public class Row {
    List<Double> data;

    public Row(List<Double> info) {
        data = new ArrayList<>();
    }
    public List<Double> getData() {
        return data;
    }
}

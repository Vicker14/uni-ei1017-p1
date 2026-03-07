package es.uji.al405104.table;

import java.util.List;

public class RowWithLabel extends Row {
    String label;
    public RowWithLabel(List<Double> data, String label) {
        this.label = label;
        super(data);
    }

    public String getLabel() {
        return label;
    }
}

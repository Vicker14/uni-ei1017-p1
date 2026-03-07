package es.uji.al405104.table;

import java.util.*;

public class Table {
    List<String> headers;
    List<Row> rows;

    public Table() {
        headers = new ArrayList<>();
        rows = new ArrayList<>();
    }
    public List<String> getHeaders() {
        return headers;
    }
    public List<Row> getRows() {
        return rows;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
    public void addRow(Row row) {
        rows.add(row);
    }
    public int getRowCount() {
        return rows.size();
    }
    public Row getRowAt(int rowIndex) {
        return rows.get(rowIndex);
    }
}

package es.uji.al405104.table;

import java.util.*;

public class Table {

  List<String> headers;
  List<Row> rows;

  /// Contructor ///
  public Table() {
    headers = new ArrayList<>();
    rows = new ArrayList<>();
  }

  /// Metodos Getters ///
  public Row getRowAt(int rowIndex) {
    return rows.get(rowIndex);
  }

  public List<String> getHeaders() {
    return headers;
  }

  public List<Row> getRows() {
    return rows;
  }

  public int getRowCount() {
    return rows.size();
  }

  public List<Double> getColumnAt(int columnIndex) {
    List<Double> column = new ArrayList<>();

    for (Row row : this.rows) {
      column.add(row.getData().get(columnIndex));
    }

    return column;
  }

  /// Metodos Setters ///
  public void setHeaders(List<String> headers) {
    this.headers = headers;
  }

  public void addRow(Row row) {
    rows.add(row);
  }
}

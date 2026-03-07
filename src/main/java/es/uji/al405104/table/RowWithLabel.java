package es.uji.al405104.table;

import java.util.List;

public class RowWithLabel extends Row {

  String label;

  /// Constructor ///
  public RowWithLabel(List<Double> data, String label) {
    super(data);
    this.label = label;
  }
  /// Metodos ///
  public String getLabel() {
    return label;
  }
}

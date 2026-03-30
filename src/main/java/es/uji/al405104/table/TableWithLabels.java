package es.uji.al405104.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {

  private Map<String, Integer> labelsToIndex;

  /// Constructor ///
  public TableWithLabels() {
    super();
    this.labelsToIndex = new HashMap<>();
  }

  /// Metodos ///
  @Override
  public RowWithLabel getRowAt(int rowIndex) {
    return (RowWithLabel) super.getRowAt(rowIndex);
  }

  public Integer getLabelAsInteger(String label) {
    if (!this.labelsToIndex.containsKey(label)) {
      this.labelsToIndex.put(label, this.labelsToIndex.size());
    }

    return this.labelsToIndex.get(label);
  }

  public int getNumRows() {
    return this.getRowCount();
  }

}

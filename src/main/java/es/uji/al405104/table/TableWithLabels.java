package es.uji.al405104.table;

import java.util.ArrayList;
import java.util.List;

public class TableWithLabels extends Table {
    List<RowWithLabel> rows = new ArrayList<>();
    public TableWithLabels() {
        super();
        rows = new ArrayList<>();
    }
    @Override
    public RowWithLabel getRowAt(int rowIndex) {
        return rows.get(rowIndex);
    }

}

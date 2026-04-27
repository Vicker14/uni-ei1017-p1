package es.uji.al405104.csv;

import es.uji.al405104.table.Row;
import es.uji.al405104.table.RowWithLabel;
import es.uji.al405104.table.TableWithLabels;

import java.util.ArrayList;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    /// Constructor
    CSVLabeledFileReader(String source) {
        this.table = new TableWithLabels();
        this.source = source;
        openSource(this.source);
    }

    /// Metodos
    @Override
    void processHeaders(String headers) {
        List<String> headersList = new ArrayList<>();

        for (String header : headers.split(",")) {
            headersList.add(header.trim());
        }

        this.table.setHeaders(headersList.subList(0, headersList.size() - 1));
    }

    @Override
    void processData(String data) {
        // separamos la data en una lista de string
        List<String> dataStringList = new ArrayList<>();
        for (String item : data.split(",")) {
            dataStringList.add(item.trim());
        }
        String label = dataStringList.getLast();

        List<Double> dataDoubleList = new ArrayList<>();
        for (String item : dataStringList.subList(0, dataStringList.size() - 1))
            dataDoubleList.add(Double.parseDouble(item));

        Row row = new RowWithLabel(dataDoubleList, label);
        this.table.addRow(row);
    }
}

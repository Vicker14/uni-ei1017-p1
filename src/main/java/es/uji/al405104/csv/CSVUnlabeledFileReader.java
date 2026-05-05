package es.uji.al405104.csv;

import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;

import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    /// Constructor
    public CSVUnlabeledFileReader(String source) {
        this.table = new Table();
        this.source = source;
    }

    /// Metodos
    @Override
    void processHeaders(String headers) {
        List<String> headersList = new ArrayList<>();

        for (String header : headers.split(",")) {
            headersList.add(header.trim());
        }

        this.table.setHeaders(headersList);
    }

    @Override
    void processData(String data) {
        List<Double> dataList = new ArrayList<>();

        for (String header : data.split(",")) {
            dataList.add(Double.parseDouble(header.trim()));
        }

        table.addRow(new Row(dataList));
    }
}

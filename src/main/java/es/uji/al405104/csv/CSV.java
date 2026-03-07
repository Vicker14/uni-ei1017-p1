package es.uji.al405104.csv;

import es.uji.al405104.table.Row;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV {

    public List<String> splitStringToString(String line) {
        List<String> result = new ArrayList<>();

        for (String header : line.split(",")) {
            result.add(header.trim());
        }
        return result;
    }
    public List<Double> splitStringToDouble(String line) {
        List<Double> result = new ArrayList<>();

        for (String header : line.split(",")) {
            result.add(Double.parseDouble(header.trim()));
        }
        return result;
    }

    public Table readTable(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) throw new FileNotFoundException(filePath);

        Scanner sc = new Scanner(file);
        Table table = new Table();

        table.setHeaders(splitStringToString(sc.nextLine()));

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Row row = new Row(splitStringToDouble(line));
            table.addRow(row);
        }

        return table;
    }
}

package es.uji.al405104.csv;

import es.uji.al405104.table.Row;
import es.uji.al405104.table.RowWithLabel;
import es.uji.al405104.table.Table;
import es.uji.al405104.table.TableWithLabels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV {

  private List<String> splitStringToString(String line) {
    List<String> result = new ArrayList<>();

    for (String header : line.split(",")) {
      result.add(header.trim());
    }
    return result;
  }

  private List<Double> splitStringToDouble(String line) {
    List<Double> result = new ArrayList<>();

    for (String header : line.split(",")) {
      result.add(Double.parseDouble(header.trim()));
    }
    return result;
  }

  private String filePath(String fileName) throws IOException {
    try {
      return getClass().getClassLoader().getResource(fileName).toURI().getPath();
    }
    catch (URISyntaxException e) {
      throw new IOException();
    }
  }

  public Table readTable(String fileName) throws IOException {
    Scanner sc = new Scanner(new File(filePath(fileName)));
    Table table = new Table();

    table.setHeaders(splitStringToString(sc.nextLine()));

    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      Row row = new Row(splitStringToDouble(line));
      table.addRow(row);
    }

    return table;
  }

  public TableWithLabels readTableWithLabels(String fileName) throws IOException {
    Scanner sc = new Scanner(new File(filePath(fileName)));
    TableWithLabels table = new TableWithLabels();

    List<String> headers = new ArrayList<>(splitStringToString(sc.nextLine()));

    table.setHeaders(headers.subList(0, headers.size()-1));

    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      List<String> lineList = splitStringToString(line);

      List<Double> data = new ArrayList<>();
      for (String item : lineList) {
        try {
          data.add(Double.parseDouble(item));
        } catch (NumberFormatException e) {
          table.getLabelAsInteger(item);
          break;
        }
      }
      RowWithLabel row = new RowWithLabel(data, lineList.getLast());
      table.addRow(row);
    }

    return table;
  }
}

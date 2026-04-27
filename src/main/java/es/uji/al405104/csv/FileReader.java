package es.uji.al405104.csv;

import es.uji.al405104.table.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public abstract class FileReader<T extends Table> extends ReaderTemplate<T> {
    Scanner scanner;

    @Override
    void openSource(String source) {
        String path;
        try {
             path = getClass().getClassLoader().getResource(source).toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            this.scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void closeSource() {
        this.scanner.close();
    }

    @Override
    boolean hasMoreData() {
        return scanner.hasNextLine();
    }

    @Override
    String getNextData() {
        return scanner.nextLine();
    }
}

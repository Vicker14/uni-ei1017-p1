package es.uji.al405104.csv;

import es.uji.al405104.table.Table;

abstract class ReaderTemplate<T extends Table> {

    /// Atributos
    String source;
    T table;

    /// Metodos
    abstract void openSource(String source);
    abstract void closeSource();

    abstract void processHeaders(String headers);
    abstract void processData(String data);

    abstract boolean hasMoreData();
    abstract String getNextData();

    public final T readTableFromSource() {
        openSource(source);
        processHeaders(getNextData());
        while (hasMoreData()) {
            processData(getNextData());
        }
        closeSource();
        return table;
    }
}

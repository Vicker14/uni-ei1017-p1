package es.uji.al405104.excepciones;

public class InvalidClusterNumberException extends InvalidDataException {

    /*
        Esta excepcion tiene que extender de InvalidDataException porque en la interfaz Algorithm
        hemos, puesto que el metodo train pueda lanzar errores solo de InvalidDataException
     */

    private int invalidClustrerNumber;
    private int totalDataSize;

    public InvalidClusterNumberException(int n, int k) {
        super("Error: El numero de cluster es (" + n + ") no puede ser mayor que el total de datos (" + k + ")");

        this.invalidClustrerNumber = n;
        this.totalDataSize = k;
    }

    public int getNumberOfCusters() {
        return this.invalidClustrerNumber;
    }

    public int getTotalDataSize() {
        return this.totalDataSize;
    }
}

package es.uji.al405104.excepciones;

public class InvalidClusterNumberException extends KMeansExceptions{

    /*
        Esta excepcion tiene que extender de KMeansException porque en la interfaz Algorithm
        hemos, puesto que el metodo train pueda lanzar errores solo de KMeansException
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

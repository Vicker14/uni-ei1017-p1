package es.uji.al405104.Excepciones;

public class InvalidClusterNumberException extends KMeansExceptions{

    /*
        Esta excepcion tiene que extender de KMeansException porque en la interfaz Algorithm
        hemos puesto que el metodo train pueda lanzar errores solo de KMeansException
     */
    public InvalidClusterNumberException(int k, int n) {
        super("Error: Has pedido " + k + " grupos poro solo hay " + n + " datos");
    }
}

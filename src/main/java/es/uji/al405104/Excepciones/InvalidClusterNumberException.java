package es.uji.al405104.Excepciones;

public class InvalidClusterNumberException extends KMeansExceptions{

    public InvalidClusterNumberException(int k, int n) {
        super("Error: Has pedido " + k + " grupos poro solo hay " + n + " datos");
    }
}

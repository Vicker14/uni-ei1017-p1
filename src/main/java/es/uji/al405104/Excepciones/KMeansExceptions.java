package es.uji.al405104.Excepciones;

public class KMeansExceptions extends Exception {

    public  KMeansExceptions(String message) {
        super(message); //Enviamos el texto de error a la clase Exception
    }
}

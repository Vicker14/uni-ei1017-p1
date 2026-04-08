package es.uji.al405104.excepciones;

public class KMeansExceptions extends Exception {

    // Usado para cuando los datos proporcionados son nulo o no válidos
    public  KMeansExceptions(String message) {
        super(message); //Enviamos el texto de error a la clase Exception
    }
}

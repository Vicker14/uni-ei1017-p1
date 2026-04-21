package es.uji.al405104.excepciones;

public class InvalidDataException extends Exception {

    // Usado para cuando los datos proporcionados son nulo o no válidos
    public InvalidDataException(String message) {
        super(message); //Enviamos el texto de error a la clase Exception
    }
}

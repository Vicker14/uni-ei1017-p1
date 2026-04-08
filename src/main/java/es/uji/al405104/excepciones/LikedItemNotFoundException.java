package es.uji.al405104.excepciones;

public class LikedItemNotFoundException extends RuntimeException {
  public LikedItemNotFoundException(String message) {
    super(message);
  }
}

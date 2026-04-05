package es.uji.al405104.Excepciones;

public class LikedItemNotFoundException extends RuntimeException {
  public LikedItemNotFoundException(String message) {
    super(message);
  }
}

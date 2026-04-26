package es.uji.al405104.excepciones;

public class LikedItemNotFoundException extends RuntimeException {
    private final String itemNotFound;

    public LikedItemNotFoundException(String message, String itemNotFound) {
        super(message);
        this.itemNotFound = itemNotFound;
    }

    public String getItemNotFound() {
        return itemNotFound;
    }
}

package es.uji.al405104.javafx.controlador;

import es.uji.al405104.excepciones.InvalidDataException;

public interface Controlador {
    void recomendar() throws InvalidDataException;
    void filtroBusqueda();
    void actualizarLimites();
}

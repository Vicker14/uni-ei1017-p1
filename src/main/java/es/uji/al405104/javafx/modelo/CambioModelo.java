package es.uji.al405104.javafx.modelo;

import es.uji.al405104.excepciones.InvalidDataException;

public interface CambioModelo {
    void generaRecomendaciones(
            String nombreSeleccionada,
            int numeroRecomendaciones,
            String algoritmoSeleccionado,
            String distanciaSeleccionada
    ) throws InvalidDataException;

    int obtenerMaximoRecomendaciones(
            String cancionSeleccionada,
            String algoritmoSeleccionado,
            String distanciaSeleccionada
    ) throws Exception;
    void generaListaBusqueda(String busqueda);
}

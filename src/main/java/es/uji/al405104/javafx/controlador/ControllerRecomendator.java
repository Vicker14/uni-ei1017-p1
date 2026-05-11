package es.uji.al405104.javafx.controlador;

import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.javafx.modelo.CambioModelo;
import es.uji.al405104.javafx.vista.InterrogaVista;

public class ControllerRecomendator implements Controlador {

    /// ATRIBUTOS
    CambioModelo modelo;
    InterrogaVista vista;

    /// MÉTODOS
    //Metodo para actualizar las recomendaciones del modelo
    public void recomendar() throws InvalidDataException {
        String cancionSeleccionada = vista.getCancionSeleccionada();
        int numCancionesRecomendadas = vista.getNumRecomendaciones();
        String algoritmoSeleccionado = vista.getAlgoritmoSeleccionado();
        String distanciaSeleccionada = vista.getDistanciaSeleccionada();
        modelo.generaRecomendaciones(
                cancionSeleccionada,
                numCancionesRecomendadas,
                algoritmoSeleccionado,
                distanciaSeleccionada
        );
    }

    /// SETTERS
    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }
    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }
}
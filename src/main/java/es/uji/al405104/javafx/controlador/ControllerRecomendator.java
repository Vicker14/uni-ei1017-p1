package es.uji.al405104.javafx.controlador;

import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.javafx.modelo.CambioModelo;
import es.uji.al405104.javafx.vista.InterrogaVista;
import es.uji.al405104.javafx.vista.InformaVista;
/*
 * CLASE: ControllerRecomendator
 * * FUNCIÓN PRINCIPAL:
 * Es la logistica de JavaFX. Su responsabilidad es organizar la comunicación
 * entre la Vista y el Modelo. No realiza calculos ni dibuja elementos,- simplemente lee las
 * elecciones del usuario desde las interfaces de la vista, ordena al modelo procesar los
 * datos y, una vez obtiene los resultados, manda la orden de actualizacion visual.
 *
 * Ademas, garantiza que de la interfaz se ajuste dinamicamente a los limites del
 * Spinner. Si el cluster de una cancion es pequeño, el controlador limita el
 * rango de seleccion del usuario para evitar errores en el motor de calculo.
*/
public class ControllerRecomendator implements Controlador {

    /// ATRIBUTOS ///
    private CambioModelo modelo;
    private InterrogaVista interrogaVista;
    private InformaVista informaVista;

    /// METODOS ///
    @Override
    public void recomendar() throws InvalidDataException {
        //LEER DE LA VISTA
        String cancionSeleccionada = interrogaVista.getCancionSeleccionada();
        int numCancionesRecomendadas = interrogaVista.getNumRecomendaciones();
        String algoritmoSeleccionado = interrogaVista.getAlgoritmoSeleccionado();
        String distanciaSeleccionada = interrogaVista.getDistanciaSeleccionada();

        //MANDAR AL MODELO
        modelo.generaRecomendaciones(
                cancionSeleccionada,
                numCancionesRecomendadas,
                algoritmoSeleccionado,
                distanciaSeleccionada
        );
    }

    @Override
    public void filtroBusqueda() {
        modelo.generaListaBusqueda(interrogaVista.getTextoBusqueda());
    }

    @Override
    public void actualizarLimites() {
        //LEER el estado actual
        String cancion = interrogaVista.getCancionSeleccionada();
        String algoritmo = interrogaVista.getAlgoritmoSeleccionado();
        String distancia = interrogaVista.getDistanciaSeleccionada();

        if (cancion != null) {
            try {
                //Numero maximo de recomendaciones de esa cancion
                int maximoPosible = modelo.obtenerMaximoRecomendaciones(cancion, algoritmo, distancia);

                //Actualizacion del Spinner
                informaVista.setMaximoSpinner(maximoPosible);

            } catch (Exception e) {
                System.err.println("Error al calcular los límites del Spinner: " + e.getMessage());
            }
        }
    }

    /// SETTERS
    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista interrogaVista, InformaVista informaVista) {
        this.interrogaVista = interrogaVista;
        this.informaVista = informaVista;
    }
}
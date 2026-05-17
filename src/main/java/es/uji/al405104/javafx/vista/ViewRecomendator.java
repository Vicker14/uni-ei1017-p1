package es.uji.al405104.javafx.vista;

import es.uji.al405104.excepciones.InvalidDataException;
import es.uji.al405104.javafx.controlador.Controlador;
import es.uji.al405104.javafx.modelo.InterrogaModelo;
import es.uji.al405104.javafx.vista.paneles.PanelConfiguracion;
import es.uji.al405104.javafx.vista.paneles.PanelResultados;
import es.uji.al405104.javafx.vista.paneles.PanelSeleccionCancion;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

/*
 * CLASE: ViewRecomendator
 *
 * FUNCION PRINCIPAL:
 * Es la clase que ensambla la interfaz grafica. Su mision
 * es unir los tres paneles independientes (Configuración, Selección y Resultados)
 * en una unica ventana.
 *
 * RESPONSABILIDADES:
 * 1. Gestion de Eventos: Vigila lo que hace el usuario. Si tocas un botón o
 * cambias una opción, esta clase avisa al resto del programa para que reaccione.
 * 2. Comunicacion: Sirve de puente para que el Controlador reciba los datos de 
 * la interfaz y sepa qué tiene que calcular en cada momento.
 * 3. Usabilidad: Bloquea botones cuando no se pueden usar o actualiza la lista de canciones
 * al momento si cambias el número de recomendaciones.
 */

public class ViewRecomendator implements InterrogaVista, InformaVista {

    /// ATRIBUTOS ///
    private InterrogaModelo modelo; //Solo para leer datos
    private Controlador controlador; //Modelo que calcula las cosass

    private final Stage primaryStage; //Ventana principal del sistema

    // PANELES DE LA INTERFAZ
    private PanelConfiguracion panelConfiguracion;
    private PanelSeleccionCancion panelSeleccionCancion;
    private PanelResultados panelResultados;
    private boolean yaRecomendado = false; //Avisador de si el usuario a pulsado el boton Recomendar

    /// CONSTRUCTOR ///
    public ViewRecomendator(Stage stage) {
        this.primaryStage = stage;
    }

    //Metodo que carga la interfaz
    public void loadInterface() {
        //Instanciar paneles
        panelConfiguracion = new PanelConfiguracion();
        panelSeleccionCancion = new PanelSeleccionCancion(modelo.getNombresCanciones());
        panelResultados = new PanelResultados();

        //Conectamos los botones y la lista para que sepan que hacer
        conectarEventos();

        //Ensamblaje de la escena
        HBox root = new HBox();
        root.getStyleClass().add("root-pane"); //CSS
        root.getChildren().addAll(panelConfiguracion, panelSeleccionCancion, panelResultados); //Orden de los paneles

        Scene scene = new Scene(root, 1600, 900); //Ajuste de la ventana (Horizontal, Vertical)
        scene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());

        //Configuracion de la venta
        primaryStage.setTitle("Recomendador de Canciones");
        primaryStage.setScene(scene);
        primaryStage.show(); //Mostramos la ventana
    }

    public void conectarListaCancionesBtnRecomendar() {
        panelSeleccionCancion.getListaCanciones().getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                // 1 Activa/Desactiva boton
                panelConfiguracion.getBtnRecomendar().setDisable(newVal == null);
                // 2 Reseteamos el estado de Recomendar para que tenga que volver a pulsar el boton
                yaRecomendado = false;
                // 3 Avisa al Controlador que recalcule el maximo dinamico
                if(newVal != null) {
                    controlador.actualizarLimites();
                }
            }
        );
    }
    private void conectarEventos() {
        // EVENTO 1 El usuario cambia de cancion
        conectarListaCancionesBtnRecomendar();
        // EVENTO 2: El usuario cambia de algoritmo (KMeans/KNN)
        panelConfiguracion.getAlgoritmoToggleGroup().selectedToggleProperty().addListener(
                (obs, old, neu) -> {
                    // Solo actualizamos limites si ya habia una cancion seleccionada
                    if (panelSeleccionCancion.getCancionSeleccionada() != null) {
                        controlador.actualizarLimites();
                    }
                }
        );
        // EVENTO 3: Accionado el btono Recomendar
        panelConfiguracion.getBtnRecomendar().setOnAction(actionEvent -> {
            yaRecomendado = true; // Activamos el boton
            ejecutarRecomendacion();
        });
        // EVENTO 4: Escuchar los cambios en el Spinner (numero de canciones)
        panelConfiguracion.getSpinnerRecomendaciones().valueProperty().addListener(
                (obs, oldVal, newVal) -> {
                    // Solo actualizamos automaticamente si ya pulso el boton antes
                    if (yaRecomendado) {
                        ejecutarRecomendacion();
                    }
                }
        );
        // EVENTO 5: Clic en el boton buscar del BUSCADOR
        panelSeleccionCancion.getBtnBuscar().setOnAction(actionEvent -> {
            controlador.filtroBusqueda();
        });
    }

    //METODO PRIVADO: Para no repetir el try/catch en el boton y en el spinner
    private void ejecutarRecomendacion() {
        try {
            controlador.recomendar();
        } catch (Exception e) {
            // Aqui en un futuro podrias mostrar un Alert.error en pantalla
            throw new RuntimeException(e);
        }
    }

    /// IMPLEMENTACION DE InformaVista
    @Override
    public void mostrarResultados() {
        // Delega la actualizacion visual al panel correspondiente
        panelResultados.actualizarResultados(modelo.getListaRecomendaciones());
    }
    public void mostrarBusqueda() {
        List<String> listaBusqueda = modelo.getListaBusqueda();
        panelSeleccionCancion.setListaCanciones(listaBusqueda);
        // Conectamos el nuevo panel de selección con la búsqueda
        conectarListaCancionesBtnRecomendar();
    }

    /// IMPLEMENTACION DE InterrogaVista
    @Override
    public String getCancionSeleccionada() { return panelSeleccionCancion.getCancionSeleccionada(); }

    @Override
    public int getNumRecomendaciones() { return panelConfiguracion.getNumRecomendaciones(); }

    @Override
    public String getAlgoritmoSeleccionado() { return panelConfiguracion.getAlgoritmoSeleccionado(); }

    @Override
    public String getDistanciaSeleccionada() { return panelConfiguracion.getDistanciaSeleccionada(); }

    @Override
    public String getTextoBusqueda() {
        return panelSeleccionCancion.getTextoBusqueda();
    }

    /// SETTERS
    public void setModelo(InterrogaModelo modelo) { this.modelo = modelo; }
    public void setControlador(Controlador controlador) { this.controlador = controlador; }

    @Override
    public void setMaximoSpinner(int maximo) {
        panelConfiguracion.setMaximoRecomendacionesPosibles(maximo);
    }
}
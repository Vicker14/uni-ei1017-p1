package es.uji.al405104.javafx.vista.paneles;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;


/*
 * CLASE: PanelConfiguracion
 * Panel Lateral Izquierdo (Vista)
 * FUNCION PRINCIPAL:
 * Su responsabilidad es presentar y capturar las preferencias del usuario respecto a:
 * 1. El algoritmo de recomendación (KNN/KMeans).
 * 2. La metrica de distancia (Euclidiana/Manhattan).
 * 3. La cantidad de canciones deseadas mediante un Spinner.
 *
 * Permite ajustar en tiempo real el limite maximo del Spinner.
 * Esto asegura que el usuario solo pueda pedir una cantidad de canciones que
 * realmente existan en el grupo o cluster de la canción seleccionada.
 */

public class PanelConfiguracion extends VBox {

    //ToggleGroup agrupa los botones para que cuando se seleccione uno automaticamente se desmarque otro
    private ToggleGroup grupoAlgoritmo; //Botones algoritmos
    private ToggleGroup grupoDistancia; //Botones distancia

    private Spinner<Integer> spinnerRecomendaciones; //Boton para seleccionar el numero de canciones en Resultados

    private Button btnRecomendar; //Boton para recomendar

    /// CONSTRUCTOR ///
    public PanelConfiguracion() {
        super(15); //Configuramos la caja vertical 15px de separacion
        this.setPadding(new Insets(25)); //25px de margen por lados
        this.getStyleClass().add("panel-configuracion");
        construirInterfaz();//Lamamos al metodo para dibujar la vista
    }

    private void construirInterfaz() {

        // --- SECCION 1: ALGORITMO ---
        Label lblAlgoritmo = new Label("Tipo de Recomendación");
        lblAlgoritmo.getStyleClass().add("titulo-seccion");

        RadioButton rbKNN = new RadioButton("KNN");
        RadioButton rbKMeans = new RadioButton("KMeans");

        grupoAlgoritmo = new ToggleGroup();
        rbKNN.setToggleGroup(grupoAlgoritmo);
        rbKMeans.setToggleGroup(grupoAlgoritmo);
        rbKNN.setSelected(true); //KNN marcado por defecto al abrir la app

        // SECCION 2: DISNTANCIA ---
        Label lblDistancia = new Label("Métrica de Distancia");
        lblDistancia.getStyleClass().add("titulo-seccion");

        RadioButton rbEuclidea = new RadioButton("Euclidiana");
        RadioButton rbManhattan = new RadioButton("Manhattan");

        grupoDistancia = new ToggleGroup();
        rbEuclidea.setToggleGroup(grupoDistancia);
        rbManhattan.setToggleGroup(grupoDistancia);
        rbEuclidea.setSelected(true); //Eucledian por defecto

        // --- SECCION 3: Numero de canciones (Spinner)---
        Label lblNum = new Label("Nº de canciones");
        lblNum.getStyleClass().add("titulo-seccion");
        spinnerRecomendaciones = new Spinner<>(1, 20, 5);

        // --- SECCION 4: BOTON RECOMENDAR ---
        btnRecomendar = new Button("Recomendar");
        btnRecomendar.getStyleClass().add("boton-recomendar");
        btnRecomendar.setDisable(true); //Desactivado por defecto hasta que se seleccione una cancion

        // ---MONTAJE FINAL ---
        this.getChildren().addAll(
                new VBox(5, lblAlgoritmo, rbKNN, rbKMeans), new Separator(),
                new VBox(5, lblDistancia, rbEuclidea, rbManhattan), new Separator(),
                new VBox(5, lblNum, spinnerRecomendaciones),
                crearEspaciador(), btnRecomendar
        );
    }

    //Metodo para que el boton Recomendar se quede abajo del todo crea un hueco que ocupa todo el espacio sobrante
    private Region crearEspaciador() {
        Region espaciador = new Region();
        VBox.setVgrow(espaciador, Priority.ALWAYS);
        return espaciador;
    }

    // --- GETTERS PUBLICOS ---
    public String getAlgoritmoSeleccionado() { return ((RadioButton) grupoAlgoritmo.getSelectedToggle()).getText(); }
    public String getDistanciaSeleccionada() { return ((RadioButton) grupoDistancia.getSelectedToggle()).getText(); }

    public int getNumRecomendaciones() { return spinnerRecomendaciones.getValue(); }
    public Button getBtnRecomendar() { return btnRecomendar; }
    public Spinner<Integer> getSpinnerRecomendaciones() {return spinnerRecomendaciones;}

    public ToggleGroup getAlgoritmoToggleGroup() { return grupoAlgoritmo; }
    public ToggleGroup getDistanciaToggleGroup() { return grupoDistancia; }
    public void setMaximoRecomendacionesPosibles(int nuevoMaximo) {
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerRecomendaciones.getValueFactory();

        //Actualizamos el techo maximo
        factory.setMax(nuevoMaximo);

        if (spinnerRecomendaciones.getValue() > nuevoMaximo) { //Si el usuario tiene seleccionado un spinner mayor baja automaticamente
            factory.setValue(nuevoMaximo);
        }
    }




}
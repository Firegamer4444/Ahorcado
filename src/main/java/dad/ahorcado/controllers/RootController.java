package dad.ahorcado.controllers;

import dad.ahorcado.model.Puntuacion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // controllers

    private PartidaController partidaController = new PartidaController();
    private PalabrasController palabrasController = new PalabrasController();
    private PuntuacionesController puntuacionesController = new PuntuacionesController();

    // view

    @FXML
    private Tab palabraTab;

    @FXML
    private Tab partidaTab;

    @FXML
    private Tab puntuacionTab;

    @FXML
    private TextField nameField;

    @FXML
    private TabPane root;

    public RootController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        palabraTab.setContent(palabrasController.getRoot());
        puntuacionTab.setContent(puntuacionesController.getRoot());

        // bindings

        partidaController.palabrasListProperty().bind(palabrasController.palabrasListProperty());
        palabraTab.disableProperty().bind(partidaController.hasEndedProperty().not());
    }

    public TabPane getRoot() {
        return root;
    }

    public PalabrasController getPalabrasController() {
        return palabrasController;
    }

    public PuntuacionesController getPuntuacionesController() {
        return puntuacionesController;
    }

    @FXML
    void onStartAction(ActionEvent event) {
        if (!nameField.getText().isEmpty()){
            partidaTab.setContent(partidaController.getRoot());
            partidaController.setNombre(nameField.getText());
            partidaController.iniciarPartida();

            boolean scoreExists = false;
            for (Puntuacion puntuacion : puntuacionesController.getPuntuaciones()){
                if (puntuacion.getNombre().equals(nameField.getText())){
                    partidaController.nombreProperty().bind(puntuacion.nombreProperty());
                    partidaController.puntosProperty().bindBidirectional(puntuacion.puntosProperty());
                    scoreExists = true;
                }
            }
            if (!scoreExists){
                puntuacionesController.getPuntuaciones().add(new Puntuacion(new SimpleStringProperty(nameField.getText()) , new SimpleIntegerProperty(0)));
                partidaController.nombreProperty().bind(puntuacionesController.getPuntuaciones().get(puntuacionesController.getPuntuaciones().size() - 1).nombreProperty());
                partidaController.puntosProperty().bindBidirectional(puntuacionesController.getPuntuaciones().get(puntuacionesController.getPuntuaciones().size() - 1).puntosProperty());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ahorcado");
            alert.setHeaderText("Error");
            alert.setContentText("No puedes dejar el nombre vacio");
            alert.show();
        }

    }
}

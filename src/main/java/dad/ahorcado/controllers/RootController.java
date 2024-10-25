package dad.ahorcado.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
        partidaTab.setContent(partidaController.getRoot());
        partidaController.setNombre(nameField.getText());
    }
}

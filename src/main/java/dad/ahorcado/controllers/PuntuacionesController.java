package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PuntuacionesController implements Initializable {

    @FXML
    private GridPane root;

    @FXML
    private ListView<?> scoreList;

    public PuntuacionesController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/puntuacionesView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public GridPane getRoot() {
        return root;
    }
}

package dad.ahorcado.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PartidaController implements Initializable {

    // model

    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty palabraSecreta = new SimpleStringProperty();

    // view

    @FXML
    private TextField guessField;

    @FXML
    private ImageView hangedmanImage;

    @FXML
    private Label lettersLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label wordLabel;

    public PartidaController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/partidaView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // bindings
        nameLabel.textProperty().bind(nombre.concat(":"));
        wordLabel.textProperty().bind(palabraSecreta);

    }

    public BorderPane getRoot() {
        return root;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    @FXML
    void onGuessAction(ActionEvent event) {

    }

    @FXML
    void onLetterAction(ActionEvent event) {

    }


}

package dad.ahorcado.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PalabrasController implements Initializable{

    // model

    private ListProperty<String>  palabrasList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private StringProperty palabra = new SimpleStringProperty();
    private StringProperty palabraSeleccionada = new SimpleStringProperty();

    // view

    @FXML
    private GridPane root;

    @FXML
    private ListView<String> wordsList;

    @FXML
    private TextField wordField;

    public PalabrasController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/palabrasView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bindings

        wordsList.itemsProperty().bind(palabrasList);
        palabra.bind(wordField.textProperty());
        palabraSeleccionada.bind(wordsList.getSelectionModel().selectedItemProperty());
    }

    public GridPane getRoot() {
        return root;
    }

    public ObservableList<String> getPalabrasList() {
        return palabrasList.get();
    }

    public ListProperty<String> palabrasListProperty() {
        return palabrasList;
    }

    public void setPalabrasList(ObservableList<String> palabrasList) {
        this.palabrasList.set(palabrasList);
    }

    @FXML
    void onAddAction(ActionEvent event) {
        palabrasList.add(palabra.get());
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
        palabrasList.remove(palabraSeleccionada.get());
    }

}
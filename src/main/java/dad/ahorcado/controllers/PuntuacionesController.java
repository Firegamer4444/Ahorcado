package dad.ahorcado.controllers;

import dad.ahorcado.comparator.PuntuacionesComparator;
import dad.ahorcado.model.Puntuacion;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PuntuacionesController implements Initializable {

    // model

    private ListProperty<Puntuacion> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList(
            Puntuacion -> new Observable[]{Puntuacion.nombreProperty() , Puntuacion.puntosProperty()}
    ));

    // view

    @FXML
    private GridPane root;

    @FXML
    private ListView<Puntuacion> scoreList;

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

        // Bindings

        scoreList.itemsProperty().bind(new SimpleListProperty<>(puntuaciones.sorted(new PuntuacionesComparator())));


    }

    public GridPane getRoot() {
        return root;
    }

    public ObservableList<Puntuacion> getPuntuaciones() {
        return puntuaciones.get();
    }

    public ListProperty<Puntuacion> puntuacionesProperty() {
        return puntuaciones;
    }

    public void setPuntuaciones(ObservableList<Puntuacion> puntuaciones) {
        this.puntuaciones.set(puntuaciones);
    }
}

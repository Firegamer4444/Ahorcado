package dad.ahorcado.model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class PuntuacionList extends SimpleListProperty<Puntuacion> {

    public PuntuacionList() {
        super(FXCollections.observableArrayList());
    }

}

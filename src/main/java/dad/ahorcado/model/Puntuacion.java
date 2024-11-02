package dad.ahorcado.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Puntuacion {
    private StringProperty nombre;
    private IntegerProperty puntos;

    public Puntuacion(StringProperty nombre, IntegerProperty puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
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

    public int getPuntos() {
        return puntos.get();
    }

    public IntegerProperty puntosProperty() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos.set(puntos);
    }

    @Override
    public String toString() {
        return nombreProperty().get() + ": " + puntosProperty().get();
    }
}

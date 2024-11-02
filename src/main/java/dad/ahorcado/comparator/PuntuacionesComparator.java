package dad.ahorcado.comparator;

import dad.ahorcado.model.Puntuacion;

import java.util.Comparator;

public class PuntuacionesComparator implements Comparator<Puntuacion> {

    @Override
    public int compare(Puntuacion p1, Puntuacion p2) {
        return p2.getPuntos() - p1.getPuntos();
    }
}

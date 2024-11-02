package dad.ahorcado.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class PartidaController implements Initializable {


    // model

    private ListProperty<String> palabrasList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private BooleanProperty hasEnded = new SimpleBooleanProperty(true);
    private IntegerProperty fallos = new SimpleIntegerProperty();
    private ObjectProperty<Image> ahorcadoImagen = new SimpleObjectProperty<>();
    private StringProperty nombre = new SimpleStringProperty();
    private IntegerProperty puntos = new SimpleIntegerProperty();
    private StringProperty adivinarPalabra = new SimpleStringProperty();
    private StringProperty palabraOculta = new SimpleStringProperty("");
    private StringProperty letrasAdivinadas = new SimpleStringProperty("");


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

    @FXML
    private Button guessLetterButton;

    @FXML
    private Button guessWordButton;

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
        scoreLabel.textProperty().bind(puntos.asString());
        lettersLabel.textProperty().bind(letrasAdivinadas);
        adivinarPalabra.bind(guessField.textProperty());
        wordLabel.textProperty().bind(Bindings.createStringBinding(this::ocultarPalabra, palabraOculta , letrasAdivinadas));
        hangedmanImage.imageProperty().bind(ahorcadoImagen);

        guessField.disableProperty().bind(hasEnded);
        guessLetterButton.disableProperty().bind(hasEnded);
        guessWordButton.disableProperty().bind(hasEnded);

        fallos.addListener((o , ov , nv) -> {
            if (!nv.equals(9)){
                ahorcadoImagen.set(new Image(String.valueOf(getClass().getResource("/images/" + nv + ".png"))));
            }
            else {
                finalizarPartida(false);
            }
        });

    }

    private String ocultarPalabra() {

        String letras = letrasAdivinadas.get().toLowerCase() + letrasAdivinadas.get().toUpperCase();

        // Se agregan todas las versiones acentuadas de las letras adivinadas
        if (letrasAdivinadas.get().toLowerCase().contains("a")) letras += "áÁ";
        if (letrasAdivinadas.get().toLowerCase().contains("e")) letras += "éÉ";
        if (letrasAdivinadas.get().toLowerCase().contains("i")) letras += "íÍ";
        if (letrasAdivinadas.get().toLowerCase().contains("o")) letras += "óÓ";
        if (letrasAdivinadas.get().toLowerCase().contains("u")) letras += "úüÚÜ";

        String regex = letrasAdivinadas.get().isEmpty() ? "[\\wáéíóúÁÉÍÓÚñÑüÜ]" : "[^" + letras + "]";
        return palabraOculta.get().replaceAll(regex, "_");
    }

    public void iniciarPartida(){
        Random random = new Random();
        int randomIndex = random.nextInt(palabrasList.size() - 1);
        palabraOculta.set(palabrasList.get().get(randomIndex));
        letrasAdivinadas.set("");
        hasEnded.set(false);

        fallos.set(0);
    }

    private void finalizarPartida(Boolean partidaGanada) {
        Alert alertClose = new Alert(Alert.AlertType.CONFIRMATION);
        alertClose.setTitle("Ahorcado");
        alertClose.setHeaderText(partidaGanada ? "Has ganado" : "Has perdido");
        alertClose.setContentText("¿Quieres iniciar una nueva partida?");
        ButtonType botonAfirmativo = new ButtonType("Si");
        ButtonType botonNegativo = new ButtonType("No" );
        alertClose.getButtonTypes().setAll(botonNegativo , botonAfirmativo);

        Optional<ButtonType> result = alertClose.showAndWait();
        if (result.get() == botonAfirmativo){
            iniciarPartida();
        }
        else if (result.get() == botonNegativo){
            hasEnded.set(true);
        }
    }

    @FXML
    void onGuessAction(ActionEvent event) {
        if (adivinarPalabra.get().equals(palabraOculta.get())){
            puntos.set(puntos.get() + palabraOculta.get().length() + 9 - fallos.get());
            finalizarPartida(true);
        }
        else {
            fallos.set(fallos.get() + 1);
        }
    }

    @FXML
    void onLetterAction(ActionEvent event) {
        if (adivinarPalabra.get().length() == 1){
            if (!letrasAdivinadas.get().contains(adivinarPalabra.get().toUpperCase())){
                letrasAdivinadas.set(letrasAdivinadas.get() + " " + adivinarPalabra.get().toUpperCase());
                if (!Normalizer.normalize(palabraOculta.get() , Normalizer.Form.NFD).toUpperCase().contains(adivinarPalabra.get().toUpperCase())){
                    fallos.set(fallos.get() + 1);
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ahorcado");
            alert.setHeaderText("Error");
            alert.setContentText("Debes introducir un único carácter al adivinar una letra");
            alert.show();
        }
    }

    @FXML
    void onRestartAction(ActionEvent event) {
        iniciarPartida();
    }

    // getters and setters

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

    public int getPuntos() {
        return puntos.get();
    }

    public IntegerProperty puntosProperty() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos.set(puntos);
    }

    public boolean isHasEnded() {
        return hasEnded.get();
    }

    public BooleanProperty hasEndedProperty() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded.set(hasEnded);
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
}

package dad.ahorcado;

import dad.ahorcado.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class AhorcadoApp extends Application {
    private static final File DATA_DIR = new File(System.getProperty("user.home") , ".ahorcado");
    private static final File PALABRAS_FILE = new File(DATA_DIR, "palabras.txt");

    private RootController rootController = new RootController();

    @Override
    public void init() throws Exception {
        super.init();
        if (PALABRAS_FILE.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(PALABRAS_FILE))){
                String linea = "";
                while ((linea = reader.readLine()) != null){
                    rootController.getPalabrasController().getPalabrasList().add(linea);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(rootController.getRoot());

        primaryStage.setTitle("Ahorcado");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (!DATA_DIR.exists()){
            DATA_DIR.mkdir();
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PALABRAS_FILE))){
            for (int i = 0; i < rootController.getPalabrasController().getPalabrasList().size(); i++){
                writer.write(rootController.getPalabrasController().getPalabrasList().get(i));
                writer.write("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

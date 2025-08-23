package br.edu.ifba.saj.fwads;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import br.edu.ifba.saj.fwads.model.Sala;
import br.edu.ifba.saj.fwads.model.SalaManager;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        seedInitialData();
    
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Login.fxml"),
                "Login.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
    
        stage.setTitle("Sistema de Reservas");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    private void seedInitialData() {
        SalaManager sm = AppState.getSalaManager();
        if (sm.listarSalas().isEmpty()) {
            sm.cadastrarSala(new Sala("Sala 101", "Sala de Aula", 40,
                    new String[]{"Projetor", "Ar-Condicionado"}));
            sm.cadastrarSala(new Sala("Lab Redes", "Laboratório", 25,
                    new String[]{"Switch", "Roteadores"}));
            sm.cadastrarSala(new Sala("Auditório 1", "Auditório", 120,
                    new String[]{"Sistema de Som", "Microfone"}));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

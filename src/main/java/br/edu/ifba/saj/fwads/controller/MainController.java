package br.edu.ifba.saj.fwads.controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import br.edu.ifba.saj.fwads.AppState;
import br.edu.ifba.saj.fwads.model.Sala;
import br.edu.ifba.saj.fwads.model.SalaManager;
import br.edu.ifba.saj.fwads.model.TypeManager;

public class MainController {

    @FXML private TableView<Sala> tableSalas;
    @FXML private TableColumn<Sala, String> colNome;
    @FXML private TableColumn<Sala, String> colTipo;
    @FXML private TableColumn<Sala, Number> colCapacidade;

    @FXML private Button btnPerfil;
    @FXML private Button btnCadastrarSala;

    @FXML
    private void initialize() {
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo()));
        colCapacidade.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCapacidade()));

        tableSalas.setRowFactory(tv -> {
            TableRow<Sala> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    abrirTelaReserva(row.getItem());
                }
            });
            return row;
        });

        carregarSalas();
    }

    private void carregarSalas() {
        tableSalas.getItems().setAll(AppState.getSalaManager().listarSalas());
    }

    @FXML
private void onPerfilClick() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Profile.fxml"),
                "Profile.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) tableSalas.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Perfil do Usuário");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir Perfil: " + e.getMessage()).showAndWait();
    }
}

@FXML
private void onCadastrarSalaClick() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/CadastroSala.fxml"),
                "CadastroSala.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) tableSalas.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Cadastrar Sala");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir Cadastrar Sala: " + e.getMessage()).showAndWait();
    }
}

private void abrirTelaReserva(Sala salaSelecionada) {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Reservas.fxml"),
                "Reservas.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        br.edu.ifba.saj.fwads.controller.ReservaController controller = loader.getController();
        controller.setSala(salaSelecionada);
        Stage stage = (Stage) tableSalas.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reservar Sala: " + salaSelecionada.getNome());
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir Reserva: " + e.getMessage()).showAndWait();
    }
}
    public void setSalaManager(SalaManager ignored) {
        carregarSalas();
    }

    public void setTypeManager(TypeManager ignored) {
    }
}
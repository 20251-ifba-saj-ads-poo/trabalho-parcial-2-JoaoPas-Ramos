package br.edu.ifba.saj.fwads.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

import br.edu.ifba.saj.fwads.model.Sala;
import br.edu.ifba.saj.fwads.model.SalaManager; // ajuste o pacote conforme seu projeto

public class MainController {

    @FXML private TableView<Sala> tableSalas;
    @FXML private TableColumn<Sala, String> colNome;
    @FXML private TableColumn<Sala, String> colTipo;
    @FXML private TableColumn<Sala, Number> colCapacidade;

    private SalaManager salaManager;

    public void setSalaManager(SalaManager salaManager) {
        this.salaManager = salaManager;
        carregarSalas();
    }

    @FXML
    private void initialize() {

        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo()));
        colCapacidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCapacidade()));

        tableSalas.setRowFactory(tv -> {
            TableRow<Sala> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    abrirTelaReserva(row.getItem());
                }
            });
            return row;
        });
    }

    private void carregarSalas() {
        if (salaManager == null) return;
        List<Sala> salas = salaManager.listarSalas();
        tableSalas.getItems().setAll(salas);
    }

    @FXML
    private void onPerfilClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profile.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) tableSalas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Perfil do Usuário");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirTelaReserva(Sala salaSelecionada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Reserva.fxml"));
            Scene scene = new Scene(loader.load());



            Stage stage = (Stage) tableSalas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reservar Sala: " + salaSelecionada.getNome());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
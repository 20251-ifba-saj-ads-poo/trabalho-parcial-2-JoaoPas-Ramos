package br.edu.ifba.saj.fwads.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.ifba.saj.fwads.AppState;
import br.edu.ifba.saj.fwads.model.*;

public class ProfileController {

    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblMatricula;
    @FXML private Label lblTipo;
    @FXML private Label lblExtraTitulo;
    @FXML private Label lblExtraValor;

    @FXML private TableView<Reserva> tableReservas;
    @FXML private TableColumn<Reserva, String> colSala;
    @FXML private TableColumn<Reserva, String> colData;
    @FXML private TableColumn<Reserva, String> colMotivo;

    @FXML private Button btnVoltar;
    @FXML private Button btnLogout;

    private final DateTimeFormatter DIA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Usuario usuario() { return AppState.getUsuarioLogado(); }
    private ReservaManager reservaManager() { return AppState.getReservaManager(); }

    @FXML
    private void initialize() {
        colSala.setCellValueFactory(data -> {
            Sala s = data.getValue().getSala();
            return new SimpleStringProperty(s != null ? s.getNome() : "-");
        });

        colData.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getInicio().toLocalDate().format(DIA_FMT))
        );

        colMotivo.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getMotivo() != null ? data.getValue().getMotivo() : "")
        );

        preencherUsuario();
        carregarReservas();
    }

    private void preencherUsuario() {
        Usuario u = usuario();
        if (u == null) return;

        lblNome.setText(u.getNome());
        lblEmail.setText(u.getEmail());
        lblMatricula.setText(u.getMatricula());
        lblTipo.setText(u.getTipoUsuario());

        if (u instanceof Aluno a) {
            lblExtraTitulo.setText("Curso:");
            lblExtraValor.setText(a.getCurso());
        } else if (u instanceof Professor p) {
            lblExtraTitulo.setText("Departamento:");
            lblExtraValor.setText(p.getDepartamento());
        } else {
            lblExtraTitulo.setText("");
            lblExtraValor.setText("");
        }
    }

    private void carregarReservas() {
        tableReservas.getItems().clear();
        Usuario u = usuario();
        if (u == null) return;

        List<Reserva> minhas = reservaManager().listarReservasPorUsuario(u);
        tableReservas.getItems().addAll(minhas);
    }

    @FXML
private void onVoltarClick() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/MainView.fxml"),
                "MainView.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistema de Reservas");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        alertErro("Erro ao voltar para a tela principal: " + e.getMessage());
    }
}

@FXML
private void onLogoutClick() {
    try {
        br.edu.ifba.saj.fwads.AppState.logout();
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Login.fxml"),
                "Login.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void alertErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText(null); a.setTitle("Erro"); a.showAndWait();
    }
}

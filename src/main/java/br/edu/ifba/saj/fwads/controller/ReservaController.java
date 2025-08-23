package br.edu.ifba.saj.fwads.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.ifba.saj.fwads.AppState;
import br.edu.ifba.saj.fwads.model.Reserva;
import br.edu.ifba.saj.fwads.model.Sala;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.model.ReservaManager;
import br.edu.ifba.saj.fwads.model.SalaManager;  

public class ReservaController {

    @FXML private Label lblNome;
    @FXML private Label lblTipo;
    @FXML private Label lblCapacidade;

    @FXML private DatePicker datePicker;

    @FXML private TableView<String> tableRecursos;
    @FXML private TableColumn<String, String> colRecurso;

    @FXML private Button btnVoltar;
    @FXML private Button btnPerfil;
    @FXML private Button btnReservar;

    private Sala sala;

    private final Set<LocalDate> diasReservados = new HashSet<>();

    private Usuario usuarioLogado() { return AppState.getUsuarioLogado(); }
    private ReservaManager reservaManager() { return AppState.getReservaManager(); }
    private SalaManager salaManager() { return AppState.getSalaManager(); }

    public void setSala(Sala sala) {
        this.sala = sala;
        preencherInfoSala();
        carregarRecursos();
        carregarDiasReservados();
        configurarCalendario();
    }

    @FXML
    private void initialize() {
        colRecurso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    }


    private void preencherInfoSala() {
        if (sala == null) return;
        lblNome.setText(sala.getNome());
        lblTipo.setText(sala.getTipo());
        lblCapacidade.setText(String.valueOf(sala.getCapacidade()));
    }

    private void carregarRecursos() {
        tableRecursos.getItems().clear();
        if (sala != null && sala.getRecursos() != null) {
            tableRecursos.getItems().addAll(sala.getRecursos());
        }
    }

    private void carregarDiasReservados() {
        diasReservados.clear();
        if (sala == null) return;
        List<Reserva> reservas = reservaManager().listarReservasPorSala(sala);
        for (Reserva r : reservas) {
            LocalDate start = r.getInicio().toLocalDate();
            LocalDate end = r.getFim().minusSeconds(1).toLocalDate();
            for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                diasReservados.add(d);
            }
        }
    }

    private void configurarCalendario() {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (diasReservados.contains(item)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffdddd; -fx-text-fill: red;");
                    setTooltip(new Tooltip("Já reservado"));
                }
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    @FXML
    private void onReservarClick() {
        if (sala == null) {
            alertErro("Sala não definida.");
            return;
        }
        Usuario u = usuarioLogado();
        if (u == null) {
            alertErro("Usuário não identificado. Faça login novamente.");
            return;
        }

        LocalDate dia = datePicker.getValue();
        if (dia == null) {
            alertAviso("Selecione um dia.");
            return;
        }

        LocalDateTime inicio = dia.atStartOfDay();
        LocalDateTime fim = dia.plusDays(1).atStartOfDay();

        boolean disponivel = reservaManager().verificarDisponibilidade(sala, inicio, fim);
        if (!disponivel) {
            alertErro("Conflito de reserva");
            return; 
        }

        boolean ok = reservaManager().cadastrarReserva(
                new br.edu.ifba.saj.fwads.model.Reserva(sala, u, inicio, fim, "Reserva diária")
        );

        if (ok) {
            alertInfo("Reserva efetuada para " + dia);
            carregarDiasReservados();
            configurarCalendario();
        } else {
            alertErro("Não foi possível cadastrar a reserva.");
        }
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
            alertErro("Erro ao voltar à tela principal: " + e.getMessage());
        }
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
            Stage stage = (Stage) btnPerfil.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Perfil do Usuário");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void alertInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null); a.setTitle("Informação"); a.showAndWait();
    }

    private void alertAviso(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg);
        a.setHeaderText(null); a.setTitle("Atenção"); a.showAndWait();
    }

    private void alertErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText(null); a.setTitle("Erro"); a.showAndWait();
    }
}
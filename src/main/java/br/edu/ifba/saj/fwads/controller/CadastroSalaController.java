package br.edu.ifba.saj.fwads.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.fwads.AppState;
import br.edu.ifba.saj.fwads.model.Sala;
import br.edu.ifba.saj.fwads.model.SalaManager; 
import br.edu.ifba.saj.fwads.model.TypeManager;

public class CadastroSalaController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCapacidade;

    @FXML private TextField txtRecurso;
    @FXML private ListView<String> listRecursos;

    @FXML private ComboBox<String> cbTipo;

    @FXML private Button btnVoltar;
    @FXML private Button btnSalvar;

    private SalaManager salaManager() { return AppState.getSalaManager(); }
    private TypeManager typeManager() { return AppState.getTypeManager(); }

    @FXML
    private void initialize() {
        cbTipo.setEditable(true);
        atualizarTipos();
    }

    private void atualizarTipos() {
        cbTipo.setItems(FXCollections.observableArrayList(typeManager().listarTipos()));
    }

    @FXML
    private void onNovoTipoClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Novo tipo de sala");
        dialog.setHeaderText(null);
        dialog.setContentText("Digite o novo tipo:");
        dialog.showAndWait().ifPresent(valor -> {
            if (typeManager().adicionarTipo(valor)) {
                atualizarTipos();
                cbTipo.setValue(valor.trim());
            } else {
                alertAviso("Tipo inválido ou já existente.");
            }
        });
    }

    @FXML
    private void onAdicionarRecursoClick() {
        String recurso = txtRecurso.getText();
        if (recurso == null || recurso.isBlank()) {
            alertAviso("Informe um recurso antes de adicionar.");
            return;
        }
        listRecursos.getItems().add(recurso.trim());
        txtRecurso.clear();
        txtRecurso.requestFocus();
    }

    @FXML
    private void onRemoverRecursoClick() {
        String sel = listRecursos.getSelectionModel().getSelectedItem();
        if (sel != null) listRecursos.getItems().remove(sel);
    }

    @FXML
    private void onSalvarClick() {
        String nome = txtNome.getText();
        String tipo = (cbTipo.getEditor() != null && !cbTipo.getEditor().getText().isBlank())
                ? cbTipo.getEditor().getText().trim()
                : cbTipo.getValue();

        String capStr = txtCapacidade.getText();

        if (nome == null || nome.isBlank()
                || tipo == null || tipo.isBlank()
                || capStr == null || capStr.isBlank()) {
            alertAviso("Preencha Nome, Tipo e Capacidade.");
            return;
        }

        int capacidade;
        try {
            capacidade = Integer.parseInt(capStr.trim());
            if (capacidade <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            alertAviso("Capacidade deve ser um número inteiro positivo.");
            return;
        }

        typeManager().adicionarTipo(tipo);

        List<String> recursosList = new ArrayList<>(listRecursos.getItems());
        String[] recursos = recursosList.isEmpty() ? null : recursosList.toArray(new String[0]);

        Sala nova = new Sala(nome.trim(), tipo.trim(), capacidade, recursos);

        boolean ok = salaManager().cadastrarSala(nova);
        if (!ok) {
            alertErro("Já existe sala igual (duplicada).");
            return;
        }

        alertInfo("Sala cadastrada com sucesso!");
        voltarParaMain();
    }

    @FXML
    private void onVoltarClick() { voltarParaMain(); }

    private void voltarParaMain() {
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
            alertErro("Erro ao voltar para a lista de salas: " + e.getMessage());
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
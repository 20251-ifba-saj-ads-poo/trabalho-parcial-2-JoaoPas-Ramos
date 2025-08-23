package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.AppState;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    @FXML
    private void onLoginClick() {
        String login = txtEmail.getText();
        String senha = txtSenha.getText();

        Usuario usuario = null;

        if ("admin".equalsIgnoreCase(login) && "admin".equals(senha)) {
            usuario = new Professor("Leandro", "leandro@ifba.com", "P123", "POO");
        } else if ("aluno".equalsIgnoreCase(login) && "aluno".equals(senha)) {
            usuario = new Aluno("Joao", "joao@ifba.com", "A456", "ADS");
        }

        if (usuario != null) {
            AppState.setUsuarioLogado(usuario);
            trocarParaMainView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Credenciais inválidas!");
            alert.showAndWait();
        }
    }

    @FXML
private void onRegisterClick() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Cadastro.fxml"),
                "Cadastro.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Cadastro");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Falha ao abrir a tela de cadastro: " + e.getMessage()).showAndWait();
    }
}

private void trocarParaMainView() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/MainView.fxml"),
                "MainView.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistema de Reservas");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Falha ao abrir a tela principal: " + e.getMessage()).showAndWait();
    }
}
}

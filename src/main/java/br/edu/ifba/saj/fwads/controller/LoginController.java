package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;

    @FXML
    private void onLoginClick() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if ("carlos@uni.com".equals(email) && "123".equals(senha)) {
            trocarParaMainView();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Credenciais inválidas!");
            alert.showAndWait();
        }
    }

    @FXML
    private void onRegisterClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cadastro");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void trocarParaMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sistema de Reservas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
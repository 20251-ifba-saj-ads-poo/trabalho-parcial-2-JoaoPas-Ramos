package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import br.edu.ifba.saj.fwads.AppState;
import br.edu.ifba.saj.fwads.model.Aluno;
import br.edu.ifba.saj.fwads.model.Professor;
import br.edu.ifba.saj.fwads.model.Usuario;

public class CadastroController {

    @FXML private TextField txtEmail;
    @FXML private TextField txtNome;
    @FXML private TextField txtMatricula;
    @FXML private PasswordField txtSenha;

    @FXML private CheckBox chkProfessor;
    @FXML private CheckBox chkAluno;

    @FXML private Button btnVoltar;
    @FXML private Button btnEnviar;

    @FXML
    private void onPerfilToggle() {
        if (chkProfessor.isSelected() && chkAluno.isSelected()) {
            chkAluno.setSelected(false);
        }
    }

    @FXML
private void onVoltarClick() {
    try {
        var url = java.util.Objects.requireNonNull(
                getClass().getResource("/views/Login.fxml"),
                "Login.fxml não encontrado em /views/"
        );
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        alertErro("Erro ao voltar para a tela de login: " + e.getMessage());
    }
}

    @FXML
    private void onEnviarClick() {
        String email = txtEmail.getText();
        String nome = txtNome.getText();
        String matricula = txtMatricula.getText();
        String senha = txtSenha.getText();

        if (email == null || email.isBlank()
            || nome == null || nome.isBlank()
            || matricula == null || matricula.isBlank()
            || senha == null || senha.isBlank()) {
            alertAviso("Preencha todos os campos.");
            return;
        }
        if (!chkProfessor.isSelected() && !chkAluno.isSelected()) {
            alertAviso("Selecione se é Professor ou Aluno.");
            return;
        }

        boolean professor = chkProfessor.isSelected();

        Usuario novo = professor
                ? new Professor(nome, email, matricula, "Não informado")
                : new Aluno(nome, email, matricula, "Não informado");

        boolean ok = AppState.getUsuarioManager().cadastrarUsuario(novo, senha);
        if (!ok) {
            alertErro("Não foi possível cadastrar. E-mail já existente?");
            return;
        }

        alertInfo("Usuário cadastrado com sucesso!\n\n"
                + "Nome: " + nome + "\n"
                + "Email: " + email + "\n"
                + "Matrícula: " + matricula + "\n"
                + "Perfil: " + (professor ? "Professor" : "Aluno"));

        onVoltarClick();
    }

    private void alertInfo(String msg) {
        Alert a = new Alert(AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.setTitle("Informação");
        a.showAndWait();
    }

    private void alertAviso(String msg) {
        Alert a = new Alert(AlertType.WARNING, msg);
        a.setHeaderText(null);
        a.setTitle("Atenção");
        a.showAndWait();
    }

    private void alertErro(String msg) {
        Alert a = new Alert(AlertType.ERROR, msg);
        a.setHeaderText(null);
        a.setTitle("Erro");
        a.showAndWait();
    }
}

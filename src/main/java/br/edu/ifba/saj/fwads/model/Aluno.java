package br.edu.ifba.saj.fwads.model;

public class Aluno extends Usuario {
    private String curso;

    public Aluno(String nome, String email, String matricula, String curso) {
        super(nome, email, matricula);
        this.curso = curso;
    }

    public String getCurso() { return curso; }

    @Override
    public String getTipoUsuario() {
        return "Aluno";
    }
}
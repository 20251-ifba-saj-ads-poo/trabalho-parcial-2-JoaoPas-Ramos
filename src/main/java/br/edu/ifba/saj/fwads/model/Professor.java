package br.edu.ifba.saj.fwads.model;
public class Professor extends Usuario {
    private String departamento;

    public Professor(String nome, String email, String matricula, String departamento) {
        super(nome, email, matricula);
        this.departamento = departamento;
    }

    public String getDepartamento() { return departamento; }

    @Override
    public String getTipoUsuario() { return "Professor"; }

    @Override
    public String toString() {
        return super.toString() + " - Departamento: " + departamento;
    }
}
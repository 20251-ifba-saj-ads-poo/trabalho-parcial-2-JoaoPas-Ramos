package br.edu.ifba.saj.fwads.model;

import java.util.Objects;

public abstract class Usuario {
    private String nome;
    private String email;
    private String matricula;

    public Usuario(String nome, String email, String matricula) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
    }

   
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getMatricula() { return matricula; }

    public abstract String getTipoUsuario();

    @Override
    public String toString() {
        return getTipoUsuario() + ": " + nome + " (" + matricula + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(matricula, usuario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}
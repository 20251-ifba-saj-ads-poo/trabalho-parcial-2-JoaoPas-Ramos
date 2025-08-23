package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String nome;
    private String tipo;
    private int capacidade;
    private List<String> recursos;

    public Sala(String nome, String tipo, int capacidade, String[] recursos) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.recursos = new ArrayList<>();
        if (recursos != null) {
            for (String r : recursos) {
                if (r != null) {
                    String t = r.trim();
                    if (!t.isEmpty() && !this.recursos.contains(t)) this.recursos.add(t);
                }
            }
        }
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getCapacidade() { return capacidade; }
    public List<String> getRecursos() { return recursos; }

    public void adicionarRecurso(String recurso) {
        if (!recursos.contains(recurso)) {
            recursos.add(recurso);
        }
    }

    public void removerRecurso(String recurso) {
        recursos.remove(recurso);
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ") - Capacidade: " + capacidade + " - Recursos: " + recursos;
    }
}
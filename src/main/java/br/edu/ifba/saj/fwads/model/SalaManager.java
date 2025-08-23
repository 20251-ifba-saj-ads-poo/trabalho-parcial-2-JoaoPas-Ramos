package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.List;

public class SalaManager {
    private List<Sala> salas = new ArrayList<>();

    public boolean cadastrarSala(Sala sala) {
        if (buscarSalaPorNome(sala.getNome()) != null) {
            return false;
        }
        salas.add(sala);
        return true;
    }

    public List<Sala> listarSalas() {
        return salas;
    }

    public Sala buscarSalaPorNome(String nome) {
        for (Sala s : salas) {
            if (s.getNome().equalsIgnoreCase(nome)) {
                return s;
            }
        }
        return null;
    }
}
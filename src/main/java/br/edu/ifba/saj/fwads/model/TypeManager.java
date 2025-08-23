package br.edu.ifba.saj.fwads.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeManager {
    private final List<String> tipos = new ArrayList<>();

    public TypeManager() {
        tipos.add("Auditório");
        tipos.add("Laboratório");
        tipos.add("Sala de Aula");
    }

    public List<String> listarTipos() {
        return Collections.unmodifiableList(tipos);
    }

    public boolean adicionarTipo(String tipo) {
        if (tipo == null) return false;
        String t = tipo.trim();
        if (t.isEmpty()) return false;
        for (String s : tipos) {
            if (s.equalsIgnoreCase(t)) return false;
        }
        tipos.add(t);
        return true;
    }
}
package br.edu.ifba.saj.fwads.model;

import java.util.*;

public class UsuarioManager {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, String> senhasPorEmail = new HashMap<>();

    public boolean cadastrarUsuario(Usuario usuario, String senha) {
        if (usuario == null || senha == null || usuario.getEmail() == null) return false;
        String email = usuario.getEmail().trim().toLowerCase();
        if (email.isEmpty() || senhasPorEmail.containsKey(email)) return false;
        usuarios.add(usuario);
        senhasPorEmail.put(email, senha);
        return true;
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null) return null;
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }
}

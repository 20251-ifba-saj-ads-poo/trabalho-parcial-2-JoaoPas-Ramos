package br.edu.ifba.saj.fwads;

import br.edu.ifba.saj.fwads.model.*;

public final class AppState {

    private static Usuario usuarioLogado;

    private static final SalaManager SALA_MANAGER = new SalaManager();
    private static final ReservaManager RESERVA_MANAGER = new ReservaManager();
    private static final TypeManager TYPE_MANAGER = new TypeManager();
    private static final UsuarioManager USUARIO_MANAGER = new UsuarioManager();

    private AppState() {}

    public static Usuario getUsuarioLogado() { return usuarioLogado; }
    public static void setUsuarioLogado(Usuario usuario) { usuarioLogado = usuario;}
    public static void logout() { usuarioLogado = null; }

    public static SalaManager getSalaManager() { return SALA_MANAGER; }
    public static ReservaManager getReservaManager() { return RESERVA_MANAGER; }
    public static TypeManager getTypeManager() { return TYPE_MANAGER; }
    public static UsuarioManager getUsuarioManager() { return USUARIO_MANAGER; }
}
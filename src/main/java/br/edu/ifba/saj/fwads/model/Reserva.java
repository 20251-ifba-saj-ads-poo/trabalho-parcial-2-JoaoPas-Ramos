package br.edu.ifba.saj.fwads.model;

import java.time.LocalDateTime;

public class Reserva {
    private Sala sala;
    private Usuario usuario;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String motivo;

    public Reserva(Sala sala, Usuario usuario, LocalDateTime inicio, LocalDateTime fim, String motivo) {
        this.sala = sala;
        this.usuario = usuario;
        this.inicio = inicio;
        this.fim = fim;
        this.motivo = motivo;
    }

    public Sala getSala() { return sala; }
    public Usuario getUsuario() { return usuario; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFim() { return fim; }
    public String getMotivo() { return motivo; }

    @Override
    public String toString() {
        return "Reserva de " + sala.getNome() + " por " + usuario.getNome() +
               " de " + inicio + " até " + fim + " | Motivo: " + motivo;
    }
}

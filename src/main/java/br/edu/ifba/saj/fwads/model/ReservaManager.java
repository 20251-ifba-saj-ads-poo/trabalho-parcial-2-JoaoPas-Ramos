package br.edu.ifba.saj.fwads.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaManager {
    private List<Reserva> reservas = new ArrayList<>();


    public boolean cadastrarReserva(Reserva reserva) {
        if (verificarDisponibilidade(reserva.getSala(), reserva.getInicio(), reserva.getFim())) {
            reservas.add(reserva);
            return true;
        }
        return false;
    }

    public boolean verificarDisponibilidade(Sala sala, LocalDateTime inicio, LocalDateTime fim) {
        for (Reserva r : reservas) {
            if (r.getSala().equals(sala)) {
                boolean conflito = inicio.isBefore(r.getFim()) && fim.isAfter(r.getInicio());
                if (conflito) return false;
            }
        }
        return true;
    }

    public List<Reserva> listarReservasPorUsuario(Usuario usuario) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getUsuario().equals(usuario)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public List<Usuario> listarUsuariosPorSala(Sala sala) {
        List<Usuario> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getSala().equals(sala) && !resultado.contains(r.getUsuario())) {
                resultado.add(r.getUsuario());
            }
        }
        return resultado;
    }

    public List<Reserva> listarReservasPorSala(Sala sala) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getSala().equals(sala)) {
                resultado.add(r);
            }
        }
        return resultado;
}
}
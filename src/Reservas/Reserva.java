/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservas;

import java.time.LocalDate;

/**
 *
 * @author alexr
 */
public class Reserva {
    private String cedulaCliente;
    private String placaVehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean confirmada;

    public Reserva(String cedulaCliente, String placaVehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.cedulaCliente = cedulaCliente;
        this.placaVehiculo = placaVehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.confirmada = false;
    }

    public String getCedulaCliente() { return cedulaCliente; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public boolean isConfirmada() { return confirmada; }

    public void setPlacaVehiculo(String nuevaPlaca) {
        this.placaVehiculo = nuevaPlaca;
    }

    public void confirmar() {
        this.confirmada = true;
    }

    public boolean estaActiva(LocalDate hoy) {
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    public boolean hayConflictoDeFecha(LocalDate inicio, LocalDate fin) {
        return (fechaFin.isBefore(inicio) || fechaInicio.isAfter(fin));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Contrato;

import java.time.LocalDate;



/**
 *
 * @author oscar
 */
public class contrato {
    private String cedula;
    private String vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private estadoContrato estado;
    private double monto;


    public double getMonto() {
        return monto;
    }
    

    public String getCedula() {
        return cedula;
    }

    public void setEstado(estadoContrato estado) {
        this.estado = estado;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public estadoContrato getEstado() {
        return estado;
    }
    

    public LocalDate getFechaInicio() { 
        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser menor a la fecha actual");
        }
        return fechaInicio;
    }

    public LocalDate getFechaFinal() {
        if (fechaFinal.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalización debe ser posterior a la fecha de inicio");
        }
        return fechaFinal;
    }

    public contrato(String cedula, String vehiculo, LocalDate fechaInicio, LocalDate fechaFinal, estadoContrato estado) {
        this.cedula = cedula;
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estadoContrato.ACTIVO;
    }
}

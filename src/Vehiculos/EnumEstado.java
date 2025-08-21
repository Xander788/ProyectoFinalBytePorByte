/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehiculos;

/**
 *
 * @author Gloriana
 */
public enum EnumEstado {
    Disponible("DISPONIBLE"),
    Alquilado("EN ALQUILER"),
    Mantenimiento("EN MANTENIMIENTO");
    
    private final String disponibilidad;

    private EnumEstado(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    @Override
    public String toString() {
        return "Estado {Disponibilidad Automovil =" + disponibilidad + '}';
    }
}

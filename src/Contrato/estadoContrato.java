/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Contrato;

/**
 *
 * @author oscar
 */
public enum estadoContrato {
    ACTIVO("activo"),
    CANCELADO("Cancelado"),
    FINALIZADO("finalizado");
    
    private final String estado;

    private estadoContrato(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}

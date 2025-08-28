/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehiculos;

/**
 *
 * @author Gloriana
 */
public enum EnumTipo {
    PickUp("PickUp"),
    Sedan("Sedan"),
    Deportivos("Deportivos"),
    Crossover("Crossover"),
    Off_Roader("Off-Roader");
    
    private final String marca;

    private EnumTipo(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    @Override
    public String toString() {
        return marca;
    }

   
}

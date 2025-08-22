/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehiculos;

import java.time.LocalDate;

/**
 *
 * @author Gloriana
 */
public class Vehiculo {
    private String placa;
    private String modelo;
    private int año;
    private EnumTipo marca;
    private EnumEstado estado;

    public int getAño() {
        return año;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public EnumTipo getMarca() {  
        return marca;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setAño(int año) {//validacion años 
        if(año <= LocalDate.now().getYear() && año >= (LocalDate.now().getYear()-20)){  
        this.año = año;}
    }

    public void setPlaca(String placa) {
        if(placa != null && validarPlaca(placa))
            this.placa = placa;
    }

    public void setMarca(EnumTipo marca) {
        this.marca = marca;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    //METODOS
    public static boolean validarPlaca(String placa){
        //vslida placas de 3 letras y 3 igitos y placas de 6 digitos
        return placa.matches("^(?:[A-Z]{3}-\\d{3}|\\d{6})$");
    }
     
    public Vehiculo(String placa, String modelo, int año, EnumTipo marca, EnumEstado estado) {
        if(placa != null && validarPlaca(placa)){
            this.placa = placa;}
        this.modelo = modelo;
        if(año <= LocalDate.now().getYear() && año >= (LocalDate.now().getYear()-20)){ 
            this.año = año;}
        this.marca = marca;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vehiculo:" + "placa= " + placa + " , modelo= " + modelo + " , año = " + año + ", marca = " + marca + " , estado = " + estado;
    }

    
    
}

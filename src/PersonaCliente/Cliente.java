/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonaCliente;
import Personas.Persona;
import java.time.LocalDate;

/**
 *
 * @author Gloriana
 */
public class Cliente extends Persona {
   private String licencia;
   
   //metodos
    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        if(validarLicencia(licencia))
            this.licencia = licencia;
    }

    public static boolean validarLicencia(String licencia){
        return licencia.matches("^[A-G][0-9]{6}$");
    }
    
    //constructor
    public Cliente(String licencia, String cedula, String nombre, LocalDate birthDate, String telefono, String correo) {
        super(cedula, nombre, birthDate, telefono, correo);
        if(licencia != null && validarLicencia(licencia)){
        this.licencia = licencia;}
    }

    @Override
    public String toString() {
        return "Cliente:" +super.toString() + "Licencia: " + licencia + '}';
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Personas;
import java.time.LocalDate;
import Utils.UtilDate;

/**
 *
 * @author Gloriana
 */
public abstract class Persona {
    protected String cedula;
    protected String nombre;
    protected LocalDate birthDate; //edad
    protected  String telefono;
    protected  String correo;


    public void setTelefono(String telefono) {//falta validacion de constructor
        if(validarTelefono(telefono))
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        if(validarCorreo(correo))
        this.correo = correo;
    }

    public String getCedula() {
        return cedula;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
    //metodos
    private static boolean validarCedula (String cedula){
        //aca se valida que lleven guiones ademas 
        return cedula.matches("^8-?\\d{4}-?\\d{4}$");
    }
    
    private static boolean validarTelefono (String telefono){//revisar
        return telefono.matches("^[2678]\\d{7}$");
    }
    
    private static boolean validarCorreo (String correo){
    return correo.matches("^(?=.{1,64}@)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[^-][a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$");
    }
    
    public Persona(String cedula, String nombre, LocalDate birthDate, String telefono, String correo) {
        if(validarCedula(cedula)){
            this.cedula = cedula;}
        this.nombre = nombre;
        if(!UtilDate.noEsUnaFechaFutura(birthDate)){
            this.birthDate = birthDate;}
        if(validarTelefono(telefono)){
            this.telefono = telefono;}
        if(validarCorreo(correo)){
            this.correo = correo;}
        
    }

    @Override
    public String toString() {
        return "Persona{" + "cedula=" + cedula + ", nombre=" + nombre + ", birthDate=" + birthDate + ", telefono=" + telefono + ", correo=" + correo +'}';
    } 
    
}

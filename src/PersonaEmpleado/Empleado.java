/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonaEmpleado;
import Personas.Persona;
import java.time.LocalDate;

/**
 *
 * @author Gloriana
 */
public class Empleado extends Persona{
    private String puesto;
    private int salario;

    public String getPuesto() {
        return puesto;
    }

    public int getSalario() {
        return salario;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }
    
    //constructor
    public Empleado(String puesto, int salario, String cedula, String nombre, LocalDate birthDate, String telefono, String correo) {
        super(cedula, nombre, birthDate, telefono, correo);
        this.puesto = puesto;
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return "Empleado: Cedula: " + cedula + ", Nombre:" + nombre + ", Edad" + ", Telefono:" + telefono + ", Correo:" + correo + "Puesto= " + puesto + " , salario= " + salario + '}';
    }

        
}

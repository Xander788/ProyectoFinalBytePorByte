/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonaEmpleado;
import java.lang.reflect.Array;
import java.util.ArrayList;
import List.List;


/**
 *
 * @author Gloriana
 */
public class EmpleadoArrayList implements List<Empleado>{
     ArrayList<Empleado> Array;

    public EmpleadoArrayList() {
        this.Array = new ArrayList();
    }

    @Override
    public boolean add(Empleado t) {
        if (t != null) {
            Empleado existente = find(t.getCedula());
             if(existente != null){
                return false; 
            }
            return Array.add(t);
        }
        return false;   }

    @Override
    public boolean delete(Empleado t) {
        return Array.remove(t);    
    }
    
    @Override
    public void showall() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Empleado find(Object cedula) {
        for (Empleado t : Array) {
            if (t.getCedula().equals(cedula)) {
                return t;
            }
        }
        return null;    
    }
    public Empleado get(int index) {
        return Array.get(index);
    }

    public ArrayList<Empleado> getArray() {
        return Array;
    }
}

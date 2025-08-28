/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersonaCliente;
import java.lang.reflect.Array;
import java.util.ArrayList;
import List.List;


/**
 *
 * @author Gloriana
 */
public class ClienteArrayList implements List<Cliente>{
     ArrayList<Cliente> Array;
     private  static ClienteArrayList instance;

    public static ClienteArrayList getInstance() {
        if (instance==null) {
            instance= new ClienteArrayList();
        }
        return instance;
    }
     
     

    public ClienteArrayList() {
        this.Array = new ArrayList();
    }

    @Override
    public boolean add(Cliente t) {
        if (t != null) {
            Cliente existente = find(t.getCedula());
            if(existente != null){
                return false;
            }
            return Array.add(t);
        }
        return false; }

    @Override
    public boolean delete(Cliente t) {
        return Array.remove(t);    
    }
    
    @Override
    public void showall() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente find(Object cedula) {
        for (Cliente t : Array) {
            if (t.getCedula().equals(cedula)) {
                return t;
            }
        }
        return null;    
    }
    public Cliente get(int index) {
        return Array.get(index);
    }

    public ArrayList<Cliente> getArray() {
        return Array;
    }
}

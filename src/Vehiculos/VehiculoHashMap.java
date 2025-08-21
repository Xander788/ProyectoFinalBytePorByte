/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vehiculos;

import List.List;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Braya
 */
public class VehiculoHashMap implements List<Vehiculo> {
    HashMap<String,Vehiculo> map;

    public VehiculoHashMap() {
        this.map = new HashMap();
    }
    
    @Override
    public boolean add(Vehiculo t) {
        if (map.containsKey(t.getPlaca())) return false;
        map.put(t.getPlaca(), t);
        return true;
       
    }

    @Override
    public boolean delete(Vehiculo t) {
        if (!map.containsKey(t.getPlaca())) return false;
        map.remove(t.getPlaca());
        return true;
    }

    @Override
    public Vehiculo find(Object placa) {
        String strPlaca = String.valueOf(placa);
        return map.get(strPlaca);
    }

    @Override
    public void showall() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public HashMap<String, Vehiculo> getMap() {
        return map;
    }
    
}
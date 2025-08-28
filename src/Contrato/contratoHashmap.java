/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Contrato;

import List.List;
import java.util.HashMap;

/**
 *
 * @author oscar
 */
public class contratoHashmap implements List<contrato> {
    HashMap<String,contrato> map;

    public contratoHashmap(HashMap<String, contrato> map) {
        this.map = new HashMap();
    }
    
    

    @Override
    public boolean add(contrato t) {
        if(map.containsKey(t.getCedula())) return false;
        map.put(t.getCedula(), t);
        return true;
    }

    @Override
    public boolean delete(contrato t) {
        if(!map.containsKey(t.getCedula())) return false;
        map.remove(t.getCedula());
        return true;
        
    }

    @Override
    public contrato find(Object id) {
        String strid = String.valueOf(id);
        return map.get(strid);
    }

    @Override
    public void showall() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public HashMap<String, contrato> getMap() {
        return map;
    }
    
    
    
}

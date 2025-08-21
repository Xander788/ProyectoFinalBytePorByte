/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package List;

/**
 *
 * @author Braya
 */
public interface List <T> {
    
    public boolean add(T t);
    
    public boolean delete(T t);
    
    public T find(Object id);
    
    public void showall();
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Gloriana
 */
public class UtilGui {
    private static boolean hashValor(JComponent txt) {
        switch (txt) {
            case JTextComponent text -> {
                String s = text.getText();
                return s != null && !s.trim().isEmpty();
            }
            case JComboBox<?> combo -> {
                Object i = combo.isEditable() ? combo.getEditor().getItem() : combo.getSelectedItem();
                return i != null && !i.toString().trim().isEmpty();
            }
            case JSpinner sp -> {
                Object v = sp.getValue();
                return v != null && !String.valueOf(v).trim().isEmpty();
            }
            default -> {
                // Por defecto, se asume que es válido si no es uno de los casos anteriores
                return true;
            }
        }
    }
    
    public static boolean validarRequiere(JComponent... txts) {
        for (JComponent txt : txts) {
            if (!hashValor(txt)) {
                return false;
            }
        }
        return true;
    }
    
    private static void enseñarMensaje(Component component, Object message,String title,int messageType) {
        JOptionPane.showMessageDialog(component,message, title,messageType);
    }
    
    public static void enseñarMensaje(Component component, Object message,String title) {
        enseñarMensaje(component, message, title,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void enseñarMensajeError(Component component, Object message,String title) {
        enseñarMensaje(component, message, title,JOptionPane.ERROR_MESSAGE);
    }
}
  


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jprod
 */
public abstract class UtilDate{
    public static int calcularEdad(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    public static boolean noEsUnaFechaFutura(LocalDate fecha){
        return !fecha.isAfter(LocalDate.now());
    }
    
    public static boolean edadLegal(LocalDate birthDate){
        return calcularEdad(birthDate)>=18;
    }
    
    public static LocalDate formatoFecha(String fecha){
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    public static String toString(LocalDate fecha){
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservas;

/**
 *
 * @author alexr
 */
import PersonaCliente.ClienteArrayList;
import Vehiculos.VehiculoHashMap;
import Vehiculos.Vehiculo;
import Vehiculos.EnumEstado;
import Vehiculos.EnumTipo;

import java.time.LocalDate;
import java.util.*;

public class GestionReservas {
    private ClienteArrayList clientes;
    private VehiculoHashMap vehiculos;
    private List<Reserva> reservasConfirmadas;
    private Queue<Reserva> reservasEnEspera;

    public GestionReservas(ClienteArrayList clientes, VehiculoHashMap vehiculos) {
        this.clientes = clientes;
        this.vehiculos = vehiculos;
        this.reservasConfirmadas = new ArrayList<>();
        this.reservasEnEspera = new LinkedList<>();
    }
    
    public String crearReserva(String cedula, EnumTipo tipoVehiculo, LocalDate inicio, LocalDate fin) {
        if (clientes.find(cedula) == null) {
            return " Cliente no registrado.";
        }
        if (inicio.isBefore(LocalDate.now())) {
            return " La fecha de inicio no puede ser menor a la actual.";
        }
        if (fin.isBefore(inicio)) {
            return " La fecha de finalización debe ser posterior a la de inicio.";
        }
        if (inicio.plusDays(30).isBefore(fin)) {
            return " No se permiten reservas mayores a 30 días.";
        }
        Vehiculo disponible = buscarVehiculoDisponible(tipoVehiculo, inicio, fin);
        
        if (disponible == null) {
            reservasEnEspera.add(new Reserva(cedula, tipoVehiculo.name(), inicio, fin));
            return "️ No hay vehículos disponibles. Reserva agregada a la cola de espera.";
        }

        Reserva nueva = new Reserva(cedula, disponible.getPlaca(), inicio, fin);
        reservasConfirmadas.add(nueva);
        disponible.setEstado(EnumEstado.Alquilado);
        return "Reserva creada exitosamente.";
    }

    private Vehiculo buscarVehiculoDisponible(EnumTipo tipo, LocalDate inicio, LocalDate fin) {
        for (Vehiculo v : vehiculos.getMap().values()) {
            if (v.getMarca() == tipo && v.getEstado() == EnumEstado.Disponible && estaDisponible(v.getPlaca(), inicio, fin)) {
                return v;
            }
        }
        return null;
    }

    private boolean estaDisponible(String placa, LocalDate inicio, LocalDate fin) {
        for (Reserva r : reservasConfirmadas) {
            if (r.getPlacaVehiculo().equals(placa) && r.seSolapaCon(inicio, fin)) {
                return false;
            }
        }
        return true;
    }

    public String modificarReserva(String cedula, String nuevaPlaca) {
        for (Reserva r : reservasConfirmadas) {
            if (r.getCedulaCliente().equals(cedula) && !r.isConfirmada()) {
                Vehiculo nuevoVehiculo = vehiculos.find(nuevaPlaca);
                if (nuevoVehiculo == null) return " Vehículo no registrado.";
                if (nuevoVehiculo.getEstado() != EnumEstado.Disponible) return " Vehículo no disponible.";
                if (!estaDisponible(nuevaPlaca, r.getFechaInicio(), r.getFechaFin())) return "❌ Vehículo ocupado en ese rango de fechas.";
                r.setPlacaVehiculo(nuevaPlaca);
                return " Reserva modificada.";
            }
        }
        return " Reserva no encontrada o ya confirmada.";
    }

    public String cancelarReserva(String cedula) {
        Iterator<Reserva> it = reservasConfirmadas.iterator();
        while (it.hasNext()) {
            Reserva r = it.next();
            if (r.getCedulaCliente().equals(cedula) && LocalDate.now().isBefore(r.getFechaInicio())) {
                Vehiculo v = vehiculos.find(r.getPlacaVehiculo());
                if (v != null) v.setEstado(EnumEstado.Disponible);
                it.remove();
                return " Reserva cancelada.";
            }
        }
        return " No se puede cancelar la reserva.";
    }

    public List<Reserva> buscarReservaPorCliente(String cedula) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservasConfirmadas) {
            if (r.getCedulaCliente().equals(cedula)) resultado.add(r);
        }
        return resultado;
    }

    public List<Reserva> buscarReservaPorFechas(LocalDate inicio, LocalDate fin) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservasConfirmadas) {
            if (r.seSolapaCon(inicio, fin)) resultado.add(r);
        }
        return resultado;
    }

    public String confirmarReserva(String cedula) {
        for (Reserva r : reservasConfirmadas) {
            if (r.getCedulaCliente().equals(cedula) && !r.isConfirmada()) {
                r.confirmar();
                return " Reserva confirmada y lista para contrato de alquiler.";
            }
        }
        return " Reserva no encontrada o ya confirmada.";
    }

    public void mostrarReservas() {
        for (Reserva r : reservasConfirmadas) {
            System.out.println(r);
        }
    }

    public void mostrarReservasEnEspera() {
        for (Reserva r : reservasEnEspera) {
            System.out.println(" En espera: " + r);
        }
    }
}
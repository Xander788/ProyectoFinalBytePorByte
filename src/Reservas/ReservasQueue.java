/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reservas;

import List.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author alexr
 */
public class ReservasQueue implements List<Reserva>{
private Queue<Reserva> queue = new LinkedList<>();
public static ReservasQueue instance;

    @Override
    public boolean add(Reserva t) {
        return queue.offer(t);
    }

    @Override
    public boolean delete(Reserva t) {
        return queue.remove(t);
    }

    @Override
    public Reserva find(Object id) {
        for (Reserva r : queue) {
            if (r.getCedulaCliente().equals(id)) {
                return r;
            }
        }
        return null;

    }

    @Override
    public void showall() {
        for (Reserva r : queue) {
            System.out.println(r);
        }
    }

    public Queue<Reserva> getQueue() {
        return queue;
    }

    public static ReservasQueue getInstance() {
        if (instance==null) {
            instance= new ReservasQueue();
        }
        return instance;
    }

    private ReservasQueue() {
        queue = new LinkedList<>();
    }
    
}

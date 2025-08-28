/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Guis;

import Contrato.contrato;
import Contrato.contratoHashmap;
import Contrato.estadoContrato;
import PersonaCliente.Cliente;
import PersonaCliente.ClienteArrayList;
import Reservas.Reserva;
import Reservas.ReservasQueue;
import Utils.UtilDate;
import Utils.UtilGui;
import Vehiculos.EnumEstado;
import Vehiculos.EnumTipo;
import Vehiculos.Vehiculo;
import Vehiculos.VehiculoHashMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Braya
 */
public class FrmGestionAlquiler extends javax.swing.JInternalFrame {
    private contrato Contrato;
    private VehiculoHashMap vehiculos;
    private ClienteArrayList clientes;
    private contratoHashmap list;
    private Reserva reserva;
    private ReservasQueue reservas;
    private Vehiculo vehiculov;
    
    
    

    public FrmGestionAlquiler() {
        initComponents();
        list = new contratoHashmap();
        this.setClosable(true);      
        this.setResizable(true);     
        this.setIconifiable(true);
    }
    
    
    
    public static boolean existeCedula(ArrayList<Cliente> clientes, String cedulaIngresada){
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedulaIngresada)) {
                return true; 
            }
        }
        return false;
    }
    
    private Vehiculo buscarVehiculoDisponible(EnumTipo tipo, LocalDate inicio, LocalDate fin) {
        for (Vehiculo v : vehiculos.getMap().values()) {
            if (v.getEstado() == EnumEstado.Disponible && estaDisponible(v.getPlaca(), inicio, fin)) {
                return v;
            }
        }
        return null;
    }
    
    private boolean estaDisponible(String placa, LocalDate inicio, LocalDate fin) {
        for (Reserva r : reservas.getQueue()) {
            if (r.getPlacaVehiculo().equals(placa) && r.hayConflictoDeFecha(inicio, fin)) {
                return false;
            }
        }
        return true;
    }
    public void validarDatos(){
        
        if (clientes.find(txtcedula.getText()) == null) {
            UtilGui.enseñarMensaje(rootPane, closable," Cliente no registrado");
        }
        Boolean disponible = estaDisponible(txtplaca.getText(),UtilDate.formatoFecha(txtfechaInicio.getText()),UtilDate.formatoFecha(txtfechaFinalizacion.getText()));
        
        
        if (disponible == false) {
            
           UtilGui.enseñarMensaje(rootPane, closable,"️ El vehiculo seleccionado no esta disponible")  ;
        }
                
        if (UtilDate.formatoFecha(txtfechaInicio.getText()).isBefore(LocalDate.now())) {
            UtilGui.enseñarMensaje(rootPane, closable," La fecha de inicio no puede ser menor a la actual") ; 
        }
        if (UtilDate.formatoFecha(txtfechaFinalizacion.getText()).isBefore(UtilDate.formatoFecha(txtfechaInicio.getText()))) {
             UtilGui.enseñarMensaje(rootPane, closable," La fecha de finalización debe ser posterior a la de inicio") ;  
        } 
        if (vehiculos.find(txtplaca.getText()) == null) {
            UtilGui.enseñarMensaje(rootPane,"Vehiculo no registrado"," Error");
             return;
        }
    }
    
    public void crearContrato(){
        if(reservas.find(reserva.getCedulaCliente()).equals(txtcedula.getText())){
            txtplaca.setText(reserva.getPlacaVehiculo().toString());
            txtfechaInicio.setText(reserva.getFechaInicio().toString());
            txtfechaFinalizacion.setText(reserva.getFechaFin().toString());
            contrato nuevo = new contrato(txtcedula.getText() ,txtplaca.getText() , UtilDate.formatoFecha(txtfechaInicio.getText()) , UtilDate.formatoFecha(txtfechaFinalizacion.getText()),estadoContrato.ACTIVO);
            list.add(Contrato);
            if(!list.add(nuevo)){
                UtilGui.enseñarMensaje(rootPane, closable, "algo paso, nose pudo agregar");
            }
            UtilGui.enseñarMensaje(rootPane, closable, "Se creo exitosamente");
        }else{
            validarDatos();
            Vehiculo resultado = vehiculos.find(txtplaca.getText());
            resultado.setEstado(EnumEstado.Alquilado);
            
            contrato nuevo = new contrato(txtcedula.getText() ,txtplaca.getText() , UtilDate.formatoFecha(txtfechaInicio.getText()) , UtilDate.formatoFecha(txtfechaFinalizacion.getText()),estadoContrato.ACTIVO);
            if(!list.add(nuevo)){
                UtilGui.enseñarMensaje(rootPane, "algo paso", "error");
            }
            
            list.add(nuevo);
            UtilGui.enseñarMensaje(rootPane, "se creo exitosamente", "info"); 
        } 
    }
    public void finalizarContrato(){
        if(list.find(txtcedula.getText())==null){
            UtilGui.enseñarMensaje(rootPane, "no se encuentra el contrato", "error");
        }else{
            contrato res = list.find(txtcedula.getText());
            res.setEstado(estadoContrato.FINALIZADO);
        }
    }
    
    public void CancelarContrato(){
       if(list.find(txtcedula.getText())==null){
            UtilGui.enseñarMensaje(rootPane, "no se encuentre el contrato", "error");
        }else{
            if(list.find(Contrato.getEstado()).equals("Activo")){
                Contrato.setEstado(estadoContrato.CANCELADO);
                
            }
        }
        
    }
    
    public void mostrar(){
        txtcedula.setText(Contrato.getCedula());
        txtplaca.setText(Contrato.getVehiculo());
        txtfechaInicio.setText(UtilDate.toString(Contrato.getFechaInicio()));
        txtfechaFinalizacion.setText(UtilDate.toString(Contrato.getFechaFinal()));
    }
    
    
    private void limpiar(){
        txtcedula.setText("");
        txtplaca.setText("");
        txtfechaInicio.setText("");
        txtfechaFinalizacion.setText("");
    }
    
    public void buscar(){
        FrmBuscarContrato dialog = new FrmBuscarContrato(null, true);
        dialog.setList(list);
        dialog.setVisible(true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            contrato seleccionado = dialog.getContrato();
            if (seleccionado != null) {
                Contrato = seleccionado;
                mostrar();
            }
        }});
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnbuscar = new javax.swing.JButton();
        btrneliminar = new javax.swing.JButton();
        btncrear = new javax.swing.JButton();
        btnfinalizar = new javax.swing.JButton();
        txtcedula = new javax.swing.JFormattedTextField();
        txtplaca = new javax.swing.JFormattedTextField();
        txtfechaInicio = new javax.swing.JFormattedTextField();
        txtfechaFinalizacion = new javax.swing.JFormattedTextField();
        btnlimpiar = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        lbltarifa = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Administrador de Contratos de Alquiler");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Cedula  ");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Tipo de Vehiculo");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Fecha de Inicio");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Fecha de Finalizacion");

        btnbuscar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnbuscar.setText("Buscar contrato");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btrneliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btrneliminar.setText("Cancelar Contrato");
        btrneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btrneliminarActionPerformed(evt);
            }
        });

        btncrear.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btncrear.setText("Crear Contrato");
        btncrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearActionPerformed(evt);
            }
        });

        btnfinalizar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnfinalizar.setText("Finalizar Contrato");
        btnfinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfinalizarActionPerformed(evt);
            }
        });

        txtfechaInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        txtfechaFinalizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        btnlimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnlimpiar.setText("Limpiar");
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });

        btnactualizar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnactualizar.setText("Actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(67, 67, 67)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtplaca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                                .addComponent(txtfechaInicio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtfechaFinalizacion, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtcedula)
                                .addComponent(lbltarifa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(btnfinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btrneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btncrear, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtplaca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfechaFinalizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbltarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btrneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncrear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnfinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
       FrmBuscarContrato dialog = new FrmBuscarContrato(null, true);
        dialog.setList(list);
        dialog.setVisible(true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            contrato seleccionado = dialog.getContrato();
            if (seleccionado != null) {
                Contrato = seleccionado;
                mostrar();
            }
        }});
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btncrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearActionPerformed
        crearContrato();
    }//GEN-LAST:event_btncrearActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btnfinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfinalizarActionPerformed
        finalizarContrato();
    }//GEN-LAST:event_btnfinalizarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed

        
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btrneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrneliminarActionPerformed
        CancelarContrato();
    }//GEN-LAST:event_btrneliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btncrear;
    private javax.swing.JButton btnfinalizar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btrneliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbltarifa;
    private javax.swing.JFormattedTextField txtcedula;
    private javax.swing.JFormattedTextField txtfechaFinalizacion;
    private javax.swing.JFormattedTextField txtfechaInicio;
    private javax.swing.JFormattedTextField txtplaca;
    // End of variables declaration//GEN-END:variables
}

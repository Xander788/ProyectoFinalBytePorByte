/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Guis;

import Contrato.contrato;
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
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Braya
 */
public class FrmGestionAlquiler extends javax.swing.JInternalFrame {
    private int siguienteContrato = 1;
    private contrato Contrato;
    private VehiculoHashMap vehiculos;
    private ClienteArrayList clientes;
    private ArrayList<contrato> contratoArray;
    private Reserva reserva;
    private ReservasQueue reservas;
    private Vehiculo vehiculov;
    
    
    

    public FrmGestionAlquiler() {
        initComponents();
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
    }
    
    public void crearContratoSinreserva(){
        if(reservas.find(reserva.getCedulaCliente()).equals(txtcedula.getText())){
            txtfechaInicio.setText(reserva.getFechaInicio().toString());
            txtfechaFinalizacion.setText(reserva.getFechaFin().toString());
            txtestado.setText(estadoContrato.ACTIVO.toString());
            //contrato nuevo = new contrato(txtcedula.getText(),txtfechaInicio.getText(),txtfechaFinalizacion.getText());

            
        }else{
            validarDatos();
        }
        
   
        
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
        jLabel8 = new javax.swing.JLabel();
        txttarifa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtestado = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Administrador de Contratos de Alquiler");

        jLabel2.setText("Cedula  ");

        jLabel3.setText("Tipo de Vehiculo");

        jLabel4.setText("Fecha de Inicio");

        jLabel5.setText("Fecha de Finalizacion");

        btnbuscar.setText("Buscar contrato");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btrneliminar.setText("Cancelar Contrato");

        btncrear.setText("Crear Contrato");

        btnfinalizar.setText("Finalizar Contrato");

        txtcedula.setText("jFormattedTextField1");

        txtplaca.setText("jFormattedTextField2");

        txtfechaInicio.setText("jFormattedTextField3");

        txtfechaFinalizacion.setText("jFormattedTextField4");

        btnlimpiar.setText("Limpiar");

        btnactualizar.setText("Actualizar");

        jLabel8.setText("Tarifa $");

        jLabel9.setText("Estado");

        txtestado.setText("jTextField2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(67, 67, 67)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtplaca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                                    .addComponent(txtfechaInicio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtfechaFinalizacion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtcedula)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)
                                .addComponent(txttarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnfinalizar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btrneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncrear, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btrneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncrear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnfinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
            FrmListaContratos frmbuscar = new FrmListaContratos();
            frmbuscar.setVisible(true);
    }//GEN-LAST:event_btnbuscarActionPerformed


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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JFormattedTextField txtcedula;
    private javax.swing.JTextField txtestado;
    private javax.swing.JFormattedTextField txtfechaFinalizacion;
    private javax.swing.JFormattedTextField txtfechaInicio;
    private javax.swing.JFormattedTextField txtplaca;
    private javax.swing.JTextField txttarifa;
    // End of variables declaration//GEN-END:variables
}

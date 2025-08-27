/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Guis;

import Exceptions.VehiculoReservadoException;
import List.ListGui;
import Utils.UtilGui;
import Vehiculos.EnumEstado;
import Vehiculos.EnumTipo;
import Vehiculos.Vehiculo;
import Vehiculos.VehiculoHashMap;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Braya
 */
public class FrmGestionVehiculos extends javax.swing.JInternalFrame implements ListGui {
    VehiculoHashMap vehiculos;
    Vehiculo vehiculo;

    /**
     * Creates new form Gestion_Vehiculos
     */
    public FrmGestionVehiculos() {
        initComponents();
        this.setClosable(true);
        this.setResizable(false);     
        this.setIconifiable(true);
        vehiculos = VehiculoHashMap.getInstance();
        cargarEstados();
        cargarTipos();
    }

    @Override
    public void save() {
        if (!UtilGui.validarRequiere(txtPlaca,txtModelo,txtAño)) {
            UtilGui.enseñarMensajeError(this, "Faltan datos requeridos", "Error");
            return;
        }

        String placa = txtPlaca.getText();
        String modelo = txtModelo.getText();
        int año;
        
        if (placa.matches("^(?:[A-Z]{3}-\\d{3}|\\d{6})$")) {
           try {
            año = Integer.parseInt(txtAño.getText().trim());
        } catch (NumberFormatException e) {
            UtilGui.enseñarMensajeError(this, "Año inválido", "Error");
            return;
        }

        if (año > LocalDate.now().getYear() || año < LocalDate.now().getYear() - 20) {
            UtilGui.enseñarMensajeError(this, "El año debe estar entre " + (LocalDate.now().getYear() - 20) + " y " + LocalDate.now().getYear(), "Error");
            return;
        }

        EnumTipo tipo = EnumTipo.valueOf(ComboTipo.getSelectedItem().toString());
        EnumEstado estado = EnumEstado.valueOf(ComboEstado.getSelectedItem().toString());

        vehiculo = new Vehiculo(placa, modelo, año, tipo, estado);

        if (!vehiculos.add(vehiculo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un vehículo con esa placa");
            return;
        }

        UtilGui.enseñarMensaje(this, "Vehículo agregado: " + vehiculo.getPlaca(), "Agregado"); 
        return;
        }
        UtilGui.enseñarMensaje(this, "La placa no es valida", "Error"); 
    }

    @Override
    public void delete() {
        if (vehiculo == null) {
            UtilGui.enseñarMensajeError(this, "Debe buscar un vehículo primero", "Error");
            return;
        }

        if (vehiculo.getEstado() == EnumEstado.Alquilado) {
            try {
                throw new VehiculoReservadoException("No se puede eliminar un vehículo en alquiler");
            } catch (VehiculoReservadoException ex) {
                System.getLogger(FrmGestionVehiculos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
    }


        if (!vehiculos.delete(vehiculo)) {
            JOptionPane.showMessageDialog(this, "No se eliminó el vehículo");
            return;
        }
        vehiculos.delete(vehiculo);
        clear();
        System.out.println(vehiculos.getMap().size());
    }

    @Override
    public void update() {
        String placa = txtPlaca.getText();
        Vehiculo existente = vehiculos.find(placa);

    if (existente == null) {
        UtilGui.enseñarMensajeError(this, "Vehículo no encontrado.", "Error");
        return;
    }

    if (!UtilGui.validarRequiere(txtModelo, txtAño, ComboTipo, ComboEstado)) {
        UtilGui.enseñarMensajeError(this, "Todos los campos son obligatorios.", "Error");
        return;
    }

    String modelo = txtModelo.getText();
    int año;

    try {
        año = Integer.parseInt(txtAño.getText());
    } catch (NumberFormatException e) {
        UtilGui.enseñarMensajeError(this, "Año inválido. Debe ser un número.", "Error");
        return;
    }

    if (año > LocalDate.now().getYear() || año < LocalDate.now().getYear() - 20) {
        UtilGui.enseñarMensajeError(this, "El año debe estar entre " + (LocalDate.now().getYear() - 20) + " y " + LocalDate.now().getYear(), "Error");
        return;
    }

    try {
        EnumTipo tipo = EnumTipo.valueOf(ComboTipo.getSelectedItem().toString());
        EnumEstado estado = EnumEstado.valueOf(ComboEstado.getSelectedItem().toString());

        existente.setModelo(modelo);
        existente.setMarca(tipo);
        existente.setEstado(estado);

        clear();
    } catch (IllegalArgumentException e) {
        UtilGui.enseñarMensajeError(this, "Tipo o estado inválido.", "Error");
    }

    }

    @Override
    public void search() {
        BuscarVehiculos frm = new BuscarVehiculos(null,true);
        frm.setList(vehiculos);
        frm.setVisible(true);
        frm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                Vehiculo seleccionado = frm.getVehiculo();
                if (seleccionado != null) {
                    vehiculo = seleccionado;
                    showdata();
                }
            }
        });

    }

    @Override
    public void clear() {
        txtPlaca.setText("");
        txtModelo.setText("");
        txtAño.setText("");
        ComboTipo.setSelectedIndex(0);
        ComboEstado.setSelectedIndex(0);

    }

    @Override
    public void showdata() {
        txtPlaca.setText(vehiculo.getPlaca());
        txtModelo.setText(vehiculo.getModelo());
        txtAño.setText(String.valueOf(vehiculo.getAño()));
        ComboTipo.setSelectedItem(vehiculo.getMarca().name());
        ComboEstado.setSelectedItem(vehiculo.getEstado().name());

    }
    
    private void cargarTipos() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (EnumTipo tipo : EnumTipo.values()) {
            model.addElement(tipo.name());
        }
        ComboTipo.setModel(model);
    }

    private void cargarEstados() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (EnumEstado estado : EnumEstado.values()) {
            model.addElement(estado.name());
        }
        ComboEstado.setModel(model);
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Marca = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        txtAño = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        ComboTipo = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Gestion de Vehiculos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 6, -1, -1));

        txtPlaca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlacaActionPerformed(evt);
            }
        });
        getContentPane().add(txtPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 81, 120, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Placa");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        Marca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Marca.setText("Modelo");
        getContentPane().add(Marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        txtModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 81, 101, -1));

        txtAño.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(txtAño, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 81, 101, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Año");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Tipo de Vehiculo");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Estado");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, -1, -1));

        ComboEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(ComboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 140, -1));

        ComboTipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(ComboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 140, -1));

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, -1, -1));

        btnModificar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlacaActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        save();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        delete();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        update();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        search();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JLabel Marca;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtAño;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    // End of variables declaration//GEN-END:variables

    

}

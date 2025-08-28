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
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        ComboTipo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAño = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        Marca = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gestion de Vehiculos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 620, -1));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Estado");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 140, -1));

        ComboEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(ComboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 140, 40));

        ComboTipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(ComboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 170, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo de Vehiculo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Año");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 100, -1));

        txtAño.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtAño, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 101, 40));

        txtModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 140, 40));

        Marca.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        Marca.setForeground(new java.awt.Color(255, 255, 255));
        Marca.setText("Modelo");
        jPanel1.add(Marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 140, -1));

        txtPlaca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlacaActionPerformed(evt);
            }
        });
        jPanel1.add(txtPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Placa");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Guis/placa.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 70, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/coche.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 60, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/año.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 50, 50));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tipocarro.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 90, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/estado.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 110, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 580, 220));

        jPanel2.setOpaque(false);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar.png"))); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar.png"))); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 340, 60));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlacaActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        clear();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JTextField txtAño;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    // End of variables declaration//GEN-END:variables

    

}

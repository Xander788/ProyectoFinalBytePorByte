/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Guis;

import List.ListGui;
import PersonaCliente.Cliente;
import PersonaCliente.ClienteArrayList;
import Utils.UtilDate;
import static Utils.UtilDate.calcularEdad;
import Utils.UtilGui;
import java.awt.Frame;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Braya
 */
public class FrmGestionClientes extends javax.swing.JInternalFrame implements ListGui {
    private ClienteArrayList lista;
    private Cliente cliente;

    /**
     * Creates new form FrmGestionClientes
     */
    public FrmGestionClientes() {
        initComponents();
        lista = new ClienteArrayList();
        this.setClosable(true);      // Activa la "X" para cerrar
        this.setResizable(true);     // Permitir redimensionar
        this.setIconifiable(true);
    }

    @Override
    public void clear() {
        TxtCedula.setText("");
        txtNombre.setText("");
        TxtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtEdad.setText("");
        txtLicencia.setText("");
        txtCorreo.setText("");
    }
    
    
    private boolean validarRequiere(){
        return UtilGui.validarRequiere(TxtCedula,txtNombre,TxtTelefono,txtFechaNacimiento,txtLicencia,txtCorreo);
    }

    @Override
    public void save() {
        if (!validarRequiere()) {
            UtilGui.enseñarMensajeError(this, "Faltan datos requeridos", "Error");
            return;
        }
        String Cedula = TxtCedula.getText();
        String Nombre = txtNombre.getText();
        String Telefono = TxtTelefono.getText();
        LocalDate date = UtilDate.formatoFecha(txtFechaNacimiento.getText());
        String Licencia = txtLicencia.getText();
        String Correo = txtCorreo.getText();
        int edad = 0;

        try {
            if (date == null) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
            }
            if (date.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
            }
            edad = calcularEdad(date);
            if (edad < 18) {
                throw new IllegalArgumentException("El cliente debe ser mayor de 18 años");
            }
        } catch (IllegalArgumentException ex) {
            UtilGui.enseñarMensajeError(this, ex.getMessage(), "Error");
            return; // si hay error de fecha, no sigue guardando
        }

        txtEdad.setText(String.valueOf(edad));
        
        cliente = new Cliente(Cedula, Nombre, Telefono, date, Licencia, Correo);
        
        //Revisar validaciones internas
        if (!Nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            UtilGui.enseñarMensajeError(this, "El nombre solo puede contener letras y espacios", "Error");
            return;
        }
        if (cliente.getCedula() == null) {
            UtilGui.enseñarMensajeError(this, "Cédula inválida", "Error");
            return;
        }
        if (cliente.getTelefono() == null) {
            UtilGui.enseñarMensajeError(this, "Teléfono inválido", "Error");
            return;
        }
        if (cliente.getCorreo() == null) {
            UtilGui.enseñarMensajeError(this, "Correo inválido", "Error");
            return;
        }
        if (cliente.getLicencia() == null) {
            UtilGui.enseñarMensajeError(this, "Licencia inválida", "Error");
            return;
        }

        //Guardar en la lista y controlar duplicados
        if (lista.add(cliente)) {
            UtilGui.enseñarMensaje(this, "Cliente guardado correctamente: " + cliente.getNombre(), "Éxito");
        } else {
            UtilGui.enseñarMensajeError(this, "Ya existe un cliente con esa cédula", "Error");
        }
    }

    @Override
    public void update() {
        if(!validarRequiere()){
            UtilGui.enseñarMensaje(this, "Faltan datos requeridos", "Error");
            return;
        }
        
        String nuevoTelefono = TxtTelefono.getText();
        String nuevoCorreo = txtCorreo.getText();
        String nuevaLicencia = txtLicencia.getText();
        
        String telAnterior = cliente.getTelefono();
        cliente.setTelefono(nuevoTelefono);
        if(!telAnterior.equals(cliente.getTelefono())){
            UtilGui.enseñarMensaje(this, "Telefono actualizado correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de telefono invalido", "Error");
        }
        
        String CorreoAnterior = cliente.getCorreo();
        cliente.setCorreo(nuevoCorreo);
        if(!CorreoAnterior.equals(cliente.getCorreo())){
            UtilGui.enseñarMensaje(this, "Correo actualizado correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de correo invalido", "Exito");
        }
        
        String LicenciaAnterior = cliente.getLicencia();
         cliente.setLicencia(nuevaLicencia);
        if(!LicenciaAnterior.equals(cliente.getLicencia())){
            UtilGui.enseñarMensaje(this, "Licencia actualizada correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de licencia invalido", "Error");
        }
    }

    @Override
    public void delete() {
        if (cliente == null) {
            UtilGui.enseñarMensajeError(this, "Debe especificar al cliente que quiere eliminar", "Error");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,"¿Está seguro que desea eliminar al cliente con cédula " + cliente.getCedula() + "?","Confirmación",
            JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = lista.delete(cliente); 
            if (eliminado) {
                UtilGui.enseñarMensaje(this, "Cliente eliminado correctamente", "Éxito");
                clear();
                cliente = null;
            } else {
                UtilGui.enseñarMensajeError(this, "No se pudo eliminar el cliente", "Error");
            }
        }
    }

    @Override
    public void showdata() {
        TxtCedula.setText(cliente.getCedula());
        txtNombre.setText(cliente.getNombre());
        TxtTelefono.setText(cliente.getTelefono());
        txtFechaNacimiento.setText(UtilDate.toString(cliente.getBirthDate()));
        txtEdad.setText(String.valueOf(calcularEdad(cliente.getBirthDate())));
        txtLicencia.setText(cliente.getLicencia());
        txtCorreo.setText(cliente.getCorreo());
    }

    @Override
    public void search() {
        Frame parentFrame = JOptionPane.getFrameForComponent(this); // Obtener el Frame padre
        FrmBuscarCliente frm = new FrmBuscarCliente(parentFrame, true);
        frm.setLista(lista);
        frm.setVisible(true);
        frm.addWindowListener(new java.awt.event.WindowAdapter(){
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            Cliente seleccionado = frm.getCliente();
            if (seleccionado != null) {
                cliente = seleccionado;
                showdata();
            }
        }});
    }
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblCedula = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        lblLicencia = new javax.swing.JLabel();
        txtLicencia = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        TxtTelefono = new javax.swing.JFormattedTextField();
        TxtCedula = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Administracion de Clientes");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jPanel1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCedula.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblCedula.setForeground(new java.awt.Color(255, 255, 255));
        lblCedula.setText("Cedula");
        jPanel1.add(lblCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        lblNombre.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre");
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 180, -1));

        txtNombre.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 180, 40));

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblTelefono.setForeground(new java.awt.Color(255, 255, 255));
        lblTelefono.setText("Telefono");
        jPanel1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 120, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cedula.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 50, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/telefono.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 60, 60));

        lblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblFechaNacimiento.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaNacimiento.setText("Fecha de Nacimiento");
        jPanel1.add(lblFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        txtFechaNacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 220, 40));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fecha nacimiento.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 60, 30));

        lblEdad.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblEdad.setForeground(new java.awt.Color(255, 255, 255));
        lblEdad.setText("Edad");
        jPanel1.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 80, -1));

        txtEdad.setEditable(false);
        txtEdad.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 80, 40));

        lblLicencia.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblLicencia.setForeground(new java.awt.Color(255, 255, 255));
        lblLicencia.setText("Licencia");
        jPanel1.add(lblLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 120, -1));

        try {
            txtLicencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("A-######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtLicencia.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 110, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/licencia.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 60, 40));

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(255, 255, 255));
        lblCorreo.setText("Correo Electronico");
        jPanel1.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 270, -1));

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoFocusLost(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 270, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/correo.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 40, 40));

        try {
            TxtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtTelefono.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(TxtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 120, 40));

        TxtCedula.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(TxtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 580, 310));

        jPanel2.setOpaque(false);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Buscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Limpiar.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Guardar.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 410, 60));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 600, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCorreoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusLost
        String correo = txtCorreo.getText().trim();
        if (correo.matches("^(?=.{1,64}@)[a-zA-Z0-9._%+-]+(\\.[a-zA-Z0-9._%+-]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            UtilGui.enseñarMensaje(this,  "Correo permitido ", "Validación");
        } else {
            UtilGui.enseñarMensajeError(this, "Correo inválido ", "Error de validación");
        }
    }//GEN-LAST:event_txtCorreoFocusLost

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        clear();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        delete();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        save();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        update();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        search();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtCedula;
    private javax.swing.JFormattedTextField TxtTelefono;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblLicencia;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JFormattedTextField txtFechaNacimiento;
    private javax.swing.JFormattedTextField txtLicencia;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}


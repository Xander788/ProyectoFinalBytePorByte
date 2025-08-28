/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Guis;

import List.ListGui;
import PersonaCliente.Cliente;
import PersonaEmpleado.Empleado;
import PersonaEmpleado.EmpleadoArrayList;
import Utils.UtilDate;
import static Utils.UtilDate.calcularEdad;
import Utils.UtilGui;
import java.awt.Frame;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Braya
 */
public class FrmGestionEmpleados extends javax.swing.JInternalFrame implements ListGui{
    private EmpleadoArrayList lista;
    private Empleado empleado;

    /**
     * Creates new form FrmGestionClientes
     */
    public FrmGestionEmpleados() {
        initComponents();
        lista = new EmpleadoArrayList();
        this.setClosable(true);      // Activa la "X" para cerrar
        this.setResizable(true);     // Permitir redimensionar
        this.setIconifiable(true);
    }
    
    private boolean validarRequiere(){
        return UtilGui.validarRequiere(TxtCedula,txtNombre,txtTelefono,txtFechaNacimiento,txtCorreo,txtPuesto,txtSalario);
    }

    @Override
    public void save() {
        if(!validarRequiere()){
            UtilGui.enseñarMensaje(this, "Faltan datos requeridos", "Error");
            return;
        }
        
        String Cedula = TxtCedula.getText();
        String Nombre = txtNombre.getText();
        String Telefono = txtTelefono.getText();
        LocalDate date = UtilDate.formatoFecha(txtFechaNacimiento.getText());
        String Correo = txtCorreo.getText();
        String Puesto = txtPuesto.getText();
        String SalarioTexto = txtSalario.getText();
        int edad = 0;
        
        try {
            if(date == null){
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacia");
            }
            if(date.isAfter(LocalDate.now())){
                throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
            }
            edad = calcularEdad(date);
            if(edad<18){
                throw new IllegalArgumentException("El cliente debe ser mayor de 18 años");
            }
        }   catch (IllegalArgumentException ex) {
            UtilGui.enseñarMensaje(this, ex.getMessage(), "Error");
            return;
        }
        if(!SalarioTexto.matches("[0-9,\\s]+")){
            UtilGui.enseñarMensaje(this, "El salario solo púede contener numeros y espacio", "Error");
            return;
        }
        txtEdad.setText(String.valueOf(edad));
        
        // Convertir salario a entero
        int Salario;
        try {
            Salario = Integer.parseInt(SalarioTexto.replaceAll("[,\\s]", ""));
        } catch (NumberFormatException ex) {
            UtilGui.enseñarMensajeError(this, "Error al procesar el salario", "Error");
            return;
        }

        empleado = new Empleado(Cedula, Nombre, Telefono, date, Correo, Puesto, Salario);
        
        //Revisa validaciones internas
        if (!Nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            UtilGui.enseñarMensajeError(this, "El nombre solo puede contener letras y espacios", "Error");
            return;
        }
        if (!empleado.getCedula().matches("^[1-9]-?\\d{4}-?\\d{4}$")) {
            UtilGui.enseñarMensajeError(this, "Cédula inválida", "Error");
            return;
        }
        if (empleado.getTelefono() == null) {
            UtilGui.enseñarMensajeError(this, "Teléfono inválido", "Error");
            return;
        }
        if (empleado.getCorreo() == null) {
            UtilGui.enseñarMensajeError(this, "Correo inválido", "Error");
            return;
        }
        if (!Puesto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            UtilGui.enseñarMensajeError(this, "El puesto solo puede contener letras y espacios", "Error");
            return;
        }
        
        if (lista.add(empleado)) {
            UtilGui.enseñarMensaje(this, "Empleado guardado correctamente: " + empleado.getNombre(), "Éxito");
        } else {
            UtilGui.enseñarMensajeError(this, "Ya existe un Empleado con esa cédula", "Error");
        }
    }

    @Override
    public void delete() {
        if (empleado == null) {
            UtilGui.enseñarMensaje(this, "Debe especificar el empleado que quiere eliminar ", "Error");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar al cliente con cédula " + empleado.getCedula() + "?", "Confirmación",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = lista.delete(empleado);
            if (eliminado) {
                UtilGui.enseñarMensaje(this, "Cliente eliminado correctamente", "Éxito");
                clear();
                empleado = null;
            } else {
                UtilGui.enseñarMensajeError(this, "No se pudo eliminar el cliente", "Error");
            }
        }
    }

    @Override
    public void update() {
         if (!validarRequiere()) {
            UtilGui.enseñarMensaje(this, "Faltan datos requeridos", "Error");
            return;
        }

        String nuevoTelefono = txtTelefono.getText();
        String nuevoCorreo = txtCorreo.getText();
        String nuevoPuesto = txtPuesto.getText();
      
        String telAnterior = empleado.getTelefono(); 
        empleado.setTelefono(nuevoTelefono);
        if (!telAnterior.equals(empleado.getTelefono())) {
            UtilGui.enseñarMensaje(this, "Telefono actualizado correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de telefono invalido", "Error");
        }
        
        String correoAnterior = empleado.getCorreo(); 
        empleado.setCorreo(nuevoCorreo);
        if (!correoAnterior.equals(empleado.getCorreo())) {
            UtilGui.enseñarMensaje(this, "Correo actualizado correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de correo invalido", "Error");
        }

        if (nuevoPuesto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+([\\s.-]?[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
            empleado.setPuesto(nuevoPuesto);
            UtilGui.enseñarMensaje(this, "Puesto actualizado correctamente", "Exito");
        } else {
            UtilGui.enseñarMensajeError(this, "Formato de puesto invalido. Solo se permiten letras, espacios, puntos y guiones", "Error");
        }
    }

    @Override
    public void search() {
        Frame parentFrame = JOptionPane.getFrameForComponent(this); // Obtener el Frame padre
        FrmBuscarEmpleado frm = new FrmBuscarEmpleado(parentFrame, true);
        frm.setLista(lista);
        frm.setVisible(true);
        frm.addWindowListener(new java.awt.event.WindowAdapter(){
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            Empleado seleccionado = frm.getEmpleado();
            if (seleccionado != null) {
                empleado = seleccionado;
                showdata();
            }
        }});
    }

    @Override
    public void clear() {
        TxtCedula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        txtCorreo.setText("");
        txtEdad.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
    }

    @Override
    public void showdata() {
        TxtCedula.setText(empleado.getCedula());
        txtNombre.setText(empleado.getNombre());
        txtTelefono.setText(empleado.getTelefono());
        txtFechaNacimiento.setText(UtilDate.toString(empleado.getBirthDate()));
        txtEdad.setText(String.valueOf(calcularEdad(empleado.getBirthDate())));
        txtPuesto.setText(empleado.getPuesto());
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
        txtTelefono = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        lblPuesto = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        TxtCedula = new javax.swing.JTextField();
        pnBotones = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Administracion de Empleados");
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

        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefono.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 120, 40));

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
        jPanel1.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 80, -1));

        txtEdad.setEditable(false);
        txtEdad.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 80, 40));

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(255, 255, 255));
        lblCorreo.setText("Correo Electronico");
        jPanel1.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 270, -1));

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoFocusLost(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 270, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/correo.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 40, -1));

        lblPuesto.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblPuesto.setForeground(new java.awt.Color(255, 255, 255));
        lblPuesto.setText("Puesto");
        jPanel1.add(lblPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 190, -1));

        txtPuesto.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 190, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/puesto.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 70, 60));

        lblSalario.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lblSalario.setForeground(new java.awt.Color(255, 255, 255));
        lblSalario.setText("Salario");
        jPanel1.add(lblSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 140, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salario.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, 40, 50));

        txtSalario.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(txtSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 140, 40));

        TxtCedula.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jPanel1.add(TxtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 580, 310));

        pnBotones.setOpaque(false);

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

        javax.swing.GroupLayout pnBotonesLayout = new javax.swing.GroupLayout(pnBotones);
        pnBotones.setLayout(pnBotonesLayout);
        pnBotonesLayout.setHorizontalGroup(
            pnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnBotonesLayout.createSequentialGroup()
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
        pnBotonesLayout.setVerticalGroup(
            pnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(pnBotones, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 410, 60));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 600, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCorreoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusLost
        String correo = txtCorreo.getText().trim();
        if (correo.matches("^(?=.{1,64}@)[a-zA-Z0-9._%+-]+(\\.[a-zA-Z0-9._%+-]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            UtilGui.enseñarMensaje(this, "Correo permitido ", "Validación");
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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPuesto;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnBotones;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JFormattedTextField txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPuesto;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JFormattedTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}

package vista;

import dao.DaoAlumnos;
import dao.DaoProfesores;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase para la interfaz gráfica de inicio de sesión
 * @author Lourdes Navarro Tocón
 */
public class InicioSesion extends javax.swing.JFrame {

    /**
     * Atributos que llaman a las clases DAO de Alumnos y Profesores
     */
    
    DaoAlumnos daoAlum;
    DaoProfesores daoProf;
    /**
     * Atributo Nombre de alumno (tipo String)
     */
    static String name;
    /**
     * Atributo Path del fichero de configuración que crearemos para el inicio de sesión de los administrativos
     */
    final static Path configPath = Paths.get("init.config");
    /**
     * Atributo Nombre del administrativo (tipo String)
     */
    private static String adminName;
    /**
     * Atributo Contraseña del administrativo (tipo String)
     */
    private static String adminPass;
    
    /**
     * Constructor predeterminado donde inicializamos las clases DAO y los componentes de la interfaz gráfica
     */
    public InicioSesion() throws SQLException {
        this.daoAlum = DaoAlumnos.getInstance();
        this.daoProf = DaoProfesores.getInstance();
        cargarConfiguracion();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JOpPError = new javax.swing.JOptionPane();
        labelIniciarSesion = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelContras = new javax.swing.JLabel();
        fieldNombre = new javax.swing.JTextField();
        fieldContras = new javax.swing.JPasswordField();
        btnLogIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelIniciarSesion.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        labelIniciarSesion.setText("Iniciar Sesión");

        labelNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelNombre.setText("Nombre:");

        labelContras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelContras.setText("Contraseña:");

        btnLogIn.setText("Log In");
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelContras)
                    .addComponent(labelNombre))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(labelIniciarSesion))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldNombre)
                            .addComponent(fieldContras, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogIn)
                .addGap(185, 185, 185))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContras)
                    .addComponent(fieldContras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(btnLogIn)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método para iniciar sesión cuando pulsamos el botón "Log in"
     * @param evt Action Event
     */
    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed
        try {
            // TODO add your handling code here:
            if(daoAlum.findByName(fieldNombre.getText())!=null && !fieldContras.getText().equals("")){
                this.name=fieldNombre.getText();
                VistaAlum vAlum = new VistaAlum();
                vAlum.setVisible(true);
                this.setVisible(false);
            } else if(daoProf.findByName(fieldNombre.getText())!=null && !fieldContras.getText().equals("")){
                VistaProf vProf = new VistaProf();
                vProf.setVisible(true);
                this.setVisible(false);
            } else if(fieldNombre.getText().equals(adminName) && fieldContras.getText().equals(adminPass)){
                VistaAdmin vAdmin = new VistaAdmin();
                vAdmin.setVisible(true);
                this.setVisible(false);
            } else{
                JOptionPane.showMessageDialog(null,"Error al iniciar sesión");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "El usuario no se encuentra registrado");
        }
        
    }//GEN-LAST:event_btnLogInActionPerformed

    /**
     * Método para leer el fichero de configuración, que contiene el nombre de administrativo y la contraseña
     */
    private void cargarConfiguracion(){
        try {
            List<String> config = Files.readAllLines(configPath);
            adminName = config.get(0);
            adminPass = config.get(1);
        } catch (IOException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método main
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new InicioSesion().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JOptionPane JOpPError;
    private javax.swing.JButton btnLogIn;
    private javax.swing.JPasswordField fieldContras;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JLabel labelContras;
    private javax.swing.JLabel labelIniciarSesion;
    private javax.swing.JLabel labelNombre;
    // End of variables declaration//GEN-END:variables
}

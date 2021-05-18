package vista;

import dao.DaoAlumnos;
import dao.DaoControlesEscritos;
import dao.DaoPracticas;
import dto.ControlEscrito;
import dto.Practica;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Clase para la interfaz gráfica del menú de alumno
 * @author Lourdes Navarro Tocón
 */
public class VistaAlum extends javax.swing.JFrame {

    /**
     * Atributos que llaman a las clases DAO de Alumnos, Controles escritos y prácticas
     */
    
    static DaoAlumnos daoAlum;
    DaoControlesEscritos daoControl;
    DaoPracticas daoPract;
        
    /**
     * JScrollPanes
     */
    
    JScrollPane jScrollPane;
    JScrollPane jScrollPane1;
     
    /**
     * Tablas
     */
    
    JTable table;
    JTable table1; 
    
    /**
     * Modelo que utilizaremos para las tablas
     */
    DefaultTableModel m;
    
    /**
     * Colecciones de controles y prácticas
     */
    
    Map<Integer, ControlEscrito> controles;
    Map<Integer, Practica> practicas;
    
    /**
     * Atributo para establecer el formato de Date
     */
    static DateFormat dateformat; 
    
    /**
     * Constructor predeterminado donde inicializamos las clases DAO, los JScrollPanes, el formato de Date, los componentes de la interfaz gráfica y los métodos para mostrar los datos del alumno, crear las pestañas de Controles escritos y Prácticas
     * y calcular las medias
     */
    public VistaAlum() throws SQLException {
        this.daoAlum = DaoAlumnos.getInstance();
        this.daoControl = DaoControlesEscritos.getInstance();
        this.daoPract = DaoPracticas.getInstance();
        this.jScrollPane = new JScrollPane();
        this.jScrollPane1 = new JScrollPane();
        dateformat = new SimpleDateFormat("yyyy-MM-dd");
        
        initComponents();
        establecerDatos();
        loadPanelControl();
        loadPanelPract();
        calcularMedias();
    }

    /**
     * Método que devuelve el número de matrícula del alumno que ha iniciado sesión
     * @return Número de matrícula del alumno
     * @throws SQLException 
     */
    public static int getNMatr() throws SQLException{
        return daoAlum.findByName(InicioSesion.name).getNumMatricula();
    }
    
    /**
     * Método que devuelve el nombre del alumno que ha iniciado sesión
     * @return Nombre del alumno
     */
    public static String getNombre(){
        return InicioSesion.name;
    }
        
    /**
     * Método que devuelve el grupo del alumno que ha iniciado sesión
     * @return Grupo del alumno
     * @throws SQLException 
     */
    public static int getGrupo() throws SQLException{
        return daoAlum.findByName(InicioSesion.name).getGrupo();
    }
        
    /**
     * Método para mostrar los datos del alumno que ha iniciado sesión en los campos de texto correspondientes
     * @throws SQLException 
     */
    public void establecerDatos() throws SQLException{
        txtNMatr.setText(""+getNMatr());
        txtNombre.setText(getNombre());
        txtGrupo.setText(""+getGrupo());
    }
        
    /**
     * Método para calcular la media de los controles escritos y la media de las prácticas que ha realizado el alumno
     */
    public void calcularMedias(){
        TableModel m1 = table.getModel();
        TableModel m2 = table1.getModel();
        double totalContr=0, totalPract=0, mediaContr, mediaPract;
        int i=0, j=0;
        for(ControlEscrito c: controles.values()){
            totalContr += (double) m1.getValueAt(i, 3);
            i++;
        }
        mediaContr=totalContr/m1.getRowCount();
        txtMediaContr.setText(""+mediaContr);
        
        for(Practica getPa: practicas.values()){
            for(Date k1: getPa.getNotasPractica().keySet()){
                totalPract += (double) m2.getValueAt(j, 4);
                j++;
            }
        }
        mediaPract=totalPract/m2.getRowCount();
        txtMediaPract.setText(""+mediaPract);
    }
        
    /**
     * Método para crear la tabla que contendrá la información de los controles escritos que ha realizado el alumno
     */
    public void loadTableControl(){
        String titulos[] = {"Código", "Preguntas", "Fecha", "Nota"};
        m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
        };
        this.table = new JTable();
        table.setRowSelectionAllowed(true);
    }
       
    /**
     * Método para crear la tabla que contendrá la información de las prácticas que ha realizado el alumno
     */
    public void loadTablePract(){
        String titulos[] = {"Código", "Título", "Dificultad", "Fecha Realización", "Nota"};
        m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
        };
        this.table1 = new JTable();
        table1.setRowSelectionAllowed(true); 
    }
        
    /**
     * Método que lista todos los controles escritos de la base de datos que ha realizado el alumno
     */
    public void mostrarControl(){
        try {
            controles = daoControl.findByAlumno(getNMatr());
            String titulos[] = {"Código", "Preguntas", "Fecha", "Nota"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            int j=0;
            for (ControlEscrito getC: controles.values()) {
                m.addRow(O);
                m.setValueAt(getC.getNumControl(), j, 0);
                m.setValueAt(getC.getNumPreguntas(), j, 1);
                m.setValueAt(getC.getFecha(), j, 2);
                m.setValueAt(getC.getNotasControl().get(getNMatr()), j, 3);
                
                m.isCellEditable(j, 0);
                m.isCellEditable(j, 1);
                m.isCellEditable(j, 2);
                m.isCellEditable(j, 3);
                j++;
            }
            table.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
        
    /**
     * Método que lista todas las prácticas de la base de datos que ha realizado el alumno
     */
    public void mostrarPract(){
        try {
            practicas = daoPract.findByAlumno(getNMatr());
            String titulos[] = {"Código", "Título", "Dificultad", "Fecha Realización", "Nota"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            int j=0;
            for(Practica getPa: practicas.values()){
                for(Date k1: getPa.getNotasPractica().keySet()){
                    m.addRow(O);
                    m.setValueAt(getPa.getCodPractica(), j, 0);
                    m.setValueAt(getPa.getTitulo(), j, 1);
                    m.setValueAt(getPa.getDificultad().toString(), j, 2);
                    m.setValueAt(k1, j, 3);
                    m.setValueAt(getPa.getNotasPractica().get(k1).get(getNMatr()), j, 4);
                            
                    m.isCellEditable(j, 0);
                    m.isCellEditable(j, 1);
                    m.isCellEditable(j, 2);
                    m.isCellEditable(j, 3);
                    m.isCellEditable(j, 4);
                    j++;
                }
            }
            table1.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
        
    /**
     * Método que carga la pestaña de Controles escritos
     */
    public void loadPanelControl(){
        loadTableControl();
        mostrarControl();
        this.jScrollPane1 = new JScrollPane(table);
        paneles.addTab("Controles escritos", jScrollPane1);
    }
    
    /**
     * Método que carga la pestaña de Prácticas
     */
    public void loadPanelPract(){
        loadTablePract();
        mostrarPract();
        this.jScrollPane1 = new JScrollPane(table1);
        paneles.addTab("Prácticas", jScrollPane1);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelArea = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        btnExpediente = new javax.swing.JButton();
        labelNMatr = new javax.swing.JLabel();
        txtNMatr = new javax.swing.JFormattedTextField();
        labelNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        labelGrupo = new javax.swing.JLabel();
        txtGrupo = new javax.swing.JFormattedTextField();
        labelNotas = new javax.swing.JLabel();
        labelMediaContr = new javax.swing.JLabel();
        txtMediaContr = new javax.swing.JFormattedTextField();
        labelMediaPract = new javax.swing.JLabel();
        txtMediaPract = new javax.swing.JFormattedTextField();
        paneles = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelArea.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        labelArea.setText("Área de Alumnos");

        btnLogOut.setText("Log out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnExpediente.setText("Descargar expediente");
        btnExpediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpedienteActionPerformed(evt);
            }
        });

        labelNMatr.setText("Nº Matrícula:");

        txtNMatr.setEditable(false);
        try {
            txtNMatr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelNombre.setText("Nombre:");

        txtNombre.setEditable(false);

        labelGrupo.setText("Grupo:");

        txtGrupo.setEditable(false);
        try {
            txtGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelNotas.setText("Historial de notas de controles:");

        labelMediaContr.setText("Nota media de controles:");

        txtMediaContr.setEditable(false);
        txtMediaContr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        labelMediaPract.setText("Nota media de prácticas:");

        txtMediaPract.setEditable(false);
        txtMediaPract.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelArea)
                                    .addComponent(labelNotas)
                                    .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelMediaPract)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMediaPract, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelMediaContr)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMediaContr, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNMatr)
                                    .addComponent(labelNombre)
                                    .addComponent(labelGrupo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(btnExpediente)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelArea)
                    .addComponent(btnLogOut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNMatr)
                    .addComponent(txtNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGrupo)
                    .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMediaContr)
                            .addComponent(txtMediaContr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMediaPract)
                            .addComponent(txtMediaPract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(126, 126, 126))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(labelNotas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExpediente)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método para cerrar sesión al pulsar el botón "Log out"
     * @param evt Action Event
     */
    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        InicioSesion logIn = null;
        try {
            logIn = new InicioSesion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar sesión");
        }
        logIn.setVisible(true);
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnExpedienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedienteActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnExpedienteActionPerformed

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
            java.util.logging.Logger.getLogger(VistaAlum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAlum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAlum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAlum.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaAlum().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(VistaAlum.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExpediente;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JLabel labelArea;
    private javax.swing.JLabel labelGrupo;
    private javax.swing.JLabel labelMediaContr;
    private javax.swing.JLabel labelMediaPract;
    private javax.swing.JLabel labelNMatr;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelNotas;
    private javax.swing.JTabbedPane paneles;
    private javax.swing.JFormattedTextField txtGrupo;
    private javax.swing.JFormattedTextField txtMediaContr;
    private javax.swing.JFormattedTextField txtMediaPract;
    private javax.swing.JFormattedTextField txtNMatr;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

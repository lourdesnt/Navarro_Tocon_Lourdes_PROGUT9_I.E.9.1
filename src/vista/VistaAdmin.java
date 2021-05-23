package vista;

import dto.Alumno;
import dto.Profesor;
import dao.DaoAlumnos;
import dao.DaoProfesores;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase para la interfaz gráfica del menú de administrativo
 * @author Lourdes Navarro Tocón
 */
public class VistaAdmin extends javax.swing.JFrame {

    /**
     * Modelo que utilizaremos para las tablas
     */
    DefaultTableModel m;
    
    /**
     * Atributos que llaman a las clases DAO de Alumnos y Profesores
     */
    
    DaoProfesores daoProf;
    DaoAlumnos daoAlum;
    
    /**
     * Listas de profesores y alumnos
     */
    
    List<Profesor> profesores;
    List<Alumno> alumnos;
    
    /**
     * Tablas
     */
    
    JTable table;
    JTable table1;
    
    /**
     * JScrollPanes
     */
    
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    
    /**
     * Constructor predeterminado donde inicializamos las clases DAO, los JScrollPanes, los componentes de la interfaz gráfica y las pestañas de Alumnos y Profesores
     */
    public VistaAdmin() throws SQLException {
        this.daoProf = DaoProfesores.getInstance();
        this.daoAlum = DaoAlumnos.getInstance();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPane2 = new JScrollPane();
        
        initComponents();
        loadPanelProf();
        loadPanelAlum();
    }
    
    /**
     * Método para crear la tabla que contendrá la información de todos los profesores
     */
    public void loadTableProf(){
        String titulos[] = {"DNI", "Nombre"};
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
     * Método para crear la tabla que contendrá la información de todos los alumnos
     */
    public void loadTableAlum(){
        String titulos[] = {"Nº Matrícula", "Nombre", "Grupo"};
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
     * Método que lista todos los profesores de la base de datos y los muestra en la tabla
     */
    public void mostrarProf(){
        try {
            profesores = daoProf.findAll();
            String titulos[] = {"DNI", "Nombre"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            for (int i = 0; i < profesores.size(); i++) {
                m.addRow(O);
                Profesor getP = (Profesor) profesores.get(i);
                m.setValueAt(getP.getDni(), i, 0);
                m.setValueAt(getP.getNombre(), i, 1);
                m.isCellEditable(i, 0);
                m.isCellEditable(i, 1);
            }
            table.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
    
    /**
     * Método que lista todos los alumnos de la base de datos y los muestra en la tabla
     */
    public void mostrarAlum(){  
        try {
            alumnos = daoAlum.findAll();
            String titulos[] = {"Nº Matrícula", "Nombre", "Grupo"};
            m = new DefaultTableModel(null, titulos){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            for (int i = 0; i < alumnos.size(); i++) {
                m.addRow(O);
                Alumno getA = (Alumno) alumnos.get(i);
                m.setValueAt(getA.getNumMatricula(), i, 0);
                m.setValueAt(getA.getNombre(), i, 1);
                m.setValueAt(getA.getGrupo(), i, 2);
                m.isCellEditable(i, 0);
                m.isCellEditable(i, 1);
                m.isCellEditable(i, 2);
            }
            table1.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }

    /**
     * Método que carga la pestaña de Profesores
     */
    public void loadPanelProf(){
        loadTableProf();
        mostrarProf();
        this.jScrollPane1 = new JScrollPane(table);
        paneles.addTab("Profesores", jScrollPane1);
    }
    
    /**
     * Método que carga la pestaña de Alumnos
     */
    public void loadPanelAlum(){
        loadTableAlum();
        mostrarAlum();
        this.jScrollPane2 = new JScrollPane(table1);
        paneles.addTab("Alumnos", jScrollPane2);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogInsertarProf = new javax.swing.JDialog();
        labelAnadir = new javax.swing.JLabel();
        labelNuevoDNI = new javax.swing.JLabel();
        labelNuevoNombre = new javax.swing.JLabel();
        btnNuevoAceptar = new javax.swing.JButton();
        btnNuevoCancelar = new javax.swing.JButton();
        txtNuevoDNI = new javax.swing.JFormattedTextField();
        txtNuevoNombre = new javax.swing.JFormattedTextField();
        dialogInsertarAlum = new javax.swing.JDialog();
        labelAnadir1 = new javax.swing.JLabel();
        labelNuevoNMatr = new javax.swing.JLabel();
        labelNuevoNombre1 = new javax.swing.JLabel();
        btnNuevoAceptar1 = new javax.swing.JButton();
        btnNuevoCancelar1 = new javax.swing.JButton();
        labelNuevoGrupo = new javax.swing.JLabel();
        txtNuevoNMatr = new javax.swing.JFormattedTextField();
        txtNuevoNombre1 = new javax.swing.JFormattedTextField();
        txtNuevoGrupo = new javax.swing.JFormattedTextField();
        dialogModificarProf = new javax.swing.JDialog();
        labelModificar = new javax.swing.JLabel();
        labelModDNI = new javax.swing.JLabel();
        labelModNombre = new javax.swing.JLabel();
        btnModAceptar = new javax.swing.JButton();
        btnModCancelar = new javax.swing.JButton();
        txtModDNI = new javax.swing.JFormattedTextField();
        txtModNombre = new javax.swing.JFormattedTextField();
        dialogModificarAlum = new javax.swing.JDialog();
        labelModificar1 = new javax.swing.JLabel();
        labelModNMatr = new javax.swing.JLabel();
        labelModNombre1 = new javax.swing.JLabel();
        btnModAceptar1 = new javax.swing.JButton();
        btnModCancelar1 = new javax.swing.JButton();
        labelModGrupo = new javax.swing.JLabel();
        txtModNMatr = new javax.swing.JFormattedTextField();
        txtModNombre1 = new javax.swing.JFormattedTextField();
        txtModGrupo = new javax.swing.JFormattedTextField();
        labelArea = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        paneles = new javax.swing.JTabbedPane();
        btnModificar = new javax.swing.JButton();

        labelAnadir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAnadir.setText("Añadir nuevo profesor:");

        labelNuevoDNI.setText("DNI:");

        labelNuevoNombre.setText("Nombre:");

        btnNuevoAceptar.setText("Aceptar");
        btnNuevoAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAceptarActionPerformed(evt);
            }
        });

        btnNuevoCancelar.setText("Cancelar");
        btnNuevoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCancelarActionPerformed(evt);
            }
        });

        try {
            txtNuevoDNI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########U")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout dialogInsertarProfLayout = new javax.swing.GroupLayout(dialogInsertarProf.getContentPane());
        dialogInsertarProf.getContentPane().setLayout(dialogInsertarProfLayout);
        dialogInsertarProfLayout.setHorizontalGroup(
            dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarProfLayout.createSequentialGroup()
                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dialogInsertarProfLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAnadir)
                            .addGroup(dialogInsertarProfLayout.createSequentialGroup()
                                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNuevoNombre)
                                    .addComponent(labelNuevoDNI))
                                .addGap(18, 18, 18)
                                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNuevoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(dialogInsertarProfLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(btnNuevoAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoCancelar)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        dialogInsertarProfLayout.setVerticalGroup(
            dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarProfLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelAnadir)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoDNI)
                    .addComponent(txtNuevoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoNombre)
                    .addComponent(txtNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(dialogInsertarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoAceptar)
                    .addComponent(btnNuevoCancelar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        labelAnadir1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAnadir1.setText("Añadir nuevo alumno:");

        labelNuevoNMatr.setText("Nº Matrícula:");

        labelNuevoNombre1.setText("Nombre:");

        btnNuevoAceptar1.setText("Aceptar");
        btnNuevoAceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAceptar1ActionPerformed(evt);
            }
        });

        btnNuevoCancelar1.setText("Cancelar");
        btnNuevoCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCancelar1ActionPerformed(evt);
            }
        });

        labelNuevoGrupo.setText("Grupo:");

        txtNuevoNMatr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtNuevoGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout dialogInsertarAlumLayout = new javax.swing.GroupLayout(dialogInsertarAlum.getContentPane());
        dialogInsertarAlum.getContentPane().setLayout(dialogInsertarAlumLayout);
        dialogInsertarAlumLayout.setHorizontalGroup(
            dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarAlumLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAnadir1)
                    .addGroup(dialogInsertarAlumLayout.createSequentialGroup()
                        .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNuevoNombre1)
                            .addComponent(labelNuevoNMatr)
                            .addComponent(labelNuevoGrupo))
                        .addGap(18, 18, 18)
                        .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogInsertarAlumLayout.createSequentialGroup()
                                .addComponent(btnNuevoAceptar1)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevoCancelar1))
                            .addComponent(txtNuevoNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        dialogInsertarAlumLayout.setVerticalGroup(
            dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarAlumLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelAnadir1)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoNMatr)
                    .addComponent(txtNuevoNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoNombre1)
                    .addComponent(txtNuevoNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoGrupo)
                    .addComponent(txtNuevoGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(dialogInsertarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoAceptar1)
                    .addComponent(btnNuevoCancelar1))
                .addGap(43, 43, 43))
        );

        labelModificar.setText("Modificar profesor:");

        labelModDNI.setText("DNI:");

        labelModNombre.setText("Nombre:");

        btnModAceptar.setText("Aceptar");
        btnModAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModAceptarActionPerformed(evt);
            }
        });

        btnModCancelar.setText("Cancelar");
        btnModCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModCancelarActionPerformed(evt);
            }
        });

        txtModDNI.setEditable(false);
        try {
            txtModDNI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########U")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout dialogModificarProfLayout = new javax.swing.GroupLayout(dialogModificarProf.getContentPane());
        dialogModificarProf.getContentPane().setLayout(dialogModificarProfLayout);
        dialogModificarProfLayout.setHorizontalGroup(
            dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarProfLayout.createSequentialGroup()
                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dialogModificarProfLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelModificar)
                            .addGroup(dialogModificarProfLayout.createSequentialGroup()
                                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelModNombre)
                                    .addComponent(labelModDNI))
                                .addGap(18, 18, 18)
                                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtModDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtModNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(dialogModificarProfLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(btnModAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModCancelar)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        dialogModificarProfLayout.setVerticalGroup(
            dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarProfLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelModificar)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModDNI)
                    .addComponent(txtModDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModNombre)
                    .addComponent(txtModNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(dialogModificarProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModAceptar)
                    .addComponent(btnModCancelar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        labelModificar1.setText("Modificar alumno:");

        labelModNMatr.setText("Nº Matrícula:");

        labelModNombre1.setText("Nombre:");

        btnModAceptar1.setText("Aceptar");
        btnModAceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModAceptar1ActionPerformed(evt);
            }
        });

        btnModCancelar1.setText("Cancelar");
        btnModCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModCancelar1ActionPerformed(evt);
            }
        });

        labelModGrupo.setText("Grupo:");

        txtModNMatr.setEditable(false);
        txtModNMatr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtModGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout dialogModificarAlumLayout = new javax.swing.GroupLayout(dialogModificarAlum.getContentPane());
        dialogModificarAlum.getContentPane().setLayout(dialogModificarAlumLayout);
        dialogModificarAlumLayout.setHorizontalGroup(
            dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarAlumLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelModificar1)
                    .addGroup(dialogModificarAlumLayout.createSequentialGroup()
                        .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelModNombre1)
                            .addComponent(labelModNMatr)
                            .addComponent(labelModGrupo))
                        .addGap(18, 18, 18)
                        .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogModificarAlumLayout.createSequentialGroup()
                                .addComponent(btnModAceptar1)
                                .addGap(18, 18, 18)
                                .addComponent(btnModCancelar1))
                            .addComponent(txtModNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        dialogModificarAlumLayout.setVerticalGroup(
            dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarAlumLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelModificar1)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModNMatr)
                    .addComponent(txtModNMatr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModNombre1)
                    .addComponent(txtModNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModGrupo)
                    .addComponent(txtModGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(dialogModificarAlumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModAceptar1)
                    .addComponent(btnModCancelar1))
                .addGap(43, 43, 43))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelArea.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        labelArea.setText("Área de Administrativos");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnLogOut.setText("Log out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paneles)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModificar)
                                .addGap(11, 11, 11)
                                .addComponent(btnBorrar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(labelArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                        .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelArea)
                    .addComponent(btnLogOut))
                .addGap(18, 18, 18)
                .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnBorrar)
                    .addComponent(btnModificar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método para mostrar la ventana con el formulario donde introduciremos la información del profesor o alumno nuevo que queremos registrar cuando pulsemos el botón de "Nuevo"
     * @param evt Action Event
     */
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        if(paneles.getSelectedIndex()==0){
            dialogInsertarProf.setSize(400,300);
            dialogInsertarProf.setModal(true);
            dialogInsertarProf.setVisible(true);
            mostrarProf();
        } else if(paneles.getSelectedIndex()==1){
            dialogInsertarAlum.setSize(400,300);
            dialogInsertarAlum.setModal(true);
            dialogInsertarAlum.setVisible(true);
            mostrarAlum();
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para registrar un nuevo profesor al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnNuevoAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAceptarActionPerformed
        Profesor p = new Profesor(txtNuevoDNI.getText(), txtNuevoNombre.getText());
        profesores.add(p);
        try {
            daoProf.insert(p);
            dialogInsertarProf.dispose(); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnNuevoAceptarActionPerformed

    /**
     * Método para cancelar el registro de un nuevo profesor al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnNuevoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCancelarActionPerformed
        dialogInsertarProf.dispose();
    }//GEN-LAST:event_btnNuevoCancelarActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para registrar un nuevo alumno al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnNuevoAceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAceptar1ActionPerformed
        // TODO add your handling code here:
        Alumno a = new Alumno(Integer.parseInt(txtNuevoNMatr.getText()), txtNuevoNombre1.getText(), Integer.parseInt(txtNuevoGrupo.getText()));
        alumnos.add(a);
        try {
            daoAlum.insert(a);
            dialogInsertarAlum.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnNuevoAceptar1ActionPerformed

    /**
     * Método para cancelar el registro de un nuevo alumno al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnNuevoCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCancelar1ActionPerformed
        // TODO add your handling code here:
        dialogInsertarAlum.dispose();
    }//GEN-LAST:event_btnNuevoCancelar1ActionPerformed

    /**
     * Método para eliminar un profesor o alumno que hemos seleccionado de la tabla al pulsar el botón "Borrar"
     * @param evt Action Event
     */
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        int filsel;
        int resp;
        try {
            if(paneles.getSelectedIndex()==0){
                String dni;
                filsel=table.getSelectedRow();
                if(filsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el profesor a borrar");
                } else{
                    resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    if(resp==JOptionPane.YES_OPTION){
                        m = (DefaultTableModel) table.getModel();
                        dni = (String) m.getValueAt(filsel, 0);
                        daoProf.delete(dni);
                        mostrarProf();
                    }
                }
            } else if(paneles.getSelectedIndex()==1){
                int numMatricula;
                filsel=table1.getSelectedRow();
                if(filsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el alumno a borrar");
                } else{
                    resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    if(resp==JOptionPane.YES_OPTION){
                        m = (DefaultTableModel) table1.getModel();
                        numMatricula = (int) m.getValueAt(filsel, 0);
                        daoAlum.delete(numMatricula);
                        mostrarAlum();
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

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

    /**
     * Método para mostrar la ventana con el formulario donde modificaremos la información del profesor o alumno que hemos seleccionado de la tabla cuando pulsemos el botón de "Modificar"
     * @param evt Action Event
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int fsel;
        if(paneles.getSelectedIndex()==0){
            String dni, nombre;
            fsel=table.getSelectedRow();
            if(fsel==-1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar el profesor a modificar");
            } else{
                m = (DefaultTableModel) table.getModel();
                dni = (String) m.getValueAt(fsel,0);
                nombre = (String) m.getValueAt(fsel, 1);
                txtModDNI.setText(dni);
                txtModNombre.setText(nombre);
                dialogModificarProf.setSize(400,300);
                dialogModificarProf.setModal(true);
                dialogModificarProf.setVisible(true);
                mostrarProf();
            }
        } else if(paneles.getSelectedIndex()==1){
            int numMatricula, grupo;
            String nombre1;
            fsel=table1.getSelectedRow();
            if(fsel==-1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar el alumno a modificar");
            } else{
                m = (DefaultTableModel) table1.getModel();
                numMatricula = (int) m.getValueAt(fsel,0);
                nombre1 = (String) m.getValueAt(fsel, 1);
                grupo = (int) m.getValueAt(fsel, 2);
                txtModNMatr.setText(""+numMatricula);
                txtModNombre1.setText(nombre1);
                txtModGrupo.setText(""+grupo);
                dialogModificarAlum.setSize(400,300);
                dialogModificarAlum.setModal(true);
                dialogModificarAlum.setVisible(true);
                mostrarAlum();
            }
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * Método para cancelar la modificación de un profesor al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnModCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelarActionPerformed
        // TODO add your handling code here:
        dialogModificarProf.dispose();
    }//GEN-LAST:event_btnModCancelarActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para modificar un alumno seleccionado al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnModAceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModAceptar1ActionPerformed
        // TODO add your handling code here:
        int numMatricula = Integer.parseInt(txtModNMatr.getText());
        String nombre1 = txtModNombre1.getText();
        int grupo = Integer.parseInt(txtModGrupo.getText());
        try {
            for(Alumno a: alumnos){
                if(a.getNumMatricula()==numMatricula){
                    a.setNombre(nombre1);
                    a.setGrupo(grupo);
                    daoAlum.update(a);
                    dialogModificarAlum.dispose();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnModAceptar1ActionPerformed

    /**
     * Método para cancelar la modificación de un alumno al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnModCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelar1ActionPerformed
        // TODO add your handling code here:
        dialogModificarAlum.dispose();
    }//GEN-LAST:event_btnModCancelar1ActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para modificar un profesor seleccionado al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnModAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModAceptarActionPerformed
        // TODO add your handling code here:
        String dni = txtModDNI.getText();
        String nombre = txtModNombre.getText();
        try {
            for(Profesor p: profesores){
                if(p.getDni().equals(dni)){
                    p.setNombre(nombre);
                    daoProf.update(p);
                    dialogModificarProf.dispose();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnModAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(VistaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaAdmin().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(VistaAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnModAceptar;
    private javax.swing.JButton btnModAceptar1;
    private javax.swing.JButton btnModCancelar;
    private javax.swing.JButton btnModCancelar1;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevoAceptar;
    private javax.swing.JButton btnNuevoAceptar1;
    private javax.swing.JButton btnNuevoCancelar;
    private javax.swing.JButton btnNuevoCancelar1;
    private javax.swing.JDialog dialogInsertarAlum;
    private javax.swing.JDialog dialogInsertarProf;
    private javax.swing.JDialog dialogModificarAlum;
    private javax.swing.JDialog dialogModificarProf;
    private javax.swing.JLabel labelAnadir;
    private javax.swing.JLabel labelAnadir1;
    private javax.swing.JLabel labelArea;
    private javax.swing.JLabel labelModDNI;
    private javax.swing.JLabel labelModGrupo;
    private javax.swing.JLabel labelModNMatr;
    private javax.swing.JLabel labelModNombre;
    private javax.swing.JLabel labelModNombre1;
    private javax.swing.JLabel labelModificar;
    private javax.swing.JLabel labelModificar1;
    private javax.swing.JLabel labelNuevoDNI;
    private javax.swing.JLabel labelNuevoGrupo;
    private javax.swing.JLabel labelNuevoNMatr;
    private javax.swing.JLabel labelNuevoNombre;
    private javax.swing.JLabel labelNuevoNombre1;
    private javax.swing.JTabbedPane paneles;
    private javax.swing.JFormattedTextField txtModDNI;
    private javax.swing.JFormattedTextField txtModGrupo;
    private javax.swing.JFormattedTextField txtModNMatr;
    private javax.swing.JFormattedTextField txtModNombre;
    private javax.swing.JFormattedTextField txtModNombre1;
    private javax.swing.JFormattedTextField txtNuevoDNI;
    private javax.swing.JFormattedTextField txtNuevoGrupo;
    private javax.swing.JFormattedTextField txtNuevoNMatr;
    private javax.swing.JFormattedTextField txtNuevoNombre;
    private javax.swing.JFormattedTextField txtNuevoNombre1;
    // End of variables declaration//GEN-END:variables
}

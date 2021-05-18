package vista;

import dto.ControlEscrito;
import dto.Practica;
import dao.DaoAlumnos;
import dao.DaoControlesEscritos;
import dao.DaoPracticas;
import dao.DaoProfesores;
import dto.Dificultad;
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

/**
 * Clase para la interfaz gráfica del menú de profesor
 * @author Lourdes Navarro Tocón
 */
public class VistaProf extends javax.swing.JFrame {

    /**
     * Modelo que utilizaremos para las tablas
     */
    DefaultTableModel m;
    
    /**
     * Atributos que llaman a las clases DAO de Controles escritos, Prácticas, Profesores y Alumnos
     */
    
    DaoControlesEscritos daoControl;
    DaoPracticas daoPract;
    DaoProfesores daoProf;
    DaoAlumnos daoAlum;
    
    /**
     * Colecciones de controles, notas de prácticas y profesores que han diseñado prácticas
     */
    
    Map<Integer, ControlEscrito> controles;
    Map<Integer, Practica> notasPracticas;
    Map<Integer, Practica> disenoPracticas;
    
    /**
     * Tablas
     */
    
    JTable table;
    JTable table1;
    JTable table2;
    
    /**
     * JScrollPanes
     */
    
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane3;
    
    /**
     * Atributo para establecer el formato de Date
     */
    static DateFormat dateformat; 
    
    /**
     * Constructor predeterminado donde inicializamos las clases DAO, los JScrollPanes, el formato de Date, los componentes de la interfaz gráfica y las pestañas de Controles escritos y Prácticas
     */
    public VistaProf() throws SQLException{
        this.daoControl = DaoControlesEscritos.getInstance();
        this.daoPract = DaoPracticas.getInstance();
        this.daoAlum = DaoAlumnos.getInstance();
        this.daoProf = DaoProfesores.getInstance();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPane2 = new JScrollPane();
        this.jScrollPane3 = new JScrollPane();
        
        dateformat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        initComponents();
        loadPanelControl();
        loadPanelPract();
    }
    
    /**
     * Método para crear la tabla que contendrá la información de todos los controles escritos
     */
    public void loadTableControl(){
        String titulos[] = {"Código", "Preguntas", "Fecha", "Alumno", "Nombre", "Nota"};
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
     * Método para crear la tabla que contendrá la información de todas las notas de las prácticas
     */
    public void loadTableNotasPract(){
        String titulos[] = {"Código", "Título", "Dificultad", "Fecha Realización", "Nº Matrícula Alumno", "Alumno", "Nota"};
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
     * Método para crear la tabla que contendrá la información sobre el diseño de las prácticas
     */
    public void loadTableDisenoPract(){
        String titulos[] = {"Código", "Título", "Dificultad", "DNI Profesor", "Profesor Diseñador", "Fecha Diseno"};
        m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
        };
        this.table2 = new JTable();
        table2.setRowSelectionAllowed(true);
    }
        
    /**
     * Método que lista todos los controles escritos de la base de datos y los muestra en la tabla
     */
    public void mostrarControl(){
        try {
            controles = daoControl.findAll();
            String titulos[] = {"Código", "Preguntas", "Fecha", "Nº Matrícula Alumno", "Alumno", "Nota"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            int j=0;
            for (ControlEscrito getC: controles.values()) {
                for(int key: getC.getNotasControl().keySet()){
                    m.addRow(O);
                    m.setValueAt(getC.getNumControl(), j, 0);
                    m.setValueAt(getC.getNumPreguntas(), j, 1);
                    m.setValueAt(getC.getFecha(), j, 2);
                    m.setValueAt(key, j, 3);
                    m.setValueAt(daoAlum.findByPK(key).getNombre(), j, 4);
                    m.setValueAt(getC.getNotasControl().get(key), j, 5);
                    
                    m.isCellEditable(j, 0);
                    m.isCellEditable(j, 1);
                    m.isCellEditable(j, 2);
                    m.isCellEditable(j, 3);
                    m.isCellEditable(j, 4);
                    m.isCellEditable(j, 5);
                    j++;
                }
            }
            table.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
    
    /**
     * Método que lista todas las notas de las prácticas de la base de datos y las muestra en la tabla
     */
    public void mostrarNotasPract(){
        try {
            notasPracticas = daoPract.findAllNotas();
            String titulos[] = {"Código", "Título", "Dificultad", "Fecha Realización", "Nº Matrícula Alumno", "Alumno", "Nota"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            int j=0;
            for(Practica getPa: notasPracticas.values()){
                for(Date k1: getPa.getNotasPractica().keySet()){
                    for(int k2: getPa.getNotasPractica().get(k1).keySet()){
                        m.addRow(O);
                        m.setValueAt(getPa.getCodPractica(), j, 0);
                        m.setValueAt(getPa.getTitulo(), j, 1);
                        m.setValueAt(getPa.getDificultad().toString(), j, 2);
                        m.setValueAt(k1, j, 3);
                        m.setValueAt(k2, j, 4);
                        m.setValueAt(daoAlum.findByPK(k2).getNombre(), j, 5);
                        m.setValueAt(getPa.getNotasPractica().get(k1).get(k2), j, 6);
                            
                        m.isCellEditable(j, 0);
                        m.isCellEditable(j, 1);
                        m.isCellEditable(j, 2);
                        m.isCellEditable(j, 3);
                        m.isCellEditable(j, 4);
                        m.isCellEditable(j, 5);
                        m.isCellEditable(j, 6);
                        j++;
                    }
                    
                }
            }
            table1.setModel(m);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        }
    }
    
    /**
     * Método que lista todos los profesores que han diseñado las prácticas junto a la fecha del diseño y los muestra en la tabla
     */
    public void mostrarDisenoPract(){
        try {
            disenoPracticas = daoPract.findAllDiseno();
            String titulos[] = {"Código", "Título", "Dificultad", "DNI Profesor", "Profesor Diseñador", "Fecha Diseno"};
            m = new DefaultTableModel(null, titulos){
            @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Object O[] = null;
            int j=0;
            for (Practica getPa: disenoPracticas.values()) {
                for(String key: getPa.getProfDiseno().keySet()){
                    m.addRow(O);
                    m.setValueAt(getPa.getCodPractica(), j, 0);
                    m.setValueAt(getPa.getTitulo(), j, 1);
                    m.setValueAt(getPa.getDificultad().toString(), j, 2);
                    m.setValueAt(key, j, 3);
                    m.setValueAt(daoProf.findByPK(key).getNombre(), j, 4);
                    m.setValueAt(getPa.getProfDiseno().get(key), j, 5);
                    
                    m.isCellEditable(j, 0);
                    m.isCellEditable(j, 1);
                    m.isCellEditable(j, 2);
                    m.isCellEditable(j, 3);
                    m.isCellEditable(j, 4);
                    m.isCellEditable(j, 5);
                    j++;
                }
            }
            table2.setModel(m);
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
        loadTableNotasPract();
        mostrarNotasPract();
        this.jScrollPane2 = new JScrollPane(table1);
        panelesPract.addTab("Realización", jScrollPane2);
        loadTableDisenoPract();
        mostrarDisenoPract();
        this.jScrollPane3 = new JScrollPane(table2);
        panelesPract.addTab("Diseño", jScrollPane3);
        paneles.addTab("Prácticas", panelesPract);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogInsertarControl = new javax.swing.JDialog();
        labelAnadir = new javax.swing.JLabel();
        labelNuevoCod = new javax.swing.JLabel();
        txtNuevoCod = new javax.swing.JFormattedTextField();
        labelNuevoPreg = new javax.swing.JLabel();
        txtNuevoPreg = new javax.swing.JFormattedTextField();
        labelNuevaFecha = new javax.swing.JLabel();
        jDateNuevaFecha = new com.toedter.calendar.JDateChooser();
        labelNuevosDatos = new javax.swing.JLabel();
        labelNuevoNumMatr = new javax.swing.JLabel();
        txtNuevoNumMatr = new javax.swing.JFormattedTextField();
        labelNuevaNota = new javax.swing.JLabel();
        txtNuevaNota = new javax.swing.JFormattedTextField();
        btnNuevoAceptar = new javax.swing.JButton();
        btnNuevoCancelar = new javax.swing.JButton();
        dialogModificarControl = new javax.swing.JDialog();
        labelModificar = new javax.swing.JLabel();
        labelModCod = new javax.swing.JLabel();
        txtModCod = new javax.swing.JFormattedTextField();
        labelModPreg = new javax.swing.JLabel();
        txtModPreg = new javax.swing.JFormattedTextField();
        labelModFecha = new javax.swing.JLabel();
        jDateModFecha = new com.toedter.calendar.JDateChooser();
        labelModDatos = new javax.swing.JLabel();
        labelModNumMatr = new javax.swing.JLabel();
        txtModNumMatr = new javax.swing.JFormattedTextField();
        labelModNota = new javax.swing.JLabel();
        txtModNota = new javax.swing.JFormattedTextField();
        btnModAceptar = new javax.swing.JButton();
        btnModCancelar = new javax.swing.JButton();
        dialogBorrarControl = new javax.swing.JDialog();
        labelBorrar = new javax.swing.JLabel();
        btnBorrarTodo = new javax.swing.JButton();
        btnBorrarUno = new javax.swing.JButton();
        dialogInsertarRealiz = new javax.swing.JDialog();
        labelAnadir1 = new javax.swing.JLabel();
        labelNuevoCod1 = new javax.swing.JLabel();
        txtNuevoCod1 = new javax.swing.JFormattedTextField();
        labelNuevoTitulo = new javax.swing.JLabel();
        txtNuevoTitulo = new javax.swing.JTextField();
        labelNuevaDificultad = new javax.swing.JLabel();
        boxNuevaDificultad = new javax.swing.JComboBox<>();
        labelNuevosDatos1 = new javax.swing.JLabel();
        labelNuevoNumMatr1 = new javax.swing.JLabel();
        txtNuevoNumMatr1 = new javax.swing.JFormattedTextField();
        labelNuevaNota1 = new javax.swing.JLabel();
        txtNuevaNota1 = new javax.swing.JFormattedTextField();
        labelNuevaFechaReal = new javax.swing.JLabel();
        jDateNuevaFechaReal = new com.toedter.calendar.JDateChooser();
        btnNuevoAceptar1 = new javax.swing.JButton();
        btnNuevoCancelar1 = new javax.swing.JButton();
        dialogModificarRealiz = new javax.swing.JDialog();
        labelModificar1 = new javax.swing.JLabel();
        labelModCod1 = new javax.swing.JLabel();
        txtModCod1 = new javax.swing.JFormattedTextField();
        labelModTitulo = new javax.swing.JLabel();
        txtModTitulo = new javax.swing.JTextField();
        labelModDificultad = new javax.swing.JLabel();
        boxModDificultad = new javax.swing.JComboBox<>();
        labelModDatos1 = new javax.swing.JLabel();
        labelModNumMatr1 = new javax.swing.JLabel();
        txtModNumMatr1 = new javax.swing.JFormattedTextField();
        labelModNota1 = new javax.swing.JLabel();
        txtModNota1 = new javax.swing.JFormattedTextField();
        labelModFechaReal = new javax.swing.JLabel();
        jDateModFechaReal = new com.toedter.calendar.JDateChooser();
        btnModAceptar1 = new javax.swing.JButton();
        btnModCancelar1 = new javax.swing.JButton();
        dialogBorrarRealiz = new javax.swing.JDialog();
        labelBorrar1 = new javax.swing.JLabel();
        btnBorrarTodo1 = new javax.swing.JButton();
        btnBorrarUno1 = new javax.swing.JButton();
        dialogInsertarDiseno = new javax.swing.JDialog();
        labelAnadir2 = new javax.swing.JLabel();
        labelNuevoCod2 = new javax.swing.JLabel();
        txtNuevoCod2 = new javax.swing.JFormattedTextField();
        labelNuevoTitulo1 = new javax.swing.JLabel();
        txtNuevoTitulo1 = new javax.swing.JTextField();
        labelNuevaDificultad1 = new javax.swing.JLabel();
        labelNuevaFechaDis = new javax.swing.JLabel();
        jDateNuevaFechaDis = new com.toedter.calendar.JDateChooser();
        labelDatosProf = new javax.swing.JLabel();
        labelNuevoDNI = new javax.swing.JLabel();
        txtNuevoDNI = new javax.swing.JFormattedTextField();
        btnNuevoAceptar2 = new javax.swing.JButton();
        btnNuevoCancelar2 = new javax.swing.JButton();
        boxNuevaDificultad1 = new javax.swing.JComboBox<>();
        dialogModificarDiseno = new javax.swing.JDialog();
        labelModificar2 = new javax.swing.JLabel();
        labelModCod2 = new javax.swing.JLabel();
        txtModCod2 = new javax.swing.JFormattedTextField();
        labelModTitulo1 = new javax.swing.JLabel();
        txtModTitulo1 = new javax.swing.JTextField();
        labelModDificultad1 = new javax.swing.JLabel();
        labelModFechaDis = new javax.swing.JLabel();
        jDateModFechaDis = new com.toedter.calendar.JDateChooser();
        labelDatosProf1 = new javax.swing.JLabel();
        labelModDNI = new javax.swing.JLabel();
        txtModDNI = new javax.swing.JFormattedTextField();
        btnModAceptar2 = new javax.swing.JButton();
        btnModCancelar2 = new javax.swing.JButton();
        boxModDificultad1 = new javax.swing.JComboBox<>();
        dialogBorrarDiseno = new javax.swing.JDialog();
        labelBorrar2 = new javax.swing.JLabel();
        btnBorrarTodo2 = new javax.swing.JButton();
        btnBorrarUno2 = new javax.swing.JButton();
        labelArea = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        paneles = new javax.swing.JTabbedPane();
        panelesPract = new javax.swing.JTabbedPane();

        labelAnadir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAnadir.setText("Nuevo resultado de control escrito:");

        labelNuevoCod.setText("Número Control:");

        txtNuevoCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelNuevoPreg.setText("Número de preguntas:");

        txtNuevoPreg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelNuevaFecha.setText("Fecha:");

        jDateNuevaFecha.setDateFormatString("yyyy-mm-dd");

        labelNuevosDatos.setText("Datos del alumno:");

        labelNuevoNumMatr.setText("Nº matrícula:");

        txtNuevoNumMatr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelNuevaNota.setText("Nota:");

        txtNuevaNota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##.0#"))));

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

        javax.swing.GroupLayout dialogInsertarControlLayout = new javax.swing.GroupLayout(dialogInsertarControl.getContentPane());
        dialogInsertarControl.getContentPane().setLayout(dialogInsertarControlLayout);
        dialogInsertarControlLayout.setHorizontalGroup(
            dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAnadir)
                    .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                        .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogInsertarControlLayout.createSequentialGroup()
                                .addComponent(labelNuevoCod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNuevoCod, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogInsertarControlLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(labelNuevoNumMatr)))
                        .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNuevoNumMatr, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNuevoAceptar))
                                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(btnNuevoCancelar))
                                    .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(labelNuevaNota)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtNuevaNota, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogInsertarControlLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelNuevosDatos)
                                    .addComponent(labelNuevoPreg))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNuevoPreg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(labelNuevaFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateNuevaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        dialogInsertarControlLayout.setVerticalGroup(
            dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelAnadir)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarControlLayout.createSequentialGroup()
                        .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNuevoCod)
                            .addComponent(txtNuevoCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNuevoPreg)
                            .addComponent(txtNuevoPreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNuevaFecha))
                        .addGap(29, 29, 29)
                        .addComponent(labelNuevosDatos))
                    .addComponent(jDateNuevaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoNumMatr)
                    .addComponent(txtNuevoNumMatr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNuevaNota)
                    .addComponent(txtNuevaNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dialogInsertarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoAceptar)
                    .addComponent(btnNuevoCancelar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        labelModificar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelModificar.setText("Modificar resultado de control escrito:");

        labelModCod.setText("Número Control:");

        txtModCod.setEditable(false);
        txtModCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelModPreg.setText("Número de preguntas:");

        txtModPreg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelModFecha.setText("Fecha:");

        jDateModFecha.setDateFormatString("yyyy-MM-dd");

        labelModDatos.setText("Datos del alumno:");

        labelModNumMatr.setText("Nº matrícula:");

        txtModNumMatr.setEditable(false);
        txtModNumMatr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelModNota.setText("Nota:");

        txtModNota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##.0#"))));

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

        javax.swing.GroupLayout dialogModificarControlLayout = new javax.swing.GroupLayout(dialogModificarControl.getContentPane());
        dialogModificarControl.getContentPane().setLayout(dialogModificarControlLayout);
        dialogModificarControlLayout.setHorizontalGroup(
            dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarControlLayout.createSequentialGroup()
                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogModificarControlLayout.createSequentialGroup()
                                .addComponent(labelModCod)
                                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(txtModCod, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogModificarControlLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelModNumMatr)))
                                .addGap(15, 15, 15)
                                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                                        .addComponent(btnModAceptar)
                                        .addGap(47, 47, 47)
                                        .addComponent(btnModCancelar))
                                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                                        .addComponent(labelModPreg)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtModPreg, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                                        .addComponent(txtModNumMatr, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(74, 74, 74)
                                        .addComponent(labelModNota)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtModNota, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelModFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateModFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelModificar)))
                    .addGroup(dialogModificarControlLayout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(labelModDatos)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogModificarControlLayout.setVerticalGroup(
            dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarControlLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelModificar)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelModCod)
                        .addComponent(txtModCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelModPreg)
                        .addComponent(txtModPreg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelModFecha))
                    .addComponent(jDateModFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(labelModDatos)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModNumMatr)
                    .addComponent(txtModNumMatr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelModNota)
                    .addComponent(txtModNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dialogModificarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModAceptar)
                    .addComponent(btnModCancelar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        labelBorrar.setText("¿Desea eliminar todos los registros del control?");

        btnBorrarTodo.setText("Sí, eliminar todos");
        btnBorrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodoActionPerformed(evt);
            }
        });

        btnBorrarUno.setText("No, eliminar solo este registro");
        btnBorrarUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarUnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogBorrarControlLayout = new javax.swing.GroupLayout(dialogBorrarControl.getContentPane());
        dialogBorrarControl.getContentPane().setLayout(dialogBorrarControlLayout);
        dialogBorrarControlLayout.setHorizontalGroup(
            dialogBorrarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarControlLayout.createSequentialGroup()
                .addGroup(dialogBorrarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogBorrarControlLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnBorrarTodo)
                        .addGap(29, 29, 29)
                        .addComponent(btnBorrarUno))
                    .addGroup(dialogBorrarControlLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(labelBorrar)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        dialogBorrarControlLayout.setVerticalGroup(
            dialogBorrarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarControlLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(labelBorrar)
                .addGap(18, 18, 18)
                .addGroup(dialogBorrarControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarTodo)
                    .addComponent(btnBorrarUno))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        labelAnadir1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAnadir1.setText("Nuevo resultado de práctica:");

        labelNuevoCod1.setText("Número Práctica:");

        txtNuevoCod1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelNuevoTitulo.setText("Título:");

        labelNuevaDificultad.setText("Dificultad:");

        boxNuevaDificultad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta" }));

        labelNuevosDatos1.setText("Datos del alumno:");

        labelNuevoNumMatr1.setText("Nº matrícula:");

        txtNuevoNumMatr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelNuevaNota1.setText("Nota:");

        txtNuevaNota1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##.0#"))));

        labelNuevaFechaReal.setText("Fecha realización de la práctica:");

        jDateNuevaFechaReal.setDateFormatString("yyyy-MM-dd");

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

        javax.swing.GroupLayout dialogInsertarRealizLayout = new javax.swing.GroupLayout(dialogInsertarRealiz.getContentPane());
        dialogInsertarRealiz.getContentPane().setLayout(dialogInsertarRealizLayout);
        dialogInsertarRealizLayout.setHorizontalGroup(
            dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                        .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelNuevoCod1)
                            .addComponent(labelNuevoNumMatr1))
                        .addGap(18, 18, 18)
                        .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoNumMatr1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoCod1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(labelNuevoTitulo)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(labelNuevaDificultad)
                                .addGap(18, 18, 18)
                                .addComponent(boxNuevaDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                                .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(labelNuevaNota1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNuevaNota1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(labelNuevaFechaReal))
                                    .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(labelNuevosDatos1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jDateNuevaFechaReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(labelAnadir1)
                    .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(btnNuevoAceptar1)
                        .addGap(69, 69, 69)
                        .addComponent(btnNuevoCancelar1)))
                .addContainerGap())
        );
        dialogInsertarRealizLayout.setVerticalGroup(
            dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarRealizLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelAnadir1)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelNuevoTitulo)
                        .addComponent(labelNuevaDificultad)
                        .addComponent(txtNuevoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boxNuevaDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelNuevoCod1)
                        .addComponent(txtNuevoCod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(labelNuevosDatos1)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelNuevoNumMatr1)
                        .addComponent(txtNuevoNumMatr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelNuevaNota1)
                        .addComponent(txtNuevaNota1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelNuevaFechaReal))
                    .addComponent(jDateNuevaFechaReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dialogInsertarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoAceptar1)
                    .addComponent(btnNuevoCancelar1))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        labelModificar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelModificar1.setText("Modificar resultado de práctica:");

        labelModCod1.setText("Número Práctica:");

        txtModCod1.setEditable(false);
        txtModCod1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelModTitulo.setText("Título:");

        labelModDificultad.setText("Dificultad:");

        boxModDificultad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta" }));

        labelModDatos1.setText("Datos del alumno:");

        labelModNumMatr1.setText("Nº matrícula:");

        txtModNumMatr1.setEditable(false);
        txtModNumMatr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        labelModNota1.setText("Nota:");

        txtModNota1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##.0#"))));

        labelModFechaReal.setText("Fecha realización de la práctica:");

        jDateModFechaReal.setDateFormatString("yyyy-MM-dd");

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

        javax.swing.GroupLayout dialogModificarRealizLayout = new javax.swing.GroupLayout(dialogModificarRealiz.getContentPane());
        dialogModificarRealiz.getContentPane().setLayout(dialogModificarRealizLayout);
        dialogModificarRealizLayout.setHorizontalGroup(
            dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelModCod1)
                                    .addComponent(labelModNumMatr1))
                                .addGap(18, 18, 18)
                                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtModNumMatr1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtModCod1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(labelModTitulo)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtModTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addComponent(labelModDificultad)
                                        .addGap(18, 18, 18)
                                        .addComponent(boxModDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(labelModNota1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtModNota1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(labelModFechaReal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateModFechaReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(labelModificar1)
                            .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                                .addGap(169, 169, 169)
                                .addComponent(btnModAceptar1)
                                .addGap(69, 69, 69)
                                .addComponent(btnModCancelar1))))
                    .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(labelModDatos1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogModificarRealizLayout.setVerticalGroup(
            dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarRealizLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelModificar1)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelModTitulo)
                        .addComponent(labelModDificultad)
                        .addComponent(txtModTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boxModDificultad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelModCod1)
                        .addComponent(txtModCod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(labelModDatos1)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelModNumMatr1)
                        .addComponent(txtModNumMatr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelModNota1)
                        .addComponent(txtModNota1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelModFechaReal))
                    .addComponent(jDateModFechaReal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(dialogModificarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModAceptar1)
                    .addComponent(btnModCancelar1))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        labelBorrar1.setText("¿Desea eliminar todos los registros de la práctica?");

        btnBorrarTodo1.setText("Sí, eliminar todos");
        btnBorrarTodo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodo1ActionPerformed(evt);
            }
        });

        btnBorrarUno1.setText("No, eliminar solo este registro");
        btnBorrarUno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarUno1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogBorrarRealizLayout = new javax.swing.GroupLayout(dialogBorrarRealiz.getContentPane());
        dialogBorrarRealiz.getContentPane().setLayout(dialogBorrarRealizLayout);
        dialogBorrarRealizLayout.setHorizontalGroup(
            dialogBorrarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarRealizLayout.createSequentialGroup()
                .addGroup(dialogBorrarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogBorrarRealizLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnBorrarTodo1)
                        .addGap(29, 29, 29)
                        .addComponent(btnBorrarUno1))
                    .addGroup(dialogBorrarRealizLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(labelBorrar1)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        dialogBorrarRealizLayout.setVerticalGroup(
            dialogBorrarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarRealizLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(labelBorrar1)
                .addGap(18, 18, 18)
                .addGroup(dialogBorrarRealizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarTodo1)
                    .addComponent(btnBorrarUno1))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        labelAnadir2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAnadir2.setText("Añadir nuevos datos del diseño de la práctica:");

        labelNuevoCod2.setText("Número Práctica:");

        try {
            txtNuevoCod2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelNuevoTitulo1.setText("Título:");

        labelNuevaDificultad1.setText("Dificultad:");

        labelNuevaFechaDis.setText("Fecha diseño:");

        jDateNuevaFechaDis.setDateFormatString("yyyy-MM-dd");

        labelDatosProf.setText("Datos profesor diseñador:");

        labelNuevoDNI.setText("DNI:");

        try {
            txtNuevoDNI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########U")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnNuevoAceptar2.setText("Aceptar");
        btnNuevoAceptar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoAceptar2ActionPerformed(evt);
            }
        });

        btnNuevoCancelar2.setText("Cancelar");
        btnNuevoCancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCancelar2ActionPerformed(evt);
            }
        });

        boxNuevaDificultad1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta" }));

        javax.swing.GroupLayout dialogInsertarDisenoLayout = new javax.swing.GroupLayout(dialogInsertarDiseno.getContentPane());
        dialogInsertarDiseno.getContentPane().setLayout(dialogInsertarDisenoLayout);
        dialogInsertarDisenoLayout.setHorizontalGroup(
            dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAnadir2)
                            .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                                .addComponent(labelNuevoCod2)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoCod2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(labelNuevoTitulo1)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                                .addComponent(labelNuevoDNI)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelDatosProf)))
                    .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(btnNuevoAceptar2)))
                .addGap(16, 16, 16)
                .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                        .addComponent(labelNuevaDificultad1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boxNuevaDificultad1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelNuevaFechaDis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateNuevaFechaDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevoCancelar2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogInsertarDisenoLayout.setVerticalGroup(
            dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogInsertarDisenoLayout.createSequentialGroup()
                        .addComponent(labelAnadir2)
                        .addGap(18, 18, 18)
                        .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNuevoCod2)
                            .addComponent(txtNuevoCod2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNuevoTitulo1)
                            .addComponent(labelNuevaDificultad1)
                            .addComponent(txtNuevoTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNuevaFechaDis)
                            .addComponent(boxNuevaDificultad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateNuevaFechaDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(labelDatosProf)
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNuevoDNI)
                    .addComponent(txtNuevoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dialogInsertarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoAceptar2)
                    .addComponent(btnNuevoCancelar2))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        labelModificar2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelModificar2.setText("Modificar datos del diseño de la práctica:");

        labelModCod2.setText("Número Práctica:");

        txtModCod2.setEditable(false);
        try {
            txtModCod2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelModTitulo1.setText("Título:");

        labelModDificultad1.setText("Dificultad:");

        labelModFechaDis.setText("Fecha diseño:");

        jDateModFechaDis.setDateFormatString("yyyy-MM-dd");

        labelDatosProf1.setText("Datos profesor diseñador:");

        labelModDNI.setText("DNI:");

        txtModDNI.setEditable(false);
        try {
            txtModDNI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########U")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnModAceptar2.setText("Aceptar");
        btnModAceptar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModAceptar2ActionPerformed(evt);
            }
        });

        btnModCancelar2.setText("Cancelar");
        btnModCancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModCancelar2ActionPerformed(evt);
            }
        });

        boxModDificultad1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baja", "Media", "Alta" }));

        javax.swing.GroupLayout dialogModificarDisenoLayout = new javax.swing.GroupLayout(dialogModificarDiseno.getContentPane());
        dialogModificarDiseno.getContentPane().setLayout(dialogModificarDisenoLayout);
        dialogModificarDisenoLayout.setHorizontalGroup(
            dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelModificar2)
                            .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                                .addComponent(labelModCod2)
                                .addGap(18, 18, 18)
                                .addComponent(txtModCod2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(labelModTitulo1)
                                .addGap(18, 18, 18)
                                .addComponent(txtModTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                                .addComponent(labelModDNI)
                                .addGap(18, 18, 18)
                                .addComponent(txtModDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelDatosProf1)))
                    .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(btnModAceptar2)))
                .addGap(16, 16, 16)
                .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                        .addComponent(labelModDificultad1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boxModDificultad1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(labelModFechaDis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateModFechaDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnModCancelar2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogModificarDisenoLayout.setVerticalGroup(
            dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelModificar2)
                .addGap(18, 18, 18)
                .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogModificarDisenoLayout.createSequentialGroup()
                        .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelModCod2)
                            .addComponent(txtModCod2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelModTitulo1)
                            .addComponent(labelModDificultad1)
                            .addComponent(txtModTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelModFechaDis)
                            .addComponent(boxModDificultad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(labelDatosProf1)
                        .addGap(18, 18, 18)
                        .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelModDNI)
                            .addComponent(txtModDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateModFechaDis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dialogModificarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModAceptar2)
                    .addComponent(btnModCancelar2))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        labelBorrar2.setText("¿Desea eliminar todos los registros de la práctica?");

        btnBorrarTodo2.setText("Sí, eliminar todos");
        btnBorrarTodo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodo2ActionPerformed(evt);
            }
        });

        btnBorrarUno2.setText("No, eliminar solo este registro");
        btnBorrarUno2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarUno2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogBorrarDisenoLayout = new javax.swing.GroupLayout(dialogBorrarDiseno.getContentPane());
        dialogBorrarDiseno.getContentPane().setLayout(dialogBorrarDisenoLayout);
        dialogBorrarDisenoLayout.setHorizontalGroup(
            dialogBorrarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarDisenoLayout.createSequentialGroup()
                .addGroup(dialogBorrarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogBorrarDisenoLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnBorrarTodo2)
                        .addGap(29, 29, 29)
                        .addComponent(btnBorrarUno2))
                    .addGroup(dialogBorrarDisenoLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(labelBorrar2)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        dialogBorrarDisenoLayout.setVerticalGroup(
            dialogBorrarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogBorrarDisenoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(labelBorrar2)
                .addGap(18, 18, 18)
                .addGroup(dialogBorrarDisenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarTodo2)
                    .addComponent(btnBorrarUno2))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelArea.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        labelArea.setText("Área de Profesores");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
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

        paneles.addTab("tab1", panelesPract);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBorrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paneles)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelArea)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelArea)
                    .addComponent(btnLogOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnBorrar)
                    .addComponent(btnModificar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método para mostrar la ventana con el formulario donde introduciremos la información del control escrito nuevo o práctica nueva que queremos registrar cuando pulsemos el botón de "Nuevo"
     * @param evt Action Event
     */
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        if(paneles.getSelectedIndex()==0){
            dialogInsertarControl.setSize(800,400);
            dialogInsertarControl.setModal(true);
            dialogInsertarControl.setVisible(true);
            mostrarControl();
        } else if(paneles.getSelectedIndex()==1){
            if(panelesPract.getSelectedIndex()==0){
                dialogInsertarRealiz.setSize(600,600);
                dialogInsertarRealiz.setModal(true);
                dialogInsertarRealiz.setVisible(true);
                mostrarNotasPract();
            } else if(panelesPract.getSelectedIndex()==1){
                dialogInsertarDiseno.setSize(600,600);
                dialogInsertarDiseno.setModal(true);
                dialogInsertarDiseno.setVisible(true);
                mostrarDisenoPract();
            }
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

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
     * Método para mostrar la ventana con el formulario donde modificaremos la información del control escrito o práctica que hemos seleccionado de la tabla cuando pulsemos el botón de "Modificar"
     * @param evt Action Event
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int fsel;
        if(paneles.getSelectedIndex()==0){
            int codControl, nPreg, numMatr;
            Date fecha;
            double nota;
            fsel=table.getSelectedRow();
            if(fsel==-1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar el resultado a modificar");
            } else{
                m = (DefaultTableModel) table.getModel();
                codControl = (int) m.getValueAt(fsel,0);
                nPreg = (int) m.getValueAt(fsel, 1);
                fecha = (Date) m.getValueAt(fsel, 2);
                numMatr = (int) m.getValueAt(fsel, 3);
                nota = (double) m.getValueAt(fsel, 5);
                txtModCod.setText(""+codControl);
                txtModPreg.setText(""+nPreg);
                jDateModFecha.setDate(fecha);
                txtModNumMatr.setText(""+numMatr);
                txtModNota.setText(""+nota);
                dialogModificarControl.setSize(800,400);
                dialogModificarControl.setModal(true);
                dialogModificarControl.setVisible(true);
                mostrarControl();
            }
        } else if(paneles.getSelectedIndex()==1){
            int codPract;
            String titulo;
            Dificultad dificultad;
            if(panelesPract.getSelectedIndex()==0){
                Date fechaReal;
                int numMatr;
                double nota;
                fsel=table1.getSelectedRow();
                if(fsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el resultado a modificar");
                } else{
                    m = (DefaultTableModel) table1.getModel();
                    codPract = (int) m.getValueAt(fsel,0);
                    titulo = (String) m.getValueAt(fsel, 1);
                    dificultad = Dificultad.valueOf(m.getValueAt(fsel,2).toString());
                    fechaReal = Date.valueOf(m.getValueAt(fsel, 3).toString());
                    numMatr = (int) m.getValueAt(fsel, 4);
                    nota = (double) m.getValueAt(fsel, 6);
                    txtModCod1.setText(""+codPract);
                    txtModTitulo.setText(titulo);
                    boxModDificultad.setSelectedItem(dificultad.toString());
                    jDateModFechaReal.setDate(fechaReal);
                    txtModNumMatr1.setText(""+numMatr);
                    txtModNota1.setText(""+nota);
                    dialogModificarRealiz.setSize(800,400);
                    dialogModificarRealiz.setModal(true);
                    dialogModificarRealiz.setVisible(true);
                    mostrarNotasPract();
                }
            } else if(panelesPract.getSelectedIndex()==1){
                Date fechaDis;
                String dni;
                fsel=table2.getSelectedRow();
                if(fsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el resultado a modificar");
                } else{
                    m = (DefaultTableModel) table2.getModel();
                    codPract = (int) m.getValueAt(fsel,0);
                    titulo = (String) m.getValueAt(fsel, 1);
                    dificultad = Dificultad.valueOf(m.getValueAt(fsel, 2).toString());
                    dni = (String) m.getValueAt(fsel, 3);
                    fechaDis = Date.valueOf(m.getValueAt(fsel, 5).toString());
                    txtModCod2.setText(""+codPract);
                    txtModTitulo1.setText(titulo);
                    boxModDificultad1.setSelectedItem(dificultad.toString());
                    jDateModFechaDis.setDate(fechaDis);
                    txtModDNI.setText(dni);
                    dialogModificarDiseno.setSize(800,400);
                    dialogModificarDiseno.setModal(true);
                    dialogModificarDiseno.setVisible(true);
                    mostrarDisenoPract();
                }
            }
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * Método para mostrar la ventana de confirmación de eliminación de un control escrito o práctica que hemos seleccionado de la tabla al pulsar el botón "Borrar"
     * @param evt Action Event
     */
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        int filsel;
        if(paneles.getSelectedIndex()==0){
            filsel=table.getSelectedRow();
            if(filsel==-1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar el registro a borrar");
            } else{
                dialogBorrarControl.setSize(400,400);
                dialogBorrarControl.setModal(true);
                dialogBorrarControl.setVisible(true);
            }
        } else if(paneles.getSelectedIndex()==1){
            if(panelesPract.getSelectedIndex()==0){
                filsel=table1.getSelectedRow();
                if(filsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el registro a borrar");
                } else{
                    dialogBorrarRealiz.setSize(400,400);
                    dialogBorrarRealiz.setModal(true);
                    dialogBorrarRealiz.setVisible(true);
                }
            } else if(panelesPract.getSelectedIndex()==1){
                filsel=table2.getSelectedRow();
                if(filsel==-1){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar el registro a borrar");
                } else{
                    dialogBorrarDiseno.setSize(400,400);
                    dialogBorrarDiseno.setModal(true);
                    dialogBorrarDiseno.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para registrar un nueva nota de un control escrito al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnNuevoAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAceptarActionPerformed
        int cod = Integer.parseInt(txtNuevoCod.getText());
        int preg = Integer.parseInt(txtNuevoPreg.getText());
        Date fecha = Date.valueOf(dateformat.format(jDateNuevaFecha.getDate()));
        boolean noExiste = controles.values().stream().noneMatch(c-> c.getNumControl()==cod);
        if(noExiste){
            ControlEscrito c = new ControlEscrito(cod, preg, fecha);
            controles.put(cod, c);
        }
        
        int numMatr = Integer.parseInt(txtNuevoNumMatr.getText());
        double nota = Double.parseDouble(txtNuevaNota.getText().replace(",", "."));
        
        controles.get(cod).getNotasControl().put(numMatr, nota);
        
        try {
            if(noExiste){
                daoControl.insertControl(controles.get(cod));
            }
            daoControl.insertNota(controles.get(cod), numMatr);
            dialogInsertarControl.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnNuevoAceptarActionPerformed

    /**
     * Método para cancelar el registro de un nueva nota de control escrito al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnNuevoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCancelarActionPerformed
        dialogInsertarControl.dispose();
    }//GEN-LAST:event_btnNuevoCancelarActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para registrar datos sobre el diseño de una práctica al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnNuevoAceptar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAceptar2ActionPerformed
        // TODO add your handling code here:
        int cod = Integer.parseInt(txtNuevoCod2.getText());
        String titulo = txtNuevoTitulo1.getText();
        Dificultad dificultad = Dificultad.valueOf(boxNuevaDificultad1.getSelectedItem().toString());
        boolean noExiste = disenoPracticas.values().stream().noneMatch(pa -> pa.getCodPractica()==cod);
        if(noExiste){
            Practica pa = new Practica(cod, titulo, dificultad);
            disenoPracticas.put(cod, pa);
        }
        
        Date fecha = Date.valueOf(dateformat.format(jDateNuevaFechaDis.getDate()));
        String dni = txtNuevoDNI.getText();
       
        disenoPracticas.get(cod).getProfDiseno().put(dni, fecha);
        
        try {
            if(noExiste){
                daoPract.insertPractica(disenoPracticas.get(cod));
            }
            daoPract.insertDiseno(disenoPracticas.get(cod), dni);
            dialogInsertarDiseno.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnNuevoAceptar2ActionPerformed

    /**
     * Método para cancelar el registro de un nuevo profesor diseñador de una práctica y fecha al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnNuevoCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCancelar2ActionPerformed
        // TODO add your handling code here:
        dialogInsertarDiseno.dispose();
    }//GEN-LAST:event_btnNuevoCancelar2ActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para registrar un nueva nota de una práctica al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnNuevoAceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoAceptar1ActionPerformed
        // TODO add your handling code here:
        int cod = Integer.parseInt(txtNuevoCod1.getText());
        String titulo = txtNuevoTitulo.getText();
        Dificultad dificultad = Dificultad.valueOf(boxNuevaDificultad.getSelectedItem().toString());
        boolean noExiste = notasPracticas.values().stream().noneMatch(pa -> pa.getCodPractica()==cod);
        if(noExiste){
            Practica pa = new Practica(cod, titulo, dificultad);
            notasPracticas.put(cod, pa);
        }
        
        Date fecha = Date.valueOf(dateformat.format(jDateNuevaFechaReal.getDate()));
        int numMatr = Integer.parseInt(txtNuevoNumMatr1.getText());
        double nota = Double.parseDouble(txtNuevaNota1.getText().replace(",", "."));
       
        notasPracticas.get(cod).addNota(fecha, numMatr, nota);
        
        try {
            if(noExiste){
                daoPract.insertPractica(notasPracticas.get(cod));
            }
            daoPract.insertNota(notasPracticas.get(cod), fecha, numMatr);
            dialogInsertarRealiz.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnNuevoAceptar1ActionPerformed

    /**
     * Método para cancelar el registro de un nueva nota de práctica al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnNuevoCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCancelar1ActionPerformed
        // TODO add your handling code here:
        dialogInsertarRealiz.dispose();
    }//GEN-LAST:event_btnNuevoCancelar1ActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para modificar una nota de un control escrito seleccionada al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnModAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModAceptarActionPerformed
        // TODO add your handling code here:
        int codControl = Integer.parseInt(txtModCod.getText());
        int numPreg = Integer.parseInt(txtModPreg.getText());
        Date fecha = Date.valueOf(dateformat.format(jDateModFecha.getDate()));
        int numMatr = Integer.parseInt(txtModNumMatr.getText());
        double nota = Double.parseDouble(txtModNota.getText().replace(",", "."));
        try {
            ControlEscrito c = controles.get(codControl);
            c.setNumControl(codControl);
            c.setNumPreguntas(numPreg);
            c.setFecha(fecha);
            c.getNotasControl().replace(numMatr, nota);
            daoControl.updateControl(c);
            daoControl.updateNota(c, numMatr);
            dialogModificarControl.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnModAceptarActionPerformed

    /**
     * Método para cancelar la modificación de una nota de un control escrito al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnModCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelarActionPerformed
        // TODO add your handling code here:
        dialogModificarControl.dispose();
    }//GEN-LAST:event_btnModCancelarActionPerformed

    /**
     * Método para confirmar la eliminación de toda la información de un control, incluidas todas las notas de ese control
     * @param evt Action Event
     */
    private void btnBorrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTodoActionPerformed
        // TODO add your handling code here:
        int filsel=table.getSelectedRow();
        int codControl;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table.getModel();
            codControl = (int) m.getValueAt(filsel, 0);
            try {
                daoControl.deleteControl(codControl);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarControl();
        }
        dialogBorrarControl.dispose();
    }//GEN-LAST:event_btnBorrarTodoActionPerformed

    /**
     * Método para confirmar la eliminación de la nota seleccionada de un control escrito
     * @param evt Action Event
     */
    private void btnBorrarUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarUnoActionPerformed
        // TODO add your handling code here:
        int filsel=table.getSelectedRow();
        int codControl, numMatr;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table.getModel();
            codControl = (int) m.getValueAt(filsel, 0);
            numMatr = (int) m.getValueAt(filsel, 3);
            try {
                daoControl.deleteNota(codControl, numMatr);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarControl();
        }
        dialogBorrarControl.dispose();
    }//GEN-LAST:event_btnBorrarUnoActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para modificar una nota de una práctica seleccionada al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnModAceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModAceptar1ActionPerformed
        // TODO add your handling code here:
        int codPract = Integer.parseInt(txtModCod1.getText());
        String titulo = txtModTitulo.getText();
        Dificultad dificultad = Dificultad.valueOf(boxModDificultad.getSelectedItem().toString());
        Date fechaReal = Date.valueOf(dateformat.format(jDateModFechaReal.getDate()));
        int numMatr = Integer.parseInt(txtModNumMatr1.getText());
        double nota = Double.parseDouble(txtModNota1.getText().replace(",", "."));
        try {
            Practica pa = notasPracticas.get(codPract);
            pa.setCodPractica(codPract);
            pa.setTitulo(titulo);
            pa.setDificultad(dificultad);
            pa.getNotasPractica().get(fechaReal).replace(numMatr, nota);
            daoPract.updatePractica(pa);
            daoPract.updateNota(pa, fechaReal, numMatr);
            dialogModificarRealiz.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnModAceptar1ActionPerformed

    /**
     * Método para cancelar la modificación de una nota de una práctica al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnModCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelar1ActionPerformed
        // TODO add your handling code here:
        dialogModificarRealiz.dispose();
    }//GEN-LAST:event_btnModCancelar1ActionPerformed

    /**
     * Método para confirmar los datos que hemos introducido en el formulario para modificar un profesor que ha diseñado una práctica, con su fecha, seleccionado al pulsar el botón de "Aceptar"
     * @param evt Action Event
     */
    private void btnModAceptar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModAceptar2ActionPerformed
        // TODO add your handling code here:
        int codPract = Integer.parseInt(txtModCod2.getText());
        String titulo = txtModTitulo1.getText();
        Dificultad dificultad = Dificultad.valueOf(boxModDificultad1.getSelectedItem().toString());
        String dni = txtModDNI.getText();
        Date fechaDis = Date.valueOf(dateformat.format(jDateModFechaDis.getDate()));
        try {
            Practica pa = disenoPracticas.get(codPract);
            pa.setCodPractica(codPract);
            pa.setTitulo(titulo);
            pa.setDificultad(dificultad);
            pa.getProfDiseno().replace(dni, fechaDis);
            daoPract.updatePractica(pa);
            daoPract.updateDiseno(pa, dni);
            dialogModificarDiseno.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos o ya existen. Inténtelo de nuevo");
        }
    }//GEN-LAST:event_btnModAceptar2ActionPerformed

    /**
     * Método para cancelar la modificación de un profesor que ha diseñado una práctica, con su fecha, al pulsar el botón "Cancelar"
     * @param evt Action Event
     */
    private void btnModCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelar2ActionPerformed
        // TODO add your handling code here:
        dialogModificarDiseno.dispose();
    }//GEN-LAST:event_btnModCancelar2ActionPerformed

    /**
     * Método para confirmar la eliminación de toda la información de una práctica, incluidas todas las notas de esa práctica
     * @param evt Action Event
     */
    private void btnBorrarTodo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTodo1ActionPerformed
        // TODO add your handling code here:
        int filsel=table1.getSelectedRow();
        int codPract;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table1.getModel();
            codPract = (int) m.getValueAt(filsel, 0);
            try {
                daoPract.deletePractica(codPract);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarNotasPract();
        }
        dialogBorrarRealiz.dispose();
    }//GEN-LAST:event_btnBorrarTodo1ActionPerformed

    /**
     * Método para confirmar la eliminación de la nota seleccionada de una práctica
     * @param evt Action Event
     */
    private void btnBorrarUno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarUno1ActionPerformed
        // TODO add your handling code here:
        int filsel=table1.getSelectedRow();
        int codPract, numMatr;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table1.getModel();
            codPract = (int) m.getValueAt(filsel, 0);
            numMatr = (int) m.getValueAt(filsel, 4);
            try {
                daoPract.deleteNota(codPract, numMatr);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarNotasPract();
        }
        dialogBorrarRealiz.dispose();
    }//GEN-LAST:event_btnBorrarUno1ActionPerformed

    /**
     * Método para confirmar la eliminación de toda la información de una práctica, incluida toda la información sobre el diseño de esa práctica
     * @param evt Action Event
     */
    private void btnBorrarTodo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTodo2ActionPerformed
        // TODO add your handling code here:
        int filsel=table2.getSelectedRow();
        int codPract;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table2.getModel();
            codPract = (int) m.getValueAt(filsel, 0);
            try {
                daoPract.deletePractica(codPract);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarDisenoPract();
        }
        dialogBorrarDiseno.dispose();
    }//GEN-LAST:event_btnBorrarTodo2ActionPerformed

    /**
     * Método para confirmar la eliminación del profesor diseñador seleccionado y la fecha de una práctica
     * @param evt Action Event
     */
    private void btnBorrarUno2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarUno2ActionPerformed
        // TODO add your handling code here:
        int filsel=table2.getSelectedRow();
        int codPract;
        String dni;
        int resp = JOptionPane.showConfirmDialog(null, "Los datos serán borrados permanentemente. ¿Desea continuar?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(resp==JOptionPane.YES_OPTION){
            m = (DefaultTableModel) table2.getModel();
            codPract = (int) m.getValueAt(filsel, 0);
            dni = (String) m.getValueAt(filsel, 3);
            try {
                daoPract.deleteDiseno(codPract, dni);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            mostrarDisenoPract();
        }
        dialogBorrarDiseno.dispose();
    }//GEN-LAST:event_btnBorrarUno2ActionPerformed

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
            java.util.logging.Logger.getLogger(VistaProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VistaProf().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(VistaProf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxModDificultad;
    private javax.swing.JComboBox<String> boxModDificultad1;
    private javax.swing.JComboBox<String> boxNuevaDificultad;
    private javax.swing.JComboBox<String> boxNuevaDificultad1;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBorrarTodo;
    private javax.swing.JButton btnBorrarTodo1;
    private javax.swing.JButton btnBorrarTodo2;
    private javax.swing.JButton btnBorrarUno;
    private javax.swing.JButton btnBorrarUno1;
    private javax.swing.JButton btnBorrarUno2;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnModAceptar;
    private javax.swing.JButton btnModAceptar1;
    private javax.swing.JButton btnModAceptar2;
    private javax.swing.JButton btnModCancelar;
    private javax.swing.JButton btnModCancelar1;
    private javax.swing.JButton btnModCancelar2;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevoAceptar;
    private javax.swing.JButton btnNuevoAceptar1;
    private javax.swing.JButton btnNuevoAceptar2;
    private javax.swing.JButton btnNuevoCancelar;
    private javax.swing.JButton btnNuevoCancelar1;
    private javax.swing.JButton btnNuevoCancelar2;
    private javax.swing.JDialog dialogBorrarControl;
    private javax.swing.JDialog dialogBorrarDiseno;
    private javax.swing.JDialog dialogBorrarRealiz;
    private javax.swing.JDialog dialogInsertarControl;
    private javax.swing.JDialog dialogInsertarDiseno;
    private javax.swing.JDialog dialogInsertarRealiz;
    private javax.swing.JDialog dialogModificarControl;
    private javax.swing.JDialog dialogModificarDiseno;
    private javax.swing.JDialog dialogModificarRealiz;
    private com.toedter.calendar.JDateChooser jDateModFecha;
    private com.toedter.calendar.JDateChooser jDateModFechaDis;
    private com.toedter.calendar.JDateChooser jDateModFechaReal;
    private com.toedter.calendar.JDateChooser jDateNuevaFecha;
    private com.toedter.calendar.JDateChooser jDateNuevaFechaDis;
    private com.toedter.calendar.JDateChooser jDateNuevaFechaReal;
    private javax.swing.JLabel labelAnadir;
    private javax.swing.JLabel labelAnadir1;
    private javax.swing.JLabel labelAnadir2;
    private javax.swing.JLabel labelArea;
    private javax.swing.JLabel labelBorrar;
    private javax.swing.JLabel labelBorrar1;
    private javax.swing.JLabel labelBorrar2;
    private javax.swing.JLabel labelDatosProf;
    private javax.swing.JLabel labelDatosProf1;
    private javax.swing.JLabel labelModCod;
    private javax.swing.JLabel labelModCod1;
    private javax.swing.JLabel labelModCod2;
    private javax.swing.JLabel labelModDNI;
    private javax.swing.JLabel labelModDatos;
    private javax.swing.JLabel labelModDatos1;
    private javax.swing.JLabel labelModDificultad;
    private javax.swing.JLabel labelModDificultad1;
    private javax.swing.JLabel labelModFecha;
    private javax.swing.JLabel labelModFechaDis;
    private javax.swing.JLabel labelModFechaReal;
    private javax.swing.JLabel labelModNota;
    private javax.swing.JLabel labelModNota1;
    private javax.swing.JLabel labelModNumMatr;
    private javax.swing.JLabel labelModNumMatr1;
    private javax.swing.JLabel labelModPreg;
    private javax.swing.JLabel labelModTitulo;
    private javax.swing.JLabel labelModTitulo1;
    private javax.swing.JLabel labelModificar;
    private javax.swing.JLabel labelModificar1;
    private javax.swing.JLabel labelModificar2;
    private javax.swing.JLabel labelNuevaDificultad;
    private javax.swing.JLabel labelNuevaDificultad1;
    private javax.swing.JLabel labelNuevaFecha;
    private javax.swing.JLabel labelNuevaFechaDis;
    private javax.swing.JLabel labelNuevaFechaReal;
    private javax.swing.JLabel labelNuevaNota;
    private javax.swing.JLabel labelNuevaNota1;
    private javax.swing.JLabel labelNuevoCod;
    private javax.swing.JLabel labelNuevoCod1;
    private javax.swing.JLabel labelNuevoCod2;
    private javax.swing.JLabel labelNuevoDNI;
    private javax.swing.JLabel labelNuevoNumMatr;
    private javax.swing.JLabel labelNuevoNumMatr1;
    private javax.swing.JLabel labelNuevoPreg;
    private javax.swing.JLabel labelNuevoTitulo;
    private javax.swing.JLabel labelNuevoTitulo1;
    private javax.swing.JLabel labelNuevosDatos;
    private javax.swing.JLabel labelNuevosDatos1;
    private javax.swing.JTabbedPane paneles;
    private javax.swing.JTabbedPane panelesPract;
    private javax.swing.JFormattedTextField txtModCod;
    private javax.swing.JFormattedTextField txtModCod1;
    private javax.swing.JFormattedTextField txtModCod2;
    private javax.swing.JFormattedTextField txtModDNI;
    private javax.swing.JFormattedTextField txtModNota;
    private javax.swing.JFormattedTextField txtModNota1;
    private javax.swing.JFormattedTextField txtModNumMatr;
    private javax.swing.JFormattedTextField txtModNumMatr1;
    private javax.swing.JFormattedTextField txtModPreg;
    private javax.swing.JTextField txtModTitulo;
    private javax.swing.JTextField txtModTitulo1;
    private javax.swing.JFormattedTextField txtNuevaNota;
    private javax.swing.JFormattedTextField txtNuevaNota1;
    private javax.swing.JFormattedTextField txtNuevoCod;
    private javax.swing.JFormattedTextField txtNuevoCod1;
    private javax.swing.JFormattedTextField txtNuevoCod2;
    private javax.swing.JFormattedTextField txtNuevoDNI;
    private javax.swing.JFormattedTextField txtNuevoNumMatr;
    private javax.swing.JFormattedTextField txtNuevoNumMatr1;
    private javax.swing.JFormattedTextField txtNuevoPreg;
    private javax.swing.JTextField txtNuevoTitulo;
    private javax.swing.JTextField txtNuevoTitulo1;
    // End of variables declaration//GEN-END:variables
}

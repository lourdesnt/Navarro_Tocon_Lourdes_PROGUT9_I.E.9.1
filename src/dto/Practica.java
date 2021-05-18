package dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Clase Práctica
 * @author Lourdes Navarro Tocón
 */
public class Practica {
    /**
     * Atributo Código de práctica de la práctica (tipo int)
     */
    private int codPractica;
    /**
     * Atributo Título de la práctica (tipo String)
     */
    private String titulo;
    /**
     * Atributo Dificultad de la práctica (tipo Dificultad)
     */
    private Dificultad dificultad;
    /**
     * Colección de notas de los alumnos que han realizado la práctica en cada fecha
     */
    private Map<Date, Map<Integer, Double>> notasPractica;
    /**
     * Colección de profesores que han diseñado la práctica y su fecha
     */
    private Map<String, Date> profDiseno;
    
    /**
     * Constructor predeterminado
     */
    public Practica(){}

    /**
     * Constructor parametrizado que admite el código de práctica, el título y la dificultad de la práctica. Además, inicializa las colecciones
     * @param codPractica Código de práctica
     * @param titulo Título
     * @param dificultad Dificultad
     */
    public Practica(int codPractica, String titulo, Dificultad dificultad) {
        this.codPractica = codPractica;
        this.titulo = titulo;
        this.dificultad = dificultad;
        this.notasPractica = new HashMap<>();
        this.profDiseno = new HashMap<>();
    }

    //Getters y setters
    public int getCodPractica() {
        return codPractica;
    }

    public void setCodPractica(int codPractica) {
        this.codPractica = codPractica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Map<Date, Map<Integer, Double>> getNotasPractica() {
        return notasPractica;
    }

    public void setNotasPractica(Map<Date, Map<Integer, Double>> notasPractica) {
        this.notasPractica = notasPractica;
    }

    public Map<String, Date> getProfDiseno() {
        return profDiseno;
    }

    public void setProfDiseno(Map<String, Date> profDiseno) {
        this.profDiseno = profDiseno;
    }
    
    /**
     * Método para añadir fecha de la práctica, número de matrícula del alumno y nota en la colección de notas
     * @param fecha Fecha de realización de la práctica
     * @param numMatr Número de matrícula del alumno
     * @param nota Nota
     */
    public void addNota(Date fecha, int numMatr, double nota){
        Map<Integer, Double> notas = null;
        notas = notasPractica.get(fecha);
        if(notas==null){
            notas = new HashMap<>();
        }
        notas.put(numMatr, nota);
        notasPractica.put(fecha, notas);
    }

    //hashCode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.codPractica;
        hash = 71 * hash + Objects.hashCode(this.titulo);
        hash = 71 * hash + Objects.hashCode(this.dificultad);
        hash = 71 * hash + Objects.hashCode(this.notasPractica);
        hash = 71 * hash + Objects.hashCode(this.profDiseno);
        return hash;
    }

    //equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Practica other = (Practica) obj;
        if (this.codPractica != other.codPractica) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (this.dificultad != other.dificultad) {
            return false;
        }
        if (!Objects.equals(this.notasPractica, other.notasPractica)) {
            return false;
        }
        if (!Objects.equals(this.profDiseno, other.profDiseno)) {
            return false;
        }
        return true;
    }
    
}

package dto;

import java.util.Objects;

/**
 * Clase Alumno
 * @author Lourdes Navarro Tocón
 */
public class Alumno {
    /**
     * Atributo Número de matrícula del alumno (tipo int)
     */
    private int numMatricula;
    /**
     * Atributo Nombre del alumno (tipo String)
     */
    private String nombre;
    /**
     * Atributo Grupo del alumno (tipo int)
     */
    private int grupo;

    /**
     * Constructor predeterminado
     */
    public Alumno(){}
    
    /**
     * Constructor parametrizado que admite el número de matrícula, nombre y grupo del alumno
     * @param numMatricula Número de matrícula
     * @param nombre Nombre
     * @param grupo Grupo
     */
    public Alumno(int numMatricula, String nombre, int grupo) {
        this.numMatricula = numMatricula;
        this.nombre = nombre;
        this.grupo = grupo;
    }

    //Getters y setters
    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    //hashCode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.numMatricula;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + this.grupo;
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
        final Alumno other = (Alumno) obj;
        if (this.numMatricula != other.numMatricula) {
            return false;
        }
        if (this.grupo != other.grupo) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    
}

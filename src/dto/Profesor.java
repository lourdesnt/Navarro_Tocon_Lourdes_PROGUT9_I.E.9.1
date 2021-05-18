package dto;

import java.util.Objects;

/**
 * Clase Profesor
 * @author Lourdes Navarro Tocón
 */
public class Profesor {
    /**
     * Atributo DNI del profesor (tipo String)
     */
    private String dni;
    /**
     * Atributo Nombre del profesor (tipo String)
     */
    private String nombre;
    
    /**
     * Constructor predeterminado
     */
    public Profesor(){}

    /**
     * Constructor parametrizado que admite el DNI y el nombre del profesor
     * @param dni DNI
     * @param nombre Nombre
     */
    public Profesor(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    //Getters y setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //hashCode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.dni);
        hash = 23 * hash + Objects.hashCode(this.nombre);;
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
        final Profesor other = (Profesor) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    
}

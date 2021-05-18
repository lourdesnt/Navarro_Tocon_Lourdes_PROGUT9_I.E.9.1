package dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Clase Control Escrito
 * @author Lourdes Navarro Tocón
 */
public class ControlEscrito {
    /**
     * Atributo Número de control del control escrito (tipo int)
     */
    private int numControl;
    /**
     * Atributo Número de preguntas del control escrito (tipo int)
     */
    private int numPreguntas;
    /**
     * Atributo Fecha de realización del control escrito (tipo Date)
     */
    private Date fecha;
    /**
     * Colección de notas de los alumnos que han realizado el control escrito
     */
    private Map<Integer, Double> notasControl;
    
    /**
     * Constructor predeterminado
     */
    public ControlEscrito(){}

    /**
     * Constructor parametrizado que admite el número de control, el número de preguntas y la fecha de realización del control escrito
     * @param numControl Número de control
     * @param numPreguntas Número de preguntas
     * @param fecha Fecha de realización
     */
    public ControlEscrito(int numControl, int numPreguntas, Date fecha) {
        this.numControl = numControl;
        this.numPreguntas = numPreguntas;
        this.fecha = fecha;
        this.notasControl = new HashMap<>();
    }

    //Getters y setters
    public int getNumControl() {
        return numControl;
    }

    public void setNumControl(int numControl) {
        this.numControl = numControl;
    }

    public int getNumPreguntas() {
        return numPreguntas;
    }

    public void setNumPreguntas(int numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Map<Integer, Double> getNotasControl() {
        return notasControl;
    }

    public void setNotasControl(Map<Integer, Double> notasControl) {
        this.notasControl = notasControl;
    }

    //hashCode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.numControl;
        hash = 53 * hash + this.numPreguntas;
        hash = 53 * hash + Objects.hashCode(this.fecha);
        hash = 53 * hash + Objects.hashCode(this.notasControl);
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
        final ControlEscrito other = (ControlEscrito) obj;
        if (this.numControl != other.numControl) {
            return false;
        }
        if (this.numPreguntas != other.numPreguntas) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.notasControl, other.notasControl)) {
            return false;
        }
        return true;
    }
    
    
}

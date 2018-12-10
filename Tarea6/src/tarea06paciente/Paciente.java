package tarea06paciente;

import java.io.Serializable;


public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;

	private int habitacion;
    private int cama;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private  String dieta;
    
    
    //Nota: Con el modificador transient indicamos que un atributo no ha de serializarse
    // Al atributo no se le incluye el valor en el objeto serializado.
    // Cuando es deserializado el atributo es inicializado con su valor por defecto según
    // su tipo. 
    //Ejemplo: Si no quisieramos serializar dieta hariamos:
    //							private transient String dieta;

   
    public Paciente(int habitacion, int cama, String nombre, String apellido1, String apellido2, String dieta) {
        this.habitacion = habitacion;
        this.cama = cama;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dieta = dieta; 
       }
    
    public Paciente() {
    
    }
    
    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public int getCama() {
        return cama;
    }

    public void setCama(int cama) {
        this.cama = cama;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    @Override
    public String toString() 
            {
                return habitacion+"-"+cama+" , "+ nombre+ " , " + apellido1+ " , " +apellido2+ " , " +dieta;
            }
    }

    
    


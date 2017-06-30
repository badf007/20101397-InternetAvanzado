package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Leny96 on 15/6/2017.
 */
@Entity
public class Etiqueta implements Serializable {
    @Id
    private long id;
    private String nombre;

    public Etiqueta(){

    }

    public Etiqueta(long id, String etiqueta) {
        this.id = id;
        this.nombre = etiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return nombre;
    }

    public void setEtiqueta(String etiqueta) {
        this.nombre = etiqueta;
    }

}

package logica;

/**
 * Created by Leny96 on 15/6/2017.
 */
public class Etiqueta {
    private long id;
    private String nombre;

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

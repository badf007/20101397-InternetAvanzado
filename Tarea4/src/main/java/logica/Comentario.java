package logica;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Leny96 on 15/6/2017.
 */
@Entity
public class Comentario implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String comentario;

    @OneToOne
    private Usuario autor;
    @OneToOne
    private Articulo articulo;

    public Comentario(){

    }

    public Comentario(String comentario, Usuario autor, Articulo articulo) {
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

}

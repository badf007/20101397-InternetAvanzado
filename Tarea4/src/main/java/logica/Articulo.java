package logica;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Leny96 on 15/6/2017.
 */
@Entity
public class Articulo implements Serializable{

    @Id
    private long id;
    private String titulo;
    private String cuerpo;
    private String autor;
    private String fecha;
    private String cuerpo70;

    @ManyToOne
    private Set<Comentario> listaComentarios;

    @ManyToOne
    private Set<Etiqueta> listaEtiqueta;


    public void setListaComentarios(Set<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public String getCuerpo70() {
        return cuerpo70;
    }

    public void setCuerpo70(String cuerpo70) {
        this.cuerpo70 = cuerpo70;
    }

    public Articulo(String titulo, String cuerpo, String autor, String fecha, Set<Comentario> listacoment, Set<Etiqueta> listaetiquet) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = listacoment;
        this.listaEtiqueta = listaetiquet;
        this.cuerpo70 = cuerpo70;
    }

    public Set<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaEtiqueta(Set<Etiqueta> listaEtiqueta) {
        this.listaEtiqueta = listaEtiqueta;
    }

    public Set<Etiqueta> getListaEtiqueta() {
        return listaEtiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void addEtiqueta(Etiqueta et){
            listaEtiqueta.add(et);

    }

    public void addComentario(Comentario com){
        listaComentarios.add(com);
    }
}


package logica;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leny96 on 15/6/2017.
 */
@Entity
public  class Articulo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String titulo;
    private String cuerpo;
    private String fecha;
    private String cuerpo70;

    @OneToMany(targetEntity = Likes.class, fetch = FetchType.EAGER)
    private Set<Likes> listLikes;

    @ManyToOne(targetEntity = Usuario.class)
    private Usuario autor;

    @OneToMany(targetEntity = Comentario.class,mappedBy = "articulo",fetch = FetchType.EAGER)
    private Set<Comentario> listaComentarios;

    @ManyToMany(targetEntity = Etiqueta.class,fetch = FetchType.EAGER)
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

    public Articulo(String titulo, String cuerpo, Usuario autor, String fecha, Set<Comentario> listacoment, Set<Etiqueta> listaetiquet) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = listacoment;
        this.listaEtiqueta = listaetiquet;
        this.cuerpo70= "";
        this.listLikes = new HashSet<>();
    }


    public Articulo(){

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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void addComentario(Comentario com){
        listaComentarios.add(com);
    }

    public void addLikes(Likes like){
        listLikes.add(like);
    }
    public Set<Likes> getListLikes() {
        return listLikes;
    }

    public void setListLikes(Set<Likes> listLikes) {
        this.listLikes = listLikes;
    }

    public int cantLike(){
        int cant=0 ;
        for (Likes like: listLikes) {
            if(like.getOpcion().name().equalsIgnoreCase("like")){
                cant++;
            }
        }
        return cant;
    }
    public int cantUnLike(){
        int cant=0 ;
        for (Likes like: listLikes) {
            if(like.getOpcion().name().equalsIgnoreCase("unlike")){
                cant++;
            }
        }
        return cant;
    }
}


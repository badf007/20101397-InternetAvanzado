package logica;

/**
 * Created by Leny96 on 22/6/2017.
 */
public class ComentarioDB {
    private long id;
    private String comentario;
    private String autor;
    private long articulo;

    public ComentarioDB(String comentario, String autor, long articulo) {
        this.id = id;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public long getArticulo() {
        return articulo;
    }

    public void setArticulo(long articulo) {
        this.articulo = articulo;
    }
}

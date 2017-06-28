package Dao;

import logica.Articulo;
import logica.Comentario;
import logica.ComentarioDB;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by No. GP62 on 16/06/2017.
 */
public class ComenterioDao {

    private Sql2o sql2o;

    public ComenterioDao() {
        //subiendola en modo Embedded
        this.sql2o = new Sql2o("jdbc:h2:~/blog", "root", "");
        crearTabla();
        cargaDemo();
    }

    /**
     *
     */
    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS COMENTARIOS" +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "COMENTARIO VARCHAR(1500) NOT NULL," +
                "AUTOR VARCHAR(30) NOT NULL, FOREIGN KEY (AUTOR) REFERENCES USER(USERNAME)," +
                "ARTICULO INTEGER NOT NULL,FOREIGN KEY (ARTICULO) REFERENCES ARTICULOS(ID) "+");";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }

    private void cargaDemo() {
        System.out.println("Cargando el demo COMENTARIO...");
        if (getAllComentarios().isEmpty()) {
            crearDataDemo();
        }
    }

    public List<ComentarioDB> getAllComentarios() {
        String sql = "select * from COMENTARIOS ";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ComentarioDB.class);
        }
    }

    public List<ComentarioDB> getComentarioByArt(long art_id) {
        String sql = "select * from COMENTARIOS where ARTICULO='"+art_id+"'";
        try (Connection con = sql2o.open()) {
             return con.createQuery(sql).executeAndFetch(ComentarioDB.class);
        }
    }

    public void crearDataDemo() {
        String sql = "insert into COMENTARIOS(COMENTARIO,AUTOR,ARTICULO) values(:COMENTARIO, :AUTOR, :ARTICULO)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)
                    .addParameter("COMENTARIO", "BUEN APORTE")
                    .addParameter("AUTOR", "lenyluna")
                    .addParameter("ARTICULO", 1)
                    .executeUpdate();

            con.createQuery(sql)
                    .addParameter("COMENTARIO", "Interesante")
                    .addParameter("AUTOR", "zomgod")
                    .addParameter("ARTICULO", 2)
                    .executeUpdate();
        }
    }

    public void createData(Comentario coment) {
        String sql = "insert into COMENTARIOS(COMENTARIO,AUTOR,ARTICULO) values(:COMENTARIO, :AUTOR, :ARTICULO)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)
                    .addParameter("COMENTARIO", coment.getComentario())
                    .addParameter("AUTOR", coment.getAutor().getUsername())
                    .addParameter("ARTICULO", coment.getArticulo().getId())
                    .executeUpdate();

        }
    }

    public void removeComent(long id){
        String sql = "DELETE FROM COMENTARIOS WHERE ARTICULO='"+id+"'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }
}

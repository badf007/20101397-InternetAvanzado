package Dao;

import logica.Articulo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by No. GP62 on 16/06/2017.
 */
public class ArticuloDao {

    private Sql2o sql2o;

    public ArticuloDao() {
        //subiendola en modo Embedded

            this.sql2o = new Sql2o("jdbc:h2:~/blog", "root", "");
            crearTabla();
            cargaDemo();


          //  System.out.println("ERROR AL CONECTAR");
           // e.printStackTrace();


    }

    /**
     *
     */
    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS ARTICULOS" +
                "(ID INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL," +
                "TITULO VARCHAR(100) NOT NULL," +
                "CUERPO VARCHAR(2500) NOT NULL," +
                "AUTOR VARCHAR(30) NOT NULL,FOREIGN KEY (AUTOR) REFERENCES USER(USERNAME)," +
                "FECHA VARCHAR(30) NOT NULL" + ");";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }

    private void cargaDemo() {
        System.out.println("Cargando el demo ARTICULOS...");
        if (getAllArticulos().isEmpty()) {
            crearDataDemo();
        }
    }

    public List<Articulo> getAllArticulos() {
        String sql = "select * from ARTICULOS";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Articulo.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void createArticulo(Articulo art){
        String sql = "INSERT INTO ARTICULOS(TITULO,CUERPO,AUTOR,FECHA) VALUES(:TITULO,:CUERPO,:AUTOR,:FECHA)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                   // .addParameter("ID",)
                    .addParameter("TITULO",art.getTitulo())
                    .addParameter("CUERPO",art.getCuerpo())
                    .addParameter("AUTOR",art.getAutor() )
                    .addParameter("FECHA",art.getFecha())
                    .executeUpdate();
        }
    }

    public void crearDataDemo() {
        String sql = "insert into ARTICULOS (TITULO, CUERPO, AUTOR, FECHA) values(:TITULO, :CUERPO, :AUTOR, :FECHA)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)
                    .addParameter("TITULO", "PRIMER POST")
                    .addParameter("CUERPO", "ESTE ES EL PRIMER POST DEL BLOG")
                    .addParameter("AUTOR", "zomgod")
                    .addParameter("FECHA", "16/06/2017")
                    .executeUpdate();

            con.createQuery(sql)
                    .addParameter("TITULO", "SEGUNDO POST")
                    .addParameter("CUERPO", "ESTE ES EL SEGUNDO POST DEL BLOG")
                    .addParameter("AUTOR", "lenyluna")
                    .addParameter("FECHA", "16/06/2017")
                    .executeUpdate();
        }
    }
    public long lastArt(){
        long id=getAllArticulos().get(getAllArticulos().size()-1).getId();
        return id;
    }

    public void removeArticulo(long id){
        String sql = "DELETE FROM ARTICULOS WHERE ID='"+id+"'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }
}

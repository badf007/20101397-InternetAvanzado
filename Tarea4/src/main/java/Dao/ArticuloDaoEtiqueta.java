package Dao;

import logica.Articulo;
import logica.Etiqueta;
import logica.RelacionEti_Art;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leny96 on 20/6/2017.
 */
public class ArticuloDaoEtiqueta{
    private Sql2o sql2o;

    public ArticuloDaoEtiqueta(){
        this.sql2o = new Sql2o("jdbc:h2:~/blog", "root", "");
        crearTabla();
        cargaDemo();
       // guardaRelacion ();
    }


    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS ETIQUETA_ART"+
                "(ID INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL," +
                "ID_ART INTEGER NOT NULL, FOREIGN KEY (ID_ART) REFERENCES ARTICULOS(ID),"+
                "ID_ETI INTEGER NOT NULL, FOREIGN KEY (ID_ETI) REFERENCES ETIQUETAS(ID)"+");";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }

    public void cargaDemo() {
        System.out.println("Cargando el demo de relacion de etiqueta y articulo...");
        if (getAllRelacionEti_Art().isEmpty()) {
            crearDataDemo();
        }
    }

    private void crearDataDemo() {
        String sql = "INSERT INTO ETIQUETA_ART(ID_ART,ID_ETI) VALUES(:ID_ART,:ID_ETI)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)
                    .addParameter("ID_ART", 1)
                    .addParameter("ID_ETI", 1)
                    .executeUpdate();

            con.createQuery(sql)
                    .addParameter("ID_ART", 2)
                    .addParameter("ID_ETI", 2)
                    .executeUpdate();
        }
    }
    public List<RelacionEti_Art> getAllRelacionEti_Art(){
        String sql = "select * from ETIQUETA_ART";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(RelacionEti_Art.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RelacionEti_Art> getRelacionByEti(long id){
        String sql = "select * from ETIQUETA_ART where ID_ETI='"+id+"'";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(RelacionEti_Art.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void createRelacion(long idEti, long idArt){
        String sql ="INSERT INTO ETIQUETA_ART(ID_ART,ID_ETI) VALUES (:ID_ART,:ID_ETI)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("ID_ETI",idEti)
                    .addParameter("ID_ART",idArt)
                    .executeUpdate();
        }
    }

    public void removeRelacion(long id){
        String sql = "DELETE FROM ETIQUETA_ART WHERE ID_ART='"+id+"'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }
}

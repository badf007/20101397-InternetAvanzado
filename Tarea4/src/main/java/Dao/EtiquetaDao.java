package Dao;

import logica.Articulo;
import logica.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by No. GP62 on 16/06/2017.
 */
public class EtiquetaDao {

    private Sql2o sql2o;

    public EtiquetaDao() {
        //subiendola en modo Embedded
        this.sql2o = new Sql2o("jdbc:h2:~/blog", "root", "");
        crearTabla();
        cargaDemo();
    }

    /**
     *
     */
    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS ETIQUETAS"+
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,"+
                "NOMBRE VARCHAR(100) NOT NULL"+");";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }

    private void cargaDemo() {
        System.out.println("Cargando el demo ETIQUETA...");
        if (getAllEtiquetas().isEmpty()) {
            crearDataDemo();
        }
    }

    public List<Etiqueta> getAllEtiquetas() {
        String sql = "select * from ETIQUETAS";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Etiqueta.class);
        }
    }

    public void crearDataDemo() {
        String sql = "insert into ETIQUETAS (NOMBRE) values(:name)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)
                    .addParameter("name", "TECNOLOGIA")
                    .executeUpdate();

            con.createQuery(sql)
                    .addParameter("name", "GENERAL")
                    .executeUpdate();
        }
    }
    public  void crearEtiqueta(String name){
        String sql = "insert into ETIQUETAS (NOMBRE) values(:name)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name",name)
                    .executeUpdate();
        }
    }

    public long lastEtiq(){
        long id=getAllEtiquetas().get(getAllEtiquetas().size()-1).getId();
        return id;
    }

    public void removeEtiqueta(long id){
        String sql = "DELETE FROM ETIQUETAS WHERE ID='"+id+"'";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }


}

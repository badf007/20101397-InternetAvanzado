package Dao;

import logica.Articulo;
import logica.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by No. GP62 on 16/06/2017.
 */
public class UsuarioDao {

    private Sql2o sql2o;

    public UsuarioDao() {
        //subiendola en modo Embedded
        this.sql2o = new Sql2o("jdbc:h2:~/blog", "root", "");
        crearTabla();
        cargaDemo();
    }

    /**
     *
     */
    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS USER\n" +
                "(USERNAME VARCHAR(30) PRIMARY KEY NOT NULL,\n" +
                " NOMBRE VARCHAR(100) NOT NULL,\n" +
                " PASSWORD VARCHAR(100) NOT NULL,\n" +
                " ADMINISTRADOR BOOLEAN NOT NULL,\n" +
                "AUTOR BOOLEAN NOT NULL" + ");";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }

    private void cargaDemo() {
        System.out.println("Cargando el demo USER...");
        if (getAllUsuarios().isEmpty()) {
            crearDataDemo();
        }
    }

    public List<Usuario> getAllUsuarios() {
        String sql = "select * from USER";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public void createUsuario(Usuario user) {

        String sql = "INSERT INTO USER(USERNAME, NOMBRE, PASSWORD,ADMINISTRADOR, AUTOR)"
                + "VALUES(:USERNAME,:NOMBRE,:PASSWORD,:ADMINISTRADOR,:AUTOR);";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("USERNAME", user.getUsername())
                    .addParameter("NOMBRE", user.getNombre())
                    .addParameter("PASSWORD", user.getPassword())
                    .addParameter("ADMINISTRADOR", user.isAdministrador())
                    .addParameter("AUTOR", user.isAutor())
                    .executeUpdate();
        }
    }


    public void crearDataDemo() {
        // String sql = "insert into estudiante (matricula, nombre) values(:matricula,:nombre)";
        String sql = "INSERT INTO USER(USERNAME, NOMBRE, PASSWORD,ADMINISTRADOR, AUTOR)"
                + "VALUES(:USERNAME,:NOMBRE,:PASSWORD,:ADMINISTRADOR,:AUTOR);";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("USERNAME", "lenyluna")
                    .addParameter("NOMBRE", "administrador")
                    .addParameter("PASSWORD", "admin")
                    .addParameter("ADMINISTRADOR", "TRUE")
                    .addParameter("AUTOR", "TRUE")
                    .executeUpdate();

            con.createQuery(sql)
                    .addParameter("USERNAME", "zomgod")
                    .addParameter("NOMBRE", "ADMINISTRATOR")
                    .addParameter("PASSWORD", "root")
                    .addParameter("ADMINISTRADOR", "TRUE")
                    .addParameter("AUTOR", "TRUE")
                    .executeUpdate();

        }
    }
}

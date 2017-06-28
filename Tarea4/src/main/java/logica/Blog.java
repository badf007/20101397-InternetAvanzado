package logica;

import java.util.ArrayList;

/**
 * Created by Leny96 on 15/6/2017.
 */
public class Blog {
    private static Blog ourInstance = new Blog();
    private ArrayList<Articulo> listaArticulos;
    private ArrayList<Usuario> listaUsuarios;

    public static Blog getInstance() {
        return ourInstance;
    }

    private Blog() {
        this.listaArticulos = new ArrayList<Articulo>();
        this.listaUsuarios = new ArrayList<Usuario>();
    }

    public ArrayList<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}

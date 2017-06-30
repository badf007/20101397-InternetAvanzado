package services;

import logica.GestionDB;
import logica.Usuario;

/**
 * Created by Leny96 on 29/6/2017.
 */
public class UsuarioServices extends GestionDB<Usuario> {

    private static UsuarioServices instancia;
    public UsuarioServices() {
        super(Usuario.class);
    }

    public UsuarioServices getInstancia(){
        if(instancia==null){
            instancia= new UsuarioServices();
        }
        return instancia;
    }

    public void cargarDemo(){
        crearEntidad(new Usuario("lenyluna","leny","admin", true,true));
        crearEntidad(new Usuario("zomgod","starling","root", true,true));
    }
}

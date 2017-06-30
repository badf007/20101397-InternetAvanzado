package services;

import logica.Articulo;
import logica.Comentario;
import logica.Etiqueta;
import logica.GestionDB;

import java.util.Set;

/**
 * Created by Leny96 on 29/6/2017.
 */
public class ArticulosServices extends GestionDB<Articulo> {

    private static ArticulosServices instancia;

    public ArticulosServices() {
        super(Articulo.class);
    }

    public static ArticulosServices getInstancia(){
        if(instancia == null){
            instancia = new ArticulosServices();
        }
        return instancia;
    }
    public void cargarDemo(){
        crearEntidad(new Articulo("Primer Post","Este es elprimer post del blog", findAllByUser("lenyluna"),"16/06/2017", new Set<Comentario> , new Set<EtiquetaServices> ));
    }
}

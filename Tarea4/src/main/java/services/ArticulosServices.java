package services;

import logica.Articulo;
import logica.Comentario;
import logica.Etiqueta;
import logica.GestionDB;

import java.util.*;

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
       Set<Etiqueta> listEtiqueta  = new HashSet<>();
        listEtiqueta.add(EtiquetaServices.getInstancia().find((long)25));
        instancia.crearEntidad(new Articulo("Primer Post","Este es el primer post del blog","", findAllByUser("lenyluna"),"16/06/2017", new HashSet<Comentario>() , listEtiqueta));
        //instancia.crearEntidad(new Articulo("Segundo Post","Este es el segundo post del blog", "",findAllByUser("zomgod"),"16/06/2017", new HashSet<Comentario>(), new HashSet<Etiqueta>() ));
    }
}

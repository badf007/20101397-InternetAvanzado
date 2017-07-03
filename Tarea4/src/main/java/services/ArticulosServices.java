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
        listEtiqueta.add(EtiquetaServices.getInstancia().find((long)1));
        Set<Etiqueta> listEtiqueta2  = new HashSet<>();
        listEtiqueta2.add(EtiquetaServices.getInstancia().find((long)2));
        Articulo art1 = new Articulo("Primer Post","Este es el primer post del blog", findAllByUser("lenyluna"),"16/06/2017",new HashSet<Comentario>(), listEtiqueta);
        art1.setCuerpo70("Este es el primer post del blog");
        Articulo art2 =new Articulo("Segundo Post","Este es el segundo post del blog",findAllByUser("zomgod"),"16/06/2017", new HashSet<Comentario>(), listEtiqueta2);
        art2.setCuerpo70("Este es el segundo post del blog");
        instancia.crearEntidad(art1);
        instancia.crearEntidad(art2);
    }
}

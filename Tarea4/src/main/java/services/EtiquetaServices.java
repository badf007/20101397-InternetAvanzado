package services;

import logica.Etiqueta;
import logica.GestionDB;

/**
 * Created by Leny96 on 29/6/2017.
 */
public class EtiquetaServices extends GestionDB<Etiqueta> {

   private static EtiquetaServices instancia;

    public EtiquetaServices() {
        super(Etiqueta.class);
    }

    public EtiquetaServices getInstancia(){
        if(instancia==null){
            instancia = new EtiquetaServices();
        }
        return instancia;
    }
}

package services;

import logica.GestionDB;
import logica.RelacionEti_Art;

/**
 * Created by Leny96 on 29/6/2017.
 */
public class RelacionServices extends GestionDB<RelacionEti_Art> {
    private static RelacionServices instancia;

    public RelacionServices() {
        super(RelacionEti_Art.class);
    }

    public static RelacionServices getInstancia(){
        if(instancia == null ){
            instancia = new RelacionServices();
        }
        return instancia;
    }
}

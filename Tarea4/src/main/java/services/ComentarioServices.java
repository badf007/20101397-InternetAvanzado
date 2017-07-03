package services;

import logica.Comentario;
import logica.GestionDB;

/**
 * Created by Leny96 on 29/6/2017.
 */
public class ComentarioServices extends GestionDB<Comentario> {
    private static ComentarioServices instancia;

    public ComentarioServices() {
        super(Comentario.class);
    }

    public static ComentarioServices getInstancia(){
        if(instancia == null){
            instancia = new ComentarioServices();
        }
        return instancia;
    }

}

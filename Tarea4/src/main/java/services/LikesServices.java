package services;

import logica.Etiqueta;
import logica.GestionDB;
import logica.Likes;

/**
 * Created by Leny96 on 15/7/2017.
 */
public class LikesServices  extends GestionDB<Likes> {

    private static LikesServices instancia;
    public LikesServices() {
        super(Likes.class);
    }
    public static LikesServices getInstancia(){
        if(instancia==null){
            instancia = new LikesServices();
        }
        return instancia;
    }

}

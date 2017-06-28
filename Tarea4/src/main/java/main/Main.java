package main;

import Dao.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import logica.*;
import spark.Request;
import spark.Spark;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by Leny96 on 15/6/2017.
 */
public class Main {
    private static final String COOKIE_NAME = "user_cookies" ;
    private static String SESSION_NAME= "username";
    static UsuarioDao DBusuarios = new UsuarioDao();
    static EtiquetaDao DBetiqueta = new EtiquetaDao();
    static ArticuloDao DBarticulos = new ArticuloDao();
    static ComenterioDao DBcomentario = new ComenterioDao();
    static ArticuloDaoEtiqueta DBartEti = new ArticuloDaoEtiqueta();
    static ArrayList<Articulo> listArticulos = new ArrayList<>();
    //http://localhost:4567


    public static void main(String[] args) {

        staticFileLocation("/publico");
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        List<Articulo> allArticulos = DBarticulos.getAllArticulos();
        loadRelacion(allArticulos);

        Spark.before("/guardandoarticulo",(request, response) -> {
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            if(user==null){
                response.redirect("/");

            }
        });

        Spark.before("/CrearArticulo/",(request, response) -> {
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            if(user==null){
                response.redirect("/");
            }
        });

        Spark.before("/articulo/:id/comentario",(request, response) -> {
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            if(user==null){
                response.redirect("/articulo/"+Long.parseLong(request.params("id")));
            }
        });

        Spark.before("/signup/",(request, response) -> {
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            if(!user.isAdministrador()){
                System.out.println(user.isAdministrador());
                halt(401, "No tiene permisos para realizar esta operacion! <a href='http://localhost:4567' >Volver al inicio </a>");
                response.redirect("/");
            }
        });


        Spark.get("/", (request, response) -> {
            checkCOOKIES(request);
            StringWriter writer = new StringWriter();
            try {
                Template formTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                ArrayList<Articulo> listArtClone = (ArrayList<Articulo>) listArticulos.clone();
                Collections.reverse(listArtClone);
                map.put("ListaArticulos",listArtClone);

                Usuario user = finUser(request.session().attribute(SESSION_NAME));
                if(user==null){
                    map.put("login", "false");
                }else {
                    map.put("login", "true");
                    map.put("username",user.getUsername());
                }
                formTemplate.process(map, writer);
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
                halt(500);
            }
            return writer;
        });

        Spark.post("/login/:ubicar", (request, response) -> {
            StringWriter writer = new StringWriter();
            List<Usuario> allUsuarios = DBusuarios.getAllUsuarios();
          //  List<Articulo> allArticulos = DBarticulos.getAllArticulos();
            try {
                String username = request.queryParams("username") != null ? request.queryParams("username") : "anonymous";
                String password = request.queryParams("password") != null ? request.queryParams("password") : "unknown";

                List<Usuario> result = allUsuarios.stream()
                        .filter(item -> item.getUsername().equals(username))
                        .filter(item -> item.getPassword().equals(password))
                        //  .filter(a -> Objects.equals(a.ge, "three"))
                        .collect(Collectors.toList());

                if (result.isEmpty()) {
                    System.out.println("NINGUN USUARIO CON ESA COBINACION DE PARAMETROS ");
                   Template formTemplate = configuration.getTemplate("templates/ventanaLogin.ftl");
                    Map<String, Object> map = new HashMap<>();
                    map.put("ListaArticulos", listArticulos);
                    map.put("login", "false");
                    map.put("cargando", "true");
                    map.put("username",username);
                    if(Long.parseLong(request.params("ubicar"))!=-1){
                        response.redirect("/articulo/"+Long.parseLong(request.params("ubicar")));
                    }
                    formTemplate.process(map, writer);

                } else {
                    System.out.println("LOGEADO CON EXITO");
                    response.cookie(COOKIE_NAME,username,3600);
                    request.session().attribute(SESSION_NAME,username);
                    if(Long.parseLong(request.params("ubicar"))!=-1){
                        response.redirect("/articulo/"+Long.parseLong(request.params("ubicar")));
                    }else {
                        response.redirect("/");
                    }

                }

            } catch (Exception e) {
                response.redirect("/");
            }
            return writer;
        });

        Spark.post("/signup/", (request, response) -> {
            StringWriter writer = new StringWriter();
            boolean adm = false;
            boolean aut = false;
            //List<Usuario> allUsuarios = DBusuarios.getAllUsuarios();
            try {
                String username = request.queryParams("username") != null ? request.queryParams("username") : "anonymous";
                String password = request.queryParams("password") != null ? request.queryParams("password") : "unknown";
                String nombre = request.queryParams("nombre") != null ? request.queryParams("nombre") : "unknown";
                String administrador = request.queryParams("administrador") != null ? request.queryParams("administrador") : "false";
                String autor = request.queryParams("autor") != null ? request.queryParams("autor") : "false";

                if (administrador.equals("on")) {
                    adm = true;
                }
                if (autor.equals("on")) {
                    aut = true;
                }

                DBusuarios.createUsuario(new Usuario(username, nombre, password, adm, aut));
                System.out.println(administrador + " " + autor + " " + nombre + " " + username + " " + password);
                response.redirect("/");


            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/");
            }
            return writer;
        });

        Spark.get("/logout", (request, response) -> {
             request.session().removeAttribute(SESSION_NAME);
            response.redirect("/");
            return null;
        });

        Spark.get("/CrearArticulo/",(request, response) -> {
            StringWriter writer = new StringWriter();
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            Template formTemplate = configuration.getTemplate("templates/crearArticulo.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("username",user.getUsername());
            map.put("login", "true");
            formTemplate.process(map, writer);
            return writer;
        });

        Spark.post("/guardandoarticulo",(request, response) -> {
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            Articulo art = new Articulo(titulo, cuerpo, user.getUsername(),format.format(date).toString());
            String etiqueta[] = request.queryParams("etiqueta").split(",");

            if(!existe_articulo(titulo)) {


                for (int i = 0; i < etiqueta.length; i++) {
                    Etiqueta et = findEtiqueta(etiqueta[i]);
                    if (et == null) {
                        Etiqueta et2 = new Etiqueta(-1, etiqueta[i]);
                        try {
                            DBetiqueta.crearEtiqueta(et2.getEtiqueta());
                            et2.setId(DBetiqueta.lastEtiq());
                            art.addEtiqueta(et2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        art.addEtiqueta(et);
                    }
                }
                try {
                    DBarticulos.createArticulo(art);
                    art.setId(DBarticulos.lastArt());
                    for (Etiqueta et : art.getListaEtiqueta()) {
                        DBartEti.createRelacion(et.getId(), art.getId());
                    }
                    loadRelacionByOne(art);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            response.redirect("/");
            return null;
        });

        Spark.get("/articulo/:id",(request, response) -> {
            StringWriter writer = new StringWriter();
            long id = Long.parseLong(request.params("id"));
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            Articulo art = findArtById(id);
            try{
                Template formTemplate = configuration.getTemplate("templates/vistaArticulo.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("articulo",art);
            map.put("listEti",art.getListaEtiqueta());
            map.put("listComent",art.getListaComentarios());
                if(user==null){
                    map.put("login", "false");
                }else {
                    map.put("login", "true");
                    map.put("username",user.getUsername());
                }
            formTemplate.process(map, writer);
            }   catch (Exception e) {
                e.printStackTrace();
                response.redirect("/");
            }
            return writer;
        });

        Spark.post("/articulo/:id/comentario",(request, response) ->{
            StringWriter writer = new StringWriter();
            String comentario = request.queryParams("comentario");
            long id = Long.parseLong(request.params("id"));
            Articulo art =findArtById (id);
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            if(user!=null) {
                Comentario com = new Comentario(comentario, user, art);
                art.addComentario(com);
                try {
                    DBcomentario.createData(com);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.redirect("/articulo/"+id);
            return null;
        });

        Spark.get("/articulo/:id/EliminarArt",(request, response) ->{
            long id = Long.parseLong(request.params("id"));
            Articulo art =findArtById (id);
            try {
                DBcomentario.removeComent(art.getId());
                DBartEti.removeRelacion(art.getId());
                for (Etiqueta eti : art.getListaEtiqueta()) {
                  if(DBartEti.getRelacionByEti(eti.getId()).size()==0){
                   DBetiqueta.removeEtiqueta(eti.getId());
                  }
                }
                DBarticulos.removeArticulo(art.getId());
                listArticulos.remove(art);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.redirect("/");
            return null;
        });

    }

    private static void checkCOOKIES(Request req) {
        if (req.session().attribute(SESSION_NAME) == null) {
            Map<String, String> cookies = req.cookies();
            if (cookies.containsKey(COOKIE_NAME)) {
                System.out.println("que lo que con que lo que ");
                System.out.println("COOKIE ENCONTRADA" + cookies.get(COOKIE_NAME));
                req.session().attribute(SESSION_NAME, cookies.get(COOKIE_NAME));
            }
        }
    }

    private static Usuario finUser(String username){
        List<Usuario> allUsuarios = DBusuarios.getAllUsuarios();
        for(Usuario user: allUsuarios){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    private static Etiqueta findEtiqueta(String name){
        for (Etiqueta et: DBetiqueta.getAllEtiquetas()) {
            if(et.getEtiqueta().equalsIgnoreCase(name)){
                return et;
            }
        }
       return null;
    }

    private static Etiqueta findEtiById (long id){
        for (Etiqueta et: DBetiqueta.getAllEtiquetas()) {
            if(et.getId()==id){
                return et;
            }
        }
        return null;
    }

    private  static void loadRelacion (List<Articulo> allArticulos){
        List<RelacionEti_Art> allRelacion = DBartEti.getAllRelacionEti_Art();
        for (Articulo art: allArticulos) {
            art.setListaEtiqueta(new ArrayList<Etiqueta>());
            art.setListaComentarios(new ArrayList<Comentario>());
            for (RelacionEti_Art rel : allRelacion) {
                if(rel.getId_Art()==art.getId()){
                    art.getListaEtiqueta().add(findEtiById(rel.getId_Eti()));
                }
            }
            art.setCuerpo70(caracter(art.getCuerpo()));
            loadComentario(art);
            listArticulos.add(art);
        }
    }

    private static void loadComentario(Articulo art){
      List<ComentarioDB> listComent = DBcomentario.getComentarioByArt(art.getId());
        ArrayList<Comentario> allListComent = new ArrayList<>();
        for (ComentarioDB comentDb: listComent) {
            Comentario comen = new Comentario(comentDb.getComentario(),finUser(comentDb.getAutor()),art);
            allListComent.add(comen);
        }
      for (Comentario coment: allListComent) {
            art.addComentario(coment);
        }
    }

    private  static void loadRelacionByOne (Articulo art){
        List<RelacionEti_Art> allRelacion = DBartEti.getAllRelacionEti_Art();
            art.setListaEtiqueta(new ArrayList<Etiqueta>());
            for (RelacionEti_Art rel : allRelacion) {
                if(rel.getId_Art()==art.getId()){
                    art.getListaEtiqueta().add(findEtiById(rel.getId_Eti()));
                }
            }
            art.setCuerpo70(caracter(art.getCuerpo()));
            loadComentario(art);
            listArticulos.add(art);
    }

    public static String caracter(String cuerpo){
        String caracter70 = "";
        if(cuerpo.length()<=70){
           caracter70=cuerpo;
        }else {
            for (int i = 0; i < 70; i++) {
                caracter70 += cuerpo.charAt(i);
            }
        }
        return caracter70;
    }

    private static Articulo findArtById (long id){
        for (Articulo art: listArticulos) {
            if(art.getId()==id){
                return art;
            }
        }
        return null;
    }

    private static boolean existe_articulo (String titulo){

        for (Articulo art: listArticulos) {
            if(art.getTitulo()== titulo){
                return true;
            }
        }
        return false;
    }




}

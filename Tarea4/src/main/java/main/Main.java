package main;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import logica.*;
import services.*;
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
    //http://localhost:4567



    public static void main(String[] args) {
        ConfigDB.getInstancia().startDb();
        staticFileLocation("/publico");
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        loadDemo();
        List<Articulo> allArticulos = ArticulosServices.getInstancia().findAll();

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
                List<Articulo> listArtClone = ArticulosServices.getInstancia().findAll(); // (ArrayList<Articulo>) listArticulos.clone();
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
            List<Usuario> allUsuarios = UsuarioServices.getInstancia().findAll();
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
                    map.put("ListaArticulos", ArticulosServices.getInstancia().findAll());
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
                UsuarioServices.getInstancia().crearEntidad(new Usuario(username, nombre, password, adm, aut));
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
            // request.session().removeAttribute(SESSION_LIKES);
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
           map.put("modificar","false");
            formTemplate.process(map, writer);
            return writer;
        });

        Spark.post("/guardandoarticulo",(request, response) -> {
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            String etiqueta[] = request.queryParams("etiqueta").split(",");
            Set<Etiqueta> listEtiqueta  = new HashSet<>();
            for(int i=0; i<etiqueta.length; i++){
                Etiqueta et = findEtiqueta(etiqueta[i]);
                if (et == null) {
                    Etiqueta et2 = new Etiqueta(etiqueta[i]);
                    EtiquetaServices.getInstancia().crearEntidad(et2);
                    listEtiqueta.add(et2);
                } else {
                    listEtiqueta.add(et);
                }
            }
            for (Etiqueta et: listEtiqueta) {
                EtiquetaServices.getInstancia().crearEntidad(et);
            }
            if(!existe_articulo(titulo)) {
                Articulo art = new Articulo(titulo, cuerpo, user, format.format(date).toString(), new HashSet<Comentario>(), listEtiqueta);
                art.setCuerpo70(caracter(cuerpo));
                ArticulosServices.getInstancia().crearEntidad(art);
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
            map.put("cantLikes",art.cantLike());
            map.put("cantUnlikes",art.cantUnLike());

                if(user==null){
                    map.put("login", "false");
                }else {
                    map.put("login", "true");
                    map.put("username",user.getUsername());
                    map.put("like",veriLike(art,user));
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
                ComentarioServices.getInstancia().crearEntidad(com);
                ArticulosServices.getInstancia().editar(art);
            }
            response.redirect("/articulo/"+id);
            return null;
        });
        Spark.get("/articulo/:id/EliminarArt",(request, response) ->{
            long id = Long.parseLong(request.params("id"));
            Articulo art =findArtById (id);
            deleteComen(art);
            ArrayList<Etiqueta> arrayList =new ArrayList<>();
            for(Etiqueta et : art.getListaEtiqueta()){
                arrayList.add(et);
            }
            art.getListaEtiqueta().removeAll(art.getListaEtiqueta());
            art.getListaComentarios().removeAll(art.getListaComentarios());
            ArticulosServices.getInstancia().eliminar(id);
            veryDeleteEtique(arrayList);
            response.redirect("/");
            return null;
        });

        Spark.get("/articulo/:id/modificar",(request,response)->{
            StringWriter writer = new StringWriter();
            long id = Long.parseLong(request.params("id"));
            Articulo art =ArticulosServices.getInstancia().find(id);
            Usuario user = finUser(request.session().attribute(SESSION_NAME));
            Template formTemplate = configuration.getTemplate("templates/crearArticulo.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("username",user.getUsername());
            map.put("login", "true");
            map.put("modificar","true");
            map.put("articulo",art);
            map.put("etiquetas",addEtiquetas(art));
            formTemplate.process(map, writer);
            return writer;
        });

        Spark.get("/guardandoCambios/:id",(request,response)->{
            long id = Long.parseLong(request.params("id"));
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            Articulo art = ArticulosServices.getInstancia().find(id);
            art.setCuerpo(cuerpo);
            art.setTitulo(titulo);
            String etiqueta[] = request.queryParams("etiqueta").split(",");
            for(int i=0; i<etiqueta.length; i++) {
                if (!existeEti(art, etiqueta[i])) {
                    Etiqueta et = findEtiqueta(etiqueta[i]);
                    if (et == null ) {
                        Etiqueta et2 = new Etiqueta(etiqueta[i]);
                        EtiquetaServices.getInstancia().crearEntidad(et2);
                        art.getListaEtiqueta().add(et2);
                    }else{
                        art.getListaEtiqueta().add(et);
                    }
                }
            }
            ArticulosServices.getInstancia().editar(art);
            response.redirect("/articulo/"+id);
            return null;
        });

        Spark.get("/articulo/:id/:likes",(request,response )->{
            long id = Long.parseLong(request.params("id"));
            String opcion = request.params("likes");
            Articulo art =findArtById (id);
            Usuario user= finUser(request.session().attribute(SESSION_NAME));
            if(user != null){
                if(opcion.equalsIgnoreCase("like")){
                    Likes like = new Likes(user,Typeline.like);
                    LikesServices.getInstancia().crearEntidad(like);
                    art.addLikes(like);
                    ArticulosServices.getInstancia().editar(art);
                }else {
                    Likes like = new Likes(user,Typeline.unlike);
                    LikesServices.getInstancia().crearEntidad(like);
                    art.addLikes(like);
                    ArticulosServices.getInstancia().editar(art);
                }

            }
                response.redirect("/articulo/"+id);

            return null;
        });

        Spark.get("/findEtiqueta/:idEti",(request,response )->{
            long idEti = Long.parseLong(request.params("idEti"));
           ArrayList<Articulo> listFiltro = new ArrayList<Articulo>();
            List<Articulo> list = ArticulosServices.getInstancia().findAll();
            for (Articulo art : list) {
                for (Etiqueta eti: art.getListaEtiqueta()) {
                    if(eti.getId()==idEti){
                        listFiltro.add(art);
                    }

                }
            }
            StringWriter writer = new StringWriter();
            try {
                Template formTemplate = configuration.getTemplate("templates/listEtiqueta.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("ListaArticulos",listFiltro);
                map.put("Etiqueta",EtiquetaServices.getInstancia().find(idEti).getEtiqueta());
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


    }

    private static void loadDemo() {
     if(UsuarioServices.getInstancia().findAll().size() == 0){
            UsuarioServices.getInstancia().cargarDemo();
        }

        if(EtiquetaServices.getInstancia().findAll().size()==0){
            EtiquetaServices.getInstancia().cargarDemo();
        }

        if(ArticulosServices.getInstancia().findAll().size()==0){
            ArticulosServices.getInstancia().cargarDemo();
        }
    }

    private static void veryDeleteEtique(ArrayList<Etiqueta> listEt){
        ArrayList<Etiqueta> listId = new ArrayList<Etiqueta>();
        for(Etiqueta et2 : listEt) {
            for (Articulo art : ArticulosServices.getInstancia().findAll()) {
                for (Etiqueta et : art.getListaEtiqueta()) {
                    if(et2!=null) {
                        if (et2.getEtiqueta().equalsIgnoreCase(et.getEtiqueta())) {
                            listId.add(et2);
                            //listEt.remove(et2);
                        }
                    }
                }
            }
        }
        for(int i=0;i<listId.size();i++){
            listEt.remove(listId.get(i));
        }
        if(listEt.size()!=0){
            for(Etiqueta et1: listEt){
                if(listEt.size()!=0) {
                    EtiquetaServices.getInstancia().eliminar(et1.getId());
                }
            }
        }
    }

    private static void deleteComen(Articulo art ){
        for (Comentario com : art.getListaComentarios()) {
            System.out.println("aasas"+com.getAutor());
            ComentarioServices.getInstancia().eliminar(com.getId());
        }
    }
    private static void checkCOOKIES(Request req) {
        if (req.session().attribute(SESSION_NAME) == null) {
            Map<String, String> cookies = req.cookies();
            if (cookies.containsKey(COOKIE_NAME)) {
                System.out.println("COOKIE ENCONTRADA" + cookies.get(COOKIE_NAME));
                req.session().attribute(SESSION_NAME, cookies.get(COOKIE_NAME));
            }
        }
    }


    private static Usuario finUser(String username){
        List<Usuario> allUsuarios = UsuarioServices.getInstancia().findAll();
        for(Usuario user: allUsuarios){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    private static Etiqueta findEtiqueta(String name){
        for (Etiqueta et: EtiquetaServices.getInstancia().findAll()) {
            if(et.getEtiqueta().equalsIgnoreCase(name)){
                return et;
            }
        }
       return null;
    }

    private static String addEtiquetas(Articulo art){
        String etiquetas = "";
        int i=0;
        for (Etiqueta eti :art.getListaEtiqueta()) {
            if(i==0){
                etiquetas=eti.getEtiqueta();
            }else{
                etiquetas+=", "+eti.getEtiqueta();
            }
            i++;
        }
        return etiquetas;
    }

    private static boolean existeEti(Articulo art, String eti){
        for (Etiqueta et: art.getListaEtiqueta()) {
            if(et.getEtiqueta().equalsIgnoreCase(eti)){
                return true;
            }
        }
        return false;
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
        for (Articulo art: ArticulosServices.getInstancia().findAll()) {
            if(art.getId()==id){
                return art;
            }
        }
        return null;
    }

    private static boolean existe_articulo (String titulo){

        for (Articulo art: ArticulosServices.getInstancia().findAll()) {
            if(art.getTitulo().equalsIgnoreCase(titulo)){
                return true;
            }
        }
        return false;
    }

    private static String veriLike(Articulo art,Usuario user){
        for (Likes like: art.getListLikes()) {
            if(like.getUser().getUsername().equalsIgnoreCase(user.getUsername())){
                return "true";
            }
        }
        return "false";
    }


}

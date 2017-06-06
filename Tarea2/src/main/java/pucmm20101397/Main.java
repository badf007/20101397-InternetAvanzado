package pucmm20101397;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import javafx.beans.binding.ObjectExpression;
import spark.Spark;
import sun.awt.geom.AreaOp;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by No. GP62 on 01/06/2017.
 */
public class Main {
    //http://localhost:4567
    public static List<estudiante> estudiantes = new ArrayList<>();


    public static void main(String[] args) {
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/");
        estudiante yuca = new estudiante(256,"juajua","lola","131241");
        estudiantes.add(yuca);
        Spark.get("/", (request, response) -> {

            StringWriter writer = new StringWriter();
            Map<String, Object> lista = new HashMap<>();

            try {
                Template formTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("listaEstudiante", estudiantes);
                formTemplate.process(map, writer);
            } catch (Exception e) {
                System.out.println(e.toString());
                Spark.halt(500);
            }

            return writer;
        });


        Spark.post("/guardar/", (request, response) -> {
            StringWriter writer = new StringWriter();
            System.out.println("MALDITA YUCA");
            try {
                String nombre = request.queryParams("nombre") != null ? request.queryParams("nombre") : "anonymous";
                String apellido = request.queryParams("apellido") != null ? request.queryParams("apellido") : "unknown";
                int matricula = Integer.parseInt( request.queryParams("matricula") != null ? request.queryParams("matricula") : "00");
                String telefono = request.queryParams("telefono") != null ? request.queryParams("telefono") : "telefono";
                System.out.println( "DATOS" +nombre + " " + apellido);
                estudiantes.add(new estudiante(matricula, nombre,apellido,telefono));
                response.redirect("/");
            } catch (Exception e) {
                System.out.println(e.toString());
                Spark.halt(500);
            }

            return writer;
        });


        Spark.get("/eliminar/:matricula", (request, response) -> {
            StringWriter writer = new StringWriter();
            //System.out.println(request.params("matricula"));
            try {
                int matricula = Integer.parseInt(request.params("matricula"));
                int indice= 0;
                int finala=0;
                for (estudiante estu: estudiantes) {
                    if(estu.getMatricula() == matricula){
                        finala = indice;
                    }
                    indice++;
                }
                estudiantes.remove(finala);
                response.redirect("/");
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
                Spark.halt(500);
            }

            return writer;
        });

        Spark.post("/update/", (request, response) -> {
            StringWriter writer = new StringWriter();
            //System.out.println(request.params("matricula"));
            try {
                int mat = Integer.parseInt( request.queryParams("matricula"));
                int matricula = Integer.parseInt( request.queryParams("matricula"));
                String nombre = request.queryParams("nombre");
                String apellido = request.queryParams("apellido");
                String telefono = request.queryParams("telefono");

                int indice= 0;
                int finala=0;
                for (estudiante estu: estudiantes) {
                    if(estu.getMatricula() == mat){
                        finala = indice;
                    }
                    indice++;
                }
                estudiante yucas = estudiantes.get(finala);
                estudiantes.remove(finala);

                yucas.setApellido(apellido);
                yucas.setMatricula(matricula);
                yucas.setNombre(nombre);
                yucas.setTelefono(telefono);
                estudiantes.add(yuca);

                response.redirect("/");

            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
                Spark.halt(500);
            }

            return writer;
        });
    }
}

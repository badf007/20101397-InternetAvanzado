<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Creacion Estudiantes</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>


</head>

<body>
<nav class="light-blue ligthen-1" role="navigation">
    <div class="nav-wrapper container">
        <a href="#" class="brand-logo center">CRUD ESTUDIANTES</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">

        </ul>
    </div>

</nav>
<br>


<section class="center">

    <table class="highlight">
        <thead>
        <tr>
            <th>Id</th>
            <th>Apellido</th>
            <th>Matricula</th>
            <th>Nombre</th>
            <th>Telefono</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        Comenzando
<#list listaEstudiante as estudiante>
        <tr id="${estudiante.matricula?string["0"]}" data-matricula="${estudiante.matricula?string["0"]}" data-telefono="${estudiante.telefono}" data-apellido="${estudiante.apellido}" data-nombre="${estudiante.nombre}">
            <td></td>
            <td>${estudiante.apellido}</td>
            <td>${estudiante.matricula?string["0"]}</td>
            <td>${estudiante.nombre}</td>
            <td>${estudiante.telefono}</td>
            <td><a class="waves-effect waves-light btn yucon" href="#modal2">Editar</a></td>
            <td> <a class="waves-effect waves-light btn" href="/eliminar/${estudiante.matricula?string["0"]}">Eliminar</a></td>
        </tr>
</#list>

        </tbody>
    </table>

</section>


<div class="fixed-action-btn " style="bottom: 45px; right: 45px;">
    <a id="menu" class=" modal-trigger waves-effect waves-light btn btn-floating btn-large cyan " href="#modal1"><i class="material-icons">+</i></a>
</div>

<!-- Modal Structure -->
<div id="modal1" class="modal ">
    <div class="modal-content">
        <h4>Datos del Estudiante</h4>


            <div class="row">
                <form action="/guardar/" method="post" class="col s12">
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="icon_prefix" name="nombre" type="text" class="validate ">
                            <label for="icon_prefix">Nombre</label>
                        </div>
                        <div class="input-field col s6">
                            <i class="material-icons prefix">perm_identity</i>
                            <input id="icon_telephone" name="apellido" type="text" class="validate ">
                            <label for="icon_telephone">Apellido</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">assignment_ind</i>
                            <input id="icon_prefix" name="matricula" type="number" class="validate ">
                            <label for="icon_prefix">Matricula</label>
                        </div>
                        <div class="input-field col s6">
                            <i class="material-icons prefix">phone</i>
                            <input id="icon_telephone" name="telefono" type="tel" class="validate ">
                            <label for="icon_telephone">Telefono</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" name="Enviar"  class=" modal-action modal-close waves-effect waves-green btn-flat ">Agregar</button>
                        <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Cancelar</a>
                    </div>
                </form>
            </div>




    </div>

</div>

<!-- Modal Structure -->
<div id="modal2" class="modal ">
    <div class="modal-content  yyaca">
        <h4>Datos del Estudiante</h4>


        <div class="row">
            <form action="/update/" method="post" class="col s12 yyaca">
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">account_circle</i>
                        <input id="icon_prefix" name="nombre" type="text" class="validate nnombre">
                        <label for="icon_prefix">Nombre</label>
                    </div>
                    <div class="input-field col s6">
                        <i class="material-icons prefix">perm_identity</i>
                        <input id="icon_telephone" name="apellido" type="text" class="validate aapellido">
                        <label for="icon_telephone">Apellido</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <i class="material-icons prefix">assignment_ind</i>
                        <input id="icon_prefix" name="matricula" type="number" class="validate mmatricula">
                        <label for="icon_prefix">Matricula</label>
                    </div>
                    <div class="input-field col s6">
                        <i class="material-icons prefix">phone</i>
                        <input id="icon_telephone" name="telefono" type="tel" class="validate ttelefono">
                        <label for="icon_telephone">Telefono</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" name="Enviar"  class=" modal-action modal-close waves-effect waves-green btn-flat ">Editar</button>
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Cancelar</a>
                </div>
            </form>
        </div>




    </div>

</div>

<script>
    $(document).ready(function() {
        //initialize all modals
        $('.modal').modal();
        $('.modal2').modal();
        //now you can open modal from code
        //$('#modal1').modal('open');

        //or by click on trigger
        //$('.trigger-modal').modal();
    });

</script>

<script>
    $(document).ready(function(){
        $('.yucon').click(function(e){

            var matricula=$(this).closest('tr').attr('data-matricula');
            var nombre=$(this).closest('tr').attr('data-nombre');
            var apellido=$(this).closest('tr').attr('data-apellido');
            var telefono=$(this).closest('tr').attr('data-telefono');
            // $('.modal-body .act').attr('action','pago.php?id='+id+'');
            // console.log("funciona cojollo");
            //console.log(nombre);
            $('.yyaca .nnombre').attr('value',nombre);
            $('.yyaca .aapellido').attr('value',apellido);
            $('.yyaca .mmatricula').attr('value',matricula);
            $('.yyaca .ttelefono').attr('value',telefono);
            // $('.modal-body .ppas').attr('value',password);
            // document.getElementById("nombre").innerHTML = nombre;
            //$('.modal-body .nombre').attr('value',id);


        });
    });


</script>
</body>

</html>

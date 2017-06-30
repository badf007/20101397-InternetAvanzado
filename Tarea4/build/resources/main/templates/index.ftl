<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ULTRA BLOG</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/css/blog-home.css" rel="stylesheet">
    <link href="/css/myCSS.css" rel="stylesheet">

</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>

            </button>
            <p style="color: white;  padding-top: 15px; font-size: medium;">ULTRA BLOG</p>
        </div>


        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/">Home</a>
                </li>

                <li>
                    <a href="/CrearArticulo/" >Nuevo Art&iacuteculo</a>
                </li>
                <#if login =="false">
                <li>
                    <a onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Log in</a>
                    <div id="id01" class="modal">
                        <form class="modal-content animate" method="post" action="/login/-1">
                            <div class="imgcontainer">
                                <span onclick="document.getElementById('id01').style.display='none'" class="close"
                                      title="Close Modal">&times;</span>
                                <img src="/img/img_avatar2.png" alt="Avatar" class="avatar">
                            </div>
                            <div class="container2">
                                <label><span class="glyphicon glyphicon-user"></span><b> Username:</b></label>
                                <input type="text" name="username" required/>

                                <label><span class="glyphicon glyphicon-eye-open"></span><b> Password:</b></label>
                                <input type="password" name="password" required/>

                            </div>
                            <div class="container2" style="background-color:#f1f1f1">
                                <p align="center">
                                   <button type="submit" class="btn btn-primary"><span
                                            class="glyphicon glyphicon-off"></span> Log In
                                    </button>
                                    <button type="button" onclick="document.getElementById('id01').style.display='none'"
                                            class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>
                                        Cancel
                                    </button>
                                </p>
                            </div>
                        </form>
                    </div>
                </li>
                </#if>
                <#if login=="true">
                <li>
                    <a onclick="document.getElementById('id02').style.display='block'" style="width:auto;">Sign up</a>
                    <div id="id02" class="modal">

                        <form class="modal-content animate" method="post" action="/signup/">
                            <span onclick="document.getElementById('id02').style.display='none'" class="close"
                                  title="Close Modal">&times;</span>
                            <div class="container2">
                                <label><b>Name:</b> </label>
                                <input type="text" name="nombre" required/>
                                <label><span class="glyphicon glyphicon-user"></span><b> Username: </b></label>
                                <input type="text" name="username" required/>
                                <label><span class="glyphicon glyphicon-eye-open"></span><b> Password: </b></label>
                                <input type="password" name="password" required/>
                                <label><b>¿Es un usuario Admin?</b></label>
                                <input type="checkbox" name="administrador">
                                <label><b>¿Es un usuario autor?</b></label>
                                <input type="checkbox" name="autor">

                            </div>
                            <div class="container2" style="background-color:#f1f1f1">
                                <p align="center">
                                    <button type="submit" class="btn btn-primary"><span
                                            class="glyphicon glyphicon-send"></span> Send
                                    </button>
                                    <button type="button" onclick="document.getElementById('id02').style.display='none'"
                                            class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>
                                        Cancel
                                    </button>
                                </p>
                            </div>
                        </form>

                    </div>
                </li>
                <li >
                 <p style="color: white; padding-top: 15px; padding-left: 500px" >WELCOME ${username},<b><a href="/logout" style="color: white;"> log out</a> </b></p>

                </li>
                </#if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">

            <h1 class="page-header">
                Entradas
                <small>Todas las entradas del blog hasta la fecha</small>
            </h1>


        <#list ListaArticulos as articulo>
            <!-- First Blog Post -->
            <h2>
                ${articulo.titulo}
            </h2>
            <p class="lead">
                by <a href="index.php">${articulo.autor}</a>
            </p>
            <p><span class="glyphicon glyphicon-time"></span> Posted on ${articulo.fecha}</p>
            <p>${articulo.cuerpo70} .......</p>
            <p><b>Etiquetas del art&iacuteculo:</b>
                <#list articulo.listaEtiqueta as etiqueta>
                ${etiqueta.etiqueta},
                </#list>
            </p>
            <a class="btn btn-primary" href="/articulo/${articulo.id}" >Leer mas <span class="glyphicon glyphicon-chevron-right"></span></a>

            <hr>
        </#list>

        </div>


    </div>
    <!-- /.row -->

    <hr>

    <!-- Footer -->
    <footer>
        <div class="row" align="center">
            <div class="col-lg-12">
                <p>Copyright &copy;</p>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </footer>
</div>
<!-- jQuery-->
<script src="/js/jquery.js"></script>
<script src="/js/myJquery.js"></script>
</body>

</html>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Creando Art&iacuteculo</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <link href="/css/blog-home.css" rel="stylesheet">
    <link href="/css/myCSS.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <p style="color: white;  padding-top: 15px; font-size: medium;">ULTRA blog</p>
                </li>
                <li>
                    <a href="/">Home</a>
                </li>
                    <li>
                        <a href="/CrearArticulo/"">Nuevo Art&iacuteculo</a>
                    </li>
                <#if login =="false">
                <li>
                <a onclick="document.getElementById('id01').style.display='block'" style="width:auto;" >Log in</a>

                <div id="id01" class="modal">

                    <form class="modal-content animate" action="">
                        <div class="imgcontainer">
                            <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
                            <img src="/img/img_avatar2.png" alt="Avatar" class="avatar">
                        </div>
                        <div class="container2">
                            <label><span class="glyphicon glyphicon-user"></span><b> Username:</b></label>
                            <input type="text"  name="uname" required/>

                            <label><span class="glyphicon glyphicon-eye-open"></span><b> Password:</b></label>
                            <input type="password"  name="psw" required/>

                        </div>
                        <div class="container2" style="background-color:#f1f1f1">
                           <p align="center"><button type="submit" class="btn btn-primary" ><span class="glyphicon glyphicon-off"></span> Log In</button>
                            <button type="button" onclick="document.getElementById('id01').style.display='none'" class="btn btn-danger" ><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                            </p>
                        </div>
                    </form>
                </div>
                </li>
                <li>
                    <a onclick="document.getElementById('id02').style.display='block'" style="width:auto;" >Sign up</a>
                    <div id="id02" class="modal">

                        <form class="modal-content animate" action="/login/" method="post">
                            <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
                            <div class="container2">
                                <label><b>Name:</b> </label>
                                <input type="text" name="nombre" required/>
                                <label><span class="glyphicon glyphicon-user"></span><b> Username: </b></label>
                                <input type="text" name="usuario" required/>
                                <label><span class="glyphicon glyphicon-eye-open"></span><b> Password: </b></label>
                                <input type="text" name="psw" required/>
                                <label><b>¿Es un usuario Admin?</b></label>
                                <input type="checkbox" name="admin">
                                <label><b>¿Es un usuario autor?</b></label>
                                <input type="checkbox" name="autor">

                            </div>
                            <div class="container2" style="background-color:#f1f1f1">
                                <p align="center"><button type="submit" class="btn btn-primary" ><span class="glyphicon glyphicon-send"></span> Send</button>
                                    <button type="button" onclick="document.getElementById('id02').style.display='none'" class="btn btn-danger" ><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                                </p>
                            </div>
                        </form>

                    </div>
                </li>
                </#if>
                <#if login == "true">
                    <li >
                        <p style="color: white; padding-top: 15px; padding-left: 500px" >WELCOME ${username},<b><a href="/logout" style="color: white;"> log out</a> </b></p>

                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
        <div class="col-md-8">
            <h1 class="page-header">
                Crear nuevo Art&iacuteculo
            </h1>
            <br/>

            <form method="post" action="/guardandoarticulo">
               <input type="text" class="form-control" placeholder="Título" name="titulo"></input><br/>
                <textarea class="form-control" rows="10" placeholder="Cuerpo" style="resize: none;" name="cuerpo"></textarea><br/>
                <input type="text" class="form-control" placeholder="Etiquetas" name="etiqueta"></input><br/>
                 <p align="right">
                     <button type="submit" class="btn btn-primary" align="center"><span class="glyphicon glyphicon-send"></span> Send</button>
                     <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
                 </p>
            </form>
        </div>
    </div>
</div>
<!-- jQuery-->
<script src="/js/jquery.js"></script>
<script src="/js/myJquery.js"></script>
</body>
</html>
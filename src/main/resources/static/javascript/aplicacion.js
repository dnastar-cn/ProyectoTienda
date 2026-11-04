//variables globales
nombre_login = "" // variable que indica el nombre del usuario si se ha identificado

//parte de descarga de plantillas html
//plantilla_inicio = $.get("plantillas_html_" + idioma_actual + "/inicio.html")
$.get("plantillas_html_" + idioma_actual + "/inicio.html").done(function(res){
    plantilla_inicio = res
})


$.get("plantillas_html_" + idioma_actual + "/listado_productos.html").done(function (res){
    plantilla_productos = res
})

$.get("plantillas_html_" + idioma_actual + "/detalles_producto.html").done(function (res){
    plantilla_detalles_producto = res
})

$.get("plantillas_html_" + idioma_actual + "/registro_usuario.html").done(function (res){
    plantilla_registro_usuario = res
})

$.get("plantillas_html_" + idioma_actual + "/identificar_usuario.html").done(function (res){
    plantilla_login_usuario = res
})

$.get("plantillas_html_" + idioma_actual + "/carrito.html").done(function (res){
    plantilla_carrito = res
})

$.get("plantillas_html_" + idioma_actual + "/check_out_1.html").done(function (res){
    plantilla_check_out_1 = res
})
$.get("plantillas_html_" + idioma_actual + "/check_out_2.html").done(function (res){
    plantilla_check_out_2 = res
})
$.get("plantillas_html_" + idioma_actual + "/check_out_3.html").done(function (res){
    plantilla_check_out_3 = res
})

//parte de atencion a enlaces
$("#inicio").click(
    function (){
        $("#contenedor").html(plantilla_inicio)
    }
)

titulo_a_buscar = "" //titulo en el buscador

comienzo_resultados = 0



function mostrar_figura(){
    $.getJSON("restFiguras/listar", { titulo: titulo_a_buscar, comienzo : comienzo_resultados}).done(
        function (json){
            //alert(json)
            console.log(json)
            //vamos a usar el json para listar los productos
            resultadoMustache = Mustache.render(plantilla_productos, json.figuras)
            totalFiguras = json.totalFiguras
            $("#contenedor").html(resultadoMustache)
            $("#total_resultados").html(totalFiguras)
            //vamos a ocultar el anterior o siguiente cuando no proceda
            if(comienzo_resultados <= 0){
                //en este caso ocultamos el anterior
                $("#enlace_anterior").hide()
            }else{
                $("#enlace_anterior").show()
            }

            if ((comienzo_resultados+10)<totalFiguras){
                //en este caso si queremos que aparezca siguiente
                $("#enlace_siguiente").show()
            }else{
                $("#enlace_siguiente").hide()
            }

            //para no perder el titulo:
            $("#titulo_buscador").val(titulo_a_buscar)
            $("#titulo_buscador").focus()
            //una vez volcado el contenido voy a decir como funcionan
            //el buscador, el ant/sig y el span de total resultados
            $("#titulo_buscador").keyup(
                function (){
                    //segun el usuario escriba en el buscador lo que escriba lo guardo en la variable en el titulo a buscar
                    //invoco de nuevo al mostrar videojuegos
                    titulo_a_buscar = $(this).val()
                    mostrar_figura()
                    comienzo_resultados = 0
                }
            )//end keyup

            $("#enlace_siguiente").click(
                function (){
                    comienzo_resultados += 10
                    mostrar_figura()
                }
            )
        }
    )
}//end mostrar_figura

function mostrar_detalles(id){
    //alert('mostrar detalles del producto de id: ' + id)
    $.getJSON("restFiguras/obtener-figura",
        {
            id: id
        }).done(
        function (json){
            console.log(json)
            //alert("json recibido mira consola, vamos a usar una plantilla de plantillas_htl para mostrarlo")
            resultadoHtml=Mustache.render(plantilla_detalles_producto,json)
            $("#contenedor").html(resultadoHtml)
        }
    )//end done
}//fin mostrar_detalles

$("#figuras").click(
    mostrar_figura
)//end figuras click

//por defecto cuando se acceda a la web
//que muestre los figuras de la tienda

$("#registrarme").click(
    mostrar_formulario_registro_usuario
)

function mostrar_formulario_registro_usuario(){
    $("#contenedor").html(plantilla_registro_usuario)
    // una vez mostrado el form de registro
    // vamos a decir que va a pasar cuando se haga click en Registrarse
    $("#form_registro_usuario").submit(
        function(e){
            //codigo a ejecutar cuando se haga el submit/envio del form
            e.preventDefault() // para evitar que el boton del form
            // refresque la pagina
            //validar datos del form...
            //suponemos de momento que todos los datos estan bien
            // tenemos una cosa llamada FormData
            // que nos va a permitir obtener todo lo insertado en form
            // de una sola vez
            formulario = document.forms[0]
            formData = new FormData(formulario)
            console.log("bien, ya tengo toda la informacion introducida:")
            console.log(Object.fromEntries(formData))
            //mandar este formData a un servicio REST
            // mandar el formData a UsuariosREST
            $.ajax("restUsuarios/registrarUsuario",{
                type : "POST",
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                success: function (res){
                    alert(res)
                    confetti()
                }

            })

        }//end function
    )//end submit
}// end mostrar_formulario_registro_usuario

$("#identificarme").click(
    mostrar_login
)

function mostrar_login(){
    $("#contenedor").html(plantilla_login_usuario)
    //despues de mostrar la plantilla html de identificar usuario
    //vamos a decir que hacer cuando el usuario haga click en
    //Identificarme
    $("#form_login").submit(
        function (e){
            e.preventDefault()
            $.post("restUsuarios/identificarUsuario",
                {
                    email: $("#email").val(),
                    pass: $("#pass").val()
                }
            ).done(function (res){
                if(res == "email o pass incorrectos"){
                    alert(res)
                    return
                }
                nombre_login = res.split(",")[1]
                //aprovechamos y listamos productos
                alert("identificado correctamente, ya puedes agregar productos a carrito")
                mostrar_figura()
                $("#usuarioActual").html("Bienvenido " + nombre_login)
                //ocultar los div de enlaces para usuarios no identificados
                $(".enlace-registrarme").css("display","none")
                $(".enlace-identificarme").css("display","none")
                //mostrar los div de enlaces para usuarios identificados
                $(".enlace-carrito").css("display","block")
                $(".enlace-pedidos").css("display","block")
                $(".enlace-mis_datos").css("display","block")
                $(".enlace-cerrar_sesion").css("display","block")
            })

        }//end function
    )//end submit
}//end mostrar_login

function agregarProductoCarrito(idProducto){
    if ( nombre_login == "" ){
        alert("identificate primero")
        return
    }
    //mandar a un servicio REST la id y la cantidad del producto
    $.post("restCarrito/agregarProducto",{
        id: idProducto,
        cantidad: 1
    }).done(function(res){
        alert(res)
    })
}//end agregarProductoCarrito

$("#carrito").click(
    mostrar_productos_carrito
)

function mostrar_productos_carrito(){
    $.getJSON("restCarrito/obtenerProductos").done(
        function(res){
            console.log("json recibido:")
            console.log(res)
            // usar la plantilla carrito para mostrar el json recibido
            html = Mustache.render(plantilla_carrito, res)
            $("#contenedor").html(html)
            $("#realizar_pedido").click(
                mostrar_check_out_paso_1
            )
        }
    )
}

function mostrar_check_out_paso_1(){
    $("#contenedor").html(plantilla_check_out_1)
    $("#aceptar_paso_1").click(
        enviar_datos_paso1
    )
}

function enviar_datos_paso1(){
    //obtener los valores introducidos
    //mandarlos a PedidosRest
    nombreCompleto = $("#campo_nombre").val()
    direccion = $("#campo_direccion").val()
    provincia = $("#campo_provincia").val()

    //validaciones...

    $.post("restPedidos/procesarPaso1",
        {
            nombre: nombreCompleto,
            direccion: direccion,
            provincia: provincia
        }
    ).done(function (res){
        //alert("el servidor responde: "+ res)
        if(res == "ok"){
            $("#contenedor").html(plantilla_check_out_2);
            $("#aceptar_paso_2").click(
                enviar_datos_paso2
            )
        }else{
            alert(res)
        }
    })

}//end enviar_datos_paso1

function enviar_datos_paso2(){
    //recogiendo datos introducidos
    tipo_tarjeta = $("#tipo_tarjeta").val()
    numero_tarjeta = $("#numero_tarjeta").val()
    titular_tarjeta = $("#titular_tarjeta").val()
    //validarlos


    //mandarlos por el post
    $.post("restPedidos/procesarPaso2",{
        tarjeta: tipo_tarjeta,
        numero: numero_tarjeta,
        titular: titular_tarjeta
    }).done(function (res){
        //alert(res)
        console.log(res)
        //puedo usar la informacion recibida para mostrar lo necesario en el paso 3
        $("#contenedor").html(Mustache.render(plantilla_check_out_3,res))
        $("#boton_confirmar_pedido").click(
            enviar_datos_paso3
        )
    })
}//end enviar datos paso2

function enviar_datos_paso3(){
    $.post("restPedidos/procesarPaso3").done(
        function (res){
            alert(res)
            if(res == "ok"){
                alert("pedido completado")
            }
        }
    )
}//end enviar datos paso 3

$("#pedidos").click(
    function(){
        alert("pedir los pedidos del usuario")
    }
)
$("#mis_datos").click(
    function(){
        alert("pedir los datos del usuario")
    }
)
$("#cerrar_sesion").click(
    function(){
        alert("cerrar la sesion del usuario")
    }
)

mostrar_figura()
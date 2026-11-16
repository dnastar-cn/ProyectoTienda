//variables globales
nombre_login = "" // variable que indica el nombre del usuario si se ha identificado

//textos de los avisos en cada idioma
//idioma_actual viene del index de cada idioma
textos_avisos = {
    es: {
        registro_ok: "Te has registrado. Ya puedes iniciar sesión",
        login_error: "Email o contraseña incorrectos",
        login_ok: "Sesión iniciada. Ya puedes añadir figuras al carrito",
        login_primero: "Inicia sesión para añadir figuras al carrito",
        cantidad_error: "La cantidad tiene que ser 1 o más",
        carrito_ok: "Añadido al carrito",
        pedido_ok: "¡Pedido completado! Puedes verlo en Ver mis pedidos",
        nombre_error: "El nombre solo puede tener letras y espacios, de 2 a 20"
    },
    en: {
        registro_ok: "You're signed up. You can now log in",
        login_error: "Incorrect email or password",
        login_ok: "Logged in. You can now add figures to your cart",
        login_primero: "Log in to add figures to your cart",
        cantidad_error: "Quantity must be 1 or more",
        carrito_ok: "Added to cart",
        pedido_ok: "Order completed! You can see it in My orders",
        nombre_error: "The name can only have letters and spaces, 2 to 20"
    },
    ja: {
        registro_ok: "登録が完了しました。ログインできます",
        login_error: "メールアドレスまたはパスワードが正しくありません",
        login_ok: "ログインしました。カートに追加できます",
        login_primero: "カートに追加するにはログインしてください",
        cantidad_error: "数量は1以上にしてください",
        carrito_ok: "カートに追加しました",
        pedido_ok: "注文が完了しました。注文履歴で確認できます",
        nombre_error: "名前は2〜20文字の文字とスペースのみです"
    }
}
textos = textos_avisos[idioma_actual]

//muestra un aviso abajo de la pantalla en vez de un alert
//tipo puede ser "error" para que salga en rojo
function avisar(mensaje, tipo){
    //si ya habia un aviso lo quito para que no se amontonen
    $(".aviso-flotante").remove()
    //let para que cada aviso tenga su propia variable
    let aviso = $('<div class="aviso-flotante" role="status"></div>').text(mensaje)
    if(tipo == "error"){
        aviso.addClass("aviso-flotante-error").attr("role", "alert")
    }
    $("body").append(aviso)
    //a los 3 segundos se va y luego lo borro
    setTimeout(function(){
        aviso.addClass("aviso-flotante-saliendo")
        setTimeout(function(){ aviso.remove() }, 200)
    }, 3000)
}//end avisar

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
$.get("plantillas_html_" + idioma_actual + "/check_out_4.html").done(function (res){
    plantilla_check_out_4 = res
})
$.get("plantillas_html_" + idioma_actual + "/mis_pedidos.html").done(function (res){
    plantilla_mis_pedidos = res
})
$.get("plantillas_html_" + idioma_actual + "/mis_datos.html").done(function (res){
    plantilla_mis_datos = res
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
                    comienzo_resultados = 0
                    mostrar_figura()

                }
            )//end keyup

            $("#enlace_anterior").click(
                function (){
                    comienzo_resultados -= 10
                    mostrar_figura()
                }
            )


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
                    confetti()
                    avisar(textos.registro_ok)
                    //despues de registrarse le llevo al login
                    mostrar_login()
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
    //vamos a comprobar si hay alguna cookie en la info de identificacion
    //mucho cuidado en guardar contraseña o informacion sin encriptar
    if(typeof(Cookies.get("email")) != "undefined"){
        $("#email").val(Cookies.get("email"))
    }
    if(typeof(Cookies.get("pass")) != "undefined"){
        $("#pass").val(Cookies.get("pass"))
    }

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
                    avisar(textos.login_error, "error")
                    return
                }
                nombre_login = res.split(",")[1]
                //aprovechamos y listamos productos
                avisar(textos.login_ok)
                //una vez identificado correctamente
                //miramos si la casilla de recordar datos esta avtiva
                if($("#recordar_pass").prop("checked")){
                    //guardar los datos de acceso en una coockie
                    //un archivo que guarda el navegador
                    //para cada sitio web
                    Cookies.set("email",$("#email").val(), { expires: 100 })
                    Cookies.set("pass",$("#pass").val(), { expires: 100 })
                }
                mostrar_figura()
                id_usuario = res.split(",")[2]
                html_img = '<img class="avatar-usuario" src="mostrar_imagen_usuario?id=' + id_usuario + '" alt="Avatar de ' + nombre_login + '" onerror="this.remove()"/>'
                $("#usuarioActual").html("Bienvenido " + nombre_login + html_img)
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
        avisar(textos.login_primero, "error")
        //le llevo directamente al login
        mostrar_login()
        return
    }
    //cada input de cantidad tiene de id cantidad_ y la id de la figura
    //asi cojo la cantidad de esta figura y no de otra
    cantidad = parseInt( $("#cantidad_" + idProducto).val() )
    if( isNaN(cantidad) || cantidad < 1 ){
        avisar(textos.cantidad_error, "error")
        return
    }
    //mandar a un servicio REST la id y la cantidad del producto
    $.post("restCarrito/agregarProducto",{
        id: idProducto,
        cantidad: cantidad
    }).done(function(res){
        //el servidor responde un texto para probar con h2, al usuario le enseño uno claro
        avisar(textos.carrito_ok)
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
            if(res.length == 0){
                $("#realizar_pedido").hide()
            }
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
    localidad = $("#campo_localidad").val()
    codigoPostal = $("#campo_codigo_postal").val()
    telefono = $("#campo_telefono").val()

    //validaciones...
    if( ! validarNombre(nombreCompleto) ){
        return
    }

    $.post("restPedidos/procesarPaso1",
        {
            nombre: nombreCompleto,
            direccion: direccion,
            provincia: provincia,
            localidad: localidad,
            codigoPostal: codigoPostal,
            telefono: telefono
        }
    ).done(function (res){
        //alert("el servidor responde: "+ res)
        if(res == "ok"){
            $("#contenedor").html(plantilla_check_out_2);
            $("#aceptar_paso_2").click(
                enviar_datos_paso2
            )
        }else{
            avisar(res, "error")
        }
    })

}//end enviar_datos_paso1

function enviar_datos_paso2(){
    //recogiendo datos introducidos
    tipo_tarjeta = $("#tipo_tarjeta").val()
    numero_tarjeta = $("#numero_tarjeta").val()
    titular_tarjeta = $("#titular_tarjeta").val()
    caducidad_tarjeta = $("#caducidad_tarjeta").val()
    //validarlos


    //mandarlos por el post
    $.post("restPedidos/procesarPaso2",{
        tarjeta: tipo_tarjeta,
        numero: numero_tarjeta,
        titular: titular_tarjeta,
        caducidad: caducidad_tarjeta
    }).done(function (res){
        if(res == "ok"){
            //paso 3, elegir el metodo de envio
            $("#contenedor").html(plantilla_check_out_3)
            $("#aceptar_paso_3").click(
                enviar_datos_paso3
            )
        }else{
            avisar(res, "error")
        }
    })
}//end enviar datos paso2

function enviar_datos_paso3(){
    //el metodo de envio es el radio que esta marcado
    metodo_envio = $("input[name='metodo_envio']:checked").val()

    $.post("restPedidos/procesarPaso3",{
        metodoEnvio: metodo_envio
    }).done(function (res){
        console.log(res)
        //el servidor devuelve el resumen para mostrarlo en el paso 4
        $("#contenedor").html(Mustache.render(plantilla_check_out_4,res))
        $("#boton_confirmar_pedido").click(
            enviar_datos_paso4
        )
    })
}//end enviar datos paso3

function enviar_datos_paso4(){
    $.post("restPedidos/procesarPaso4").done(
        function (res){
            if(res == "ok"){
                confetti()
                avisar(textos.pedido_ok)
                mostrar_figura()
            }else{
                avisar(res, "error")
            }
        }
    )
}//end enviar datos paso 4

//ver mis pedidos
$("#pedidos").click(
    mostrar_mis_pedidos
)

function mostrar_mis_pedidos(){
    //pido al servidor los pedidos del usuario
    $.getJSON("restPedidos/misPedidos").done(
        function(res){
            console.log(res)
            $("#contenedor").html(Mustache.render(plantilla_mis_pedidos, res))
        }
    )
}//end mostrar_mis_pedidos

//mis datos
$("#mis_datos").click(
    mostrar_mis_datos
)

function mostrar_mis_datos(){
    //pido los datos del usuario y los pongo en el form
    $.getJSON("restUsuarios/misDatos").done(
        function(res){
            $("#contenedor").html(Mustache.render(plantilla_mis_datos, res))
            $("#form_mis_datos").submit(
                function(e){
                    e.preventDefault()
                    $.post("restUsuarios/actualizarMisDatos",
                        {
                            nombre: $("#mis_datos_nombre").val(),
                            email: $("#mis_datos_email").val(),
                            pass: $("#mis_datos_pass").val()
                        }
                    ).done(function(res){
                        if(res.startsWith("ok,")){
                            //cambio el nombre del bienvenido de arriba
                            nombre_login = res.split(",")[1]
                            html_img = '<img class="avatar-usuario" src="mostrar_imagen_usuario?id=' + id_usuario + '" alt="Avatar de ' + nombre_login + '" onerror="this.remove()"/>'
                            $("#usuarioActual").html("Bienvenido " + nombre_login + html_img)
                            $("#mis_datos_error").addClass("hidden")
                            $("#mis_datos_ok").removeClass("hidden")
                            $("#mis_datos_pass").val("")
                        }else{
                            //si falla algo, por ejemplo el email repetido
                            $("#mis_datos_ok").addClass("hidden")
                            $("#mis_datos_error").text(res).removeClass("hidden")
                        }
                    })
                }//end function
            )//end submit
        }
    )
}//end mostrar_mis_datos

$("#cerrar_sesion").click(cerrar_sesion)

function cerrar_sesion(){
    $.post("restUsuarios/cerrarSesion").done(
        function(res){
            if(res == "ok"){
                location.reload()
            }
        }
    )
}

mostrar_figura()
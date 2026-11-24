//validaciones por expresiones regulares
// en js todo lo que se ponga entre / es
//expresion regular
// ^ es para decir que debe empezar
// $ debe terminar
// la i al final es para case insensitive,
// considerar mayusculas y minusculas
regexp_nombre = /^[a-z áéíóúñ]{2,20}$/i

regexp_numero_tarjeta = /^\d{16}$/
regexp_titular = /^[a-z áéíóúñ]{2,40}$/i
regexp_caducidad = /^(0[1-9]|1[0-2])\/\d{2}$/ //mes de 01 a 12, barra y año de 2 cifras

//paso 2 del pedido, el tipo "0" es el "Elegir tipo" del desplegable
function validarTarjeta(tipo, numero, titular, caducidad){
    if(tipo != "0" && regexp_numero_tarjeta.test(numero)
        && regexp_titular.test(titular) && regexp_caducidad.test(caducidad)){
        return true;
    }else{
        avisar(textos.tarjeta_error, "error")
        return false;
    }
}

function validarNombre(nombre){
    if(regexp_nombre.test(nombre)){
        return true;
    }else{
        avisar(textos.nombre_error, "error")
        return false;
    }
}
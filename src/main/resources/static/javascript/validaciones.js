//validaciones por expresiones regulares
// en js todo lo que se ponga entre / es
//expresion regular
// ^ es para decir que debe empezar
// $ debe terminar
// la i al final es para case insensitive,
// considerar mayusculas y minusculas
regexp_nombre = /^[a-z áéíóúñ]{2,20}$/i

function validarNombre(nombre){
    if(regexp_nombre.test(nombre)){
        return true;
    }else{
        avisar(textos.nombre_error, "error")
        return false;
    }
}
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
        alert("nombre debe ser letras y espacios de 2 a 20 caracteres")
        return false;
    }
}
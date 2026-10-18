package com.xander.tienda.figuras.interceptores;


//un metodo de esta clase se va a ejecutar antes de cualquier acceso a admin/
//ahi vamos  a controlar si permitimos accedes o no


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//cuando arranque la app hace que haya un objeto de esta clase en el sistema
//controlado por spring
@Component
public class InterceptorAdmin implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //aqui pongo el codigo lo que se va a ejecuar antes de lo que yo diga

        String passAdmin="";  //va a ser un pass que el usuario tiene que introducir

        //si el usuario inserto una contraseña en el login de admin:
        if(request.getParameter("pass-login-admin") != null) {
            passAdmin = request.getParameter("pass-login-admin");
            if(passAdmin.equals("123")){
                //guardo en una variable de sesion que el usuario actual
                //es un admin
                request.getSession().setAttribute("admin","ok");
            }
        }

        //esteinterceptor se va a ejecutar antes del acceso a cualquier ruta
        //vamos a ver si se quiere acceder a /admin

        System.out.println("hola desde el interceptor");
        if(request.getRequestURI().contains("/admin/") ){
            //miro si la variable de sesion admin existe y vale ok
            //eso quiere decir que el usuario introdujo correctamente
            // el pass en el form del login admin

            if( ! (request.getSession().getAttribute("admin") != null
            && request.getSession().getAttribute("admin").equals("ok"))){
                //si en sesion no esta este admin ok. redirecciono al form de login
                response.sendRedirect("../loginAdmin");
                return false;
            }

        }
        return true;
    }
}

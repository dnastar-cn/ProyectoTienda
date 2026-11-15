package com.xander.tienda.figuras.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ControladorAdmin {

    //la redireccion que hace el interceptor sera atendida
    // aqui que es el unico sitio donde se puede devolver una vista
    @RequestMapping("loginAdmin")
    public String loginAdmin(){
        return "admin/login_admin";
    }

    @RequestMapping("admin/inicioAdmin")
    public String inicioAdmin(){
        return "admin/inicio_admin";
    }
    @RequestMapping("admin/cerrarSesion")
    public RedirectView cerrarSesion(HttpServletRequest request){
       request.getSession().invalidate();
       RedirectView rw = new RedirectView();
       rw.setUrl("http://localhost:8080/");
       return rw;
    }
}

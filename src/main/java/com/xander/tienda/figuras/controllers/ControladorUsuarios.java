package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.servicios.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//gestionar usuarios en administracion, listar y borrar
@Controller
@RequestMapping("admin/")
public class ControladorUsuarios {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @RequestMapping("gestionarUsuarios")
    public String gestionarUsuarios(Model model){
        model.addAttribute("usuarios", servicioUsuarios.obtenerUsuarios());
        return "admin/gestionar_usuarios";
    }

    @RequestMapping("borrarUsuario")
    public String borrarUsuario(@RequestParam("id") Long id, Model model){
        try {
            servicioUsuarios.borrarUsuario(id);
        } catch (Exception e) {
            //si no se puede borrar porque tiene pedidos
            //le mando el mensaje a la vista
            model.addAttribute("error", e.getMessage());
        }
        return gestionarUsuarios(model);
    }

}//end class

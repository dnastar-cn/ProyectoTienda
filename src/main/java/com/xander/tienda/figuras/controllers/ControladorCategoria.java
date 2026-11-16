package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.model.Categoria;
import com.xander.tienda.figuras.servicios.ServicioCategorias;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//gestionar categorias en administracion
//es igual que el de figuras, listar registrar editar y borrar
@Controller
@RequestMapping("admin/")
public class ControladorCategoria {

    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarCategorias")
    public String gestionarCategorias(Model model){
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/gestionar_categorias";
    }

    @RequestMapping("registrarCategoria")
    public String registrarCategoria(Model model){
        model.addAttribute("nueva", new Categoria());
        return "admin/registro_categoria";
    }

    @RequestMapping("guardarCategoria")
    public String guardarCategoria(@ModelAttribute("nueva") @Valid Categoria nueva,
                                   BindingResult resultadoValidacion,
                                   Model model){
        if(resultadoValidacion.hasErrors()){
            //si hay errores vuelve al form
            return "admin/registro_categoria";
        }
        servicioCategorias.registrarCategoria(nueva);
        return gestionarCategorias(model);
    }

    @RequestMapping("editarCategoria")
    public String editarCategoria(@RequestParam("id") int id, Model model){
        model.addAttribute("categoriaEditar", servicioCategorias.obtenerCategoriaPorId(id));
        return "admin/editar_categoria";
    }

    @RequestMapping("guardarCambiosCategoria")
    public String guardarCambiosCategoria(@ModelAttribute("categoriaEditar") @Valid Categoria categoriaEditar,
                                          BindingResult resultadoValidacion,
                                          Model model){
        if(resultadoValidacion.hasErrors()){
            return "admin/editar_categoria";
        }
        servicioCategorias.guardarCambiosCategoria(categoriaEditar);
        return gestionarCategorias(model);
    }

    @RequestMapping("borrarCategoria")
    public String borrarCategoria(@RequestParam("id") int id, Model model){
        try {
            servicioCategorias.borrarCategoria(id);
        } catch (Exception e) {
            //si no se puede borrar porque tiene figuras
            //le mando el mensaje a la vista
            model.addAttribute("error", e.getMessage());
        }
        return gestionarCategorias(model);
    }

}//end class

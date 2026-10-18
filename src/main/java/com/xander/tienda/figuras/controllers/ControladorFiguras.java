package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.model.Figura;
import com.xander.tienda.figuras.servicios.ServicioCategorias;
import com.xander.tienda.figuras.servicios.ServicioFiguras;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/")
public class ControladorFiguras {

    //pido a spring que me de el objeto que tiene de
    //la unica clase que tiene el implements de Serviciofiguras
    //dicho objeto es el obj de la clase que tiene @Service class ServiciofigurasImplJPA implements Serviciofiguras
    @Autowired
    private ServicioFiguras servicioFiguras;
    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarFiguras")
    public String gestionarFiguras(Model model){
        model.addAttribute("figuras",servicioFiguras.obtenerFiguras());
        return "admin/gestionar_productos";
    }

    @RequestMapping("registrarFigura")
    public String registrarFigura(Model model){
        Figura nuevo = new Figura();
        nuevo.setPrecio(1);
        model.addAttribute("nuevo",nuevo);
        // necesito obtener las categorias
        // para meterlas en este model de spring
        // y que le lleguen al html del return
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/registro_producto";
    }

    @RequestMapping("guardarFigura")
    public String guardarFigura(@ModelAttribute("nuevo") @Valid Figura nuevo,
                                    BindingResult resultadoValidacion,
                                    Model model){
        //modelatribute es para que funcionen las validaciones del objeto
        System.out.println("Recibido " + nuevo);

        if(resultadoValidacion.hasErrors()){
            //si el figura tiene errores mandamos al usuario a
            // el formulario de nuevo
            return "admin/registro_producto";

        }

        servicioFiguras.registrarFigura(nuevo);
        return "admin/registro_producto_ok";
    }

    @RequestMapping("borrarFigura")
    public String borrarFigura(@RequestParam("id") Long id, Model model) throws Exception {  //model variable que metes cosas y llega a la vista que le llega al return
        //repaso : model es una variable donde meto lo que quiero que le llegue
        //a la vista indicada en el return
        System.out.println("id recibida para hacer borrar registro: " + id);

        servicioFiguras.borrarFigura(id);

        return gestionarFiguras(model);
    }

    @RequestMapping("editarFigura")
    public String editarFigura(@RequestParam("id")Long id, Model model){

        Figura fEditar = servicioFiguras.obtenerFiguraPorId(id);
        fEditar.setIdCategoria(fEditar.getCategoria().getId());

        model.addAttribute("figuraEditar", fEditar);
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());

        return "admin/editar_producto";
    }

    //el metodo va a atender la ruta guardarcambiofigura
    //esa ruta llega al hacer click en el guardar cambios del form de edicio
    @RequestMapping("guardarCambiosFigura")
    public String guardarCambiosFigura(
            @ModelAttribute("figuraEditar")
            @Valid
            Figura figuraEditar,
            BindingResult resultadoValidacion,
            Model model) throws Exception {  //model en estos metodos es para meterle lo que queramos que le llegue a la vista devuelta en el return
        if(resultadoValidacion.hasErrors()){
            return "admin/editar_producto";
        }else {
            servicioFiguras.guardarCambiosFigura(figuraEditar);
            return gestionarFiguras(model);
        }
    }

}//end class

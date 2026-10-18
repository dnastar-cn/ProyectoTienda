package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.servicios.ServicioSetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorInicio {

    // cuando se de la pagina de inicio
    // llamamos al ServicioSetUp para que prepare
    // los registros iniciales si es necesario
    @Autowired
    private ServicioSetUp servicioSetUp;

    @RequestMapping()
    public String inicio(){

        // vamos a llamar a un servicio para asegurar
        // que tengamos unos productos y unas categorias
        // en la base de datos
        servicioSetUp.prepararRegistrosIniciales();

        return "index";
    }

}

package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.servicios.ServicioSetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorInicio {

    // cuando se de la pagina de inicio
    // llamamos al ServicioSetUp para que prepare
    // los registros iniciales si es necesario
    @Autowired
    private ServicioSetUp servicioSetUp;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping()
    public String inicio(){

        // vamos a llamar a un servicio para asegurar
        // que tengamos unos productos y unas categorias
        // en la base de datos
        servicioSetUp.prepararRegistrosIniciales();
        //vamos a ver que idioma es el que tiene seleccionado el usuario
        //para dar un index u otro
        String idiomaActual=messageSource.getMessage("idioma.actual",null, LocaleContextHolder.getLocale());
        System.out.println("devolver el index para el idioma " +  idiomaActual);

        return "index_"+ idiomaActual;
    }

}

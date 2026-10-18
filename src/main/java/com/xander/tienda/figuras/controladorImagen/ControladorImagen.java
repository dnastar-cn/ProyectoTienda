package com.xander.tienda.figuras.controladorImagen;

import com.xander.tienda.figuras.servicios.ServicioFiguras;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

//es un controlador que sirve para ofrecer la imagen indicada
@Controller

public class ControladorImagen {
    @Autowired
    private ServicioFiguras servicioFiguras;


    @RequestMapping("mostrar_imagen")
    public void mostrarImagen(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        byte[] info = servicioFiguras.obtenerFiguraPorId(id).getImagenPortada();

        if(info == null ) {
            return;
        }
        //emitir info
        response.setContentType("image/jpeg, image/jpg, image.png, image.gif, image.webp");
        response.getOutputStream().write(info);
        response.getOutputStream().close();

    }
}


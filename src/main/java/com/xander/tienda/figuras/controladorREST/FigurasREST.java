package com.xander.tienda.figuras.controladorREST;

import com.xander.tienda.figuras.servicios.ServicioFiguras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//necesario para listar y ver detalles
//en la parte publica

//un servicio rest se le puede llamar API
//ESTE archivo seria el api

//los @restcontroller no devuelven vistas sino json
@RestController
@RequestMapping("restFiguras/")
public class FigurasREST {

    @Autowired
    private ServicioFiguras servicioFiguras;

//    @RequestMapping("listar")
//    public List<Figura> listar(){
//        return servicioFiguras.obtenerFiguras();
//    }
    // ahora que se ha complicado un poco
    // vamos a tomar más control en devolver el json con el
    // listado de figuras
    @RequestMapping("listar")
    public List<Map<String,Object>> listar(){
        return servicioFiguras.obtenerJSONfiguras();
    }

    //para testear:
//   http://localhost:8080/restFiguras/obtener-figura?id=1
    @RequestMapping("obtener-figura")
    public Map<String,Object> obtenerfigura(@RequestParam("id") Long id){
        //estos return @restcontroler devuelven json
        return servicioFiguras.obtenerJSONFiguraPorId(id);
    }
}

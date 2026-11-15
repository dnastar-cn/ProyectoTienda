package com.xander.tienda.figuras.controllers;

import com.xander.tienda.figuras.constantesPedidos.EstadosPedido;
import com.xander.tienda.figuras.model.Pedido;
import com.xander.tienda.figuras.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/")
public class ControladorPedidos {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("gestionarPedidos")
    public String gestionarPedidos(Model model){
        model.addAttribute("pedidos",servicioPedidos.obtenerPedidos());
        return "admin/gestionar_pedidos";   //devuelvo el html

    }

    @RequestMapping("verDetallesPedido")
    public String verDetallesPedido(@RequestParam("id") Long id, Model model){
        Pedido p = servicioPedidos.obtenerPedidoPorId(id);
        model.addAttribute("pedido",p);
        //meter los estados en model para formar un desplegable en pedidos_detalle.html
        Map<String,String> estados = new HashMap<String, String>();
        estados.put(EstadosPedido.Completado.name(), "Completado");
        estados.put(EstadosPedido.Enviado.name(), "Enviado");
        estados.put(EstadosPedido.Incidencia.name(), "Incidencia en el pedido");
        model.addAttribute("estados",estados);
        return "admin/pedidos_detalle";
    }
}

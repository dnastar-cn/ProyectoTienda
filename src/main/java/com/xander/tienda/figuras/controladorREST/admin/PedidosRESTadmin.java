package com.xander.tienda.figuras.controladorREST.admin;

import com.xander.tienda.figuras.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/restPedido")
public class PedidosRESTadmin {
    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("actualizarEstadoPedido")
    public String actualizarEstadoPedido(@RequestParam("id") Integer id, String estado) {
        servicioPedidos.actualizarEstadoPedido(id, estado);
        return "ok";
    }
}

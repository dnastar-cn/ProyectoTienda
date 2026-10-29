package com.xander.tienda.figuras.controladorREST;

import com.xander.tienda.figuras.model.Usuario;
import com.xander.tienda.figuras.responseData.ResumenPedido;
import com.xander.tienda.figuras.servicios.ServicioPedidos;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restPedidos/")   //devuelve json
public class PedidosRest {

    @Autowired  //crea un objeto
    private ServicioPedidos  servicioPedidos;

    @RequestMapping("procesarPaso1")
    public String paso1(String nombre, String direccion, String provincia, HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso1(nombre,direccion,provincia, u.getId());
        return "ok";
    }

    @RequestMapping("procesarPaso2")
    public ResumenPedido paso2 (String tarjeta, String numero, String titular, HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso2(titular,numero,tarjeta,u.getId());
        return servicioPedidos.obtenerResumenPedido(u.getId());
    }

    @RequestMapping("procesarPaso3")
    public String procesarPaso3 (HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.confirmarPedido(u.getId());
        return "ok";
    }

}










































































































































































































































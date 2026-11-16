package com.xander.tienda.figuras.controladorREST;

import com.xander.tienda.figuras.model.Usuario;
import com.xander.tienda.figuras.responseData.ResumenPedido;
import com.xander.tienda.figuras.servicios.ServicioPedidos;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restPedidos/")   //devuelve json
public class PedidosRest {

    @Autowired  //crea un objeto
    private ServicioPedidos  servicioPedidos;

    @RequestMapping("procesarPaso1")
    public String paso1(String nombre, String direccion, String provincia,
                        String localidad, String codigoPostal, String telefono,
                        HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso1(nombre,direccion,provincia,
                localidad,codigoPostal,telefono, u.getId());
        return "ok";
    }

    @RequestMapping("procesarPaso2")
    public String paso2 (String tarjeta, String numero, String titular, String caducidad, HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso2(titular,numero,tarjeta,caducidad,u.getId());
        return "ok";
    }

    //paso 3 nuevo, el metodo de envio
    //devuelve el resumen para el paso 4
    @RequestMapping("procesarPaso3")
    public ResumenPedido paso3 (String metodoEnvio, HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso3(metodoEnvio, u.getId());
        return servicioPedidos.obtenerResumenPedido(u.getId());
    }

    @RequestMapping("procesarPaso4")
    public String procesarPaso4 (HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.confirmarPedido(u.getId());
        return "ok";
    }

    //para ver mis pedidos, devuelve los pedidos del usuario que esta identificado
    @RequestMapping("misPedidos")
    public List<Map<String,Object>> misPedidos (HttpServletRequest request) {
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        return servicioPedidos.obtenerPedidosUsuario(u.getId());
    }

}










































































































































































































































package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Pedido;
import com.xander.tienda.figuras.responseData.ResumenPedido;

import java.util.List;
import java.util.Map;

public interface ServicioPedidos {

    //gestion en administracion
    List<Pedido> obtenerPedidos();
    Pedido obtenerPedidoPorId(long id);
    void actualizarEstadoPedido(long id, String estado);

    //operaciones para la parte publica - ajax/api rest
    void procesarPaso1(String nombreCompleto, String direccion,
                       String provincia, String localidad,
                       String codigoPostal, String telefono, long idUsuario);
    void procesarPaso2(String titular, String numero,
                       String tipoTarjeta, String caducidad, long idUsuario);
    void procesarPaso3(String metodoEnvio, long idUsuario);
    ResumenPedido obtenerResumenPedido(long idUsuario);
    void confirmarPedido(long idUsuario);

    //para ver mis pedidos
    List<Map<String,Object>> obtenerPedidosUsuario(long idUsuario);

}












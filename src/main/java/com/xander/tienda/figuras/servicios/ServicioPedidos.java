package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Pedido;
import com.xander.tienda.figuras.responseData.ResumenPedido;

import java.util.List;

public interface ServicioPedidos {

    //gestion en administracion
    List<Pedido> obtenerPedidos();
    Pedido obtenerPedidoPorId(long id);
    void actualizarEstadoPedido(long id, String estado);

    //operaciones para la parte publica - ajax/api rest
    void procesarPaso1(String nombreCompleto, String direccion,
                       String provincia, long idUsuario);
    void procesarPaso2(String titular, String numero,
                       String tipoTarjeta, long idUsuario);
    ResumenPedido obtenerResumenPedido(long idUsuario);
    void confirmarPedido(long idUsuario);

}












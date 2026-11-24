package com.xander.tienda.figuras.servicios;

import java.util.List;
import java.util.Map;

public interface ServicioCarrito {

    void agregarProducto(long idUsuario, long idProducto, int cantidad);

    //cambia la cantidad de una figura del carrito, si es menor que 1 la quita
    void cambiarCantidad(long idUsuario, long idProducto, int cantidad);

    // operaciones que devuelven json:
    List<Map<String,Object>> obtenerProductosCarrito(long idUsuario);

}

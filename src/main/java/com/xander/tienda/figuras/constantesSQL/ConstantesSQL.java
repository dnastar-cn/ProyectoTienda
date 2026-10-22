package com.xander.tienda.figuras.constantesSQL;

public class ConstantesSQL {

    public final static String SQL_OBTENER_FIGURAS_JSON =
            "select tf.id, tf.titulo, tf.precio, tf.plataforma, c.nombre as categoria from TABLA_FIGURAS as tf, CATEGORIA as c where tf.categoria_id = c.id";

    public static final String SQL_OBTENER_FIGURA_JSON =
            "select tf.id, tf.titulo, tf.precio, tf.plataforma, tf.descripcion, c.nombre as categoria from TABLA_FIGURAS as tf, CATEGORIA as c where tf.categoria_id = c.id and tf.id = :id";

    public static final String SQL_OBTENER_PRODUCTOS_CARRITO_JSON =
            "select tf.id as figura_id, tf.precio as precio, " +
            "tf.titulo, pc.cantidad " +
            "from tabla_figuras as tf, producto_carrito as pc " +
            "where tf.id = pc.figura_id and pc.carrito_id = :carrito_id ";

    public static final String SQL_BORRAR_PRODUCTOS_CARRITO =
            "delete from producto_carrito where carrito_id = :carrito_id";
}

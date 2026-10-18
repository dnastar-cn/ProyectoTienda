package com.xander.tienda.figuras.constantesSQL;

public class ConstantesSQL {

    public final static String SQL_OBTENER_FIGURAS_JSON =
            "select tf.id, tf.titulo, tf.precio, tf.plataforma, c.nombre as categoria from TABLA_FIGURAS as tf, CATEGORIA as c where tf.categoria_id = c.id";

    public static final String SQL_OBTENER_FIGURA_JSON =
            "select tf.id, tf.titulo, tf.precio, tf.plataforma, tf.descripcion, c.nombre as categoria from TABLA_FIGURAS as tf, CATEGORIA as c where tf.categoria_id = c.id and tf.id = :id";
}

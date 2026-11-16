package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Usuario;

import java.util.List;

public interface ServicioUsuarios {

    void registrarUsuario(Usuario u);

    Usuario obtenerUsuarioPorEmailYpass(String email, String pass);

    Usuario obtenerUsuarioPorId(Long id);

    //gestion en administracion
    List<Usuario> obtenerUsuarios();

    void borrarUsuario(Long id) throws Exception;

    //para mis datos
    Usuario actualizarDatosUsuario(Long id, String nombre, String email, String pass) throws Exception;
}

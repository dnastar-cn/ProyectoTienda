package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Usuario;

public interface ServicioUsuarios {

    void registrarUsuario(Usuario u);

    Usuario obtenerUsuarioPorEmailYpass(String email, String pass);

}

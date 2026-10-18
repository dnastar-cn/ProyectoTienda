package com.xander.tienda.figuras.controladorREST;

// necesario para registrar/identificar usuarios
// en la parte publica

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.xander.tienda.figuras.model.Usuario;
import com.xander.tienda.figuras.servicios.ServicioUsuarios;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("restUsuarios/")
public class UsuariosREST {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @RequestMapping("registrarUsuario")
    public String registrarUsuario(@RequestParam Map<String,Object> formData){
        System.out.println("recibido form data: " + formData);
        //formData tiene la info del usuario a registrar
        //pues vamos a registrarlo
        //transformar el formData a Usuario
        //vamos a hacerlo usando una libreria llamada gson
        Gson gson = new Gson();
        JsonElement json = gson.toJsonTree(formData);
        Usuario u = gson.fromJson(json, Usuario.class);

        servicioUsuarios.registrarUsuario(u);
        return "te has registrado correctamente, ya puedes identificarte";
    }

    //recordatorio: todos estos metodos se llaman desde el js de index.html
    @RequestMapping("identificarUsuario")
    public String identificarUsuario(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            HttpServletRequest request){
        //comprobar si hay un usuario con este email y pass en bd
        //si es asi, los datos son correctos
        //marcamos al usuario en cuestion que está identificado
        //usando una variable de sesion como hicimos con el interceptor
        //de administracion
        Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email,pass);
        String respuesta = "";
        if( u != null ){
            request.getSession().setAttribute("usuario_identificado", u );
            respuesta = "ok," + u.getNombre() ;
        }else{
            respuesta = "email o pass incorrectos";
        }
        return respuesta;
    }

}

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("restUsuarios/")
public class UsuariosREST {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @RequestMapping("registrarUsuario")
    public String registrarUsuario(@RequestParam Map<String,Object> formData,
                                   MultipartHttpServletRequest multipartRequest){
        System.out.println("recibido form data: " + formData);
        //formData tiene la info del usuario a registrar
        //pues vamos a registrarlo
        //transformar el formData a Usuario
        //vamos a hacerlo usando una libreria llamada gson
        Gson gson = new Gson();
        JsonElement json = gson.toJsonTree(formData);
        Usuario u = gson.fromJson(json, Usuario.class);
        //extraigo del form data la imagen para asignarla al avatar del usuario
        try {
            //el avatar es opcional, si no viene no hago nada
            if( multipartRequest.getFile("avatar") != null ){
                u.setAvatar(multipartRequest.getFile("avatar").getBytes());
            }
            servicioUsuarios.registrarUsuario(u);
        } catch (Exception e) {
            //le devuelvo al js el motivo, por ejemplo email repetido
            return e.getMessage();
        }
        return "ok";
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
            respuesta = "ok," + u.getNombre() + "," + u.getId() ;
        }else{
            respuesta = "email o pass incorrectos";
        }
        return respuesta;
    }

    @RequestMapping("cerrarSesion")
    public String cerrarSesion(HttpServletRequest request){
        request.getSession().invalidate();
        return "ok";
    }

    //para mis datos, devuelve los datos del usuario identificado
    //la pass no la mandamos al js por seguridad
    @RequestMapping("misDatos")
    public Map<String,Object> misDatos(HttpServletRequest request){
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        Map<String,Object> datos = new HashMap<>();
        datos.put("id", u.getId());
        datos.put("nombre", u.getNombre());
        datos.put("email", u.getEmail());
        return datos;
    }

    //guarda los cambios que hace el usuario en mis datos
    @RequestMapping("actualizarMisDatos")
    public String actualizarMisDatos(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam(name = "pass", defaultValue = "") String pass,
            HttpServletRequest request){
        Usuario u = (Usuario)
                request.getSession().getAttribute("usuario_identificado");
        try {
            Usuario actualizado = servicioUsuarios.actualizarDatosUsuario(u.getId(), nombre, email, pass);
            //actualizo tambien el usuario de la sesion
            request.getSession().setAttribute("usuario_identificado", actualizado);
            return "ok," + actualizado.getNombre();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

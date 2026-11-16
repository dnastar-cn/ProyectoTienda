package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.constantesSQL.ConstantesSQL;
import com.xander.tienda.figuras.model.Carrito;
import com.xander.tienda.figuras.model.Usuario;
import com.xander.tienda.figuras.servicios.ServicioUsuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioUsuariosImplJPA implements ServicioUsuarios {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void registrarUsuario(Usuario u) {
        entityManager.persist(u);
    }

    @Override
    public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
        Query query =
                entityManager.createQuery(
                "select u from Usuario u where u.email = :email and u.pass = :pass");
        //la anterior consulta de jpa seria como la sql:
        // select * from usuario where email = ? and pass = ?
        query.setParameter("email", email);//asi asigno el :email
        query.setParameter("pass", pass); // asi asigno el :pass
        List<Usuario> res = query.getResultList();

        if( res.size() == 0 ){
            return null;
        }
        return res.get(0);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return entityManager.createQuery("select u from Usuario u order by u.id").getResultList();
    }

    @Override
    public void borrarUsuario(Long id) throws Exception {
        Usuario u = entityManager.find(Usuario.class, id);
        if( u == null ){
            throw new Exception("el usuario a borrar no existe");
        }
        //si tiene pedidos no lo borro para no perder los pedidos
        long totalPedidos = (long) entityManager.createQuery(
                "select count(p) from Pedido p where p.usuario.id = :idUsuario")
                .setParameter("idUsuario", id)
                .getSingleResult();
        if( totalPedidos > 0 ){
            throw new Exception("no se puede borrar a " + u.getNombre()
                    + " porque tiene " + totalPedidos + " pedidos");
        }
        //antes de borrar el usuario hay que borrar su carrito
        //y los productos del carrito porque estan asociados
        List<Carrito> carritos = entityManager.createQuery(
                "select c from Carrito c where c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", id)
                .getResultList();
        for( Carrito c : carritos ){
            entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO)
                    .setParameter("carrito_id", c.getId())
                    .executeUpdate();
            entityManager.remove(c);
        }
        entityManager.remove(u);
    }

    @Override
    public Usuario actualizarDatosUsuario(Long id, String nombre, String email, String pass) throws Exception {
        Usuario u = entityManager.find(Usuario.class, id);
        if( u == null ){
            throw new Exception("el usuario no existe");
        }
        if( nombre == null || nombre.trim().length() < 2 ){
            throw new Exception("el nombre debe tener al menos 2 caracteres");
        }
        //el email es unico, miro que no lo tenga otro usuario
        long otrosConEseEmail = (long) entityManager.createQuery(
                "select count(u) from Usuario u where u.email = :email and u.id <> :id")
                .setParameter("email", email)
                .setParameter("id", id)
                .getSingleResult();
        if( otrosConEseEmail > 0 ){
            throw new Exception("ese email ya lo usa otro usuario");
        }
        u.setNombre(nombre.trim());
        u.setEmail(email);
        //si deja la pass vacia se queda la que tenia
        if( pass != null && !pass.isEmpty() ){
            u.setPass(pass);
        }
        return entityManager.merge(u);
    }
}

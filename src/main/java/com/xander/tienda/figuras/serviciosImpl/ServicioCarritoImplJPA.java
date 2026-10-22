package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.constantesSQL.ConstantesSQL;
import com.xander.tienda.figuras.model.Carrito;
import com.xander.tienda.figuras.model.Figura;
import com.xander.tienda.figuras.model.ProductoCarrito;
import com.xander.tienda.figuras.model.Usuario;
import com.xander.tienda.figuras.servicios.ServicioCarrito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioCarritoImplJPA implements ServicioCarrito {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void agregarProducto(long idUsuario, long idProducto, int cantidad) {
        Usuario uBaseDatos =
                entityManager.find(Usuario.class, idUsuario);
        //esto da problemas
        //Carrito c = uBaseDatos.getCarrito();
        //esto es mas seguro:
        Carrito c = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();


        if ( c == null ){
            //si el usuario no tiene un carrito asociado, lo creamos
            // y lo guardamos en BD
            c = new Carrito();
            c.setUsuario(uBaseDatos);
            entityManager.persist(c);
        }
        //ver si el producto que se quiere agregar ya esta en el carrito
        //si lo esta, hay que incrementar su cantidad
        boolean productoEnCarrito = false;
        for(ProductoCarrito pc : c.getProductosCarrito()){
            if(pc.getFigura().getId() == idProducto){
                //el producto ya esta en el carrito
                productoEnCarrito = true;
                pc.setCantidad(pc.getCantidad() + cantidad);
                entityManager.merge(pc);
            }
        }
        //si el producto no esta en el carrito, creamos un nuevo ProductoCarrito
        if( ! productoEnCarrito ){
            ProductoCarrito pc = new ProductoCarrito();
            pc.setCarrito(c);
            pc.setFigura( entityManager.find(Figura.class, idProducto) );
            pc.setCantidad(cantidad);
            entityManager.persist(pc);
        }
    }

    @Override
    public List<Map<String, Object>> obtenerProductosCarrito(long idUsuario) {
        //sacar los productos del carrito a partir de la id de usuario
        // OBTENER EL CARRITO ASI AHORA MISMO NO ES FIABLE:
//        Usuario u = entityManager.find(Usuario.class, idUsuario)
//         Carrito c = u.getCarrito();
        // ESTO MEJOR:
        Carrito c = entityManager.createQuery(
                "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        List<Map<String,Object>> res = null;
        if(c != null){
            Query query = entityManager.createNativeQuery(
                    ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO_JSON
            );
            query.setParameter("carrito_id", c.getId());
            //procesar la query
            NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
            nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            res = nativeQuery.getResultList();
        }
        return res;
    }
}

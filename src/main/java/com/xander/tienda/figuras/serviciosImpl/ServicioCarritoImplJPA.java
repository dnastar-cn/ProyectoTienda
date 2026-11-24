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

import java.util.ArrayList;
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
        List<Carrito> resCarrito = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();

        Carrito c = null;
        if (resCarrito.size() == 0) {
            //el usuario no tiene carrito: lo creamos y lo guardamos en BD
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(uBaseDatos);
            entityManager.persist(nuevoCarrito);
            c = nuevoCarrito;
        } else {
            //ya tiene carrito: usamos el que hay
            c = resCarrito.get(0);
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
    public void cambiarCantidad(long idUsuario, long idProducto, int cantidad) {
        //busco esa figura en el carrito de ese usuario
        List<ProductoCarrito> res = entityManager.createQuery(
                "select pc from ProductoCarrito pc where pc.carrito.usuario.id = :idUsuario and pc.figura.id = :idProducto",
                ProductoCarrito.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("idProducto", idProducto)
                .getResultList();
        for( ProductoCarrito pc : res ){
            if( cantidad < 1 ){
                entityManager.remove(pc); //borrar producto
            }else{
                pc.setCantidad(cantidad);
            }
        }
    }

    @Override
    public List<Map<String, Object>> obtenerProductosCarrito(long idUsuario) {
        //sacar los productos del carrito a partir de la id de usuario
        // OBTENER EL CARRITO ASI AHORA MISMO NO ES FIABLE:
//        Usuario u = entityManager.find(Usuario.class, idUsuario)
//         Carrito c = u.getCarrito();
        // ESTO MEJOR:

        //esto peta cuando un usuario sin ningun producto añadido le da a carrito por el singleresult,
        //porque abre el carrito sin haber añadido nada
//        Carrito c = entityManager.createQuery(
//                "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
//                .setParameter("idUsuario", idUsuario)
//                .getSingleResult();
//        List<Map<String,Object>> res = null;
//        if(c != null){
//            Query query = entityManager.createNativeQuery(
//                    ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO_JSON
//            );
//            query.setParameter("carrito_id", c.getId());
//            //procesar la query
//            NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
//            nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
//            res = nativeQuery.getResultList();
//        }
//        return res;

        //solucion: igual que en agregarProducto, uso getResultList()
        //que devuelve una lista (vacia si no hay carrito) en vez de dar error
        List<Carrito> resCarrito = entityManager.createQuery(
                        "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();

        //si la lista esta vacia, el usuario aun no tiene carrito:
        //devuelvo una lista vacia y el js mostrara "Tu carrito esta vacio"
        List<Map<String,Object>> res = new ArrayList<>();

        if(resCarrito.size() > 0){
            //el usuario si tiene carrito: es el primero (y unico) de la lista
            Carrito c = resCarrito.get(0);
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

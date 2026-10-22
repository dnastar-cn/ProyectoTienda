package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.constantesPedidos.EstadosPedido;
import com.xander.tienda.figuras.constantesSQL.ConstantesSQL;
import com.xander.tienda.figuras.model.*;
import com.xander.tienda.figuras.responseData.ResumenPedido;
import com.xander.tienda.figuras.servicios.ServicioCarrito;
import com.xander.tienda.figuras.servicios.ServicioPedidos;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioPedidosImplJPA implements ServicioPedidos {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServicioCarrito servicioCarrito;

    @Override
    public List<Pedido> obtenerPedidos() {
        return List.of();
    }

    @Override
    public Pedido obtenerPedidoPorId(long id) {
        return null;
    }

    @Override
    public void actualizarEstadoPedido(long id, String estado) {

    }

    // operaciones con la parte publica

    @Override
    public void procesarPaso1(String nombreCompleto, String direccion, String provincia, long idUsuario) {
        //cada usuario podra tener como maximo un solo
        //pedido en estado "en proceso"
        //ese pedido es donde rellenaremos los datos de envio,
        //pago y demas, si hubiera
        //si el usuario finaliza el pedido, el estado del pedido
        //pasa a ser "completado"
        //puede haber tantos pedidos "completado" como se quiera
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setNombreCompleto(nombreCompleto);
        p.setDireccion(direccion);
        p.setProvincia(provincia);
        entityManager.merge(p);//actualiza o registra
    }

    @Override
    public void procesarPaso2(String titular, String numero, String tipoTarjeta, long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setTitularTarjeta(titular);
        p.setNumeroTarjeta(numero);
        p.setTipoTarjeta(tipoTarjeta);
        entityManager.merge(p);
    }

    @Override
    public ResumenPedido obtenerResumenPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        ResumenPedido resumen = new ResumenPedido();
        resumen.setNombreCompleto(p.getNombreCompleto());
        resumen.setDireccion(p.getDireccion());
        resumen.setProvincia(p.getProvincia());
        resumen.setTipoTarjeta(p.getTipoTarjeta());
        resumen.setTitularTarjeta(p.getTitularTarjeta());
        resumen.setNumeroTarjeta(p.getNumeroTarjeta());
        resumen.setFiguras(
                servicioCarrito.obtenerProductosCarrito(idUsuario));
        return resumen;
    }

    @Override
    public void confirmarPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        Carrito c = entityManager.createQuery(
                        "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        //pasar los productos del carrito al pedido
        if( c != null && c.getProductosCarrito().size() > 0){
            for(ProductoCarrito pc : c.getProductosCarrito()){
                ProductoPedido pp = new ProductoPedido();
                pp.setCantidad(pc.getCantidad());
                pp.setFigura(pc.getFigura());
                p.getProductosPedido().add(pp);
                pp.setPedido(p);
                entityManager.persist(pp);
            }
        }
        //borrar los productos del carrito del usuario
        entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO).setParameter("carrito_id", c.getId()).executeUpdate();
        p.setEstado(EstadosPedido.Completado.toString());
        entityManager.merge(p);
    }

    private Pedido obtenerPedidoActual(long idUsuario) {
        //vamos a pedir del usuario actual todos los pedidos "en proceso"
        Query consulta =
                entityManager.createQuery(
                        "select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id"
                ).setParameter("estado", EstadosPedido.EnProceso.name())
                        .setParameter("usuario_id", idUsuario);
        List<Pedido> resultadoConsulta = consulta.getResultList();
        if(resultadoConsulta.size()==1){
            return resultadoConsulta.get(0);
        }
        //si el metodo sigue para adelante hay que crear un nuevo
        //pedido con el estado en proceso
        Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);
        Pedido pedidoEnProceso = new Pedido();
        pedidoEnProceso.setUsuario(uBaseDatos);
        pedidoEnProceso.setEstado(EstadosPedido.EnProceso.name());
        return pedidoEnProceso;
    }
}











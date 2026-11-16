package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.model.*;
import com.xander.tienda.figuras.servicios.ServicioPedidos;
import com.xander.tienda.figuras.servicios.ServicioSetUp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
@Transactional
public class ServicioSetUpImplJPA implements ServicioSetUp {

    @PersistenceContext
    private EntityManager entityManager;

    //en el caso de los pedidos, aprovechamos
    //y testeamos el servicio de pedidos
    @Autowired
    private ServicioPedidos servicioPedidos;

    @Override
    public void prepararRegistrosIniciales() {
        // preparar unos registros si la bd esta vacia
        Configuracion configuracion = null;
        // la idea presentada se basa que va a haber un solo registro
        // en la tabla de Configuracion, ese registro nos indicara
        // si la base de datos ya tiene sus registros iniciales
        // como por ejemplo: productos, categorias, usuarios, pedidos etc.

        //en sql seria como: select * from configuracion as c
        try {
            configuracion =
                    (Configuracion)
                            entityManager.createQuery(
                                            "select c from Configuracion c").
                                    getSingleResult();
        }catch (NoResultException e){
            System.out.println(
                    "no se encontro un unico registro en la tabla de Configuracion");
        }
        if( configuracion == null || ! configuracion.isRegistrosIniciales() ){
            //hago todos los registros iniciales
            //categorias
            Categoria cPokemon = new Categoria("Pokémon", "Figuras del mundo Pokémon");
            Categoria cDragonBall = new Categoria("Dragon Ball", "Figuras de Dragon Ball");
            Categoria cMarvel = new Categoria("Marvel", "Superhéroes y villanos de Marvel");
            Categoria cOnePiece = new Categoria("One Piece", "Piratas de One Piece");
            entityManager.persist(cPokemon);
            entityManager.persist(cDragonBall);
            entityManager.persist(cMarvel);
            entityManager.persist(cOnePiece);
            //productos
            //el primer registro tendra id 1
            Figura fPikachu = new Figura("Pikachu", "La mascota eléctrica más famosa", 19.95, "Pokémon Center");
            fPikachu.setSku("FIG-0001");
            fPikachu.setAltura(12);
            fPikachu.setMaterial("PVC");
            fPikachu.setCategoria(cPokemon);
            fPikachu.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/1.jpg"));
            entityManager.persist(fPikachu);

            Figura fCharizard = new Figura("Charizard", "Dragón de fuego en pose de ataque", 34.50, "First 4 Figures");
            fCharizard.setSku("FIG-0002");
            fCharizard.setAltura(30);
            fCharizard.setMaterial("Poliresina");
            fCharizard.setCategoria(cPokemon);
            fCharizard.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/2.jpg"));
            entityManager.persist(fCharizard);

            Figura fGoku = new Figura("Goku Super Saiyan", "Goku transformado lanzando un Kamehameha", 39.90, "Bandai S.H.Figuarts");
            fGoku.setSku("FIG-0003");
            fGoku.setAltura(14);
            fGoku.setMaterial("PVC y ABS");
            fGoku.setCategoria(cDragonBall);
            fGoku.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/3.jpg"));
            entityManager.persist(fGoku);

            Figura fVegeta = new Figura("Vegeta", "El príncipe de los Saiyans", 37.00, "Bandai S.H.Figuarts");
            fVegeta.setSku("FIG-0004");
            fVegeta.setAltura(14);
            fVegeta.setMaterial("PVC y ABS");
            fVegeta.setCategoria(cDragonBall);
            fVegeta.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/4.jpg"));
            entityManager.persist(fVegeta);

            Figura fSpiderman = new Figura("Spider-Man", "El trepamuros colgado de su telaraña", 29.99, "Bandai S.H.Figuarts");
            fSpiderman.setSku("FIG-0005");
            fSpiderman.setAltura(15);
            fSpiderman.setMaterial("PVC y ABS");
            fSpiderman.setCategoria(cMarvel);
            fSpiderman.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/5.jpg"));
            entityManager.persist(fSpiderman);

            Figura fIronMan = new Figura("Iron Man", "Tony Stark con la armadura Mark 85", 45.00, "Bandai S.H.Figuarts");
            fIronMan.setSku("FIG-0006");
            fIronMan.setAltura(15);
            fIronMan.setMaterial("PVC y ABS");
            fIronMan.setCategoria(cMarvel);
            fIronMan.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/6.jpg"));
            entityManager.persist(fIronMan);

            Figura fLuffy = new Figura("Monkey D Luffy Gear 5", "Luffy en su forma más poderosa", 49.95, "Bandai S.H.Figuarts");
            fLuffy.setSku("FIG-0007");
            fLuffy.setAltura(15);
            fLuffy.setMaterial("PVC y ABS");
            fLuffy.setCategoria(cOnePiece);
            fLuffy.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/7.jpg"));
            entityManager.persist(fLuffy);

            Figura fZoro = new Figura("Roronoa Zoro", "El espadachín de las tres espadas", 42.00, "Bandai S.H.Figuarts");
            fZoro.setSku("FIG-0008");
            fZoro.setAltura(15);
            fZoro.setMaterial("PVC y ABS");
            fZoro.setCategoria(cOnePiece);
            fZoro.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/8.jpg"));
            entityManager.persist(fZoro);

            Figura fMewtwo = new Figura("Mewtwo", "El Pokémon legendario creado en un laboratorio", 32.00, "Jazwares");
            fMewtwo.setSku("FIG-0009");
            fMewtwo.setAltura(11);
            fMewtwo.setMaterial("Plástico");
            fMewtwo.setCategoria(cPokemon);
            fMewtwo.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/9.jpg"));
            entityManager.persist(fMewtwo);

            Figura fThanos = new Figura("Thanos", "El titán loco con el guantelete del infinito", 55.00, "Bandai S.H.Figuarts");
            fThanos.setSku("FIG-0010");
            fThanos.setAltura(19);
            fThanos.setMaterial("PVC y ABS");
            fThanos.setCategoria(cMarvel);
            fThanos.setImagenPortada(leerImagenInicial(
                    "http://localhost:8080/imagenes_productos_iniciales/10.jpg"));
            entityManager.persist(fThanos);

            // vamos a registrar unos usuarios y algun carrito
            Usuario u1 = new Usuario();
            u1.setNombre("Pepe");
            u1.setPass("123");
            u1.setEmail("pepe@gmail.com");
            entityManager.persist(u1);

            //vamos a preparar un carrito
            Carrito c1 = new Carrito();
            c1.setUsuario(u1);
            entityManager.persist(c1);

            ProductoCarrito pc1 = new ProductoCarrito();
            pc1.setCarrito(c1);
            pc1.setFigura(fPikachu);
            pc1.setCantidad(3);
            c1.getProductosCarrito().add(pc1);
            entityManager.persist(pc1);

            ProductoCarrito pc2 = new ProductoCarrito();
            pc2.setCarrito(c1);
            pc2.setFigura(fGoku);
            pc2.setCantidad(1);
            c1.getProductosCarrito().add(pc2);
            entityManager.persist(pc2);

            //vamos tambien a registrar un pedido
            //a partir del carrito
            servicioPedidos.procesarPaso1(
                    "Pedro Fernandez","Arguelles 12",
                    "Madrid", "Madrid", "28008", "600123456", u1.getId());
            servicioPedidos.procesarPaso2(
                    "Pedro Fernandez Del Soto","12345678912",
                    "VISA", "12/28", u1.getId()
            );
            servicioPedidos.procesarPaso3("Estándar", u1.getId());
            servicioPedidos.confirmarPedido(u1.getId());
            //ya tenemos un pedido en bd :)

            //registrar 100 productos de prueba,para probar paginacion
            String titulo = "Figura ";
            double precio = 1;
            String descripcion = "Producto de prueba para test";
            for(int i=0;i<100;i++){
                Figura fNuevo = new Figura();
                fNuevo.setTitulo(titulo + i);
                fNuevo.setFabricante("prueba");
                fNuevo.setAltura(10);
                fNuevo.setMaterial("prueba");
                fNuevo.setPrecio(precio);
                fNuevo.setDescripcion(descripcion + " " + i);
                fNuevo.setCategoria(cDragonBall);
                fNuevo.setImagenPortada(leerImagenInicial(
                        "http://localhost:8080/imagenes_productos_iniciales/default.jpg"));

                entityManager.persist(fNuevo);
            }


            //una vez realizados los registros iniciales
            //guardo en registro en la tabla Configuracion
            // indicando que todo esta ok
            configuracion = new Configuracion();
            configuracion.setRegistrosIniciales(true);
            entityManager.persist(configuracion);
        }//end if
    }//end prepararRegistrosIniciales

    private byte[] leerImagenInicial(String ruta_imagen){
        byte[] info = null;
        try {
            URL url = new URL(ruta_imagen);
            info = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}//end class

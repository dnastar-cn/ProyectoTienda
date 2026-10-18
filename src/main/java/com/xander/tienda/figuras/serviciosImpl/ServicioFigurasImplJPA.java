package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.constantesSQL.ConstantesSQL;
import com.xander.tienda.figuras.model.Categoria;
import com.xander.tienda.figuras.model.Figura;
import com.xander.tienda.figuras.servicios.ServicioFiguras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service //marcas que la clase actua como servicio de datos
@Transactional  //los metodos de la clase se ejecutan como unica transaccion
//Transaccion
public class ServicioFigurasImplJPA implements ServicioFiguras {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarFigura(Figura f) {
        //el figura ahora mismo tiene la foto subida
        //pero en imagen portada no tiene nada
        try {
            f.setImagenPortada(f.getFotoSubida().getBytes());
        } catch (IOException e) {

            System.out.println("no pude procesar la foto subida");

            throw new RuntimeException(e);

        }
        // de la idCategoria recibida, hay que asignar la categoria de la base datos
        // idCategoria es la seleccionada en el desplegable de categorias
        Categoria c = entityManager.find(Categoria.class, f.getIdCategoria());
        f.setCategoria(c);
        entityManager.persist(f);
    }

    @Override
    public List<Figura> obtenerFiguras() {
        //lanzar JPQL
        // la consulta lanzada equivale al sql : select * from tabla_figuras as f
        return entityManager.createQuery("select f from Figura f").getResultList();
    }

    @Override
    public Figura obtenerFiguraPorId(long id) {
        return entityManager.find(Figura.class, id);
    }

    @Override
    public void borrarFigura(long id) throws Exception{
        //en JPA la forma comun de borrar un registro es pedirlo comprobar que existe y luego borrarlo
        Figura figuraAborrar = entityManager.find(Figura.class, id);
        if( figuraAborrar != null){
            entityManager.remove(figuraAborrar);
        }else{
            throw new Exception("id no encontrada");
        }

    }

    @Override
    public void guardarCambiosFigura(Figura figuraEditar) throws Exception {
        //usando la id seleccionada en el desplegable de categorias
        //tengo que asociar la categoria correspondiente a la figura
        Categoria c = entityManager.find(Categoria.class,
                figuraEditar.getIdCategoria());
        figuraEditar.setCategoria(c);

        //para no perder la foto hay que detectar si el admin le ha asinado al videojuego una nueva foto
        //en caso contrario le vamos a cargar la foto anterior que tuviese
        if(figuraEditar.getFotoSubida().getSize() == 0){
            Figura original = entityManager.find(Figura.class, figuraEditar.getId());
            figuraEditar.setImagenPortada(original.getImagenPortada());
        }else {
            figuraEditar.setImagenPortada(figuraEditar.getFotoSubida().getBytes());
        }
        //merge actualiza el registro si ya existe
        //si no existe lo crea
        entityManager.merge(figuraEditar);
    }

    @Override
    public List<Map<String, Object>> obtenerJSONfiguras() {
        // con JPA hemos asociado categoria a producto
        // problema: el json recibido en portada no es correcto
        // solucion: dejar claro en una consulta a base de datos que informacion
        // va a formar el json que necesitamos en portada

        //Map de String object es un conjunto de elementos
        // donde cada elemento tiene un string y un Object
        // un Object en java es cualquier tipo de dato
        // Object es la clase padre de todas las clases en java
        //para tener el control total, vamos a usar sql
        // asi preparo una sql para JPA
        Query consulta =
                entityManager.createNativeQuery(
                        ConstantesSQL.SQL_OBTENER_FIGURAS_JSON
                );
        //usar la consulta directamente es mucho mas lioso
        //que lo siguiente
        NativeQueryImpl nativeQuery = (NativeQueryImpl) consulta;
        nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return nativeQuery.getResultList();
    }

    @Override
    public Map<String, Object> obtenerJSONFiguraPorId(Long id) {
        return (Map<String, Object>) ((NativeQueryImpl)entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_FIGURA_JSON)).setParameter("id",id).setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE).getResultList().get(0);
    }

}

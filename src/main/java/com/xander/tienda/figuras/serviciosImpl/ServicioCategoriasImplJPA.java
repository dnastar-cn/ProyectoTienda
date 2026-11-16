package com.xander.tienda.figuras.serviciosImpl;

import com.xander.tienda.figuras.model.Categoria;
import com.xander.tienda.figuras.servicios.ServicioCategorias;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioCategoriasImplJPA implements ServicioCategorias {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Categoria> obtenerCategorias() {
        return entityManager.createQuery("select c from Categoria c").getResultList();
    }

    @Override
    public Categoria obtenerCategoriaPorId(int id) {
        return entityManager.find(Categoria.class, id);
    }

    @Override
    public void registrarCategoria(Categoria c) {
        entityManager.persist(c);
    }

    @Override
    public void guardarCambiosCategoria(Categoria c) {
        entityManager.merge(c);//actualiza la categoria que ya existe
    }

    @Override
    public void borrarCategoria(int id) throws Exception {
        Categoria c = entityManager.find(Categoria.class, id);
        if( c == null ){
            throw new Exception("la categoria a borrar no existe");
        }
        //si la categoria tiene figuras no se puede borrar
        //porque las figuras se quedarian sin categoria
        if( c.getFiguras().size() > 0 ){
            throw new Exception("no se puede borrar la categoria "
                    + c.getNombre() + " porque tiene " + c.getFiguras().size() + " figuras");
        }
        entityManager.remove(c);
    }
}

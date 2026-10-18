package com.xander.tienda.figuras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {

    private String nombre;
    private String descripcion;

    //voy a especificar la asociacion de Categoria
    //con Figura
    // asociaciones en hibernate/jpa:
    // OneToOne: 1 cat solo puede ser asignada a 1 prod
    // OneToMany: 1 cat puede estar asociada a muchos productos
    // ManyToOne: muchas cat pueden estar asociadas a 1 prod
    // ManyToMany: muchas cat pueden ser asignadas a muchos productos

    @OneToMany(mappedBy = "categoria")
    private List<Figura> figuras = new ArrayList<Figura>();

    @Id
    @GeneratedValue
    private int id;

    public Categoria() {
    }

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Figura> getFiguras() {
        return figuras;
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras = figuras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

package com.xander.tienda.figuras.model;

import jakarta.persistence.*;

@Entity
public class ProductoCarrito {

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Figura figura;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @Id
    @GeneratedValue
    private Long id;

    //campos extra
    private int cantidad;

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

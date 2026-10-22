package com.xander.tienda.figuras.model;

import jakarta.persistence.*;

@Entity
public class ProductoPedido {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Figura figura;

    private int cantidad;

    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

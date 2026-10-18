package com.xander.tienda.figuras.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tabla_figuras")
public class Figura {

    @Size(min=3, max=40, message = "Titulo debe tener entre 3 y 40 caracteres")
    @NotEmpty(message = "debes insertar un titulo")
    //una expresion regular es una forma de indicar que queremos o no en un campo de entrada de usuario
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÑüÜ\\-:'& 0-9]+",
    message = "titulo solo puede contener letras numeros y espacios"
    )

    private String titulo;

    @Min(value = 1, message = "Precio minimo 1 euro ")
    @Max(value = 99999, message = "El precio maximo es este 99999 euros ")
    private double precio;

    @NotNull(message = "debes indicar un precio")
    private String plataforma;
    private String descripcion;

    private String sku; //stock keeping unit //numero de control del producto

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //este campo va a ser solo para gestionar la subida de archivos
    //el input type="file"
    //solo para recibir facilmente la imaggen del form
    // @transiente es para decir que el campo sea ignorado de cara a base de datos
    @Transient
    private MultipartFile fotoSubida;

    //siguiente campo es para almacenar la imagen en base de datos
    @Lob  // para tipos de columna p. ej blob clob , para guardar imagenes/archivos/arrays de bytes
    private byte[] imagenPortada;

    //En asociaciones podemos especificar dos opciones para
    // fetch
    // LAZY (vago) - Cuando se pida una figura, no se va a pedir ningun dato de categoria a la base de datos
    // EAGER (ansioso) - Cuando se pida una figura, por defecto se pide a la base de datos la informacion de su categoria
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Categoria categoria;

    //para gestionar la id del form y para otros usos
    @Transient
    private int idCategoria;

    public Figura(){

    }

    public Figura(String titulo, double precio, String plataforma, String descripcion, long id) {

        //una forma muy comoda para hacer validaciones
        //es usar un standar que se llama JSR 303
        //se basa en poner anptaciones de validacion a cada campo




        this.titulo = titulo;
        this.precio = precio;
        this.plataforma = plataforma;
        this.descripcion = descripcion;
        this.id = id;
    }

    public Figura(String titulo, String descripcion, double precio, String plataforma) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.plataforma = plataforma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(byte[] imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public MultipartFile getFotoSubida() {
        return fotoSubida;
    }

    public void setFotoSubida(MultipartFile fotoSubida) {
        this.fotoSubida = fotoSubida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "figura{" +
                "titulo='" + titulo + '\'' +
                ", precio=" + precio +
                ", plataforma='" + plataforma + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}

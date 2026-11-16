package com.xander.tienda.figuras.responseData;

import java.util.List;
import java.util.Map;

//esta es la informacion que recibira el usuario para confirmar el pedido
public class ResumenPedido {

    //datos paso 1:
    private String nombreCompleto;
    private String direccion;
    private String provincia;
    private String localidad;
    private String codigoPostal;
    private String telefono;

    //datos paso 2:
    private String titularTarjeta;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String caducidadTarjeta;

    //datos paso 3:
    private String metodoEnvio;

    private List<Map<String,Object>> figuras;

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCaducidadTarjeta() {
        return caducidadTarjeta;
    }

    public void setCaducidadTarjeta(String caducidadTarjeta) {
        this.caducidadTarjeta = caducidadTarjeta;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public List<Map<String, Object>> getFiguras() {
        return figuras;
    }

    public void setFiguras(List<Map<String, Object>> figuras) {
        this.figuras = figuras;
    }
}

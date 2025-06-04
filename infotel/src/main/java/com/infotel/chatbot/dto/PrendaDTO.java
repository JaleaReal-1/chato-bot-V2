package com.infotel.chatbot.dto;

public class PrendaDTO {
    private String tipo;
    private String color;
    private String descripcion;
    private String imagenUrl; // ➕ Nuevo campo



    //NUEVOS CAMPOS:
    private String nombre;
    private Double precio;
    private String material;
    private String imagen;

    public PrendaDTO() {}

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public Double getPrecio() {return precio;}
    public void setPrecio(Double precio) {this.precio = precio;}

    public String getMaterial() {return material;}
    public void setMaterial(String material) {this.material = material;}
}

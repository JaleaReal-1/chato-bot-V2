package com.infotel.chatbot.dto;

public class CompraRequestDTO {
    private Long conjuntoId;
    private String nombreCliente;
    private String telefono;
    private String direccion;
    private String tipoEnvio;

    // Getters y Setters
    public Long getConjuntoId() { return conjuntoId; }
    public void setConjuntoId(Long conjuntoId) { this.conjuntoId = conjuntoId; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }
}

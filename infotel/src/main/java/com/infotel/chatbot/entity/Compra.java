package com.infotel.chatbot.entity;

import jakarta.persistence.*;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private String telefono;
    private String direccion;
    private String tipoEnvio; // opcional: "delivery", "recoger en tienda", etc.

    @OneToOne
    private Conjunto conjunto;

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }

    public Conjunto getConjunto() { return conjunto; }
    public void setConjunto(Conjunto conjunto) { this.conjunto = conjunto; }
}

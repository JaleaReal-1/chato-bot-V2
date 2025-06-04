package com.infotel.chatbot.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Conjunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteId; // opcionalmente puedes usarlo más adelante

    @ManyToMany
    private List<Prenda> prendas;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public List<Prenda> getPrendas() { return prendas; }
    public void setPrendas(List<Prenda> prendas) { this.prendas = prendas; }
}

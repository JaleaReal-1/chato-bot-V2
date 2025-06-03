// com.infotel.chatbot.dto.RespuestaChatDTO.java
package com.infotel.chatbot.dto;

import java.util.List;

public class RespuestaChatDTO {
    private String mensaje;
    private List<PrendaDTO> prendas; // ← Cambiado a PrendaDTO

    public RespuestaChatDTO() {
    }

    public RespuestaChatDTO(String mensaje, List<PrendaDTO> prendas) {
        this.mensaje = mensaje;
        this.prendas = prendas;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<PrendaDTO> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<PrendaDTO> prendas) {
        this.prendas = prendas;
    }
}

package com.infotel.chatbot.dto;

import java.util.List;

public class GuardarConjuntoRequest {
    private List<Long> prendaIds;

    public List<Long> getPrendaIds() { return prendaIds; }
    public void setPrendaIds(List<Long> prendaIds) { this.prendaIds = prendaIds; }
}

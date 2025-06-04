package com.infotel.chatbot.service;

import com.infotel.chatbot.dto.GuardarConjuntoRequest;
import com.infotel.chatbot.entity.Conjunto;
import com.infotel.chatbot.entity.Prenda;
import com.infotel.chatbot.repository.ConjuntoRepository;
import com.infotel.chatbot.repository.PrendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjuntoService {

    private final ConjuntoRepository conjuntoRepository;
    private final PrendaRepository prendaRepository;

    public ConjuntoService(ConjuntoRepository conjuntoRepository, PrendaRepository prendaRepository) {
        this.conjuntoRepository = conjuntoRepository;
        this.prendaRepository = prendaRepository;
    }

    public Long guardarConjunto(GuardarConjuntoRequest request) {
        List<Prenda> prendas = prendaRepository.findAllById(request.getPrendaIds());
        Conjunto conjunto = new Conjunto();
        conjunto.setPrendas(prendas);
        conjuntoRepository.save(conjunto);
        return conjunto.getId(); // Devolver ID del conjunto para redirigir
    }
}

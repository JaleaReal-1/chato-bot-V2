package com.infotel.chatbot.service;

import com.infotel.chatbot.dto.CompraRequestDTO;
import com.infotel.chatbot.entity.Compra;
import com.infotel.chatbot.entity.Conjunto;
import com.infotel.chatbot.repository.CompraRepository;
import com.infotel.chatbot.repository.ConjuntoRepository;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ConjuntoRepository conjuntoRepository;

    public CompraService(CompraRepository compraRepository, ConjuntoRepository conjuntoRepository) {
        this.compraRepository = compraRepository;
        this.conjuntoRepository = conjuntoRepository;
    }

    public Compra registrarCompra(CompraRequestDTO dto) {
        Conjunto conjunto = conjuntoRepository.findById(dto.getConjuntoId())
                .orElseThrow(() -> new IllegalArgumentException("Conjunto no encontrado"));

        Compra compra = new Compra();
        compra.setNombreCliente(dto.getNombreCliente());
        compra.setTelefono(dto.getTelefono());
        compra.setDireccion(dto.getDireccion());
        compra.setTipoEnvio(dto.getTipoEnvio());
        compra.setConjunto(conjunto);

        return compraRepository.save(compra);
    }
}

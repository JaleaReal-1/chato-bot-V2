package com.infotel.chatbot.service;

import com.infotel.chatbot.dto.PrendaDTO;
import com.infotel.chatbot.entity.Prenda;
import com.infotel.chatbot.repository.PrendaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrendaService {

    private final PrendaRepository prendaRepository;

    @Value("${server.port}")
    private String port;

    public PrendaService(PrendaRepository prendaRepository) {
        this.prendaRepository = prendaRepository;
    }

    public List<PrendaDTO> obtenerTodas() {
        String baseUrl = "http://localhost:" + port + "/modelos/";


        return prendaRepository.findAll().stream().map(prenda -> {
            PrendaDTO dto = new PrendaDTO();
            dto.setNombre(prenda.getNombre());
            dto.setTipo(prenda.getTipo());
            dto.setMaterial(prenda.getMaterial());
            dto.setColor(prenda.getColor());
            dto.setDescripcion(prenda.getDescripcion());
            dto.setImagenUrl(baseUrl + prenda.getImagenUrl());
            dto.setImagen(baseUrl + prenda.getImagen());


            // nuevos campos

            dto.setPrecio(prenda.getPrecio());


            return dto;
        }).collect(Collectors.toList());
    }
}


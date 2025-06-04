package com.infotel.chatbot.controller;

import com.infotel.chatbot.dto.PrendaDTO;
import com.infotel.chatbot.service.PrendaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prendas")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend local
public class PrendaController {

    private final PrendaService prendaService;

    public PrendaController(PrendaService prendaService) {
        this.prendaService = prendaService;
    }

    @GetMapping
    public List<PrendaDTO> obtenerTodas() {
        return prendaService.obtenerTodas();
    }
}

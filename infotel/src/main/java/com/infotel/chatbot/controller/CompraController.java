package com.infotel.chatbot.controller;

import com.infotel.chatbot.dto.CompraRequestDTO;
import com.infotel.chatbot.entity.Compra;
import com.infotel.chatbot.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Compra> registrarCompra(@RequestBody CompraRequestDTO dto) {
        Compra compra = compraService.registrarCompra(dto);
        return ResponseEntity.ok(compra);
    }
}

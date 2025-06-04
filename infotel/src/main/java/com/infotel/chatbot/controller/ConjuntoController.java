package com.infotel.chatbot.controller;

import com.infotel.chatbot.dto.GuardarConjuntoRequest;
import com.infotel.chatbot.service.ConjuntoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conjuntos")
@CrossOrigin(origins = "*")
public class ConjuntoController {

    private final ConjuntoService conjuntoService;

    public ConjuntoController(ConjuntoService conjuntoService) {
        this.conjuntoService = conjuntoService;
    }

    @PostMapping
    public ResponseEntity<Long> guardarConjunto(@RequestBody GuardarConjuntoRequest request) {
        Long idConjunto = conjuntoService.guardarConjunto(request);
        return ResponseEntity.ok(idConjunto); // El frontend usará este ID para redirigir
    }
}

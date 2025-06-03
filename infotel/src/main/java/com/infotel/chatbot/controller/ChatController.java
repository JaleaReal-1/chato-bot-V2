package com.infotel.chatbot.controller;

import com.infotel.chatbot.dto.RespuestaChatDTO;
import com.infotel.chatbot.entity.Prenda;
import com.infotel.chatbot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // <— aquí habilitas CORS para tu React
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<RespuestaChatDTO> chat(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        RespuestaChatDTO respuesta = chatService.enviarPromptYBuscarPrendas(prompt);
        return ResponseEntity.ok(respuesta);
    }

}

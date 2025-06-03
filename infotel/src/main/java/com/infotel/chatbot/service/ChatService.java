package com.infotel.chatbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotel.chatbot.dto.PrendaDTO;
import com.infotel.chatbot.dto.RespuestaChatDTO;
import com.infotel.chatbot.entity.Prenda;
import com.infotel.chatbot.repository.PrendaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class ChatService {

    private final WebClient webClient;
    private final PrendaRepository prendaRepository;

    // URL base para servir los archivos .glb desde src/main/resources/static/modelos/
    private static final String BASE_URL_PRENDAS = "http://localhost:8080/modelos/";

    public ChatService(WebClient.Builder webClientBuilder, PrendaRepository prendaRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.groq.com/openai/v1").build();
        this.prendaRepository = prendaRepository;
    }

    public RespuestaChatDTO enviarPromptYBuscarPrendas(String prompt) {
        String apiKey = "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";

        try {
            String promptMejorado = """
                Por favor, devuelve únicamente un JSON con una lista de prendas, 
                donde cada prenda tiene estos atributos: tipo, color y descripcion. 
                No agregues ningún texto adicional, solo el JSON. 
                Ejemplo de respuesta esperada:

                [
                  {"tipo": "camisa", "color": "azul", "descripcion": "casual"},
                  {"tipo": "pantalon", "color": "negro", "descripcion": "formal"}
                ]

                Prompt de usuario: """ + prompt;

            Map<String, Object> requestBody = Map.of(
                    "model", "llama3-8b-8192",
                    "messages", List.of(Map.of("role", "user", "content", promptMejorado))
            );

            String response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            String content = root.path("choices").get(0).path("message").path("content").asText().trim();

            if (!content.startsWith("[") && !content.startsWith("{")) {
                int startIndex = content.indexOf('[');
                int endIndex = content.lastIndexOf(']');
                if (startIndex >= 0 && endIndex > startIndex) {
                    content = content.substring(startIndex, endIndex + 1);
                } else {
                    return new RespuestaChatDTO("❌ No se encontró un formato válido de prendas.", List.of());
                }
            }

            List<PrendaDTO> prendasDTO;
            try {
                prendasDTO = mapper.readValue(content, new TypeReference<List<PrendaDTO>>() {});
            } catch (Exception e) {
                return new RespuestaChatDTO("❌ No se pudo interpretar correctamente la solicitud.", List.of());
            }

            Set<Prenda> resultados = new HashSet<>();
            for (PrendaDTO dto : prendasDTO) {
                List<Prenda> coincidencias = prendaRepository.findByTipoAndColorAndDescripcionContainingIgnoreCase(
                        dto.getTipo(), dto.getColor(), dto.getDescripcion());

                if (coincidencias.isEmpty())
                    coincidencias = prendaRepository.findByTipoAndColor(dto.getTipo(), dto.getColor());

                if (coincidencias.isEmpty())
                    coincidencias = prendaRepository.findByDescripcionContainingIgnoreCase(dto.getDescripcion());

                resultados.addAll(coincidencias);
            }

            List<Prenda> prendasFinales = new ArrayList<>(resultados);

            if (prendasFinales.isEmpty()) {
                return new RespuestaChatDTO("❌ No se encontraron prendas que coincidan con tu descripción.", List.of());
            }

            // 🔄 Aquí comienza la parte modificada: convertir Prenda → PrendaDTO (con URL completa)
            List<PrendaDTO> prendasDtoFinales = new ArrayList<>();
            for (Prenda prenda : prendasFinales) {
                PrendaDTO dto = new PrendaDTO();
                dto.setTipo(prenda.getTipo());
                dto.setColor(prenda.getColor());
                dto.setDescripcion(prenda.getDescripcion());
                // Armar la URL completa apuntando a src/main/resources/static/modelos/{archivo.glb}
                dto.setImagenUrl(BASE_URL_PRENDAS + prenda.getImagenUrl());
                prendasDtoFinales.add(dto);
            }
            // 🔄 Fin de la parte modificada

            // Generar respuesta del bot con estilo
            String resumenPrompt = generarPromptResumen(prendasFinales);
            String mensajeBot = generarRespuestaBot(resumenPrompt, apiKey);

            // Retornar RespuestaChatDTO con mensaje y lista de PrendaDTO
            return new RespuestaChatDTO(mensajeBot, prendasDtoFinales);

        } catch (Exception e) {
            e.printStackTrace();
            return new RespuestaChatDTO("❌ Ocurrió un error al procesar tu solicitud.", List.of());
        }
    }

    private String generarPromptResumen(List<Prenda> prendas) {
        StringBuilder sb = new StringBuilder();
        sb.append("Estas son las prendas disponibles:\n");

        for (Prenda prenda : prendas) {
            sb.append(String.format("- %s %s (%s)\n",
                    capitalize(prenda.getTipo()), prenda.getColor(), prenda.getDescripcion()));
        }

        sb.append("\nPor favor, como asistente de moda, sugiere combinaciones, consejos de estilo, o cómo podrían usarse estas prendas para distintas ocasiones. Agrega emojis si lo consideres útil.");
        return sb.toString();
    }

    private String generarRespuestaBot(String prompt, String apiKey) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "llama3-8b-8192",
                    "messages", List.of(Map.of("role", "user", "content", prompt))
            );

            String response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return root.path("choices").get(0).path("message").path("content").asText().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Aquí tienes algunas prendas interesantes. ¿Te gustaría combinarlas?";
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

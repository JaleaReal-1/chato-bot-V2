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
                                    
                    te voy a mandar un json para que alimente tu informacion sobre el 
                    catalogo que tenemos en la empresa:
                        [
                        {"id":"1","tipo":"chaqueta","color":"cafe","descripcion":"una chaqueta de color cafe especialmente para el frio de los andes peruanos","imagen_url":"chaqueta.glb","nombre":"Chaqueta Andina\\n","precio":"89.9","material":"lana de alpaca","imagen":null},
                        {"id":"2","tipo":"chaleco","color":"gris , oscuro","descripcion":"Chaleco tejido de corte recto, ideal para climas frescos, con diseño asimétrico y bolsillos frontales amplios.","imagen_url":"CARDIGAN_CHINO.glb","nombre":"Cardigan Chino","precio":"79.9","material":"lana de alpaca","imagen":null},
                        {"id":"3","tipo":"vestido","color":"llamita, cafe","descripcion":"Vestido corto que usualmente se usa para eventos formales, trabajo y actividades al aire libre.","imagen_url":"polivestido.glb","nombre":"Poli Vestido Tortuga","precio":"129.9","material":"tela fina","imagen":null},
                        {"id":"4","tipo":"vestido_capela","color":"gris , con bolsillos decorados blanco, marron, negro, en forma circular","descripcion":"Ideal para temporada de frio tejido de alpaca y capucha para el frio y para estar vestido para dias casuales.\\ncon capucha y bolsillos","imagen_url":"polivestido_capela.glb","nombre":"Poli Vestido Capela","precio":"149.9","material":"lana de alpaca","imagen":null},
                        {"id":"5","tipo":"Poncho","color":"Azul marino con franjas beige y marrón","descripcion":"Poncho artesanal de corte amplio, abotonado en el cuello, ideal para clima frío con estilo tradicional andino.","imagen_url":"Poncho_tortuga.glb","nombre":"Poncho Tortuga","precio":"99.9","material":"lana de aplaca","imagen":null},
                        {"id":"6","tipo":"Chompa","color":"Azul con patrones en relieve","descripcion":"Chompa de punto azul con diseño de texturas variadas, ideal para un look casual y abrigado en tiempos de frio. ","imagen_url":"SWEATER_PUNTO_VARON.glb","nombre":"Chompa Andina","precio":"89.9","material":"lana de alpaca","imagen":null}
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
                dto.setNombre(prenda.getNombre());
                dto.setTipo(prenda.getTipo());
                dto.setMaterial(prenda.getMaterial());
                dto.setColor(prenda.getColor());
                dto.setDescripcion(prenda.getDescripcion());
                dto.setPrecio(prenda.getPrecio());
                // Armar la URL completa apuntando a src/main/resources/static/modelos/{archivo.glb}
                dto.setImagenUrl(BASE_URL_PRENDAS + prenda.getImagenUrl());
                dto.setImagen(BASE_URL_PRENDAS + prenda.getImagen());
                prendasDtoFinales.add(dto);
            }


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
        sb.append("Eres un asesor de moda de INFOTEL, una marca que combina tradición textil peruana con tecnología.\n");
        sb.append("Estas son las prendas seleccionadas por el usuario:\n");

        for (Prenda prenda : prendas) {
            sb.append(String.format("- %s %s de %s (%s)\n",
                    capitalize(prenda.getTipo()), prenda.getColor(), prenda.getMaterial(), prenda.getDescripcion()));
        }

        sb.append("\nRedacta una respuesta breve, amigable y cercana (máximo 2 o 3 frases). Puedes incluir emojis. Ejemplo de estilo:\n");
        sb.append("\"¡Genial elección! La camisa azul de algodón combina perfecto con ese pantalón formal. 😎 ¿Quieres ver cómo queda en el avatar?\"\n");
        sb.append("Evita repetir las prendas literalmente, enfócate en dar una impresión general positiva y coherente con el estilo elegido.");
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

# 🤖 INFOTEL - Chatbot con Avatar 3D y Catálogo de Prendas

Este proyecto desarrollado para **INFOTEL BUSINESS S.A.C.** permite a los usuarios recibir sugerencias de prendas mediante un chatbot con IA, visualizar dichas prendas sobre un avatar 3D, y finalizar su compra ingresando sus datos personales.

---

## 🧾 Índice

- [📘 Descripción General](#-descripción-general)
- [🗂️ Estructura del Proyecto](#️-estructura-del-proyecto)
- [🔧 Tecnologías Usadas](#-tecnologías-usadas)
- [🎨 Frontend (React + TypeScript)](#-frontend-react--typescript)
- [🛠️ Backend (Spring Boot)](#-backend-spring-boot)
- [🗃️ Base de Datos (MySQL)](#️-base-de-datos-mysql)
- [🔁 Flujo del Sistema](#-flujo-del-sistema)
- [📌 Pendientes y Mejoras Futuras](#-pendientes-y-mejoras-futuras)

---

## 📘 Descripción General

El sistema permite a los clientes de **INFOTEL**:

- Conversar con un **asistente virtual** para describir su estilo o preferencia.
- Visualizar las **prendas sugeridas** directamente en un **avatar 3D interactivo**.
- Acceder a un **catálogo de productos** completo.
- **Seleccionar prendas** para combinarlas.
- **Finalizar la compra** mediante un formulario.

---

## 🗂️ Estructura del Proyecto

frontend/
├── App.tsx # Contenedor principal del layout
├── index.tsx # Punto de entrada React
├── components/
│ ├── Chat.tsx # Componente de chatbot
│ ├── Catalogo.tsx # Catálogo de prendas (carrusel/grid)
│ └── AvatarScene.tsx # Visualización 3D con Three.js
└── pages/
└── Comprar.tsx # Formulario de compra

backend/
├── controller/
│ ├── PrendaController.java
│ └── ChatController.java
├── model/
│ └── Prenda.java
├── service/
│ ├── PrendaService.java
│ └── ChatService.java
├── dto/
│ └── RespuestaChatDTO.java
├── repository/
│ └── PrendaRepository.java
└── INFOTELApplication.java


---

## 🔧 Tecnologías Usadas

| Tipo        | Tecnología              | Descripción                             |
|-------------|--------------------------|-----------------------------------------|
| Frontend    | React + TypeScript       | SPA interactiva                         |
| Estilos     | CSS                      | Diseño responsivo personalizado         |
| Render 3D   | Three.js + GLTFLoader    | Visualización del avatar y prendas      |
| Backend     | Spring Boot (Java)       | Lógica del negocio, API REST            |
| BD          | MySQL                    | Almacenamiento de prendas               |
| IA          | LLaMA 3 (vía Groq API)   | Procesamiento del lenguaje natural      |
| Comunicación| Axios / WebClient        | Cliente HTTP entre frontend y backend   |

---

## 🎨 Frontend (React + TypeScript)

### `App.tsx`
Contenedor principal. Distribuye:
- Chat (izquierda)
- AvatarScene (centro)
- Catálogo y prendas seleccionadas (derecha)
- Botón para finalizar compra

### `Chat.tsx`
- Permite al usuario enviar un mensaje.
- Envía el prompt al backend con `axios.post("/api/chat", { prompt })`.
- Recibe sugerencias y las muestra con posibilidad de selección.

### `Catalogo.tsx`
- Consulta `GET /api/prendas` al backend.
- Muestra las prendas disponibles con su imagen, nombre, precio y tipo de material.
- Permite seleccionar prendas directamente desde el catálogo.

### `AvatarScene.tsx`
- Visualiza el avatar y prendas seleccionadas en tiempo real.
- Usa Three.js para renderizar modelos `.glb`.
- Posiciona prendas sobre el avatar según su tipo.

### `Comprar.tsx`
- Formulario donde el usuario ingresa: nombre, correo, dirección.
- Recupera las prendas seleccionadas del `localStorage`.

---

## 🛠️ Backend (Spring Boot)

### `PrendaController.java`
- `GET /api/prendas`: retorna la lista completa de prendas.

### `ChatController.java`
- `POST /api/chat`: recibe un prompt del usuario, lo procesa y devuelve sugerencias.

### `ChatService.java`
- Envía el prompt a la API de lenguaje (Groq).
- Extrae palabras clave.
- Filtra y selecciona prendas que coinciden.
- Devuelve mensaje + lista de prendas sugeridas.

### `RespuestaChatDTO.java`
```java
public class RespuestaChatDTO {
    private String mensaje;
    private List<Prenda> prendas;
}

### `Prenda.java`
@Entity
public class Prenda {
    private Long id;
    private String nombre;
    private String tipo;
    private String color;
    private String material;
    private Double precio;
    private String imagenUrl;
}

🗃️ Base de Datos (MySQL)

🔁 Flujo del Sistema

Usuario escribe en el chat: “ponle una camisa azul”.

Chatbot envía el prompt al backend (POST /api/chat).

El backend interpreta la intención y filtra prendas tipo camisa de color azul.

Devuelve una respuesta con mensaje + prendas.

Usuario selecciona una prenda → se aplica al avatar.

Puede combinar más prendas desde el catálogo.

Al terminar, hace clic en Finalizar compra.

Se redirige a /comprar, donde llena sus datos.

🏁 Créditos
Proyecto desarrollado en el marco de una Hackathon regional con enfoque en tecnología e identidad cultural, representando a INFOTEL BUSINESS S.A.C. – Juliaca, Puno, Perú 🇵🇪



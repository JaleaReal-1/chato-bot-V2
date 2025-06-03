// src/components/Chat.tsx
import React, { useState } from "react";
import axios from "axios";

export interface Prenda {
    id: number;
    tipo: string;
    color: string;
    descripcion: string;
    imagenUrl: string; // URL al .glb
}

interface RespuestaChatDTO {
    mensaje: string;
    prendas: Prenda[];
}

interface ChatProps {
    onAgregarPrenda: (urlPrenda: string, tipo: string) => void;
}

export default function Chat({ onAgregarPrenda }: ChatProps) {
    const [prompt, setPrompt] = useState<string>("");
    const [esperando, setEsperando] = useState<boolean>(false);
    const [respuesta, setRespuesta] = useState<RespuestaChatDTO>({
        mensaje: "",
        prendas: [],
    });
    const [error, setError] = useState<string>("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!prompt.trim()) return;
        setEsperando(true);
        setError("");
        try {
            const res = await axios.post<RespuestaChatDTO>(
                "http://localhost:8080/api/chat",
                { prompt }
            );
            setRespuesta(res.data);
        } catch (err) {
            console.error(err);
            setError("Error al contactar al servidor. Intenta nuevamente.");
        } finally {
            setEsperando(false);
        }
    };

    return (
        <div style={{ width: "100%", padding: 16, boxSizing: "border-box", color: "#fff" }}>
            <form onSubmit={handleSubmit}>
        <textarea
            rows={3}
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            placeholder="Describe cómo quieres tu ropa..."
            style={{
                width: "100%",
                padding: 8,
                borderRadius: 4,
                border: "1px solid #444",
                backgroundColor: "#222",
                color: "#fff",
                marginBottom: 8,
            }}
        />
                <button
                    type="submit"
                    disabled={esperando}
                    style={{
                        width: "100%",
                        padding: "10px 0",
                        borderRadius: 4,
                        border: "none",
                        backgroundColor: "#007bff",
                        color: "#fff",
                        cursor: esperando ? "not-allowed" : "pointer",
                    }}
                >
                    {esperando ? "Pensando…" : "Enviar"}
                </button>
            </form>

            {error && <p style={{ color: "salmon", marginTop: 8 }}>{error}</p>}

            {respuesta.mensaje && (
                <div
                    style={{
                        marginTop: 16,
                        backgroundColor: "#333",
                        padding: 8,
                        borderRadius: 4,
                    }}
                >
                    <strong>Bot dice:</strong>
                    <p style={{ whiteSpace: "pre-wrap", margin: "8px 0 0 0" }}>{respuesta.mensaje}</p>
                </div>
            )}

            {respuesta.prendas.length > 0 && (
                <div style={{ marginTop: 16 }}>
                    <strong>Prendas encontradas:</strong>
                    <ul style={{ listStyle: "none", padding: 0, marginTop: 8 }}>
                        {respuesta.prendas.map((p) => (
                            <li
                                key={p.id}
                                style={{
                                    marginBottom: 12,
                                    backgroundColor: "#222",
                                    padding: 8,
                                    borderRadius: 4,
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                }}
                            >
                                <div>
                                    <p style={{ margin: "0 0 4px 0", color: "#ddd" }}>
                                        <strong>{capitalize(p.tipo)}</strong> {p.color} — {p.descripcion}
                                    </p>
                                    <small style={{ color: "#888" }}>
                                        <code>{p.imagenUrl}</code>
                                    </small>
                                </div>
                                <button
                                    onClick={() => onAgregarPrenda(p.imagenUrl, p.tipo)}
                                    style={{
                                        marginLeft: 8,
                                        padding: "6px 12px",
                                        borderRadius: 4,
                                        border: "none",
                                        backgroundColor: "#28a745",
                                        color: "#fff",
                                        cursor: "pointer",
                                    }}
                                >
                                    Añadir
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

function capitalize(str: string) {
    if (!str) return str;
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

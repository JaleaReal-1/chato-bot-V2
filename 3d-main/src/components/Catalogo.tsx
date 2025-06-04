import React, { useEffect, useState } from "react";
import axios from "axios";
import { Prenda } from "./Chat";

interface CatalogoProps {
    onAgregarPrenda: (urlPrenda: string, tipo: string) => void;
    modoCarrusel?: boolean;
}

export default function Catalogo({ onAgregarPrenda, modoCarrusel }: CatalogoProps) {
    const [prendas, setPrendas] = useState<Prenda[]>([]);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        axios.get<Prenda[]>("http://localhost:8080/api/prendas")
            .then(res => setPrendas(res.data))
            .catch(() => setError("No se pudo cargar el catálogo."));
    }, []);

    return (
        <div style={{ marginTop: 16 }}>
            {modoCarrusel ? (
                <div style={{
                    display: "flex",
                    overflowX: "auto",
                    gap: "1rem",
                    paddingBottom: "1rem",
                    scrollbarWidth: "none"
                }}>
                    {prendas.map((p) => (
                        <div
                            key={p.id}
                            style={{
                                minWidth: 220,
                                backgroundColor: "#1e1e1e",
                                borderRadius: "10px",
                                padding: "12px",
                                boxShadow: "0 2px 8px rgba(0,0,0,0.5)",
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "space-between"
                            }}
                        >
                            <img
                                src={p.imagenUrl}
                                alt={p.nombre}
                                style={{ width: "100%", height: 120, objectFit: "cover", borderRadius: 6, marginBottom: 8 }}
                            />
                            <strong style={{ color: "#fff" }}>{capitalize(p.nombre)}</strong>
                            <p style={{ color: "#aaa", fontSize: "0.85rem", margin: "4px 0" }}>
                                {p.tipo} - {p.color}<br />
                                Material: {p.material}<br />
                                <span style={{ color: "#80ffaa" }}>S/. {p.precio.toFixed(2)}</span>
                            </p>
                            <button
                                onClick={() => onAgregarPrenda(p.imagenUrl, p.tipo)}
                                style={{
                                    marginTop: "auto",
                                    padding: "8px",
                                    borderRadius: "6px",
                                    border: "none",
                                    backgroundColor: "#28a745",
                                    color: "#fff",
                                    cursor: "pointer",
                                    fontWeight: "bold"
                                }}
                            >
                                + Añadir
                            </button>
                        </div>
                    ))}
                </div>
            ) : (
                <>
                    <h3 style={{ color: "#fff" }}>Catálogo de Prendas</h3>
                    {error && <p style={{ color: "salmon" }}>{error}</p>}
                    <ul style={{ listStyle: "none", padding: 0 }}>
                        {prendas.map(p => (
                            <li
                                key={p.id}
                                style={{
                                    marginBottom: 12,
                                    backgroundColor: "#2a2a2a",
                                    padding: 12,
                                    borderRadius: 6,
                                    display: "flex",
                                    justifyContent: "space-between",
                                    alignItems: "center",
                                    boxShadow: "0 1px 5px rgba(0,0,0,0.3)"
                                }}
                            >
                                <div>
                                    <strong style={{ color: "#fff" }}>{capitalize(p.nombre)}</strong><br />
                                    <span style={{ color: "#ccc" }}>{p.descripcion}</span><br />
                                    <small style={{ color: "#aaa" }}>
                                        {p.tipo} - {p.color} | {p.material} | S/. {p.precio.toFixed(2)}
                                    </small>
                                </div>
                                <button
                                    onClick={() => onAgregarPrenda(p.imagenUrl, p.tipo)}
                                    style={{
                                        marginLeft: 12,
                                        padding: "8px 12px",
                                        borderRadius: 6,
                                        border: "none",
                                        backgroundColor: "#28a745",
                                        color: "#fff",
                                        cursor: "pointer",
                                        fontWeight: "bold"
                                    }}
                                >
                                    Añadir
                                </button>
                            </li>
                        ))}
                    </ul>
                </>
            )}
        </div>
    );
}

function capitalize(str: string) {
    if (!str) return str;
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

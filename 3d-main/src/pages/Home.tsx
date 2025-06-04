import React, { useState } from "react";
import Chat from "../components/Chat";
import Catalogo from "../components/Catalogo";
import AvatarScene from "../components/AvatarScene";

import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {
    const [prendasSeleccionadas, setPrendasSeleccionadas] = useState<{ url: string; tipo: string }[]>([]);
    const navigate = useNavigate();

    const handleAgregarPrenda = (urlPrenda: string, tipoPrenda: string) => {
        setPrendasSeleccionadas((prev) => {
            if (prev.some((p) => p.url === urlPrenda)) return prev;
            return [...prev, { url: urlPrenda, tipo: tipoPrenda }];
        });
    };

    const handleEliminarPrenda = (urlPrenda: string) => {
        setPrendasSeleccionadas((prev) => prev.filter((p) => p.url !== urlPrenda));
    };

    const handleFinalizarCompra = () => {
        localStorage.setItem("prendasSeleccionadas", JSON.stringify(prendasSeleccionadas));
        navigate("/comprar");
    };

    return (
        <div style={{ display: "flex", flexDirection: "column", height: "100vh" }}>
            <div style={{ display: "flex", flex: 1, overflow: "hidden" }}>
                <div
                    style={{
                        width: "25%",
                        backgroundColor: "#111",
                        color: "#fff",
                        padding: "1rem",
                        overflowY: "auto",
                    }}
                >
                    <h2 style={{ marginBottom: "1rem" }}>🧠 Asistente de Moda</h2>
                    <Chat onAgregarPrenda={handleAgregarPrenda} />
                </div>

                <div style={{ width: "50%", backgroundColor: "#eaeaea" }}>
                    <AvatarScene prendasUrls={prendasSeleccionadas} />
                </div>

                <div
                    style={{
                        width: "25%",
                        backgroundColor: "#222",
                        color: "#fff",
                        padding: "1rem",
                        overflowY: "auto",
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-between"
                    }}
                >
                    <div>
                        <h3>👕 Prendas seleccionadas</h3>
                        {prendasSeleccionadas.length === 0 ? (
                            <p style={{ color: "#aaa" }}>No has agregado ninguna prenda todavía.</p>
                        ) : (
                            <ul style={{
                                listStyle: "none",
                                padding: 0,
                                display: "flex",
                                flexWrap: "wrap",
                                gap: "1rem"
                            }}>
                                {prendasSeleccionadas.map((p, index) => (
                                    <li
                                        key={index}
                                        style={{
                                            backgroundColor: "#333",
                                            padding: "10px",
                                            borderRadius: "8px",
                                            display: "flex",
                                            flexDirection: "column",
                                            alignItems: "flex-start",
                                            boxShadow: "0 1px 6px rgba(0,0,0,0.3)",
                                            width: "150px"
                                        }}
                                    >
                                        <span style={{ fontWeight: "bold", color: "#fff" }}>{capitalize(p.tipo)}</span>
                                        <button
                                            onClick={() => handleEliminarPrenda(p.url)}
                                            style={{
                                                marginTop: "0.5rem",
                                                background: "#f55",
                                                border: "none",
                                                color: "#fff",
                                                padding: "6px 10px",
                                                borderRadius: "4px",
                                                cursor: "pointer",
                                                width: "100%"
                                            }}
                                        >
                                            🗑️ Eliminar
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>

                    {prendasSeleccionadas.length > 0 && (
                        <button
                            onClick={handleFinalizarCompra}
                            style={{
                                marginTop: "1rem",
                                backgroundColor: "#28a745",
                                border: "none",
                                color: "#fff",
                                padding: "10px",
                                borderRadius: "6px",
                                cursor: "pointer"
                            }}
                        >
                            🛒 Finalizar Compra
                        </button>
                    )}
                </div>
            </div>

            <div
                style={{
                    height: "230px",
                    backgroundColor: "#0f0f0f",
                    borderTop: "1px solid #333",
                    overflowX: "hidden",
                    padding: "1rem",
                }}
            >
                <h3 style={{ marginBottom: "1rem", color: "#fff" }}>🛍️ Catálogo</h3>
                <Catalogo onAgregarPrenda={handleAgregarPrenda} modoCarrusel />
            </div>
        </div>
    );
};

function capitalize(str: string) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

export default Home;

// src/pages/Comprar.tsx
import React, { useState, useEffect } from "react";

interface Prenda {
    url: string;
    tipo: string;
}

export default function Comprar() {
    const [prendas, setPrendas] = useState<Prenda[]>([]);
    const [nombre, setNombre] = useState("");
    const [email, setEmail] = useState("");
    const [direccion, setDireccion] = useState("");

    useEffect(() => {
        const guardadas = localStorage.getItem("prendasSeleccionadas");
        if (guardadas) {
            setPrendas(JSON.parse(guardadas));
        }
    }, []);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Datos del cliente:", { nombre, email, direccion });
        console.log("Prendas seleccionadas:", prendas);
        alert("✅ Pedido registrado. ¡Gracias por tu compra!");
    };

    return (
        <div style={{ padding: 32, backgroundColor: "#111", color: "#fff", minHeight: "100vh" }}>
            <h2>🧾 Formulario de Compra</h2>
            <form onSubmit={handleSubmit} style={{ maxWidth: 600 }}>
                <div style={{ marginBottom: 16 }}>
                    <label>Nombre completo:</label><br />
                    <input type="text" value={nombre} onChange={(e) => setNombre(e.target.value)} required style={inputStyle} />
                </div>
                <div style={{ marginBottom: 16 }}>
                    <label>Email:</label><br />
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required style={inputStyle} />
                </div>
                <div style={{ marginBottom: 16 }}>
                    <label>Dirección de envío:</label><br />
                    <textarea value={direccion} onChange={(e) => setDireccion(e.target.value)} required style={{ ...inputStyle, height: 80 }} />
                </div>

                <button type="submit" style={botonStyle}>Confirmar Compra</button>
            </form>

            {prendas.length > 0 && (
                <div style={{ marginTop: 32 }}>
                    <h3>👗 Prendas seleccionadas:</h3>
                    <ul style={{ listStyle: "none", padding: 0 }}>
                        {prendas.map((p, i) => (
                            <li key={i} style={{ marginBottom: 8 }}>{p.tipo} - <code>{p.url}</code></li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

const inputStyle = {
    width: "100%",
    padding: "8px",
    borderRadius: "4px",
    border: "1px solid #444",
    backgroundColor: "#222",
    color: "#fff"
};

const botonStyle = {
    padding: "10px 20px",
    backgroundColor: "#28a745",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer"
};

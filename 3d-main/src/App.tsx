// src/App.tsx
import React, { useState } from "react";
import Chat from "./components/Chat";
import AvatarScene from "./components/AvatarScene";

const App: React.FC = () => {
    // Estado que almacenará { url: string, tipo: string } de cada prenda añadida
    const [prendasSeleccionadas, setPrendasSeleccionadas] = useState<{ url: string; tipo: string }[]>([]);

    const handleAgregarPrenda = (urlPrenda: string, tipoPrenda: string) => {
        setPrendasSeleccionadas((prev) => {
            // Si ya existe esa URL, no la duplicamos
            if (prev.some((p) => p.url === urlPrenda)) {
                return prev;
            }
            return [...prev, { url: urlPrenda, tipo: tipoPrenda }];
        });
    };

    return (
        <div style={{ display: "flex", width: "100vw", height: "100vh" }}>
            {/* Panel izquierdo: Chat */}
            <div
                style={{
                    width: "30%",
                    backgroundColor: "#111",
                    overflowY: "auto",
                    padding: "1rem",
                }}
            >
                <Chat onAgregarPrenda={handleAgregarPrenda} />
            </div>

            {/* Panel derecho: Escena 3D */}
            <div style={{ width: "70%" }}>
                <AvatarScene prendasUrls={prendasSeleccionadas} />
            </div>
        </div>
    );
};

export default App;

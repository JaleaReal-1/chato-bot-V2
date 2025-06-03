// src/App.tsx
import React, { useState } from "react";
import Chat from "./components/Chat";
import AvatarScene from "./components/AvatarScene";

const App: React.FC = () => {
    // Estado que almacena URLs de las prendas que el usuario ha añadido
    const [prendasSeleccionadas, setPrendasSeleccionadas] = useState<string[]>([]);

    const handleAgregarPrenda = (urlPrenda: string) => {
        setPrendasSeleccionadas((prev) =>
            prev.includes(urlPrenda)
                ? prev.filter((u) => u !== urlPrenda)
                : [...prev, urlPrenda]
        );
    };


    return (
        <div style={{ display: "flex", width: "100vw", height: "100vh" }}>
            {/* Panel izquierdo: Chat + lista de prendas */}
            <div
                style={{
                    width: "30%",
                    backgroundColor: "#111",
                    overflowY: "auto",
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

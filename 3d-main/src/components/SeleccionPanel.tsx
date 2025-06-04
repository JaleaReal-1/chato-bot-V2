import React from "react";

interface Props {
    prendas: { url: string; tipo: string; nombre?: string }[];
    onEliminar: (index: number) => void;
}

export default function SeleccionPanel({ prendas, onEliminar }: Props) {
    return (
        <div>
            <h3>Prendas seleccionadas</h3>
            {prendas.map((prenda, idx) => (
                <div key={idx} style={{ marginBottom: "1rem", borderBottom: "1px solid #ddd" }}>
                    <p>{prenda.nombre || prenda.tipo}</p>
                    <button onClick={() => onEliminar(idx)}>❌ Quitar</button>
                </div>
            ))}
        </div>
    );
}

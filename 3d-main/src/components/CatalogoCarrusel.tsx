import React from "react";
import "./CatalogoCarrusel.css";

const prendasMock = [
    {
        id: 1,
        nombre: "Camisa azul",
        tipo: "camisa",
        url: "/modelos/camisa.glb",
        imagen_url: "/imagenes/camisa.jpg",
    },
    {
        id: 2,
        nombre: "Chaqueta marrón",
        tipo: "chaqueta",
        url: "/modelos/chaqueta.glb",
        imagen_url: "/imagenes/chaqueta.jpg",
    },
    // Agrega más
];

export default function CatalogoCarrusel({ onSeleccionar }: { onSeleccionar: (p: any) => void }) {
    return (
        <div className="carrusel-container">
            {prendasMock.map((prenda) => (
                <div key={prenda.id} className="prenda-card" onClick={() => onSeleccionar(prenda)}>
                    <img src={prenda.imagen_url} alt={prenda.nombre} />
                    <span>{prenda.nombre}</span>
                </div>
            ))}
        </div>
    );
}

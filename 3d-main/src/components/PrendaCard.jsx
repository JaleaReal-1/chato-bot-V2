import React from 'react';

const PrendaCard = ({ prenda, onSeleccionar }) => {
    return (
        <div className="prenda-card">
            <h3>{prenda.nombre}</h3>
            <img src={prenda.imagenUrl.replace('.glb', '.jpg')} alt={prenda.nombre} style={{ width: '100%', height: 'auto' }} />
            <p><strong>Precio:</strong> S/ {prenda.precio.toFixed(2)}</p>
            <p><strong>Tela:</strong> {prenda.tela}</p>
            <p><strong>Tipo:</strong> {prenda.tipo}</p>
            <p><strong>Color:</strong> {prenda.color}</p>
            <p><strong>Estilo:</strong> {prenda.descripcion}</p>
            <button onClick={() => onSeleccionar(prenda)}>Probar</button>
        </div>
    );
};

export default PrendaCard;

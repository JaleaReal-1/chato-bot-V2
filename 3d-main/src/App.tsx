// src/App.tsx
import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Comprar from "./pages/Comprar";

export default function App() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/comprar" element={<Comprar />} />
        </Routes>
    );
}

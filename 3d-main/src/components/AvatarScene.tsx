// src/components/AvatarScene.tsx
import React from "react";
import { Canvas } from "@react-three/fiber";
import { OrbitControls, useGLTF } from "@react-three/drei";

interface AvatarSceneProps {
    prendasUrls: string[];
}

// Componente para cargar un modelo .glb genérico
function Modelo3D({ url }: { url: string }) {
    const { scene } = useGLTF(url);
    return <primitive object={scene} />;
}

export default function AvatarScene({ prendasUrls }: AvatarSceneProps) {
    // URL del avatar base
    const avatarUrl = "https://models.readyplayer.me/683f1068e7966b80ca0a5447.glb";
    const { scene: avatarScene } = useGLTF(avatarUrl);

    return (
        <Canvas
            style={{ width: "100%", height: "100%" }}
            camera={{ position: [0, 1.6, 3], fov: 50 }}
        >
            <ambientLight intensity={0.5} />
            <directionalLight position={[5, 5, 5]} intensity={1} />
            {/* Avatar base */}
            <primitive object={avatarScene} scale={1.5} />

            {/* Por cada prenda URL, renderizamos su modelo */}
            {prendasUrls.map((url, idx) => (
                <Modelo3D key={idx} url={url} />
            ))}

            <OrbitControls target={[0, 1.6, 0]} />
        </Canvas>
    );
}

// Preload del avatar base
useGLTF.preload(
    "https://models.readyplayer.me/683f1068e7966b80ca0a5447.glb"
);

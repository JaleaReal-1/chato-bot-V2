// components/ModelViewer.tsx
import React, { Suspense } from "react";
import { Canvas } from "@react-three/fiber";
import { OrbitControls, useGLTF } from "@react-three/drei";

interface ModelViewerProps {
    url: string;
}

function Model({ url }: { url: string }) {
    const { scene } = useGLTF(url);
    return <primitive object={scene} scale={1.5} />;
}

export default function ModelViewer({ url }: ModelViewerProps) {
    return (
        <div style={{ width: "100%", height: "200px" }}>
            <Canvas camera={{ position: [0, 1, 3], fov: 35 }}>
                <ambientLight intensity={1} />
                <directionalLight position={[2, 2, 2]} />
                <Suspense fallback={null}>
                    <Model url={url} />
                </Suspense>
                <OrbitControls enableZoom={false} />
            </Canvas>
        </div>
    );
}

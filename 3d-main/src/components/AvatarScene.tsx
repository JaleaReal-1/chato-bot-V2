// src/components/AvatarScene.tsx
import React from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import { OrbitControls, useGLTF, Environment, useTexture } from "@react-three/drei";
import * as THREE from "three";

// Carga del avatar principal
function Avatar() {
    const { scene } = useGLTF("https://models.readyplayer.me/683f1068e7966b80ca0a5447.glb");
    return <primitive object={scene} scale={1.5} />;
}

// Crea el piso texturizado
function Piso() {
    const texture = useTexture("/ff.jpg");
    return (
        <mesh rotation={[-Math.PI / 2, 0, 0]} position={[0, 0, 0]} receiveShadow>
            <planeGeometry args={[10, 10]} />
            <meshStandardMaterial map={texture} roughness={0.2} metalness={0.3} />
        </mesh>
    );
}

// Crea las paredes de la habitación
function Habitacion() {
    const textura = useTexture("/pared.jpg");
    return (
        <>
            {/* Pared trasera */}
            <mesh position={[0, 5, -5]}>
                <planeGeometry args={[10, 10]} />
                <meshStandardMaterial map={textura} />
            </mesh>
            {/* Pared delantera */}
            <mesh position={[0, 5, 5]} rotation={[0, Math.PI, 0]}>
                <planeGeometry args={[10, 10]} />
                <meshStandardMaterial map={textura} />
            </mesh>
            {/* Pared izquierda */}
            <mesh position={[-5, 5, 0]} rotation={[0, Math.PI / 2, 0]}>
                <planeGeometry args={[10, 10]} />
                <meshStandardMaterial map={textura} />
            </mesh>
            {/* Pared derecha */}
            <mesh position={[5, 5, 0]} rotation={[0, -Math.PI / 2, 0]}>
                <planeGeometry args={[10, 10]} />
                <meshStandardMaterial map={textura} />
            </mesh>
        </>
    );
}

// Partículas de fondo
function ParticleBackground() {
    const particlesRef = React.useRef<THREE.Points>(null!);
    const particleCount = 500;
    const positions = new Float32Array(particleCount * 3);
    for (let i = 0; i < particleCount; i++) {
        positions[i * 3] = Math.random() * 10 - 5;
        positions[i * 3 + 1] = Math.random() * 10 - 5;
        positions[i * 3 + 2] = Math.random() * 10 - 5;
    }
    const geometry = new THREE.BufferGeometry();
    geometry.setAttribute("position", new THREE.BufferAttribute(positions, 3));

    useFrame(() => {
        if (particlesRef.current) {
            particlesRef.current.rotation.y += 0.001;
        }
    });

    return (
        <points ref={particlesRef}>
            <primitive object={geometry} attach="geometry" />
            <pointsMaterial attach="material" color={0x8b5e3c} size={0.1} />
        </points>
    );
}

// Map que asocia cada *tipo* de prenda a su transform en el avatar
const transformsPorTipo: Record<string, { scale: number; position: [number, number, number]; rotation: [number, number, number] }> = {
    chaqueta: { scale: 1.2, position: [0, 1.73, 0.082], rotation: [0, 4.75, 0] },
    chaleco: { scale: 1.45, position: [-0.05, 1.62, 0.04], rotation: [0, 4.75, 0] },
    vestido: { scale: 1.25, position: [-0.01, 1.72, 0.0015], rotation: [0, 4.65 , 0] },
    vestido_capela: { scale: 1.25, position: [-0.0, 1.72, 0.02], rotation: [0, 4.65 , 0] },
    poncho: { scale: 1.4, position: [-0, 1.62, -0.015], rotation: [0, 4, 0] },
    sombrero: { scale: 1.5, position: [0, 1.9, 0], rotation: [0, 0, 0] },
    Chompa: { scale: 1.5, position: [0, 1.73, 0.082], rotation: [0, 4, 0] },
    // Agrega más tipos según necesites, con sus valores de transform
};

// Componente genérico para renderizar una prenda .glb
interface Prenda3DProps {
    url: string;
    tipo: string;
}

function Prenda3D({ url, tipo }: Prenda3DProps) {
    const { scene } = useGLTF(url);

    // Ajustamos el material para que brille un poco
    React.useEffect(() => {
        scene.traverse((child) => {
            if (child instanceof THREE.Mesh && child.material instanceof THREE.MeshStandardMaterial) {
                child.material.envMapIntensity = 2.5;
                child.material.roughness = 0.4;
                child.material.metalness = 0.1;
            }
        });
    }, [scene]);

    // Vemos si tenemos un transform preconfigurado para este tipo
    const transform = transformsPorTipo[tipo.toLowerCase()] || {
        scale: 1,
        position: [0, 1, 0],
        rotation: [0, 0, 0],
    };

    return (
        <primitive
            object={scene}
            scale={transform.scale}
            position={transform.position}
            rotation={transform.rotation}
        />
    );
}

interface AvatarSceneProps {
    prendasUrls: { url: string; tipo: string }[];
}

export default function AvatarScene({ prendasUrls }: AvatarSceneProps) {
    const avatarUrl = "https://models.readyplayer.me/683f1068e7966b80ca0a5447.glb";
    const { scene: avatarScene } = useGLTF(avatarUrl);

    return (
        <Canvas shadows camera={{ position: [0, 1.6, 3], fov: 50 }}>
            <ambientLight intensity={1.2} />
            <directionalLight intensity={2} position={[5, 5, 5]} castShadow />
            <Environment preset="city" />
            <OrbitControls target={[0, 1.6, 0]} />

            <ParticleBackground />

            <primitive object={avatarScene} scale={1.5} />

            {/* Por cada prenda, renderizamos con su transform */}
            {prendasUrls.map((p, idx) => (
                <Prenda3D key={idx} url={p.url} tipo={p.tipo} />
            ))}

            <Piso />
            <Habitacion />
        </Canvas>
    );
}

// Precarga los modelos
useGLTF.preload("https://models.readyplayer.me/683f1068e7966b80ca0a5447.glb");
// También puedes pre-cargar algunas prendas si lo deseas:
// useGLTF.preload("/modelos/chaqueta.glb");
// useGLTF.preload("/modelos/camisa.glb");


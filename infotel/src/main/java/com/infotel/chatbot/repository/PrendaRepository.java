package com.infotel.chatbot.repository;


import com.infotel.chatbot.entity.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrendaRepository extends JpaRepository<Prenda, Long> {
    List<Prenda> findByDescripcionContainingIgnoreCase(String descripcion);
    List<Prenda> findByTipoAndColor(String tipo, String color);
    List<Prenda> findByTipoAndColorAndDescripcionContainingIgnoreCase(String tipo, String color, String descripcion);
}

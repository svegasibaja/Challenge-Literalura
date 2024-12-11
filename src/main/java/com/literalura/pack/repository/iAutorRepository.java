package com.literalura.pack.repository;
import com.literalura.pack.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iAutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findAll();

    List<Autor> findByCumpleaniosLessThanOrFechaFallecimientoGreaterThanEqual(int anioBuscado, int anioBuscado1);

}

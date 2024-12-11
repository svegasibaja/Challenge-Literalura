package com.literalura.pack.repository;
import com.literalura.pack.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface iLibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);

}

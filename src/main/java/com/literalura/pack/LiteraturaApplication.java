package com.literalura.pack;


import com.literalura.pack.library.Libreria;
import com.literalura.pack.repository.iAutorRepository;
import com.literalura.pack.repository.iLibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

    @Autowired
    private iLibroRepository libroRepository;
    @Autowired
    private iAutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraturaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Libreria libreria = new Libreria(libroRepository, autorRepository);
        libreria.consumo();

    }
}

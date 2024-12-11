package com.literalura.pack.library;

import com.literalura.pack.model.Autor;
import com.literalura.pack.repository.iAutorRepository;
import com.literalura.pack.repository.iLibroRepository;
import com.literalura.pack.config.ConsumoApiGutendex;
import com.literalura.pack.config.ConvertirDatos;
import com.literalura.pack.model.Libro;
import com.literalura.pack.model.LibrosRespuestaApi;
import com.literalura.pack.model.records.DatosLibro;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Libreria {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApiGutendex consumoApi = new ConsumoApiGutendex();
    private  ConvertirDatos convertir = new ConvertirDatos();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibro = new ArrayList<>();
    private iLibroRepository libroRepository;
    private iAutorRepository autorRepository;
    public Libreria(iLibroRepository libroRepository, iAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void consumo(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    
                    --------------- MENU -----------------
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Buscar libros por idioma
                   
                    0 - Salir
                    
                    Seleccione una opcion:  """;

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {

                System.out.println("Por favor, ingrese un número válido.");

                sc.nextLine();
                continue;
            }



            switch (opcion){
                case 1:
                    buscarLibroEnLaWeb();
                    break;
                case 2:
                    librosBuscados();
                    break;
                case 3:
                    BuscarAutores();
                    break;
                case 4:
                    buscarAutoresPorAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    opcion = 0;
                    System.out.println("Aplicación cerrada");
                    break;
                default:

                    System.out.println("Opción Incorrecta.");
                    System.out.println("Intente con una nueva opcion");
                    consumo();
                    break;
            }
        }
    }

    private Libro getDatosLibro(){
        System.out.println("Ingrese el nombre del libro: ");
        var nombreLibro = sc.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(API_BASE + nombreLibro.replace(" ", "%20"));

        LibrosRespuestaApi datos = convertir.convertirDatosJsonAJava(json, LibrosRespuestaApi.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0); // Obtener el primer libro de la lista
            return new Libro(primerLibro);
        } else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }


    private void buscarLibroEnLaWeb() {
        Libro libro = getDatosLibro();

        if (libro == null){
            System.out.println("Libro no encontrado. el valor es null");
            return;
        }

        try{
            boolean libroExists = libroRepository.existsByTitulo(libro.getTitulo());
            if (libroExists){
                System.out.println("El libro ya existe en la base de datos!");
            }else {
                libroRepository.save(libro);
                System.out.println(libro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persisitir el libro buscado!");
        }
    }

    @Transactional(readOnly = true)
    private void librosBuscados(){

        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros encontrados en la base de datos:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }


    private  void BuscarAutores(){

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos. \n");
        } else {
            System.out.println("Autores encontrados en la base de datos: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                // add() retorna true si el nombre no estaba presente y se añade correctamente
                if (autoresUnicos.add(autor.getNombre())){
                    System.out.println(autor.getNombre()+'\n');
                }
            }
        }
    }

    private void  buscarLibrosPorIdioma(){
        System.out.println("Ingrese Idioma en el que quiere buscar: \n");
        System.out.println("Opción - es : Libros en español.");
        System.out.println("Opción - en : Libros en ingles.");


        var idioma = sc.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros segun idioma encontrados en la base de datos:");
            for (Libro libro : librosPorIdioma) {
                System.out.println(libro.toString());
            }
        }

    }

    private void buscarAutoresPorAnio() {

        System.out.println("Indica el año para consultar que autores estan vivos: \n");
        var anioBuscado = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepository.findByCumpleaniosLessThanOrFechaFallecimientoGreaterThanEqual(anioBuscado, anioBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + anioBuscado + ".");
        } else {
            System.out.println("Los autores que estaban vivos en el año " + anioBuscado + " son:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getCumpleanios() != null && autor.getFechaFallecimiento() != null) {
                    if (autor.getCumpleanios() <= anioBuscado && autor.getFechaFallecimiento() >= anioBuscado) {
                        if (autoresUnicos.add(autor.getNombre())) {
                            System.out.println("Autor: " + autor.getNombre());
                        }
                    }
                }
            }
        }
    }

}

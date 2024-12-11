# Challenge-Literalura
Esta es una biblioteca por consola desarrollada en Spring Boot con Java.

# Proyecto Literatura

Este proyecto es una aplicación de consola desarrollada en **Spring Boot** que permite a los usuarios buscar libros utilizando la API de [Gutendex](https://gutendex.com/). Los libros encontrados se almacenan en una base de datos PostgreSQL llamada **literalura**. La aplicación ofrece varias funcionalidades, como listar libros, autores y filtrar por idioma y estado de los autores.

## Funcionalidades

1. **Buscar un libro por título**: Permite al usuario buscar un libro ingresando su título. Si el libro se encuentra, se guarda en la base de datos.
2. **Listar libros registrados**: Muestra todos los libros que han sido guardados en la base de datos.
3. **Listar autores de los libros registrados**: Muestra todos los autores de los libros almacenados en la base de datos.
4. **Listar autores vivos**: Permite al usuario ingresar un año y muestra los autores que están vivos en ese año.
5. **Listar libros por idioma**: Muestra los libros guardados en la base de datos filtrados por el idioma especificado.

## Requisitos

- Java 17 o superior
- Maven
- PostgreSQL

## Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu_usuario/literatura.git
   cd literatura

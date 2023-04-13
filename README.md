# Compilador

Universidad del Valle de Guatemala

Departamento de Ciencias de la Computación

Diseño de Lenguajes de Programación - Sección 10

Cristian Fernando Laynez Bachez - 201281

## Tecnologías

Este proyecto fue creado y desarrolllado con:

- Java
- JUnit (Este solo fue para llevar a cabo pruebas unitarias)
- Graphviz

## Librerias y Herramientas Necesarias

### `Java`

Como minimo tener la version 17

### `Graphviz`

Descargar Graphviz: https://graphviz.org/download/
Implementar el path: https://stackoverflow.com/questions/35064304/runtimeerror-make-sure-the-graphviz-executables-are-on-your-systems-path-aft

Nos servirá para ver de una manera visual las simulaciones.

## Ejecucion de Automatas

Para correr este proyecto debes de compilar el archivo Main.java y ejecutar el main con los argumentos solicitados (en este caso sería la expresion regular y la cadena de aceptación) de la siguiente manera:

```
cd src
javac Main.java
java Main 'expresion_regular' 'cadena_aceptacion'
```

Ejemplo:
```
java Main 'a(a|b)*c' 'abc'
```

![Alt text](/img/img3.png 'Corriendo Programa en vscode actualizado')

## Ejecucion del Analizador Yal

Para correr esta parte de este proyecto debes de compilar el archivo Yal.java y ejecutar esta clase con sus correspondientes argumentos (en este caso seria el archivo yal a analizar):

```
java Yal 'direccion_archivo.yal'
```


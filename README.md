# Compilador

Universidad del Valle de Guatemala

Diseño de Lenguajes de Programación - Sección 10

Cristian Laynez Bachez - 201281

## Tecnologías

Este proyecto fue creado y desarrolllado con:

- Java
- JUnit
- Graphviz

## Librerias y Herramientas Necesarias

### `Java`

Como minimo tener la version 8

### `Graphviz`

Descargar Graphviz: https://graphviz.org/download/
Implementar el path: https://stackoverflow.com/questions/35064304/runtimeerror-make-sure-the-graphviz-executables-are-on-your-systems-path-aft

Nos servirá para ver de una manera visual las simulaciones.

## Setup

Para correr este proyecto debes de compilar el archivo Main.java y ejecutar el main con un argumento (en este caso sería la expresion regular) de la siguiente manera:

### Opcion 1 (Ejecutar por cmd/powershell)

```
cd src
javac Main.java
java Main '(a*|b*)c'
```

![Alt text](/img/img1.png 'Corriendo Programa terminal')

### Opcion 2 (Ejemplo dentro de VSCode Windows)

```
& 'C:\Program Files\Java\jdk-14.0.1\bin\java.exe' '@C:\Users\CRISTI~1\AppData\Local\Temp\cp_1an12v5kdyar8p68zla384s3a.argfile' 'Main' 'aaab*'
```

![Alt text](/img/img2.png 'Corriendo Programa en vscode')

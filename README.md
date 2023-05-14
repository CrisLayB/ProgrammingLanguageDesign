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

## ---> Automatas

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

![Alt text](/img/ForREADME/automatas.png 'Corriendo Programa en vscode actualizado')

## ---> Generador de Analizadores Léxicos (YALex)

Para correr esta parte de este proyecto debes de compilar el archivo Yal.java y ejecutar esta clase con sus correspondientes argumentos (en este caso seria el archivo yal a analizar):

```
java LexicalAnalyzerGenerator 'direccion_archivo_yal.yal'
```

![Alt text](/img/ForREADME/lexicalAna01.png 'Scanner Generado')

Este programa es capaz de detectar errores sintaxicos del archivo .yal ingresado.

Si todo salio en orden entonces se creara un Scanner donde se guardara todo el automata y las acciones a llevar a cabo, este se llamara "Scanner.java" (y también se crea un pdf con el automata generado).

Para correr el Scanner generado debes de ingresar un archivo donde este todo el input a analizar para obtener los tokens esperados.

```
java Scanner 'inputFile'
```

![Alt text](/img/ForREADME/lexicalAna02.png 'Scanner Ejecutado')

En la carpeta "docs" se guardaran en archivos los tokens generados, los archivos se llaman "outputFile".

## ---> Generador de Analizadores Sintáticos (YAPar)

```
java ParserGenerator 'direccion_archivo_yalpar.yalp' 'archivo_output_tokens_generados'
```
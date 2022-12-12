package com.cesur.examenaddicc22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
class Ejercicio1 {

    /**
     * Enunciado:
     * <p>
     * Completar el método estadísticasDeArchivo de manera que lea el archivo
     * de texto que se le pasa como parámetro, lo analice y muestre por consola
     * el número de caracteres, palabras y líneas total.
     * <p>
     * Modificar solo el código del método.
     */

    static void solucion() {

        estadísticasDeArchivo("pom.xml");
    }

    private static void estadísticasDeArchivo(String archivo) {

        /* añadir código */
        Integer numCharacters = 0;
        Integer numLines = 0;
        Integer numWords = 0;

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(archivo));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                numCharacters += line.length();
                numLines ++;

                String[] words = line.split("\\P{L}+");
                numWords += words.length;
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Estadísticas de " + archivo);
        System.out.println(numLines + " líneas");
        System.out.println(numCharacters + " carácteres");
        System.out.println(numWords + " palabras");
    }

}

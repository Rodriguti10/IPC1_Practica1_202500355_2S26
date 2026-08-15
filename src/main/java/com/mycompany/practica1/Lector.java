package com.mycompany.practica1;

import java.util.Scanner;

/**
 * Centraliza la lectura de datos desde la consola. Todas las lecturas
 * validan el formato antes de devolver el dato, para que el programa nunca
 * finalice de forma inesperada ante una entrada inválida.
 */
public class Lector {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Lee un token de la entrada estándar. Si la entrada se cierra
     * inesperadamente, el programa finaliza de forma controlada en lugar
     * de lanzar una excepción sin manejar.
     */
    public static String leerToken() {
        if (!sc.hasNext()) {
            System.out.println("\nNo hay más datos de entrada disponibles. Cerrando el programa.");
            sc.close();
            System.exit(0);
        }
        return sc.next().trim();
    }

    public static int leerEnteroSeguro() {
        while (true) {
            String entrada = leerToken();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida, ingrese un número entero: ");
            }
        }
    }

    public static int leerEnteroConEtiqueta(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = leerToken();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, debe ingresar un número entero.");
            }
        }
    }

    public static double leerDecimalConEtiqueta(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = leerToken();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, debe ingresar un número (ejemplo: 20.00).");
            }
        }
    }

    public static void cerrar() {
        sc.close();
    }
}

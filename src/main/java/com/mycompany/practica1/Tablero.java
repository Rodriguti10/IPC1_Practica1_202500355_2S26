package com.mycompany.practica1;

import java.util.Random;

/**
 * Representa el tablero completo de 10x10 (vía exterior + 64 espacios
 * internos), la posición de la entrada y la salida, y el cálculo de la
 * ruta más corta entre ambas por el borde exterior.
 */
public class Tablero {

    public static final int TAMAÑO_INTERNO = 8;      // 8 x 8 espacios internos
    public static final int TAMAÑO_COMPLETO = 10;    // 10 x 10 tablero completo

    // Tablero completo (índices 1..10 utilizados; el índice 0 se ignora)
    private char[][] celdas = new char[TAMAÑO_COMPLETO + 1][TAMAÑO_COMPLETO + 1];

    // Posición de la entrada y la salida sobre la vía exterior (coordenadas
    // del tablero completo, en el rango 1..10)
    private int entradaFila, entradaColumna;
    private int salidaFila, salidaColumna;

    private Random rand = new Random();

    public Tablero() {
        inicializar();
        generarEntradaSalida();
    }

    private void inicializar() {
        for (int f = 1; f <= TAMAÑO_COMPLETO; f++) {
            for (int c = 1; c <= TAMAÑO_COMPLETO; c++) {
                boolean esBorde = (f == 1 || f == TAMAÑO_COMPLETO || c == 1 || c == TAMAÑO_COMPLETO);
                celdas[f][c] = esBorde ? '=' : 'L';
            }
        }
    }

    /** Genera aleatoriamente la entrada y la salida sobre el borde, sin usar esquinas. */
    private void generarEntradaSalida() {
        int[] pos = generarPosicionBorde();
        entradaFila = pos[0];
        entradaColumna = pos[1];

        do {
            pos = generarPosicionBorde();
            salidaFila = pos[0];
            salidaColumna = pos[1];
        } while (salidaFila == entradaFila && salidaColumna == entradaColumna);

        celdas[entradaFila][entradaColumna] = 'E';
        celdas[salidaFila][salidaColumna] = 'S';
    }

    /** Devuelve una posición aleatoria sobre el borde del tablero, excluyendo las esquinas. */
    private int[] generarPosicionBorde() {
        int lado = rand.nextInt(4); // 0 arriba, 1 abajo, 2 izquierda, 3 derecha
        int fila, columna;
        switch (lado) {
            case 0:
                fila = 1;
                columna = 2 + rand.nextInt(TAMAÑO_COMPLETO - 2); // 2..9
                break;
            case 1:
                fila = TAMAÑO_COMPLETO;
                columna = 2 + rand.nextInt(TAMAÑO_COMPLETO - 2);
                break;
            case 2:
                columna = 1;
                fila = 2 + rand.nextInt(TAMAÑO_COMPLETO - 2);
                break;
            default:
                columna = TAMAÑO_COMPLETO;
                fila = 2 + rand.nextInt(TAMAÑO_COMPLETO - 2);
                break;
        }
        return new int[]{fila, columna};
    }

    /** Marca como ocupada (A) la celda correspondiente a una fila/columna interna (1-8). */
    public void marcarOcupado(int fila, int columna) {
        celdas[fila + 1][columna + 1] = 'A';
    }

    /** Marca como libre (L) la celda correspondiente a una fila/columna interna (1-8). */
    public void marcarLibre(int fila, int columna) {
        celdas[fila + 1][columna + 1] = 'L';
    }

    /** Imprime el tablero completo de 10x10 junto con el conteo de espacios. */
    public void mostrar(int ocupados, int libres) {
        System.out.println("E = Entrada     S = Salida      = = Vía exterior");
        System.out.println("L = Lugar libre A = Automóvil");
        System.out.println();

        // Encabezado de columnas: solo se numeran las 8 columnas internas
        // (1-8). Las columnas de la vía exterior se dejan en blanco.
        System.out.print("   ");
        for (int c = 1; c <= TAMAÑO_COMPLETO; c++) {
            if (c == 1 || c == TAMAÑO_COMPLETO) {
                System.out.print("  ");
            } else {
                System.out.print((c - 1) + " ");
            }
        }
        System.out.println();

        for (int f = 1; f <= TAMAÑO_COMPLETO; f++) {
            // Igual que con las columnas, solo se numeran las 8 filas
            // internas (1-8); las filas de la vía exterior no llevan número.
            if (f == 1 || f == TAMAÑO_COMPLETO) {
                System.out.print("   ");
            } else {
                System.out.printf("%2d ", f - 1);
            }
            for (int c = 1; c <= TAMAÑO_COMPLETO; c++) {
                System.out.print(celdas[f][c] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Espacios ocupados: " + ocupados);
        System.out.println("Espacios libres:   " + libres);
    }

    /** Compara el recorrido horario y antihorario entre la entrada y la salida. */
    public void mostrarRutaMasCorta() {
        int[][] perimetro = construirPerimetro();
        int total = perimetro.length;

        int idxEntrada = indiceEnPerimetro(perimetro, entradaFila, entradaColumna);
        int idxSalida = indiceEnPerimetro(perimetro, salidaFila, salidaColumna);

        int horario = (idxSalida - idxEntrada + total) % total;
        int antihorario = total - horario;

        System.out.println("Entrada: fila " + entradaFila + ", columna " + entradaColumna);
        System.out.println("Salida: fila " + salidaFila + ", columna " + salidaColumna);

        if (horario == antihorario) {
            System.out.println("Ambas rutas tienen la misma distancia (" + horario + " posiciones). Puede utilizar cualquiera de las dos.");
        } else if (horario < antihorario) {
            System.out.println("Ruta recomendada: sentido horario (" + horario + " posiciones)");
            System.out.println("Ruta alterna: sentido antihorario (" + antihorario + " posiciones)");
        } else {
            System.out.println("Ruta recomendada: sentido antihorario (" + antihorario + " posiciones)");
            System.out.println("Ruta alterna: sentido horario (" + horario + " posiciones)");
        }
    }

    /** Construye, en orden horario, las coordenadas del borde exterior (36 celdas). */
    private int[][] construirPerimetro() {
        int total = 4 * (TAMAÑO_COMPLETO - 1); // 36 celdas para un borde de 10x10
        int[][] perimetro = new int[total][2];
        int idx = 0;

        // Fila superior: de columna 1 a 10
        for (int c = 1; c <= TAMAÑO_COMPLETO; c++) {
            perimetro[idx][0] = 1;
            perimetro[idx][1] = c;
            idx++;
        }
        // Columna derecha: de fila 2 a 10
        for (int f = 2; f <= TAMAÑO_COMPLETO; f++) {
            perimetro[idx][0] = f;
            perimetro[idx][1] = TAMAÑO_COMPLETO;
            idx++;
        }
        // Fila inferior: de columna 9 a 1
        for (int c = TAMAÑO_COMPLETO - 1; c >= 1; c--) {
            perimetro[idx][0] = TAMAÑO_COMPLETO;
            perimetro[idx][1] = c;
            idx++;
        }
        // Columna izquierda: de fila 9 a 2
        for (int f = TAMAÑO_COMPLETO - 1; f >= 2; f--) {
            perimetro[idx][0] = f;
            perimetro[idx][1] = 1;
            idx++;
        }
        return perimetro;
    }

    private int indiceEnPerimetro(int[][] perimetro, int fila, int columna) {
        for (int i = 0; i < perimetro.length; i++) {
            if (perimetro[i][0] == fila && perimetro[i][1] == columna) {
                return i;
            }
        }
        return -1;
    }
}

package com.mycompany.practica1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Sistema de Estacionamiento de Vehículos - IPC1
 * Punto de entrada del programa: muestra el menú principal y delega cada
 * opción a la clase Estacionamiento.
 */
public class Practica1 {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Estacionamiento estacionamiento = new Estacionamiento();

        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.leerEnteroSeguro();
            switch (opcion) {
                case 1: estacionamiento.ingresarVehiculo(); break;
                case 2: estacionamiento.retirarVehiculo(); break;
                case 3: estacionamiento.mostrarEstacionamiento(); break;
                case 4: estacionamiento.buscarVehiculoPorPlaca(); break;
                case 5: estacionamiento.mostrarRutaMasCorta(); break;
                case 6: estacionamiento.mostrarIngresos(); break;
                case 7: System.out.println("Gracias por usar el sistema. ¡Hasta luego!"); break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 7);

        Lector.cerrar();
    }

    private static void mostrarMenu() {
        System.out.println("===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehículo");
        System.out.println("2. Retirar vehículo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehículo por placa");
        System.out.println("5. Mostrar ruta más corta entre entrada y salida");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }
}

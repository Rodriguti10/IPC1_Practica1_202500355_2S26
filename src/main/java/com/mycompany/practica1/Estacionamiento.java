package com.mycompany.practica1;

/**
 * Clase principal de lógica de negocio: coordina el Tablero, la Caja y el
 * arreglo de vehículos estacionados. Expone las 6 operaciones del menú.
 */
public class Estacionamiento {

    private static final int MAX_VEHICULOS = 64;

    private Tablero tablero = new Tablero();
    private Caja caja = new Caja();

    // Arreglo nativo de vehículos. Un espacio libre se identifica con
    // vehiculos[i] == null.
    private Vehiculo[] vehiculos = new Vehiculo[MAX_VEHICULOS];
    private int ocupados = 0;

    // -----------------------------------------------------------
    // 1. Ingresar vehículo
    // -----------------------------------------------------------
    public void ingresarVehiculo() {
        if (ocupados >= MAX_VEHICULOS) {
            System.out.println("El estacionamiento está lleno. No se pueden ingresar más vehículos.");
            return;
        }

        String placa = leerPlacaValidaNoDuplicada();
        int[] posicion = leerFilaColumnaLibre();
        int fila = posicion[0];
        int columna = posicion[1];

        double cambio = caja.cobrar();

        int indiceLibre = buscarIndiceLibre();
        vehiculos[indiceLibre] = new Vehiculo(placa, fila, columna);
        tablero.marcarOcupado(fila, columna);
        ocupados++;

        System.out.printf("Cambio: Q%.2f%n", cambio);
        System.out.println("Vehículo ingresado correctamente.");
    }

    // -----------------------------------------------------------
    // 2. Retirar vehículo
    // -----------------------------------------------------------
    public void retirarVehiculo() {
        String placa = leerPlacaValida();
        int indice = buscarIndicePlaca(placa);
        if (indice == -1) {
            System.out.println("No se encontró ningún vehículo con esa placa.");
            return;
        }
        Vehiculo v = vehiculos[indice];
        tablero.marcarLibre(v.getFila(), v.getColumna());
        vehiculos[indice] = null;
        ocupados--;

        System.out.println("Vehículo retirado correctamente.");
        System.out.println("Fila: " + v.getFila());
        System.out.println("Columna: " + v.getColumna());
    }

    // -----------------------------------------------------------
    // 3. Mostrar estacionamiento
    // -----------------------------------------------------------
    public void mostrarEstacionamiento() {
        tablero.mostrar(ocupados, MAX_VEHICULOS - ocupados);
    }

    // -----------------------------------------------------------
    // 4. Buscar vehículo por placa
    // -----------------------------------------------------------
    public void buscarVehiculoPorPlaca() {
        String placa = leerPlacaValida();
        int indice = buscarIndicePlaca(placa);
        if (indice == -1) {
            System.out.println("Vehículo no encontrado.");
            return;
        }
        Vehiculo v = vehiculos[indice];
        System.out.println("Vehículo encontrado.");
        System.out.println("Fila: " + v.getFila());
        System.out.println("Columna: " + v.getColumna());
    }

    // -----------------------------------------------------------
    // 5. Mostrar ruta más corta
    // -----------------------------------------------------------
    public void mostrarRutaMasCorta() {
        tablero.mostrarRutaMasCorta();
    }

    // -----------------------------------------------------------
    // 6. Mostrar ingresos
    // -----------------------------------------------------------
    public void mostrarIngresos() {
        caja.mostrarIngresos();
    }

    // -----------------------------------------------------------
    // Métodos privados de apoyo
    // -----------------------------------------------------------

    /** Pide una placa hasta que tenga formato válido y no esté ya registrada. */
    private String leerPlacaValidaNoDuplicada() {
        String placa;
        while (true) {
            System.out.print("Placa: ");
            placa = Lector.leerToken();
            if (!ValidadorPlaca.validar(placa)) {
                System.out.println("Placa inválida. Formato requerido: P###LLL (ej. P401JZQ).");
                continue;
            }
            if (buscarIndicePlaca(placa) != -1) {
                System.out.println("Esa placa ya se encuentra registrada en el estacionamiento.");
                continue;
            }
            return placa;
        }
    }

    /** Pide una placa solo verificando el formato (para retirar/buscar). */
    private String leerPlacaValida() {
        String placa;
        while (true) {
            System.out.print("Ingrese la placa: ");
            placa = Lector.leerToken();
            if (!ValidadorPlaca.validar(placa)) {
                System.out.println("Placa inválida. Formato requerido: P###LLL (ej. P401JZQ).");
                continue;
            }
            return placa;
        }
    }

    /** Pide fila y columna (1-8) validando rango y que el espacio esté libre. */
    private int[] leerFilaColumnaLibre() {
        int fila, columna;
        while (true) {
            fila = Lector.leerEnteroConEtiqueta("Fila: ");
            if (fila < 1 || fila > Tablero.TAMAÑO_INTERNO) {
                System.out.println("Fila fuera de rango. Debe estar entre 1 y 8.");
                continue;
            }
            columna = Lector.leerEnteroConEtiqueta("Columna: ");
            if (columna < 1 || columna > Tablero.TAMAÑO_INTERNO) {
                System.out.println("Columna fuera de rango. Debe estar entre 1 y 8.");
                continue;
            }
            if (posicionOcupada(fila, columna)) {
                System.out.println("Ese espacio ya está ocupado. Elija otra posición.");
                continue;
            }
            return new int[]{fila, columna};
        }
    }

    private boolean posicionOcupada(int fila, int columna) {
        for (int i = 0; i < MAX_VEHICULOS; i++) {
            if (vehiculos[i] != null && vehiculos[i].getFila() == fila && vehiculos[i].getColumna() == columna) {
                return true;
            }
        }
        return false;
    }

    private int buscarIndicePlaca(String placa) {
        for (int i = 0; i < MAX_VEHICULOS; i++) {
            if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placa)) {
                return i;
            }
        }
        return -1;
    }

    private int buscarIndiceLibre() {
        for (int i = 0; i < MAX_VEHICULOS; i++) {
            if (vehiculos[i] == null) {
                return i;
            }
        }
        return -1; // no debería ocurrir porque ya se validó espacio disponible
    }
}

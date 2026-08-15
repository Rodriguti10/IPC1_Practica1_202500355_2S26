package com.mycompany.practica1;

/**
 * Administra el cobro de la tarifa fija y lleva el registro de los
 * ingresos acumulados por el estacionamiento.
 */
public class Caja {

    public static final double TARIFA = 10.00; // Q10.00 por vehículo

    private int vehiculosCobrados = 0;
    private double totalRecaudado = 0.0;

    /**
     * Cobra la tarifa fija, validando que el monto entregado no sea
     * negativo ni insuficiente, y devuelve el cambio correspondiente.
     */
    public double cobrar() {
        System.out.println("Tarifa: Q" + String.format("%.2f", TARIFA));
        double monto;
        while (true) {
            monto = Lector.leerDecimalConEtiqueta("Ingrese el monto entregado: Q");
            if (monto < 0) {
                System.out.println("No se aceptan montos negativos.");
                continue;
            }
            if (monto < TARIFA) {
                System.out.println("Pago insuficiente. Debe entregar al menos Q" + String.format("%.2f", TARIFA));
                continue;
            }
            break;
        }
        vehiculosCobrados++;
        totalRecaudado += TARIFA;
        return monto - TARIFA;
    }

    public void mostrarIngresos() {
        System.out.println("===== INGRESOS =====");
        System.out.println("Vehículos cobrados: " + vehiculosCobrados);
        System.out.printf("Tarifa por vehículo: Q%.2f%n", TARIFA);
        System.out.printf("Total recaudado: Q%.2f%n", totalRecaudado);
    }
}

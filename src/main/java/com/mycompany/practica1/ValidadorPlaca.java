package com.mycompany.practica1;

/**
 * Valida el formato de las placas: P###LLL
 * - Exactamente 7 caracteres.
 * - Posición 0: letra 'P' mayúscula.
 * - Posiciones 1-3: dígitos 0-9.
 * - Posiciones 4-6: letras mayúsculas (no se aceptan minúsculas).
 */
public class ValidadorPlaca {

    public static boolean validar(String placa) {
        if (placa == null || placa.length() != 7) {
            return false;
        }
        if (placa.charAt(0) != 'P') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(placa.charAt(i))) {
                return false;
            }
        }
        for (int i = 4; i <= 6; i++) {
            char c = placa.charAt(i);
            if (!Character.isUpperCase(c) || !Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}

package nutricion;

import javax.swing.*;

public class Nutricion {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioDatosPersona frame = new FormularioDatosPersona();
            frame.setVisible(true);
        });
    }
}
package nutricion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class FormularioDatosPersona extends JFrame {
    // Componentes principales
    private JRadioButton radioMasculino;
    private JRadioButton radioFemenino;
    private JTextField txtEdad;
    private JTextField txtEstatura;
    private JTextField txtPeso;
    private JButton btnBuscar;
    private JTextArea txtResultado;

    public FormularioDatosPersona() {
        // Configuración básica de la ventana
        setTitle("Formulario de Datos Personales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Panel principal con padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel de formulario usando GridBagLayout para mayor control
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 1. Configuración del selector de sexo
        JLabel lblSexo = new JLabel("Sexo:");
        radioMasculino = new JRadioButton("Masculino");
        radioFemenino = new JRadioButton("Femenino");
        radioMasculino.setSelected(true); // Opción por defecto

        // Grupo de botones para asegurar selección única
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(radioMasculino);
        grupoSexo.add(radioFemenino);

        // Panel para los radio buttons
        JPanel panelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSexo.add(radioMasculino);
        panelSexo.add(radioFemenino);

        // Añadir componentes de sexo al formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblSexo, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(panelSexo, gbc);

        // 2. Campo para la edad
        JLabel lblEdad = new JLabel("Edad (años):");
        txtEdad = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(lblEdad, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtEdad, gbc);

        // 3. Campo para la estatura
        JLabel lblEstatura = new JLabel("Estatura (metros):");
        txtEstatura = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(lblEstatura, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtEstatura, gbc);

        // 4. Campo para el peso
        JLabel lblPeso = new JLabel("Peso (kilogramos):");
        txtPeso = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(lblPeso, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtPeso, gbc);

        // Panel para el botón centrado
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(100, 30));
        panelBoton.add(btnBuscar);

        // Añadir los paneles al panel principal
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(panelBoton, BorderLayout.CENTER);

        // Añadir el panel principal a la ventana
        add(mainPanel);

        // Configurar la acción del botón
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDatos();
            }
        });
    }

    private void buscarDatos() {
        try {
            // Obtener los datos ingresados
            String sexo = radioMasculino.isSelected() ? "Masculino" : "Femenino";
            int edad = Integer.parseInt(txtEdad.getText());
            double estatura = Double.parseDouble(txtEstatura.getText());
            double peso = Double.parseDouble(txtPeso.getText());

            // Calcular el IMC (Índice de Masa Corporal) como ejemplo
            double tmb;
            if(sexo.equals("Masculino")) {
                tmb = 66.5 + (13.75 * peso) + (5.003 * estatura * 100) - (6.75 * edad);
            }else{
                tmb = 655.1 + (9.563 * peso) + (1.85 * estatura * 100) - (4.676 * edad);
            }

            GUIFrame frame = new GUIFrame((int)tmb);
            frame.setVisible(true);


        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese valores numéricos válidos para edad, estatura y peso.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
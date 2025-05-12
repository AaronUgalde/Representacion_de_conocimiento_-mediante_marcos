package nutricion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.util.*;
import java.util.stream.Stream;
import org.jpl7.*;
import java.io.File;

public class GUIFrame extends JFrame {
    private final JLabel[] desayuno;
    private final JLabel[] almuerzo;
    private final JLabel[] comida;
    private final JLabel[] merienda;
    private final JLabel[] cena;
    private final JTextArea resultadosArea;
    private final JLabel jMenu = new JLabel();
    private final JButton firstButton;
    private final JButton prevButton;
    private final JButton nextButton;
    private final JButton lastButton;
    private List<MenuDieta> menus = new ArrayList<>();
    private int currentIndex = 0;
    private int gasto;

    private final JPanel breakfastPanel;
    private final JPanel lunchPanel;
    private final JPanel comidaPanel;
    private final JPanel snackPanel;
    private final JPanel dinnerPanel;

    // Colores del tema Wii Fit
    private final Color WII_BLUE = new Color(130, 200, 255);
    private final Color WII_LIGHT_BLUE = new Color(200, 230, 255);
    private final Color WII_GREEN = new Color(120, 210, 170);
    private final Color WII_LIGHT_GREEN = new Color(200, 255, 230);
    private final Color WII_BACKGROUND = new Color(245, 250, 255);
    private final Color WII_TEXT = new Color(50, 70, 90);

    public GUIFrame(int gasto) {
        this.gasto = gasto;

        setTitle("Planificador de Nutrición Wii Fit");
        setSize(870, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, WII_BACKGROUND, 0, getHeight(), WII_LIGHT_BLUE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        getContentPane().setLayout(null);

        // Encabezado
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, WII_BLUE, getWidth(), 0, WII_GREEN);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(10, 10, 820, 60);
        getContentPane().add(headerPanel);

        JLabel titleLabel = new JLabel("Planificador de Nutrición Wii Fit");
        titleLabel.setFont(new Font("Meiryo", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 10, 500, 40);
        headerPanel.add(titleLabel);

        // Contador de menús
        jMenu.setFont(new Font("Meiryo", Font.BOLD, 14));
        jMenu.setForeground(Color.WHITE);
        jMenu.setBounds(650, 15, 150, 30);
        headerPanel.add(jMenu);

        // Imagen de la entrenadora de Wii Fit
        JLabel imag = new JLabel();
        ImageIcon icono = new ImageIcon("img/wiifit_trainer.png");
        Image imagen = icono.getImage();
        int w = imagen.getWidth(null);
        int h = imagen.getHeight(null);
        if (w > 0 && h > 0) {
            double scale = Math.min(230.0/w, 350.0/h);
            int newWidth = (int)(w * scale);
            int newHeight = (int)(h * scale);
            imagen = imagen.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            imag.setIcon(new ImageIcon(imagen));
            imag.setBounds(600, 90, newWidth, newHeight);
            getContentPane().add(imag);
        }

        JPanel caloriesPanel = createRoundedPanel(WII_LIGHT_GREEN, WII_GREEN);
        caloriesPanel.setBounds(20, 80, 200, 50);
        getContentPane().add(caloriesPanel);

        JLabel caloriesLabel = new JLabel("Calorías: " + gasto);
        caloriesLabel.setFont(new Font("Meiryo", Font.BOLD, 16));
        caloriesLabel.setForeground(WII_TEXT);
        caloriesPanel.add(caloriesLabel);

        resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        resultadosArea.setLineWrap(true);
        resultadosArea.setWrapStyleWord(true);
        resultadosArea.setFont(new Font("Meiryo", Font.PLAIN, 14));
        resultadosArea.setBackground(WII_LIGHT_BLUE);
        resultadosArea.setForeground(WII_TEXT);
        resultadosArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane panelResultado = new JScrollPane(resultadosArea);
        panelResultado.setBounds(20, 140, 540, 100);
        panelResultado.setBorder(new CompoundBorder(
                new LineBorder(WII_BLUE, 2, true),
                new EmptyBorder(5, 5, 5, 5)
        ));
        getContentPane().add(panelResultado);

        String[] mealLabels = {"Desayuno", "Almuerzo", "Comida", "Merienda", "Cena"};
        int[] yPos = {250, 320, 390, 460, 530};

        for (int i = 0; i < mealLabels.length; i++) {
            JPanel labelPanel = createRoundedPanel(WII_BLUE, WII_BLUE.darker());
            labelPanel.setBounds(20, yPos[i], 120, 30);
            getContentPane().add(labelPanel);

            JLabel label = new JLabel(mealLabels[i]);
            label.setFont(new Font("Meiryo", Font.BOLD, 14));
            label.setForeground(Color.WHITE);
            labelPanel.add(label);
        }

        breakfastPanel = createFoodPanel(4);
        lunchPanel = createFoodPanel(4);
        comidaPanel = createFoodPanel(3);
        snackPanel = createFoodPanel(2);
        dinnerPanel = createFoodPanel(3);

        JPanel[] panels = {breakfastPanel, lunchPanel, comidaPanel, snackPanel, dinnerPanel};
        int[] panelY = {280, 350, 420, 490, 560};
        int[] panelW = {500, 500, 400, 250, 400};
        int[] panelH = {40, 40, 40, 40, 40};

        for (int i = 0; i < panels.length; i++) {
            panels[i].setBounds(20, panelY[i], panelW[i], panelH[i]);
            getContentPane().add(panels[i]);
        }

        desayuno = new JLabel[4];
        almuerzo = new JLabel[4];
        comida = new JLabel[3];
        merienda = new JLabel[2];
        cena = new JLabel[3];

        initLabels(desayuno, breakfastPanel);
        initLabels(almuerzo, lunchPanel);
        initLabels(comida, comidaPanel);
        initLabels(merienda, snackPanel);
        initLabels(cena, dinnerPanel);

        JPanel navigationPanel = createRoundedPanel(WII_LIGHT_BLUE, WII_BLUE);
        navigationPanel.setBounds(20, 610, 550, 50);
        navigationPanel.setLayout(new GridLayout(1, 4, 10, 0));
        getContentPane().add(navigationPanel);

        firstButton = createNavigationButton("Primero", WII_GREEN);
        prevButton = createNavigationButton("Anterior", WII_GREEN);
        nextButton = createNavigationButton("Siguiente", WII_GREEN);
        lastButton = createNavigationButton("Último", WII_GREEN);

        navigationPanel.add(firstButton);
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(lastButton);

        firstButton.addActionListener(e -> mostrarPrimero());
        prevButton.addActionListener(e -> mostrarAnterior());
        nextButton.addActionListener(e -> mostrarSiguiente());
        lastButton.addActionListener(e -> mostrarUltimo());

        buscarMenus();
    }

    private JPanel createRoundedPanel(Color backgroundColor, Color borderColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(backgroundColor);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2d.setColor(borderColor);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createFoodPanel(int columns) {
        JPanel panel = new JPanel(new GridLayout(1, columns, 10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(WII_LIGHT_BLUE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2d.setColor(WII_BLUE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private JButton createNavigationButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Meiryo", Font.BOLD, 14));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    private void initLabels(JLabel[] labels, JPanel panel) {
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            panel.add(labels[i]);
        }
    }

    private void buscarMenus() {
        resultadosArea.setText("");
        menus.clear();
        try {
            File knowledgeFile = new File("knowledge.pl");
            if (!knowledgeFile.exists()) {
                resultadosArea.setText("Error: No se encuentra el archivo knowledge.pl");
                return;
            }

            Query consult = new Query("consult('knowledge.pl')");
            if (!consult.hasSolution()) {
                resultadosArea.setText("Error: No se pudo cargar la base de conocimientos");
                return;
            }

            String queryStr = String.format("dietas(%d, Menus)", gasto);
            Query q = new Query(queryStr);

            try {
                Map<String, Term>[] solutions = q.allSolutions();

                if (solutions == null || solutions.length == 0) {
                    resultadosArea.setText("No se encontraron menús para el gasto calórico especificado: " + gasto);
                    return;
                }

                for (Map<String, Term> solution : solutions) {
                    if (solution == null || !solution.containsKey("Menus")) {
                        continue;
                    }

                    Term menusTerm = solution.get("Menus");
                    if (!menusTerm.isList()) {
                        continue;
                    }

                    Term[] menuArray = menusTerm.listToTermArray();
                    for (Term menu : menuArray) {
                        try {
                            if (!menu.isCompound() || menu.arity() != 6) {
                                continue;
                            }

                            List<String> mD = new ArrayList<>();
                            List<String> mA = new ArrayList<>();
                            List<String> mC = new ArrayList<>();
                            List<String> mM = new ArrayList<>();
                            List<String> mE = new ArrayList<>();

                            for (int i = 1; i <= 5; i++) {
                                Term argTerm = menu.arg(i);
                                List<String> targetList;
                                switch (i) {
                                    case 1: targetList = mD; break;
                                    case 2: targetList = mA; break;
                                    case 3: targetList = mC; break;
                                    case 4: targetList = mM; break;
                                    case 5: targetList = mE; break;
                                    default: continue;
                                }

                                if (argTerm.isList()) {
                                    Term[] items = argTerm.listToTermArray();
                                    for (Term item : items) {
                                        if (item.isAtom()) {
                                            targetList.add(item.name());
                                        }
                                    }
                                }
                            }

                            int kcal = menu.arg(6).intValue();
                            menus.add(new MenuDieta(mD, mA, mC, mM, mE, kcal));
                        } catch (Exception ex) {
                            System.err.println("Error procesando menú individual: " + ex.getMessage());
                        }
                    }
                }
            } finally {
                q.close();
            }

        } catch (Exception e) {
            resultadosArea.setText("Error al procesar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        currentIndex = 0;
        if (menus.isEmpty()) {
            resultadosArea.setText("No se encontraron menús que cumplan con el criterio.");
        } else {
            mostrarMenuActual();
        }
    }

    private void mostrarMenuActual() {
        if (menus.isEmpty()) return;
        MenuDieta m = menus.get(currentIndex);
        resultadosArea.setText(m.toString());
        jMenu.setText("Menú: " + (currentIndex + 1) + " de " + menus.size());

        Stream.of(desayuno, almuerzo, comida, merienda, cena)
                .forEach(arr -> Stream.of(arr).forEach(lbl -> lbl.setIcon(null)));
        cargarIconos(desayuno, m.getDesayuno());
        cargarIconos(almuerzo, m.getAlmuerzo());
        cargarIconos(comida, m.getComida());
        cargarIconos(merienda, m.getMerienda());
        cargarIconos(cena, m.getCena());
    }

    private void cargarIconos(JLabel[] labels, List<String> alimentos) {
        for (int i = 0; i < alimentos.size() && i < labels.length; i++) {
            ImageIcon icon = new ImageIcon("img/" + alimentos.get(i) + ".jpeg");
            Image img = icon.getImage();
            if (img != null && img.getWidth(null) > 0) {
                img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                // Crear un panel redondo para la imagen
                JPanel roundPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(getBackground());
                        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                        g2.dispose();
                    }
                };
                labels[i].setIcon(new ImageIcon(img));
            }
        }
    }

    private void mostrarPrimero()   { currentIndex = 0; mostrarMenuActual(); }
    private void mostrarAnterior() { if (currentIndex > 0) currentIndex--; mostrarMenuActual(); }
    private void mostrarSiguiente() { if (currentIndex < menus.size() - 1) currentIndex++; mostrarMenuActual(); }
    private void mostrarUltimo()    { currentIndex = menus.size() - 1; mostrarMenuActual(); }
}
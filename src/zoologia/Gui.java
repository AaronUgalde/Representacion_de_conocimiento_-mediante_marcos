package zoologia;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jpl7.Query;

public class Gui extends JFrame {
    private Query q1;
    private String[] clases;
    private String[] properties;
    private JList<String> listaClases;
    private JList<String> listaPropiedades;
    private final DefaultListModel<String> listModel1 = new DefaultListModel<>();
    private final DefaultListModel<String> listModel2 = new DefaultListModel<>();
    private final JTextArea displayArea = new JTextArea();
    private final JTextArea descripcionArea = new JTextArea();
    private final JLabel imagen = new JLabel();
    private final JTextArea propiedadesDisplayArea = new JTextArea();

    public Gui() {
        super("Zoológico");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int width = pantalla.width;
        int height = pantalla.height;
        this.setBounds((width - 865) / 2, (height - 600) / 2, 865, 610);
        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabPan = new JTabbedPane();
        tabPan.setBounds(0, 0, 855, 580);
        this.add(tabPan);
        JPanel tab01 = new JPanel();
        JPanel tab02 = new JPanel();
        JPanel tab03 = new JPanel();

        tabPan.add("Consulta Clases", tab01);
        tabPan.add("Búsqueda por propiedades", tab02);
        tabPan.add("Árbol taxonómico", tab03);

        cargaConocimiento("engine.pl");
        cargaConocimiento("knowledge.pl");

        // Panel de Consulta Clases
        clases = consultaClases();
        for (String clase : clases) listModel1.addElement(clase.trim());
        listaClases = new JList<>(listModel1);
        JLabel et01 = new JLabel("Clases:");
        JLabel et02 = new JLabel("Propiedades:");
        JLabel et03 = new JLabel("Descripción");
        JScrollPane spClases = new JScrollPane(listaClases);
        JScrollPane spProps = new JScrollPane(displayArea);
        JScrollPane spDesc = new JScrollPane(descripcionArea);

        tab01.setLayout(null);
        displayArea.setEditable(false);
        descripcionArea.setEditable(false);
        et01.setBounds(10, 10, 300, 20);
        et02.setBounds(10, 245, 300, 20);
        et03.setBounds(10, 430, 300, 20);
        et01.setFont(new Font("Arial", Font.BOLD, 16));
        et02.setFont(new Font("Arial", Font.BOLD, 16));
        et03.setFont(new Font("Arial", Font.BOLD, 16));
        spClases.setBounds(10, 30, 300, 200);
        listaClases.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        listaClases.setFont(new Font("Consolas", Font.BOLD, 18));
        spProps.setBounds(10, 265, 300, 150);
        displayArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        spDesc.setBounds(10, 450, 300, 85);
        descripcionArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        descripcionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        imagen.setBounds(330, 30, 500, 500);
        imagen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        tab01.add(et01);
        tab01.add(et02);
        tab01.add(et03);
        tab01.add(spClases);
        tab01.add(spProps);
        tab01.add(spDesc);
        tab01.add(imagen);
        listaClases.addListSelectionListener(evt -> gestionaClases(evt));
        listaClases.setSelectedIndex(0);

        // Panel de Búsqueda por propiedades
        properties = consultaTodasPropiedades();
        for (String prop : properties) listModel2.addElement(prop.trim());
        listaPropiedades = new JList<>(listModel2);
        JScrollPane spTodas = new JScrollPane(listaPropiedades);
        JLabel et04 = new JLabel("Todas las Propiedades");

        tab02.setLayout(null);
        et04.setBounds(10, 10, 300, 20);
        et04.setFont(new Font("Arial", Font.BOLD, 16));
        spTodas.setBounds(10, 30, 400, 500);
        listaPropiedades.setFont(new Font("Consolas", Font.BOLD, 18));
        listaPropiedades.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        propiedadesDisplayArea.setEditable(false);
        propiedadesDisplayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane spResProps = new JScrollPane(propiedadesDisplayArea);
        spResProps.setBounds(420, 30, 400, 500);
        tab02.add(et04);
        tab02.add(spTodas);
        tab02.add(spResProps);
        listaPropiedades.addListSelectionListener(evt -> gestionaPropiedades(evt));
        listaPropiedades.setSelectedIndex(0);

        // Panel del árbol taxonómico
        tab03.setLayout(null);
        DefaultMutableTreeNode root = buildTaxonomyTree();
        JTree tree = new JTree(root);
        JScrollPane spTree = new JScrollPane(tree);
        spTree.setBounds(0, 0, 840, 550);
        tab03.add(spTree);

        // Handle the X-Button
        class MyWindowAdapter extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent eventObject) {
                goodBye();
            }
        }
        addWindowListener(new MyWindowAdapter());
    }

    private DefaultMutableTreeNode buildTaxonomyTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("reino_animalia");
        DefaultMutableTreeNode filo = new DefaultMutableTreeNode("filo_arthropoda"); root.add(filo);
        DefaultMutableTreeNode clase = new DefaultMutableTreeNode("clase_insecta"); filo.add(clase);
        DefaultMutableTreeNode orden = new DefaultMutableTreeNode("orden_hymenoptera"); clase.add(orden);
        DefaultMutableTreeNode familia = new DefaultMutableTreeNode("familia_formicidae"); orden.add(familia);
        // Myrmicinae
        DefaultMutableTreeNode subFam1 = new DefaultMutableTreeNode("subfamilia_myrmicinae"); familia.add(subFam1);
        DefaultMutableTreeNode genAtta = new DefaultMutableTreeNode("genero_atta"); subFam1.add(genAtta);
        genAtta.add(new DefaultMutableTreeNode("att_cephalotes"));
        genAtta.add(new DefaultMutableTreeNode("att_texana"));
        genAtta.add(new DefaultMutableTreeNode("att_sexdens"));
        DefaultMutableTreeNode genSolen = new DefaultMutableTreeNode("genero_solenopsis"); subFam1.add(genSolen);
        genSolen.add(new DefaultMutableTreeNode("sol_invicta"));
        genSolen.add(new DefaultMutableTreeNode("sol_geminata"));
        // Formicinae
        DefaultMutableTreeNode subFam2 = new DefaultMutableTreeNode("subfamilia_formicinae"); familia.add(subFam2);
        DefaultMutableTreeNode genFormica = new DefaultMutableTreeNode("genero_formica"); subFam2.add(genFormica);
        genFormica.add(new DefaultMutableTreeNode("for_rufa"));
        genFormica.add(new DefaultMutableTreeNode("for_polyctena"));
        genFormica.add(new DefaultMutableTreeNode("for_fusca"));
        DefaultMutableTreeNode genCampono = new DefaultMutableTreeNode("genero_camponotus"); subFam2.add(genCampono);
        genCampono.add(new DefaultMutableTreeNode("cam_pennsylvanicus"));
        genCampono.add(new DefaultMutableTreeNode("cam_rufipes"));
        genCampono.add(new DefaultMutableTreeNode("cam_castaneus"));
        // Dorylinae
        DefaultMutableTreeNode subFam3 = new DefaultMutableTreeNode("subfamilia_dorylinae"); familia.add(subFam3);
        DefaultMutableTreeNode genEciton = new DefaultMutableTreeNode("genero_eciton"); subFam3.add(genEciton);
        genEciton.add(new DefaultMutableTreeNode("ecit_burchellii"));
        genEciton.add(new DefaultMutableTreeNode("ecit_hamatum"));
        genEciton.add(new DefaultMutableTreeNode("ecit_lucanoides"));
        return root;
    }

    private void gestionaClases(ListSelectionEvent ev) {
        String selectedValue = listaClases.getSelectedValue();
        String t = "";
        if (!"top".equals(selectedValue.trim())) {
            for (String sc : consultaSuperclases(selectedValue))
                if (!"top".equals(sc.trim())) t += "es(" + sc.trim() + ")\n";
        }
        for (String p : consultaPropiedades(selectedValue)) t += p.trim() + "\n";
        displayArea.setText(t);
        String str = obtieneDescripcion(selectedValue);
        descripcionArea.setText(str.substring(1, str.length() - 1));
        ImageIcon icon = new ImageIcon("imagenes/" + selectedValue + ".jpeg");
        Image img = icon.getImage().getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(img));
    }

    private void gestionaPropiedades(ListSelectionEvent ev) {
        String selectedValue = listaPropiedades.getSelectedValue();
        q1 = new Query("tiene_propiedad(" + selectedValue + ",L)");
        String[] resultado = {};
        if (q1.hasSolution()) {
            String lista = q1.nextSolution().get("L").toString();
            lista = lista.substring(lista.indexOf('[') + 1, lista.indexOf(']'));
            resultado = lista.split(",");
        }
        StringBuilder sb = new StringBuilder();
        for (String c : resultado) sb.append(c.trim()).append("\n");
        propiedadesDisplayArea.setText(sb.toString());
    }

    // ... resto de métodos (consultaClases, consultaPropiedades, etc.) permanecen sin cambios ...

    private String obtieneDescripcion(String clase)
    {
        String consulta = "obtiene_descripcion(" + clase + ",L)";
        String resultado = "";
        q1 = new Query(consulta);
        if(q1.hasSolution())
        {
            Map soluciones = q1.nextSolution();
            resultado+=soluciones.get("L");
        }
        return resultado;
    }

    private String[] consultaPropiedades(String obj)
    {
        String consulta = "propiedadesc("+ obj+ ", L)";
        //System.out.println(consulta);
        String[] resultado = null;
        q1 = new Query(consulta);
        if(q1.hasSolution())
        {
            String respuesta = "";
            Map soluciones = q1.nextSolution();
            respuesta+=soluciones.get("L");
            resultado = respuesta.substring(respuesta.indexOf("[")+1, respuesta.indexOf("]")).split(",");
        }
        return resultado;
    }

    private String[] consultaSuperclases(String clase)
    {
        String consulta = "superclases_de("+ clase + ", L)";
        //System.out.println(consulta);
        String[] resultado = null;
        q1 = new Query(consulta);
        if(q1.hasSolution())
        {
            String respuesta = "";
            Map soluciones = q1.nextSolution();
            respuesta+=soluciones.get("L");
            resultado = respuesta.substring(respuesta.indexOf("[")+1, respuesta.indexOf("]")).split(",");
        }
        return resultado;
    }

    private String[] consultaTodasPropiedades()
    {
        String consulta = "todas_propiedades(L)";
        String[] resultado = null;
        q1 = new Query(consulta);
        if(q1.hasSolution())
        {
            String respuesta = "";
            Map soluciones = q1.nextSolution();
            respuesta+=soluciones.get("L");
            resultado = respuesta.substring(respuesta.indexOf("[")+1, respuesta.indexOf("]")).split(",");
        }
        return resultado;
    }

    private void cargaConocimiento(String archivo)
    {
        String consulta = "consult('" + "prolog/" + archivo + "')";
        q1 = new Query(consulta); // Se general el Query con el String de consulta
        System.out.println( consulta + " " + (q1.hasSolution() ? "succeeded" : "failed") ); // se realiza la consulta
    }

    private String[] consultaClases()
    {
        String consulta = "clases(L)";
        String[] resultado = null;
        q1 = new Query(consulta);
        if(q1.hasSolution())
        {
            String respuesta = "";
            Map soluciones = q1.nextSolution();
            respuesta+=soluciones.get("L");
            resultado = respuesta.substring(respuesta.indexOf("[")+1, respuesta.indexOf("]")).split(",");
        }
        return resultado;
    }

    private void goodBye()
    {
        int respuesta = JOptionPane.showConfirmDialog(rootPane, "Are you sure?","Exit",JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION) System.exit(0);
    }
}

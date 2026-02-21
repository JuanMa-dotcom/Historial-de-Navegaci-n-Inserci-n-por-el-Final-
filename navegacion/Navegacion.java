/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.navegacion;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author jv134
 */
public class Navegacion extends JFrame {

    private HistorialNavegacion historial;
    private JTextField urlField;
    private JLabel currentUrlLabel;
    private JList<String> historyList;
    private DefaultListModel<String> listModel;
    private JLabel countLabel;

    private final Color BG_DARK      = new Color(15, 17, 26);
    private final Color BG_PANEL     = new Color(22, 26, 40);
    private final Color BG_CARD      = new Color(30, 35, 55);
    private final Color ACCENT_BLUE  = new Color(64, 156, 255);
    private final Color ACCENT_GREEN = new Color(56, 211, 159);
    private final Color TEXT_PRIMARY = new Color(230, 235, 255);
    private final Color TEXT_MUTED   = new Color(120, 130, 165);
    private final Color BORDER_COLOR = new Color(45, 52, 75);

    public Navegacion() {
        historial = new HistorialNavegacion();
        inicializar();
    }

    private void inicializar() {
        setTitle("Navegacion - Lista Doblemente Enlazada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 620);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 0));

        add(crearBarraSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);

        historial.agregarDireccion("https://www.google.com");
        historial.agregarDireccion("https://www.github.com");
        historial.agregarDireccion("https://www.wikipedia.org");
        refrescarVista();
    }

 
    private JPanel crearBarraSuperior() {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(14, 20, 14, 20)));

        JLabel logo = new JLabel("< Navegacion");
        logo.setFont(new Font("Monospaced", Font.BOLD, 17));
        logo.setForeground(ACCENT_BLUE);

        JLabel sub = new JLabel("Lista Doblemente Enlazada - Insercion al Frente");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(TEXT_MUTED);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        titlePanel.setOpaque(false);
        titlePanel.add(logo);
        titlePanel.add(sub);

        urlField = new JTextField("https://");
        urlField.setFont(new Font("Monospaced", Font.PLAIN, 13));
        urlField.setBackground(BG_CARD);
        urlField.setForeground(TEXT_PRIMARY);
        urlField.setCaretColor(ACCENT_BLUE);
        urlField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        urlField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accionNavegar();
            }
        });

        JButton btnNavegar = crearBoton("Navegar >>", ACCENT_BLUE);
        btnNavegar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accionNavegar();
            }
        });

        JPanel urlPanel = new JPanel(new BorderLayout(8, 0));
        urlPanel.setOpaque(false);
        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.add(btnNavegar, BorderLayout.EAST);

        panel.add(titlePanel, BorderLayout.WEST);
        panel.add(urlPanel, BorderLayout.CENTER);
        return panel;
    }

   -
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(16, 0));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panel.add(crearTarjetaActual(), BorderLayout.WEST);
        panel.add(crearPanelHistorial(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTarjetaActual() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        card.setPreferredSize(new Dimension(260, 0));

        JLabel titulo = new JLabel("Pagina Actual");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        titulo.setForeground(ACCENT_BLUE);

        currentUrlLabel = new JLabel(
                "<html><body style='width:210px'>Sin paginas</body></html>");
        currentUrlLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        currentUrlLabel.setForeground(ACCENT_GREEN);

        JLabel desc = new JLabel(
                "<html><span style='color:#7882a5;font-size:10px'>"
                + "Nodo cabeza de la<br>lista enlazada</span></html>");

        JButton btnRetroceder = crearBoton("<< Retroceder", new Color(200, 70, 90));
        btnRetroceder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accionRetroceder();
            }
        });

        JPanel top = new JPanel(new GridLayout(2, 1, 0, 6));
        top.setOpaque(false);
        top.add(titulo);
        top.add(desc);

        card.add(top, BorderLayout.NORTH);
        card.add(currentUrlLabel, BorderLayout.CENTER);
        card.add(btnRetroceder, BorderLayout.SOUTH);
        return card;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG_DARK);

        JLabel titulo = new JLabel(
                "Historial de Navegacion (mas reciente -> mas antiguo)");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        titulo.setForeground(TEXT_PRIMARY);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        listModel = new DefaultListModel<String>();
        historyList = new JList<String>(listModel);
        historyList.setBackground(BG_CARD);
        historyList.setForeground(TEXT_PRIMARY);
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyList.setSelectionBackground(new Color(64, 156, 255, 60));
        historyList.setSelectionForeground(TEXT_PRIMARY);
        historyList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        historyList.setFixedCellHeight(28);

        historyList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setOpaque(true);
                lbl.setFont(new Font("Monospaced", Font.PLAIN, 12));
                lbl.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                if (index == 0) {
                    lbl.setForeground(ACCENT_GREEN);
                    lbl.setBackground(isSelected
                            ? new Color(56, 211, 159, 40)
                            : new Color(56, 211, 159, 15));
                } else {
                    lbl.setForeground(isSelected ? TEXT_PRIMARY : TEXT_MUTED);
                    lbl.setBackground(isSelected
                            ? new Color(64, 156, 255, 40) : BG_CARD);
                }
                return lbl;
            }
        });

        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setBackground(BG_CARD);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        JButton btnLimpiar = crearBoton("Limpiar historial", TEXT_MUTED);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accionLimpiar();
            }
        });

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnLimpiar, BorderLayout.SOUTH);
        return panel;
    }

    //panel
    private JPanel crearBarraEstado() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_PANEL);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(6, 20, 6, 20)));

        countLabel = new JLabel("Nodos en la lista: 0");
        countLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
        countLabel.setForeground(TEXT_MUTED);

        JLabel info = new JLabel(
                "Implementacion: Lista Doblemente Enlazada  |  Insercion al Frente");
        info.setFont(new Font("SansSerif", Font.PLAIN, 11));
        info.setForeground(TEXT_MUTED);

        bar.add(countLabel, BorderLayout.WEST);
        bar.add(info, BorderLayout.EAST);
        return bar;
    }

    //mas y mas botones
    private JButton crearBoton(String texto, final Color color) {
        JButton btn = new JButton(texto) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.darker());
                } else {
                    g2.setColor(new Color(
                            color.getRed(), color.getGreen(), color.getBlue(), 30));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(color);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(color);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return btn;
    }

    //Acciones

    private void accionNavegar() {
        String url = urlField.getText().trim();
        if (url.isEmpty() || url.equals("https://")) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa una URL valida.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        historial.agregarDireccion(url);
        urlField.setText("https://");
        refrescarVista();
    }

    private void accionRetroceder() {
        if (historial.estaVacio()) {
            JOptionPane.showMessageDialog(this,
                    "No hay paginas en el historial.",
                    "Historial vacio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String actual = historial.mostrarActual();
        JOptionPane.showMessageDialog(this,
                "<html>Retrocediste desde:<br><b>" + actual + "</b></html>",
                "Retroceder", JOptionPane.INFORMATION_MESSAGE);
        historial.eliminarCabeza();
        refrescarVista();
    }

    private void accionLimpiar() {
        int op = JOptionPane.showConfirmDialog(this,
                "Limpiar todo el historial?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            historial = new HistorialNavegacion();
            refrescarVista();
        }
    }

    private void refrescarVista() {
        if (historial.estaVacio()) {
            currentUrlLabel.setText(
                    "<html><body style='width:210px;color:#7882a5'>"
                    + "Sin paginas en el historial</body></html>");
        } else {
            currentUrlLabel.setText(
                    "<html><body style='width:210px'>"
                    + historial.mostrarActual() + "</body></html>");
        }
        listModel.clear();
        for (String item : historial.obtenerHistorial()) {
            listModel.addElement(item);
        }
        countLabel.setText("Nodos en la lista: " + historial.getTamanio());
    }

    //Main
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Navegacion().setVisible(true);
            }
        });
    }
}


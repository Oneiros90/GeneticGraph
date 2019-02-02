package gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import util.FileManager;

public class Frame extends JFrame {

    //Dimensione del frame
    private static final Dimension SIZE = new Dimension(1000, 677);
    private Editor drawingPanel;

    //Costruttore
    public Frame() {
        super("GeneticGraph");

        this.setIconImage(FileManager.getImageFromResource("/images/edge.png"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Ottimizzo le dimensioni della finestra
        this.pack();

        //Definisco la dimensione e la posizione del frame
        this.setSizeWithoutInsets(SIZE);
        this.setLocationRelativeTo(null);

        //Imposto il Look And Feel predefinito del sistema operativo
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Impossibile impostare il look and feel: " + ex);
        }

        this.drawingPanel = new Editor();
        this.drawingPanel.setSize(SIZE);
        this.add(this.drawingPanel);
        this.drawingPanel.graphArea.requestFocus();
    }

    /**Funzione di avvio del programma*/
    public static void main(String[] args) {
        Frame f = new Frame();
        f.setVisible(true);

        /*int[] array = {1, 2, 3, 4, 5};
        int con = 1;
        while (true) {
            System.out.print(con + " - ");
            for (int i : array) {
                System.out.print(i + " ");
            }
            System.out.println();
            con++;

            int k = -1;
            for (int i = 1; i < array.length - 1; i++) {
                if (array[i] < array[i + 1] && i > k) {
                    k = i;
                }
            }
            if (k == -1) {
                break;
            }

            int j = -1;
            for (int i = k; i < array.length; i++) {
                if (array[i] > array[k] && i > j) {
                    j = i;
                }
            }

            int swap = array[k];
            array[k] = array[j];
            array[j] = swap;

            int[] lastSeq = new int[array.length - k - 1];
            for (int i = 0; i < lastSeq.length; i++) {
                lastSeq[i] = array[i + k + 1];
            }
            for (int i = 0; i < lastSeq.length; i++) {
                array[i + k + 1] = lastSeq[lastSeq.length - 1 - i];
            }
        }*/
    }

    /**Imposta la grandezza del frame senza considerare i bordi*/
    private void setSizeWithoutInsets(Dimension d) {
        Insets i = this.getInsets();
        this.setSize(d.width + i.left + i.right, d.height + i.top + i.bottom);
    }
}

/**Listener che gestisce il passaggio da un pannello all'altro*/
class SwapPanelListener implements ActionListener {

    private JPanel pFrom;
    private JPanel pTo;

    public SwapPanelListener(JPanel from, JPanel to) {
        this.pFrom = from;
        this.pTo = to;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.pTo.setVisible(true);
        this.pFrom.setVisible(false);
    }
}

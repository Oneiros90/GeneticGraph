package util;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class BGPanel extends JPanel {
    
    private Image image;
    
    public BGPanel(String path) {
        
        //Chiamo il costruttore della classe JPanel senza impostare alcun layout
        super(null);
        
        //Rimuovo l'opacità al pannello
        this.setOpaque(false);
        
        //Imposto l'immagine
        this.setImageFromResource(path);
    }
    
    /**Imposto l'immagine da visualizzare nel pannello a partire dal suo percorso nelle risorse*/
    public final void setImageFromResource(String path) {
        this.image = FileManager.getImageFromResource(path);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            g.drawImage(this.image, 0, 0, this.image.getWidth(null), this.image.getHeight(null), this);
        }
    }
}

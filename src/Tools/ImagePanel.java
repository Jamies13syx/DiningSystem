package Tools;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    Image image;
    int width;
    int height;

    public ImagePanel(Image image){
        this.image = image;
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

    }
}

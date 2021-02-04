package greader.view.renderer;

import javax.swing.*;
import java.awt.*;

public class RoundRectPanel extends JPanel {

    private Color color = new Color(43, 43, 43);

    public RoundRectPanel() {
        super();
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public RoundRectPanel(LayoutManager layoutManager) {
        super(layoutManager);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public RoundRectPanel(LayoutManager layoutManager, Color color) {
        super(layoutManager);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
    }

}

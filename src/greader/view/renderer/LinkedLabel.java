package greader.view.renderer;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class LinkedLabel extends JLabel {

    private final URI uri;

    public LinkedLabel(String text, String uri) {
        this.uri = URI.create(uri);
        setToolTipText(uri);
        super.setText(format(text));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                open(LinkedLabel.this.uri);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to open " + uri + " - your computer is likely misconfigured.\n" +
                                "Error Message: " + e.getMessage(),
                        "Cannot Open Link", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Java is not able to open a browser on your computer.",
                    "Cannot Open Link", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String format(String text) {
        return String.format(
                "<html><span style=\"color: #%02X%02X%02X;\">%s</span></html>", 88, 156, 246, text);
    }

}

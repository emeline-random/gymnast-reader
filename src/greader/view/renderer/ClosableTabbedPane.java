package greader.view.renderer;

import javax.swing.*;
import java.awt.*;

public class ClosableTabbedPane extends JTabbedPane {

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        title = getTitle(title);
        super.addTab(title, icon, component, tip);
        initListener(title);
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        title = getTitle(title);
        super.addTab(title, icon, component);
        initListener(title);
    }

    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, true);
    }

    public void addTab(String title, Component component, boolean closable) {
        title = getTitle(title);
        super.addTab(title, component);
        if (closable) initListener(title);
    }

    private String getTitle(String base) {
        String res = base;
        if (indexOfTab(base) != -1) {
            int i = 1;
            while (indexOfTab(base + " (" + i + ")") != -1) i++;
            res = base + " (" + i + ")";
        }
        return res;
    }

    private void initListener(String title) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(15.0f));
        panel.add(label);
        panel.setOpaque(false);
        JButton button= new JButton(new com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(button.getFont().deriveFont(15.0f));
        button.addActionListener(e -> {
            if (indexOfTab(title) != -1) {
                removeTabAt(indexOfTab(title));
            }
        });
        panel.add(button);
        setTabComponentAt(indexOfTab(title), panel);
    }
}

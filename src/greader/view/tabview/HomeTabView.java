package greader.view.tabview;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatFileViewFileIcon;
import com.formdev.flatlaf.icons.FlatTreeCollapsedIcon;
import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import greader.model.LoadData;
import greader.view.MainFrame;
import greader.view.ParentComponent;
import greader.view.renderer.ClosableTabbedPane;
import greader.view.renderer.LinkedLabel;
import greader.view.renderer.RoundRectPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class HomeTabView extends JPanel {

    private final ClosableTabbedPane tabbedPane;
    private final ParentComponent frame;

    public HomeTabView(ParentComponent frame) {
        super(new FlowLayout());
        this.frame = frame;
        this.tabbedPane = frame.getMainPanel();
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        init();
    }

    private void init() {
        JPanel mainPanel = getMainPanel();
        mainPanel.setAlignmentX(LEFT_ALIGNMENT);
        this.add(mainPanel);
        this.add(Box.createHorizontalStrut(20));
        this.add(getAboutPanel());
        this.add(new JLabel("Recent"));
        this.add(getRecentPanel());
    }

    private JPanel getMainPanel() {
        JPanel panel = new RoundRectPanel();
        panel.setPreferredSize(new Dimension(1100, 250));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        JLabel label = new JLabel("Welcome to Gymnast Reader");
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);
        GridLayout layout = new GridLayout(3, 4);
        layout.setHgap(50);
        layout.setVgap(10);
        JPanel tools = this.getTransparentPanel(layout);
        tools.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tools.add(this.getTransparentPanel(new JLabel(new FlatTreeOpenIcon()), new JLabel("Open file")));
        tools.add(this.getTransparentPanel(new JLabel(new FlatSVGIcon("resources/copy.svg")), new JLabel("Merge files")));
        tools.add(this.getTransparentPanel(new JLabel(new FlatFileViewFileIcon()), new JLabel("Split File")));
        tools.add(new JLabel("<html>Open a pdf file and edit it with Icepdf.</html>"));
        tools.add(new JLabel("<html>Load pdf files and group them into one new pdf.</html>"));
        tools.add(new JLabel("<html>Open a pdf and save only some pages of it in a new file.</html>"));

        tools.add(getMainPanelButton(this.frame.getOpenAction(), new FlatTreeOpenIcon(), "Open"));
        tools.add(getMainPanelButton(this.frame.getMergeAction(), new FlatSVGIcon("resources/copy.svg"), "Merge"));
        tools.add(getMainPanelButton(this.frame.getSplitAction(), new FlatFileViewFileIcon(), "Split"));
        panel.add(tools);
        return panel;
    }

    private JButton getMainPanelButton(ActionListener action, Icon icon, String txt) {
        JButton button = new JButton(txt);
        button.setIcon(icon);
        button.addActionListener(action);
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.setFont(button.getFont().deriveFont(20f));
        return button;
    }

    private JPanel getAboutPanel() {
        JPanel panel = new RoundRectPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(720, 250));

        JLabel title = new JLabel("About this app");
        JLabel about = new JLabel("<html><div style=\"text-align: justify\">" +
                "This app is made with <a href=\"\" style=\"text-decoration: none\"" +
                ">Icepdf</a>, which is an open-source library that allows to create and configure automatically " +
                "the panel used to see files. The application also uses <a href=\"\" style=\"text-decoration: none\"" +
                ">FlatLaf</a> look and feels which allows to have this dark scheme (which is" +
                " IntelliJ Darcula scheme).</div></html>");
        panel.add(title, BorderLayout.NORTH);
        panel.add(about, BorderLayout.CENTER);
        JPanel p1 = this.getTransparentPanel(new GridLayout(1, 2), new JLabel(new FlatTreeCollapsedIcon()),
                new LinkedLabel("Icepdf", "http://www.icesoft.org/java/downloads/icepdf-downloads.jsf"),
                new JLabel(new FlatTreeCollapsedIcon()), new LinkedLabel("FlatLaf", "https://github.com/JFormDesigner/FlatLaf"));
        panel.add(p1, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel getRecentPanel() {
        ArrayList<File> lastFiles = LoadData.getLastFiles();
        int rows = (int) Math.ceil(lastFiles.size() / 4d);
        GridLayout layout = new GridLayout(rows, 4);
        layout.setHgap(10);
        layout.setVgap(10);
        JPanel panel = new RoundRectPanel(layout);
        panel.setPreferredSize(new Dimension(1850, rows * 200));
        for (File f : lastFiles) panel.add(getFilePanel(f));
        for (int i = lastFiles.size(); i < rows * 4; i++) panel.add(new JLabel());
        return panel;
    }

    private JPanel getFilePanel(File f) {
        JPanel littlePanel = new RoundRectPanel(new GridLayout(3, 1), UIManager.getColor("Panel.background"));
        littlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        littlePanel.add(new JLabel(new FlatFileViewFileIcon()));
        JLabel label = new JLabel("<html>" + f.getName() + "</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        littlePanel.add(label);
        JButton open = new JButton("Open");
        open.addActionListener(e -> {
            this.tabbedPane.addTab(f.getName(), PdfTabView.getView(f.getPath()));
            this.tabbedPane.setSelectedIndex(this.tabbedPane.indexOfTab(f.getName()));
        });
        open.putClientProperty("JButton.buttonType", "roundRect");
        littlePanel.add(open);
        return littlePanel;
    }

    private JPanel getTransparentPanel(Component... components) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        for (Component c : components) panel.add(c);
        return panel;
    }

    private JPanel getTransparentPanel(LayoutManager layout, Component... components) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        for (Component c : components) panel.add(c);
        return panel;
    }

}

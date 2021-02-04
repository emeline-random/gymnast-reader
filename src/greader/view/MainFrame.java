package greader.view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatFileViewFileIcon;
import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import greader.model.LoadData;
import greader.view.renderer.ClosableTabbedPane;
import greader.view.tabview.HomeTabView;
import greader.view.tabview.MergeTabView;
import greader.view.tabview.PdfTabView;
import greader.view.tabview.SplitTabView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final ClosableTabbedPane mainPanel = new ClosableTabbedPane();

    public MainFrame() {
        super("Gymnast Reader");
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/resources/GymnastReader.png")));
        } catch (IOException ignored) {
        }
        this.createMenu();
        this.setSize(1500, 800);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainPanel.addTab("Home", new HomeTabView(this), false);
        this.add(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                LoadData.persistFiles();
                e.getWindow().dispose();
            }
        });
    }

    private void createMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu tools = new JMenu("Tools");

        JMenuItem open = new JMenuItem("Open");
        open.setIcon(new FlatTreeOpenIcon());
        open.addActionListener(getOpenAction());
        file.add(open);

        JMenuItem merge = new JMenuItem("Merge PDF");
        merge.setIcon(new FlatSVGIcon("resources/copy.svg"));
        merge.addActionListener(getMergeAction());
        JMenuItem split = new JMenuItem("Split PDF");
        split.setIcon(new FlatFileViewFileIcon());
        split.addActionListener(getSplitAction());
        tools.add(merge);
        tools.add(split);

        bar.add(file);
        bar.add(tools);
        this.add(bar, BorderLayout.NORTH);
    }

    public ActionListener getMergeAction() {
        return e -> {
            int i = 0;
            while (this.mainPanel.indexOfTab("Merge " + i) != -1) i++;
            this.mainPanel.addTab("Merge " + i, new MergeTabView(this.mainPanel));
            this.mainPanel.setSelectedIndex(this.mainPanel.indexOfTab("Merge " + i));
        };
    }

    public ActionListener getSplitAction() {
        return e -> {
            int i = 0;
            while (this.mainPanel.indexOfTab("Split " + i) != -1) i++;
            this.mainPanel.addTab("Split " + i, new SplitTabView(this.getMainPanel()));
            this.mainPanel.setSelectedIndex(this.mainPanel.indexOfTab("Split " + i));
        };
    }

    public ActionListener getOpenAction() {
        return e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setFileFilter(new FileNameExtensionFilter("pdfs", "pdf"));
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(this);
            for (File f : chooser.getSelectedFiles()) {
                this.mainPanel.addTab(f.getName(), PdfTabView.getView(f.getPath()));
            }
        };
    }

    public ClosableTabbedPane getMainPanel() {
        return mainPanel;
    }
}

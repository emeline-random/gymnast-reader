package greader.view.tabview;

import com.formdev.flatlaf.icons.FlatFileChooserNewFolderIcon;
import com.formdev.flatlaf.icons.FlatFileViewFileIcon;
import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import com.itextpdf.text.DocumentException;
import greader.utils.PdfUtils;
import greader.view.FileListModel;
import greader.view.renderer.ComponentRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MergeTabView extends JPanel {

    private final JTabbedPane tabbedPane;
    
    public MergeTabView(JTabbedPane pane) {
        this.tabbedPane = pane;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.init();
    }

    private void init() {
        FileListModel flm = new FileListModel();
        JTable table = new JTable(flm);
        table.setRowHeight(50);
        table.setDefaultRenderer(JButton.class, new ComponentRenderer());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (table.getSelectedRow() >= 0 && table.getSelectedColumn() == 1) {
                    ((FileListModel) table.getModel()).removeFile(table.getSelectedRow());
                }
            }
        });
        this.add(new JScrollPane(table));
        this.add(Box.createVerticalStrut(20));

        JButton add = new JButton("add pdf file");
        add.setIcon(new FlatFileChooserNewFolderIcon());
        add.setAlignmentX(CENTER_ALIGNMENT);
        add.addActionListener(e -> {
            File f = this.getFile(true);
            if (f!= null) flm.addFile(f);
        });
        this.add(add);
        this.add(Box.createVerticalStrut(20));

        AtomicReference<File> outputFile = new AtomicReference<>();
        JPanel fileOutputPanel = new JPanel(new GridLayout(1, 2));
        fileOutputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel fileName = new JLabel("selected output file");
        fileName.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        fileName.setOpaque(true);
        fileName.setBackground(new Color(43, 43, 43));
        JButton browse = new JButton("Browse");
        browse.setIcon(new FlatTreeOpenIcon());
        browse.addActionListener(e -> {
            File f = this.getFile(false);
            if (f != null) {
                fileName.setText(f.getPath());
                outputFile.set(f);
            }
        });
        fileOutputPanel.add(fileName);
        fileOutputPanel.add(browse);
        this.add(fileOutputPanel);
        this.add(Box.createVerticalStrut(20));

        JButton merge = new JButton("Merge");
        merge.setIcon(new FlatFileViewFileIcon());
        merge.setAlignmentX(CENTER_ALIGNMENT);
        merge.addActionListener(e -> {
            if (outputFile.get() != null && flm.getRowCount() > 0) {
                try {
                    PdfUtils.mergePdfs(flm.getFiles(), outputFile.get().getAbsolutePath());
                    this.tabbedPane.addTab(outputFile.get().getName(), PdfTabView.getView(outputFile.get().getPath()));
                    this.tabbedPane.setSelectedIndex(this.tabbedPane.indexOfTab(outputFile.get().getName()));
                } catch (DocumentException | IOException documentException) {
                    JOptionPane.showMessageDialog(this, documentException.getMessage(),
                            "Gymnast Reader", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(merge);
    }

    private File getFile(boolean open) {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("pdfs", "pdf"));
        chooser.setMultiSelectionEnabled(false);
        if (open) chooser.showOpenDialog(this);
        else chooser.showSaveDialog(this);
        return chooser.getSelectedFile();
    }
    
}

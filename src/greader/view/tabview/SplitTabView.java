package greader.view.tabview;

import greader.utils.PdfUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class SplitTabView extends JPanel {

    public SplitTabView(JTabbedPane tabbedPane) {
        JLabel file = new JLabel("        ");
        JLabel dest = new JLabel("        ");
        file.setBackground(new Color(43, 43, 43));
        dest.setBackground(new Color(43, 43, 43));
        file.setOpaque(true);
        dest.setOpaque(true);
        JButton split = new JButton("Split");
        JButton browse1 = new JButton("Browse");
        JButton browse = new JButton("Browse");
        JTextField fInput = new JTextField("1");
        JTextField fOutput = new JTextField("1");
        AtomicReference<File> in = new AtomicReference<>();
        AtomicReference<File> out = new AtomicReference<>();
        JButton help = new JButton();
        help.putClientProperty("JButton.buttonType", "help");
        help.addActionListener(e -> JOptionPane.showMessageDialog(this, "The first page index is 1 ! From and to indexes" +
                " will be included in the output document", "Gymnsat Reader", JOptionPane.INFORMATION_MESSAGE));

        split.addActionListener(e -> {
            try {
                PdfUtils.splitPdf(in.get(), Integer.parseInt(fInput.getText()),
                        Integer.parseInt(fOutput.getText()), out.get().getAbsolutePath());
                tabbedPane.addTab(out.get().getName(), PdfTabView.getView(out.get().getPath()));
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(out.get().getName()));
            } catch (Exception ioException) {
                JOptionPane.showMessageDialog(this, ioException.getMessage(),
                        "Gymnast Reader", JOptionPane.ERROR_MESSAGE);
            }
        });
        addListener(file, browse, in);
        addListener(dest, browse1, out);
        this.add(addAll(new JPanel(), new JLabel("File to split : "), file, browse));
        this.add(addAll(new JPanel(), new JLabel("From page "), fInput, help, new JLabel(" to page "), fOutput));
        this.add(addAll(new JPanel(), new JLabel("Output file : "), dest, browse1));
        this.add(split);
    }

    private void addListener(JLabel output, JButton browse1, AtomicReference<File> out) {
        browse1.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("pdf files", "pdf"));
            chooser.setMultiSelectionEnabled(false);
            chooser.showOpenDialog(this);
            if (chooser.getSelectedFile() != null) {
                out.set(chooser.getSelectedFile());
                output.setText(out.get().getAbsolutePath());
            }
        });
    }

    private static JPanel addAll(JPanel panel, JComponent... components) {
        for (JComponent c : components) panel.add(c);
        return panel;
    }

}

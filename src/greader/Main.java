package greader;

import com.formdev.flatlaf.FlatDarculaLaf;
import greader.model.LoadData;
import greader.view.MainFrame;
import greader.view.tabview.PdfTabView;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        restoreUI();
        MainFrame frame = null;
        int i = 0;
        boolean init = false;
        while (i < args.length) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    showHelp();
                    return;
                case "-s":
                    if ((i = i + 1) < args.length) {
                        LoadData.setSaveFile(args[i]);
                        init = true;
                        frame = new MainFrame();
                    } else {
                        showHelp();
                        return;
                    }
                    break;
                default:
                    if (!init) LoadData.setSaveFile("gymnastReader.txt");
                    if (frame == null) frame = new MainFrame();
                    File f = new File(args[i]);
                    frame.getMainPanel().addTab(f.getName(), PdfTabView.getView(args[i]));
                    frame.getMainPanel().setSelectedIndex(frame.getMainPanel().indexOfTab(f.getName()));
                    LoadData.addFile(f.getAbsolutePath());
            }
            i++;
        }
        if (!init) LoadData.setSaveFile("gymnastReader.txt");
        if (args.length == 0) frame = new MainFrame();
        frame.setVisible(true);
    }

    private static void showHelp() {
        System.out.println("Gymnast Reader : \n\n java -jar PdfMerger.jar [options] [path]\n\n" +
                "Allows to open this pdf viewer, if path is given, the file at the corresponding path" +
                " is opened. \n\n-s path   sets the save file to the path");
    }

    public static void restoreUI() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(20.0f));
        UIManager.put("Button.font", UIManager.getFont("Button.font").deriveFont(20.0f));
        UIManager.put("TableHeader.font", UIManager.getFont("TableHeader.font").deriveFont(20.0f));
        UIManager.put("Table.font", UIManager.getFont("Table.font").deriveFont(20.0f));
        UIManager.put("TabbedPane.font", UIManager.getFont("TabbedPane.font").deriveFont(15.0f));
        UIManager.put("MenuBar.font", UIManager.getFont("MenuBar.font").deriveFont(15.0f));
        UIManager.put("Menu.font", UIManager.getFont("Menu.font").deriveFont(15.0f));
        UIManager.put("MenuItem.font", UIManager.getFont("MenuItem.font").deriveFont(15.0f));
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

}

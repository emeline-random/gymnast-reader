package greader.model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadData {

    private static File FILE;
    private final static int FILES_NUMBER = 12;

    public static void setSaveFile(String path) {
        FILE = new File(path);
        if (!FILE.exists()) {
            try {
                if (!FILE.createNewFile()) throw new IOException();
            } catch (IOException e) {
                showErrorMessage("we cannot create save file at " + path);
            }
        }
    }

    private final static ArrayList<String> files = new ArrayList<>();

    public static ArrayList<File> getLastFiles() {
        ArrayList<File> files = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(FILE);
            while (scanner.hasNextLine()) {
                File file = new File(scanner.nextLine());
                if (file.exists()) files.add(file);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            showErrorMessage("It seems that the file used to load your historic has not been found ...");
        }
        return files;
    }

    public static void persistFiles() {
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE.getAbsolutePath()),
                    Charset.forName("Cp1252")));
            lines.removeIf(files::contains);
            files.removeIf(s -> !(new File(s).exists()));
            if (lines.size() + files.size() > FILES_NUMBER) {
                int start = files.size() -1 - (FILES_NUMBER - files.size());
                lines.subList(start, files.size() - 1);
            }
            files.addAll(lines);
            files.removeIf(s -> !(new File(s).exists()));
            PrintWriter writer = new PrintWriter(FILE);
            for (String file : files) writer.println(file);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("It seems that the file used to save your historic has not been found ...");
        }
    }

    public static void addFile(String path) {
        if (!files.contains(path)) files.add(path);
        if (files.size() > FILES_NUMBER) files.remove(0);
    }

    private static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Gymnast Reader", JOptionPane.ERROR_MESSAGE);
    }

}

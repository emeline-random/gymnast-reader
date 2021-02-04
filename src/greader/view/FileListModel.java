package greader.view;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;

public class FileListModel extends AbstractTableModel {

    private final ArrayList<File> files;
    private final static String[] headers = {"File", "Delete"};

    public FileListModel(){
        this.files = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return files.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return files.get(rowIndex).getAbsolutePath();
            case 1:
                JButton delete = new JButton("Delete");
                delete.addActionListener(e -> this.removeFile(rowIndex));
                return delete;
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        if (columnIndex == 1) {
            return JButton.class;
        }
        return Object.class;
    }

    public void removeFile(int index) {
        this.files.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void addFile (File file) {
        files.add(file);
        fireTableRowsInserted(files.size() -1, files.size() -1);
    }

    public ArrayList<File> getFiles() {
        return files;
    }
}

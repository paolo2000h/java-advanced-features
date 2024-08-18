package example;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class App extends JFrame {
    private JPanel mainPanel;
    private JButton chooseDirectoryButton;
    private JButton saveSnapshotButton;
    private JButton reloadButton;
    private JTable table1;
    private JScrollPane tableScrollPane;
    private JPanel buttonPanel;
    private JPanel labelPanel;
    private JLabel directoryLabel;
    private DefaultTableModel defaultTableModel;
    private FileManager fileManager;
    private String directoryName;

    public App() {
        System.out.println("Inside Java 16");
        setup();

        saveSnapshotButton.addActionListener(e -> {
            fileManager.createCurrentSnapshot(directoryName);
            fileManager.saveSnapshot(directoryName);
            reloadTable();
        });

        chooseDirectoryButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = jFileChooser.showOpenDialog(chooseDirectoryButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                directoryName = jFileChooser.getSelectedFile().getAbsolutePath();
                directoryLabel.setText(directoryName);
                reloadTable();
                reloadButton.setEnabled(true);
                saveSnapshotButton.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(saveSnapshotButton, "No directory chosen");
            }
        });

        reloadButton.addActionListener(e -> reloadTable());
    }

    private void reloadTable() {
        fileManager.generateSnapshotFolder();
        try {
            defaultTableModel.setRowCount(0);
            fileManager.loadSnapshot(directoryName);
            fileManager.createCurrentSnapshot(directoryName);
            HashMap<String, Boolean> files = fileManager.compareSnapshots();
            Vector<String> vector;
            for (Map.Entry<String, Boolean> entry : files.entrySet()) {
                vector = new Vector<>();
                vector.add(entry.getKey());
                if (entry.getValue()) {
                    vector.add("Modified");
                } else {
                    vector.add("Unmodified");
                }
                defaultTableModel.addRow(vector);
                tableScrollPane.setViewportView(table1);
            }
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new App();
            frame.setVisible(true);
        });
    }

    private void setup() {
        this.setTitle("MD5 Snapshot Saver");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(false);
        this.setBounds(100, 100, 500, 500);

        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        chooseDirectoryButton.setText("Choose directory");
        saveSnapshotButton.setText("Save snapshot");
        saveSnapshotButton.setEnabled(false);
        reloadButton.setText("Reload directory");
        reloadButton.setEnabled(false);
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Filename");
        defaultTableModel.addColumn("Status");
        table1.setModel(defaultTableModel);
        table1.setEnabled(false);
        tableScrollPane.setViewportView(table1);
        fileManager = new FileManager();
        directoryLabel.setText("");
    }
}

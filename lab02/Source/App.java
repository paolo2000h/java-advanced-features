package org.lab02;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.lab02.dataProcessing.ArithmeticAverage;
import org.lab02.dataProcessing.Data;
import org.lab02.table.MeasurementTableModel;
import org.lab02.table.ResultTableModel;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import java.awt.EventQueue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App extends JFrame {
    private JPanel mainPanel;
    private JTable measurementTable;
    private JPanel weakReferencesPanel;
    private JLabel weakReferencesLabel;
    private JScrollPane tableMeasurementsScrollPane;
    private JTable resultTable;
    private JScrollPane tableResultScrollPane;
    private JLabel dataProcessingLabel;
    private JLabel processingTypeLabel;
    private JLabel measureLabel;
    private JList<String> fileList;
    private JButton loadFilesButton;
    private JComboBox<String> processingTypeBox;
    private String directoryName;
    private WeakHashMap<String, Data> weakHashMap;

    App() {
        setup();

        loadFilesButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = jFileChooser.showOpenDialog(loadFilesButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                directoryName = jFileChooser.getSelectedFile().getAbsolutePath();
                DefaultListModel<String> defaultListModel = new DefaultListModel<>();
                try {
                    defaultListModel.addAll(getFileList(directoryName));
                    fileList.setModel(defaultListModel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(loadFilesButton, "No directory chosen");
            }
        });

        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && fileList.getSelectedValue() != null) {
                loadMeasurements(fileList.getSelectedValue());
            }
        });
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        EventQueue.invokeLater(() -> {
            JFrame frame = new App();
            frame.setVisible(true);
        });
    }

    private void setup() {
        this.setTitle("WeakReferences App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(false);

        weakReferencesLabel.setText("Loaded using ...");
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataProcessingLabel.setText("Data processing method:");
        measureLabel.setText("Measurements: ");
        loadFilesButton.setText("Load files");

        String[] options = {
                ArithmeticAverage.DATA_PROCESSING_NAME,
        };
        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(options);
        processingTypeBox.setModel(comboBoxModel);
        weakHashMap = new WeakHashMap<>();
        pack();
    }

    private List<String> getFileList(String directoryName) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(directoryName))) {
            return stream.filter(Files::isRegularFile).map(Path::toAbsolutePath).map(Path::toString).collect(Collectors.toList());
        }
    }

    private void loadMeasurements(String dir) {
        Data data;
        if (!weakHashMap.containsKey(dir)) {
            String[] header = ReaderCSV.getHeaders(dir).toArray(new String[0]);
            List<List<String>> cells = ReaderCSV.getRecords(dir);

            measurementTable.setModel(new MeasurementTableModel(header, cells));
            RowSorter<?> sorter = measurementTable.getRowSorter();
            sorter.toggleSortOrder(0);

            ArithmeticAverage arithmeticAverage = new ArithmeticAverage();
            String[] results = arithmeticAverage.getResult(cells, header);
            resultTable.setModel(new ResultTableModel(header, results));

            data = new Data();
            data.setList(cells, header, results);
            weakHashMap.put(new String(dir), data);
            weakReferencesLabel.setText("Loaded from file");
        } else {
            data = weakHashMap.get(dir);

            measurementTable.setModel(new MeasurementTableModel(data.getHeaders(), data.getRecords()));
            RowSorter<?> sorter = measurementTable.getRowSorter();
            sorter.toggleSortOrder(0);

            resultTable.setModel(new ResultTableModel(data.getHeaders(), data.getResults()));
            weakReferencesLabel.setText("Loaded using WeakReferences");
        }
    }
}
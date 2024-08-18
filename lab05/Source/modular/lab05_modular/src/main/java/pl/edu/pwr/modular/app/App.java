package pl.edu.pwr.modular.app;

import com.formdev.flatlaf.FlatIntelliJLaf;
import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;
import loader.FileReader;
import table.ConfusionMatrixModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;
import java.util.ServiceLoader;

public class App extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton loadFileButton;
    private JComboBox<AnalysisService> algorithmsComboBox;
    private JScrollPane tableScrollPane;
    private JTable table1;
    private JButton calculateButton;
    private JTextField resultTextField;

    public App() {
        setUp();
        loadServices();

        loadFileButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = jFileChooser.showOpenDialog(loadFileButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                try {
                    String[] headers = FileReader.readCSVHeaders(filePath);
                    String[][] values = FileReader.readCSVValues(filePath);
                    DataSet dataSet = new DataSet();
                    dataSet.setHeader(headers);
                    dataSet.setData(values);
                    ConfusionMatrixModel confusionMatrixModel = new ConfusionMatrixModel(headers, values);
                    table1.setModel(confusionMatrixModel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(loadFileButton, "No directory chosen");
            }
        });

        calculateButton.addActionListener(e -> {
            ConfusionMatrixModel confusionMatrixModel = (ConfusionMatrixModel) table1.getModel();
            String[][] values = Arrays.stream(confusionMatrixModel.getCells())
                    .map(row -> Arrays.copyOfRange(row, 1, row.length))
                    .toArray(String[][]::new);
            DataSet dataSet = new DataSet();
            dataSet.setData(values);
            AnalysisService analysisService = (AnalysisService) algorithmsComboBox.getSelectedItem();
            try {
                analysisService.submit(dataSet);
                DataSet dataSetResult = analysisService.retrieve(true);
                String result = dataSetResult.getData()[0][0];
                resultTextField.setText(result);
            } catch (AnalysisException ex) {
                throw new RuntimeException(ex);
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

    private void setUp() {
        this.setTitle("Confusion matrix app");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(true);
        pack();
    }

    private void loadServices() {
        ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
        for (AnalysisService service : loader) {
            algorithmsComboBox.addItem(service);
        }
    }
}

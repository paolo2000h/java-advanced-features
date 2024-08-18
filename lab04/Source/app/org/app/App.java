package org.app;

import com.formdev.flatlaf.FlatIntelliJLaf;
import loader.ModuleManager;
import processing.StatusListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends JFrame {
    ModuleManager moduleManager;
    MyStatusListener myStatusListener;
    private JPanel mainPanel;
    private JButton loadCatalogButton;
    private JScrollPane classesScrollPane;
    private JScrollPane taskScrollPane;
    private JButton reloadButton;
    private JButton unloadButton;
    private JTextField taskTextField;
    private JButton submitTaskButton;
    private JList<Class<?>> classesList;
    private JList<String> taskList;
    private JButton aboutButton;

    App() {
        setUp();
        myStatusListener = new MyStatusListener(taskList);


        loadCatalogButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = jFileChooser.showOpenDialog(loadCatalogButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                String directory = jFileChooser.getSelectedFile().getAbsolutePath();
                loadCatalogButton.setText(directory);
                loadClassFiles(directory);
            } else {
                JOptionPane.showMessageDialog(loadCatalogButton, "No directory chosen");
            }
        });

        reloadButton.addActionListener(e -> {
            String directory = loadCatalogButton.getText();
            loadClassFiles(directory);
        });

        unloadButton.addActionListener(e -> {
            moduleManager.unloadModule();
            clearClassList();
        });

        submitTaskButton.addActionListener(e -> {
            submitTaskButton.setEnabled(false);
            Class<?> cls = classesList.getSelectedValue();
            String text = taskTextField.getText();
            try {
                Object obj = cls.getConstructor().newInstance();
                Method submitTaskMethod = cls.getMethod("submitTask", String.class, StatusListener.class);
                boolean b = (boolean) submitTaskMethod.invoke(obj, text, myStatusListener);

                Method getResult = cls.getMethod("getResult");

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    String result = null;
                    while (true) {
                        try {
                            Thread.sleep(800);
                            result = (String) getResult.invoke(obj);
                        } catch (InterruptedException | IllegalAccessException | IllegalArgumentException |
                                 InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                        if (result != null) {
                            System.out.println("Result: " + result);
                            JOptionPane.showMessageDialog(submitTaskButton, "Result: " + result);
                            submitTaskButton.setEnabled(true);
                            executor.shutdown();
                            break;
                        }
                    }
                });
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        });

        aboutButton.addActionListener(e -> {
            Class<?> cls = classesList.getSelectedValue();
            try {
                Object obj = cls.getConstructor().newInstance();
                Method getInfo = cls.getMethod("getInfo");
                JOptionPane.showMessageDialog(aboutButton, getInfo.invoke(obj));
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException ex) {
                ex.printStackTrace();
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

    private void loadClassFiles(String directory) {
        moduleManager = new ModuleManager(directory);
        List<Class<?>> classes = moduleManager.getClasses();
        DefaultListModel<Class<?>> defaultListModel = new DefaultListModel<>();
        defaultListModel.addAll(classes);
        classesList.setModel(defaultListModel);
    }

    private void setUp() {
        this.setTitle("Class loader app");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(false);
        pack();
    }

    private void clearClassList() {
        DefaultListModel listModel = (DefaultListModel) classesList.getModel();
        listModel.removeAllElements();
        System.gc();
    }
}

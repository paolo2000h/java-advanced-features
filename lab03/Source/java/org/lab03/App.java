package org.lab03;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.lab03.Artist.ArtistManager;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.Locale;

public class App extends JFrame {
    private JPanel mainPanel;
    private JPanel textFieldPanel;
    private JTextField artistNameField;
    private JButton acceptArtistNameButton;
    private JComboBox<String> languageComboBox;
    private JLabel question1;
    private JTextField fillAnswer1;
    private JButton checkQuizButton;
    private JLabel answer1;
    private JTextField fillAnswer2;
    private JTextField fillAnswer3;
    private JLabel question2;
    private JLabel answer2;
    private JLabel question3;
    private JLabel answer3;
    private Quiz quiz;
    private Locale locale;
    private String[] answers;
    private boolean isChecked = false;

    App() {
        setup();

        acceptArtistNameButton.addActionListener(e -> {
            String artistName = artistNameField.getText();
            if (artistNameField.getText().isBlank()) {
                JOptionPane.showMessageDialog(acceptArtistNameButton, "Write artist name!");
                return;
            }
            try {
                deleteTexts();
                ArtistManager artistManager = new ArtistManager(artistName);
                locale = getLanguage();
                quiz = new Quiz(locale, artistManager);
                answers = quiz.getAnswers();
                updateQuiz();
                question1.setEnabled(true);
                question2.setEnabled(true);
                question3.setEnabled(true);
                fillAnswer1.setEnabled(true);
                fillAnswer2.setEnabled(true);
                fillAnswer3.setEnabled(true);
                answer1.setEnabled(true);
                answer2.setEnabled(true);
                answer3.setEnabled(true);
            } catch (Exception f) {
                JOptionPane.showMessageDialog(acceptArtistNameButton, "Artist not found!");
            }
        });

        checkQuizButton.addActionListener(e -> checkAnswers());

        languageComboBox.addActionListener(e -> {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            assert selectedLanguage != null;
            if (selectedLanguage.equals("Polski")) {
                locale = new Locale("pl", "PL");
            }
            if (selectedLanguage.equals("English")) {
                locale = new Locale("en", "EN");
            }
            if (quiz != null) {
                quiz.updateLocale(locale);
                updateQuiz();
                if (isChecked) {
                    checkAnswers();
                }
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

    private void updateQuiz() {
        question1.setText(quiz.getQuestion1());
        question2.setText(quiz.getQuestion2());
        question3.setText(quiz.getQuestion3());
    }

    private void deleteTexts() {
        isChecked = false;
        question1.setText("");
        question2.setText("");
        question3.setText("");
        answer1.setText("");
        answer2.setText("");
        answer3.setText("");
    }

    private void checkAnswers() {
        isChecked = true;
        String a1 = fillAnswer1.getText();
        String a2 = fillAnswer2.getText();
        String a3 = fillAnswer3.getText();

        boolean isAnswer1 = a1.equals(answers[0]);
        boolean isAnswer2 = a2.equals(answers[1]);
        boolean isAnswer3 = a3.equals(answers[2]);

        answer1.setText(quiz.getAnswer1());
        answer2.setText(quiz.getAnswer2());
        answer3.setText(quiz.getAnswer3());
        if (isAnswer1) {
            answer1.setForeground(new Color(0, 100, 0));
        } else {
            answer1.setForeground(Color.RED);
        }
        if (isAnswer2) {
            answer2.setForeground(new Color(0, 100, 0));
        } else {
            answer2.setForeground(Color.RED);
        }
        if (isAnswer3) {
            answer3.setForeground(new Color(0, 100, 0));
        } else {
            answer3.setForeground(Color.RED);
        }
    }

    private void setup() {
        this.setTitle("MusicQuiz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(false);
        artistNameField.setText("Maryla Rodowicz");
        String[] options = {"Polski", "English"};
        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(options);
        languageComboBox.setModel(comboBoxModel);
        question1.setEnabled(false);
        question2.setEnabled(false);
        question3.setEnabled(false);
        fillAnswer1.setEnabled(false);
        fillAnswer2.setEnabled(false);
        fillAnswer3.setEnabled(false);
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        pack();
    }

    private Locale getLanguage() {
        if (locale == null) {
            return new Locale("pl", "PL");
        }
        return locale;
    }
}

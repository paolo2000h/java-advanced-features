package app;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.EventQueue;
import java.security.PrivateKey;
import java.security.PublicKey;

import security.*;

public class App extends JFrame {
    private JPanel panel;
    private JButton loadFileInButton;
    private JButton loadPrivateKeyButton;
    private JButton loadPublicKeyButton;
    private JButton loadFileOutButton;
    private JButton encryptButton;
    private JButton decryptButton;
    private String fileIn;
    private String fileOut;
    private String privateKey;
    private String publicKey;

    App() {
        setUp();

        loadFileInButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = jFileChooser.showOpenDialog(loadFileInButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                fileIn = jFileChooser.getSelectedFile().getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(loadFileInButton, "No file chosen");
            }
        });

        loadFileOutButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = jFileChooser.showOpenDialog(loadFileOutButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                fileOut = jFileChooser.getSelectedFile().getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(loadFileOutButton, "No file chosen");
            }
        });

        loadPrivateKeyButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = jFileChooser.showOpenDialog(loadPrivateKeyButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                privateKey = jFileChooser.getSelectedFile().getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(loadPrivateKeyButton, "No file chosen");
            }
        });

        loadPublicKeyButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = jFileChooser.showOpenDialog(loadPublicKeyButton);

            if (result == JFileChooser.APPROVE_OPTION) {
                publicKey = jFileChooser.getSelectedFile().getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(loadPublicKeyButton, "No file chosen");
            }
        });

        encryptButton.addActionListener(e -> {
            try {
                PublicKey pubKey = KeyUtils.loadPublicKeyFromFile(publicKey);
                FileEncryptor.encryptFile(fileIn, fileOut, pubKey);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        decryptButton.addActionListener(e -> {
            try {
                PrivateKey privKey = KeyUtils.loadPrivateKeyFromFile(privateKey);
                FileEncryptor.decryptFile(fileIn, fileOut, privKey);
            } catch (Exception ex) {
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
        this.setTitle("File decryption/encryption app");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setResizable(false);
        pack();
    }
}

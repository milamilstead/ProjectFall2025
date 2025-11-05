import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UserQueryCSVDialogBST {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserQueryCSVDialogBST::showFileSelectionDialog);
    }

    private static void showFileSelectionDialog() {
        JFileChooser fileChooser = new JFileChooser("DataFiles\\ThirdPartyDataCSVFiles");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("DataFiles\\ThirdPartyDataCSVFiles\\TopSongs5000Edited.csv"));
        fileChooser.setDialogTitle("Select CSV File");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            openQueryDialog(selectedFile.getAbsolutePath());
        } else {
            System.exit(0);
        }
    }

    private static void openQueryDialog(String filename) {
        // Create the BST instance using selected filename
        KVStringPairBST ourKVStringPairBST = new KVStringPairBST(filename);

        JFrame frame = new JFrame("Query Dialog for " + new File(filename).getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(700, 500);

        // Top panel for input and submit button
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        JLabel inputLabel = new JLabel(ourKVStringPairBST.keyTitle + ": <Enter key>");
        inputLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

        topPanel.add(inputLabel, BorderLayout.WEST);
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(submitButton, BorderLayout.EAST);

        // Center panel for results
        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Bottom panel for clear button
        JPanel bottomPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bottomPanel.add(clearButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action listener for submit
        submitButton.addActionListener(e -> {
            String userInput = inputField.getText().trim();
            if (!userInput.isEmpty()) {
                String result = ourKVStringPairBST.Search(userInput);
                outputArea.append("You entered: " + userInput + "\n");
                outputArea.append(result + "\n\n");
                inputField.setText("");
            }
        });

        // Action listener for clear
        clearButton.addActionListener(e -> outputArea.setText(""));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

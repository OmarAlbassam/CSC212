import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Test {

    static boolean isAVL = true; // true for AVL, false for List. Use of Inverted Index
    static SearchEngine s = new SearchEngine();

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the program terminate after the user clicks x
    
        JTextField inputField = new JTextField();
        inputField.setBounds(150, 20, 300, 30);
        frame.add(inputField);
    
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 140, 500, 200);
        frame.add(scrollPane);
    
        JTextArea outputField = new JTextArea();
        outputField.setFont(new Font("Arial", Font.BOLD, 16));
        outputField.setEditable(false);
        outputField.setBackground(Color.white);
        scrollPane.setViewportView(outputField); 
    
        JButton booleanButton = new JButton("Boolean Retrieval");
        booleanButton.setBounds(120, 60, 150, 50);
        booleanButton.addActionListener(e -> {

            long startTime = System.nanoTime();

            String input = inputField.getText();
            if (input.equals("")) // This is to handle empty search box retreival requests
                return;
            String result = booleanQueryAction(input);
            outputField.setText(result);

            long finishTime = System.nanoTime();
            long durationInMillis = (finishTime - startTime) / 1_000_000;

            System.out.println("Query finished at " + durationInMillis + " ms.");
        });
        frame.add(booleanButton);
    
        JButton rankedButton = new JButton("Ranked Retrieval");
        rankedButton.setBounds(320, 60, 150, 50);
        rankedButton.addActionListener(e -> {

            long startTime = System.nanoTime();

            String input = inputField.getText();
            if (input.equals("")) // This is to handle empty search box retreival requests
                return;
            String result = rankedRetrievalAction(input);
            outputField.setText(result);

            long finishTime = System.nanoTime();
            long durationInMillis = (finishTime - startTime) / 1_000_000;

            System.out.println("Retrieval finished at " + durationInMillis + " ms.");
        });
        frame.add(rankedButton);

        JLabel selectedDS = new JLabel("Selected: AVL");
        selectedDS.setBounds(490, 20, 100, 30);
        frame.add(selectedDS);

        JButton switch_data_structure = new JButton("<html>Switch List<br>inverted index</html>");
        switch_data_structure.setBounds(480, 40, 100, 80);
        switch_data_structure.setFont(new Font("Arial", Font.BOLD, 8));
        switch_data_structure.addActionListener(e -> {
            String[] s = switchInvertedIndex();
            switch_data_structure.setText(s[0]);
            selectedDS.setText("Selected: " + s[1]);
        });

        frame.add(switch_data_structure);
        
    
        frame.setVisible(true);
    }
    

    public static String rankedRetrievalAction(String in) {
        AVL<String> out = s.rankedSearch(in);
        LinkedPQ pq = out.makePQ();
        return pq.result();
    }

    public static String booleanQueryAction(String in) {
        AVL<String> result = s.querySearch(in);
        LinkedList<String> list = (LinkedList<String>)result.makeList();
        return list.result();
    }

    public static String[] switchInvertedIndex() {
        if (isAVL) {
            isAVL = false;
            // Implement some code to change the data structure in SearchEngine from AVL to List

            String[] arr = {"<html>Switch AVL<br>inverted index</html>", "List"};
            return arr;
        } else if (!isAVL) {
            isAVL = true;
            // Implement some code to change the data structure in SearchEngine from List to AVL

            String[] arr = {"<html>Switch List<br>inverted index</html>", "AVL"};
            return arr;
        }

        return null;
    }
}

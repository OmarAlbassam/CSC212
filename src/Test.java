import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
public class Test {

    static String data_structure = "Inverted Index with AVL"; // true for AVL, false for List. Use of Inverted Index
    static SearchEngine s = new SearchEngine();

    @SuppressWarnings("unused")
    public static void main(String[] args) {


        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the program terminate after the user clicks x

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setBounds(52, 0, 500, 30);
        frame.add(searchLabel);

        JTextField inputField = new JTextField();
        inputField.setBounds(50, 20, 500, 30);
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
        booleanButton.setBounds(50, 60, 150, 50);
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
        rankedButton.setBounds(200, 60, 150, 50);
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

        JLabel selectedDS = new JLabel("Selected Data Structure:");
        selectedDS.setBounds(365, 55, 250, 30);
        frame.add(selectedDS);

        String[] ds = {"Inverted Index with AVL", "Inverted Index with LinkedList", "Index with LinkedList"};
        JComboBox<String> DSComboBox = new JComboBox<>(ds);
        DSComboBox.setBounds(350, 80, 200, 30);
        DSComboBox.addActionListener(e -> {
            data_structure = (String) DSComboBox.getSelectedItem();
            System.out.println(data_structure);
        });
        frame.add(DSComboBox);
    
        frame.setVisible(true);
    }
    

    public static String rankedRetrievalAction(String in) {
        AVL<String> out = s.rankedSearchAVL(in);
        LinkedPQ pq = out.makePQ();
        return pq.result();
    }

    public static String booleanQueryAction(String in) {
        AVL<String> result = s.querySearchAVL(in);
        LinkedList<String> list = (LinkedList<String>)result.makeList();
        return list.result();
    }

}
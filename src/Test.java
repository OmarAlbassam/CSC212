import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Test {
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
            String input = inputField.getText();
            String result = booleanQueryAction(input);
            outputField.setText(result);
        });
        frame.add(booleanButton);
    
        JButton rankedButton = new JButton("Ranked Retrieval");
        rankedButton.setBounds(320, 60, 150, 50);
        rankedButton.addActionListener(e -> {
            String input = inputField.getText();
            String result = rankedRetievalAction(input);
            outputField.setText(result);
        });
        frame.add(rankedButton);
    
        frame.setVisible(true);
    }
    

    public static String rankedRetievalAction(String in) {
        AVL<String> out = s.rankedSearchAVL(in);
        LinkedPQ pq = out.makePQ();
        return pq.result();
    }

    public static String booleanQueryAction(String in) {
        AVL<String> result = s.querySearch(in);
        LinkedList<String> list = (LinkedList<String>)result.makeList();
        return list.result();
    }
}

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Main {

    static String data_structure = "Inverted Index with AVL"; // By default, AVL Inverted Index is selected
    static SearchEngine s = new SearchEngine();

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(600, 410);
        frame.setTitle("Search Engine");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the program terminate after the user clicks x

        JLabel tokensLabel = new JLabel("Tokens: " + s.getToken());
        tokensLabel.setBounds(50, 340, 150, 30); // Moved to the bottom-left of the frame
        tokensLabel.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(tokensLabel);

        JLabel vocabLabel = new JLabel("Vocab: " + s.getVocab());
        vocabLabel.setBounds(150, 340, 150, 30); // Moved to the bottom-right of the frame
        vocabLabel.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(vocabLabel);

        JLabel docsLabel = new JLabel("Docs: " + s.getDocCount());
        docsLabel.setBounds(240, 340, 150, 30); // Moved to the bottom-right of the frame
        docsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(docsLabel);

        JLabel time = new JLabel("Time: ");
        time.setBounds(320, 340, 250, 30); // Moved to the bottom-left of the frame
        time.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(time);

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

            String input = inputField.getText();
            input = input.strip();
            if (input.equals("")) { // This is to handle empty search box retreival requests
                JOptionPane.showMessageDialog(frame, "Search box is empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String result = booleanQueryAction(input);
            outputField.setText(result);

            String queryTime = String.format("%.4f", SearchEngine.queryTime); // This is to format the time to 4 decimal places
            time.setText("Time: finished at " + queryTime + " ms.");
        });
        frame.add(booleanButton);

        JButton rankedButton = new JButton("Ranked Retrieval");
        rankedButton.setBounds(200, 60, 150, 50);
        rankedButton.addActionListener(e -> {

            String input = inputField.getText();
            input = input.strip();
            if (input.equals("")) { // This is to handle empty search box retreival requests
                JOptionPane.showMessageDialog(frame, "Search box is empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String result = rankedRetrievalAction(input);
            outputField.setText(result);
            String queryTime = String.format("%.4f", SearchEngine.queryTime); // This is to format the time to 4 decimal places
            time.setText("Time: finished at " + queryTime + " ms.");

        });
        frame.add(rankedButton);

        JLabel selectedDS = new JLabel("Selected Index:");
        selectedDS.setBounds(355, 55, 250, 30);
        frame.add(selectedDS);

        String[] ds = { "Inverted Index with AVL", "Inverted Index with LinkedList", "Index with LinkedList" };
        JComboBox<String> DSComboBox = new JComboBox<>(ds);
        DSComboBox.setBounds(350, 80, 200, 30);
        DSComboBox.addActionListener(e -> {
            data_structure = (String) DSComboBox.getSelectedItem();
            System.out.println(data_structure);
        });
        frame.add(DSComboBox);

        frame.setVisible(true);

        // ***** InvertedIndex without stopWords *****
        // TextProccesor tp = new TextProccesor();
        // tp.fetchData(new LinkedList<String>());
        // LinkedIndex<ResultList<String>> l =tp.buildInvertedIndex();
        // l.findFirst();
        // int count=0;
        // while(!l.last()) {
        // count++;
        // l.findNext();
        // }
        // count++;
        // System.out.println("Count: "+count);
        // System.out.println("VocabCount: "+tp.vocabCount);

    }

    public static String rankedRetrievalAction(String in) {
        if (data_structure.equals("Inverted Index with AVL")) {
            s.rankedSearchInvertedAVL(in);
            AVL<String> out = s.rankedSearchInvertedAVL(in);
            LinkedPQ<String> pq = out.makePQ();
            return pq.result();

        } else if (data_structure.equals("Inverted Index with LinkedList")) {
            ResultList<String> out = (ResultList<String>) s.rankedSearchInvertedList(in);
            LinkedPQ<String> pq = out.makePQ();
            return pq.result();

        } else {
            ResultList<String> out = (ResultList<String>) s.rankedSearchList(in);
            LinkedPQ<String> pq = out.makePQ();
            return pq.result();
        }
    }

    public static String booleanQueryAction(String in) {
        if (data_structure.equals("Inverted Index with AVL")) {
            s.querySearchInvertedAVL(in);
            AVL<String> out = s.querySearchInvertedAVL(in);
            LinkedList<String> res = (LinkedList<String>) out.makeList();
            return res.result();
        } else if (data_structure.equals("Inverted Index with LinkedList")) {
            ResultList<String> out = (ResultList<String>) s.querySearchInvertedList(in);
            String res = out.result();
            return res;
        } else {
            ResultList<String> out = (ResultList<String>) s.querySearchList(in);
            String res = out.result();
            return res;
        }
    }

}
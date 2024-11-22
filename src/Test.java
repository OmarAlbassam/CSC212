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

    static String data_structure = "Inverted Index with AVL"; // By default, AVL Inverted Index is selected
    static SearchEngine s = new SearchEngine();

    @SuppressWarnings("unused")
    public static void main(String[] args) {


        JFrame frame = new JFrame();
        frame.setSize(600, 410);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the program terminate after the user clicks x
        
        JLabel tokensLabel=new JLabel("Tokens: "+s.getToken());
        tokensLabel.setBounds(50, 340, 250, 30); // Moved to the bottom-left of the frame
        tokensLabel.setFont(new Font("Arial",Font.BOLD,15));
        frame.add(tokensLabel);
        
        JLabel vocabLabel=new JLabel("Vocab: "+s.getVocab());
        vocabLabel.setBounds(180, 340, 250, 30); // Moved to the bottom-right of the frame
        vocabLabel.setFont(new Font("Arial",Font.BOLD,15));
        frame.add(vocabLabel);
        
        JLabel time=new JLabel("Time: ");
        time.setBounds(320, 340, 250, 30); // Moved to the bottom-left of the frame
        time.setFont(new Font("Arial",Font.BOLD,15));
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

            long startTime = System.nanoTime();

            String input = inputField.getText();
            input = input.strip();
            if (input.equals("")) // This is to handle empty search box retreival requests
                return;
            String result = booleanQueryAction(input);
            outputField.setText(result);

            long finishTime = System.nanoTime();
            long durationInMillis = (finishTime - startTime) / 1_000_000;

            //System.out.println("Query finished at " + durationInMillis + " ms.");
            time.setText("Time: finished at " + durationInMillis + " ms.");
        });
        frame.add(booleanButton);
    
        JButton rankedButton = new JButton("Ranked Retrieval");
        rankedButton.setBounds(200, 60, 150, 50);
        rankedButton.addActionListener(e -> {

            long startTime = System.nanoTime();

            String input = inputField.getText();
            input = input.strip();
            if (input.equals("")) // This is to handle empty search box retreival requests
                return;
            String result = rankedRetrievalAction(input);
            outputField.setText(result);

            long finishTime = System.nanoTime();
            long durationInMillis = (finishTime - startTime) / 1_000_000;

            time.setText("Time: finished at " + durationInMillis + " ms.");

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



    	
    	//***** InvertedIndex without stopWords *****
//    	TextProccesor tp = new TextProccesor();
//    	tp.fetchData(new LinkedList<String>()); 
//    	LinkedIndex<ResultList<String>> l =tp.buildInvertedIndex();
//    	l.findFirst();
//    	int count=0;
//    	while(!l.last()) {
//    		count++;
//    	l.findNext();	
//    	}
//    	count++;
//    	System.out.println("Count: "+count);
//    	System.out.println("VocabCount: "+tp.vocabCount);
    	
    }
    

    public static String rankedRetrievalAction(String in) {
        AVL<String> out = s.rankedSearchInvertedAVL(in);
        LinkedPQ pq = out.makePQ();
        return pq.result();
    }

    public static String booleanQueryAction(String in) {
        AVL<String> result = s.querySearchInvertedAVL(in);
        LinkedList<String> list = (LinkedList<String>)result.makeList();
        return list.result();
    }

}
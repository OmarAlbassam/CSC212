import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Test {
    static SearchEngine s = new SearchEngine();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLayout(null);

        JTextField inputField = new JTextField();
        inputField.setBounds(150, 20, 300, 30);
        frame.add(inputField);

        JTextArea outputField = new JTextArea();
        outputField.setFont(new Font("Arial", Font.BOLD, 16));
        outputField.setEditable(false);
        outputField.setBackground(Color.white);
        outputField.setBounds(50, 140, 500, 200);
        frame.add(outputField);

        JButton booleanButton = new JButton("Boolean Retrieval");
        booleanButton.setBounds(120, 60, 150, 50);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static String rankedRetievalAction(String in) {
        String input = in;
        AVL<String> out = s.rankedSearchAVL(input);
        LinkedPQ pq = out.makePQ();
        String res = pq.Result();
        return res;
    }
}

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextProccesor {

    private static final String DOCS_PATH = "data/dataset.csv",
            STOP_WORDS_PATH = "data/stop.txt";
    private static final String ALPHANUMERIC_REGEX = "[^a-zA-Z0-9\\s]";
    private File docsFile, stopFile;

    TextProccesor() {
        docsFile = new File(DOCS_PATH);
        stopFile = new File(STOP_WORDS_PATH);
    }

    public void fetchData(List<List<String>> list, List<String> stopWords) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(docsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] lineWords = null; // This array is used to store individual words from each document (each line)
        try {
            String line;
            input.readLine(); // Read header line to skip it
            while ((line = input.readLine()) != null) {
                // Split the entries by comma so that lineValues[0] = {Document ID},
                // lineValues[1+] = {Content}
                String[] lineValues = line.split(",");

                // This try-catch block considers end of document entries. If it reaches the row
                // after the last document, a NumberFormatException will be thrown. (breaks
                // loop)
                try {
                    Integer.parseInt(lineValues[0]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    break;
                }
                // If document, then continue processing
                lineValues[1] = lineValues[1].toLowerCase();
                lineValues[1] = lineValues[1].replaceAll(ALPHANUMERIC_REGEX, ""); // Replace all non-alphanumeric values to blanks

                // Split individual words from the doc's content into an array
                lineWords = lineValues[1].split(" ");
                LinkedList<String> ls = new LinkedList<>();
                for (int i = 0; i < lineWords.length; i++) {
                    if (!ls.contains(lineWords[i]) && !stopWords.contains(lineWords[i]))
                        ls.insert(lineWords[i]);
                }

                list.insert(ls);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> fetchStopWords() {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(stopFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LinkedList<String> list = null;
        try {
            list = new LinkedList<>();
            String line;
            while ((line = input.readLine()) != null)
                list.insert(line);

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}

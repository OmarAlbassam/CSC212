import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TextProccesor {
    
    private static final String
    DOCS_PATH = "data/dataset.csv",
    STOP_WORDS_PATH = "data/stop.txt";

    private static final String ALPHANUMERIC_REGEX = "[^a-zA-Z0-9\\s]";

    private File docsFile, stopFile;
    

    TextProccesor() {
        docsFile = new File(DOCS_PATH);
        stopFile = new File(STOP_WORDS_PATH);
    }

    public void initiateProccesor() throws FileNotFoundException {
        FileReader fr = new FileReader(docsFile);
        BufferedReader input = new BufferedReader(fr);
        try {
            String line;
            input.readLine(); // Read header line to skip it
            String lineWords[] = null; // This array is used to store individual words from each document (each line)
            while ((line = input.readLine()) != null) {
                // Split the entries by comma so that lineValues[0] = {Document ID}, lineValues[1] = {Content}
                String[] lineValues = line.split(",");
                lineValues[1] = lineValues[1].toLowerCase();
                lineValues[1] = lineValues[1].replaceAll(ALPHANUMERIC_REGEX, ""); // Replace all non-alphanumeric values to blanks

                // Split individual words from the doc's content into an array
                lineWords = lineValues[1].split(" ");

                // This try-catch block considers end of document entries. If it reaches the row
                // after the last document, a NumberFormatException will be thrown. (breaks loop)
                try {
                    Integer.parseInt(lineValues[0]);
                } catch (NumberFormatException e) {
                    break;
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

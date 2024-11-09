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

    private LinkedList<List<String>> listOfDocs; // ListOfDocs -> List<String> -> {docID, ..., finalWord}

    TextProccesor() {
        docsFile = new File(DOCS_PATH);
        stopFile = new File(STOP_WORDS_PATH);
        listOfDocs = new LinkedList<>();
    }

    public void fetchData(List<String> stopWords) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(docsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] lineWords = null; // This array is used to store individual words from each document (each line)
        try {

            input.readLine(); // Read header line to skip it
            String line = input.readLine();
            while (line != null) {
                // Split the entries by comma so that lineValues[0] = {Document ID},
                // lineValues[1+] = {Content}
                String[] lineValues = line.split(","); // 0: 0, 1: Markerufece, 2: mdijdisj, 3: ....

                if (lineValues.length == 0)
                    break;

                String lineContent = "";
                for (int i = 1; i < lineValues.length; i++) {
                    lineContent += lineValues[i];
                }

                // If document, then continue processing
                lineContent = lineContent.toLowerCase().replaceAll(ALPHANUMERIC_REGEX, ""); // Replace all
                                                                                            // non-alphanumeric values
                                                                                            // to blanks

                // Split individual words from the doc's content into an array
                lineWords = lineContent.split(" ");
                LinkedList<String> ls = new LinkedList<>();
                ls.insert(lineValues[0]); // Insert key at the begining (Doc ID)
                for (int i = 0; i < lineWords.length; i++) {
                    if (!stopWords.contains(lineWords[i]))
                        ls.insert(lineWords[i]);
                }

                listOfDocs.insert(ls);
                line = input.readLine();
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

    public LinkedIndex<List<String>> buildIndex() {

        LinkedIndex<List<String>> index = new LinkedIndex<>();

        listOfDocs.findFirst();

        while (!listOfDocs.last()) {
            listOfDocs.retrieve().findFirst();
            String key = listOfDocs.retrieve().retrieve();
            listOfDocs.retrieve().findNext();
            LinkedList<String> l = new LinkedList<>();
            while (!listOfDocs.retrieve().last()) {
                if (!l.contains(listOfDocs.retrieve().retrieve()))
                    l.insert(listOfDocs.retrieve().retrieve());
                listOfDocs.retrieve().findNext();
            }
            if (!l.contains(listOfDocs.retrieve().retrieve()))
                l.insert(listOfDocs.retrieve().retrieve());
            index.insert(l, key);
            listOfDocs.findNext();
        }

        listOfDocs.retrieve().findFirst();
        String key = listOfDocs.retrieve().retrieve();
        listOfDocs.retrieve().findNext();
        LinkedList<String> l = new LinkedList<>();
        while (!listOfDocs.retrieve().last()) {
            if (!l.contains(listOfDocs.retrieve().retrieve()))
                l.insert(listOfDocs.retrieve().retrieve());
            listOfDocs.retrieve().findNext();
        }
        if (!l.contains(listOfDocs.retrieve().retrieve()))
            l.insert(listOfDocs.retrieve().retrieve());
        index.insert(l, key);

        return index;

    }
    public LinkedIndex<List<String>> buildInvertedIndex() {
        LinkedIndex<List<String>> invIndex = new LinkedIndex<>();
    
        listOfDocs.findFirst();
        while (!listOfDocs.last()) {
            listOfDocs.retrieve().findFirst();
            String docId = listOfDocs.retrieve().retrieve();
    
            // Move to the first word in the document content
            listOfDocs.retrieve().findNext();
    
            while (!listOfDocs.retrieve().last()) {
                String word = listOfDocs.retrieve().retrieve();
    
                if (invIndex.findKey(word)) {
                    List<String> docIds = invIndex.retrieve();
                    if (!docIds.contains(docId)) {
                        docIds.insert(docId);
                    }
                } else {
                    LinkedList<String> newDocIds = new LinkedList<>();
                    newDocIds.insert(docId);
                    invIndex.insert(newDocIds, word);
                }
    
                listOfDocs.retrieve().findNext();
            }
    
            // Handle the last word in the document
            String lastWord = listOfDocs.retrieve().retrieve();
            if (invIndex.findKey(lastWord)) {
                List<String> docIds = invIndex.retrieve();
                if (!docIds.contains(docId)) {
                    docIds.insert(docId);
                }
            } else {
                LinkedList<String> newDocIds = new LinkedList<>();
                newDocIds.insert(docId);
                invIndex.insert(newDocIds, lastWord);
            }
    
            listOfDocs.findNext();
        }
    
        return invIndex;
    }
    
}

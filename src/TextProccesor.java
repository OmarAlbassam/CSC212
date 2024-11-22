import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextProccesor {
    int vocabCount;
    int tokensCount;
    private static final String DOCS_PATH = "data/dataset.csv", STOP_WORDS_PATH = "data/stop.txt";
    private static final String ALPHANUMERIC_REGEX = "'s\\b|[^a-zA-Z0-9\\s-]";
    private File docsFile, stopFile;

    private LinkedList<List<String>> listOfDocs; // ListOfDocs -> List<String> -> {docID, firstWord, ..., lastWord}

    TextProccesor() {
        docsFile = new File(DOCS_PATH);
        stopFile = new File(STOP_WORDS_PATH);
        listOfDocs = new LinkedList<>();
        vocabCount = 0;
        tokensCount = 0;
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
                lineContent = lineContent.toLowerCase().replaceAll(ALPHANUMERIC_REGEX, "").replaceAll("-", " "); // Replace
                                                                                                                 // values
                                                                                                                 // to
                                                                                                                 // blanks

                // Split individual words from the doc's content into an array
                lineWords = lineContent.split(" ");
                tokensCount += lineWords.length; // to count tokens
                LinkedList<String> ls = new LinkedList<>();
                ls.insert(lineValues[0]); // Insert key at the begining (Doc ID)
                for (int i = 0; i < lineWords.length; i++) {
                    if (!stopWords.contains(lineWords[i])) {
                        ls.insert(lineWords[i]);
                    }
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

    public LinkedIndex<ResultList<String>> buildIndex() {

        LinkedIndex<ResultList<String>> index = new LinkedIndex<>();

        listOfDocs.findFirst();

        while (!listOfDocs.last()) {
            listOfDocs.retrieve().findFirst();
            String key = listOfDocs.retrieve().retrieve();
            listOfDocs.retrieve().findNext();

            ResultList<String> l = new ResultList<>();
            while (!listOfDocs.retrieve().last()) {
                if (!l.contains(listOfDocs.retrieve().retrieve()))
                    l.insert(listOfDocs.retrieve().retrieve());
                else // else if already in resultList; increment frequency*
                    l.incrementNode();

                listOfDocs.retrieve().findNext();
            }
            if (!l.contains(listOfDocs.retrieve().retrieve()))
                l.insert(listOfDocs.retrieve().retrieve());
            else // if already in resultList; increment frequency*
                l.incrementNode();

            index.insert(l, key);
            listOfDocs.findNext();
        }

        listOfDocs.retrieve().findFirst();
        String key = listOfDocs.retrieve().retrieve();
        listOfDocs.retrieve().findNext();
        ResultList<String> l = new ResultList<>();
        while (!listOfDocs.retrieve().last()) {
            if (!l.contains(listOfDocs.retrieve().retrieve()))
                l.insert(listOfDocs.retrieve().retrieve());
            else // if already in resultList; increment frequency*
                l.incrementNode();

            listOfDocs.retrieve().findNext();
        }
        if (!l.contains(listOfDocs.retrieve().retrieve()))
            l.insert(listOfDocs.retrieve().retrieve());
        else // if already in resultList; increment frequency*
            l.incrementNode();
        index.insert(l, key);

        return index;

    }

    public LinkedIndex<ResultList<String>> buildInvertedIndex() {

        LinkedIndex<ResultList<String>> invIndex = new LinkedIndex<>();

        listOfDocs.findFirst();
        while (!listOfDocs.last()) {
            listOfDocs.retrieve().findFirst();
            String docId = listOfDocs.retrieve().retrieve();

            // Move to the first word in the document content
            listOfDocs.retrieve().findNext();

            while (!listOfDocs.retrieve().last()) {
                String word = listOfDocs.retrieve().retrieve();

                if (invIndex.findKey(word)) {
                    ResultList<String> docIds = invIndex.retrieve();
                    if (!docIds.contains(docId)) {
                        docIds.insert(docId);

                    } else {
                        docIds.incrementNode();
                    }
                } else {
                    ResultList<String> newDocIds = new ResultList<>();
                    newDocIds.insert(docId);
                    invIndex.insert(newDocIds, word);
                    vocabCount++;

                }

                listOfDocs.retrieve().findNext();
            }

            // Handle the last word in the document
            String lastWord = listOfDocs.retrieve().retrieve();
            if (invIndex.findKey(lastWord)) {
                ResultList<String> docIds = invIndex.retrieve();
                if (!docIds.contains(docId)) {
                    docIds.insert(docId);
                } else {
                    docIds.incrementNode();
                }
            } else {
                ResultList<String> newDocIds = new ResultList<>();
                newDocIds.insert(docId);
                invIndex.insert(newDocIds, lastWord);
                vocabCount++;

            }
            listOfDocs.findNext();
        }
        listOfDocs.retrieve().findFirst();
        String docId = listOfDocs.retrieve().retrieve();

        // Move to the first word in the document content
        listOfDocs.retrieve().findNext();

        while (!listOfDocs.retrieve().last()) {
            String word = listOfDocs.retrieve().retrieve();

            if (invIndex.findKey(word)) {
                ResultList<String> docIds = invIndex.retrieve();
                if (!docIds.contains(docId)) {
                    docIds.insert(docId);
                } else {
                    docIds.incrementNode();
                }
            } else {
                ResultList<String> newDocIds = new ResultList<>();
                newDocIds.insert(docId);
                invIndex.insert(newDocIds, word);
                vocabCount++;

            }

            listOfDocs.retrieve().findNext();
        }

        // Handle the last word in the document
        String lastWord = listOfDocs.retrieve().retrieve();
        if (invIndex.findKey(lastWord)) {
            ResultList<String> docIds = invIndex.retrieve();
            if (!docIds.contains(docId)) {
                docIds.insert(docId);
            } else {
                docIds.incrementNode();
            }
        } else {
            ResultList<String> newDocIds = new ResultList<>();
            newDocIds.insert(docId);
            invIndex.insert(newDocIds, lastWord);
            vocabCount++;

        }
        return invIndex;
    }

    public AVL<AVL<String>> buildInvertedIndexAVL() {

        AVL<AVL<String>> avl = new AVL<>();

        listOfDocs.findFirst();
        while (!listOfDocs.last()) {

            listOfDocs.retrieve().findFirst();
            String docId = listOfDocs.retrieve().retrieve();

            listOfDocs.retrieve().findNext();
            while (!listOfDocs.retrieve().last()) {

                String word = listOfDocs.retrieve().retrieve();

                if (avl.findKey(word)) {
                    if (avl.retrieve().findKey(docId))
                        avl.retrieve().incrementNode();
                    else
                        avl.retrieve().insert(docId, null);
                } else {
                    AVL<String> idAVL = new AVL<>();

                    idAVL.insert(docId, null);

                    avl.insert(word, idAVL);
                }

                listOfDocs.retrieve().findNext();
            }
            // Last word in the doc
            String word = listOfDocs.retrieve().retrieve();

            if (avl.findKey(word)) {
                avl.retrieve().insert(docId, null);
            } else {
                AVL<String> idAVL = new AVL<>();

                idAVL.insert(docId, null);

                avl.insert(word, idAVL);
            }

            listOfDocs.retrieve().findNext();
            listOfDocs.findNext();
        }

        listOfDocs.retrieve().findFirst();
        String docId = listOfDocs.retrieve().retrieve();

        listOfDocs.retrieve().findNext();
        while (!listOfDocs.retrieve().last()) {

            String word = listOfDocs.retrieve().retrieve();

            if (avl.findKey(word)) {
                if (avl.retrieve().findKey(docId)) // Same word in same document
                    avl.retrieve().incrementNode(); // then increment
                else
                    avl.retrieve().insert(docId, null); // otherwise insert new doc id
            } else {
                AVL<String> idAVL = new AVL<>();

                idAVL.insert(docId, null);

                avl.insert(word, idAVL);
            }

            listOfDocs.retrieve().findNext();
        }
        // Last word in the doc
        String word = listOfDocs.retrieve().retrieve();

        if (avl.findKey(word)) {
            avl.retrieve().insert(docId, null);
        } else {
            AVL<String> idAVL = new AVL<>();

            idAVL.insert(docId, null);

            avl.insert(word, idAVL);
        }

        listOfDocs.retrieve().findNext();
        listOfDocs.findNext();

        return avl;
    }
}
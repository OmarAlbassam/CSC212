
public class Test {
    public static void main(String[] args) {
        TextProccesor tp = new TextProccesor();
        LinkedList<String> stopWords = new LinkedList<>();
        // (Index) ulitmateList = {0:List0, 1:List1, ..., n:Listn}
       Index<List<String>> index = new Index<>();
       LinkedList<List<String>> listOfDocs = new LinkedList<>();
        // (Inverted Index - List) = {word1:List(docs), ..., wordn:List(docs)}
        // (Inverted Index - BST) = {word1:List(docs), ..., wordn:List(docs)}
        
        stopWords = (LinkedList<String>)tp.fetchStopWords();
        tp.fetchData(listOfDocs, stopWords);
        
        listOfDocs.findFirst();
        while (!listOfDocs.last()) {
            listOfDocs.retrieve().print();
            listOfDocs.findNext();
            System.out.println("-------------------");
        } listOfDocs.retrieve().print();
    }
}


public class Test {
    public static void main(String[] args) {

        TextProccesor tp = new TextProccesor();
        LinkedList<String> stopWords = new LinkedList<>();
        IndexLinkedList<List<String>> index = new IndexLinkedList<>();
        
        
        stopWords = (LinkedList<String>)tp.fetchStopWords();
        tp.fetchData(stopWords);

        tp.buildIndex(index);

        index.print();
        
        

        // listOfDocs.findFirst();
        // while (!listOfDocs.last()) {
        //     listOfDocs.retrieve().print();
        //     listOfDocs.findNext();
        //     System.out.println("-------------------");
        // } listOfDocs.retrieve().print();
    }
}

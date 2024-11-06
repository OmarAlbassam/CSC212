
public class Test {
    public static void main(String[] args) {

        TextProccesor tp = new TextProccesor();
        LinkedList<String> stopWords = new LinkedList<>();
        LinkedIndex<List<String>> index = new LinkedIndex<>();
        
        
        stopWords = (LinkedList<String>)tp.fetchStopWords();
        tp.fetchData(stopWords);

        tp.buildIndex(index);

        index.print();
        
        

        
    }
}

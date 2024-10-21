
public class Test {
    public static void main(String[] args) {
        TextProccesor tp = new TextProccesor();
        LinkedList<String> uniqueWords = new LinkedList<>();
        LinkedList<String> stopWords = new LinkedList<>();
        // (Index) ulitmateList = {0:List0, 1:List1, ..., n:Listn}
        LinkedList<List<String>> ulitmateList = new LinkedList<>();
        // (Inverted Index - List) = {word1:List(docs), ..., wordn:List(docs)}
        // (Inverted Index - BST) = {word1:List(docs), ..., wordn:List(docs)}
        
        stopWords = (LinkedList<String>)tp.fetchStopWords();
        tp.fetchData(ulitmateList, stopWords);
        uniqueWords.print();

        ulitmateList.findFirst();
        System.out.println(ulitmateList.retrieve());
        
    }
}

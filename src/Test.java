
public class Test {
    public static void main(String[] args) {

        TextProccesor tp = new TextProccesor();        
        tp.fetchData((LinkedList<String>)tp.fetchStopWords());
        LinkedIndex<List<String>> index = tp.buildIndex();
        // index.print();

        LinkedIndex<List<String>> invertedIndex = tp.buildInvertedIndex();
        // invertedIndex.print();
        
        AVL<List<String>> avl = tp.buildInvertedIndexAVL(invertedIndex);

        avl.print();
    }
}

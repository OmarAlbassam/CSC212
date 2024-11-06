
public class Test {
    public static void main(String[] args) {

        TextProccesor tp = new TextProccesor();
        LinkedList<String> stopWords = new LinkedList<>();
        LinkedIndex<List<String>> index = new LinkedIndex<>();
        
        
        stopWords = (LinkedList<String>)tp.fetchStopWords();
        tp.fetchData(stopWords);

        tp.buildIndex(index);

        index.print();
        
        AVL<Integer> avl = new AVL<>();

        avl.insert(1, null);
        avl.insert(2, null);
        avl.insert(3, null);
        avl.insert(4, null);
        avl.insert(5, null);


        // avl.insert(2, 1);
        // avl.insert(53, 1);
        // avl.insert(31, 1);
        // avl.insert(12, 1);
        // avl.insert(3, 1);
        // avl.insert(145, 1);
        // avl.insert(22, 1);
        

        
    }
}

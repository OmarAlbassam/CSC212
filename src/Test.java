
public class Test {
    public static void main(String[] args) {

        TextProccesor tp = new TextProccesor();
        tp.fetchData((LinkedList<String>)tp.fetchStopWords());
        
        SearchEngine se = new SearchEngine();
        AVL<String> avl = se.rankedSearchAVL("market sports");
        
        
        
        LinkedPQ pq = avl.makePQ();
        pq.print();


        // AVL<String> avl = se.querySearch("market OR sports AND warming");
        // avl.print();

    }
}

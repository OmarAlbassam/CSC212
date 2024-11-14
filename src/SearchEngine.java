
public class SearchEngine {
    
    private static AVL<AVL<String>> resultSet;
    private TextProccesor tp;
    SearchEngine() {
        resultSet = tp.buildInvertedIndexAVL(tp.buildInvertedIndex());
    }

    public static List<String> querySearch(String prompt) {
        
        LinkedList<String> resultDocs = new LinkedList<>();
        LinkedList<LinkedList<String>> finalDocs = new LinkedList<>();
        // 1 - market AND sports -> intersect AND: {0, 1, 2} AND {1, 2, 3} = {1}
        // 2 - aerospace OR programming -> union OR: {0, 1, 2} OR {1, 2, 3} = {0,1 ,2, 3}
        // 3 - (data AND computer) OR bagel -> intersect AND then union OR

        String[] promptWords = prompt.split(" ");
        for (int i = 0; i < promptWords.length; i++) {
            if (promptWords[i].equalsIgnoreCase("and")) {

                resultSet.findKey(promptWords[i - 1]);
                AVL<String> docIds1 = resultSet.retrieve();

                resultSet.findKey(promptWords[i + 1]);
                AVL<String> docIds2 = resultSet.retrieve();

                LinkedList<String> intersectionResult = AVL.intersect(docIds1, docIds2);

                intersectionResult.print();
            }
        }
        return resultDocs;
    }

    

    

}

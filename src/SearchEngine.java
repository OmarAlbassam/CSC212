
public class SearchEngine {
    
    AVL<List<String>> queryResultSet,
                    rankingResultSet;
    TextProccesor tp;
    SearchEngine() {
        queryResultSet = tp.buildInvertedIndexAVL(tp.buildInvertedIndex());
    }

    public String querySearch(String prompt) {
        
        LinkedList<String> resultDocs = new LinkedList<>();

        // 1 - market AND sports -> intersect AND
        // 2 - aerospace OR programming -> union OR
        // 3 - (data AND computer) OR bagel -> intersect AND then union OR

        String[] promptWords = prompt.split(" ");
        for (int i = 0; i < promptWords.length; i++) {
            if (promptWords[i].equalsIgnoreCase("AND")) {
                andOperator(promptWords[i - 1], promptWords[i + 1]);
            } else if (promptWords[i].equalsIgnoreCase("OR")) {
                orOperator(promptWords[i - 1], promptWords[i + 1]);
            }
        }

        String result = "";
        return result;
    }

    private List<String> andOperator(String firstWord, String secondWord) {

        LinkedList<String> intersectionResult = new LinkedList<>();

        if (queryResultSet.findKey(firstWord)) {
            x
        }

        if (queryResultSet.findKey(secondWord)) {

        }

        return intersectionResult;
    }

    private void orOperator(String firstWord, String secondWord) {

    }
}

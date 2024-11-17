
public class SearchEngine {

    private AVL<AVL<String>> resultSetAVL;
    private LinkedIndex<ResultList<String>> resultSetList;
    private TextProccesor tp;

    SearchEngine() {
        tp = new TextProccesor();
        tp.fetchData(tp.fetchStopWords());
        resultSetAVL = tp.buildInvertedIndexAVL();
        resultSetList = tp.buildInvertedIndex();
    }

    public AVL<String> rankedSearchAVL(String prompt) {

        String[] promptWords = prompt.toLowerCase().split(" ");
        AVL<String> results = new AVL<>();

        for (String word : promptWords) {
            if (!resultSetAVL.findKey(word)) continue;

            results.insertTreeWithFrequency(resultSetAVL.retrieve());
        }
        return results;
    }

    public AVL<String> querySearchAVL(String prompt) {


        // sport omar AND car AND house left AND OR
        String[] postfixExpression = postfix(prompt.toLowerCase());
        LinkedStack<AVL<String>> resultStack = new LinkedStack<>();

        for (String token : postfixExpression) {
            if (isOperator(token)) {
                // Pop two AVLs from the stack for the operation
                AVL<String> right = resultStack.pop();
                AVL<String> left = resultStack.pop();

                // Perform intersection or union based on the operator
                if (token.equalsIgnoreCase("AND")) {
                    resultStack.push(AVL.intersect(left, right));
                } else if (token.equalsIgnoreCase("OR")) {
                    resultStack.push(AVL.union(left, right));
                }
            } else { // It's a word
                // Retrieve the AVL<String> for the term from the term index
                AVL<String> termAVL = null;
                if (resultSetAVL.findKey(token)) 
                    termAVL = resultSetAVL.retrieve(); 
                
                if (termAVL != null) {
                    resultStack.push(termAVL);
                } else {
                    // Handle case where the term is not in the index
                    resultStack.push(new AVL<>()); // Push an empty AVL if term not found
                }
            }
        }

        return resultStack.empty() ? new AVL<>() : resultStack.pop();
    }

    public List<String> rankedSearchList(String prompt){
        
        String[] promptWords = prompt.toLowerCase().split(" ");
        ResultList<String> results = new ResultList<>();

        for (String word : promptWords) {
            if (!resultSetList.findKey(word)) continue;

            results.insertListWithFrequency(resultSetList.retrieve());
        }

        return results;
    }   

    public List<String> querySearchList(String prompt) {

        // sport omar AND car AND house left AND OR
        String[] postfixExpression = postfix(prompt.toLowerCase());
        LinkedStack<ResultList<String>> resultStack = new LinkedStack<>();

        for (String token : postfixExpression) {
            if (isOperator(token)) {
                // Pop two AVLs from the stack for the operation
                ResultList<String> right = resultStack.pop();
                ResultList<String> left = resultStack.pop();

                // Perform intersection or union based on the operator
                if (token.equalsIgnoreCase("AND")) {
                    resultStack.push(left.intersect(right));
                } else if (token.equalsIgnoreCase("OR")) {
                    resultStack.push(left.union(right));
                }
            } else { // It's a word
                // Retrieve the AVL<String> for the term from the term index
                ResultList<String> termAVL = null;
                if (resultSetList.findKey(token)) 
                    termAVL = resultSetList.retrieve(); 
                
                if (termAVL != null) {
                    resultStack.push(termAVL);
                } else {
                    // Handle case where the term is not in the index
                    resultStack.push(new ResultList<>()); // Push an empty AVL if term not found
                }
            }
        }

        return resultStack.empty() ? new ResultList<>() : resultStack.pop();
    }
    // helpers*
    private String[] postfix(String prompt) {
        LinkedStack<String> operatorStack = new LinkedStack<>();

        String[] promptWords = prompt.split(" ");
        String[] outputArray = new String[promptWords.length];

        int index = 0;
        for (String token : promptWords) {

            if (!isOperator(token)) {
                outputArray[index] = token;
                index++;
            } else { // is an operator
                if (operatorStack.empty())
                    operatorStack.push(token);
                else {
                    while (!operatorStack.empty()) {
                        String tmp = operatorStack.pop();
        
                        if (precedence(tmp) >= precedence(token)) {
                            outputArray[index] = tmp;
                            index++;
                        } else {
                            // If tmp has lower precedence, push it back onto the stack and break
                            operatorStack.push(tmp);
                            break;
                        }
                    }
                    operatorStack.push(token);
                }
            }
        }

        while (!operatorStack.empty()) {
            outputArray[index] = operatorStack.pop();
            index++;
        }

        return outputArray;
    }
    private int precedence(String operator) {
        operator = operator.toLowerCase();

        switch (operator) {
            case "and":
                return 2;
            case "or":
                return 1;
            default:
                return 0;
        }
    }

    private boolean isOperator(String operator) {
        return operator.equalsIgnoreCase("and") || operator.equalsIgnoreCase("or");
    }
}

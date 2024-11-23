
public class SearchEngine {

	private AVL<AVL<String>> invertedIndexAVL;
	private LinkedIndex<ResultList<String>> invertedIndexList;
	private LinkedIndex<ResultList<String>> indexList;
	private TextProccesor tp;
	static double queryTime = 0;

	SearchEngine() {
		tp = new TextProccesor();
		tp.fetchData(tp.fetchStopWords());
		indexList = tp.buildIndex();
		invertedIndexList = tp.buildInvertedIndex();
		invertedIndexAVL = tp.buildInvertedIndexAVL();
		dummyWarmUp();
	}
	
	// this is a dummy warm up method to avoid the first search to be slow,
	// just makes sure that the queryTime attribute is consistent.
	private void dummyWarmUp() {
		rankedSearchInvertedAVL("sports omar life marathon");
		rankedSearchInvertedList("sports omar life marathon");
		rankedSearchList("sports omar life marathon");
		querySearchInvertedAVL("sport or omar or ai and life");
		querySearchInvertedList("sport or omar or ai and life");
		querySearchList("sport or omar or ai and life");
	}

	public AVL<String> rankedSearchInvertedAVL(String prompt) {

		long startTime = System.nanoTime();
		String[] promptWords = prompt.toLowerCase().split(" ");
		AVL<String> results = new AVL<>();
		
		for (String word : promptWords) {
			if (!invertedIndexAVL.findKey(word))
				continue;
			
			results.insertTreeWithFrequency(invertedIndexAVL.retrieve());
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
		return results;
	}

	public AVL<String> querySearchInvertedAVL(String prompt) {

		long startTime = System.nanoTime();
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
				if (invertedIndexAVL.findKey(token))
					termAVL = invertedIndexAVL.retrieve();

				if (termAVL != null) {
					resultStack.push(termAVL);
				} else {
					// Handle case where the term is not in the index
					resultStack.push(new AVL<>()); // Push an empty AVL if term not found
				}
			}
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
		return resultStack.empty() ? new AVL<>() : resultStack.pop();
	}

	public List<String> rankedSearchInvertedList(String prompt) {

		long startTime = System.nanoTime();
		String[] promptWords = prompt.toLowerCase().split(" ");
		ResultList<String> results = new ResultList<>();

		for (String word : promptWords) {
			if (!invertedIndexList.findKey(word))
				continue;

			results.insertListWithFrequency(invertedIndexList.retrieve());
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
		return results;
	}

	public List<String> querySearchInvertedList(String prompt) {

		long startTime = System.nanoTime();
		// sport omar AND car AND house left AND OR
		String[] postfixExpression = postfix(prompt.toLowerCase());
		LinkedStack<ResultList<String>> resultStack = new LinkedStack<>();

		for (String token : postfixExpression) {
			if (isOperator(token)) {
				// Pop two Lists from the stack for the operation
				ResultList<String> right = resultStack.pop();
				ResultList<String> left = resultStack.pop();

				// Perform intersection or union based on the operator
				if (token.equalsIgnoreCase("AND")) {
					resultStack.push(left.intersect(right));
				} else if (token.equalsIgnoreCase("OR")) {
					resultStack.push(left.union(right));
				}
			} else { // It's a word
				// Retrieve the ResultList<String> for the term from the term index
				ResultList<String> termList = null;
				if (invertedIndexList.findKey(token))
					termList = invertedIndexList.retrieve();

				if (termList != null) {
					resultStack.push(termList);
				} else {
					// Handle case where the term is not in the index
					resultStack.push(new ResultList<>()); // Push an empty ResultList if term not found
				}
			}
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
		return resultStack.empty() ? new ResultList<>() : resultStack.pop();
	}

	public List<String> rankedSearchList(String prompt) {
		
		long startTime = System.nanoTime();

		String[] promptWords = prompt.toLowerCase().split(" ");
		ResultList<String> results = new ResultList<>();

		indexList.findFirst();
		while (!indexList.last()) {
			String docId = indexList.getKey();
			indexList.retrieve().findFirst();
			while (!indexList.retrieve().last()) {
				for (String word : promptWords) {
					if (word.equalsIgnoreCase(indexList.retrieve().retrieve())) { // do the frequency score addition
						if (results.contains(docId)) {
							results.incrementFrequencyBy(indexList.retrieve().getFrequency());
						} else
							results.insert(docId, indexList.retrieve().getFrequency());
					}
				}
				indexList.retrieve().findNext();
			}
			for (String word : promptWords) {
				if (word.equalsIgnoreCase(indexList.retrieve().retrieve())) { // do the frequency score addition
					if (results.contains(docId)) {
						results.incrementFrequencyBy(indexList.retrieve().getFrequency());
					} else
						results.insert(docId, indexList.retrieve().getFrequency());
				}
			}
			indexList.findNext();
		}

		String docId = indexList.getKey();
		indexList.retrieve().findFirst();
		while (!indexList.retrieve().last()) {
			for (String word : promptWords) {
				if (word.equalsIgnoreCase(indexList.retrieve().retrieve())) { // do the frequency score addition
					if (results.contains(docId)) {
						results.incrementFrequencyBy(indexList.retrieve().getFrequency());
					} else
						results.insert(docId, indexList.retrieve().getFrequency());
				}
			}
			indexList.retrieve().findNext();
		}
		for (String word : promptWords) {
			if (word.equalsIgnoreCase(indexList.retrieve().retrieve())) { // do the frequency score addition
				if (results.contains(docId)) {
					results.incrementFrequencyBy(indexList.retrieve().getFrequency());
				} else
					results.insert(docId, indexList.retrieve().getFrequency());
			}
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
		return results;
	}

	public List<String> querySearchList(String prompt) {
		
		long startTime = System.nanoTime();

		String[] postfixExpression = postfix(prompt.toLowerCase());
		LinkedStack<ResultList<String>> resultStack = new LinkedStack<>();

		for (String token : postfixExpression) {
			if (isOperator(token)) {
				ResultList<String> right = resultStack.pop();
				ResultList<String> left = resultStack.pop();

				// Perform intersection or union based on the operator
				if (token.equalsIgnoreCase("AND")) {
					resultStack.push(left.intersect(right));
				} else if (token.equalsIgnoreCase("OR")) {
					resultStack.push(left.union(right));
				}
			} else { // it is a word
				ResultList<String> docIds = new ResultList<>();
				indexList.findFirst();
				while (!indexList.last()) {
					if (indexList.retrieve().contains(token)) {
						docIds.insert(indexList.getKey());
					}
					indexList.findNext();
				}
				if (indexList.retrieve().contains(token)) { // last doc check!
					docIds.insert(indexList.getKey());
				}

				resultStack.push(docIds);
			}
		}
		queryTime = (double)(System.nanoTime() - startTime) / 1000000;
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

	int getToken() {
		return tp.tokensCount;
	}

	int getVocab() {
		return tp.vocabCount;
	}

	int getDocCount() {
		return tp.docCount;
	}
}

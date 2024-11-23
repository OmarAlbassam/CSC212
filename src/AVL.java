
public class AVL<T> {

    private AVLNode<T> root, current;

    public AVL() {
        root = current = null;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean full() {
        return false;
    }

    public T retrieve() {
        return current.data;
    }

    public void update(T val) {
        current.data = val;
    }

    public boolean findKey(String key) {
        if (root == null)
            return false;

        AVLNode<T> tmp = root;
        while (tmp != null) {
            if (key.compareTo(tmp.key) < 0)
                tmp = tmp.left;
            else if (key.compareTo(tmp.key) > 0)
                tmp = tmp.right;
            else { // key.compareTo(tmp.key) == 0
                current = tmp;
                return true;
            }
        }
        return false;
    }

    public static AVL<String> intersect(AVL<String> avl1, AVL<String> avl2) {
        AVL<String> result = new AVL<>();
        intersectHelper(avl1.root, avl2.root, result);
        return result;
    }

    private static void intersectHelper(AVLNode<String> root1, AVLNode<String> root2, AVL<String> result) {
        LinkedStack<AVLNode<String>> stack1 = new LinkedStack<>();
        LinkedStack<AVLNode<String>> stack2 = new LinkedStack<>();
        AVLNode<String> current1 = root1;
        AVLNode<String> current2 = root2;

        while ((current1 != null || !stack1.empty()) && (current2 != null || !stack2.empty())) {
            // Traverse to the leftmost node of both trees
            while (current1 != null) {
                stack1.push(current1);
                current1 = current1.left;
            }
            while (current2 != null) {
                stack2.push(current2);
                current2 = current2.left;
            }

            current1 = stack1.pop();
            current2 = stack2.pop();
            stack1.push(current1);
            stack2.push(current2);

            int cmp = current1.key.compareTo(current2.key);

            if (cmp == 0) {
                // Keys are equal; add to result
                result.insert(current1.key, null);
                stack1.pop();
                stack2.pop();
                current1 = current1.right;
                current2 = current2.right;
            } else if (cmp < 0) {
                // current1.key is smaller; move to next node in avl1
                stack1.pop();
                current1 = current1.right;
                current2 = null; // Keep current2 the same
            } else {
                // current2.key is smaller; move to next node in avl2
                stack2.pop();
                current2 = current2.right;
                current1 = null; // Keep current1 the same
            }
        }
    }

    public static AVL<String> union(AVL<String> avl1, AVL<String> avl2) {
        AVL<String> result = new AVL<>();
        unionHelper(avl1.root, avl2.root, result); // Add all nodes from docIds1 and docIds2
        return result;
    }

    private static void unionHelper(AVLNode<String> root1, AVLNode<String> root2, AVL<String> result) {
        LinkedStack<AVLNode<String>> stack1 = new LinkedStack<>();
        LinkedStack<AVLNode<String>> stack2 = new LinkedStack<>();
        AVLNode<String> current1 = root1;
        AVLNode<String> current2 = root2;
    
        while ((current1 != null || !stack1.empty()) || (current2 != null || !stack2.empty())) {
            // Traverse to the leftmost node of both trees
            while (current1 != null) {
                stack1.push(current1);
                current1 = current1.left;
            }
            while (current2 != null) {
                stack2.push(current2);
                current2 = current2.left;
            }
    
            if (!stack1.empty() && !stack2.empty()) {
                current1 = stack1.pop();
                current2 = stack2.pop();
                stack1.push(current1);
                stack2.push(current2);
    
                int cmp = current1.key.compareTo(current2.key);
    
                if (cmp == 0) {
                    // Keys are equal; add to result
                    result.insert(current1.key, null);
                    stack1.pop();
                    stack2.pop();
                    current1 = current1.right;
                    current2 = current2.right;
                } else if (cmp < 0) {
                    // current1.key is smaller; add to result and move to next node in avl1
                    result.insert(current1.key, null);
                    stack1.pop();
                    current1 = current1.right;
                    current2 = null; // Keep current2 the same
                } else {
                    // current2.key is smaller; add to result and move to next node in avl2
                    result.insert(current2.key, null);
                    stack2.pop();
                    current2 = current2.right;
                    current1 = null; // Keep current1 the same
                }
            } else if (!stack1.empty()) {
                // Only nodes in avl1 remain
                current1 = stack1.pop();
                result.insert(current1.key, null);
                current1 = current1.right;
            } else if (!stack2.empty()) {
                // Only nodes in avl2 remain
                current2 = stack2.pop();
                result.insert(current2.key, null);
                current2 = current2.right;
            }
        }
    }

    public void insert(String key, T val) {
        root = insertHelper(root, key, val);
    }

    public void delete(String key) {
        root = deleteHelper(root, key);
        current = root;
    }

    // **************** INSERT & DELETE HELPERS ****************
    private AVLNode<T> insertHelper(AVLNode<T> node, String key, T val) {
        if (node == null) {
            current = new AVLNode<>(key, val);
            return current;
        }
        if (key.compareTo(node.key) < 0)
            node.left = insertHelper(node.left, key, val);
        else if (key.compareTo(node.key) > 0)
            node.right = insertHelper(node.right, key, val);
        else // key.compareTo(node.key) == 0
            return node; // Duplicate key, do nothing

        updateHeight(node);
        return rebalance(node);
    }

    private AVLNode<T> deleteHelper(AVLNode<T> node, String key) {

        if (node == null)
            return null;

        // BST Recursive deletion
        if (key.compareTo(node.key) < 0)
            node.left = deleteHelper(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = deleteHelper(node.right, key);
        else { // Case: 1 child or Case: no children
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            // Case: 2 childeren
            AVLNode<T> minInRightSubtree = node.right;
            while (minInRightSubtree.left != null) {
                minInRightSubtree = minInRightSubtree.left;
            }

            node.key = minInRightSubtree.key;
            node.data = minInRightSubtree.data;
            node.right = deleteHelper(node.right, minInRightSubtree.key);
        }

        // Update node height
        updateHeight(node);

        // rebalance if needed
        return rebalance(node);
    }

    public void incrementNode() {
        current.frequency++;
    }

    public String getKey() {
        return current.key;
    }

    public int getFrequency() {
        return current.frequency;
    }

    // **************** BALANCING METHODS ****************
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int height(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = max(height(node.left), height(node.right)) + 1;
    }

    private int balanceFactor(AVLNode<T> node) {
        return height(node.right) - height(node.left);
    }

    private AVLNode<T> rotateLeft(AVLNode<T> node) {

        AVLNode<T> rightChild = node.right;

        node.right = rightChild.left;
        rightChild.left = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    private AVLNode<T> rotateRight(AVLNode<T> node) {

        AVLNode<T> leftChild = node.left;

        node.left = leftChild.right;
        leftChild.right = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private AVLNode<T> rebalance(AVLNode<T> node) {

        int balanceFactor = balanceFactor(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            // Right
            if (balanceFactor(node.left) <= 0)
                node = rotateRight(node);
            // Left Right
            else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }
        // Right-heacy?
        else if (balanceFactor > 1) {
            // Left
            if (balanceFactor(node.right) >= 0)
                node = rotateLeft(node);
            // Right Left
            else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }
        return node;
    }

    public List<String> makeList() {
        LinkedList<String> list = new LinkedList<>();
        makeListHelper(root, list);
        return list;
    }

    private void makeListHelper(AVLNode<T> node, List<String> list) {
        if (node == null)
            return;

        makeListHelper(node.left, list);

        list.insert(node.key);

        makeListHelper(node.right, list);
    }

    public LinkedPQ<String> makePQ() {
        LinkedPQ<String> queue = new LinkedPQ<>();
        makePQHelper(root, queue);
        return queue;
    }

    private void makePQHelper(AVLNode<T> node, LinkedPQ<String> queue) {
        if (node == null)
            return;

        makePQHelper(node.left, queue);

        queue.enqueue(node.key, node.frequency);

        makePQHelper(node.right, queue);
    }

    // Ranked Retrieval Methods
    public void insertTreeWithFrequency(AVL<T> avl) {
        insertTreeWithFrequencyHelper(avl.root, this);
    }

    private void insertTreeWithFrequencyHelper(AVLNode<T> node, AVL<T> newTree) {
        if (node == null)
            return;

        // Traverse the left subtree
        insertTreeWithFrequencyHelper(node.left, newTree);

        // Insert the current key into the new tree
        if (!newTree.findKey(node.key)) {
            newTree.insert(node.key, null);
            newTree.current.frequency = node.frequency;
        } else
            newTree.current.frequency += node.frequency;

        // Traverse the right subtree
        insertTreeWithFrequencyHelper(node.right, newTree);
    }

    // **************** DEBUGGING METHODS ****************

    // public static void print(AVL<AVL<String>> outerAVL) {
    // printOuterAVLHelper(outerAVL.root, 0);
    // }

    // private static void printOuterAVLHelper(AVLNode<AVL<String>> node, int level)
    // {
    // if (node != null) {
    // printOuterAVLHelper(node.right, level + 1);

    // // Print the outer AVL node's level and then its inner AVL keys
    // System.out.print("Level " + level + ", (" + node.key + ")" + " -> AVL Keys:
    // ");
    // printInnerAVLKeys(node.data);

    // printOuterAVLHelper(node.left, level + 1);
    // }
    // }

    // private static void printInnerAVLKeys(AVL<String> innerAVL) {
    // printInnerAVLHelper(innerAVL.root);
    // System.out.println(); // Newline after printing all keys of one inner AVL
    // }

    // private static void printInnerAVLHelper(AVLNode<String> node) {
    // if (node != null) {
    // printInnerAVLHelper(node.left);
    // System.out.print(node.key + " "); // Print each key in the inner AVL
    // printInnerAVLHelper(node.right);
    // }
    // }

    // @SuppressWarnings("unchecked")
    // public void print() {
    // if (root == null) {
    // System.out.println("Tree is empty");
    // return;
    // }

    // Queue<AVLNode<AVL<T>>> queue = new LinkedList<>();
    // queue.add((AVLNode<AVL<T>>) root);

    // int level = 0;
    // while (!queue.isEmpty()) {
    // int levelSize = queue.size(); // Number of nodes in the current level

    // System.out.println("Level " + level + ":");

    // for (int i = 0; i < levelSize; i++) {
    // AVLNode<AVL<T>> node = queue.poll();

    // // Print the node key and balance factor
    // System.out.println("Key: " + node.key + ", BF: " + balanceFactor((AVLNode<T>)
    // node) + ", Frequency: "
    // + node.frequency);
    // // node.data.print(); // Print the list data in each node

    // // Add left and right children to the queue if they exist
    // if (node.left != null) {
    // queue.add(node.left);
    // }
    // if (node.right != null) {
    // queue.add(node.right);
    // }
    // }

    // System.out.println(); // Newline after each level
    // level++;
    // }

    // }
}

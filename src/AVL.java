// The balance factor "BF" of a node denotes the 
// difference of the heights "H" of the right and
// left subtree ("node.right" and "node.left"), that is:
//           BF(node) = H(node.right) - H(node.left)

// Three important cases:
// 1 - If the balance factor is < 0, the node is said to be left-heavy
// 2 - If the balance factor is > 0, the node is said to be right-heavy
// 3 - If the balance factor is = 0, the node is said to be balanced

// Implemeneted methods height(AVLNode) returns the height of the node, and
// balanceFactor(AVLNode) returns the balance factor (rightHeight - leftHeight) 

// Balancing a left-heavy node:
// Right rotation
// Left-right Rotation

// Balancing a right-heavy node:
// Left rotation
// Right-left rotation

// *** THESE IMPORTS WERE STRICTLY USED FOR DEBUGGING THE PRINT METHOD ***
import java.util.LinkedList;
import java.util.Queue;

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
        intersectHelper(avl1.root, avl2, result);
        return result;
    }

    private static void intersectHelper(AVLNode<String> node, AVL<String> avl2, AVL<String> resultAvl) {
        if (node == null)
            return;

        // traverse left sub tree
        intersectHelper(node.left, avl2, resultAvl);

        if (avl2.findKey(node.key)) {
            resultAvl.insert(node.key, null);
        }

        // traverse right sub tree
        intersectHelper(node.right, avl2, resultAvl);
    }

    public static AVL<String> union(AVL<String> avl1, AVL<String> avl2) {
        AVL<String> result = new AVL<>();
        unionHelper(avl1.root, result); // Add all nodes from docIds1
        unionHelper(avl2.root, result); // Add all nodes from docIds2
        return result;
    }

    private static void unionHelper(AVLNode<String> node, AVL<String> result) {
        if (node == null)
            return;

        // Traverse left subtree
        unionHelper(node.left, result);

        // Insert node data into the result AVL
        if (!result.findKey(node.key)) {
            result.insert(node.key, null);
        }

        // Traverse right subtree
        unionHelper(node.right, result);
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

    @SuppressWarnings("unchecked")
    public void print() {
    if (root == null) {
    System.out.println("Tree is empty");
    return;
    }

    Queue<AVLNode<AVL<T>>> queue = new LinkedList<>();
    queue.add((AVLNode<AVL<T>>) root);

    int level = 0;
    while (!queue.isEmpty()) {
    int levelSize = queue.size(); // Number of nodes in the current level

    System.out.println("Level " + level + ":");

    for (int i = 0; i < levelSize; i++) {
    AVLNode<AVL<T>> node = queue.poll();

    // Print the node key and balance factor
    System.out.println("Key: " + node.key + ", BF: " + balanceFactor((AVLNode<T>)
    node) + ", Frequency: "
    + node.frequency);
    // node.data.print(); // Print the list data in each node

    // Add left and right children to the queue if they exist
    if (node.left != null) {
    queue.add(node.left);
    }
    if (node.right != null) {
    queue.add(node.right);
    }
    }

    System.out.println(); // Newline after each level
    level++;
    }

    }
}

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

    public boolean findKey(int key) {
        if (empty())
            return false;
    
        AVLNode<T> tmp = root;
        while (tmp != null) {
            if (key < tmp.key)
                tmp = tmp.left;
            else if (key > tmp.key)
                tmp = tmp.right;
            else {
                current = tmp;
                return true;
            }
        }
        return false;
    }
    

    public void insert(int key, T val) {
        root = insertHelper(root, key, val);
    }

    public void delete(int key) {
        root = deleteHelper(root, key);
        current = root;
    }

    // **************** HELPER METHODS ****************
    private AVLNode<T> insertHelper(AVLNode<T> node, int key, T val) {
        if (node == null) {
            current = new AVLNode<>(key, val);
            return current;
        }
        if (key < node.key)
            node.left = insertHelper(node.left, key, val);
        else if (key > node.key)
            node.right = insertHelper(node.right, key, val);
        else
            return node; // Duplicate key, do nothing
    
        updateHeight(node);
        return rebalance(node);
    }

    private AVLNode<T> deleteHelper(AVLNode<T> node, int key) {
        
        if (node == null) 
            return null;
        
        // BST Recursive deletion
        if (key < node.key) 
            node.left = deleteHelper(node.left, key);
        else if (key > node.key)
            node.right = deleteHelper(node.right, key);
        else { // Case: 1 child
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            // Case: 2 childeren or no children
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

    private int height(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = max(height(node.left), height(node.right)) + 1;
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

    private int max(int a, int b) {
        return a > b ? a : b;
    }

}


enum Order {
    inOrder, postOrder, preOrder
};

enum Relative {
    Root, Parent, LeftChild, RightChild
};

public class AVL<T> {

    private AVLNode<T> root, current;

    public AVL() {
        root = current = null;
    }

    public AVLNode<T> getRoot() {
        return root;
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

    public boolean findKey(int tkey) {
        AVLNode<T> p = root, q = root;

        if (empty())
            return false;

        while (p != null) {
            q = p;
            if (p.key == tkey) {
                current = p;
                return true;
            } else if (tkey < p.key)
                p = p.left;
            else
                p = p.right;
        }

        current = q;
        return false;
    }

    public void update(T val) {
        current.data = val;
    }

    public void insert(int key, T data) { // r is root on first call
        root = insertNode(new AVLNode<>(key, data), root);
    }

    private AVLNode<T> insertNode(AVLNode<T> newNode, AVLNode<T> r) {
        if (r == null)
            r = new AVLNode<T>(newNode);
        else if (newNode.key < r.key) {
            r.left = insertNode(newNode, r.left);
            if (height(r.right) - height(r.left) == -2) // need to rebalance
                if (newNode.key < r.left.key)
                    r = ll_Rotation(r);
                else
                    r = lr_Rotation(r);
        } else if (newNode.key > r.key) {
            r.right = insertNode(newNode, r.right);
            if (height(r.right) - height(r.left) == 2) // need to rebalance
                if (newNode.key > r.right.key)
                    r = rr_Rotation(r);
                else
                    r = rl_Rotation(r);
        } else
            ;
        // Duplicate key; do nothing
        r.height = max(height(r.left), height(r.right)) + 1;
        return r;
    }

    public void delete(int key) {
        root = deleteNode(root, key);
    }

    private AVLNode<T> deleteNode(AVLNode<T> root, int key) {
        // STEP 1: CODE FOR STANDARD BST DELETE HERE (without return)
        // Search for tkey
        int k = key;
        AVLNode<T> p = root;
        AVLNode<T> q = null; // Parent of p
        while (p != null) {
            if (k < p.key) {
                q = p;
                p = p.left;
            } else if (k > p.key) {
                q = p;
                p = p.right;
            } else { // Have found the key 
                if (p.left != null && p.right != null) { // Deleted node has 2 children
                    AVLNode<T> min = p.right;
                    q = p;
                    while (min.left != null) { // This loop finds min in right subtree
                        q = min;
                        min = min.left;
                    }
                    // After the loop:
                    // p is wanted for deletion
                    // q is parent of min
                    // min is the min in right subtree

                    // Swapping min & p
                    p.key = min.key;
                    p.data = min.data;
                    k = min.key;
                    p = min;
                }

                // Now wanted to be deleted is p
                if (p.left != null) // if one child on left
                    p = p.left;
                else // if one child on right or no children
                    p = p.right;

                if (q == null)  // No parent for p
                    root = p;
                else {
                    if (k < q.key)
                        q.left = p;
                    else
                        q.right = p;
                }
                
                current = root;
                break;
            }
        }

        if (root == null) // If the tree has only one node then return
            return root;
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether this node
        // became unbalanced)
        int balance = getBalance(root);
        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance < -1 && getBalance(root.left) <= 0)
            return ll_Rotation(root);
        // Left Right Case
        if (balance < -1 && getBalance(root.left) > 0)
            return lr_Rotation(root);
        // Right Right Case
        if (balance > 1 && getBalance(root.right) >= 0)
            return rr_Rotation(root);
        // Right Left Case
        if (balance > 1 && getBalance(root.right) < 0)
            return rl_Rotation(root);
        return root;
        
    }

    // HELPER METHODS
    private int height(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(AVLNode<T> node) {
        int leftChildHeight = height(node.left),
            rightChildHeight = height(node.right);
        node.height = max(leftChildHeight, rightChildHeight) + 1;
    }

    private int balanceFactor(AVLNode<T> node) {
        return height(node.right) - height(node.left);
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private AVLNode<T> rr_Rotation(AVLNode<T> A) {
        AVLNode<T> B = A.right;
        A.right = B.left;
        B.left = A;
        A.height = max(height(A.left), height(A.right)) + 1;
        B.height = max(height(B.right), A.height) + 1;
        return B;
    }

    private AVLNode<T> ll_Rotation(AVLNode<T> A) {
        AVLNode<T> B = A.left;
        A.left = B.right;
        B.right = A;
        A.height = max(height(A.left), height(A.right)) + 1;
        B.height = max(height(B.left), A.height) + 1;
        return B;
    }

    private AVLNode<T> lr_Rotation(AVLNode<T> A) {
        A.left = rr_Rotation(A.left);
        return ll_Rotation(A);
    }

    private AVLNode<T> rl_Rotation(AVLNode<T> A) {
        A.right = ll_Rotation(A.right);
        return rr_Rotation(A);
    }

    private int getBalance(AVLNode<T> node) {
        if (node == null)
            return 0;
        return height(node.right) - height(node.left);

    }

}

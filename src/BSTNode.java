public class BSTNode<T> {
    public int key;
    public T data;
    public BSTNode<T> left, right;

    BSTNode(int k, T val) {
        data = val;
        key = k;
    }

    BSTNode() {
        data = null;
        key = 0;
    }

    BSTNode(int k, T val, BSTNode<T> l, BSTNode<T> r) {
        data = val;
        key = k;
        right = r;
        left = l;
    }
}

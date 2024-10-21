
public class Node<T> {

    public T data;
    public Node<T> next;
    public Node<T> previous;

    public Node() {
        data = null;
        next = null;
        previous = null;
    }

    public Node(T value) {
        data = value;
        next = null;
        previous = null;
    }

    public void setData(T value) {
        data = value;
    }

    public void setNext(Node<T> node) {
        next = node;
    }

    public void setPrevious(Node<T> node) {
        previous = node;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

}

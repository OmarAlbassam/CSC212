public class LinkedStack<T> implements Stack<T> {

    private Node<T> top;

    LinkedStack() {
        top = null;
    }

    public Node<T> getTop() {
        return top;
    }

    public boolean empty() {
        return top == null;
    }

    public boolean full() {
        return false;
    }

    public void push(T e) {
        Node<T> tmp = new Node<T>(e);
        tmp.next = top;
        top = tmp;
    }

    public T pop() {
        T tmp = top.data;
        top = top.next;
        return tmp;
    }

    // Debuggin methods
    public void print() {
        Node<T> tmp = top;

        while (tmp.next != null) {
            System.out.print(tmp.data + " -> ");
            tmp = tmp.next;
        }
        System.out.println(tmp.data);
    }

}
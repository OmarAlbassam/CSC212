
public class IndexNode<T> {

    public String key;
    public T data;
    public IndexNode<T> next;

    public IndexNode() {
        data = null;
        next = null;
        key = null;
    }

    public IndexNode(T value, String k) {
        data = value;
        next = null;
        key = k;
    }

    

}

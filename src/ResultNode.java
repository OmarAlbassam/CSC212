
public class ResultNode<T> {

    public T data;
    public ResultNode<T> next;
    public int frequency;

    public ResultNode() {
        data = null;
        next = null;
        frequency = 1;
    }

    public ResultNode(T value) {
        data = value;
        next = null;
        frequency = 1;
    }

    public ResultNode(T value, int freq) {
        this.data = value;
        this.frequency = freq;
        next = null;
    }

}

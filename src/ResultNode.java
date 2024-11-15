
public class ResultNode {

    public String data;
    public ResultNode next;
    public int frequency;

    public ResultNode() {
        data = null;
        next = null;
        frequency = 1;
    }

    public ResultNode(String value) {
        data = value;
        next = null;
    }

    public ResultNode(String value, int freq) {
        this.data = value;
        this.frequency = freq;
        next = null;
    }

}

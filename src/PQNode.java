public class PQNode {
    public String data;
    public int priority;
    public PQNode next;

    public PQNode() {
        next = null;
    }

    public PQNode(String e, int p) {
        data = e;
        priority = p;
    }

    // Setters/Getters?
}
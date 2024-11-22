public class LinkedPQ<T> {
    private int size;
    private PQNode<T> head;

    /* tail is of no use here. */
    public LinkedPQ() {
        head = null;
        size = 0;
    }

    public int length() {
        return size;
    }

    public boolean full() {
        return false;
    }

    public void enqueue(T e, int pty) {
        PQNode<T> tmp = new PQNode<>(e, pty);
        if ((size == 0) || (pty > head.priority)) {
            tmp.next = head;
            head = tmp;
        } else {
            PQNode<T> p = head;
            PQNode<T> q = null;
            while ((p != null) && (pty <= p.priority)) {
                q = p;
                p = p.next;
            }
            tmp.next = p;
            q.next = tmp;
        }
        size++;
    }

    public PQElement<T> serve() {
        PQNode<T> node = head;
        PQElement<T> pqe = new PQElement<>(node.data, node.priority);
        head = head.next;
        size--;
        return pqe;
    }

    public void print() {
        PQNode<T> tmp = head;
        for (int i = 0; i < length(); i++) {
            if (tmp.next != null)
                System.out.print(tmp.data + ":" + tmp.priority + " -> ");
            else
                System.out.println(tmp.data + ":" + tmp.priority);
            tmp = tmp.next;
        }
    }

    public String result() {
        if (head == null)
            return "No Results";

        StringBuilder result = new StringBuilder("Document ID\t\tScore\n");
        PQNode<T> tmp = head;

        while (tmp != null) {
            result.append(tmp.data).append("\t\t").append(tmp.priority).append("\n");
            tmp = tmp.next;
        }

        return result.toString();
    }
}

	// public String Result() {
	// 	String result = "";
	// 	PQNode tmp = head;
	// 	int j = 0;// to add \n after 8 iterations so it fills the lines
	// 	for (int i = 0; i < length(); i++) {
	// 		if (tmp.next != null) {
	// 			result += "( "+tmp.data + " : " + tmp.priority+" ) ";
	// 			if (j == 7) {
	// 				result += "\n";
	// 				j = 0;
	// 			} else 
	// 				j++;
				
	// 		} else {
	// 			result += "( "+tmp.data + " : " + tmp.priority+" ) "; // Append with a newline
	// 		}

	// 		tmp = tmp.next;
	// 	}
	// 	if (result.isEmpty())
	// 		result = "Not Found";
	// 	return result;
	// }


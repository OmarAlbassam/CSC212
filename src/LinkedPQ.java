public class LinkedPQ {
	private int size;
	private PQNode head;

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

	public void enqueue(String e, int pty) {
		PQNode tmp = new PQNode(e, pty);
		if ((size == 0) || (pty > head.priority)) {
			tmp.next = head;
			head = tmp;
		} else {
			PQNode p = head;
			PQNode q = null;
			while ((p != null) && (pty <= p.priority)) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}

	public PQElement serve() {
		PQNode node = head;
		PQElement pqe = new PQElement(node.data, node.priority);
		head = head.next;
		size--;
		return pqe;
	}

	public void print() {
		PQNode tmp = head;
		for (int i = 0; i < length(); i++) {
			if (tmp.next != null)
				System.out.print(tmp.data + ":" + tmp.priority + " -> ");
			else
				System.out.println(tmp.data + ":" + tmp.priority);
			tmp = tmp.next;
		}
	}

	public String result() {
		String result = "Document ID\t\tScore\n";
		PQNode tmp = head;

		while (tmp != null) {
			result += tmp.data + "\t\t" + tmp.priority + "\n";
			tmp = tmp.next;
		}

		return result;
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

}
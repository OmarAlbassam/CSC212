

public class ResultList {
    
    private ResultNode head;
    private ResultNode current;
    
    ResultList() {
        head = current = null;
    }

    public void findFirst() {
        current = head;
    }

    
    public void findNext() {
        current = current.next;
    }

    
    public String retrieve() {
        return current.data;
    }

    
    public void update(String e) {
        current.data = e;
    }

    public int getFrequency() {
        return current.frequency;
    }

    
    public void insert(String e, int freq) {
        ResultNode temp;
        if (empty()) {
            head = current = new ResultNode(e, freq);
        } else {
            temp = current.next;
            current.next = new ResultNode(e, freq);
            current = current.next;
            current.next = temp;
        }
    }

    
    public void remove() {
        if (head == current) {
            head = head.next;
        } else {
            ResultNode tmp = head;
            while (tmp.next != current) 
                tmp = tmp.next;
            tmp.next = current.next;
        }
        
        if (current.next == null)
            current = head;
        else
            current = current.next;
        
    }

    
    public boolean full() {
        return false;
    }

    
    public boolean empty() {
        return head == null;
    }

    
    public boolean last() {
        return current.next == null;
    }

    
    public void clear() {
        head = current = null;
    }

    public void print() {
        if (head == null)
            return;
        current = head;
        while (current.next != null) {
            System.out.print(current.data + ":" + current.frequency + " -> ");
            current = current.next;
        }
        System.out.println(current.data);
    }

    public boolean contains(String val) {
        if (head == null)
            return false;
        ResultNode tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(val))
                return true;
            tmp = tmp.next;
        }
        return false;
    }

}

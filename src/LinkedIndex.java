public class LinkedIndex<T> {
    
    private IndexNode<T> head, current;

    LinkedIndex() {
        head = current = null;
    }

    public void insert(T e, String key) {
        IndexNode<T> temp;
        if (empty()) {
            head = current = new IndexNode<T>(e, key);
        } else {
            temp = current.next;
            current.next = new IndexNode<T>(e, key);
            current = current.next;
            current.next = temp;
        }
    }
    
    public void remove() {
        if (head == current) {
            head = head.next;
        } else {
            IndexNode<T> tmp = head;
            while (tmp.next != current) 
                tmp = tmp.next;
            tmp.next = current.next;
        }
        
        if (current.next == null)
            current = head;
        else
            current = current.next;
        
    }

    public T find(T val) {
        if (head == null)
            return null;
        IndexNode<T> tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(val))
                return tmp.data;
            tmp = tmp.next;
        }
        return null;
    }

    public boolean findKey(String val) {
        if (head == null)
            return false;

        IndexNode<T> tmp = head;
        while (tmp != null) {
            if (tmp.key.equals(val)) { // contains key
                current = tmp;
                return true;
            }
            tmp = tmp.next;
        }
        return false;
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
    
    public void findFirst() {
        current = head;
    }

    
    public void findNext() {
        current = current.next;
    }

    
    public T retrieve() {
        return current.data;
    }

    public String getKey() {
        return current.key;
    }
    
    public void update(T e) {
        current.data = e;
    }

    // Method to print the key alongside the list
    @SuppressWarnings("unchecked")
    public void print() {
        IndexNode<T> tmp = head;
        while (tmp.next != null) {
            System.out.println(tmp.key + ":");
            ((LinkedList<T>)tmp.data).print();
            tmp = tmp.next;
        }
        System.out.println(tmp.key + ":");
        ((LinkedList<T>)tmp.data).print();
    }

    
}

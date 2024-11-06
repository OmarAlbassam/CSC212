public class LinkedIndex<T> extends LinkedList<T>  {
    
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
    @Override
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

    public boolean contains(T val) {
        if (head == null)
            return false;
        IndexNode<T> tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(val))
                return true;
            tmp = tmp.next;
        }
        return false;
    }
    
    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean last() {
        return current.next == null;
    }
    @Override
    public void findFirst() {
        current = head;
    }

    @Override
    public void findNext() {
        current = current.next;
    }

    @Override
    public T retrieve() {
        return current.data;
    }

    @Override
    public void update(T e) {
        current.data = e;
    }

    // Method to print the key alongside the list
    @Override
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

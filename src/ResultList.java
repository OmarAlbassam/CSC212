

public class ResultList<T> implements List<T> {
    
    private ResultNode<T> head;
    private ResultNode<T> current;
    
    ResultList() {
        head = current = null;
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

    
    public void update(T e) {
        current.data = e;
    }

    public int getFrequency() {
        return current.frequency;
    }

    
    public void insert(T e) {
        ResultNode<T> temp;
        if (empty()) {
            head = current = new ResultNode<T>(e);
        } else {
            temp = current.next;
            current.next = new ResultNode<T>(e);
            current = current.next;
            current.next = temp;
        }
    }
    public void insert(T e, int freq) {
        ResultNode<T> temp;
        if (empty()) {
            head = current = new ResultNode<T>(e, freq);
        } else {
            temp = current.next;
            current.next = new ResultNode<T>(e, freq);
            current = current.next;
            current.next = temp;
        }
    }

    
    public void remove() {
        if (head == current) {
            head = head.next;
        } else {
            ResultNode<T> tmp = head;
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
        System.out.println(current.data + ":" + current.frequency);
    }

    // public boolean contains(String val) {
    //     if (head == null)
    //         return false;
    //     ResultNode<T> tmp = head;
    //     while (tmp != null) {
    //         if (tmp.data.equals(val))
    //             return true;
    //         tmp = tmp.next;
    //     }
    //     return false;
    // }

    public boolean contains(T val) {
        if (head == null)
            return false;
        ResultNode<T> tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(val)) {
                current = tmp;
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    public void incrementNode() {
        current.frequency++;
    }

    public ResultList<T> intersect(ResultList<T> l1) {
        ResultList<T> tmpList = new ResultList<>();
        ResultNode<T> tmp = head;

        while (tmp != null) {
            if (l1.contains(tmp.data))
                tmpList.insert(tmp.data);
            tmp = tmp.next;
        }
        return tmpList;
    }

    public ResultList<T> union(ResultList<T> l1) {
        ResultList<T> tmpList = new ResultList<T>();
        ResultNode<T> tmp = head;

        while (tmp != null) {
            tmpList.insert(tmp.data);
            tmp = tmp.next;
        }
        l1.findFirst();
        while (!l1.last()) {
            if (tmpList.contains(l1.retrieve())) {
                // Do nothing
            } else {
                tmpList.insert(l1.retrieve());
            }
            l1.findNext();
        }
        if (tmpList.contains(l1.retrieve())) {
            // Do nothing
        } else {
            tmpList.insert(l1.retrieve());

        }
        return tmpList;
    }

    public void insertListWithFrequency(ResultList<T> list) {

        ResultNode<T> tmp = list.head;
        while (tmp != null) {

            if (this.contains(tmp.data))
                current.frequency += tmp.frequency;
            else {
                insert(tmp.data);
                current.frequency = tmp.frequency;
            }

            tmp = tmp.next;
        }
    }

}

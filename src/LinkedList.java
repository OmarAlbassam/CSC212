
public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> current;
    
    LinkedList() {
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

    
    public void insert(T e) {
        Node<T> temp;
        if (empty()) {
            head = current = new Node<T>(e);
        } else {
            temp = current.next;
            current.next = new Node<T>(e);
            current = current.next;
            current.next = temp;
        }
    }

    
    public void remove() {
        if (head == current) {
            head = head.next;
        } else {
            Node<T> tmp = head;
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
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println(current.data);
    }

    public boolean contains(T val) {
        if (head == null)
            return false;
        Node<T> tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(val))
                return true;
            tmp = tmp.next;
        }
        return false;
    }

    public String result() {
        
        if (head == null)
            return "No Results";
 
        String result = "{";
        Node<T> tmp = head;
        while (tmp.next != null) {
            result += tmp.data + ", ";
            tmp = tmp.next;
        } result += tmp.data + "}";

        return result;
    }

    public LinkedList<T> intersect (LinkedList<T> l1){
        LinkedList<T> tmpList = new LinkedList<T>();
        Node<T> tmp = head;

        while (!tmp.equals(null)){
        if(l1.contains(tmp.data))
        tmpList.insert(tmp.data);
        tmp = tmp.next;
        }
        return tmpList;
    }

    public LinkedList<T> union (LinkedList<T> l1){
        LinkedList<T> tmpList = new LinkedList<T>();
        Node<T> tmp = head;

        while(tmp != null){
            tmpList.insert(tmp.data);
            tmp = tmp.next;
        }
        l1.findFirst();
        while(!l1.last()){
            if(tmpList.contains(null)){

            }
            else{
                tmpList.insert(l1.retrieve());
            }
            l1.findNext(); 
        }
        if(tmpList.contains(null)){

        }
        else{
            tmpList.insert(l1.retrieve());

        }
        return tmpList;
    }

}


public class ResultList<T> implements List<T> {

    private ResultNode<T> head;
    private ResultNode<T> current;

    ResultList() {
        head = current = null;
    }

    ResultList(ResultList<T> list) {
        ResultNode<T> listNode = list.head;
        while (listNode != null) {
            this.insert(listNode.data, listNode.frequency);
            listNode = listNode.next;
        }
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

    public void incrementFrequencyBy(int i) {
        current.frequency += i;
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

        if (l1.head == null || this.head == null)
            return new ResultList<>();

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

        if (l1.head == null)
            return new ResultList<>(this);
        if (this.head == null)
            return new ResultList<>(l1);

        ResultList<T> tmpList = new ResultList<T>();
        ResultNode<T> tmp = head;

        while (tmp != null) {
            tmpList.insert(tmp.data);
            tmp = tmp.next;
        }
        l1.findFirst();
        while (!l1.last()) {
            if (!tmpList.contains(l1.retrieve()))
                tmpList.insert(l1.retrieve());

            l1.findNext();
        }
        if (!tmpList.contains(l1.retrieve()))
            tmpList.insert(l1.retrieve());

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

    public String result() {

        if (head == null)
            return "No Results";

        String result = "{";
        ResultNode<T> tmp = head;
        while (tmp.next != null) {
            result += tmp.data + ", ";
            tmp = tmp.next;
        }
        result += tmp.data + "}";

        return result;
    }

    public LinkedPQ<T> makePQ() {
        if (head == null)
            return new LinkedPQ<>();

        LinkedPQ<T> queue = new LinkedPQ<>();
        ResultNode<T> tmp = head;
        while (tmp != null) {
            queue.enqueue(tmp.data, tmp.frequency);
            tmp = tmp.next;
        }
        return queue;
    }

}
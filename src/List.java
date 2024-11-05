
public interface List <T> {
    
    public void findFirst();
    public void findNext();
    public T retrieve();
    public void update(T e);
    public void insert(T e);
    public void remove();
    public boolean full();
    public boolean empty();
    public boolean last();
    public void print();
    public boolean contains(T val);
}

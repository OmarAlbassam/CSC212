public class PQElement<T> {
    public int priority;
    public T data;

    PQElement(T d, int p) {
        priority = p;;
        data = d;
    }
}

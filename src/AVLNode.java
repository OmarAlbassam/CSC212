
public class AVLNode<T> {
	public String key;
	public int frequency;
	public T data;
	public int height;	// Must be in range (+1, 0, -1)
	public AVLNode<T> left, right;
	
	public AVLNode(String key, T data) {
		this.key = key;
		this.data = data;
		height = 0;
		left = right = null;
		frequency = 1;
	}

	public AVLNode(AVLNode<T> n) {
		key = n.key;
		data = n.data;
		height = n.height;
		left = n.left;
		right = n.right;
		frequency = 1;
	}

}

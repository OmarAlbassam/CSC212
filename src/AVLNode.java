

public class AVLNode<T> {
	public int key;
	public T data;
	public int height;	// Balance is enum (+1, 0, -1)
	public AVLNode<T> left, right;
	
	public AVLNode(int key, T data) {
		this.key = key;
		this.data = data;
		height = 0;
		left = right = null;
	}

	public AVLNode(AVLNode<T> n) {
		key = n.key;
		data = n.data;
		height = n.height;
		left = n.left;
		right = n.right;
	}

	
}

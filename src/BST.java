public class BST<T> {

    BSTNode<T> root, current;
	
	public BST() {
		root = current = null;
	}
	
	public boolean empty() {
		return root == null;
	}
	
	public boolean full() {
		return false;
	}
	
	public T retrieve () {
		return current.data;
	}

    public boolean findkey(int tkey) {
		BSTNode<T> p = root, q = root;
				
		if(empty())
			return false;
		
		while(p != null) {
			q = p;
			if(p.key == tkey) {
				current = p;
				return true;
			}
			else if(tkey < p.key)
				p = p.left;
			else
				p = p.right;
		}
		
		current = q;
		return false;
	}


    public boolean insert(int k, T val) {
		BSTNode<T> p, q = current;
		
		if(findkey(k)) {
			current = q;  // findkey() modifies current
			return false; // key already in the BST
		}
		
		p = new BSTNode<T>(k, val, null, null);
		if (empty()) {
			root = current = p;
			return true;
		}
		else {
			// current is pointing to parent of the new key
			if (k < current.key)
				current.left = p;
			else
				current.right = p;
			current = p;
			return true;
		}
	}

    public boolean remove_key(int tkey) {
		
        // Search for tkey
        int k = tkey;
        BSTNode<T> p = root;
        BSTNode<T> q = null; // Parent of p
        while (p != null) {
            if (k < p.key) {
                q = p;
                p = p.left;
            } else if (k > p.key) {
                q = p;
                p = p.right;
            } else { // Have found the key 
                if (p.left != null && p.right != null) { // Deleted node has 2 children
                    BSTNode<T> min = p.right;
                    q = p;
                    while (min.left != null) { // This loop finds min in right subtree
                        q = min;
                        min = min.left;
                    }
                    // After the loop:
                    // p is wanted for deletion
                    // q is parent of min
                    // min is the min in right subtree

                    // Swapping min & p
                    p.key = min.key;
                    p.data = min.data;
                    k = min.key;
                    p = min;
                }

                // Now wanted to be deleted is p
                if (p.left != null) // if one child on left
                    p = p.left;
                else // if one child on right or no children
                    p = p.right;

                if (q == null)  // No parent for p
                    root = p;
                else {
                    if (k < q.key)
                        q.left = p;
                    else
                        q.right = p;
                }
                
                current = root;
                return true;
            }
        }
        
        return false; // Key not found
	}

}

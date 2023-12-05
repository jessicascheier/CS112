import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;

/*
 * Binary Search Tree (BST) implementation
 * 
 * K is a class that extends the Comparable interface.
 * 
 * By saying extends Comparable we enforce K class to have
 * the compareTo method implemented.
 */
public class BST<K extends Comparable<K>, V> {
    
    private Node root; // root node of the BST
    private int  size; // number of items (nodes) in the BST

    // Node class keeps the pair (key, value) together
    private class Node {
        K key;     // key that is used for searching    
        V value;   // value associated with the key
        private Node left;   // left subtree
        private Node right;  // right subtree

        public Node (K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // Insert the pair <key, value> into BST
    // If already in the BST, update it. No DUPLICATE KEY ALLOWED
    public void put (K key, V value) {

        // 1. Search for key
        Node ptr = root;   // starts pointer at root
        Node prev = null;  // previous is one step behind ptr
        int cmp = 0;

        while ( ptr != null ) {
            cmp = key.compareTo(ptr.key);
            if ( cmp == 0 ) {
                // found key in the BST, update value for key
                ptr.value = value;
                return;
            } 
            prev = ptr; // prev points to where ptr is pointing to

            // now ptr will step down
            if ( cmp < 0 ) {
                // target is smaller than ptr.key
                ptr = ptr.left;
            } else {
                // target is greater than ptr.key
                ptr = ptr.right;
            }
        }

        // ptr is null at this point, why?
        // previous is at the parent where key,value will be inserted

        // 2. Key not present in BST
        // Insert at the failure point (must keep a prev pointer)
        // Two cases: bst is empty or bst has at least one element
        Node newNode = new Node(key,value); // create a new node to be inserted

        if ( prev == null ) {
            // bst is empty
            root = newNode;
        } else if ( cmp < 0 ) {
            // hookup to the left of prev
            prev.left = newNode;
        } else {
            // hookup to the right of prev
            prev.right = newNode;
        }
        
        size += 1;
    }

    // Searches for target
    // Returns the value associated with @target
    // If @target is not present then return null
    public V get (K target) {
        Node ptr = root;   // starts pointer at root

        while ( ptr != null ) {
            int cmp = target.compareTo(ptr.key);  // count towards running time
            if ( cmp == 0 ) {
                // found target
                return ptr.value;
            } 
            if ( cmp < 0 ) {
                // target is smaller than ptr.key
                ptr = ptr.left;
            } else {
                // target is greater than ptr.key
                ptr = ptr.right;
            }
        }
        return null; // key is not found
    }

    public void delete (K target) {
	
		// 1. find node to delete: call it x
		Node x = root;
		Node p = null; // previous or parent
		int c = 0;
		while ( x != null ) {
			c = target.compareTo(x.key);
			if ( c == 0 ) {
				break; // found target to delete
			}
			p = x;
			/*if ( c < 0 ) {
				x = x.left;
			} else {
				x = x.right;
			} */
			x = ( c < 0 ) ? x.left : x.right; // ternary operator: same as the if above
		}
		// 2. if x is not found
		if ( x == null ) {
			throw new NoSuchElementException(target + " not found");
		}
		// 3. case 3 (does x have 2 children?)
		Node y = null;
		if ( x.left != null && x.right != null ) {
			// 3.1 find the inorder succcessor
			y = x.right;
			p = x;
			while ( y.left != null ) { // go all the way right
				p = y;
				y = y.left;
			}
			// 3.2 copy y's key and value over x's key and value
			x.key = y.key;
            x.value = y.value;
			
			// 3.3  prepare to delete 
			x = y;
		}
		
		// 4. handle case 1 (leaf) and 2 (one child)
		Node tmp = (x.right != null) ? x.right : x.left;
		if ( x == p.left ) {
			
		} else {
			p.right = tmp;
		}
		size -= 1;
	}

    // Returns the inorder successor of node h
    public Node successor (Node h) {

            if ( h == null || h.right == null ) return null;
            Node ptr = h.right;  // 5 points if student returns any node on the right subtree

            while ( ptr.left != null ) {
                ptr = ptr.left;
            }
            // 20 points for returning the successor 
        return ptr;  // 5 points for returning a node
        // MAX of 10 points if student return inorder predecessor
    }

    // Returns the minimum value of h's subtree
    // STUDENT SOLUTION CAN BE DIFFERENT
    private Node minimum (Node h) {

        if ( h.left == null ) { // 10 points for base case
            return h; // 5 pointing for returning a node that is on the left subtree
        }
        return minimum (h.left); // 15 points for recursive step on left subtree
        // If student returns minimum node on the right subtree MAX of 10 points
    }

    // Returns the minimum value of entire BST
    public K min (Node h) {
        return minimum(root).key;
    }
        
    // Enqueue all the keys into queue
    public LinkedQueue<K> keys() 
    { 
       LinkedQueue<K> q = new LinkedQueue<K>(); 
       inorder(root, q); 
       return q;
    }

    // Traverses the tree inorder: ascending order
    public void inorder(Node x, LinkedQueue<K> q) 
    { 
        if (x == null) return; 
        inorder(x.left, q); 
        q.enqueue(x.key);  // counted towards the running time
        inorder(x.right, q); 
    } 

    public void preorder(Node x, LinkedQueue<K> q) 
    { 
        if (x == null) return;
        q.enqueue(x.key); 
        preorder(x.left, q);  
        preorder(x.right, q); 
    } 

    public void postorder(Node x, LinkedQueue<K> q) 
    { 
        if (x == null) return; 
        postorder(x.left, q);  
        postorder(x.right, q); 
        q.enqueue(x.key);
    } 

    public void print() {
        foo(root);
    }

    private void foo(Node x) 
    {
        if (x == null) 
            return; 
        StdOut.print(x.key);
        foo(x.left);
        foo(x.right); 
    }
    
    public static void main (String args[]) {

        // Key is the NetID
        // Value is the Student
        BST<NetID, Student> bst = new BST<NetID,Student>();
       
        // create netid and student objects
        NetID id = new NetID("jm123");
        Student s = new Student("John Mars",2022);
        // then insert 
        bst.put(id, s);

        // OR create and insert in one line
        bst.put(new NetID("ds39"), new Student("David Sun",2024));
        bst.put(new NetID("af43"), new Student("Ann Fleur",2023));
        bst.put(new NetID("cs87"), new Student("Caroline Smith",2022));
        bst.put(new NetID("mh421"),new Student("Martha Hamilton",2024));
        bst.put(new NetID("js564"),new Student("John Stewart",2022));
        bst.put(new NetID("mm34"), new Student("Marc Medina",2025));

        // retrieve the value associated with key js564
        s = bst.get(new NetID("js564"));
        System.out.println(s);

        // Retrieve all keys
        LinkedQueue<NetID> allKeysQueue = new LinkedQueue<NetID>();
        bst.inorder(bst.root, allKeysQueue);

        // Print all keys
        while ( !allKeysQueue.isEmpty() ) {
            System.out.println(allKeysQueue.dequeue().getID());
        }

        /*
        // Delete
        bst.delete(new NetID("af43"));

        // Retrieve all keys
        allKeysQueue = new LinkedQueue<NetID>();
        bst.inorder(bst.root, allKeysQueue);

        while ( !allKeysQueue.isEmpty() ) {
            System.out.println(allKeysQueue.dequeue().getID());
        }
        */
    }
}

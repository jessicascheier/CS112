public class LinkedList {
    
    Node front; // reference to the first node of the LL

    LinkedList () {
        front = null; // no linked list
    }

    // running time O(1)
    public void addFront (int item) {
        // 1. create a node object with item as data
        // that points to the front node
        Node n = new Node(item,front);

        // 2. hook node at the begining of the LL
        front = n;
    }

    // running time: O(n)
    public void print () {
        // starts on the first node (front)
        Node ptr = front; // ptr points to where front points too
        while ( ptr != null ) {
            // it is not the end of the LL
            System.out.print(ptr.data + " -> ");

            // ptr hops into the next node of the LL
            ptr = ptr.next; // count this towards running time
        }
        // we reached the end of the LL
        System.out.println("\\");
    }

    // running time
    // best case: the first item is target O(1)
    // worst case: last item is target, or target is not present O(n)
    public boolean search (int target) {

        for (Node ptr = front; ptr != null; ptr = ptr.next ) {

            if ( ptr.data == target ) { // count this towards running time
                return true;
            }
        }
        return false;
    }

    // running time
    // O(1) adding after the first item
    // O(n) adding after the last item
    public boolean addAfter (int item, int target) {

        Node ptr = front;

        while ( ptr != null && ptr.data != target ) {
            ptr = ptr.next; // count this towards running time
        }

        if ( ptr == null ) {
            // target has not been found
            return false;
        } else {
            Node n = new Node(item, ptr.next); 
            ptr.next = n;
            return true;
        }
    }

    // O(1)
    public void clear () {
        front = null;
    }

    // O(n) deleting the last item
    public boolean delete (int target) {

        Node ptr = front;
        Node prev = null;

        // is ptr out of the LL, has ptr found the target?
        while ( ptr != null && ptr.data != target ) {
            prev = ptr;
            ptr = ptr.next;
        }

        // target was not found OR target was found

        if ( ptr == null ) {
            // item not found
            return false;
        } else if ( ptr == front ) {
            // target is the front node
            front = front.next;
        } else {
            prev.next = ptr.next;
        }
        return true;
    }

    public static void main (String[] args) {
        // instantiate a LL
        LinkedList intLL = new LinkedList();
        intLL.addFront(4);
        intLL.addFront(2);
        intLL.addFront(1);
        intLL.addFront(0);
        intLL.print();
        intLL.addAfter(3, 2);
        intLL.print();
        intLL.addAfter(5, 4);
        intLL.print();
        intLL.delete(3);
        intLL.print();
        intLL.delete(0);
        intLL.print();
    }
}

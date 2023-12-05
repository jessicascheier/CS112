/*public class Node {

    //instance variables
    int data; //value part of node
    Node next; //links nodes
    
    Node (int d, Node n) { //describes one item in linked list
        data = d;
        next = n;
    }

    public static void main(String[] args) {

    }
    
}*/

public class LinkedList {
    
    Node front; // reference to the first node of the LL

    LinkedList () {
        front = null; // no linked list
    }

    //O(1)
    public void addFront (int item) {
        // 1. create a node object with item as data
        // that points to the front node
        Node n = new Node(item,front);

        // 2. hook node at the begining of the LL
        front = n;
    }

    //O(n) - iterating over every single one in there
    public void print () {
        // starts on the first node (front)
        Node ptr = front; // ptr points to where front points too
        while ( ptr != null ) {
            // it is not the end of the LL
            System.out.print(ptr.data + " -> ");

            // ptr hops into the next node of the LL
            ptr = ptr.next;
        }
        // we reached the end of the LL
        System.out.println("\\");
    }

    // running time
    // best case: the first item is target O(1)
    // worst case: last item is target, or target is not there O(n)
    public boolean search (int target) {

        for (Node ptr = front; ptr != null; ptr = ptr.next ) {

            if ( ptr.data == target ) {
                return true; //count this towards running time
            }
        }
        return false;
    }

    // running time
    // best case: O(1) adding after first item
    // worst case: O(n)
    public boolean addAfter (int item, int target) {

        Node ptr = front;

        while ( ptr != null && ptr.data != target ) {
            ptr = ptr.next; //count this towards running time
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

    //O(n)
    public boolean delete (int target) {
        Node ptr = front;
        Node prev = null;
        while (ptr != null && ptr.data != target) {
            prev = ptr;
            ptr = ptr.next;
        }

        //target was not found OR target was found
        
        if (ptr == null) {
            //item not found
            return false;
        } else if (ptr == front) {
            //target is the front node
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
    }
}

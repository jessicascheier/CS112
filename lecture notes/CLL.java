public class CLL<T> { //type of data i'm holding, string integer, any object , like arraylisst
    
    private class Node<T> {
        T data;
        Node<T> next;
    }

    private Node<T> last; // points to the last node in the LL
    private int size;

    public CLL () {
        last = null;
        size = 0;
    }
    
    public boolean isEmpty () {
        return size == 0;
        //return last == null;
    }

    public int size() {
        return size;
    }

    public void addToLast (T item) {

        Node<T> oldLast = last;
        
        if ( last != null ) {
            last = new Node<T>();
            last.data = item;
            last.next = oldLast.next; // points to front
            oldLast.next = last;
        } else {
            // list is empty
            last = new Node<T>();
            last.data = item;
            last.next = last;
        }

        size += 1;
    }

    public void addToFront (T item) {

        Node<T> n = new Node<T>();
        n.data = item;

        if ( last == null ) {
            // list is empty
            n.next = n; // node points to itself
            last = n;
        } else {
            n.next = last.next; // new node points to the front of the list
            last.next = n; // last points to the new front
        }

        size += 1;
    }

    public void print () {

        if ( isEmpty() ) {
            System.out.println("CLL is empty");
        } else {
            Node<T> ptr = last.next; //points to first node
            do {
                System.out.print(ptr.data + " -> ");
                ptr = ptr.next; //go to the next node
            } while ( ptr != last.next );
            System.out.println(" back to front");
        }
    }

    public static void main (String[] args) {

        CLL<String> grocery = new CLL<String>();
        grocery.addToFront("lettuce");
        grocery.addToFront("bananas");
        grocery.addToFront("potatoes");
        grocery.addToFront("nuggets");
        grocery.addToFront("lemon");
        grocery.print();

        CLL<Integer> numbers = new CLL<Integer>();
        numbers.addToFront(4);
        numbers.addToFront(3);
        /*Doubly Linked List
         1 --> 2 --> 3 --> 4
           <--   <--   <--
         F

         D (data) N (next node) P (previous node), allows us to connect nodes back and forth
         good for spotify playlists cuz you can go back and forth

         [\ 1 -->] [<-- 2 -->] [<-- 3 -->] [<-- 4 \]
                       PTR
        updating number 1, need to update previous part of 3
        | node 1:|      | node 3:|
         ptr.prev.next = ptr.next, 
         ptr.next.prev = ptr.prev,
        this is how you delete an item (#2)
        #2 will be garbage collected*/

    }
}

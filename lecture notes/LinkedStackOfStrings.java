import java.util.NoSuchElementException;

public class LinkedStackOfStrings {
    
    private Node first = null; // front of the list
    
    // nested private class seen inside the file only
    private class Node   {      
        private String item;      
        private Node next;   
    }
    
    public boolean isEmpty() {  
        return first == null;  
    }
 
    // Running time? O(1)
    // AddToFront
    public void push(String item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }   
    
    // Running time? O(1)
    // Problems? Underflow
    public String pop() {

        if ( isEmpty() ) {
            // underflow
            // code returns to the caller
            // the caller receives the object
            throw new NoSuchElementException("My stack is empty");
        }
        String item = first.item;
        first = first.next;
        return item;   
    }

    public static void main (String[] args) {
        LinkedStackOfStrings s = new LinkedStackOfStrings();
        s.push("jacket");
        s.push("shirt");
        s.push("pants");
        s.push("socks");
       
        String item = null;
        int n = 4;
        try {
            while ( n > 0 ) {
                item = s.pop();
                System.out.println("Popped item: " + item);
                n--;
            } 
        } catch (NoSuchElementException  e) {
            // where we handle the exception
            System.out.println("Caught the exception." + e.getMessage());
        }

        
    }
}
 
import edu.princeton.cs.algs4.*;

public class FixedCapacityStackOfStrings {
   
    private String[] s;
    private int n = 0;  // number of items on the stack
    
    public FixedCapacityStackOfStrings(int capacity)   {  
        s = new String[capacity]; 
    }
    
    public boolean isEmpty() {  
        return n == 0;  
    } 
   
    // 1.Running time? O(1)
    // 2.Problems? overflow (not enough space: stack is full)
    public void push(String item) {  
        s[n] = item; 
        n += 1;
    }  
   
    // 1.Running time? O(1)
    // 2.Problem? underflow (stack is empty)
    // 3. loitering: holding a reference to an object that is no longer needed
    public String pop() {
        n -= 1;
        String item = s[n]; // save the item to return to user
        s[n] = null;        // IMPORTANT to remove the reference to avoid loitering
        return item;
    }



    public static void main(String[] args) {

        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(4);

        StdOut.print("Enter items to be pushed onto the stack: ");
        while ( !StdIn.isEmpty() ) {
            stack.push(StdIn.readString());
        }

        System.out.println("All items pushed on the stack, pop from stack");

        while ( !stack.isEmpty() ) {
            System.out.println("\t" + stack.pop());
        }

        System.out.println("Stack empty");
    }
}

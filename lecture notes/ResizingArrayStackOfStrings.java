import edu.princeton.cs.algs4.*;

public class ResizingArrayStackOfStrings {
    
    private String[] s; // data
    private int size = 0;  // number of items in the stack
    
    public ResizingArrayStackOfStrings()   {  
        s = new String[1]; //one array positon 
    }
    
    public boolean isEmpty() {  
        return size == 0;  
    } 
   
    /* 
     * Avoids overflow by doubling the size of the array
     * Two possibilities: 
     *     a) increase the size of the array by 1 at every push: infeasible as it becomes too expensive ~n^2
     *     b) double the size of the array whenever array is full: ~n [in amortized time analysis O(1)]
     *
     * Running time in terms of array accesses
     * Best case: O(1)
     * Worst case: 2n + 1 => ~2n => O(n)
     */
    public void push(String item) {  
        if (size == s.length) {
            // reached capacity, now double the size
            resize (2 * s.length);
        }
        s[size] = item;  
        size += 1;
    }  

    // Running time in terms of array accesses (tilde and big-oh): ~2n, O(n)
    private void resize ( int capacity ) {

        // new array with capacity
        String[] copy = new String[capacity];

        for (int i = 0; i < size; i++){
            copy[i] = s[i];
        }
        // replace old array s with new array copy
        s = copy; // s and copy point to the same array after this line

        // garbage collector will get rid of the old array s
    }
   
    // Running time in terms of array accesses
    // Best case: O(1)
    // Worst case: ~2n + 2 => O(n)
    public String pop() {
        String item = s[size-1]; // save the item to return to user
        s[size-1] = null; // let garbage collector know that there 
                       //are no references to the item that was just popped
        size -= 1;
        if ( size > 0 && size == s.length/4 ) {
            resize(s.length/2);
        }
        return item;
    }

    


    public static void main(String[] args) {

        // this is the client
        ResizingArrayStackOfStrings stack = new ResizingArrayStackOfStrings();

        System.out.print("Enter a string: ");
        while ( !StdIn.isEmpty() ) {
            System.out.print("Enter a string: ");
            stack.push(StdIn.readString());
        }

        System.out.println("All items pushed on the stack, pop from stack");

        while ( !stack.isEmpty() ) {
            System.out.println("\t" + stack.pop());
        }

        System.out.println("Stack empty");
    }
}

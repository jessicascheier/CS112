package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        int numProd = StdIn.readInt();

        Warehouse warehouse = new Warehouse();
        while (!StdIn.isEmpty()) {
            int day = Integer.parseInt(StdIn.readString());
            int id = Integer.parseInt(StdIn.readString());
            String name = StdIn.readString();
            int stock = Integer.parseInt(StdIn.readString());
            int demand = Integer.parseInt(StdIn.readString());
            warehouse.betterAddProduct(id, name, stock, day, demand);
        }
        StdOut.println(warehouse);
    }
}

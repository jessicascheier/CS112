package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        int numOfProducts = StdIn.readInt();
        Warehouse warehouse = new Warehouse();
        int count = 0;
        while (count != numOfProducts) {
            int day = Integer.parseInt(StdIn.readString());
            int id = Integer.parseInt(StdIn.readString());
            String name = StdIn.readString();
            int stock = Integer.parseInt(StdIn.readString());
            int demand = Integer.parseInt(StdIn.readString());
            warehouse.addProduct(id, name, stock, day, demand);
            count++;
        }
        StdOut.println(warehouse);
    }
}
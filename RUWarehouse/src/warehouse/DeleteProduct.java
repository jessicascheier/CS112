package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        int numOfProducts = StdIn.readInt();
        Warehouse warehouse = new Warehouse();
        int count = 0;
        while (count != numOfProducts) {
            String str = StdIn.readString();
            if (str.equals("delete")) {
                int id = Integer.parseInt(StdIn.readString());
                warehouse.deleteProduct(id);
            } else if (str.equals("add")) {
                int day = Integer.parseInt(StdIn.readString());
                int id = Integer.parseInt(StdIn.readString());
                String name = StdIn.readString();
                int stock = Integer.parseInt(StdIn.readString());
                int demand = Integer.parseInt(StdIn.readString());
                warehouse.addProduct(id, name, stock, day, demand);
            }
        }
        StdOut.print(warehouse);
    }
}

package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse warehouse = new Warehouse();
        int numOfProducts = StdIn.readInt();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str.equals("restock")) {
                int id = Integer.parseInt(StdIn.readString());
                int amount = Integer.parseInt(StdIn.readString());
                warehouse.restockProduct(id, amount);
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

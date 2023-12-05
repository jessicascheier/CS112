package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        int numOfProducts = StdIn.readInt();
        Warehouse warehouse = new Warehouse();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str.equals("purchase")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                warehouse.purchaseProduct(id, day, amount);
            } else if (str.equals("add")) {
                int day = Integer.parseInt(StdIn.readString());
                int id = Integer.parseInt(StdIn.readString());
                String name = StdIn.readString();
                int stock = Integer.parseInt(StdIn.readString());
                int demand = Integer.parseInt(StdIn.readString());
                warehouse.addProduct(id, name, day, stock, demand);
            }
        }
        StdOut.print(warehouse);
    }
}

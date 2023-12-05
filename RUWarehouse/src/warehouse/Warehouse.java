package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        Product newProduct = new Product(id, name, stock, day, demand); // create product from given arguments
        int last = id%10; // last digit
        sectors[last].add(newProduct); // add into designated sector
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int fix = sectors[id%10].getSize(); // get size of sector
        sectors[id%10].swim(fix); // swim
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        if (sectors[id%10].getSize() == 5) { // size is 5
            sectors[id%10].swap(1, 5); // swap
            sectors[id%10].deleteLast(); // deleteLast
            sectors[id%10].sink(1); // sink
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        for (int i = 1; i < sectors[id%10].getSize() + 1; i++) {
            if (sectors[id%10].get(i).getId() == id) {
                sectors[id%10].get(i).updateStock(amount); // update stock of item that matches given id
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        for (int i = 1; i < sectors[id%10].getSize() + 1; i++) {
            if (sectors[id%10].get(i).getId() == id) { // if id matches given id
                int size = sectors[id%10].getSize(); // get size of sector
                sectors[id%10].swap(i, size); // swap
                sectors[id%10].deleteLast(); // delete
                sectors[id%10].sink(i); // sink to fix heap
            }
        }
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int loc = id%10; // location
        for (int i = 1; i < sectors[loc].getSize() + 1; i++) {
            if (amount < sectors[loc].get(i).getStock() && sectors[loc].get(i).getId() == id) {
                sectors[loc].get(i).updateStock(amount * -1); // update stock, amount purchased is deducted
                sectors[loc].get(i).setLastPurchaseDay(day); // update last day it was purchased
                sectors[loc].get(i).updateDemand(amount); // demand increased by amount purchased
                sectors[loc].sink(i);
            }
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        if (sectors[id%10].getSize() < 5) {
            addProduct(id, name, stock, day, demand);
        } else {
            int second = 0;
            if (id%10 != 9) {
                second = ((id%10) + 1);
            } else {
                second = 0;
            }
            while (sectors[second].getSize() >= 5) {
                if (second == id%10) {
                    break;
                }
                if (second == 9) {
                    second = 0;
                }
                else {
                    second++;
                }
            }
            addProduct(second, name, stock, day, demand);
        }
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
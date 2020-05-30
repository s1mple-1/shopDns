package dns;

public class Product {
    private String name;
    private long price;
    boolean warranty;
    private long totalPrice;

    public Product(String name, long price, boolean warrainty, long totalPrice) {
        this.name = name;
        this.price = price;
        this.warranty = warrainty;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}

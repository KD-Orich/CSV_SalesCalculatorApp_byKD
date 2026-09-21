package p9979w;

public class ProductSales {

    private String productName;
    private int totalSales;
    private int totalQuantity;

    public ProductSales(
            String productName,
            int totalSales,
            int totalQuantity) {

        this.productName = productName;
        this.totalSales = totalSales;
        this.totalQuantity = totalQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
}
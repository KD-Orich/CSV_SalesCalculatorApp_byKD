package p9979w;

/*
toString() は「参照型をリストに入れたとき」に便利
例えば、
ArrayList<Sales> list = new ArrayList<>();
この Sales が自分で作ったクラスなら、list の中身は Salesオブジェクト（参照型）。そこで、
System.out.println(list);
とすると、各要素について toString() が呼ばれる。

重要なのは、外から
sale.getPrice()
sale.getQuantity()
のように値を取得できるようにしておくことです。


*/

public class Sale {
    private String salesId;
    private String salesDate;
    private String employeeId;
    private String productName;
    private int unitPrice;
    private int quantity;

    public Sale(String salesId, String salesDate, String employeeId, String productName, int unitPrice, int quantity) {
        this.salesId = salesId;
        this.salesDate = salesDate;
        this.employeeId = employeeId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    public String getSalesId() {
        return salesId;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getProductName() {
        return productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "売上ID: " + salesId + ", 日付け: " + salesDate + ", 従業員ID: " + employeeId + ", 商品名: " + productName
                + ", 単価: " + unitPrice + ", 数量: " + quantity + "\n";
    }
}

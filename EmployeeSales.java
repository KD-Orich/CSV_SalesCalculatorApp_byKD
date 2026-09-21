package p9979w;

//ここのクラスは、先に社員id 名前 売上 ボーナス判定をまとめておくと、あとで集計が楽になるかと思って作ったが、
//そうにはならなかった様子。bonusTargetを現在使っていないかも。
public class EmployeeSales {
    private String employeeId;
    private String employeeName;
    private int totalSales;
    private boolean bonusTarget;

    public EmployeeSales(String employeeId,String employeeName,int totalSales,boolean bonusTarget) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.totalSales = totalSales;
        this.bonusTarget = bonusTarget;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public boolean isBonusTarget() {
        return bonusTarget;
    }

    public EmployeeSales(String employeeId,String employeeName,int totalSales) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.totalSales = totalSales;
    }
}

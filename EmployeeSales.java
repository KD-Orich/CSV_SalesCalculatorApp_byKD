package p9979w;

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
}

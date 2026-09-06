package p9979w;

public class Employee {
    private String employeeId;
    private String name;
    private String department;
    private String status;

    public Employee(String employeeId, String name, String department, String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "従業員番号：" + employeeId+ ", 担当者名：" + name+ ", 所属：" + department+ ", 在籍状況：" + status;
    }
}

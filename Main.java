package p9979w;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

/*
p9980のデータを、ウィンドウから出せるようにしていきたいという目的で作成。趣味だからとりあえず置いておく。
クラス	役割
Main	アプリ開始
Sale	1件の売上データ
Employee	1人の従業員データ
EmployeeSales	従業員別集計結果
SalesCalculator	売上計算
SaleCsvReader	売上CSV読み込み
EmployeeCsvReader	従業員CSV読み込み
SalesManagementFrame	ウィンドウ・ボタン
という感じです。これは今まで勉強してきた**「1つのクラスに何でも詰め込まない」「高凝集・低結合」**にも繋がってきます。




*/


//まず Main.java はこんなにシンプルにします。「売上管理システムのウィンドウを作ってください」という感じです。
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SalesManagementFrame();
        });
    }
}


//         try {
//             BufferedReader reader = new BufferedReader(new FileReader("p9979wData/sales.csv"));
//             String header = reader.readLine();
//             String line;
//             List<Sale> sales = new ArrayList<>();
//             while ((line = reader.readLine()) != null) {
//                 String[] data = line.split(",");
//                 Sale sale = new Sale(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]),
//                         Integer.parseInt(data[5]));
//                 sales.add(sale);
//             }
//             System.out.println("sales.csvのデータリスト\n"+sales);
//             System.out.println("employees.csvのデータリスト");

//             for (Employee employee : employees) {
//                 System.out.println(employee.getEmployeeId()+ " "+ employee.getName()+ " "+ employee.getDepartment()+ " "+ employee.getStatus());
//                 String employeeId  = employee.getEmployeeId();
//             }
//             SalesCalculator calculator = new SalesCalculator();
//             Map<String, Integer> employeeSales = new HashMap<>();
//             for (Sale sale : sales) {
//                 int salesAmount = calculator.calculateSales(sale);
//                 String employeeId = sale.getEmployeeId();
//                 employeeSales.put(
//                         employeeId,
//                         employeeSales.getOrDefault(employeeId, 0) + salesAmount);
//             }
//             List<EmployeeSales> rankingList = new ArrayList<>();
//             for (Employee employee : employees) {
//                 String employeeId = employee.getEmployeeId();
//                 String employeeName = employee.getName();
//                 int totalSales = employeeSales.getOrDefault(employeeId, 0);
//                 boolean bonusTarget = totalSales >= 1_800_000;
//                 EmployeeSales result = new EmployeeSales(employeeId,employeeName,totalSales,bonusTarget);
//                 rankingList.add(result);
//             }
//             System.out.println("社員ごとの売上ランキング＆ボーナス支給判定");
//             rankingList.sort((employee1, employee2) -> Integer.compare(employee2.getTotalSales(),employee1.getTotalSales()));
//             int rank = 1;
//             for (EmployeeSales result : rankingList) {
//                 String bonus;
//                 if (result.isBonusTarget()) {
//                     bonus = "ボーナス対象";
//                 } else {
//                     bonus = "対象外";
//                 }
//                 System.out.println(rank + "位 "+ result.getEmployeeId() + " "+ result.getEmployeeName() + " "+ result.getTotalSales() + "円 "+ bonus);
//                 rank++;
//             }
//         } catch (IOException ex) {
//             System.out.println("ファイルの読み込みに失敗しました。");
//         }
//     }
// }

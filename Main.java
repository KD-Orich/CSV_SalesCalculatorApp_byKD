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
        // 全体の説明「SwingのGUI処理を、Swing専用のスレッドで実行してください
        // SwingUtilitiesさん、あとでこの処理を実行してね。その処理は
        // new SalesManagementFrame()です。で、SalesManagementFrameに飛ぶ。」
        // SwingUtilitiesの説明「JavaのSwingに用意されているユーティリティクラス
        // Swingには、JFrame,JButton,JLabel,JTextField,などGUIを作るためのクラスがある。」
        // invokeLaterの説明「「この処理をSwingのイベントディスパッチスレッド（EDT）で実行してね」というもの。
        // SwingUtilitiesのinvokeLater()メソッドがnew new SalesManagementFrame();入れる。
        // 厳密には、invokeLater() が new SalesManagementFrame() そのものを「EDTに移動」させているわけではない。
        // new A() を実行する処理（Runnable）をイベントキューに登録して、EDTが後でそれを実行する
        // SwingではGUI操作を基本的に EDT（Event Dispatch Thread） という専用スレッドで行うルールがある。
        // EDTは Event Dispatch Thread（イベントディスパッチスレッド）という「処理を実行する担当者」。
        // EDTはヒープ領域やstaticフィールドのような「メモリ上の場所」ではない。
        // ラムダ式「「()なので今回は引数はありません。矢印の右側の処理を実行してください」という意味。
        // 「呼び出されたら{ } の中の、実際にやってほしい処理(インスタンスを作ってね)」という処理を渡している。」
        // Runnable と書いていないのになぜラムダ式が使えるのかというと
        // Javaは invokeLater() の引数の型を見ている。invokeLater() は概念的には、
        // public static void invokeLater(Runnable task)というメソッド。javaがRunnableが必要だなと判断して
        // じゃあこれは Runnable として扱えばいいな。として、ラムダ式には
        // 1つの抽象メソッドしか持たないインターフェースのうち、Runnableが選ばれる。
        // これを ターゲット型（target type） と考えると分かりやすい。
        SwingUtilities.invokeLater(() -> {
            // ここでデータ型と変数を指定せずnewしている。コンストラクタを実行すること自体に意味がある場合に使える。
            new SalesManagementFrame();
        });
    }
}

/*
 * ラムダ式は「1つの抽象メソッドしか持たないインターフェース」を、簡単に実装するときに使える。
 * ラムダ式勉強 今回のラムダの分解から。
 * インターフェースを実装してrun()を呼べるように。
 * public interface Runnable {
 * void run();
 * }
 * public class Main implements Runnable {
 * 
 * @Override
 * public void run() {
 * new SalesManagementFrame();
 * }
 * }
 * 「説明」
 * invokeLater が受け取るのは「クラス」ではなく Runnable型で
 * public static void invokeLater(Runnable doRun)に、
 * SwingUtilities.invokeLater(task);で渡している。
 * invokeLaterに渡す→EDTがrun()する→SalesManagementFrameのrun()が実行される。
 * 
 * Runnable task = new Main();
 * SwingUtilities.invokeLater(task);
 * 
 * 
 * ここでデータ型と変数を指定せずnewしている。コンストラクタを実行すること自体に意味がある場合に使える。
 * new SalesManagementFrame();
 * }
 * });
 * 
 * 
 */

// try {
// BufferedReader reader = new BufferedReader(new
// FileReader("p9979wData/sales.csv"));
// String header = reader.readLine();
// String line;
// List<Sale> sales = new ArrayList<>();
// while ((line = reader.readLine()) != null) {
// String[] data = line.split(",");
// Sale sale = new Sale(data[0], data[1], data[2], data[3],
// Integer.parseInt(data[4]),
// Integer.parseInt(data[5]));
// sales.add(sale);
// }
// System.out.println("sales.csvのデータリスト\n"+sales);
// System.out.println("employees.csvのデータリスト");

// for (Employee employee : employees) {
// System.out.println(employee.getEmployeeId()+ " "+ employee.getName()+ " "+
// employee.getDepartment()+ " "+ employee.getStatus());
// String employeeId = employee.getEmployeeId();
// }
// SalesCalculator calculator = new SalesCalculator();
// Map<String, Integer> employeeSales = new HashMap<>();
// for (Sale sale : sales) {
// int salesAmount = calculator.calculateSales(sale);
// String employeeId = sale.getEmployeeId();
// employeeSales.put(
// employeeId,
// employeeSales.getOrDefault(employeeId, 0) + salesAmount);
// }
// List<EmployeeSales> rankingList = new ArrayList<>();
// for (Employee employee : employees) {
// String employeeId = employee.getEmployeeId();
// String employeeName = employee.getName();
// int totalSales = employeeSales.getOrDefault(employeeId, 0);
// boolean bonusTarget = totalSales >= 1_800_000;
// EmployeeSales result = new
// EmployeeSales(employeeId,employeeName,totalSales,bonusTarget);
// rankingList.add(result);
// }
// System.out.println("社員ごとの売上ランキング＆ボーナス支給判定");
// rankingList.sort((employee1, employee2) ->
// Integer.compare(employee2.getTotalSales(),employee1.getTotalSales()));
// int rank = 1;
// for (EmployeeSales result : rankingList) {
// String bonus;
// if (result.isBonusTarget()) {
// bonus = "ボーナス対象";
// } else {
// bonus = "対象外";
// }
// System.out.println(rank + "位 "+ result.getEmployeeId() + " "+
// result.getEmployeeName() + " "+ result.getTotalSales() + "円 "+ bonus);
// rank++;
// }
// } catch (IOException ex) {
// System.out.println("ファイルの読み込みに失敗しました。");
// }
// }
// }

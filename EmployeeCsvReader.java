package p9979w;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//「CSVをSaleのArrayListに変換するクラス」です。

public class EmployeeCsvReader {
    //「File を1つ受け取って、ArrayList<Employee> を返します」というメソッド。「ファイルを読んでいる途中で問題が起きる可能性がありますよ」と宣言している。
    public ArrayList<Employee> read(File file) throws IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        //Main / GUI→「このファイル読んで」→EmployeeCsvReader→BufferedReader→ArrayList<Employee>→GUIへ返すという流れでファイルを呼ぶようにする。
        BufferedReader br = new BufferedReader(new FileReader(file));
        // ここに今までMainに書いていた
        // CSV読み込み処理を移す
        br.readLine(); // 1行目を読み飛ばす
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            Employee employee = new Employee(data[0],data[1],data[2],data[3]);
            employees.add(employee);
        }
        br.close();
        return employees;
    }
}

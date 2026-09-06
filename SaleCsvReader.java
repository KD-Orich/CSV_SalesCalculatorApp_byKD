package p9979w;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SaleCsvReader {
    //「ファイルを読んでいる途中で問題が起きる可能性がありますよ」とthrows IOExceptionで宣言している。
    public ArrayList<Sale> read(File file) throws IOException {
        //Fileを受け取る→CSVを読む→ArrayList<Sale>を返す
        ArrayList<Sale> sales = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        // 1行目（タイトル）を読み飛ばす
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            Sale sale = new Sale(
                data[0], // 売上番号
                data[1], // 販売日
                data[2], // 従業員ID
                data[3], // 商品名
                Integer.parseInt(data[4]), // 単価
                Integer.parseInt(data[5])  // 数量
            );
            sales.add(sale);
        }
        br.close();
        return sales;
    }
}

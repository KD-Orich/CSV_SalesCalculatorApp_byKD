package p9979w;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
JPanel
JLabel
JButton
JFileChooser
それぞれ、
クラス	役割
JPanel	部品をまとめる箱
JLabel	「従業員CSV：」などの文字
JButton	「ファイルを選択」ボタン
JFileChooser	ファイル選択ダイアログ
です。

*/

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
//テキストエリアを作るなら↓が必要
import javax.swing.JTextArea;
//スクロールするなら↓が必要
import javax.swing.JScrollPane;

import java.io.File;

import java.awt.Dimension;

import java.awt.BorderLayout;
import java.awt.GridLayout;

//Mainから呼ばれて、ウィンドウの設定を決めたこのファイルからウィンドウを出せる。「売上管理システム」というタイトルのウィンドウが出る。

public class SalesManagementFrame extends JFrame {

    // この画面が動いている間、選択した従業員・売上CSVを覚えておくために変数を置いた。File型。後で「1.
    // 全売上表示」などのボタンを押したときに、このファイルを使えます。
    private File employeeFile;
    private File saleFile;

    private ArrayList<Employee> employeeList;
    private ArrayList<Sale> saleList;

    private JTextArea resultArea;
    private SalesCalculator salesCalculator = new SalesCalculator();

    // ココ、コンストラクタなので呼ばれたら実行される。
    public SalesManagementFrame() {
        setTitle("売上管理システム");
        // 最初に開くサイズの指定
        setSize(800, 600);
        // ウィンドウの最小サイズを指定する方法。画面の崩れ防止で考える。
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // メインパネルを作る・GUI部品を入れておく箱を作る
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 上部の headerPanelタイトル部分です。setTitleとは別の様子。
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("売上管理システム");
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // CSV部分の filePanelを作成
        JPanel filePanel = new JPanel();
        // 従業員CSV・従業員CSV：という文字を表示する部品を作っています。
        JLabel employeeLabel = new JLabel("従業員CSV：");
        // [ファイルを選択]というボタンを作っています。
        JButton employeeButton = new JButton("ファイルを選択");
        // ボタンから選択したファイル名を表示する JLabel を追加
        JLabel employeeFileLabel = new JLabel("未選択");
        // 売上CSV
        JLabel saleLabel = new JLabel("売上CSV：");
        JButton saleButton = new JButton("ファイルを選択");
        JLabel saleFileLabel = new JLabel("未選択");
        JButton loadButton = new JButton("データを読み込む");

        // contentPanel を作る、「メニューと結果をまとめる箱」
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // メニューパネルを作ります。
        JPanel menuPanel = new JPanel();
        // 10個のボタンを縦に並べます。10,1,5,5の部分は、10行 × 1列で、ボタン同士に5ピクセルの隙間を空けて並べるという意味。
        menuPanel.setLayout(new GridLayout(10, 1, 5, 5));
        JButton allSalesButton = new JButton("1. 全売上表示");
        JButton employeeSalesButton = new JButton("2. 従業員別売上");
        JButton dailySalesButton = new JButton("3. 日別売上");
        JButton monthlySalesButton = new JButton("4. 月別売上");
        JButton yearlySalesButton = new JButton("5. 年別売上");
        JButton productSalesButton = new JButton("6. 商品別売上");
        JButton salesRankingButton = new JButton("7. 売上ランキング");
        JButton productRankingButton = new JButton("8. 商品販売数量ランキング");
        JButton bonusButton = new JButton("9. ボーナス対象者一覧");
        JButton employeeListButton = new JButton("10. 従業員一覧");
        // 文字数が違っても10個のボタンが同じグリッド内に収まる呪文
        menuPanel.add(allSalesButton);
        menuPanel.add(employeeSalesButton);
        menuPanel.add(dailySalesButton);
        menuPanel.add(monthlySalesButton);
        menuPanel.add(yearlySalesButton);
        menuPanel.add(productSalesButton);
        menuPanel.add(salesRankingButton);
        menuPanel.add(productRankingButton);
        menuPanel.add(bonusButton);
        menuPanel.add(employeeListButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

        // リザルトエリアを伸縮させるために追記2 入れ子構造実装中
        JLabel resultLabel = new JLabel("結果");
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        // JTextArea テキストエリアを作成する データ型の指定はprivate JTextArea resultArea;と上で宣言しているので要らない。
        // 20，60はサイズを指定していたが、サイズが変わるようにデフォルトに変更。
        resultArea = new JTextArea();
        // ユーザーが結果欄を直接編集できないようにします。
        resultArea.setEditable(false);
        // リザルトエリアを伸縮させるために追記
        resultArea.setLineWrap(false);
        // スクロールしたいので追加↓
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(menuPanel, BorderLayout.WEST);
        contentPanel.add(resultPanel, BorderLayout.CENTER);

        mainPanel.add(filePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // パネルに追加
        // panel
        // ├─ 従業員CSV：
        // └─ [ファイルを選択]
        // という状態になります。
        // panelインスタンスに、社員ラベル、ボタン 売上ラベル、ボタンを追加しているというコードなだけ。
        // Swingではパネルを入れ子にして画面を構成するのが一般的です。ということで入れ子構造でパネルを分割して表示する手法
        // JFrame
        // │
        // └─ mainPanel
        // │
        // ├─ headerPanel
        // │
        // ├─ filePanel
        // │
        // └─ contentPanel
        // │
        // ├─ menuPanel
        // │
        // └─ resultPanel
        filePanel.add(employeeLabel);
        filePanel.add(employeeButton);
        filePanel.add(employeeFileLabel);

        // タイトルラベルを新しく作ったので追加
        headerPanel.add(titleLabel);

        filePanel.add(saleLabel);
        filePanel.add(saleButton);
        filePanel.add(saleFileLabel);
        filePanel.add(loadButton);

        // 次に「ボタンを押したらファイル選択」を追加
        // [ファイルを選択]→クリック→JFileChooser→ファイル選択画面 showOpenDialog(this)
        // の意味は、ファイルを開くためのダイアログを、このウィンドウを親として表示する
        employeeButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // 選択したファイルを取得
            // ユーザーが、[開く]を押したのか、[キャンセル]したのかという結果を受け取る文↓
            // showOpenDialog() や showSaveDialog() が返す定数の値は、
            // 定数名 値(int) 意味
            // JFileChooser.APPROVE_OPTION 0 「開く」や「保存」ボタンが押された
            // JFileChooser.CANCEL_OPTION 1 「キャンセル」ボタンや「✕」で閉じられた
            // JFileChooser.ERROR_OPTION -1 エラーが発生した、または異常終了した
            // という数値データをresultに入れている。
            int result = fileChooser.showOpenDialog(this);

            // もし、resultと一緒なら
            // ユーザーがファイル選択画面で「開く」などの確定操作をしたという意味
            if (result == JFileChooser.APPROVE_OPTION) {
                // setText()とは？これは、JLabelに表示されている文字を変更するメソッドです。未選択の部分が、CSVファイル名に変わる呪文。
                // fileChooser.getSelectedFile()の部分が、選択されたファイルそのもののFileオブジェクト。そこからgetName()でファイル名だけを取得できます。
                // employeeFileLabel.setText(fileChooser.getSelectedFile().getName());

                // ↑のsetTextを廃止して、employeeFileを採用していく。
                employeeFile = fileChooser.getSelectedFile();

                // 従業員CSVを選択したときに保存する
                // ファイルを選択→getSelectedFile()→employeeFileに保存→employeeFile.getName()→画面にファイル名表示
                employeeFileLabel.setText(employeeFile.getName());
            }
        });

        // 売上CSVも同じ、売上ボタンにも同じ処理を追加できます。
        saleButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                saleFile = fileChooser.getSelectedFile();
                saleFileLabel.setText(saleFile.getName());
            }
        });

        // 読み込みボタンを押したらチェックする処理
        loadButton.addActionListener(e -> {
            if (employeeFile == null) {
                System.out.println("従業員CSVを選択してください");
                return;
            }
            if (saleFile == null) {
                System.out.println("売上CSVを選択してください");
                return;
            }
            // 読込ボタンを押すときの挙動の流れ。
            // [従業員CSVを選択]→employeeFile→EmployeeCsvReader→employeeList
            // [売上CSVを選択]→saleFile→SaleCsvReader→saleList
            // を作るtry部分。
            // GUIの中に、
            // SalesManagementFrame
            // │
            // ├─ employeeFile
            // ├─ saleFile
            // │
            // ├─ employeeList
            // │
            // └─ saleList
            // ここからのtryで、社員CSVを読んで、売上CSVを読んで、とするだけで読めるので、クラスファイルを作った意味があるという場面。
            try {
                EmployeeCsvReader employeeReader = new EmployeeCsvReader();
                SaleCsvReader saleReader = new SaleCsvReader();
                employeeList = employeeReader.read(employeeFile);
                saleList = saleReader.read(saleFile);
                // setText() の意味は、JTextAreaの中身をこの文字列に置き換えるという意味です。今の中身を上書きしてしまうので、一回しか使えない呪文。
                resultArea.setText("CSV読み込み完了\n" + "従業員数：" + employeeList.size() + "\n" + "売上件数：" + saleList.size());
            } catch (IOException ex) {
                // ※今は仮のprint。GUIをもう少し作り込む段階になったら、「CSVファイルの読み込みに失敗しました」というダイアログを出すようにしましょう。と、予告。
                ex.printStackTrace();
            }
        });

        // 全体売上表示ボタン処理
        // CSVが入っていない場合はnullがくるので、ボタンを押されたときに処理する。
        allSalesButton.addActionListener(e -> {
            if (saleList == null) {
                //※まずはコンソールにエラーメッセージを出す状態。
                System.out.println("先にCSVを読み込んでください");
                return;
            }
            resultArea.setText("");
            // 今回はappendで追加追加していく。setTextすると上書きで消えてしまう為。
            resultArea.append("売上番号,販売日,従業員番号,商品名,単価,数量,売上金額\n");
            // saleList の中に入っている Sale を1件ずつ sale という変数に入れて処理する
            for (Sale sale : saleList) {
                int salesAmount = salesCalculator.calculateSales(sale);
                resultArea.append("売上番号：" + sale.getSalesId() + " " + "販売日：" + sale.getSalesDate() + " " + "従業員番号："
                        + sale.getEmployeeId() + " " + "商品名：" +
                        sale.getProductName() + " " + "単価：" + sale.getUnitPrice() + "円 " + "数量：" + sale.getQuantity()
                        + " " + "売上金額：" + salesAmount + "円\n");
            }
        });

        // 従業員別売上用アクションリスナー
        // employeeSalesButton が操作されたときに、この処理を実行してくださいという式(JButtonのメソッド)
        // e -> {の部分は、ラムダ式 new ActionListener(){@Override public void
        // actionPerformed(ActionEvent e){ が省略されている。}}
        employeeSalesButton.addActionListener(e -> {
            // 一度リザルトエリアのテキストを空文字にする処理
            resultArea.setText("");
            // 従業員IDと、その従業員の売上合計を保存するMapをここで作ります。Stringがキー、Integerが値です。
            Map<String, Integer> employeeSales = new HashMap<>();
            // saleList の中にある Sale を1件ずつ取り出す。取り出しながら、売上金額を計算し、社員番号を取ってくる。
            for (Sale sale : saleList) {
                // 売上金額を計算 salesCalculatorのcalculateSalesにsaleを渡すと、Sale型
                // saleに渡せて、計算して返ってくるのでint型salesAmountに入れる。
                int salesAmount = salesCalculator.calculateSales(sale);
                // 従業員IDを取り出す
                String employeeId = sale.getEmployeeId();
                // この従業員IDの現在の合計を取得する。まだ存在しなければ0を返す。最初に従業員IDから売上金額を拾おうとすると存在がないので、0を入れてから始めるということ。
                // E001はまだMapに存在しない→0を返す。今回の売上が50,000円なら、0 + 50,000→50,000、となっていく。
                // 従業員IDを参照しつつ、employeeSalesというMAPに、拡張for文で従業員IDと売上合計額を保存していく。
                employeeSales.put(
                        employeeId,
                        employeeSales.getOrDefault(employeeId, 0) + salesAmount);
            }
            // MAPキーで作った従業員別売上リストを、従業員の番号順に並べ替える処理をここで入れる。
            // employeeSales.keySet()でMapのキーを取り出します。それをnew ArrayList<>(...)でListにします。
            List<String> employeeIds = new ArrayList<>(employeeSales.keySet());
            // そして、employeeIds.sort(String::compareTo);で並べ替えます。その後順番に表示します。ここがメソッド参照で新しい技術。
            employeeIds.sort(String::compareTo);
            // ここで一人ずつ順番に表示する処理。
            // 最初の for が終わったら、employeeSales の中身が、E001 →
            // 70000なら、employeeSales.keySet()によって、E001というキーの集合を取得できます。
            for (String employeeId : employeeIds) {
                // 売上合計を取得として、employeeIdが E001 なら、employeeSales.get("E001")となり、MapがE001 →
                // 70000ならtotalSalesには、70000が入ります。
                int totalSales = employeeSales.get(employeeId);
                // ここはsetTextではなくてappend()。resultArea.append("A");→
                // 現在の文字の後ろに追加する。上のsetTextで先に全部消してから、append(...)で結果を1行ずつ追加しています。
                resultArea.append(
                        "従業員ID：" + employeeId
                                + "　売上金額：" + totalSales + "円\n");
            }
        });

        //➆に飛んで、売上ランキングを作成していく。
        salesRankingButton.addActionListener(e -> {
            //リザルトエリアの初期化（更新処理）
            resultArea.setText("");
            //従業員ID → その従業員の合計売上を保存する Map の準備。
            Map<String, Integer> employeeSales = new HashMap<>();

            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                String employeeId = sale.getEmployeeId();

                employeeSales.put(
                    employeeId,
                    employeeSales.getOrDefault(employeeId, 0) + salesAmount
                );
            }
            //➁集計結果をEmployeeSalesに変換
            List<EmployeeSales> rankingList = new ArrayList<>();

            for (String employeeId : employeeSales.keySet()) {

                int totalSales = employeeSales.get(employeeId);

                String employeeName = "";

                for (Employee employee : employeeList) {

                    if (employee.getEmployeeId().equals(employeeId)) {
                        employeeName = employee.getName();
                        break;
                    }
                }

                EmployeeSales employeeSalesData =
                    new EmployeeSales(employeeId, employeeName, totalSales);

                rankingList.add(employeeSalesData);
            }
            //大きい売上 → 小さい売上の降順を作る形。2→1の順で書くと大きい順で、1→2の順で書くと小さい順。
            rankingList.sort((employee1, employee2) ->Integer.compare(
                        employee2.getTotalSales(),
                        employee1.getTotalSales()
                    )
            );

            //すでに売上順になった rankingList を、上から順番に表示する処理
            int rank = 1;

            for (EmployeeSales employee : rankingList) {

                resultArea.append(
                    "第" + rank + "位　"
                    + "従業員ID：" + employee.getEmployeeId()
                    + "　名前：" + employee.getEmployeeName()
                    + "　売上金額：" + employee.getTotalSales() + "円\n"
                );

                rank++;
            }
        });


        //➈ボーナス判定
        bonusButton.addActionListener(e -> {

            resultArea.setText("");
            Map<String, Integer> employeeSales = new HashMap<>();
            //売上リストから同じようにリストを作る。今後のリファクタリングの勉強としてこのまま作る。
            //１従業員ごとの売上を集計
            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                String employeeId = sale.getEmployeeId();

                employeeSales.put(
                    employeeId,
                    employeeSales.getOrDefault(employeeId, 0) + salesAmount
                );
            }
            //２EmployeeSalesに従業員ごとのデータをまとめる
            List<EmployeeSales> employeeSalesList = new ArrayList<>();

            for (String employeeId : employeeSales.keySet()) {

                int totalSales = employeeSales.get(employeeId);

                String employeeName = "";

                for (Employee employee : employeeList) {

                    if (employee.getEmployeeId().equals(employeeId)) {
                        employeeName = employee.getName();
                        break;
                    }
                }

                EmployeeSales employeeSalesData =
                    new EmployeeSales(employeeId, employeeName, totalSales);

                employeeSalesList.add(employeeSalesData);
            }

            //３従業員idで比較するソート。String型のまま比較している。
            employeeSalesList.sort((employee1, employee2) ->employee1.getEmployeeId().compareTo(
                        employee2.getEmployeeId()
                    )
            );

            //４ボーナス判定して表示
            for (EmployeeSales employeeSalesData : employeeSalesList) {

                if (employeeSalesData.getTotalSales() >= 1700000) {

                    resultArea.append(
                        "従業員ID：" + employeeSalesData.getEmployeeId()
                        + "　名前：" + employeeSalesData.getEmployeeName()
                        + "　売上金額：" + employeeSalesData.getTotalSales() + "円"
                        + "　ボーナス対象\n"
                    );

                } else {

                    resultArea.append(
                        "従業員ID：" + employeeSalesData.getEmployeeId()
                        + "　名前：" + employeeSalesData.getEmployeeName()
                        + "　売上金額：" + employeeSalesData.getTotalSales() + "円"
                        + "　対象外\n"
                    );
                }
            }
        });

        //➉従業員一覧表示
        employeeListButton.addActionListener(e -> {

            resultArea.setText("");

            // 従業員ID順に並べる。元の employeeList 自体を並べ替えないために新しくインスタンス化する。
            List<Employee> sortedEmployeeList = new ArrayList<>(employeeList);

            sortedEmployeeList.sort(
                (employee1, employee2) ->
                    employee1.getEmployeeId().compareTo(
                        employee2.getEmployeeId()
                    )
            );

            // 従業員情報を表示
            for (Employee employee : sortedEmployeeList) {

                resultArea.append(
                    "従業員ID：" + employee.getEmployeeId()
                    + "　名前：" + employee.getName()
                    + "　部署：" + employee.getDepartment()
                    + "　雇用状況：" + employee.getStatus()
                    + "\n"
                );
            }
        });

        //➂日別売上
        //yyyy/MM/dd のように年月日の桁・形式が統一されているので、String型のままMAPに入れて比較できるという算段。
        dailySalesButton.addActionListener(e -> {

            resultArea.setText("");

            Map<String, Integer> dailySales = new HashMap<>();

            // 日付ごとに売上を集計
            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                String salesDate = sale.getSalesDate();

                dailySales.put(
                    salesDate,
                    dailySales.getOrDefault(salesDate, 0) + salesAmount
                );
            }

            // 日付を昇順に並べる
            List<String> salesDates =
                new ArrayList<>(dailySales.keySet());

            salesDates.sort(String::compareTo);

            // 表示
            for (String salesDate : salesDates) {

                int totalSales = dailySales.get(salesDate);

                resultArea.append(
                    "売上日：" + salesDate
                    + "　売上金額：" + totalSales + "円\n"
                );
            }
        });

        //➃月別売上
        monthlySalesButton.addActionListener(e -> {

            resultArea.setText("");

            Map<String, Integer> monthlySales = new HashMap<>();

            // 月ごとに売上を集計
            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                String salesDate = sale.getSalesDate();

                //例えばsubstring(2, 5)だとしたら、0 1 [2 3 4] 5の234に該当する文字だけが取り出せる。
                //文字： 2 0 2 6 / 0 9 / 2 1
                //番号： 0 1 2 3 4 5 6 7 8 9、という上下の文字列の関係性。今回は↓
                // yyyy/MM/dd → yyyy/MM 0番目から始めて、7番目は含めず、0～6番目を取り出す。という式
                String salesMonth = salesDate.substring(0, 7);

                monthlySales.put(
                    salesMonth,
                    monthlySales.getOrDefault(salesMonth, 0) + salesAmount
                );
            }

            // 月を昇順に並べる
            List<String> salesMonths =
                new ArrayList<>(monthlySales.keySet());

            salesMonths.sort(String::compareTo);

            // 表示
            for (String salesMonth : salesMonths) {

                int totalSales = monthlySales.get(salesMonth);

                resultArea.append(
                    "売上月：" + salesMonth
                    + "　売上金額：" + totalSales + "円\n"
                );
            }
        });


        //➄年別売上
        yearlySalesButton.addActionListener(e -> {

            resultArea.setText("");

            Map<String, Integer> yearlySales = new HashMap<>();

            // 年ごとに売上を集計
            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                String salesDate = sale.getSalesDate();

                // yyyy/MM/dd → yyyy、ここが月別と違うところで、前半4文字のみ抜き取って判定している。
                String salesYear = salesDate.substring(0, 4);

                yearlySales.put(
                    salesYear,
                    yearlySales.getOrDefault(salesYear, 0) + salesAmount
                );
            }

            // 年を昇順に並べる
            List<String> salesYears =
                new ArrayList<>(yearlySales.keySet());

            salesYears.sort(String::compareTo);

            // 表示
            for (String salesYear : salesYears) {

                int totalSales = yearlySales.get(salesYear);

                resultArea.append(
                    "売上年：" + salesYear
                    + "　売上金額：" + totalSales + "円\n"
                );
            }
        });


        //➅商品別売上
        productSalesButton.addActionListener(e -> {

            resultArea.setText("");

            Map<String, Integer> productSales = new HashMap<>();

            // 商品ごとに売上を集計
            for (Sale sale : saleList) {

                int salesAmount = salesCalculator.calculateSales(sale);

                //ここが商品名で今回はMAPに入れる部分。商品名で同じ商品名の金額は足す仕組み。
                String productName = sale.getProductName();

                productSales.put(
                    productName,
                    productSales.getOrDefault(productName, 0) + salesAmount
                );
            }

            // 商品名をListにする
            List<String> productNames =
                new ArrayList<>(productSales.keySet());

            // 売上金額の高い順に並べる
            productNames.sort(
                (product1, product2) ->
                    Integer.compare(
                        productSales.get(product2),
                        productSales.get(product1)
                    )
            );

            // ランキング表示
            int rank = 1;

            for (String productName : productNames) {

                int totalSales = productSales.get(productName);

                resultArea.append(
                    "第" + rank + "位　"
                    + "商品名：" + productName
                    + "　売上金額：" + totalSales + "円\n"
                );

                rank++;
            }
        });


        //➇商品販売個数ランキングと同数なら売上額もランキング
        productRankingButton.addActionListener(e -> {

            resultArea.setText("");

            Map<String, ProductSales> productMap = new HashMap<>();

            // 商品ごとに売上金額と販売数量を集計
            for (Sale sale : saleList) {

                String productName = sale.getProductName();

                int salesAmount =
                    salesCalculator.calculateSales(sale);

                int quantity =
                    sale.getQuantity();

                ProductSales productSales =
                    productMap.get(productName);

                if (productSales == null) {

                    productSales = new ProductSales(
                        productName,
                        salesAmount,
                        quantity
                    );

                } else {

                    productSales = new ProductSales(
                        productName,
                        productSales.getTotalSales() + salesAmount,
                        productSales.getTotalQuantity() + quantity
                    );
                }

                productMap.put(productName, productSales);
            }

            // Mapの値をListにする
            List<ProductSales> rankingList =
                new ArrayList<>(productMap.values());

            // ①販売数量が多い順
            // ②同じ数量なら売上金額が高い順
            rankingList.sort(
                (product1, product2) -> {
                    //まず数量を比較します。
                    int quantityCompare =
                        Integer.compare(
                            product2.getTotalQuantity(),
                            product1.getTotalQuantity()
                        );

                    //数量が違うなら、数量の比較結果だけで順位を決める
                    //「販売数量を比較した結果、同じ数量ではなかったなら、その結果をそのまま採用する」
                    if (quantityCompare != 0) {
                        //「この比較処理の結果として、quantityCompareの値を返します」
                        return quantityCompare;
                    }

                    return Integer.compare(
                        product2.getTotalSales(),
                        product1.getTotalSales()
                    );
                    /*
                    ここのif文面白くて、メソッドと違いラムダ式だからreturnで終わるけどその下にも式を書けるみたい。
                    if (quantityCompare != 0) {
                        return quantityCompare;
                    } else {
                        return Integer.compare(
                            product2.getTotalSales(),
                            product1.getTotalSales()
                        );
                    }
                    と同じ動きをするタイプで、ifでtrueならreturnで終わり。
                    そうでなければ次のreturn Integerまで進むという形。
                     */
                }
            );

            // ランキング表示
            int rank = 1;

            for (ProductSales productSales : rankingList) {

                resultArea.append(
                    "第" + rank + "位　"
                    + "商品名：" + productSales.getProductName()
                    + "　販売数量：" + productSales.getTotalQuantity() + "個"
                    + "　売上金額：" + productSales.getTotalSales() + "円\n"
                );

                rank++;
            }
        });

        // メインパネルをウィンドウに追加
        // このSalesManagementFrame()という、JFrameの中にmainPanelを配置してくださいという追加add。
        add(mainPanel);
        // ここまでで、
        // JFrame
        // └── JPanel
        // ├── JLabel
        // ├── JButton
        // ├── JLabel
        // └── JButton
        // という構造になる。

        // 「このコンポーネントを表示状態にする」という意味。visible（見える状態） = true で扱う。falseだと非表示にする、となる。
        // new JFrame()の部分は、JFrameオブジェクトを作っただけで、「画面に表示して」とは言っていない。ので、表示する式が必要。
        setVisible(true);
    }
}

package p9979w;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    public SalesManagementFrame() {
        setTitle("売上管理システム");
        //最初に開くサイズの指定
        setSize(800, 600);
        //ウィンドウの最小サイズを指定する方法。画面の崩れ防止で考える。
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // メインパネルを作る・GUI部品を入れておく箱を作る
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //上部の headerPanelタイトル部分です。setタイトルとは別なのかな？
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("売上管理システム");
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel,BorderLayout.NORTH);

        //CSV部分の filePanelを作成
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


        //contentPanel を作る、「メニューと結果をまとめる箱」
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());


        //メニューパネルを作ります。
        JPanel menuPanel = new JPanel();
        //10個のボタンを縦に並べます。10,1,5,5の部分は、10行 × 1列という意味になるらしい。謎。
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
        //文字数が違っても10個のボタンが同じグリッド内に収まる呪文
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

        //リザルトエリアを伸縮させるために追記2 入れ子構造実装中
        JLabel resultLabel = new JLabel("結果");
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        // JTextArea テキストエリアを作成する データ型の指定どうなってるんだろ。
        // 20，60はサイズを指定していたが、サイズが変わるようにデフォルトに変更。
        resultArea = new JTextArea();
        // ユーザーが結果欄を直接編集できないようにします。
        resultArea.setEditable(false);
        //リザルトエリアを伸縮させるために追記
        resultArea.setLineWrap(false);
        // スクロールしたいので追加↓
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);


        contentPanel.add(menuPanel, BorderLayout.WEST);
        contentPanel.add(resultPanel, BorderLayout.CENTER);


        mainPanel.add(filePanel,BorderLayout.NORTH);
        mainPanel.add(contentPanel,BorderLayout.CENTER);



        // パネルに追加
        // panel
        // ├─ 従業員CSV：
        // └─ [ファイルを選択]
        // という状態になります。
        // panelインスタンスに、社員ラベル、ボタン 売上ラベル、ボタンを追加しているというコードなだけ。
//         Swingではパネルを入れ子にして画面を構成するのが一般的です。ということで入れ子構造でパネルを分割して表示する手法
        // JFrame
        // │
        // └─ mainPanel
        //    │
        //    ├─ headerPanel
        //    │
        //    ├─ filePanel
        //    │
        //    └─ contentPanel
        //       │
        //       ├─ menuPanel
        //       │
        //       └─ resultPanel
        filePanel.add(employeeLabel);
        filePanel.add(employeeButton);
        filePanel.add(employeeFileLabel);

        //タイトルラベルを新しく作ったので追加
        headerPanel.add(titleLabel);

        filePanel.add(saleLabel);
        filePanel.add(saleButton);
        filePanel.add(saleFileLabel);
        filePanel.add(loadButton);


        //以下古いpanel用なのでコメントアウト。-----------
        // panel.add(saleLabel);
        // panel.add(saleButton);
        // panel.add(saleFileLabel);

        // データを読み込むボタン用
        // panel.add(loadButton);
        // 全売上表示ボタン用←すでに作っているのでコメントアウトにしておく。
        //menuPanel.add(allSalesButton);
        // JTextAreaを画面に追加
        // panel.add(resultArea);
        // JTextArea画面にスクロールを作ろうとしたら指定が変わった↓ 縦の場合。
        //panel.add(scrollPane);

        // パネルをウィンドウに追加はお引越し

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
            // というのをresultに入れているはず。
            int result = fileChooser.showOpenDialog(this);

            // もし、resultと一緒なら
            // ユーザーが選択したファイルを表示かな？
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

        // 全体売上表示ボタン処理 ※まずはコンソールに出してみる状態。
        // CSVが入っていない場合はnullがくるので、ボタンを押されたときに処理する。
        allSalesButton.addActionListener(e -> {
            if (saleList == null) {
                System.out.println("先にCSVを読み込んでください");
                return;
            }
            // 計算用SalesCalculatorをインスタンス化
            SalesCalculator calculator = new SalesCalculator();
            resultArea.setText("");
            // 今回はappendで追加追加していく。setTextすると上書きで消えてしまう為。
            resultArea.append("売上番号,販売日,従業員番号,商品名,単価,数量,売上金額\n");
            // saleList の中に入っている Sale を1件ずつ sale という変数に入れて処理する
            for (Sale sale : saleList) {
                int salesAmount = calculator.calculateSales(sale);
                resultArea.append("売上番号：" + sale.getSalesId() + " " + "販売日：" + sale.getSalesDate() + " " + "従業員番号："
                        + sale.getEmployeeId() + " " + "商品名：" +
                        sale.getProductName() + " " + "単価：" + sale.getUnitPrice() + "円 " + "数量：" + sale.getQuantity()
                        + " " + "売上金額：" + salesAmount + "円\n");
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

        setVisible(true);
    }
}

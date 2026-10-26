package p9979w;

/*
ArrayListから取り出したものを別クラスに引数として渡す
部分です。
sale.getUnitPrice()のところ。別クラスのメソッドを呼びだしている。クラスから別クラスのメソッドを直接呼んでいるようにも見える。
Quantityはクォンタティと読む。数量。

public int calculateSales(Sale sale)が
Mainクラスから、calculateSales(Saleクラスのオブジェクト)という形で呼び出せるようになる呪文。
つまりArrayListの中のsaleインスタンスのメソッドgetterでデータを取得できる。sale.getPrice()など。
SalesCalculator calculator = new SalesCalculator();
for (Sale sale : saleList) {
    int salesAmount = calculator.calculateSales(sale);
    System.out.println(salesAmount);
}
という呪文で
saleListにSale　1　売上金額　と取り出せるらしい。
SalesCalculator型calculatorインスタンス変数を作り
forでSale型sale変数をsaleList回数ループ。
Amount→アマウント　総計
*/
public class SalesCalculator {
    //ここにSaleインスタンス化したオブジェクトを召喚する。
    //クラスから、calculateSales(Saleクラスのオブジェクト)という形で呼び出せるようになる呪文。
    //つまりArrayListの中のsaleインスタンスのメソッドgetterでデータを取得できる。sale.getPrice()など。
    public int calculateSales(Sale sale) {
    //オブジェクトを呼ぶだけで、中のメソッドを呼んでgetter出来る。
    return sale.getUnitPrice() * sale.getQuantity();
    }
}

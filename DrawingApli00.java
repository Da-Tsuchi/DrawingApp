/**
 * DrawingApli00.java
 * 
 * Simple Drawing Application
 * 簡単なお絵かきソフト
 * ・フリーハンド，直線，四角，楕円(塗りつぶし)の描画機能
 * 
 * オプション機能
 * 1.図形の描画経過を描画する
 * 2.消しゴム機能を追加
 * 3.太さを変更する機能
 *
 */
import java.awt.*;
import java.awt.event.*;


public class DrawingApli00 extends Frame implements ActionListener {
    // ■ フィールド変数
    Button bt1, bt2, bt3, bt4,bt5,bt6,bt7,bt8,bt9,bt10; // フレームに配置するボタンの宣言
    Panel  pnl;                // ボタン配置用パネルの宣言
    MyCanvas mc;               // 別途作成した MyCanvas クラス型の変数の宣言

    // ■ main メソッド（スタート地点）
    public static void main(String [] args) {
        new DrawingApli00(); 
    }

    //全消去　背景色で画面サイズ分塗りつぶし
    //逐次描画　画用紙を複数用意

    // ■ コンストラクタ
    DrawingApli00() {
        super("PAINT");
        this.setSize(1000, 600); 

        pnl = new Panel();       // Panel のオブジェクト（実体）を作成
        mc = new MyCanvas(this); // mc のオブジェクト（実体）を作成

        this.setLayout(new BorderLayout(10, 10)); // レイアウト方法の指定
        this.add(pnl, BorderLayout.NORTH);         // 上側に パネルを配置
        this.add(mc,  BorderLayout.CENTER);       // 中央に mc （キャンバス）を配置
                                            // BorerLayout の場合，West と East の幅は
                                            // 部品の大きさで決まる，Center は West と East の残り幅
        pnl.setLayout(new GridLayout(1,9));  // ボタンを配置するため，９行１列のグリッドをパネル上にさらに作成
        bt1 = new Button("～"); bt1.addActionListener(this); pnl.add(bt1);// ボタンを順に配置
        bt2 = new Button("/");      bt2.addActionListener(this); pnl.add(bt2);
        bt3 = new Button("□"); bt3.addActionListener(this); pnl.add(bt3);
        bt4 = new Button("○");      bt4.addActionListener(this); pnl.add(bt4);
        bt5 = new Button("■");      bt5.addActionListener(this); pnl.add(bt5);
        bt6 = new Button("●");      bt6.addActionListener(this); pnl.add(bt6);
        bt7 = new Button("全消去");      bt7.addActionListener(this); pnl.add(bt7);
        bt8 = new Button("消しゴム");      bt8.addActionListener(this); pnl.add(bt8);
        bt9 = new Button("色変更");      bt9.addActionListener(this); pnl.add(bt9);
        bt10 = new Button("太さ変更");      bt10.addActionListener(this); pnl.add(bt10);

        this.setVisible(true); //可視化
    }

    // ■ メソッド
    // ActionListener を実装しているため、例え内容が空でも必ず記述しなければならない
    public void actionPerformed(ActionEvent e){ // フレーム上で生じたイベントを e で取得
        if (e.getSource() == bt1)      // もしイベントが bt1 で生じたなら
        mc.mode=1;                   // モードを１に
        else if (e.getSource() == bt2) // もしイベントが bt2 で生じたなら
        mc.mode=2;                   // モードを２に
        else if (e.getSource() == bt3) // もしイベントが bt3 で生じたなら
        mc.mode=3;                   // モードを３に
        else if (e.getSource() == bt4) // もしイベントが bt4 で生じたなら
        mc.mode=4;                   // モードを４に
        else if (e.getSource() == bt5) // もしイベントが bt5 で生じたなら
        mc.mode=5;                   // モードを5に
        else if (e.getSource() == bt6) // もしイベントが bt6 で生じたなら
        mc.mode=6;                   // モードを6に
        else if (e.getSource() == bt7){ // もしイベントが bt7 で生じたなら
            mc.mode=7;                   // モードを7に
            mc.repaint();
        }
        else if (e.getSource() == bt8) // もしイベントが bt8 で生じたなら
        mc.mode=8;                   // モードを8に
        else if (e.getSource() == bt9){ // もしイベントが bt9 で生じたなら
        mc.mode=9;                   // モードを9に
        mc.colorSelect = new ColorSelect();
        mc.colorFlag = true;//別ウィンドウで色を指定
        }
        else if (e.getSource() == bt10){ // もしイベントが bt10 で生じたなら
            mc.mode=10;                   // モードを10に
            mc.thickSelect = new ThickSelect();
            mc.thickFlag = true;
        }
    }
}   
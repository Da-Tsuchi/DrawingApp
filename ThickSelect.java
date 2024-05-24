import java.awt.*;
import java.awt.event.*;

public class ThickSelect extends Frame implements AdjustmentListener,ActionListener{
    // ■ フィールド変数
    Label lb1, lb2, lb3, lb4;
    Scrollbar sbar1, sbar2, sbar3, sbar4;
    String s1 = "", s2 = "";
    int thick = 1;
    int decThick;//任意の線の太さ
    Panel p_north = new Panel();
    Button btDic;//決定ボタン（ボタン型変数）

    // ■ コンストラクタ
    ThickSelect() {
        super("太さ指定");
        this.setSize(400, 300); 

        // 上部にスクロールバーを配置するためにボーダーレイアウトを利用
        this.setLayout(new BorderLayout());
        // 上部の領域 (NORTH) 内に８行のグリッドレイアウトをはめ込む
        p_north.setLayout(new GridLayout(2,1));

        lb1 = new Label("太さ", Label.CENTER); 
        p_north.add(lb1); // ラベルを配置

        sbar1 = new Scrollbar(Scrollbar.HORIZONTAL, 5, 10, 1, 20);
        sbar1.addAdjustmentListener(this); 
        p_north.add(sbar1); // スクロールバーを配置

        this.add(p_north, BorderLayout.NORTH);

        //決定ボタンを設定
        Panel p_south = new Panel();
        p_south.setLayout(new GridLayout(1,1));
        btDic = new Button("太さを決定");p_south.add(btDic);  // ボタン設定
        btDic.addActionListener(this);    
        add(p_south, BorderLayout.SOUTH);         // パネルを SOUTHに配置

        this.setVisible(true);
    }

    //メソッド
    // スクロールバーのいずれかが動かされたら呼び出される
    public void adjustmentValueChanged(AdjustmentEvent e) {
        Scrollbar sbar = (Scrollbar)e.getAdjustable();
        if (sbar == sbar1) {
        thick = sbar1.getValue();
        s2 = "太さ:" + thick;
        }
        repaint();
    }

    // ボタンクリック処理
    public void actionPerformed(ActionEvent e){
        Button btn = (Button)e.getSource();
        if(btn == btDic){
            //決定ボタンが押されたとき
            decThick = thick;
            
            repaint();
            this.setVisible(false);//不可視化
        }
        System.out.println(decThick);
	}

    // このメソッドは，上位の Frame クラスのメソッドをオーバーライド（上書き）
    public void paint(Graphics g) {
        g.setColor(Color.black); // ペンの色設定
        // 色つきの四角を描く
        g.fillRect(10, 10, getSize().width-20, thick*10);

        g.setColor(Color.BLACK);       // ペンの色設定
        g.drawString(s2, (getSize().width/2)-20, getSize().height/2); // 文字列表示2
        
  }
}

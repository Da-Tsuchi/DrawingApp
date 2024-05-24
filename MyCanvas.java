 /**
 * MyCanvas.java
 * 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;

class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
    // ■ フィールド変数
    int x, y,$px,$py;   // mouse pointer position
    int px, py; // preliminary position
    int ow, oh; // width and height of the object
    int mode;   // drawing mode associated as below
    //色の値(初期値は黒)
    int red =0;
    int green = 0;
    int blue=0;
    int thick = 1;//線の太さ

    boolean drawMidFlag = false;//描画途中かどうかを判定するためのフラグ
    boolean colorFlag = false;//別ウィンドウで色を指定するときのフラグ
    boolean thickFlag = false;//別ウィンドウで太さを指定するときのフラグ
    
    Image img = null;   // 仮の画用紙
    Image imgDraft = null;//描画途中用の画用紙
    
    Graphics gc = null; // 仮の画用紙用のペン
    Graphics gcDraft = null;//描画途中用のペン
    
    ColorSelect colorSelect;
    ThickSelect thickSelect;

    Dimension d; // キャンバスの大きさ取得用

    // ■ コンストラクタ
    MyCanvas(DrawingApli00 obj){
        mode=0;                       // initial value 
        this.setSize(1500,900);        // キャンバスのサイズを指定
        addMouseListener(this);       // マウスのボタンクリックなどを監視するよう指定
        addMouseMotionListener(this); // マウスの動きを監視するよう指定
    }

    // ■ メソッド（オーバーライド）
    // フレームに何らかの更新が行われた時の処理
    public void update(Graphics g) {
        paint(g); // 下記の paint を呼び出す
    }

    // ■ メソッド（オーバーライド）
    public void paint(Graphics g) {
        d = getSize();   // キャンバスのサイズを取得
        if (img == null) {// もし仮の画用紙の実体がまだ存在しなければ
            img = createImage(d.width, d.height); // 作成
        }
        if (gc == null){  // もし仮の画用紙用のペン (GC) がまだ存在しなければ
            gc =img.getGraphics(); // 作成
        }
        if (imgDraft == null) {// もし描画途中用の画用紙の実体がまだ存在しなければ
            imgDraft = createImage(d.width, d.height); // 作成
        }
        if (gcDraft == null){  // もし描画途中用の画用紙用のペン (gcDraft) がまだ存在しなければ
            gcDraft = imgDraft.getGraphics(); // 作成
        }

        Graphics2D g3 = (Graphics2D)gcDraft;//描画途中のペンの太さを変えるためにsetStrokeメソッドを使いGraphic2Dに型変換
        g3.setStroke(new BasicStroke(thick));//太さを太く設定

        //colorFlagが上がったときは任意のRGB値を反映
        if(colorFlag == true){
            //colorSelectクラスの変数を指定
            red = colorSelect.decRed;
            green = colorSelect.decGrn;
            blue = colorSelect.decBlu;
            gc.setColor(new Color(red, green, blue)); // ペンの色設定
        }

        //thickFlagが上がったときは任意のRGB値を反映
        if(thickFlag == true){
            //thickSelectクラスの変数を指定
            thick = thickSelect.decThick;
            Graphics2D g2 = (Graphics2D)gc;//setStrokeメソッドを使うためにGraphic2Dに型変換
            g2.setStroke(new BasicStroke(thick));//消しゴム用に太さを太く設定
        }
        switch (mode){
            case 1: // フリーハンド
                gc.drawLine(px, py, x, y); // 仮の画用紙に描画
                break;
            case 2: // 直線
                if(drawMidFlag == true){
                    gcDraft.setColor(Color.white);//色を白に設定
        		    gcDraft.fillRect(0, 0 , d.width, d.height);//背景を消す
                    gcDraft.drawImage(img, 0,0,this);//途中を描画する紙にこれまでの描画結果をのせる
                    g3.setStroke(new BasicStroke(thick));//太さを設定
                    gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                    gcDraft.drawLine(px, py, x,y);//描画経過を描く
                }else{
                    gc.drawLine(px, py, x, y); // 仮の画用紙に描画
                }
                break;
            case 3: // 四角
                if(drawMidFlag == true){
                    gcDraft.setColor(Color.white);//色を白に設定
                    gcDraft.fillRect(0, 0 , d.width, d.height);//背景を消す
                    gcDraft.drawImage(img, 0,0,this);//途中を描画する紙にこれまでの描画結果をのせる
                    g3.setStroke(new BasicStroke(thick));//太さを設定
                    gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                    gcDraft.drawRect(px,py, ow, oh);//描画経過を描く
                }else{
                    gc.drawRect(px, py, ow, oh); // 仮の画用紙に描画
                }
                break;
            case 4: // 楕円
                if(drawMidFlag == true){
                    gcDraft.setColor(Color.white);//色を白に設定
                    gcDraft.fillRect(0, 0 , d.width, d.height);//背景を消す
                    gcDraft.drawImage(img, 0,0,this);//途中を描画する紙にこれまでの描画結果をのせる
                    g3.setStroke(new BasicStroke(thick));//太さを設定
                    gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                    gcDraft.drawOval(px, py, ow, oh);//描画経過を描く
                }else{
                    gc.drawOval(px, py, ow, oh); // 仮の画用紙に描画
                }
                    break;
            case 5://塗りつぶし四角
                if(drawMidFlag == true){
                    gcDraft.setColor(Color.white);//色を白に設定
                    gcDraft.fillRect(0, 0 , d.width, d.height);//背景を消す
                    gcDraft.drawImage(img, 0,0,this);//途中を描画する紙にこれまでの描画結果をのせる
                    g3.setStroke(new BasicStroke(thick));//太さを設定
                    gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                    gcDraft.fillRect(px,py, ow, oh);//描画経過を描く
                }else{
                    gc.fillRect(px, py, ow, oh); // 仮の画用紙に描画
                }
                break;
            case 6://塗りつぶし楕円
                if(drawMidFlag == true){
                    gcDraft.setColor(Color.white);//色を白に設定
                    gcDraft.fillRect(0, 0 , d.width, d.height);//背景を消す
                    gcDraft.drawImage(img, 0,0,this);//途中を描画する紙にこれまでの描画結果をのせる
                    g3.setStroke(new BasicStroke(thick));//太さを設定
                    gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                    gcDraft.fillOval(px, py, ow, oh);//描画経過を描く
                }else{
                    gc.fillOval(px, py, ow, oh); // 仮の画用紙に描画
                }
                break;
            case 7://全消去
                gc.setColor(Color.white);//ペンの色を白にして
                gc.fillRect(0, 0, d.width, d.height);//キャンバス全体を白くする
                gcDraft.setColor(new Color(red, green, blue));//任意のRGB値を反映
                gc.setColor(new Color(red, green, blue));//任意のRGB値を反映
                break;

            case 8://消しゴム
                Graphics2D g2 = (Graphics2D)gc;//setStrokeメソッドを使うためにGraphic2Dに型変換
                g2.setStroke(new BasicStroke(thick+3));//消しゴム用に太さを太く設定
                gc.setColor(Color.white);
                gc.drawLine(px, py, x, y);//線を描く
                g2.setStroke(new BasicStroke(thick));//太さをもとに戻す
                gc.setColor(new Color(red,green,blue));//色をもとに戻す
                break;

            case 9://色選択
                colorFlag = true;//別ウィンドウで色を指定
                colorSelect = new ColorSelect();//色選択クラスのインスタンスを生成
                break;

            case 10://太さ選択
                thickFlag = true;
                thickSelect = new ThickSelect();//太さ選択クラスのインスタンスを生成
                break;
        }
        if(drawMidFlag==true){//描画経過を描くフラグが立っているときはdraftの仮の画用紙に描く
            g.drawImage(imgDraft, 0, 0, this);// 仮の画用紙の内容を draft に描画
            
        }else{//それ以外は仮の画用紙に描く
            g.drawImage(img, 0, 0, this); // 仮の画用紙の内容を MyCanvas に描画
        }
    }

    // ■ メソッド
    
    public void mousePressed(MouseEvent e){ // マウスボタンが押された時の処理
        switch (mode){
        case 1:
        case 8: // mode が１,8の場合，次の内容を実行する
            x = e.getX();
            y = e.getY();
            break;
            
        case 2: // mode が２もしくは
            px = e.getX();
            py = e.getY();
            break;
        case 3:
        case 4:
        case 5:
        case 6: // ３,４,5,6の場合，次の内容を実行する
            px =$px= e.getX();
            py =$py= e.getY();
            break;
        case 7:
            x = e.getX();
            y = e.getY();
            break;
        }
    }

    public void mouseReleased(MouseEvent e){ // マウスボタンが離された時の処理
        switch (mode){
        case 2: // mode が２
            x = e.getX();
            y = e.getY();
            drawMidFlag =false;//描画途中のフラグを下げる
            repaint(); // 再描画
            break;

        case 3: //modeが3,4,5,または6
        case 4:
        case 5:
        case 6:
            x = e.getX();
            y = e.getY();
            //左上以外からの描画を可能にする（$付き変数は定数）
            if(x<$px){//マウスがある位置が始点より左側なら
                px = x;//中身を入れ替え
            }else{//右側
                px = $px;//中身を戻す
            }
            if(y<$py){//マウスがある位置が始点より上側なら
                py = y;//中身を入れ替え
            }else{//下側
                py = $py;//中身を戻す
            }
            //図形の幅、高さ
            ow = Math.abs(x-$px);
            oh = Math.abs(y-$py);
            drawMidFlag =false;//描画途中のフラグを下げる
            repaint();
            break;
        }
    }

    public void mouseDragged(MouseEvent e){ // マウスがドラッグされた時の処理
        switch (mode){
        case 1: 
        case 8:// mode が１,8の場合，次の内容を実行する
            px = x;
            py = y;
            x = e.getX();
            y = e.getY();
            repaint(); // 再描画
            break;
        case 2://mode2
            drawMidFlag = true;//描画途中のフラグを上げる
            x = e.getX();
            y = e.getY();
            repaint();// 再描画
            break;
        case 3:  //modeが3,4,5,または6
        case 4:
        case 5:
        case 6:
            drawMidFlag = true;//描画途中のフラグを上げる
            x = e.getX();
            y = e.getY();
            //左上以外からの描画を可能にする
            if(x<$px){//マウスがある位置が始点より左側
                px = x;//中身入れ替え
            }else{//右側
                px = $px;//中身を戻す
            }
            if(y<$py){//マウスがある位置が始点より上側
                py = y;//中身を入れ替え
            }else{//下側
                py = $py;//中身を戻す
            }
            //図形の幅、高さ
            ow = Math.abs(x-$px);
            oh = Math.abs(y-$py);
            //確認用
            System.out.printf("$px=%d|$py=%d|px=%d|py=%d|x=%d|y=%d",$px,$py,px,py,x,y);
            System.out.println("");
            repaint();//再描画
            break;
        }
    }

    // ■ メソッド
    // 下記のマウス関連のメソッドは，MouseMotionListener をインターフェースとして実装しているため
    // 例え使わなくても必ず実装しなければならない
    public void mouseMoved(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
    public void mouseClicked(MouseEvent e){}// 今回は使わないが、無いとコンパイルエラー
    public void mouseEntered(MouseEvent e){}// 今回は使わないが、無いとコンパイルエラー
    public void mouseExited(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
}
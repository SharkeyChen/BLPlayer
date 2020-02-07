import sun.font.FontDesignMetrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class BarrageScene extends JFrame{
    private int barrage_max = 50;
    private ArrayList<Barrage> list = new ArrayList<Barrage>();
    private ArrayList<Barrage> backlist = new ArrayList<Barrage>();
    private int barwidth;
    private int barheight;
    private int posX,posY;
    public BarrageScene(Point pos){
        this.posX = pos.x;
        this.posY = pos.y;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.barwidth = 874;
        this.barheight = 528;
        this.setLocation(this.posX,this.posY);
        this.setSize(874,528);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(0,0,0,0));
        this.setType(Type.UTILITY);
        this.Paint_Barrage();
        this.setVisible(true);
    }

    public void Add_Barrage(String k,int y, float stamp){
        System.out.println(this.barwidth);
        Barrage b = new Barrage(k, y , this.barwidth, stamp);
        FontDesignMetrics fm = FontDesignMetrics.getMetrics(b.font);
        b.width = fm.stringWidth(b.str);
        b.height = fm.getHeight();
        list.add(b);
        System.out.println("添加弹幕");
    }

    public void setBarrageWH(int width,int height){
        this.barwidth = width - 25;
        this.barheight = height;
        setSize(width - 25,height);
    }

    @Override
    public void setLocation(Point pos){
        setLocation(pos.x + 12, pos.y + 55);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
//        System.out.println("正在滚动" + list.size() + "条弹幕");
        for(int i = 0;i < list.size();i ++){
            Barrage b = list.get(i);
            g.setFont(b.font);
            g.setColor(b.color);
            g.drawString(b.str, b.x, b.y);
        }
    }

    public void Paint_Barrage(){
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0;i < list.size();i ++){
                    Barrage b = list.get(i);
                    b.x -= 1;
                    if(b.Border_Judge() == true){
                        list.remove(i);
                        System.out.println("弹幕退出");
                    }
                }
                repaint();
            }
        },100,5);
    }

    public void ReadBarrageFile(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(new File(path + "\\弹幕.csv")));
            System.out.println("读取地址");
            String line = null;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String item[] = line.split(",");
                backlist.add(new Barrage(item[item.length-2], 50, this.barwidth, Float.parseFloat(item[item.length-1])));
                backlist.sort(new Comparator<Barrage>() {
                    @Override
                    public int compare(Barrage o1, Barrage o2) {
                        if(o1.stamp < o2.stamp)
                            return -1;
                        else{
                            return 1;
                        }
                    }
                });
            }
        }catch(Exception e){
            System.out.println("弹幕加载失败");
            return ;
        }
//        for(int i = 0;i < backlist.size() ;i ++){
//            System.out.println(backlist.get(i).str + " "  + backlist.get(i).stamp);
//        }
        System.out.println("弹幕加载成功");
    }

    public void CheckBarrageList(float stamp){
        if(backlist.size() == 0){
            return ;
        }
        else{
            Barrage b = backlist.get(0);
            if(stamp >= b.stamp) {
                b.y = (int) (Math.random() * (500 - 30) + 30);
                list.add(b);
                backlist.remove(0);
                return;
            }
        }
    }
}
